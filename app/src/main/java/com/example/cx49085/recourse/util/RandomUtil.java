package com.example.cx49085.recourse.util;

import java.util.Random;

/**
 * Created by ChenJY on 2018/5/13.
 */

public class RandomUtil {
    /**
     * 生成指定位数的随机数
     * @param length
     * @return
     */
    public static int getRandom(int length){
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            val += String.valueOf(random.nextInt(10));
        }
        return Integer.valueOf(val);
    }
    public static void main(String[] args) {
        System.out.println(getRandom(6));
    }
}
