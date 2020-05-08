package com.ecommerce.myapplication.movie;

import com.ecommerce.myapplication.AppModule;
import com.ecommerce.myapplication.api.ApiModule;
import com.ecommerce.myapplication.api.ApiService;
import com.ecommerce.myapplication.models.response.DiscoverResponse;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;

/**
 * Created by abhishek
 * on 14/12/17.
 */

@Module(includes = AppModule.class)
public class MovieModule {

    private final Contracts.View movieView;

    public MovieModule(Contracts.View movieView) {
        this.movieView = movieView;
    }

    @Provides
//    @MovieScope
    @MovieScope
    MovieListPresenter provideMovieListPresenter(ApiService apiService) {
        return new MovieListPresenter(movieView, apiService);
    }


}
