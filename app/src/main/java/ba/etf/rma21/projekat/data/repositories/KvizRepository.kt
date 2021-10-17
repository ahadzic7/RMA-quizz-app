package ba.etf.rma21.projekat.data.repositories

import android.os.Build
import androidx.annotation.RequiresApi
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.*
import ba.etf.rma21.projekat.data.api.ApiAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList


class KvizRepository {

    companion object {

        suspend fun getAll(): List<Kviz> {
            return withContext(Dispatchers.IO) {

                val pom = ApiAdapter.retrofit.getAllKvizes()

                val result = mutableListOf<Kviz>()

                for(kviz in pom) {
                    val pom1 = ApiAdapter.retrofit.getGrupeZaKviz(kviz.id!!)
                    val kvizZUbaciti = kviz
                    val listaNaziva = mutableListOf<String>()
                    for(grupa in pom1) {
                        val naziv = ApiAdapter.retrofit.getPredmetId(grupa.predmetId!!).naziv
                        if(!listaNaziva.contains(naziv)) {
                            if(kvizZUbaciti.nazivPredmeta == null) {
                                kvizZUbaciti.nazivPredmeta = naziv
                            }
                            else kvizZUbaciti.nazivPredmeta += "," + naziv
                            listaNaziva.add(naziv)
                        }
                    }
                    result.add(kvizZUbaciti)
                }
                return@withContext result
            }
        }

        suspend fun getById(id:Int): Kviz? {
            return withContext(Dispatchers.IO) {
                val response = ApiAdapter.retrofit.getKvizZaId(id)
                if(response.id == null)
                    return@withContext null
                return@withContext response
            }

        }

        private fun kvizToKviz(fakeKvizovi: List<Kviz>, realKvizovi: List<Kviz>): List<Kviz> {
            val kvizovi = ArrayList<Kviz>()
            for(kviz in fakeKvizovi) {
                for(k in realKvizovi) {
                    if(kviz.id?.equals(k.id)!!)
                        kvizovi.add(k)
                }
            }
            return kvizovi
        }

        suspend fun getUpisaniKvizovi():List<Kviz> {
            return withContext(Dispatchers.IO) {
                val upisaneGrupe = PredmetIGrupaRepository.getUpisaneGrupe()
                val kvizoviZaUpisaneGrupe = arrayListOf<Kviz>()
                for(grupa in upisaneGrupe)
                    kvizoviZaUpisaneGrupe.addAll(ApiAdapter.retrofit.getKvizoveZaGrupu(grupa.id!!))

                val sviKvizovi = getAll()
                val result = kvizToKviz(kvizoviZaUpisaneGrupe, sviKvizovi)


                return@withContext result
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        suspend fun getUpisaniKvizoviDB():List<Kviz> {
            return withContext(Dispatchers.IO) {
            //    DBRepository.updateNow()
                val kvizovi = AppDatabase.getInstance(AccountRepository.getContext()).kvizDao().getAll()
                return@withContext kvizovi
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        suspend fun getDoneDB():List<Kviz> {
            return withContext(Dispatchers.IO) {
             //   DBRepository.updateNow()
                val kvizovi = AppDatabase.getInstance(AccountRepository.getContext()).kvizDao().getAll()
                return@withContext kvizovi
            }
        }

        private suspend fun takenToKviz(pokusaji:List<KvizTaken>):List<Kviz> {
            val sviKvizovi = getUpisaniKvizovi()
            val pokusaniKvizovi = ArrayList<Kviz>()

            for(kviz in sviKvizovi) {
                for(pokusaj in pokusaji) {
                    if (kviz.id?.equals(pokusaj.KvizId)!!) {
                        kviz.osvojeniBodovi = pokusaj.osvojeniBodovi?.toFloat()
                        kviz.datumRada = pokusaj.datumRada
                        pokusaniKvizovi.add(kviz)
                    }
                }
            }

            return pokusaniKvizovi
        }

        @RequiresApi(Build.VERSION_CODES.O)
        suspend fun getDone():List<Kviz> {
            return withContext(Dispatchers.IO) {
                val pokusajiKvizova = TakeKvizRepository.getPocetiKvizovi()
                if(pokusajiKvizova == null)
                    return@withContext emptyList<Kviz>()

                val kvizovi =  takenToKviz(pokusajiKvizova) as ArrayList
                val pom = ArrayList<Kviz>()
                for(k in kvizovi) {
                    val odgovori = OdgovorRepository.getOdgovoriKviz(k.id)
                    for(o in odgovori) {
                        if(o.odgovoreno?.equals(-1)!!)
                        {
                            pom.add(k)
                        }
                    }
                }
                kvizovi.removeAll(pom)
                return@withContext kvizovi
            }
        }

        suspend fun getFuture():List<Kviz> {
            return withContext(Dispatchers.IO) {
                val kvizovi = getUpisaniKvizovi()
                val danas = Date()
                return@withContext kvizovi.filter { kviz -> stringToDate(kviz.datumPocetka)!!.after(danas) }.toCollection(arrayListOf())
            }
        }

        private fun stringToDate(value: String?): Date? {
            return if (value == null) null
            else Date(value)
        }

        private fun notTakenToKviz(pokusaji:List<KvizTaken>?, kvizovi : List<Kviz>):List<Kviz> {
            if(pokusaji == null)
                return kvizovi

            val nepokusaniKvizovi = ArrayList<Kviz>()
            for(kviz in kvizovi) {
                for(pokusaj in pokusaji) {
                    if(kviz.id?.equals(pokusaj.KvizId)!!) {
                        break
                    }
                }
                nepokusaniKvizovi.add(kviz)
            }

            return nepokusaniKvizovi
        }
        @RequiresApi(Build.VERSION_CODES.O)
        suspend fun getNotTaken():List<Kviz> {
            return withContext(Dispatchers.IO) {
                val pokusajiKvizova = TakeKvizRepository.getPocetiKvizovi()
                val upisaniKvizovi = getUpisaniKvizoviDB()


                val kvizovi =  notTakenToKviz(pokusajiKvizova, upisaniKvizovi)
                val danas = Date()
                val kv = ArrayList<Kviz>()

                for(k in kvizovi) {
                    val kraj = stringToDate(k.datumKraj)
                    if(kraj != null)
                    if(kraj.before(danas))
                        kv.add(k)
                }
                return@withContext kv

            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        suspend fun getFutureDB(): List<Kviz> {
            return withContext(Dispatchers.IO) {
                val kvizovi = getUpisaniKvizoviDB()
                val danas = Date()
                return@withContext kvizovi.filter { kviz -> stringToDate(kviz.datumPocetka)!!.after(danas) }.toCollection(arrayListOf())
            }
        }

    }
}