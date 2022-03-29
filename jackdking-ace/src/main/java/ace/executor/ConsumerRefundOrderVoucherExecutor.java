package ace.executor;


import ace.annoation.Executor;
import ace.core.AceContext;
import ace.core.AceResult;

@Executor(name = "ConsumerRefundOrderVoucherExecutor")
public class ConsumerRefundOrderVoucherExecutor implements IExecutor{
    @Override
    public AceResult execute(AceContext aceContext) {

        //根据aceContext中输入参数，填充消费凭证字段

        AceResult consumerRefundOrderVoucher = new AceResult(true,"consumerRefundOrderVoucherExecutor");


        return consumerRefundOrderVoucher;
    }
}
