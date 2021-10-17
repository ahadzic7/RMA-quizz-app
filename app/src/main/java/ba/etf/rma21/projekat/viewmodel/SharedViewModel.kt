package ba.etf.rma21.projekat.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.KvizTaken
import ba.etf.rma21.projekat.data.models.Odgovor
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.repositories.PitanjeKvizRepository
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.math.roundToInt

class SharedViewModel : ViewModel() {
    //spasava odabrana stanja za upis predmeta
    var cashGodina : Int? = null
    var predmetPozicija : Int? = null
    var grupaPozicija : Int? = null

    fun cashirajGodinu(item: Int) { cashGodina = item }
    fun cashirajPredmet(item: String?, position: Int?) {
        if(item.equals("-")) {
            predmetPozicija = null
            return
        }
        predmetPozicija = position
    }
    fun cashirajGrupu(item: String?, position: Int?) {
        if(item.equals("-")) {
            grupaPozicija = null
            return
        }
        grupaPozicija = position
    }

    var predanKviz:Boolean = false


//    //mapa da cuva odgovorena pitanja
//    private var pitanjeOdabir : HashMap<Pitanje, Int> = HashMap()
//    fun addPitanjeOdabir(pitanje: Pitanje, odgovor: Int) { pitanjeOdabir.put(pitanje, odgovor) }
//    fun getOdabir(pitanje: Pitanje) : Int? = pitanjeOdabir.get(pitanje)
//    fun clearPitanjaOdbir() { pitanjeOdabir.clear() }

    //kada odgovorimo na list view da oboji iteme na navigaciji pitanja
    var tacanOdgovor = MutableLiveData<Boolean?>()
    fun setTacanOdgovor(item: Boolean) {
        tacanOdgovor.value = item
    }
    fun clearTacanOdgovor() { tacanOdgovor.value = null }

    //za klikanje na predaj kviz da nam prikaze fragment poruka
    var predatKviz = MutableLiveData<Boolean>()

    fun predatKviz(item: Boolean) {
        if(predatKviz.value == null) {
            predatKviz.value = item
            tacanOdgovor = MutableLiveData()
        }
    }
    fun clearPredatKviz() { predatKviz = MutableLiveData() }

}