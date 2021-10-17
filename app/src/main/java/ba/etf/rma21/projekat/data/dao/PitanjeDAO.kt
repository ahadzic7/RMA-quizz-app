package ba.etf.rma21.projekat.data.dao

import androidx.room.*
import ba.etf.rma21.projekat.data.models.Pitanje

@Dao
interface PitanjeDAO {
    @Query("SELECT * FROM Pitanje")
    suspend fun getAll(): List<Pitanje>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPitanje(vararg users: Pitanje)

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertPitanja(vararg users: List<Pitanje>)

    @Delete
    suspend fun deleteGrupa(user: Pitanje)

    @Query("DELETE FROM Pitanje")
    suspend fun deleteAll()

    @Query("SELECT * FROM Pitanje WHERE id == :idPitanja")
    suspend fun getById(idPitanja: Int):Pitanje

    @Query("SELECT * FROM Pitanje WHERE kvizId == :kvizId")
    fun getByKvizId(kvizId:Int): List<Pitanje>

    @Query("SELECT COUNT(*) FROM Pitanje")
    fun getCount():Int
}