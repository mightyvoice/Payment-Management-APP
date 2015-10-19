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
    public Double totalPayThisMonth;
    //Date
    public Integer dueDay;
    public Date statementDate;

    //
    public ArrayList<MyPaymentItem> paymentList = new ArrayList<MyPaymentItem>();

    public MyAccount(){}

    public MyAccount(String accountName, String bankName, Integer dueDay,
                     Double statementBalance){
        this.accountName = accountName;
        this.bankName = bankName;
        this.dueDay = dueDay;
        this.statementBalance = statementBalance;
        this.toPayBalance = statementBalance;
        this.totalPayThisMonth = 0.0;
    }

    public String toString(){
        String tmp = ",   ";
        return this.accountName + tmp +
                this.bankName + tmp +
                MyLib.getDateSuffix(this.dueDay) + tmp + "$" +
                this.toPayBalance.toString() + tmp + "$" +
                this.statementBalance.toString();
    }

    public static MyAccount getMyAccountFromString(String data){
        String[] l = data.split(",");
        if(l[3].indexOf("$") > -1){
            l[3] = MyLib.cutFirstChar(l[3]);
        }
        Double stateBalance = new Double(Double.parseDouble(l[3].trim()));
        double tmp = Double.parseDouble(l[3].trim());
        MyAccount ans = new MyAccount(l[0].trim(),
                l[1].trim(),
                MyLib.stringToInteger(l[2].trim()),
                stateBalance);
        return ans;
    }

    public void updateToPayBalance(){
        if(this.statementBalance > this.totalPayThisMonth){
            this.toPayBalance = this.statementBalance - this.totalPayThisMonth;
        }
        else{
            this.toPayBalance = 0.0;
        }
    }
}
