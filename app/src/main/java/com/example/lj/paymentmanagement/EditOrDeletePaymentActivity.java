package com.example.lj.paymentmanagement;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class EditOrDeletePaymentActivity extends ActionBarActivity {

    private EditText paidAccountNameInput;
    private EditText payAccountNameInput;
    private EditText payAmountInput;
    private TextView payDateInput;

    private Integer payYear;
    private Integer payMonth;
    private Integer payDay;
    private String payDateToString;

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
        payDateInput = (TextView) findViewById(R.id.editPayDayInput);

        payAccountNameInput.setText(MyData.selectedPaymentItem.payAccountName);
        paidAccountNameInput.setText(MyData.selectedPaymentItem.paidAccountName);
        payAmountInput.setText(MyData.selectedPaymentItem.payAmount.toString());

        initPayDateInput();
    }

    public void initPayDateInput(){
        String[] tmp = MyData.selectedPaymentItem.payDate.split("/");
        payYear = Integer.parseInt(tmp[0]);
        payMonth = Integer.parseInt(tmp[1]);
        payDay = Integer.parseInt(tmp[2]);
        payDateToString = payYear+"/"+payMonth+"/"+payDay;
        payDateInput.setText(payDateToString);
        payDateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        EditOrDeletePaymentActivity.this, payDateSet, payYear, payMonth - 1, payDay);
                datePickerDialog.show();
            }
        });
    }

    DatePickerDialog.OnDateSetListener payDateSet =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    payYear = year;
                    payMonth = monthOfYear+1;
                    payDay = dayOfMonth;
                    payDateToString = payYear+"/"+payMonth+"/"+payDay;
                    payDateInput.setText(payDateToString);
                }
            };

    public void deletePaymentButtonClicked(View view){
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
