/**
 * Created by Vimal on July-2021.
 */
package com.vimal.retrofitwithdatabase.api;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
 
    String BASE_URL = "https://simplifiedcoding.net/demos/";

    @GET("marvel")
    Call<ArrayList<Hero>> getHeroes();
 
}