package com.example.lj.paymentmanagement;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditOrDeleteAccountActivity extends ActionBarActivity {

    public EditText editAccountName;
    public EditText editAccountBank;
    public EditText editAccountDueDay;
    public EditText editAccountStaBalance;
    public Button editAccountButton;
    public Button deleteAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_or_delete_account);

        DisplayMetrics ds = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(ds);

        int width = ds.widthPixels;
        int height = ds.heightPixels;

        getWindow().setLayout(
                (int) (width * MyConfigure.NEW_ACTIVITY_WIDTH_RATIO),
                (int) (height * MyConfigure.NEW_ACTIVITY_HEIGHT_RATIO));

        editAccountName = (EditText) findViewById(R.id.editAccountNameInput);
        editAccountBank = (EditText) findViewById(R.id.editAccountBankInput);
        editAccountDueDay = (EditText) findViewById(R.id.editAccountDueDayInput);
        editAccountStaBalance = (EditText) findViewById(R.id.editAccountStaBalanceInput);

        editAccountName.setText(MyData.selectedAccount.accountName);
        editAccountBank.setText(MyData.selectedAccount.bankName);
        editAccountDueDay.setText(MyData.selectedAccount.dueDay.toString());
        editAccountStaBalance.setText(MyData.selectedAccount.statementBalance.toString());

    }

    public void changeAccountButtonClicked(View view) {

        MyData.editAccount = new MyAccount(editAccountName.getText().toString(),
                                            editAccountBank.getText().toString(),
                                            new Integer(Integer.parseInt(editAccountDueDay.getText().toString())),
                                            new Integer(Integer.parseInt(editAccountDueDay.getText().toString())),
                                            new Double(Double.parseDouble(editAccountStaBalance.getText().toString())));

        new AlertDialog.Builder(this).setTitle("Confirm Change")
                .setMessage("Confirm to change the Account: \n"+
                        MyData.selectedAccount.accountName + " of " +
                        MyData.selectedAccount.bankName+" whose due day is " +
                        MyData.selectedAccount.dueDay.toString() + " and statement balance is " +
                        MyData.selectedAccount.statementBalance.toString() + "\nto " +
                        "New account: \n" +
                        MyData.editAccount.accountName + " of " +
                        MyData.editAccount.bankName+" whose due day is " +
                        MyData.editAccount.dueDay.toString() + " and statement balance is " +
                        MyData.editAccount.statementBalance.toString())
                .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (MyData.selectAccountIndex > -1) {
                            MyData.myData.changeSelectedAccount();
                        }
                        finish();
                    }
                }).setNegativeButton("Return", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).show();
    }

    public void deleteAccountButtonClicked(View view) {
        new AlertDialog.Builder(this).setTitle("Confirm")
                .setMessage("Confirm to delete the account: "+
                MyData.selectedAccount.accountName+" from " +
                MyData.selectedAccount.bankName)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyData.confirmedToDelete = true;
                        if(MyData.confirmedToDelete && MyData.selectAccountIndex > -1){
                            MyData.myData.deleteSelectedAccount();
                        }
                        finish();
                    }
                }).setNegativeButton("Return", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyData.confirmedToDelete = false;
                    }
                }).show();
    }
}
