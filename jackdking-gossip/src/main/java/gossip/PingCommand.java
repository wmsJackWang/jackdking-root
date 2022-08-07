package gossip;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName PingComand
 * @Description TODO
 * @Author jackdking
 * @Date 26/06/2022 7:53 下午
 * @Version 2.0
 **/
public class PingCommand implements Command{

    private String commandType = "Ping";

    @Override
    public String getCommandType() {
        return commandType;
    }
}
