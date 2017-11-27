package com.doe.consumer.user;


import com.doe.adapters.ServerAdapter;
import com.doe.consumer.donation.IDonationService;
import com.doe.models.Donation;
import com.doe.models.User;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserConsumer {

    private IUserService userService;
    private Retrofit retrofit;

    public UserConsumer() {
        this.retrofit = new Retrofit.
                Builder().
                baseUrl(ServerAdapter.URL_BASE).
                addConverterFactory(GsonConverterFactory.create()).
                build();

        this.userService = retrofit.create(IUserService.class);

    }

    public Call<User> postCadastro(User user){
        return this.userService.postCadastro(user);
    }

}
