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

    public MyPaymentItem(){}

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
                paidAccountName + ",  $" +
                payAmount.toString() + ",  " +
                payDate;
    }

    public static MyPaymentItem getMyPaymentItemFromString(String data){
        String[] l = data.split(",");
        String tmp = MyLib.cutFirstChar(l[2]);
        if(l[2].indexOf("$") > -1){
            l[2] = MyLib.cutFirstChar(l[2]);
        }
        MyPaymentItem ans = new MyPaymentItem(l[0].trim(),
                l[1].trim(),
                Double.parseDouble(l[2].trim()),
                l[3].trim());
        return ans;
    }
}
