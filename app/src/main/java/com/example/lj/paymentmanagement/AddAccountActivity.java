package com.example.lj.paymentmanagement;

import android.content.Intent;
import android.net.Uri;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class AddAccountActivity extends ActionBarActivity {

    public EditText newAccountNameInput;
    public EditText newAccountBankInput;
    public EditText newAccountDueDayInput;
    public Button addNewAccountButton;

    TextToSpeech ttsObj;

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
        newAccountDueDayInput = (EditText)findViewById(R.id.newAccountDueDayInput);

        newAccountNameInput.setText("Freedom");
        newAccountBankInput.setText("BoA");

        String allInput = newAccountNameInput.getText().toString() + ",    " +
                newAccountBankInput.getText().toString() + ",    " +
                newAccountDueDayInput.getText().toString();
        Intent data = new Intent();
        data.setData(Uri.parse(allInput));
        setResult(RESULT_OK, data);

//        String toSpeak = newAccountNameInput.getText().toString();
//
//        ttsObj = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener(){
//            @Override
//            public void onInit(int status) {
//                if (status != TextToSpeech.ERROR) {
//                    ttsObj.setLanguage(Locale.US);
//                }
//            }
//        });
//        ttsObj.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
//        Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();

        //close the activity
        finish();
    }

//    public void onPause(){
//        if(ttsObj !=null){
//            ttsObj.stop();
//            ttsObj.shutdown();
//        }
//        super.onPause();
//    }

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
