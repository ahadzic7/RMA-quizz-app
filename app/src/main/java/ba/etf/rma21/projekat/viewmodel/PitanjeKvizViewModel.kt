package ba.etf.rma21.projekat.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Odgovor
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.repositories.OdgovorRepository
import ba.etf.rma21.projekat.data.repositories.PitanjeKvizRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PitanjeKvizViewModel {
    val scope = CoroutineScope(Job() + Dispatchers.Main)

    @RequiresApi(Build.VERSION_CODES.O)
    fun getPitanja(onSuccess: (pitanja: List<Pitanje>, kviz : Kviz, odgovori: List<Odgovor>) -> Unit, onError: (poruka: String) -> Unit, kviz: Kviz) {
        scope.launch {
            val pitanja = PitanjeKvizRepository.getPitanja(kviz.id)
            when(pitanja) {
                is List<Pitanje> -> {
                    val odgovori = OdgovorRepository.getOdgovoriKviz(kviz.id)
                    onSuccess.invoke(pitanja, kviz, odgovori)
                }
                else -> if (onError != null) {
                    onError.invoke("problem u getPitanja PitanjeViewModel")
                }
            }
        }
    }
}