package com.patrickzhong.spark.util;

/**
 * Created by patrickzhong on 6/26/18.
 */
public class TextUtil {

    public static String combine(String[] args, int start, int end){
        return combine(args, start, end, " ");
    }

    public static String combine(String[] args, int start, int end, String sep){
        String s = "";
        if(end > args.length || end < 0) end = args.length;

        for(int i = start; i < end; i++)
            s += (s.equals("") ? "" : sep) + args[i];
        return CC.translate(s);
    }

}
