package ba.etf.rma21.projekat.data.dao

import androidx.room.*
import ba.etf.rma21.projekat.data.models.Grupa

@Dao
interface GrupaDAO {
    @Query("SELECT * FROM Grupa")
    suspend fun getAll(): List<Grupa>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGrupu(vararg users: Grupa)


    @Delete
    suspend fun deleteGrupa(user: Grupa)

    @Query("DELETE FROM Grupa")
    suspend fun deleteAll()

}