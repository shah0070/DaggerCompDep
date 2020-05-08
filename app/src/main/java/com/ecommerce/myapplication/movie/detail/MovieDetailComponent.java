package com.ecommerce.myapplication.movie.detail;

import com.ecommerce.myapplication.AppComponent;
import com.ecommerce.myapplication.dao.DatabaseInteractor;
import com.ecommerce.myapplication.movie.MovieScope;
import com.ecommerce.myapplication.movie.MovieScopeDetail;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Subcomponent;

/**
 * Created by abhishek
 * on 14/12/17.
 *
 * Custom component for movie detail screen, this is a child component of App Component
 * and needs to be smaller in size
 */
@MovieScopeDetail
@Component(dependencies = AppComponent.class,modules = {MovieDetailModule.class} )
public interface MovieDetailComponent {
    DatabaseInteractor getDatabaseInteractor();
    void inject(MovieDetailFragment movieDetailFragment);
}
