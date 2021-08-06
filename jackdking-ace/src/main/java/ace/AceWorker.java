package com.autonavi.aos.tmp.settle.hotel.gather.prepay.ace;


public class AceWorker {
    private static AceWorker aceWorker;
    private AceWorker(){

    }
    public static AceWorker getInstance() {
        if(aceWorker==null) {
            synchronized (AceWorker.class) {
                if(aceWorker==null) {
                    aceWorker = new AceWorker();
                }
            }
        }
        return aceWorker;
    }

    public AceResult classify(String classfierName , AceContext aceContext) {
        return null;
    }

    public AceResult execute(String executorName , AceContext aceContext){

        return null;
    }

}
