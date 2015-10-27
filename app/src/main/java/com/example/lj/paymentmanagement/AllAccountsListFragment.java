package com.example.lj.paymentmanagement;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllAccountsListFragment extends Fragment {

    ListView accountListView = null;

    public AllAccountsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = null;
        rootView = inflater.inflate(R.layout.fragment_account_list, container, false);
        accountListView = (ListView) rootView.findViewById(R.id.accountListView);
        accountListView.setAdapter(MyData.accountListAdapter);
        registerAccountListViewItemClicked();
        MyData.myData.updateAccountListView();
        return rootView;
    }

    public void registerAccountListViewItemClicked(){
        accountListView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent,
                                                   View view, int position, long id) {
                        if (position == MyData.allMyAccounts.size()) {
                            Toast.makeText(getActivity(),
                                    "This item cannot be deleted or edited", Toast.LENGTH_LONG).show();
                            return true;
                        }
                        MyData.selectAccountIndex = position;
                        MyData.selectedAccount = MyData.allMyAccounts.get(position);
                        startActivity(new Intent(getActivity(),
                                EditOrDeleteAccountActivity.class));
                        return true;
                    }
                });
    }
}
