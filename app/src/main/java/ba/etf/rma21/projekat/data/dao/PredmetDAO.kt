package ba.etf.rma21.projekat.data.dao

import androidx.room.*
import ba.etf.rma21.projekat.data.models.Predmet

@Dao
interface PredmetDAO {
    @Query("SELECT * FROM Predmet")
    suspend fun getAll(): List<Predmet>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPredmet(vararg users: Predmet)

    @Delete
    suspend fun deletePredmet(user: Predmet)

    @Query("DELETE FROM Predmet")
    suspend fun deleteAll()

}