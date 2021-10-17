package ba.etf.rma21.projekat.data.repositories

import android.os.Build
import androidx.annotation.RequiresApi
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.api.ApiAdapter
import ba.etf.rma21.projekat.data.models.Pitanje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PitanjeKvizRepository {
    companion object {

//        suspend fun getPitanja(idKviza: Int?): List<Pitanje> {
//            return withContext(Dispatchers.IO) {
//                return@withContext ApiAdapter.retrofit.getPitanja(idKviza)
//            }
//        }

        @RequiresApi(Build.VERSION_CODES.O)
        suspend fun getPitanja(idKviza: Int?): List<Pitanje> {
            return withContext(Dispatchers.IO) {
           //     DBRepository.updateNow()
                var pitanja =  AppDatabase.getInstance(AccountRepository.getContext()).pitanjeDao().getByKvizId(idKviza!!)
                if(pitanja.isEmpty()) { // ako je baza prazna daj mi s apia
                    pitanja = ApiAdapter.retrofit.getPitanja(idKviza)
                    var id = AppDatabase.getInstance(AccountRepository.getContext()).pitanjeDao().getCount() + 1
                    for(p in pitanja) {
                        p.idZaBazu = id
                        p.kvizId = idKviza
                        AppDatabase.getInstance(AccountRepository.getContext()).pitanjeDao().insertPitanje(p)
                        id++
                    }
                }
                return@withContext pitanja
            }
        }


//        suspend fun getMojaPitanja(): List<Pitanje> {
//            return withContext(Dispatchers.IO) {
//                val kvizovi = KvizRepository.getUpisaniKvizovi()
//                val pitanja = ArrayList<Pitanje>()
//                for(k in kvizovi)
//                    pitanja.addAll(getPitanja(k.id))
//                return@withContext pitanja
//            }
//        }
        @RequiresApi(Build.VERSION_CODES.O)
        suspend fun getMojaPitanja(): List<Pitanje> {
            return withContext(Dispatchers.IO) {
                val kvizovi = KvizRepository.getUpisaniKvizoviDB()
                val pitanja = ArrayList<Pitanje>()
                for(k in kvizovi)
                    pitanja.addAll(getPitanja(k.id))
                return@withContext pitanja
            }
        }
    }
}