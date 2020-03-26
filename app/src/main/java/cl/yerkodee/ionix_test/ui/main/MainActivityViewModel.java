package cl.yerkodee.ionix_test.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import cl.yerkodee.ionix_test.model.category.Category;
import cl.yerkodee.ionix_test.repository.CategoryRepository;
import cl.yerkodee.ionix_test.repository.utils.Resource;

public class MainActivityViewModel extends ViewModel {

    private final LiveData<Resource<List<Category>>> categories;

    @Inject
    public MainActivityViewModel(CategoryRepository categoryRepository){
        categories = categoryRepository.loadAllCategories();
    }

    public LiveData<Resource<List<Category>>> getCategories(){
        return categories;
    }

}
