package ArrayUtils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.Map;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName ArrayCopyTest
 * @Description TODO
 * @Author jackdking
 * @Date 23/11/2021 4:31 下午
 * @Version 2.0
 **/
@Slf4j
public class ArrayUtilsTest {

    @Test
    public  void testAddAll() {

        int [] origArray = {1,2,3};
        int [] secondArray = {5,6};

        int [] newArray = ArrayUtils.addAll(origArray, secondArray);
        log.info("newArray:{}", ArrayUtils.toString(newArray));
    }

    @Test
    public void testReserve() {

        int [] origArray = {1,2,3,5,6};

        log.info("origArray:{}", ArrayUtils.toString(origArray));
        ArrayUtils.reverse(origArray);
        log.info("reserveArray:{}", ArrayUtils.toString(origArray));
    }

    /*
     * byte 2 Byte
     * short 2 Short
     * int  2 Integer
     * boolean Boolean
     * float  Float
     * char Character
     * double Double
     * long Long
     *
     */
    @Test
    public void testToObject() {

        int [] origArray = {1,2,3,5,6};

        log.info("origArray:{}", ArrayUtils.toString(origArray));
        Integer [] objectArray = ArrayUtils.toObject(origArray);
        log.info("objectArray:{}", ArrayUtils.toString(objectArray));
    }

    /*
     * byte 2 Byte
     * short 2 Short
     * int  2 Integer
     * boolean Boolean
     * float  Float
     * char Character
     * double Double
     * long Long
     *
     */
    @Test
    public void testToPritive() {

        Integer [] origArray = {1,2,3,5,6,null};

        log.info("origArray:{}", ArrayUtils.toString(origArray));
        int [] objectArray = ArrayUtils.toPrimitive(origArray, 0);
        log.info("objectArray:{}", ArrayUtils.toString(objectArray));
    }

    @Test
    public void testToStringArray() {

        Integer [] origArray = {1,2,3,5,6,null};

        log.info("origArray:{}", ArrayUtils.toString(origArray));
        String [] stringArray = ArrayUtils.toStringArray(origArray, "0");
        log.info("stringArray:{}", ArrayUtils.toString(stringArray));
    }

    @Test
    public void testToMap() {

        Integer [][] origArray = {{1, 29}, {2, 90}};

        log.info("origArray:{}", ArrayUtils.toString(origArray));
        Map map = ArrayUtils.toMap(origArray);
        log.info("stringArray:{}", ArrayUtils.toString(map));
    }
    //byte short int long float double boolean char
    @Test
    public void testClone() {

        int [] origArray = {1,2,3,5,6};

        log.info("origArray:{}", ArrayUtils.toString(origArray));
        int[] newArray = ArrayUtils.clone(origArray);
        log.info("after clone");
        newArray[0] = 100;
        log.info("origArray:{}", ArrayUtils.toString(origArray));
        log.info("newArray:{}", ArrayUtils.toString(newArray));
    }

}
