package ba.etf.rma21.projekat.data.dao

import androidx.room.*
import ba.etf.rma21.projekat.data.models.KvizTaken
import ba.etf.rma21.projekat.data.models.Odgovor
@Dao
interface OdgovorDAO {
    @Query("SELECT * FROM Odgovor")
    suspend fun getAll(): List<Odgovor>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOdgovor(vararg users: Odgovor)

    @Delete
    suspend fun deleteOdgovor(user: Odgovor)

//    @Update
//    suspend fun updateAccount(account: Odgovor)

    @Query("SELECT * FROM Odgovor WHERE KvizTakenId == :kvizTakenId")
    fun getOdgovoriByKvizTakenId(kvizTakenId:Int):List<Odgovor>

    @Query("SELECT COUNT(*) FROM Odgovor")
    fun getCount():Int

    @Query("DELETE  FROM Odgovor")
    fun deleteAll():Int

}