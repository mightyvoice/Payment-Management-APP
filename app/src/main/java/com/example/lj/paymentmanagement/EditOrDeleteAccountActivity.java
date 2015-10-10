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
import android.widget.Toast;

public class EditOrDeleteAccountActivity extends ActionBarActivity {

    public EditText editAccountName;
    public EditText editAccountBank;
    public EditText editAccountDueDay;
    public EditText editAccountToPay;
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

        getWindow().setLayout((int) (width * 0.6), (int) (height * 0.7));

        editAccountName = (EditText) findViewById(R.id.editAccountNameInput);
        editAccountBank = (EditText) findViewById(R.id.editAccountBankInput);
        editAccountDueDay = (EditText) findViewById(R.id.editAccountDueDayInput);
        editAccountToPay = (EditText) findViewById(R.id.editAccountToPayInput);

        editAccountName.setText(MyData.selectedAccount.accountName);
        editAccountBank.setText(MyData.selectedAccount.bankName);
        editAccountDueDay.setText(MyData.selectedAccount.dueDay.toString());
        editAccountToPay.setText(MyData.myData.getAccountStatementBalance(MyData.selectedAccount.accountName).toString());

//        editAccountName.setEnabled(false);

    }

    public void changeAccountButtonClicked(View view) {

        MyData.editAccount = new MyAccount();
        MyData.editAccount.accountName = editAccountName.getText().toString();
        MyData.editAccount.bankName = editAccountBank.getText().toString();
        MyData.editAccount.dueDay = Integer.parseInt(
                editAccountDueDay.getText().toString());
        MyData.editAccount.toPayBalance = Double.parseDouble(
                editAccountToPay.getText().toString());

        new AlertDialog.Builder(this).setTitle("Confirm Change")
                .setMessage("Confirm to change the Account: \n"+
                        MyData.selectedAccount.accountName + " of " +
                        MyData.selectedAccount.bankName+" whose due day is " +
                        MyData.selectedAccount.dueDay.toString() + " and to pay balance is " +
                        MyData.selectedAccount.toPayBalance.toString() + "\nto " +
                        "New account: \n" +
                        MyData.editAccount.accountName + " of " +
                        MyData.editAccount.bankName+" whose due day is " +
                        MyData.editAccount.dueDay.toString() + " and to pay balance is " +
                        MyData.editAccount.toPayBalance.toString())
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
        Toast.makeText(this, "delete button clicked", Toast.LENGTH_LONG).show();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_or_delete_account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
