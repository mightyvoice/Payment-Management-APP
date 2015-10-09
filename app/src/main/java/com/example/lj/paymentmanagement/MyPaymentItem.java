package com.example.lj.paymentmanagement;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by lj on 15/9/25.
 */
public class MyPaymentItem {

    public String payAccountName;
    public String paidAccountName;
    public String payDate;
    public Double payAmount;
    public boolean ifArrived = false;
    public Double totalPayAmountThisMonth;

    public MyPaymentItem(String payAccountName, String paidAccountName,
                         Double payAmount, String payDate){
        this.payAccountName = payAccountName;
        this.paidAccountName = paidAccountName;
        this.payAmount = payAmount;
        this.totalPayAmountThisMonth = 0.0;
        this.payDate = payDate;
    }

    public String toString(){
        return payAccountName + ",  " +
                paidAccountName + ",  " +
                payAmount.toString() + ",  "+
                totalPayAmountThisMonth.toString();

    }

    public static MyPaymentItem getMyPaymentItemFromString(String data){
        String[] l = data.split(",");
        String tmp = MyLib.cutFirstChar(l[2]);
        if(l[2].indexOf("$") > -1){
            l[2] = MyLib.cutFirstChar(l[2]);
        }
        MyPaymentItem ans = new MyPaymentItem(l[0].replaceAll(" ", ""),
                l[1].replaceAll(" ", ""),
                Double.parseDouble(l[2]),
                l[3].replaceAll(" ", ""));
        return ans;
    }
}
