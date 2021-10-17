package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.api.ApiAdapter
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Predmet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PredmetIGrupaRepository {
    companion object {
        val hashStudenta = AccountRepository.acHash

        private lateinit var context: Context
        fun setContext(_context: Context) { context = _context }

        suspend fun getPredmeti(): List<Predmet> {
            return  withContext(Dispatchers.IO) {
                return@withContext ApiAdapter.retrofit.getAllPredmeti()
            }
        }

        suspend fun getUpisaniPredmeti(): List<Predmet> {
            val predmeti = getPredmeti()
            val upisaneGrupe = getUpisaneGrupe()
            val upisaniPredmeti = ArrayList<Predmet>()
            for(ug in upisaneGrupe) {
                for(p in predmeti) {
                    if(ug.predmetId!!.equals(p.id))
                        upisaniPredmeti.add(p)
                }
            }
            return upisaniPredmeti
        }

        @RequiresApi(Build.VERSION_CODES.O)
        suspend fun getUpisaniPredmetiDB(): List<Predmet> {
            return withContext(Dispatchers.IO) {
        //        DBRepository.updateNow()
                val upisaniPredmeti = AppDatabase.getInstance(AccountRepository.getContext()).predmetDao().getAll()

                return@withContext upisaniPredmeti
            }
        }

        suspend fun getPredmetZaId(id:Int) : Predmet {
            return withContext(Dispatchers.IO) {
                return@withContext ApiAdapter.retrofit.getPredmetId(id)
            }
        }


        suspend fun getGrupe(): List<Grupa> {
            return withContext(Dispatchers.IO) {
                return@withContext ApiAdapter.retrofit.getAllGrupe()
            }
        }

        suspend fun getGrupeZaPredmet(idPredmeta: Int): List<Grupa> {
            return withContext(Dispatchers.IO) {
                return@withContext ApiAdapter.retrofit.getGrupeZaPredmet(idPredmeta)
            }
        }

        suspend fun upisiUGrupu(idGrupa: Int): Boolean {
            return withContext(Dispatchers.IO) {
                val x= AccountRepository.getHash()
                val response =  ApiAdapter.retrofit.upisiNaGrupu(idGrupa, x)
                return@withContext !response.message.equals("Grupa not found.")
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        suspend fun upisiUGrupuDB(idGrupa: Int): Boolean {
            return withContext(Dispatchers.IO) {
                val x= AccountRepository.getHash()
                val response =  ApiAdapter.retrofit.upisiNaGrupu(idGrupa, x)
                if(response.message.equals("Grupa not found.")) return@withContext false

            //    DBRepository.updateNow()

                return@withContext true
            }
        }

        suspend fun getUpisaneGrupe(): List<Grupa> {
            return withContext(Dispatchers.IO) {
                val x= AccountRepository.getHash()
                return@withContext ApiAdapter.retrofit.getUpisaneGrupe(x)
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        suspend fun getUpisaneGrupeDB(): List<Grupa> {
            return withContext(Dispatchers.IO) {
            //    DBRepository.updateNow()

                val x= AccountRepository.getHash()
                return@withContext ApiAdapter.retrofit.getUpisaneGrupe(x)
            }
        }
    }
}