package com.example.lj.paymentmanagement;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;

public class AddAccountActivity extends ActionBarActivity{

    public EditText addAccountNameInput;
    public EditText addAccountBankInput;
    public EditText addAccountDueDayInput;
    public EditText addAccountStaBalanceInput;
    public EditText addAccountStaDayInput;
//    public Button addNewAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        resizeDisplay();

    }

    public void resizeDisplay(){
        DisplayMetrics ds = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(ds);

        int width = ds.widthPixels;
        int height = ds.heightPixels;

        getWindow().setLayout(
                (int) (width * MyConfigure.NEW_ACTIVITY_WIDTH_RATIO),
                (int) (height * MyConfigure.NEW_ACTIVITY_HEIGHT_RATIO));
    }

    public void addNewAccountButtonClicked(View view){
        addAccountNameInput = (EditText)findViewById(R.id.addPayAccountNameInput);
        addAccountBankInput = (EditText)findViewById(R.id.addPaidAccountNameInput);
        addAccountDueDayInput = (EditText)findViewById(R.id.addAccountDueDayInput);
        addAccountStaDayInput = (EditText)findViewById(R.id.addAccountStaDayInput);
        addAccountStaBalanceInput = (EditText)findViewById(R.id.addAccountStaBalanceInput);

        String accountName;
        int staDay;
        int dueDay;
        double staBalance;
        try {
            accountName = MyLib.captureName(addAccountNameInput.getText().toString());
        }catch (Exception e){
            accountName = "";
        }
        try{
            staDay = Integer.parseInt(addAccountStaDayInput.getText().toString());
        }catch (Exception e){
            staDay = 0;
        }
        try{
            dueDay = Integer.parseInt(addAccountDueDayInput.getText().toString());
        }catch (Exception e){
            dueDay = 0;
        }
        try{
            staBalance = Double.parseDouble(addAccountStaBalanceInput.getText().toString());
        }catch (Exception e){
            staBalance = -1.0;
        }

        if(MyData.ifAccountAlreadyExist(accountName)){
            new AlertDialog.Builder(this).setTitle("Amount Name error")
                    .setMessage("The account already exists")
                    .setNegativeButton("Return", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        }
        else if(dueDay < 1 || dueDay > 31){
                new AlertDialog.Builder(this).setTitle("Due Day error")
                        .setMessage("The input due day is out of scope")
                        .setNegativeButton("Return", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
        }
        else if(staDay < 1 || staDay > 31){
            new AlertDialog.Builder(this).setTitle("Statement Day Error")
                    .setMessage("The input statement day is out of scope")
                    .setNegativeButton("Return", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        }
        else if(staBalance < 0.0){
            new AlertDialog.Builder(this).setTitle("Statement Balance Error")
                    .setMessage("The input statement balance is out of scope")
                    .setNegativeButton("Return", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        }
        else {
            MyData.newAccount = new MyAccount(
                    MyLib.captureLongName(addAccountNameInput.getText().toString()),
                    MyLib.captureLongName(addAccountBankInput.getText().toString()),
                    dueDay,
                    staDay,
                    staBalance
            );
            MyData.myData.addAccountToDatabase(MyData.newAccount);
            finish();
        }
    }
}
