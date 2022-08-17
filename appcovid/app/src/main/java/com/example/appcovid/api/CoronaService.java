package com.example.appcovid.api;

import com.example.appcovid.data.AllCountry;
import com.example.appcovid.data.CountryList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CoronaService {
    @GET("countries/?sort=country")
    Call<List<CountryList>> getCountries();


    @GET("countries/{country}")
    Call<CountryList> getCountryInfo(

            @Path("country") String country
    );

    @GET("all")
    Call<AllCountry> getAllCountries();
}
