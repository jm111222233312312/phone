package com.cookandroid.person;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PersonService {
@GET("list")
    Call<List<Person>> findAll();
@POST("insert")
    Call<Person> save (@Body Person personDto);

@PUT("update/{id}")
    Call<Person> update(@Path("id") Long id, @Body Person person);

@DELETE("delete/{id}")
    Call<Void> delete(@Path("id") Long id);
}
