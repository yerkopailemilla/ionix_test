package cl.yerkodee.ionix_test.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import cl.yerkodee.ionix_test.api.IonixService;
import cl.yerkodee.ionix_test.api.UrlIterator;
import cl.yerkodee.ionix_test.utilities.LiveDataCallAdapterFactory;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RetrofitModule {

    public static final String BASE_URL = "https://$s";

    @Singleton
    @Provides
    Gson provideGson() {
        return new GsonBuilder().setLenient()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();
    }

    @Singleton
    @Provides
    LiveDataCallAdapterFactory provideLiveDataCallAdapterFactory() {
        return new LiveDataCallAdapterFactory();
    }

    @Singleton
    @Provides
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor interceptor, UrlIterator iterator) {
        return new OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(chain -> {
                    Request original = chain.request();

                    Request.Builder builder = original.newBuilder();
                    String url = String.format(original.url().toString().replace("$", "%"), iterator.getCurrentBaseUrl());
                    builder.url(url);

                    Request request = builder.build();
                    return chain.proceed(request);
                })
                .build();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(Gson gson, LiveDataCallAdapterFactory liveDataCallAdapterFactory, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(liveDataCallAdapterFactory)
                .client(client)
                .build();
    }

    @Singleton
    @Provides
    IonixService provideIonixService(Retrofit retrofit) {
        return retrofit.create(IonixService.class);
    }

}
