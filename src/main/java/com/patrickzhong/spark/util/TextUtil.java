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

    public static String format(int i){
        return format(i+"");
    }

    public static String format(String s){
        String r = "";
        for(int j = s.length()-1; j >= 0; j--){
            r = s.charAt(j) + r;
            if((s.length() - j) % 3 == 0)
                r = "," + r;
        }

        if(r.startsWith(","))
            return r.substring(1);

        return r;
    }

}
