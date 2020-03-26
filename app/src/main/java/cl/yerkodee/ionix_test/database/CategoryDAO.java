package cl.yerkodee.ionix_test.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import cl.yerkodee.ionix_test.model.category.Category;

@Dao
public interface CategoryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertList(List<Category> categories);

    @Query("SELECT * FROM category_table")
    LiveData<List<Category>> findAllCategories();

}
