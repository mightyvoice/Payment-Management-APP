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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class AddPaymentActivity extends ActionBarActivity {

    private EditText paidAccountNameInput;
    private EditText payAccountNameInput;
    private EditText payAmountInput;
    private EditText payDateInput;
    private Spinner  payAccountSpinner;

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
        payDateInput = (EditText) findViewById(R.id.payDateInput);

        payDateInput.setText(MyLib.getCurrentDate());

        initPayAccountSpinner();
    }

    public void initPayAccountSpinner(){
        payAccountSpinnerAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, payAccountList);
        payAccountSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        payAccountList.clear();
        for(MyAccount account: MyData.allMyAccounts){
            payAccountList.add(account.accountName);
        }
        payAccountSpinner.setAdapter(payAccountSpinnerAdapter);
        payAccountSpinner.setVisibility(View.VISIBLE);
        payAccountSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedAccountName = payAccountList.get(position);
            }
        });
    }

    public void addNewPaymentButtonClicked(View view){

        String amount = payAmountInput.getText().toString();
        try{
            Double.parseDouble(amount);
        }catch (Exception e){
            payAmountInput.setText("0");
        }

        //the the pay account does not exist do and payment
        if(!MyData.myData.ifAccountNameAlreadyExist(
                payAccountNameInput.getText().toString())){
            new AlertDialog.Builder(this).setTitle("Error")
                    .setMessage("The pay account does not exist")
                    .setNegativeButton("Return", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            }).show();
            return;
        }

        MyData.newPaymentItem = new MyPaymentItem(
                selectedAccountName,
                paidAccountNameInput.getText().toString(),
                new Double(Double.parseDouble(payAmountInput.getText().toString())),
                payDateInput.getText().toString()
        );

        MyData.myData.addPaymentToDatabase(MyData.newPaymentItem);

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_payment, menu);
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
