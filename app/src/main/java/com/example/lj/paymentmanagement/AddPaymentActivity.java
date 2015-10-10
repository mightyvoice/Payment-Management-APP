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
import android.widget.EditText;

public class AddPaymentActivity extends ActionBarActivity {

    private EditText paidAccountNameInput;
    private EditText payAccountNameInput;
    private EditText payAmountInput;
    private EditText payDateInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);

        DisplayMetrics ds = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(ds);

        int width = ds.widthPixels;
        int height = ds.heightPixels;

        getWindow().setLayout((int) (width * 0.6), (int) (height * 0.7));

        payAccountNameInput = (EditText)findViewById(R.id.payAccountNameInput);
        paidAccountNameInput = (EditText)findViewById(R.id.paidAccountNameInput);
        payAmountInput = (EditText)findViewById(R.id.payAmountInput);
        payDateInput = (EditText)findViewById(R.id.payDateInput);

        payDateInput.setText(MyLib.getCurrentDate());
    }

    public void addNewPaymentButtonClicked(View view){

//        payAccountNameInput.setText("Freedom");
//        paidAccountNameInput.setText("BoA");
//        payAmountInput.setText("20");
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

        String allInput = payAccountNameInput.getText().toString() + ",    " +
                paidAccountNameInput.getText().toString() + ",    " +
                payAmountInput.getText().toString() + ",    "+
                payDateInput.getText().toString();
        Intent data = new Intent();
        data.setData(Uri.parse(allInput));
        setResult(RESULT_OK, data);

        //close the activity
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
