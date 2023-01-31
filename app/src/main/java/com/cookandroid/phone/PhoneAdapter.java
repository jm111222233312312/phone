package com.cookandroid.phone;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = v.inflate(v.getContext(), R.layout.layout_add_concat,null);
                final EditText etName = dialogView.findViewById(R.id.etname);
                final EditText etTel = dialogView.findViewById(R.id.etle);

                etName.setText(phone.getName());
                etTel.setText(phone.getTel());

                AlertDialog.Builder dlg = new AlertDialog.Builder(v.getContext());
                dlg.setTitle("연락처 수정");
                dlg.setView(dialogView);
                dlg.setPositiveButton("update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Phone phoneDto = new Phone();
                        phoneDto.setName(etName.getText().toString());
                        phoneDto.setTel(etTel.getText().toString());

                        PhoneService phoneService = Retrofit2Client.getInstance().getPhoneService();
                        Call<Phone> call = phoneService.update(phone.getId(),phoneDto);
                        call.enqueue(new Callback<Phone>() {
                            @Override
                            public void onResponse(Call<Phone> call, Response<Phone> response) {
                                updateItem(response.body(),position);
                            }

                            @Override
                            public void onFailure(Call<Phone> call, Throwable t) {

                            }
                        });
                    }

                });
                dlg.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PhoneService phoneService = Retrofit2Client.getInstance().getPhoneService();
                        Call<Void> call = phoneService.deleteById(phone.getId());
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                removeItem(position);

                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        });
                    }
                });
                dlg.show();
            }
        });
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
    public void updateItem(Phone phone, int position){
        Phone p = phoneList.get(position);
        p.setName(phone.getName());
        p.setTel(phone.getTel());
        notifyDataSetChanged();//새로고침

    }
    public void removeItem(int position){
        phoneList.remove(position);
        notifyDataSetChanged();
    }

}

