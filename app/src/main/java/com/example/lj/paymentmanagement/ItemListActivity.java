package com.example.lj.paymentmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Collections;

/**
 * The main entrance of the whole app
 */


/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ItemListFragment} and the item details
 * (if present) is a {@link ItemDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link ItemListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class ItemListActivity extends FragmentActivity
        implements ItemListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private void initDatabaseAndView(){
        //init database
//        this.deleteDatabase(MyData.DATABASE_NAME);
        MyData.myData = new MyData(this, null, null, 1);
        MyData.accountListAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, MyData.displayAccountList);
        MyData.paymentListAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, MyData.displayPaymentList);
        MyData.myData.updateAccountListView();
        MyData.myData.updatePaymentListView();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((ItemListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.item_list))
                    .setActivateOnItemClick(true);
        }

        initDatabaseAndView();

//
    }

    /**
     * Callback method from {@link ItemListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(ItemDetailFragment.ARG_ITEM_ID, id);
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, ItemDetailActivity.class);
            detailIntent.putExtra(ItemDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
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
