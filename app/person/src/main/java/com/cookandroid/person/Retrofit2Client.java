package com.cookandroid.person;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit2Client {
    private static Retrofit2Client instance;
    private PersonService personService;
    public Retrofit2Client(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.100.102.28:8800")//내가 만든 스프링부트 서버
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        personService = retrofit.create(PersonService.class);// PhoneService객체를 통해서 Retrofit을 생성할 것임.
    }
    public static Retrofit2Client getInstance(){
        if (instance==null){
            instance = new Retrofit2Client();
        }
        return instance;
    }

    public PersonService getPersonService() {
        return personService;
    }
}
