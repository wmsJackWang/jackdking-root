package chain.commands;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

import java.util.Date;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName PrintDateCommand
 * @Description TODO
 * @Author jackdking
 * @Date 08/12/2021 1:43 下午
 * @Version 2.0
 **/
public class PrintDateCommand implements Command {
    @Override
    public boolean execute(Context context) throws Exception {
        System.out.println(new Date());
        return true;
    }
}
