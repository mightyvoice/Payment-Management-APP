package com.example.lj.paymentmanagement;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.Collections;

public class MainActivity extends ActionBarActivity implements ActionBar.TabListener  {

    ActionBar actionBar;
    AllAccountsListFragment allAccountsListFragment;
    AllPaymentsListFragment allPaymentsListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initActionbar();
        initDatabaseAndView();
        initAllAccountsListFragment();
    }

    void initActionbar(){
        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab tab1 = actionBar.newTab();
        tab1.setText("Accounts");
        tab1.setTabListener(this);
        ActionBar.Tab tab2 = actionBar.newTab();
        tab2.setText("Payments");
        tab2.setTabListener(this);

        actionBar.addTab(tab1);
        actionBar.addTab(tab2);
    }

    private void initDatabaseAndView(){
        //init database
//        this.deleteDatabase(MyData.DATABASE_NAME);
        MyData.myData = new MyData(this, null, null, 1);
        MyData.accountListAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, MyData.displayAccountList);
        MyData.paymentListAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, MyData.displayPaymentList);
    }

    private void initAllAccountsListFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        allAccountsListFragment = new AllAccountsListFragment();
        fragmentTransaction.replace(R.id.mainActivityContainer, allAccountsListFragment);
        fragmentTransaction.commit();
    }

    private void initAllPaymentsListFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        allPaymentsListFragment = new AllPaymentsListFragment();
        fragmentTransaction.replace(R.id.mainActivityContainer, allPaymentsListFragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        if(tab.getText() == "Accounts"){
            initAllAccountsListFragment();
        }
        else if(tab.getText() == "Payments"){
            initAllPaymentsListFragment();
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        if(tab.getText() == "Payments"){
            initAllAccountsListFragment();
        }
        else if(tab.getText() == "Accounts"){
            initAllPaymentsListFragment();
        }
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        if(tab.getText() == "Accounts"){
            initAllAccountsListFragment();
        }
        else if(tab.getText() == "Payments"){
            initAllPaymentsListFragment();
        }
    }

    public void addAccountButtonClicked(View view){
        startActivity(new Intent(this, AddAccountActivity.class));
    }

    public void addPaymentMainPageButtonClicked(View view){
        startActivity(new Intent(this, AddPaymentActivity.class));
    }

    public void addPaymentButtonClicked(View view){
        startActivity(new Intent(this, AddPaymentActivity.class));
    }

    public void accountNameLabelButtonClicked(View view){
        MyData.myData.updateAllAccountsFromDatabase();
        Collections.sort(MyData.allMyAccounts, MyAccount.accountNameComparator);
        MyData.myData.updateAccountListView();
        MyAccount.accountNameReverseSortFlag = !MyAccount.accountNameReverseSortFlag;
    }

    public void accountDueDayLabelButtonClicked(View view){
        MyData.myData.updateAllAccountsFromDatabase();
        Collections.sort(MyData.allMyAccounts, MyAccount.dueDayComparator);
        MyData.myData.updateAccountListView();
        MyAccount.dueDayReverseSortFlag = !MyAccount.dueDayReverseSortFlag;
    }

    public void accountBankNameLabelButtonClicked(View view){
        MyData.myData.updateAllAccountsFromDatabase();
        Collections.sort(MyData.allMyAccounts, MyAccount.bankNameComparator);
        MyData.myData.updateAccountListView();
        MyAccount.bankNameReverseSortFlag = !MyAccount.bankNameReverseSortFlag;
    }

    public void accountToPayLabelButtonClicked(View view){
        MyData.myData.updateAllAccountsFromDatabase();
        Collections.sort(MyData.allMyAccounts, MyAccount.toPayBalanceeComparator);
        MyData.myData.updateAccountListView();
        MyAccount.toPayBalanceReverseSortFlag = !MyAccount.toPayBalanceReverseSortFlag;
    }

    public void accountStaBalanceLabelButtonClicked(View view){
        MyData.myData.updateAllAccountsFromDatabase();
        Collections.sort(MyData.allMyAccounts, MyAccount.statementBalanceComparator);
        MyData.myData.updateAccountListView();
        MyAccount.statementBalanceReverseSortFlag = !MyAccount.statementBalanceReverseSortFlag;
    }

    public void accountAPRLabelButtonClicked(View view){
        MyData.myData.updateAllAccountsFromDatabase();
        Collections.sort(MyData.allMyAccounts, MyAccount.APRComparator);
        MyData.myData.updateAccountListView();
        MyAccount.APRReverseSortFlag = !MyAccount.APRReverseSortFlag;
    }

    public void accountInterestLabelButtonClicked(View view){
        MyData.myData.updateAllAccountsFromDatabase();
        Collections.sort(MyData.allMyAccounts, MyAccount.toPayInterestComparator);
        MyData.myData.updateAccountListView();
        MyAccount.toPayInterestReverseSortFlag = !MyAccount.toPayInterestReverseSortFlag;
    }

    public void payAccountNameLabelButtonClicked(View view){
        MyData.myData.updateAllMyPaymentItemsFromDatabase();
        Collections.sort(MyData.allMyPaymentItems, MyPaymentItem.payAccountNameComparator);
        MyData.myData.updatePaymentListView();
        MyPaymentItem.payAccountNameReverseSortFlag = !MyPaymentItem.payAccountNameReverseSortFlag;
    }

    public void paidAccountNameLabelButtonClicked(View view){
        MyData.myData.updateAllMyPaymentItemsFromDatabase();
        Collections.sort(MyData.allMyPaymentItems, MyPaymentItem.paidAccountNameComparator);
        MyData.myData.updatePaymentListView();
        MyPaymentItem.paidAccountNameReverseSortFlag = !MyPaymentItem.paidAccountNameReverseSortFlag;
    }

    public void payAmountLabelButtonClicked(View view){
        MyData.myData.updateAllMyPaymentItemsFromDatabase();
        Collections.sort(MyData.allMyPaymentItems, MyPaymentItem.payAmountComparator);
        MyData.myData.updatePaymentListView();
        MyPaymentItem.payAmountReverseSortFlag = !MyPaymentItem.payAmountReverseSortFlag;
    }

    public void payDateLabelButtonClicked(View view){
        MyData.myData.updateAllMyPaymentItemsFromDatabase();
        Collections.sort(MyData.allMyPaymentItems, MyPaymentItem.payDateComparator);
        MyData.myData.updatePaymentListView();
        MyPaymentItem.payDateReverseSortFlag = !MyPaymentItem.payDateReverseSortFlag;
    }
}