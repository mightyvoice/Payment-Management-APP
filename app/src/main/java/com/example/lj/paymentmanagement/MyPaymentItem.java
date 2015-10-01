package com.example.lj.paymentmanagement;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by lj on 15/9/25.
 */
public class MyPaymentItem {
    public String payAccountName;
    public String paidAccountName;
    public Date payDate = new Date(Calendar.YEAR, Calendar.MONTH, Calendar.DATE);

    public Double payAmount;
    public boolean ifArrived;
    public Double totalPayAmountThisMonth;

    public MyPaymentItem(String payAccountName, String paidAccountName,
                         Double payAmount){
        this.payAccountName = payAccountName;
        this.paidAccountName = paidAccountName;
        this.payAmount = payAmount;
        this.totalPayAmountThisMonth = 0.0;
    }

    public String toString(){
        return payAccountName + ",  " +
                paidAccountName + ",  " +
                payAmount.toString() + ",  "+
                totalPayAmountThisMonth.toString();

    }
}
