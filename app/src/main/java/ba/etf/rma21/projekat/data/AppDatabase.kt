package ba.etf.rma21.projekat.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ba.etf.rma21.projekat.data.dao.*
import ba.etf.rma21.projekat.data.models.*

@Database(entities = arrayOf(Account::class, Grupa::class, Kviz::class, Pitanje::class, Predmet::class, Odgovor::class, KvizTaken::class), version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDAO
    abstract fun grupaDao(): GrupaDAO
    abstract fun kvizDao(): KvizDAO
    abstract fun pitanjeDao(): PitanjeDAO
    abstract fun predmetDao(): PredmetDAO
    abstract fun odgovorDao(): OdgovorDAO
    abstract fun kvizTakenDao(): KvizTakenDAO

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun setInstance(appdb:AppDatabase) {
            INSTANCE=appdb
        }
        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = buildRoomDB(context)
                }
            }
            return INSTANCE!!
        }
        private fun buildRoomDB(context: Context)
                = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "RMA21DB").build()
    }
}