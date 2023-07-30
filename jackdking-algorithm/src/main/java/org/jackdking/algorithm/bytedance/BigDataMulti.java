package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;

public class BigDataMulti extends Sort {
    public static void main(String[] args) {
        String input1 = "733064366";
        String input2 = "459309139";

        String res = MultiTwoBigData(input1, input2);
        System.out.println("大数相乘结果：" + res);

        res = MultiTwoBigData20230710(input1, input2);
        System.out.println("大数相乘结果：" + res);

    }

  private static String MultiTwoBigData20230710(String input1, String input2) {
      // 反者道之动， 弱者道之用
      int[] newValArray = new int[input1.length() + input2.length()];
      int[] arr1 = new int[input1.length()];
      int[] arr2 = new int[input2.length()];
      int index = 0, up = 0, val = 0;
      for (char ch : input1.toCharArray()) {
        arr1[index++] = ch-'0';
      }
      index = 0;
      for (char ch: input2.toCharArray()) {
        arr2[index++] = ch-'0';
      }
//      for (int i = newValArray.length-1; i >0; i--) {
//        val = arr1[i] * arr2[i] + up;
//        newValArray[i] = val%10;
//        up = val/10;
//      }
      for(int i = arr1.length -1; i >=0; i --) {
        for (int j = arr2.length -1; j >=0; j--) {
          newValArray[i+j+1] += arr1[i] * arr2[j];
        }
      }

      for (int i = newValArray.length -1; i > 0; i --) {
        val = newValArray[i] +up;
        newValArray[i] = val%10;
        up = val/10;
      }

      if (up!=0){
        newValArray[0] = up;
      }
      boolean isOk = true;
      StringBuilder sb = new StringBuilder();

      for (int ch : newValArray) {
        if (isOk&&ch==0) {
          continue;
        } else {
          sb.append(ch);
          isOk = false;
        }
      }

      return sb.toString();
  }




  private static String MultiTwoBigData(String input1, String input2) {

        int[] arr1 = new int[input1.length()];
        int[] arr2 = new int[input2.length()];


        int[] res = new int[input1.length() + input2.length()];

        int carry = 0;
        for (int i = 0 ; i < arr1.length ; i++) {
            arr1[i] = input1.charAt(i) - '0';
        }

        for (int i = 0 ; i < arr2.length ; i++) {
            arr2[i] = input2.charAt(i) - '0';
        }

        for (int i = input1.length() - 1 ; i >= 0 ; i--) {
            for (int j = input2.length() - 1 ; j >= 0 ; j--) {
                res[i + j + 1] += arr1[i] * arr2[j];
            }
        }

        for (int i = res.length - 1 ; i >= 0 ; i--) {
            //问题：先求进位值，后求位值
//            res[i] = (res[i] + carry)%10;
//            carry = (res[i] + carry)/10;
            res[i] += carry;
            carry = res[i] / 10;
            res[i] = res[i] % 10;
        }

        StringBuilder sb = new StringBuilder();
        boolean isZero = true;
        for (int i = 0 ; i < res.length ; i++) {
            if (res[i] != 0) {
                isZero = false;
            }
            if (!isZero) {
                sb.append(res[i]);
            }
        }
        return sb.length() == 0 ? "0" : sb.toString();
    }
}
