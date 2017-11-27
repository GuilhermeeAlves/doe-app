package com.doe.consumer.user;

import com.doe.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IUserService {

    @POST("user/")
    Call<User> postCadastro(@Body User user);

}
