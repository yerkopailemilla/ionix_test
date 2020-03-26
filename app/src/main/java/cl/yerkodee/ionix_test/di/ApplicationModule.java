package cl.yerkodee.ionix_test.di;

import android.app.Application;

import androidx.room.Room;

import javax.inject.Singleton;

import cl.yerkodee.ionix_test.database.CategoryDAO;
import cl.yerkodee.ionix_test.database.DetailDAO;
import cl.yerkodee.ionix_test.database.IonixDatabase;
import dagger.Module;
import dagger.Provides;

@Module(includes = ViewModelModule.class)
public class ApplicationModule {

    @Singleton
    @Provides
    IonixDatabase provideDb(Application app){
        return Room.databaseBuilder(app, IonixDatabase.class, "ionix_test.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Singleton
    @Provides
    CategoryDAO provideCategoryDAO(IonixDatabase db){
        return db.categoryDAO();
    }

    @Singleton
    @Provides
    DetailDAO provideDetailDAO(IonixDatabase db){
        return db.detailDAO();
    }

}
