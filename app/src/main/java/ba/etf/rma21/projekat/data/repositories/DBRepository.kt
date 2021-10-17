package ba.etf.rma21.projekat.data.repositories

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.api.ApiAdapter
import ba.etf.rma21.projekat.data.dao.KvizTakenDAO
import ba.etf.rma21.projekat.data.models.Account
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class DBRepository {
    companion object {
        private var hashStudenta = "c7483b55-e746-464e-86e9-9b232b174c0b"
        private lateinit var context: Context
        fun setContext(_context: Context) { context = _context }

        @SuppressLint("SimpleDateFormat")
        private fun getDateFormat(date: Date): String? {
            val format = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss")
            return format.format(date)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        suspend fun updateNow(): Boolean {
            return withContext(Dispatchers.IO) {
                var account  = AppDatabase.getInstance(AccountRepository.getContext()).accountDao().getAccount()
                if(account == null) {
                    account = ApiAdapter.retrofit.getAccount(hashStudenta)
                    AccountRepository.postaviHash(hashStudenta)
        //                  account = AppDatabase.getInstance(AccountRepository.getContext()).accountDao().getAccount()
                    updateData(account)
        //                  return@withContext false
                }
                val d = account.lastUpdate
                val x = getDateFormat(Date(0,0,0))!!
                val response =  ApiAdapter.retrofit.isChanged(account.acHash, d)
                if(response.message == null) {
                    if(response.changed!!)
                        updateData(account)
                    return@withContext response.changed
                }
                //ne postoji student sa tim hashom
                return@withContext false
            }!!
        }


        suspend fun updateData(account: Account) {
            val update = updateAccount(account)
            if(!update) return
            deleteData()
            insertData()
        }

        private suspend fun  deleteData() {
            AppDatabase.getInstance(AccountRepository.getContext()).grupaDao().deleteAll()
            AppDatabase.getInstance(AccountRepository.getContext()).predmetDao().deleteAll()
            AppDatabase.getInstance(AccountRepository.getContext()).kvizDao().deleteAll()
            AppDatabase.getInstance(AccountRepository.getContext()).kvizTakenDao().deleteAll()
        }

        private suspend fun updateAccount(account: Account?): Boolean {
            if(account != null) {
                AppDatabase.getInstance(AccountRepository.getContext()).accountDao().deleteAccount(account)
                val newAccount = Account(account.id, account.student, account.acHash, getDateFormat(Date())!!, null)
                AppDatabase.getInstance(AccountRepository.getContext()).accountDao().insertAccount(newAccount)
                return true
            }
            return false
        }

        @RequiresApi(Build.VERSION_CODES.O)
        private suspend fun insertData() {
            val upisaneGrupe = ApiAdapter.retrofit.getUpisaneGrupe(AccountRepository.getHash())
            val predmeti = PredmetIGrupaRepository.getUpisaniPredmeti()
            val kvizovi = KvizRepository.getUpisaniKvizovi()
            val pitanja = PitanjeKvizRepository.getMojaPitanja()
            val pokusaji = ApiAdapter.retrofit.getPokusaji(AccountRepository.getHash())

            for(ug in upisaneGrupe)
                AppDatabase.getInstance(AccountRepository.getContext()).grupaDao().insertGrupu(ug)
            for(p in predmeti)
                AppDatabase.getInstance(AccountRepository.getContext()).predmetDao().insertPredmet(p)
            for(k in kvizovi)
                AppDatabase.getInstance(AccountRepository.getContext()).kvizDao().insertKviz(k)
            for(p in pitanja)
                AppDatabase.getInstance(AccountRepository.getContext()).pitanjeDao().insertPitanje(p)
//            if(pokusaji != null)
            for (kt in pokusaji)
                AppDatabase.getInstance(AccountRepository.getContext()).kvizTakenDao().insertKvizTaken(kt)


        }


    }

}
