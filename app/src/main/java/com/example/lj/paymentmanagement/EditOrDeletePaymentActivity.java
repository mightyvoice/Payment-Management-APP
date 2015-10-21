package com.example.lj.paymentmanagement;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class EditOrDeletePaymentActivity extends ActionBarActivity {
    private Spinner  editPayAccountSpinner;
    private EditText editPaidAccountNameInput;
    private EditText editPayAmountInput;
    private TextView editPayDayInput;

    private Integer payYear;
    private Integer payMonth;
    private Integer payDay;
    private String payDateToString;
    private ArrayList<String> payAccountList = new ArrayList<String>();
    private ArrayAdapter<String> payAccountSpinnerAdapter = null;
    private String selectedAccountName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_or_delete_payment);

        DisplayMetrics ds = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(ds);

        int width = ds.widthPixels;
        int height = ds.heightPixels;

        getWindow().setLayout(
                (int) (width * MyConfigure.NEW_ACTIVITY_WIDTH_RATIO),
                (int) (height * MyConfigure.NEW_ACTIVITY_HEIGHT_RATIO));

        editPayAccountSpinner = (Spinner) findViewById(R.id.editPayAccoutSpinner);
        editPaidAccountNameInput = (EditText) findViewById(R.id.editPaidAccountNameInput);
        editPayAmountInput = (EditText) findViewById(R.id.editPayAmountInput);
        editPayDayInput = (TextView) findViewById(R.id.editPayDayInput);

        editPaidAccountNameInput.setText(MyData.selectedPaymentItem.paidAccountName);
        editPayAmountInput.setText(MyData.selectedPaymentItem.payAmount.toString());

        initPayAccountSpinner();
        initPayDateInput();
    }

    public void initPayDateInput(){
        String[] tmp = MyData.selectedPaymentItem.payDate.split("/");
        payYear = Integer.parseInt(tmp[0]);
        payMonth = Integer.parseInt(tmp[1]);
        payDay = Integer.parseInt(tmp[2]);
        payDateToString = payYear+"/"+payMonth+"/"+payDay;
        editPayDayInput.setText(payDateToString);
        editPayDayInput.setOnClickListener(new View.OnClickListener() {
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
                    editPayDayInput.setText(payDateToString);
                }
            };

    public void initPayAccountSpinner(){
        payAccountList.clear();
        MyAccount.paidTimesReverseSortFlag = true;
        Collections.sort(MyData.allMyAccounts, MyAccount.paidTimesComparator);
        MyAccount.paidTimesReverseSortFlag = false;
        for(MyAccount account: MyData.allMyAccounts){
            payAccountList.add(account.accountName + " from " + account.bankName);
        }
        payAccountSpinnerAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, payAccountList);
        payAccountSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editPayAccountSpinner.setAdapter(payAccountSpinnerAdapter);
        editPayAccountSpinner.setVisibility(View.VISIBLE);
        editPayAccountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedAccountName = payAccountList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

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
        MyData.editPaymentItem.payAccountName = MyLib.captureLongName(MyLib.getRealAccountNameFromInput(selectedAccountName));
        MyData.editPaymentItem.paidAccountName = MyLib.captureLongName(editPaidAccountNameInput.getText().toString());
        MyData.editPaymentItem.payAmount = Double.parseDouble(editPayAmountInput.getText().toString());
        MyData.editPaymentItem.payDate = editPayDayInput.getText().toString();

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
}
