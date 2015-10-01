package com.example.lj.paymentmanagement;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by lj on 15/9/29.
 */
public class MyAccount {

    //name
    public String accountName;
    public String bankName;

    //balance
    public Double statementBalance;
    public Double currentBalance;
    public Double needToPayBalance;

    //Date
    public Date payDueDate;
    public Date statementDate;

    //
    public ArrayList<MyPaymentItem> paymentList = new ArrayList<MyPaymentItem>();

    public MyAccount(String accountName, String bankName){
        this.accountName = accountName;
        this.bankName = bankName;
        this.statementBalance = 0.0;
        this.currentBalance = 0.0;
        this.needToPayBalance = 0.0;
    }

    public String toString(){
        return this.accountName + ", " + this.bankName + ", " +
                this.statementBalance.toString() + ", "+
                this.currentBalance.toString() + ", "+
                this.needToPayBalance.toString();
    }

}
