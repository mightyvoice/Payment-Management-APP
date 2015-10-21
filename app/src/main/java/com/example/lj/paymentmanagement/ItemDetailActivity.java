package com.example.lj.paymentmanagement;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.Collections;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ItemListActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link ItemDetailFragment}.
 */
public class ItemDetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        // Show the Up button in the action bar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(ItemDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(ItemDetailFragment.ARG_ITEM_ID));
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, ItemListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    public void addAccountButtonClicked(View view){
        startActivity(new Intent(this, AddAccountActivity.class));
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
