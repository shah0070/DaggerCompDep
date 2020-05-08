package com.ecommerce.myapplication;


import androidx.room.Room;

import com.ecommerce.myapplication.api.ApiService;
import com.ecommerce.myapplication.dao.DatabaseInteractor;
import com.ecommerce.myapplication.dao.DatabaseWrapper;
import com.ecommerce.myapplication.database.AppDatabase;
import com.ecommerce.myapplication.movie.MovieScope;
import com.ecommerce.myapplication.movie.MovieScopeDetail;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by abhishek
 * on 14/12/17.
 *
 * Provides application class
 */

@Module
public class AppModule {

    private final BaseApplication application;

    public AppModule(BaseApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    DatabaseInteractor providesDatabaseInteractor(AppDatabase appDatabase) {
        return new DatabaseWrapper(appDatabase);
    }

    @Provides
    @Singleton
    BaseApplication providesApplication () {
        return application;
    }

    @Provides
    @Singleton
    AppDatabase providesAppDatabase() {
        return Room.databaseBuilder(application, AppDatabase.class, "movieData").allowMainThreadQueries().build();
    }

    @Provides
//    @Singleton
    @MovieScope
    //@MovieScopeDetail
    ApiService providesApiService() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.hostnameVerifier((str, sslSession) -> true);

        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);

        // Add headers
        builder.interceptors().add(chain -> {
            Request request = chain.request();

            request = request.newBuilder()
                    .build();
            return chain.proceed(request);

        });

        // Logging
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.interceptors().add(interceptor);

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setDateFormat("yyyy-MM-dd")
                .create();

        RxJava2CallAdapterFactory rxAdapter = RxJava2CallAdapterFactory
                .createWithScheduler(Schedulers.io());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(rxAdapter)
                .build();
        return retrofit.create(ApiService.class);
    }
}
