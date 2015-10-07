package com.example.lj.paymentmanagement;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by lj on 15/9/29.
 */
public class MyAccount {

    public int _id;
    //name
    public String accountName;
    public String bankName;

    //balance
    public Double statementBalance;
    public Double currentBalance;
    public Double toPayBalance;

    //Date
    public Integer dueDay;
    public Date statementDate;

    //
    public ArrayList<MyPaymentItem> paymentList = new ArrayList<MyPaymentItem>();

    public MyAccount(){}

    public MyAccount(String accountName, String bankName, Integer dueDay,
                     Double toPayBalance){
        this.accountName = accountName;
        this.bankName = bankName;
        this.dueDay = dueDay;
        this.statementBalance = 0.0;
        this.currentBalance = 0.0;
        this.toPayBalance = toPayBalance;
    }

    public String toString(){
        return this.accountName + ", " + this.bankName + ", " +
                this.statementBalance.toString() + ", "+
                this.currentBalance.toString() + ", "+
                this.toPayBalance.toString();
    }

}
