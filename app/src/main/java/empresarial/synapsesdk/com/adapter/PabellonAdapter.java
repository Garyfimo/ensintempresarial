package empresarial.synapsesdk.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import empresarial.synapsesdk.com.model.Pabellon;
import empresarial.synapsesdk.com.model.Piso;

public class PabellonAdapter extends BaseExpandableListAdapter {

    private List<Pabellon> groups;
    private LayoutInflater mInflater;

    public PabellonAdapter(Context context) {
        groups = new ArrayList<Pabellon>();
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return groups.get(i).getPisos().size();
    }

    @Override
    public Object getGroup(int i) {
        return groups.get(i);
    }

    @Override
    public Object getChild(int groupPos, int childPosition) {
        return groups.get(groupPos).getPisos().get(childPosition);
    }

    @Override
    public long getGroupId(int i) {
        return groups.get(i).getId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groups.get(groupPosition).getPisos().get(childPosition).getIdPiso();
    }

    @Override
    public boolean hasStableIds() {

        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {
        View v;
        if (convertView == null) {
            v = newGroupView(isExpanded, parent);
        } else {
            v = convertView;
        }
        bindGroupView(v, groupPosition, groups.get(groupPosition));
        return v;
    }

    private void bindGroupView(View view, int pos, Pabellon pabellon) {
        TextView v = (TextView) view.findViewById(android.R.id.text1);
        if (v != null) {
            v.setText(String.format("Pabellon %02d", pos + 1));
        }
    }

    public View newGroupView(boolean isExpanded, ViewGroup parent) {
        return mInflater.inflate((isExpanded) ? android.R.layout.simple_expandable_list_item_1 : android.R.layout.simple_expandable_list_item_1,
                parent, false);
    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        View v;
        if (convertView == null) {
            v = newChildView(isLastChild, parent);
        } else {
            v = convertView;
        }
        bindChildView(v, groups.get(groupPosition).getPisos().get(childPosition));
        return v;
    }

    public View newChildView(boolean isLastChild, ViewGroup parent) {
        return mInflater.inflate((isLastChild) ? android.R.layout.simple_expandable_list_item_2 : android.R.layout.simple_expandable_list_item_2, parent, false);
    }

    private void bindChildView(View view, Piso data) {
        TextView v = (TextView) view.findViewById(android.R.id.text1);
        if (v != null) {
            v.setText("Piso " + data.getNumPiso());
        }
    }

    @Override
    public boolean isChildSelectable(int i, int i2) {
        return true;
    }

    public void add(List<Pabellon> list) {
        groups.addAll(list);
    }

    public void clear() {
        groups.clear();
    }


    class GroupViewHolder {

        GroupViewHolder(View view) {
        }
    }

    class ChildViewHolder {

        ChildViewHolder(View view) {

        }
    }
}
