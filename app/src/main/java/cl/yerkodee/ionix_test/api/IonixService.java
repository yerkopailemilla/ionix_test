package cl.yerkodee.ionix_test.api;

import androidx.lifecycle.LiveData;

import java.util.List;

import cl.yerkodee.ionix_test.model.category.Category;
import cl.yerkodee.ionix_test.model.user.ValidateRutResponse;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface IonixService {

    @GET("bins/h3ayu")
    LiveData<IonixApiInterceptor<List<List<Category>>>> getCategories();

    @GET()
    LiveData<IonixApiInterceptor<ValidateRutResponse>> validateRut(@Url String url);

}
