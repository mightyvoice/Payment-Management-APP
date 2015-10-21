package com.example.lj.paymentmanagement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by lj on 15/10/2.
 */
public class MyLib {

    public static Integer stringToInteger(String x){
        int ans = 0;
        try{
            ans = Integer.parseInt(x);
        }
        finally {
            return new Integer(ans);
        }
    }

    public static String getCurrentDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime()); //2014/08/06 16:00:22
    }

    public static Double roundTo2DecimalPoints(Double x){
        Double ans = Math.round(x.doubleValue()*100)/100.00;
        return ans;
    }

    public static String cutFirstChar(String x){
        if(x == "") return "";
        x = x.replace(" ", "");
        return x.substring(1);
    }

    public static String getDateSuffix(Integer x){
        String ans = x.toString();
        if(x == 1){
            ans += "st";
        }
        else if(x == 2){
            ans += "nd";
        }
        else if(x == 3){
            ans += "rd";
        }
        else{
            ans += "th";
        }
        return ans;
    }

    public static String captureName(String name) {
        char[] cs=name.toCharArray();
        if(cs[0] >= 'a' && cs[0] <= 'z'){
            cs[0]-=32;
        }
        return String.valueOf(cs);

    }

    public static String captureLongName(String name){
        String[] l = name.split(" ");
        String ans = "";
        for(int i = 0; i < l.length; i++){
            if(i > 0){
                ans += " ";
            }
            ans += captureName(l[i]);
        }
        return ans;
    }

    public static String getRealAccountNameFromInput(String name){
        String[] l = name.split(" ");
        String ans = "";
        for(int i = 0; i < l.length; i++){
            if(l[i].equals("from")){
                break;
            }
            else{
                if(i > 0){
                    ans += " ";
                }
                ans += captureName(l[i]);
            }
        }
        return ans;
    }
}
