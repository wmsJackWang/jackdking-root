package example;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName SlideCounterLimiter
 * @Description TODO
 * @Author jackdking
 * @Date 28/07/2023 11:55 上午
 * @Version 2.0
 **/

import java.util.Date;
import java.util.Deque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 滑动窗口限流
 * 使用方式：
 * 1.使用时创建一个SlideCounterLimiter对象
 * 2.请求进入调用requestAccess()方法，根据访问结果判断是否被限制访问
 *
 * 设计思路：
 * 1.滑动窗口被分割成N个子窗口，创建一个限制长度为N的队列
 * 2.队列首部是最老的子窗口，队列尾部是最新的子窗口
 * 3.每个请求都需要找到自己所在的子窗口，如果没有就创建，并加入到队列，如果队列已满
 *   就把队首最老的子窗口踢除，踢出后统计的请求数要减去被踢出的子窗口接收的请求数
 * 4.所有子窗口是时间上连续的
 * 5.对所有的请求进行区分，第一次请求和后续任意一次请求
 * 6.第一次请求，需要首先初始化(创建一个空的队列，请求时间作为开始时间创建第一个子窗口，被访问总次数设置0次)，第
 *   次请求必然在第一个子窗口时间内，子窗口的受访次数+1，被访问总次数+1。
 * 7.后续任意一次请求，可分为三种情况
 * 8.第一种情况，请求进入直接被统计入队尾的子窗口
 * 9.第二种情况，请求进入后需要以队尾子窗口不断创建后续子窗口并加入队列，直到请求时间可以被统计入新建的子窗口内
 * 10.第三种情况，是对第二种情况的优化，当请求访问离上一次请求间隔太过久远，可以直接初始化作为一个新的开始，不必
 *    用步骤2的处理方式，以减少性能消耗
 * 11.对于上述第二种情况，因为是请求驱动的滑动效果，请求时间是不可预估的，对于一定时间间隔内的请求，可能需要向队
 *    列补齐断层的子窗口，建议不要使用过大的分割数
 *
 */
public class SlideCounterLimiter {
  /**
   * 限流窗口时间长度
   */
  private long periodTime;
  /**
   * 限流窗口访问量上限
   */
  private long limitTimes;
  /**
   * 限流窗口分割成子窗口数
   */
  private int partSize;
  /**
   * 子窗口时间长度
   */
  private long subPeriodTime;
  /**
   * 最新子窗口起始时间(用于判断是否生产子窗口及计算子窗口起始结束时间)
   */
  private long currentTimeStart;
  /**
   * 最新子窗口结束时间(用于判断是否生产子窗口及计算子窗口起始结束时间)
   */
  private long currentTimeEnd;
  /**
   * 双向队列
   * 1.存放子窗口
   * 2.长度为子窗口数
   * 3.最新子窗口尾部添加，最老子窗口头部删除，实现滑动
   */
  private Deque<SubCounterLimiter> deque;
  /**
   * 滑动窗口总访问数
   * 1.记录滑动窗口目前访问总量
   * 2.窗口滑动时，减去最老子窗口的访问量
   * 3.访问成功 + 1
   */
  private long acceptTimes;

  public SlideCounterLimiter(long periodTime, long limitTimes, int partSize) {
    this.periodTime = periodTime;
    this.limitTimes = limitTimes;
    this.partSize = partSize;
    this.subPeriodTime = periodTime/partSize;
  }

  /**
   * 子窗口类
   */
  private class SubCounterLimiter{
    /**
     * 子窗口起始时间
     */
    private long subTimeStart;
    /**
     * 子窗口结束时间
     */
    private long subTimeEnd;
    /**
     * 子窗口接受访问数
     */
    private long subAcceptTimes;

    public SubCounterLimiter(long subTimeStart, long subTimeEnd) {
      this.subTimeStart = subTimeStart;
      this.subTimeEnd = subTimeEnd;
      this.subAcceptTimes = 0L;
    }

    public long getSubTimeStart() {
      return subTimeStart;
    }

    public long getSubTimeEnd() {
      return subTimeEnd;
    }

    public long getSubAcceptTimes() {
      return subAcceptTimes;
    }

    public void setSubTimeStart(long subTimeStart) {
      this.subTimeStart = subTimeStart;
    }

    public void setSubTimeEnd(long subTimeEnd) {
      this.subTimeEnd = subTimeEnd;
    }

    public void setSubAcceptTimes(long subAcceptTimes) {
      this.subAcceptTimes = subAcceptTimes;
    }
  }

  /**
   * 步骤1.区分第一次请求和后续请求，第一次请求执行初始化方法
   * 步骤2.后续请求，分三种情况：
   *   <1>.请求时间与最新一次子窗口的结束时间相差超过一个periodTime，执行初始化方法
   *       作为一次新的开始
   *   <2>.请求时间与最新一次子窗口的结束时间相差少于一个periodTime且不在最新子窗口
   *       的时间段内，则创建新的子窗口且加入队列，并且队列已满时挤出最老的子窗口和a-
   *       -cceptTimes减去挤出子窗口的访问量，直到请求时间在最新的子窗口时间段内
   *   <3>.请求时间在最新一次子窗口内
   * 步骤3.步骤1和步骤2保证了当前请求在最新子窗口时间段内
   * 步骤4.访问次数进行判断，是否可以访问，成功访问则总访问量+1，子窗口访问量+1
   */
  public synchronized boolean requestAccess(){

    long requestTime = new Date().getTime();//请求时间

    if(deque==null){//第一次请求
      init(requestTime);
    }else{//后续任意一次请求
      if(requestTime>=periodTime+deque.getLast().getSubTimeEnd()){
        init(requestTime);
      }else if(requestTime>=deque.getLast().getSubTimeEnd() && requestTime<periodTime+deque.getLast().getSubTimeEnd()){
        recur(requestTime);
      }
    }

    System.out.println("限流时间段：["+deque.getFirst().getSubTimeStart()+"]至["+deque.getLast().getSubTimeEnd() +"]");
    System.out.println("访问时间："+requestTime);
    System.out.println("目前访问量："+acceptTimes);

    if(acceptTimes<limitTimes){
      acceptTimes++;
      deque.getLast().setSubAcceptTimes(deque.getLast().getSubAcceptTimes()+1);
      System.out.println("访问结果：成功！");
      return true;
    }else{
      System.out.println("访问结果：失败，系统忙，稍后再试！");
      return false;
    }

  }

  /**
   * 1.访问量初始化
   * 2.队列初始化
   * 3.创建第一个子窗口
   * 4.队列加入子窗口
   */
  private void init(long requestTime){
    System.out.println("初始化...");
    acceptTimes = 0L;
    deque = new LinkedBlockingDeque<>(partSize);
    currentTimeStart = requestTime;
    currentTimeEnd = currentTimeStart + subPeriodTime;
    SubCounterLimiter initFirst = createSubCounterLimiter(currentTimeStart,currentTimeEnd);
    deque.add(initFirst);
  }

  /**
   * 1.递归获得子窗口，并更新入队列
   * 2.直到请求时刻在获得子窗口时间区间内，该子窗口作为最新子窗口，递归创建子窗口停止
   */
  private void recur(long requestTime){
    currentTimeStart = deque.getLast().getSubTimeEnd();
    currentTimeEnd = currentTimeStart + subPeriodTime;
    SubCounterLimiter last = createSubCounterLimiter(currentTimeStart,currentTimeEnd);
    while(!deque.offerLast(last)){
      acceptTimes = acceptTimes - deque.getFirst().getSubAcceptTimes();
      deque.removeFirst();
    }
    if(requestTime>=deque.getLast().getSubTimeEnd()){
      recur(requestTime);
    }
  }

  /**
   * 创建子窗口
   */
  private SubCounterLimiter createSubCounterLimiter(long timeStart,long timeEnd) {
    return new SubCounterLimiter(timeStart, timeEnd);
  }

}
