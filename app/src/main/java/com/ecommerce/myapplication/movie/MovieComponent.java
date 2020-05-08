package com.ecommerce.myapplication.movie;

import com.ecommerce.myapplication.AppComponent;
import com.ecommerce.myapplication.api.ApiService;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Subcomponent;

/**
 * Created by abhishek
 * on 14/12/17.
 *
 * Custom scope for movie screen, this is a child component of App Component
 * and needs to be smaller in size
 */
@MovieScope
@Component(dependencies = AppComponent.class,modules = {MovieModule.class} )
public interface MovieComponent {

    void inject(MovieListFragment movieListFragment);

}