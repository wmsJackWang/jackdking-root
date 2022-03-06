package chain.commands;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName PrintTimestampCommand
 * @Description TODO
 * @Author jackdking
 * @Date 08/12/2021 1:44 下午
 * @Version 2.0
 **/
public class PrintTimestampCommand  implements Command {
    @Override
    public boolean execute(Context context) throws Exception {
        System.out.println(System.currentTimeMillis());
        return false;
    }
}