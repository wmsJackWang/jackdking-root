package org.jackdking.algorithm.bytedance.last;

public class JumpFloor {
    public static void main(String[] args) {

    }
    public int jumpFloor(int target) {
        if(target==1){
            return 1;
        }else if(target==2){
            return 2;
        }else {
            return jumpFloor(target-1)+jumpFloor(target-2);
        }
    }
}
