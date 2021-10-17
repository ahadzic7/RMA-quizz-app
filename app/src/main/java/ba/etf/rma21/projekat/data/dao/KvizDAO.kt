package ba.etf.rma21.projekat.data.dao

import androidx.room.*
import ba.etf.rma21.projekat.data.models.Kviz

@Dao
interface KvizDAO {

    @Query("SELECT * FROM Kviz")
    suspend fun getAll(): List<Kviz>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKviz(vararg users: Kviz)


    @Delete
    suspend fun deleteKviz(user: Kviz)

    @Query("DELETE FROM Kviz")
    suspend fun deleteAll()
}