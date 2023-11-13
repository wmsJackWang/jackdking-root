package org.jackdking.algorithm.bytedance.last;

public class JumpFloor {
    public static void main(String[] args) {

        int val = 4;
        int res = jumpFloor20231004(val);
        System.out.println(res);

        res = jumpFloor(val);
        System.out.println(res);

        //双指针， 注意：先变first指针， 再变second指针
        res = jumpFloor20231004V1(val);
        System.out.println(res);

      //双指针， 注意：先变first指针， 再变second指针
      res = jumpFloor20231009(val);
      System.out.println(res);

    }

  private static int jumpFloor20231009(int val) {
      if (val == 1) {
        return 1;
      }
      if (val == 2) {
        return 2;
      }
      int f = 1, s = 2, temp;
      for (int i = 3 ; i<= val ; i++){
        temp = f+s;
        f = s;
        s = temp;
      }
      return s;
  }

  private static int jumpFloor20231004V1(int k) {

        if (k == 1) {
            return 1;
        }
        if (k == 2) {
            return 2;
        }

        int first = 1, second = 2;
        int res = 0;
        for (int i = 3; i <= k ; i++){
            res = first + second;
            first = second;
            second = res;
        }
        return res;
    }

    private static int jumpFloor20231004(int k) {

        if (k <1) {
            return 0;
        }

        if (k == 1){
            return 1;
        }
        if (k == 2) {
            return 2;
        }
        return jumpFloor20231004(k-1)+ jumpFloor20231004(k-2);
    }

    public static int jumpFloor(int target) {
        if(target==1){
            return 1;
        }else if(target==2){
            return 2;
        }else {
            return jumpFloor(target-1)+jumpFloor(target-2);
        }
    }
}
