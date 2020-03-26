package cl.yerkodee.ionix_test.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import cl.yerkodee.ionix_test.model.category.Category;
import cl.yerkodee.ionix_test.model.user.DetailResponse;

@Database(entities = {Category.class, DetailResponse.class}, exportSchema = false, version = 2)
public abstract class IonixDatabase extends RoomDatabase {
    abstract public CategoryDAO categoryDAO();
    abstract public DetailDAO detailDAO();
}
