package ace;


public class AceWorker {
    private final static AceWorker INSTANCE = new AceWorker();
    private AceWorker(){

    }
    public static AceWorker getInstance() {
        return INSTANCE;
    }

    public AceResult classify(String classfierName , AceContext aceContext) {
        return null;
    }

    public AceResult execute(String executorName , AceContext aceContext){

        return null;
    }

}
