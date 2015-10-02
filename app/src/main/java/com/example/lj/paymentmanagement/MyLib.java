package com.example.lj.paymentmanagement;

/**
 * Created by lj on 15/10/2.
 */
public class MyLib {
    public static Integer stringToInteger(String x){
        int ans = 0;
        for(int i = 0; i < x.length(); i++){
            if(x.charAt(i) <= '9' && x.charAt(i) >= '0'){
                ans = ans * 10 + x.charAt(i)-'0';
            }
        }
        return new Integer(ans);
    }
}
