package code.structure.multibiz.processors;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class MultiBizProcessor {

    private static Map<String, Consumer<String>> bizProcessors;

    public MultiBizProcessor(){
        bizProcessors = new HashMap<>();
        bizProcessors.put("1", this::biz1Processor);
        bizProcessors.put("2", this::biz2Processor);

    }

    public  void biz1Processor(String bizInfo){
        System.out.println("deal with bizType 1, bizInfo:"+bizInfo);
    }

    public void biz2Processor(String bizInfo){
        System.out.println("deal with bizType 2, bizInfo:"+bizInfo);
    }

    public void extensibleProcess(String bizType, String bizInfo){
        Optional.ofNullable(bizProcessors.get(bizType))
                .orElseGet(() -> o -> System.out.println("业务不存在,bizType:"+o))
                .accept(bizInfo);
    }

    public void process(String bizType, String bizInfo){

        if (bizType == "1") {
            System.out.println("deal with bizType 1, bizInfo:"+bizInfo);
        } else if (bizType == "2") {
            System.out.println("deal with bizType 2, bizInfo:"+bizInfo);
        } else {
            System.out.println("业务不存在,bizType:" + bizType);
        }
    }

    public static void main(String[] args) {

        MultiBizProcessor multiBizProcessor = new MultiBizProcessor();
        multiBizProcessor.extensibleProcess("1", "处理器输入参数");
        multiBizProcessor.extensibleProcess("2", "处理器输入参数");
        multiBizProcessor.extensibleProcess("3", "处理器输入参数");


        multiBizProcessor.process("1", "处理器输入参数");
        multiBizProcessor.process("2", "处理器输入参数");
        multiBizProcessor.process("3", "处理器输入参数");

    }

}
