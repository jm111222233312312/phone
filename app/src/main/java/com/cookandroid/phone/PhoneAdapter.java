package com.cookandroid.phone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.MyViewHolder> {
    private List<Phone> phoneList;
    public PhoneAdapter(List<Phone> phoneList){
        this.phoneList = phoneList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.phone_list,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Phone phone = phoneList.get(position);
        holder.tvname.setText(phone.getName());
        holder.tvtel.setText(phone.getTel());
    }

    @Override
    public int getItemCount() {
        return (null !=phoneList ? phoneList.size() : 0);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tvname;
        private TextView tvtel;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            tvname = itemView.findViewById(R.id.name);
            tvtel = itemView.findViewById(R.id.phone);
        }
    }

    //mainactivity에서 해도 되지만 코드 간결성을 위해 여기에서 작업
    //insert
    public void addItem(Phone phone){
        phoneList.add(phone);
        notifyDataSetChanged();//새로고침
    }

}

