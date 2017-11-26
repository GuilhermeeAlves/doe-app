package com.doe.consumer.donation;


import com.doe.models.Donation;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IDonationService {

    @POST("donation/")
    Call<Donation> postCadastro(@Body Donation donation);

}
