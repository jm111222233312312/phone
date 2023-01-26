package com.cookandroid.phone;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

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
                addContact();
            }
        });

        PhoneService phoneService = Retrofit2Client.getInstance().getPhoneService();//만들어진 PhoneService객체 반환
        Call<List<Phone>> call = phoneService.findAll();//데이터 조회, 스프링부트랑 연결.response.body()로 받아옴
        call.enqueue(new Callback<List<Phone>>() {
            @Override
            public void onResponse(Call<List<Phone>> call, Response<List<Phone>> response) {
                List<Phone> phoneList = response.body();
                recyclerView = findViewById(R.id.recyclerview);//받아온 값을 recyclerview에 뿌림
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

    private void addContact(){
        View dialogView = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.layout_add_concat,null);

        final EditText etName = dialogView.findViewById(R.id.etname);
        final EditText etTel = dialogView.findViewById(R.id.etle);

        AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
        dlg.setTitle("연락처 등록");
        dlg.setView(dialogView);
        dlg.setPositiveButton("등록", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Phone phoneDto = new Phone();
                phoneDto.setName(etName.getText().toString());
                phoneDto.setTel(etTel.getText().toString());

                Log.d("insert response","onClick: 등록 클릭 시 값 확인"+phoneDto);

                PhoneService phoneService = Retrofit2Client.getInstance().getPhoneService();
                Call<Phone> call = phoneService.save(phoneDto);

                call.enqueue(new Callback<Phone>() {
                    @Override
                    public void onResponse(Call<Phone> call, Response<Phone> response) {
                    phoneAdapter.addItem(response.body()); //저장된 객체가 ArrayList에 추가
                    }

                    @Override
                    public void onFailure(Call<Phone> call, Throwable t) {

                    }
                });
            }
        });
        dlg.setNegativeButton("닫기",null);
        dlg.show();
    }
}