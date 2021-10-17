package ba.etf.rma21.projekat.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.KvizTaken
import ba.etf.rma21.projekat.data.models.Odgovor
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.repositories.OdgovorRepository
import ba.etf.rma21.projekat.data.repositories.TakeKvizRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class OdgovorViewModel {
    companion object {
        val scope = CoroutineScope(Job() + Dispatchers.Main)

        @RequiresApi(Build.VERSION_CODES.O)
        fun getOdgovoriZaKviz(onSuccess: (odgovori: List<Odgovor>, pitanje: Pitanje?) -> Unit, onError: (poruka: String) -> Unit, kviz: Kviz, pitanje: Pitanje?) {
            scope.launch {
                val result = OdgovorRepository.getOdgovoriKviz(kviz.id)
                when (result) {
                    is List<Odgovor> -> onSuccess.invoke(result, pitanje)
                    else -> onError.invoke("problem u getOdgovoriZaKviz OdgovorViewModel")
                }
            }
        }


        @RequiresApi(Build.VERSION_CODES.O)
        fun postaviOdgovor(onSuccess: (response: Int, odgovor: Odgovor) -> Unit, onError: (poruka: String) -> Unit, kvizTakenId: Int, pitanjeId: Int, o: Int) {
            scope.launch {
                val result = OdgovorRepository.postaviOdgovorKviz(kvizTakenId, pitanjeId, o)
                when (result) {
                    is Int -> {
                        if(!result.equals(-1)) {
                            val odgovor = Odgovor(id = 0, odgovoreno = o, pitanjeId = pitanjeId, kvizTakenId = kvizTakenId)
                            onSuccess.invoke(result, odgovor)
                        }
                    }
                    else -> onError.invoke("problem u postaviOdgovor OdgovorViewModel")
                }
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun predajOdgovore(onSuccess: () -> Unit, kvizId: Int) {
            scope.launch {
                OdgovorRepository.predajOdgovore(kvizId)
                onSuccess.invoke()
            }
        }
    }
}