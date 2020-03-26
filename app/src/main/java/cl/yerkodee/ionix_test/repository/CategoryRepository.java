package cl.yerkodee.ionix_test.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import cl.yerkodee.ionix_test.AppThreadExecutor;
import cl.yerkodee.ionix_test.api.IonixApiInterceptor;
import cl.yerkodee.ionix_test.api.IonixService;
import cl.yerkodee.ionix_test.database.CategoryDAO;
import cl.yerkodee.ionix_test.model.category.Category;
import cl.yerkodee.ionix_test.repository.utils.NetworkBoundResource;
import cl.yerkodee.ionix_test.repository.utils.Resource;

@Singleton
public class CategoryRepository {

    private final CategoryDAO categoryDAO;
    private final IonixService ionixService;
    private final AppThreadExecutor appThreadExecutor;

    @Inject
    public CategoryRepository(CategoryDAO categoryDAO, IonixService ionixService,
                              AppThreadExecutor appThreadExecutor) {
        this.categoryDAO = categoryDAO;
        this.ionixService = ionixService;
        this.appThreadExecutor = appThreadExecutor;
    }

    public LiveData<Resource<List<Category>>> loadAllCategories(){
        return new NetworkBoundResource<List<Category>, List<List<Category>>>(appThreadExecutor){

            @Override
            protected boolean shouldFetch(List<Category> data) {
                Log.d("CAT_REPOSITORY", "shouldFetch: " + data);
                return data == null || data.isEmpty();
            }

            @Override
            protected LiveData<List<Category>> loadFromDb() {
                Log.d("CAT_REPOSITORY", "loadFromDb: " + categoryDAO.findAllCategories().getValue());
                return categoryDAO.findAllCategories();
            }

            @Override
            protected void saveCallResult(List<List<Category>> item) {
                Log.d("CAT_REPOSITORY", "saveCallResult: " + item.size());
                for (List<Category> categories : item) {
                    categoryDAO.insertList(categories);
                }
            }

            @Override
            protected LiveData<IonixApiInterceptor<List<List<Category>>>> createCall() {
                Log.d("CAT_REPOSITORY", "createCall: " + ionixService.getCategories().getValue());
                return ionixService.getCategories();
            }

        }.asLiveData();
    }

}
