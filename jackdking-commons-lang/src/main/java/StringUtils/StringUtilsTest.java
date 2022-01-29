package StringUtils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Test;

public class StringUtilsTest {

    @Test
    public void testisNumeric() {

        String str = "1010101";

        System.out.println(str+" 是否为数字："+StringUtils.isNumeric(str));


        str = "1010101h";

        System.out.println(str+" 是否为数字："+StringUtils.isNumeric(str));

        //判断字符串数字
        System.out.println(StringUtils.isNumeric("123"));//---false
        System.out.println(StringUtils.isNumeric("12 3"));//---false (不识别非数字字符、运算符号、小数点、空格……)
        System.out.println(StringUtils.isNumericSpace("12 3"));//---true


        //判断是否为空(注：isBlank与isEmpty 区别)
        System.out.println(StringUtils.isBlank(null));//---true
        System.out.println(StringUtils.isBlank(""));//---true
        System.out.println(StringUtils.isBlank(" "));//---true
        System.out.println(StringUtils.isNoneBlank(" ", "bar"));//---false

        System.out.println(StringUtils.isEmpty(null));//---true
        System.out.println(StringUtils.isEmpty(""));//---true
        System.out.println(StringUtils.isEmpty(" "));//---false
        System.out.println(StringUtils.isNoneEmpty(" ", "bar"));//---true


        //随机生成n位数数字
        int n =10;
        System.out.println(RandomStringUtils.randomNumeric(n));
        //在指定字符串中生成长度为n的随机字符串
        System.out.println(RandomStringUtils.random(n, "abcdefghijk"));
        //指定从字符或数字中生成随机字符串
        System.out.println(RandomStringUtils.random(n, true, false));
        System.out.println(RandomStringUtils.random(n, false, true));


        //从数组中选出最大值
        System.out.println(NumberUtils.max(new int[] { 1, 2, 3, 4 }));//---4
        //判断字符串是否全是整数
        System.out.println(NumberUtils.isDigits("153.2"));//--true
        //判断字符串是否全是整数
        System.out.println(NumberUtils.isDigits("153"));//--false
        //判断字符串是否是有效数字
        System.out.println(NumberUtils.isNumber("0321.1"));//---false


    }


}
