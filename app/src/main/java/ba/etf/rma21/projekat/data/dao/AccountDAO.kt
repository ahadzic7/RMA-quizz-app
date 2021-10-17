package ba.etf.rma21.projekat.data.dao

import androidx.room.*
import ba.etf.rma21.projekat.data.models.Account

@Dao
interface AccountDAO {
    @Query("SELECT * FROM Account")
    suspend fun getAccount(): Account?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(vararg users: Account)

    @Delete
    suspend fun deleteAccount(user: Account)

    @Update
    suspend fun updateAccount(account: Account)

//    @Query("UPDATE Account SET lastUpdate = )
//    suspend fun updateLastUpdate(string: String)
}

//UPDATE table
//SET
//    column1 = new_value1,
//    column2 = new_value2,
//    ...
//WHERE
//    condition;