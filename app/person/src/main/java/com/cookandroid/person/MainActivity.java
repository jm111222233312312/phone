package com.cookandroid.person;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Person person;
    EditText etName, etPhone;

    //인터페이스
    public interface OnLongItemClickListener{
        void onLongItemClick(int pos);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycleview);
        List<Person> personList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        //Adapter
        PersonAdapter adapter = new PersonAdapter(personList);
        recyclerView.setAdapter(adapter);



        //데이터 강제 주입
//        for(int i =0; i<20;i++){
//            adapter.addItem(new Person("홍길동"+i,"010-1111-1111"));
//        }

        //데이터 조회
        PersonService personService = Retrofit2Client.getInstance().getPersonService();
        Call<List<Person>> call = personService.findAll();
        call.enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                List<Person> personList = response.body();
                recyclerView.setLayoutManager(linearLayoutManager);
                PersonAdapter adapter = new PersonAdapter(personList);
                recyclerView.setAdapter(adapter);
                //  Log.d("data","onResponse: 응답 받은 데이터: "+personList);
                //name 출력
                adapter.setListener(new PersonListener() {
                    @Override
                    public void onItemClick(int position) {
                        Person person = adapter.getItem(position);
                        TextView name = findViewById(R.id.name);
                        name.setText(person.getName().toString());
                        TextView phone = findViewById(R.id.phone);
                        phone.setText(person.getPhone().toString());
                        Log.d("select", person.getId()+"");
                        //   Toast.makeText(MainActivity.this,person.getName(),Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {

            }
        });


//        //phone 출력 롱클릭
//        adapter.setOnLongItemClickListener(new OnLongItemClickListener() {
//            @Override
//            public void onLongItemClick(int pos) {
//                Person person = adapter.getLongItem(pos);
//                Toast.makeText(MainActivity.this,person.getPhone(),Toast.LENGTH_SHORT).show();
//            }
//        });


        ////////버튼 이벤트///////
        Button insertBtn = findViewById(R.id.insertBtn);
        Button updateBtn = findViewById(R.id.updateBtn);
        Button deleteBtn = findViewById(R.id.deleteBtn);

        EditText name = findViewById(R.id.name);
        EditText phone = findViewById(R.id.phone);
        PersonAdapter personAdapter = new PersonAdapter(personList);

        //insert
        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person personDto = new Person();
                personDto.setName(name.getText().toString());
                personDto.setPhone(phone.getText().toString());

                PersonService personService = Retrofit2Client.getInstance().getPersonService();
                Call<Person> call = personService.save(personDto);
                call.enqueue(new Callback<Person>() {
                    @Override
                    public void onResponse(Call<Person> call, Response<Person> response) {
                        personAdapter.addItem(response.body());
                    }

                    @Override
                    public void onFailure(Call<Person> call, Throwable t) {

                    }
                });
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,person.getId()+"",Toast.LENGTH_SHORT).show();
                etName = findViewById(R.id.name);
                etPhone = findViewById(R.id.phone);

                person.setName(etName.getText().toString());
                person.setPhone(etPhone.getText().toString());
                PersonService personService = Retrofit2Client.getInstance().getPersonService();
                Call<Person> call = personService.update(person.getId(), person);
                Log.d("update", person.getId()+"");
                call.enqueue(new Callback<Person>() {
                    @Override
                    public void onResponse(Call<Person> call, Response<Person> response) {
                        Person person = response.body();
                    }

                    @Override
                    public void onFailure(Call<Person> call, Throwable t) {

                    }
                });

            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            private int position;
            @Override
            public void onClick(View v) {
                Person person = new Person();
                PersonService personService = Retrofit2Client.getInstance().getPersonService();
                Call<Void> call = personService.delete(person.getId());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Person person = personList.get(position);
                        personAdapter.removeItem(position);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });

    }
}