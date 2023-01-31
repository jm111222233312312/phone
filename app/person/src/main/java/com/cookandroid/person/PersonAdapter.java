package com.cookandroid.person;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.MyViewHolder> {
    //PersonListener 객체 생성
    private PersonListener listener;

    //PersonListener 연결
    public void setListener(PersonListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public PersonAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.person_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view, listener);
        return viewHolder;
    }

    private List<Person> personList;

    public PersonAdapter(List<Person> personList) {
        this.personList = personList;
    }


    @Override
    public void onBindViewHolder(@NonNull PersonAdapter.MyViewHolder holder, int position) {
        Person person = personList.get(position);
        holder.tvname.setText(person.getName());
        holder.tvphone.setText(person.getPhone());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                person.setName(name.getText().toString());
//                person.setPhone(phone.getText().toString());
//                PersonService personService = Retrofit2Client.getInstance().getPhoneService();
//                Call<Person> call = personService.update(person.getId(), person);
//                Log.d("update", person.getId()+"");
//                call.enqueue(new Callback<Person>() {
//                    @Override
//                    public void onResponse(Call<Person> call, Response<Person> response) {
//                       updateItem(response.body(),position);
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<Person> call, Throwable t) {
//
//                    }
//                });
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return (null != personList ? personList.size() : 0);
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvname;
        private TextView tvphone;

        public MyViewHolder(@NonNull View itemView, final PersonListener listener) {
            super(itemView);
            tvname = itemView.findViewById(R.id.tvname);
            tvphone = itemView.findViewById(R.id.tvphone);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    listener.onItemClick(position);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    onLongItemClickListener.onLongItemClick(position);
                    return true;
                }
            });
        }
    }

    public void addItem(Person person) {
        personList.add(person);
        notifyDataSetChanged();
    }

    public Person getItem(int position) {
        Person person = personList.get(position);
        return person;
    }

    ;

    private MainActivity.OnLongItemClickListener onLongItemClickListener;

    //setter
    public void setOnLongItemClickListener(MainActivity.OnLongItemClickListener onLongItemClickListener) {
        this.onLongItemClickListener = onLongItemClickListener;
    }

    public Person getLongItem(int pos) {
        Person person = personList.get(pos);
        return person;
    }

    public void updateItem(Person person, int position){
        Person p = personList.get(position);
        p.setName(person.getName());
        p.setPhone(person.getPhone());
        notifyDataSetChanged();
    }


    public void removeItem(int position) {
        personList.remove(position);
        notifyDataSetChanged();
    }
}
