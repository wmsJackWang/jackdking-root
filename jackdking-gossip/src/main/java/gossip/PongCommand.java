package gossip;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName PongConmand
 * @Description TODO
 * @Author jackdking
 * @Date 26/06/2022 7:54 下午
 * @Version 2.0
 **/
public class PongCommand implements Command{

    private String commandType = "Ping";

    @Override
    public String getCommandType() {
        return commandType;
    }
}
