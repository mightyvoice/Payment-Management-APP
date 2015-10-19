package com.example.lj.paymentmanagement;

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
    public EditText newAccountToPayInput;
    public Button addNewAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        DisplayMetrics ds = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(ds);

        int width = ds.widthPixels;
        int height = ds.heightPixels;

        getWindow().setLayout((int)(width*0.6), (int)(height*0.7));
    }

    public void addNewAccountButtonClicked(View view){
        newAccountNameInput = (EditText)findViewById(R.id.payAccountNameInput);
        newAccountBankInput = (EditText)findViewById(R.id.paidAccountNameInput);
        newAccountDueDayInput = (EditText)findViewById(R.id.editAccountDueDayInput);
        newAccountToPayInput = (EditText)findViewById(R.id.editAccountStaBalanceInput);

        MyData.newAccount = new MyAccount(newAccountNameInput.getText().toString(),
                newAccountBankInput.getText().toString(),
                new Integer(Integer.parseInt(newAccountDueDayInput.getText().toString())),
                new Double(Double.parseDouble(newAccountToPayInput.getText().toString())));

        MyData.myData.addAccountToDatabase(MyData.newAccount);

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_account, menu);
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
