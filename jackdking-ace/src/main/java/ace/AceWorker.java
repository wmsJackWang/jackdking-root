package ace;


public class AceWorker {
    private final static AceWorker INSTANCE = new AceWorker();
    private AceWorker(){

    }
    public static AceWorker getInstance() {
        return INSTANCE;
    }

    /*
     * 遍历所有的分类器，返回满足的分类器并获取分类器返回的执行器。
     */
    public AceResult classify(AceContext aceContext){return null;}

    public AceResult classify(String classfierName , AceContext aceContext) {
        return null;
    }

    public AceResult execute(String executorName , AceContext aceContext){

        return null;
    }

}
