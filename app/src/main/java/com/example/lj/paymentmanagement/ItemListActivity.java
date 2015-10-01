package com.example.lj.paymentmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;


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

        //init all accounts and payment records. load all data to MyData.accountList
        // and Mydata.paymentList
        initAllData();
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

    private int newAccountRequestCode = 1;
    public void addAccountButtonClicked(View view){
        startActivityForResult(new Intent(this, AddAccountActivity.class), newAccountRequestCode);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == newAccountRequestCode){
            if(resultCode == RESULT_OK){
                Toast.makeText(this, data.getData().toString(), Toast.LENGTH_LONG).show();
                MyData.accountList.add(data.getData().toString());
                MyData.accountListAdapter.notifyDataSetChanged();
            }
        }

        if(requestCode == newPaymentRequestCode){
            if(resultCode == RESULT_OK){
                Toast.makeText(this, data.getData().toString(), Toast.LENGTH_LONG).show();
                MyData.paymentList.add(data.getData().toString());
                MyData.paymentListAdapter.notifyDataSetChanged();
            }
        }
    }

    private int newPaymentRequestCode = 2;
    public void addPaymentButtonClicked(View view){
        startActivityForResult(new Intent(this, AddPaymentActivity.class), newPaymentRequestCode);
    }


    private void initAllData() {
        MyData.accountList.clear();
        MyData.paymentList.clear();
    }

}
