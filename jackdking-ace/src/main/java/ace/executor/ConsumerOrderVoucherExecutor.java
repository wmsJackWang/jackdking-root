package ace.executor;

import ace.annoation.Executor;
import ace.core.AceContext;
import ace.core.AceResult;

@Executor(name = "ConsumerOrderVoucherExecutor")
public class ConsumerOrderVoucherExecutor implements IExecutor{
    @Override
    public AceResult execute(AceContext aceContext) {

        //根据aceContext中输入参数，填充消费凭证字段

        AceResult consumerOrderVoucher = new AceResult(true,"consumerOrderVoucher");

        return consumerOrderVoucher;
    }
}
