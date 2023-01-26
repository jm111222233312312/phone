package com.cookandroid.phone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PhoneAdapter phoneAdapter;
    LinearLayoutManager manager;
    private FloatingActionButton floatingBtn;
    private PhoneService phoneService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //데이터 추가
        floatingBtn = findViewById(R.id.floatingBtn);
        floatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        PhoneService phoneService = Retrofit2Client.getInstance().getPhoneService();//PhoneService객체 반환
        Call<List<Phone>> call = phoneService.findAll();//데이터 조회, 스프링부트랑 연결.response.body()로 받아옴
        call.enqueue(new Callback<List<Phone>>() {
            @Override
            public void onResponse(Call<List<Phone>> call, Response<List<Phone>> response) {
                List<Phone> phoneList = response.body();
                recyclerView = findViewById(R.id.recyclerview);//recyclerview에 뿌림
                manager =new LinearLayoutManager(MainActivity.this,RecyclerView.VERTICAL,false);
                recyclerView.setLayoutManager(manager);//recyclerview와 manager 연결
                phoneAdapter = new PhoneAdapter(phoneList);
                recyclerView.setAdapter(phoneAdapter);//recyclerview와 phoneAdapter 연결
                Log.d("data","onResponse: 응답 받은 데이터: "+phoneList);
            }

            @Override
            public void onFailure(Call<List<Phone>> call, Throwable t) {

            }
        });
    }
}