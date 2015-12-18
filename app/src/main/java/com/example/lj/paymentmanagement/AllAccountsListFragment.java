package com.example.lj.paymentmanagement;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllAccountsListFragment extends Fragment {

    private ListView accountListView = null;

    public AllAccountsListFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = null;
        rootView = inflater.inflate(R.layout.fragment_all_accounts_list, container, false);
        accountListView = (ListView) rootView.findViewById(R.id.accountListView);
        accountListView.setAdapter(MyData.accountListAdapter);
        accountListView.setSelectionAfterHeaderView();
        registerAccountListViewItemClicked();
        MyData.myData.updateAllAccountsFromDatabase();
        MyAccount.accountNameReverseSortFlag = false;
        Collections.sort(MyData.allMyAccounts, MyAccount.accountNameComparator);
        MyData.myData.updateAccountListView();

        return rootView;
    }

    public void registerAccountListViewItemClicked(){
        accountListView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent,
                                                   View view, int position, long id) {
                        if (position == 0){
                            Toast.makeText(getActivity(),
                                    "This item cannot be deleted or edited", Toast.LENGTH_LONG).show();
                            return true;
                        }
                        MyData.selectAccountIndex = position-1;
                        MyData.selectedAccount = MyData.allMyAccounts.get(position-1);
                        startActivity(new Intent(getActivity(),
                                EditOrDeleteAccountActivity.class));
                        return true;
                    }
                });
    }

    /**
     * Adapter for our list of {@link GroupItem}s.
     */

}
