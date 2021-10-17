package ba.etf.rma21.projekat.data.repositories

import android.os.Build
import androidx.annotation.RequiresApi
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.api.ApiAdapter
import ba.etf.rma21.projekat.data.models.KvizTaken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TakeKvizRepository {
    companion object {
        val hashStudenta = AccountRepository.acHash

//        suspend fun zapocniKviz(idKviza: Int?): KvizTaken? {
//            val poceti = getPocetiKvizovi()
//            if(poceti != null) { //u slucaju da vec postoji
//                val pokusaj = poceti.find { kvizTaken -> kvizTaken.KvizId.equals(idKviza) }
//                if (pokusaj != null)
//                    return pokusaj
//            }
//            val pokusaj = withContext(Dispatchers.IO) {
//                val x= AccountRepository.getHash()
//                return@withContext ApiAdapter.retrofit.pokusaj(x, idKviza)
//            }
//            if(pokusaj?.id == null)
//                return null
//            return pokusaj
//        }

        @RequiresApi(Build.VERSION_CODES.O)
        suspend fun zapocniKviz(idKviza: Int?): KvizTaken? {
            val poceti = getPocetiKvizovi()
            if(poceti != null) { //u slucaju da vec postoji
                val pokusaj = poceti.find { kvizTaken -> kvizTaken.KvizId.equals(idKviza) }
                if (pokusaj != null)
                    return pokusaj
            }
            val pokusaj = withContext(Dispatchers.IO) {
                val x= AccountRepository.getHash()
                val pokusaj = ApiAdapter.retrofit.pokusaj(x, idKviza)
                if(pokusaj?.id == null)
                    return@withContext null
                AppDatabase.getInstance(AccountRepository.getContext()).kvizTakenDao().insertKvizTaken(pokusaj!! )
                return@withContext pokusaj
            }
            if(pokusaj == null)
                return null
            return pokusaj
        }

//        suspend fun getPocetiKvizovi(): List<KvizTaken>? {
//            val pokusaji = withContext(Dispatchers.IO) {
//                val x= AccountRepository.getHash()
//                return@withContext ApiAdapter.retrofit.getPokusaji(x)
//            }
//            if (pokusaji.isEmpty())
//                return null
//            return pokusaji
//        }

        @RequiresApi(Build.VERSION_CODES.O)
        suspend fun getPocetiKvizovi(): List<KvizTaken>? {
            val pokusaji = withContext(Dispatchers.IO) {
                var kt = AppDatabase.getInstance(AccountRepository.getContext()).kvizTakenDao().getAll()
                if(kt.isEmpty()) {
                    kt = ApiAdapter.retrofit.getPokusaji(AccountRepository.getHash())
                }
                return@withContext kt
            }
            if (pokusaji.isEmpty())
                return null
            return pokusaji
        }
    }
}