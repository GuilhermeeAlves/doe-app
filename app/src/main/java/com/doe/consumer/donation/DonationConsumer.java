package com.doe.consumer.donation;

import com.doe.adapters.ServerAdapter;
import com.doe.models.Donation;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DonationConsumer {

    private IDonationService donationService;
    private Retrofit retrofit;

    public DonationConsumer(){
        this.retrofit = new Retrofit.
                Builder().
                baseUrl(ServerAdapter.URL_BASE).
                addConverterFactory(GsonConverterFactory.create()).
                build();

        this.donationService = retrofit.create(IDonationService.class);

    }

    public Call<Donation> postCadastro(Donation donation){
        return this.donationService.postCadastro(donation);
    }

}
