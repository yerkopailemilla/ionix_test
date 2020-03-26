package cl.yerkodee.ionix_test.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import cl.yerkodee.ionix_test.model.user.DetailResponse;

@Dao
public interface DetailDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DetailResponse category);

    @Query("SELECT * FROM detail_table WHERE det_id = 2")
    LiveData<DetailResponse> findDetailUser();

}
