package com.example.lj.paymentmanagement;

import java.util.ArrayList;
import java.util.Comparator;
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
    public Double toPayBalance;
    public Double totalPayThisMonth;

    //Date
    public Integer dueDay;
    public Integer staDay;
    public Integer paidTimes;

    public MyAccount(){}

    public MyAccount(String accountName, String bankName, Integer dueDay,
                     Integer staDay,
                     Double statementBalance){
        this.accountName = accountName;
        this.bankName = bankName;
        this.dueDay = dueDay;
        this.staDay = staDay;
        this.statementBalance = statementBalance;
        this.toPayBalance = statementBalance;
        this.totalPayThisMonth = 0.0;
        this.paidTimes = 0;
    }

    public String toString(){
        String tmp = ",   ";
        return this.accountName + tmp +
                this.bankName + tmp +
                MyLib.getDateSuffix(this.dueDay) + tmp + "$" +
                this.statementBalance.toString() + tmp + "$" +
                this.toPayBalance.toString();
    }

    public void updateToPayBalance(){
        if(this.statementBalance > this.totalPayThisMonth){
            this.toPayBalance = this.statementBalance - this.totalPayThisMonth;
        }
        else{
            this.toPayBalance = 0.0;
        }
    }

    public static Boolean accountNameReverseSortFlag = false;
    public static Comparator<MyAccount> accountNameComparator = new
            Comparator<MyAccount>() {
                @Override
                public int compare(MyAccount x, MyAccount y) {
                    if(accountNameReverseSortFlag) {
                        return -x.accountName.compareTo(y.accountName);
                    }
                    else{
                        return x.accountName.compareTo(y.accountName);
                    }
                }
            };

    public static Boolean bankNameReverseSortFlag = false;
    public static Comparator<MyAccount> bankNameComparator = new
            Comparator<MyAccount>() {
                @Override
                public int compare(MyAccount x, MyAccount y) {
                    if(bankNameReverseSortFlag) {
                        return -x.bankName.compareTo(y.bankName);
                    }
                    else{
                        return x.bankName.compareTo(y.bankName);
                    }
                }
            };

    public static Boolean dueDayReverseSortFlag = false;
    public static Comparator<MyAccount> dueDayComparator = new
            Comparator<MyAccount>() {
                @Override
                public int compare(MyAccount x, MyAccount y) {
                    if(dueDayReverseSortFlag) {
                        return -x.dueDay.compareTo(y.dueDay);
                    }
                    else{
                        return x.dueDay.compareTo(y.dueDay);
                    }
                }
            };

    public static Boolean paidTimesReverseSortFlag = false;
    public static Comparator<MyAccount> paidTimesComparator = new
            Comparator<MyAccount>() {
                @Override
                public int compare(MyAccount x, MyAccount y) {
                    if(paidTimesReverseSortFlag) {
                        return -x.paidTimes.compareTo(y.paidTimes);
                    }
                    else{
                        return x.paidTimes.compareTo(y.paidTimes);
                    }
                }
            };

    public static Boolean statementBalanceReverseSortFlag = false;
    public static Comparator<MyAccount> statementBalanceComparator = new
            Comparator<MyAccount>() {
                @Override
                public int compare(MyAccount x, MyAccount y) {
                    if(statementBalanceReverseSortFlag) {
                        return -x.statementBalance.compareTo(y.statementBalance);
                    }
                    else{
                        return x.statementBalance.compareTo(y.statementBalance);
                    }
                }
            };

    public static Boolean toPayBalanceReverseSortFlag = false;
    public static Comparator<MyAccount> toPayBalanceeComparator = new
            Comparator<MyAccount>() {
                @Override
                public int compare(MyAccount x, MyAccount y) {
                    if(toPayBalanceReverseSortFlag) {
                        return -x.toPayBalance.compareTo(y.toPayBalance);
                    }
                    else{
                        return x.toPayBalance.compareTo(y.toPayBalance);
                    }
                }
            };
}
