package com.example.firestoretest2;

import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class UserAdapter extends BaseAdapter {

    List<User> userList;

    public UserAdapter(List<User> userList){
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater myInlfater = LayoutInflater.from(parent.getContext());
            convertView = myInlfater.inflate(R.layout.user_layout, parent, false);

            TextView txtUserName = convertView.findViewById(R.id.txtUserName);
            TextView txtUserEmail = convertView.findViewById(R.id.txtUserEmail);

            User userRecord = (User) getItem(position);

            txtUserName.setText(userRecord.getUserName());
            txtUserName.setGravity(Gravity.LEFT);

            txtUserEmail.setText(userRecord.getUserEmail());
            txtUserEmail.setGravity(Gravity.LEFT);

        }

        return convertView;


    }
}
