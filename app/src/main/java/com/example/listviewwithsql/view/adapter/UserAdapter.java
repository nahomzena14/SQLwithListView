package com.example.listviewwithsql.view.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.listviewwithsql.databinding.UserItemLayoutBinding;
import com.example.listviewwithsql.model.User;

import java.util.List;


public class UserAdapter extends BaseAdapter {

    private List<User> userList;
    private UserDelegate userDelegate;


    public UserAdapter(List<User> userList,UserDelegate userDelegate){
        this.userList = userList;
        this.userDelegate = userDelegate;
    }

    public interface UserDelegate{
        void selectUser(User user);
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public User getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void updateList(List<User> userList){
        this.userList = userList;
        //this.userList.add(newUser);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        @SuppressLint("ViewHolder") UserItemLayoutBinding binding =UserItemLayoutBinding.inflate(LayoutInflater.from(viewGroup.getContext()),
                viewGroup,
                false);
        binding.userTextView.setText(userList.get(position).toString());

        binding.getRoot().setOnClickListener(v -> {
            userDelegate.selectUser(userList.get(position));
        });
        return  binding.getRoot();
    }
}
