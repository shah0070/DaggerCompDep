package com.ecommerce.myapplication.api;

import com.ecommerce.myapplication.models.response.DiscoverResponse;

import java.util.Map;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiService {
    @GET("discover/movie")
    Observable<DiscoverResponse> getMoviesList(@QueryMap Map<String, Object> params);

}
