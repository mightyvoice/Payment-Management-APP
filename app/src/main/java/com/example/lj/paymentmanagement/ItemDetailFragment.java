
package com.example.lj.paymentmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lj.paymentmanagement.ItemDetailActivity;
import com.example.lj.paymentmanagement.ItemListActivity;
import com.example.lj.paymentmanagement.MyAccount;
import com.example.lj.paymentmanagement.MyPaymentItem;
import com.example.lj.paymentmanagement.R;
import com.example.lj.paymentmanagement.dummy.DummyContent;

import java.util.ArrayList;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {



    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";


    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = null;
        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            if(mItem.item_name == "Account") {
                rootView = inflater.inflate(R.layout.fragment_account_list, container, false);
                ItemListActivity.accountListView = (ListView) rootView.findViewById(R.id.accountListView);
                ItemListActivity.accountListView.setAdapter(MyData.accountListAdapter);
                registerAccountListViewItemClicked();
            }
            if(mItem.item_name == "Payment List"){
                rootView = inflater.inflate(R.layout.fragment_payment_list, container, false);
                ItemListActivity.paymentListView = (ListView) rootView.findViewById(R.id.paymentListView);
                ItemListActivity.paymentListView.setAdapter(MyData.paymentListAdapter);
            }

        }
        return rootView;
    }

    public void registerAccountListViewItemClicked(){
        ItemListActivity.accountListView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener(){
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent,
                                                   View view, int position, long id) {
                        MyData.clickedItemID = position;
                        if(position == MyData.accountList.size()-1){
                            Toast.makeText(getActivity(),
                                    "This item cannot be deleted or edited", Toast.LENGTH_LONG).show();
                            return true;
                        }
                        startActivity(new Intent(getActivity(), EditOrDeleteAccountActivity.class));
//                        Toast.makeText(getActivity(),
//                                MyData.accountList.get(position) + " is Clicked", Toast.LENGTH_LONG).show();
                        if(MyData.confirmedToDelete && MyData.clickedItemID > -1){
                            MyData.deleteAccountByCurrentIndex();
                        }
                        return true;
                    }
                });
    }

}
