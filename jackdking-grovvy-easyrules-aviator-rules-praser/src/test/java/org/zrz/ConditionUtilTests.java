package org.zrz;

import org.junit.Assert;
import org.junit.Test;
import parser.ConditionUtil;

import java.io.IOException;

/**
 * 规则条件助手类单元测试
 *
 * @author zrz
 * @date 2021/5/13 14:31
 */
public class ConditionUtilTests {

    @Test
    public void convertToGroovyOr() throws IOException {
        String jsonStr = "{\"or\":[{\"and\":[{\"gt\":[\"A\",41, 2]},{\"eq\":[\"B\",20 ,1]}]},{\"lt\":[\"C\",30 ,1]},{\"ne\":[\"D\",50 ,1]}]}";
        String expected = "(((args.A >= 10) && (args.B == 20)) || (args.C < 30) || (args.D != 50))";
//        extracted(jsonStr, expected);
        System.out.println(ConditionUtil.convertToGroovy(jsonStr, "args."));
    }

    private void extracted(String jsonStr, String expected) throws IOException {
        Assert.assertEquals(expected, ConditionUtil.convertToGroovy(jsonStr, "args."));
    }

    @Test
    public void convertToGroovyEmptyNode() throws IOException {
        String jsonStr = "{\"and\": [{}, {\"eq\":[\"A\",\"1\", 1]}, {\"notnull\":[\"E\", null, 1]}]}";
        String expected = "( 1==1 && (args.A == \"1\") && (args.E != null))";
        extracted(jsonStr, expected);
    }

    @Test
    public void convertToGroovyAnd() throws IOException {
        String jsonStr = "{\"or\":[{\"lt\":[\"C\",30 ,1]},{\"and\":[{\"ge\":[\"A\",10 ,1]},{\"eq\":[\"B\",20 ,1]}]}]}";
        String expected = "((args.C < 30) || ((args.A >= 10) && (args.B == 20)))";
        extracted(jsonStr, expected);
    }

    @Test
    public void convertToGroovyNotComplex() throws Exception {
        String jsonStr = "{\"not\":{\"or\": [{}, {\"eq\":[\"A\",1 ,1]}]}}";
        String expected = "!( 1==1 || (args.A == 1))";
        extracted(jsonStr, expected);
    }

    @Test
    public void convertToGroovyNotSimple() throws Exception {

        String jsonStr = "{\"not\":{\"eq\":[\"A\",1 ,1]}}";
        String expected = "!(args.A == 1)";
        extracted(jsonStr, expected);
    }

    @Test
    public void generateGroovyFunc() throws IOException {

        String actual = ConditionUtil.generateGroovyFunc("{\"and\":[{\"eq\":[\"店主级别\",\"优秀店主\" ,1]},{\"or\":[{\"ge\":[\"订单金额\",100 ,1]},{\"ge\":[\"累计下单金额\",1000 ,1]}]}]}"
                , "validateCondition", "args");
        String expected = "def validateCondition(args){ return ((args.店主级别 == \"优秀店主\") && ((args.订单金额 >= 100) || (args.累计下单金额 >= 1000)));}";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void extractParams() throws IOException {

        System.out.println(ConditionUtil.extractParams("{\"or\":[{\"and\":[{\"ge\":[\"A\",10 ,2, 1]},{\"eq\":[\"B\",20 ,1, 1]}]},{\"lt\":[\"C\",30 ,1, 1]},{\"ne\":[\"D\",50 ,1, 1]}]}"));

        System.out.println(ConditionUtil.extractParams("{\"or\":[{\"lt\":[\"C\",30 ,1, 1]},{\"and\":[{\"ge\":[\"A\",10 ,1, 1]},{\"eq\":[\"B\",20 ,1, 1]}]}]}"));

        System.out.println(ConditionUtil.extractParams("{\"and\": [{}, {\"eq\":[\"A\",\"1\" ,1, 1]}, {\"notnull\":[\"E\", null ,1, 1]}]}"));

        Assert.assertTrue(true);
    }
}
