package com.example.lj.paymentmanagement;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddAccountActivity extends ActionBarActivity {

    public EditText newAccountNameInput;
    public EditText newAccountBankInput;
    public EditText newAccountDueDayInput;
    public EditText newAccountStaBalanceInput;
    public Button addNewAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        DisplayMetrics ds = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(ds);

        int width = ds.widthPixels;
        int height = ds.heightPixels;

        getWindow().setLayout((int) (width * 0.6), (int) (height * 0.7));
    }

    public void addNewAccountButtonClicked(View view){
        newAccountNameInput = (EditText)findViewById(R.id.payAccountNameInput);
        newAccountBankInput = (EditText)findViewById(R.id.paidAccountNameInput);
        newAccountDueDayInput = (EditText)findViewById(R.id.editAccountDueDayInput);
        newAccountStaBalanceInput = (EditText)findViewById(R.id.editAccountStaBalanceInput);

        //if account already exist
        if(MyData.ifAccountAlreadyExist(
                MyLib.captureName(newAccountNameInput.getText().toString()))){
            new AlertDialog.Builder(this).setTitle("Amount repeat error")
                    .setMessage("The account already exists")
                    .setNegativeButton("Return", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        }
        else {
            Integer dueDay;
            try {
                dueDay = new Integer(Integer.parseInt(newAccountDueDayInput.getText().toString()));
            }catch (Exception e){
                dueDay = 0;
            }
            Double staBalance;
            try {
                staBalance = new Double(Double.parseDouble(newAccountStaBalanceInput.getText().toString()));
            }catch (Exception e){
                staBalance = 0.0;
            }
            MyData.newAccount = new MyAccount(
                    MyLib.captureName(newAccountNameInput.getText().toString()),
                    newAccountBankInput.getText().toString(),
                    dueDay,
                    staBalance);
            MyData.myData.addAccountToDatabase(MyData.newAccount);
            finish();
        }
    }

}
