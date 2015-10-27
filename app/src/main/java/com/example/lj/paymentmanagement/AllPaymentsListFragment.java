package com.example.lj.paymentmanagement;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllPaymentsListFragment extends Fragment {


    ListView paymentListView = null;

    public AllPaymentsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = null;
        rootView = inflater.inflate(R.layout.fragment_payment_list, container, false);
        paymentListView = (ListView) rootView.findViewById(R.id.paymentListView);
        paymentListView.setAdapter(MyData.paymentListAdapter);
        registerPaymentListViewItemClicked();
        MyData.myData.updatePaymentListView();
        return rootView;
    }

    public void registerPaymentListViewItemClicked(){
        paymentListView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent,
                                                   View view, int position, long id) {
                        if (position == MyData.displayPaymentList.size() - 1) {
                            Toast.makeText(getActivity(),
                                    "This item cannot be deleted or edited", Toast.LENGTH_LONG).show();
                            return true;
                        }
                        MyData.selectPaymentItemIndex = position;
                        MyData.selectedPaymentItem = MyData.allMyPaymentItems.get(position);
                        MyData.editPaymentItem = MyData.allMyPaymentItems.get(position);
                        startActivity(new Intent(getActivity(),
                                EditOrDeletePaymentActivity.class));
                        return true;
                    }
                });
    }


}
