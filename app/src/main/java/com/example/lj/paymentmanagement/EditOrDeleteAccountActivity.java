package com.example.lj.paymentmanagement;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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

    public EditText newAccountNameInput;
    public EditText newAccountBankInput;
    public EditText newAccountDueDayInput;
    public EditText newAccountToPayInput;
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

        newAccountNameInput = (EditText) findViewById(R.id.payAccountNameInput);
        newAccountBankInput = (EditText) findViewById(R.id.paidAccountNameInput);
        newAccountDueDayInput = (EditText) findViewById(R.id.newAccountDueDayInput);
        newAccountToPayInput = (EditText) findViewById(R.id.toPayInput);

        newAccountNameInput.setText(MyData.selectedAccount.accountName);
        newAccountBankInput.setText(MyData.selectedAccount.bankName);
        newAccountDueDayInput.setText(MyData.selectedAccount.dueDay.toString());
        newAccountToPayInput.setText(MyData.selectedAccount.toPayBalance.toString());

    }

    public void editAccountButtonClicked(View view) {
//        newAccountNameInput = (EditText) view.findViewById(R.id.payAccountNameInput);
//        newAccountBankInput = (EditText) view.findViewById(R.id.paidAccountNameInput);
//        newAccountDueDayInput = (EditText) view.findViewById(R.id.newAccountDueDayInput);
//        newAccountToPayInput = (EditText) view.findViewById(R.id.toPayInput);
//
////        newAccountNameInput.setText("Freedom");
////        newAccountBankInput.setText("BoA");
//
//        String allInput = newAccountNameInput.getText().toString() + ",    " +
//                newAccountBankInput.getText().toString() + ",   " +
//                newAccountDueDayInput.getText().toString() + ",   " +
//                newAccountToPayInput.getText().toString();
//        Intent data = new Intent();
//        data.setData(Uri.parse(allInput));
//        setResult(RESULT_OK, data);
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
