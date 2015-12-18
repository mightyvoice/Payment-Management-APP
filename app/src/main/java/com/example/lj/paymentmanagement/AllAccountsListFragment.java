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
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllAccountsListFragment extends Fragment {

    private AnimatedExpandableListView accountListView = null;
    private ExampleAdapter adapter;
    public static List<GroupItem> items;

    public AllAccountsListFragment() {
        // Required empty public constructor
    }

    private void initAllAccountsList(){
        items = new ArrayList<GroupItem>();

        // Populate our list with groups and it's children
        for(int i = 1; i < 100; i++) {
            GroupItem item = new GroupItem();

            item.accountName = "Group " + i;
            item.bankName = "group" + i;
            item.title1 = "Group " + i;
            item.hint1 = "group" + i;

            for(int j = 0; j < i; j++) {
                ChildItem child = new ChildItem();
                child.title = "Awesome item " + j;
                child.hint = "Too awesome";

                item.items.add(child);
            }

            items.add(item);
        }


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = null;
        rootView = inflater.inflate(R.layout.fragment_all_accounts_list, container, false);
        initAllAccountsList();

        ///////Original list view initialization///////////////

//        accountListView.setAdapter(MyData.accountListAdapter);
//        accountListView.setSelectionAfterHeaderView();
//        registerAccountListViewItemClicked();
//        MyData.myData.updateAllAccountsFromDatabase();
//        MyAccount.accountNameReverseSortFlag = false;
//        Collections.sort(MyData.allMyAccounts, MyAccount.accountNameComparator);
//        MyData.myData.updateAccountListView();

        /////////////Expandable listview initialization//////////
        adapter = new ExampleAdapter(this.getActivity().getApplicationContext());
        adapter.setData(items);
        accountListView = (AnimatedExpandableListView) rootView.findViewById(R.id.accountlistView);
        accountListView.setAdapter(adapter);
        accountListView.setOnGroupClickListener(new OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // We call collapseGroupWithAnimation(int) and
                // expandGroupWithAnimation(int) to animate group
                // expansion/collapse.
                if (accountListView.isGroupExpanded(groupPosition)) {
                    accountListView.collapseGroupWithAnimation(groupPosition);
                } else {
                    accountListView.expandGroupWithAnimation(groupPosition);
                }
                return true;
            }

        });

        return rootView;
    }

//    public void registerAccountListViewItemClicked(){
//        accountListView.setOnItemLongClickListener(
//                new AdapterView.OnItemLongClickListener() {
//                    @Override
//                    public boolean onItemLongClick(AdapterView<?> parent,
//                                                   View view, int position, long id) {
//                        if (position == 0){
//                            Toast.makeText(getActivity(),
//                                    "This item cannot be deleted or edited", Toast.LENGTH_LONG).show();
//                            return true;
//                        }
//                        MyData.selectAccountIndex = position-1;
//                        MyData.selectedAccount = MyData.allMyAccounts.get(position-1);
//                        startActivity(new Intent(getActivity(),
//                                EditOrDeleteAccountActivity.class));
//                        return true;
//                    }
//                });
//    }


/////////////////////For expandable list view //////////////////////

    private static class GroupItem {
        String accountName;
        String bankName;
        String title1;
        String hint1;
        List<ChildItem> items = new ArrayList<ChildItem>();
    }

    private static class ChildItem {
        String title;
        String hint;
    }

    private static class ChildHolder {
        TextView title;
        TextView hint;
    }

    private static class GroupHolder {
        TextView title;
        TextView hint;
        TextView title1;
        TextView hint1;
    }

    /**
     * Adapter for our list of {@link GroupItem}s.
     */
    private class ExampleAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
        private LayoutInflater inflater;

        private List<GroupItem> items;

        public ExampleAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void setData(List<GroupItem> items) {
            this.items = items;
        }

        @Override
        public ChildItem getChild(int groupPosition, int childPosition) {
            return items.get(groupPosition).items.get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            GroupHolder holder;
            GroupItem item = getGroup(groupPosition);
            if (convertView == null) {
                holder = new GroupHolder();
                convertView = inflater.inflate(R.layout.account_title, parent, false);
                holder.title = (TextView) convertView.findViewById(R.id.textTitle);
                holder.hint = (TextView) convertView.findViewById(R.id.textHint);
                holder.title1 = (TextView) convertView.findViewById(R.id.textTitle1);
                holder.hint1 = (TextView) convertView.findViewById(R.id.textHint1);
                convertView.setTag(holder);
            } else {
                holder = (GroupHolder) convertView.getTag();
            }

            holder.title.setText(item.accountName);
            holder.hint.setText(item.bankName);
            holder.title1.setText(item.accountName);
            holder.hint1.setText(item.accountName);

            return convertView;
        }

        @Override
        public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChildHolder holder;
            ChildItem item = getChild(groupPosition, childPosition);
            if (convertView == null) {
                holder = new ChildHolder();
                convertView = inflater.inflate(R.layout.account_detail, parent, false);
                holder.title = (TextView) convertView.findViewById(R.id.textTitle);
                holder.hint = (TextView) convertView.findViewById(R.id.textHint);
                convertView.setTag(holder);
            } else {
                holder = (ChildHolder) convertView.getTag();
            }

            holder.title.setText(item.title);
            holder.hint.setText(item.hint);

            return convertView;
        }

        @Override
        public int getRealChildrenCount(int groupPosition) {
            return items.get(groupPosition).items.size();
        }

        @Override
        public GroupItem getGroup(int groupPosition) {
            return items.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return items.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int arg0, int arg1) {
            return true;
        }

    }
}
