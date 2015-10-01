
package com.example.lj.paymentmanagement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
                showAccounts(rootView);
            }
            if(mItem.item_name == "Payment List"){
                rootView = inflater.inflate(R.layout.fragment_payment_list, container, false);
                showPayments(rootView);
            }

        }
        return rootView;
    }

    private void showAccounts(View rootView){
        MyData.accountListAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, MyData.accountList);

        ListView accountListView = (ListView) rootView.findViewById(R.id.accountListView);
        accountListView.setAdapter(MyData.accountListAdapter);
    }

    private void showPayments(View rootView){
        MyData.paymentListAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, MyData.paymentList);
        ListView paymentListView = (ListView) rootView.findViewById(R.id.paymentListView);
        paymentListView.setAdapter(MyData.paymentListAdapter);
    }

}
