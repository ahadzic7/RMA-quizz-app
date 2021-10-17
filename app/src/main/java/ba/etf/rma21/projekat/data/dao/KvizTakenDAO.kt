package ba.etf.rma21.projekat.data.dao

import androidx.room.*
import ba.etf.rma21.projekat.data.models.Account
import ba.etf.rma21.projekat.data.models.KvizTaken

@Dao
interface KvizTakenDAO {
    @Query("SELECT * FROM KvizTaken")
    suspend fun getAll(): List<KvizTaken>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKvizTaken(vararg users: KvizTaken)

    @Delete
    suspend fun deleteAccount(user: KvizTaken)

    @Update
    suspend fun updateAccount(account: KvizTaken)

    @Query("SELECT * FROM KvizTaken WHERE KvizId == :kvizId")
    fun getByKvizId(kvizId:Int): KvizTaken?

    @Query("DELETE FROM KvizTaken")
    suspend fun deleteAll()

}