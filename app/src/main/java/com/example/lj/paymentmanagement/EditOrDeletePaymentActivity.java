package com.example.lj.paymentmanagement;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditOrDeletePaymentActivity extends ActionBarActivity {

    private EditText paidAccountNameInput;
    private EditText payAccountNameInput;
    private EditText payAmountInput;
    private EditText payDateInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_or_delete_payment);

        DisplayMetrics ds = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(ds);

        int width = ds.widthPixels;
        int height = ds.heightPixels;

        getWindow().setLayout((int) (width * 0.6), (int) (height * 0.7));

        paidAccountNameInput = (EditText) findViewById(R.id.paidAccountNameInput);
        payAccountNameInput = (EditText) findViewById(R.id.payAccountNameInput);
        payAmountInput = (EditText) findViewById(R.id.payAmountInput);
        payDateInput = (EditText) findViewById(R.id.payDateInput);

        payAccountNameInput.setText(MyData.selectedPaymentItem.payAccountName);
        paidAccountNameInput.setText(MyData.selectedPaymentItem.paidAccountName);
        payAmountInput.setText(MyData.selectedPaymentItem.payAmount.toString());
        payDateInput.setText(MyData.selectedPaymentItem.payDate);
    }

    public void deletePaymentButtonClicked(View view){
        Toast.makeText(this, "delete button clicked", Toast.LENGTH_LONG).show();
        new AlertDialog.Builder(this).setTitle("Confirm Delete")
                .setMessage("Confirm to delete the payment to: "+
                        MyData.selectedPaymentItem.payAccountName+" on " +
                        MyData.selectedPaymentItem.payDate + " of $" +
                        MyData.selectedPaymentItem.payAmount.toString())
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyData.confirmedToDelete = true;
                        if(MyData.confirmedToDelete && MyData.selectPaymentItemIndex > -1){
                            MyData.myData.deleteSelectedPaymentItem();
                        }
                        finish();
                    }
                }).setNegativeButton("Return", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyData.confirmedToDelete = false;
            }
        }).show();
    }

    public void changePaymentButtonClicked(View view){
        paidAccountNameInput = (EditText) findViewById(R.id.paidAccountNameInput);
        payAccountNameInput = (EditText) findViewById(R.id.payAccountNameInput);
        payAmountInput = (EditText) findViewById(R.id.payAmountInput);
        payDateInput = (EditText) findViewById(R.id.payDateInput);

        MyData.editPaymentItem = new MyPaymentItem();
        MyData.editPaymentItem.payAccountName = payAccountNameInput.getText().toString();
        MyData.editPaymentItem.paidAccountName = paidAccountNameInput.getText().toString();
        MyData.editPaymentItem.payAmount = Double.parseDouble(payAmountInput.getText().toString());
        MyData.editPaymentItem.payDate = payDateInput.getText().toString();

        new AlertDialog.Builder(this).setTitle("Confirm Change")
                .setMessage("Confirm to change the payment : $"+
                        MyData.selectedPaymentItem.payAmount.toString() + " to " +
                        MyData.selectedPaymentItem.payAccountName+" from " +
                        MyData.selectedPaymentItem.paidAccountName + " on " +
                        MyData.selectedPaymentItem.payDate + " to\n " +
                        "New payment: $" + MyData.editPaymentItem.payAmount.toString() + " to "+
                        MyData.editPaymentItem.payAccountName + " from " +
                        MyData.editPaymentItem.paidAccountName + " on " +
                        MyData.editPaymentItem.payDate)
                .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(MyData.selectPaymentItemIndex > -1){
                            MyData.myData.changeSelectedPaymentItem();
                        }
                        finish();
                    }
                }).setNegativeButton("Return", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_or_delete_payment, menu);
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
