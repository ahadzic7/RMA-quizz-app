package ba.etf.rma21.projekat.data.repositories

import android.annotation.SuppressLint
import android.content.Context
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.api.ApiAdapter
import ba.etf.rma21.projekat.data.models.Account
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class AccountRepository {
    companion object {
        var acHash: String? = null

        private lateinit var context: Context

        fun setContext(_context:Context){
            context = _context
            DBRepository.setContext(context)
            OdgovorRepository.setContext(context)
            PredmetIGrupaRepository.setContext(context)
        }

        fun getContext() = context

        suspend fun postaviHash(acHash: String): Boolean {
            if (this.acHash == null || !this.acHash.equals(acHash)) {
                this.acHash = acHash
                withContext(Dispatchers.IO) {
                    val account  = AppDatabase.getInstance(context).accountDao().getAccount()
                    if(account == null) {
                        if(acHash.length == 0)
                            AppDatabase.getInstance(context).accountDao().insertAccount(Account(0, "student", acHash, getDateFormat(Date())!!, null))
                        else {
                            val x = acHash
                            val newAccount = ApiAdapter.retrofit.getAccount(acHash)
                            if (newAccount.message == null) {
                                AppDatabase.getInstance(context).accountDao().insertAccount(newAccount)
                            }
                            else
                                AppDatabase.getInstance(context).accountDao().insertAccount(Account(0, "student", acHash, getDateFormat(Date())!!, null))
                        }
                    }
                    else if(!account.acHash.equals(acHash)) {
                        deleteData(account)
                        val newAccount = ApiAdapter.retrofit.getAccount(acHash)
                        if(newAccount.message == null)
                            AppDatabase.getInstance(context).accountDao().insertAccount(newAccount)
                        else
                            AppDatabase.getInstance(context).accountDao().insertAccount(Account(0, "student", acHash, getDateFormat(Date())!!, null))
                    }
                }
                return false
            }
            return true
        }

        private suspend fun  deleteData(account: Account) {
            AppDatabase.getInstance(context).accountDao().deleteAccount(account)
            AppDatabase.getInstance(context).grupaDao().deleteAll()
            AppDatabase.getInstance(context).predmetDao().deleteAll()
            AppDatabase.getInstance(context).kvizDao().deleteAll()
            AppDatabase.getInstance(context).odgovorDao().deleteAll()
            AppDatabase.getInstance(context).kvizTakenDao().deleteAll()
        }

        @SuppressLint("SimpleDateFormat")
        private fun getDateFormat(date: Date): String? {
            val format = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss")
            return format.format(date)
        }


        fun getHash(): String  {
            if(acHash == null) return "c7483b55-e746-464e-86e9-9b232b174c0b"
            return acHash!!
        }
    }
}