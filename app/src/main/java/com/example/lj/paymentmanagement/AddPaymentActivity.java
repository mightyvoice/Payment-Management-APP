package com.example.lj.paymentmanagement;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class AddPaymentActivity extends ActionBarActivity {

    private Spinner  payAccountSpinner;
    private EditText paidAccountNameInput;
    private EditText payAmountInput;
    private TextView payDateInput;

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
        setContentView(R.layout.activity_add_payment);

        DisplayMetrics ds = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(ds);

        int width = ds.widthPixels;
        int height = ds.heightPixels;

        getWindow().setLayout((int) (width * 0.6), (int) (height * 0.7));

        payAccountSpinner = (Spinner) findViewById(R.id.payAccountSpinner);
        paidAccountNameInput = (EditText) findViewById(R.id.paidAccountNameInput);
        payAmountInput = (EditText) findViewById(R.id.payAmountInput);
        payDateInput = (TextView) findViewById(R.id.payDateInput);

        initPayAccountSpinner();
        initPayDateInput();
    }

    public void initPayDateInput(){
        Calendar c = Calendar.getInstance();
        payYear = c.get(Calendar.YEAR);
        payMonth = c.get(Calendar.MONTH)+1;
        payDay = c.get(Calendar.DAY_OF_MONTH);
        payDateToString = payYear+"/"+payMonth+"/"+payDay;
        payDateInput.setText(payDateToString);
        payDateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddPaymentActivity.this, payDateSet, payYear, payMonth - 1, payDay);
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

    public void initPayAccountSpinner(){

        payAccountList.clear();
        MyData.myData.updateAllAccountsPaidTimes();
        MyAccount.paidTimesReverseSortFlag = true;
        Collections.sort(MyData.allMyAccounts, MyAccount.paidTimesComparator);
        for(MyAccount account: MyData.allMyAccounts){
            payAccountList.add(account.accountName + " from " + account.bankName);
        }
        payAccountSpinnerAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, payAccountList);
        payAccountSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        payAccountSpinner.setAdapter(payAccountSpinnerAdapter);
        payAccountSpinner.setVisibility(View.VISIBLE);
        payAccountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedAccountName = payAccountList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void addPaymentButtonClicked(View view){

        String amount = payAmountInput.getText().toString();
        try{
            Double.parseDouble(amount);
        }catch (Exception e){
            payAmountInput.setText("0");
        }

        if(Double.parseDouble(payAmountInput.getText().toString()) == 0.0){
            new AlertDialog.Builder(this).setTitle("Amount Error")
                    .setMessage("Pay Amount Cannot Be $0")
                    .setNegativeButton("Return", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        }
        else {
            MyData.newPaymentItem = new MyPaymentItem(
                    MyLib.captureName(selectedAccountName.split(" ")[0]),
                    paidAccountNameInput.getText().toString(),
                    new Double(Double.parseDouble(payAmountInput.getText().toString())),
                    payDateToString
            );

            MyData.myData.addPaymentToDatabase(MyData.newPaymentItem);

            finish();
        }
    }

}
