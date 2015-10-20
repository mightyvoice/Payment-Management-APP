package com.example.lj.paymentmanagement;

import java.util.Calendar;
import java.util.Comparator;
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

    public static Boolean payAccountNameReverseSortFlag = false;
    public static Comparator<MyPaymentItem> payAccountNameComparator = new
            Comparator<MyPaymentItem>() {
                @Override
                public int compare(MyPaymentItem x, MyPaymentItem y) {
                    if(payAccountNameReverseSortFlag) {
                        return -x.payAccountName.compareTo(y.payAccountName);
                    }
                    else{
                        return x.payAccountName.compareTo(y.payAccountName);
                    }
                }
            };

    public static Boolean paidAccountNameReverseSortFlag = false;
    public static Comparator<MyPaymentItem> paidAccountNameComparator = new
            Comparator<MyPaymentItem>() {
                @Override
                public int compare(MyPaymentItem x, MyPaymentItem y) {
                    if(paidAccountNameReverseSortFlag) {
                        return -x.paidAccountName.compareTo(y.paidAccountName);
                    }
                    else{
                        return x.paidAccountName.compareTo(y.paidAccountName);
                    }
                }
            };

    public static Boolean payAmountReverseSortFlag = false;
    public static Comparator<MyPaymentItem> payAmountComparator = new
            Comparator<MyPaymentItem>() {
                @Override
                public int compare(MyPaymentItem x, MyPaymentItem y) {
                    if(payAmountReverseSortFlag) {
                        return -x.payAmount.compareTo(y.payAmount);
                    }
                    else{
                        return x.payAmount.compareTo(y.payAmount);
                    }
                }
            };

    public static Boolean payDateReverseSortFlag = false;
    public static Comparator<MyPaymentItem> payDateComparator = new
            Comparator<MyPaymentItem>() {
                @Override
                public int compare(MyPaymentItem x, MyPaymentItem y) {
                    if(payDateReverseSortFlag) {
                        return -x.payDate.compareTo(y.payDate);
                    }
                    else{
                        return x.payDate.compareTo(y.payDate);
                    }
                }
            };
}
