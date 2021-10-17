package ba.etf.rma21.projekat.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import ba.etf.rma21.projekat.data.repositories.OdgovorRepository
import ba.etf.rma21.projekat.data.repositories.PitanjeKvizRepository
import ba.etf.rma21.projekat.data.repositories.TakeKvizRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class KvizViewModel() {

    val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun getAll(onSuccess: (kvizovi: List<Kviz>) -> Unit, onError: (poruka: String) -> Unit) {
        scope.launch {
            val result = KvizRepository.getAll()
            when(result) {
                is List<Kviz> -> onSuccess.invoke(result)
                else -> if (onError != null) {
                    onError.invoke("problem u getAll KvizViewModel")
                }
            }
        }
    }



    @RequiresApi(Build.VERSION_CODES.O)
    fun getMyKvizes(onSuccess: (kvizovi: List<Kviz>) -> Unit, onError: (poruka: String) -> Unit) {
        scope.launch {
            val upisaniKvizovi = KvizRepository.getUpisaniKvizoviDB()
            when(upisaniKvizovi) {
                is List<Kviz> ->  {
                    val pokusaniKvizovi = TakeKvizRepository.getPocetiKvizovi()
                    if(pokusaniKvizovi == null)
                        onSuccess.invoke(upisaniKvizovi)
                    else {
                        val result = ArrayList<Kviz>()
                        for(kviz in upisaniKvizovi) {
                            for(pokusani in pokusaniKvizovi) {

                                if(kviz.id?.equals(pokusani.KvizId)!!) {
                                    val pitanja = PitanjeKvizRepository.getPitanja(kviz.id)
                                    val odgovori = OdgovorRepository.getOdgovoriKviz(kviz.id)

                                    if(!pitanja.size.equals(odgovori.size))
                                        continue

                                        kviz.datumRada = pokusani.datumRada
                                }


                            }
                            result.add(kviz)
                        }
                        onSuccess.invoke(result)
                    }

                }
                else -> if (onError != null) {
                    onError.invoke("problem u getMyKvizes KvizViewModel")
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDone(onSuccess: (kvizovi: List<Kviz>) -> Unit, onError: (poruka: String) -> Unit) {
        scope.launch {
            val result = KvizRepository.getDoneDB()
            when(result) {
                is List<Kviz> -> onSuccess.invoke(result)
                else -> if (onError != null) {
                    onError.invoke("")
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getFuture(onSuccess: (kvizovi: List<Kviz>) -> Unit, onError: (poruka: String) -> Unit) {
        scope.launch {
            val result = KvizRepository.getFutureDB()
            when(result) {
                is List<Kviz> -> onSuccess.invoke(result)
                else -> if (onError != null) {
                    onError.invoke("")
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getNotTaken(onSuccess: (kvizovi: List<Kviz>) -> Unit, onError: (poruka: String) -> Unit) {
        scope.launch {
            val result = KvizRepository.getNotTaken()
            when(result) {
                is List<Kviz> -> onSuccess.invoke(result)
                else -> if (onError != null) {
                    onError.invoke("")
                }
            }
        }
    }

}