package ba.etf.rma21.projekat.viewmodel


import android.os.Build
import androidx.annotation.RequiresApi
import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.data.repositories.PredmetIGrupaRepository
import ba.etf.rma21.projekat.data.repositories.old.PredmetRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ba.etf.rma21.projekat.data.models.*


class PredmetListViewModel {

    companion object { fun newInstance(): PredmetListViewModel = PredmetListViewModel() }
    val scope = CoroutineScope(Job() + Dispatchers.Main)


//    fun getUpisani(): List<Predmet> {
//        return PredmetRepository.getUpisani()
//    }

//    fun getAll(): List<Predmet> {
//        return PredmetRepository.getAll()
//    }

    fun upisiPredmet(noviPredmet: Predmet) {
        PredmetRepository.upisiPredmet(noviPredmet)
    }

//    fun getPredmeteZaGodinu(godina: Int): ArrayList<Predmet> {
//        return PredmetRepository.getZaGodinu(godina)
//    }

    fun getAll(onSuccess: (kvizovi: List<Predmet>, tipKviza:Int) -> Unit, onError: () -> Unit) {
        scope.launch {
            val result = PredmetIGrupaRepository.getPredmeti()
            when(result) {
                is List<Predmet> -> onSuccess.invoke(result, 0)
                else -> if (onError != null) {
                    onError.invoke()
                }
            }
        }
    }

    suspend fun getPredmetByGodina(godina: Int): List<Predmet> {

        var predmeti:List<Predmet> = PredmetIGrupaRepository.getPredmeti()
        predmeti = predmeti.filter { predmet -> predmet.godina == godina }

        return predmeti
    }



    fun getAll():List<Predmet>{
        lateinit var predmeti :List<Predmet>
        scope.launch {
            predmeti = PredmetIGrupaRepository.getPredmeti()
        }
        return predmeti
    }





    fun getPredmeteZaGodinu(godina: Int): List<Predmet> {
        val predmetiZaGodinu = mutableListOf<Predmet>()
        scope.launch {
            val predmeti = PredmetIGrupaRepository.getPredmeti()
            for (predmet in predmeti) {
                if(predmet.godina.equals(godina))
                    predmetiZaGodinu.add(predmet)
            }
        }

        return predmetiZaGodinu
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDataZaOdabranuGodinu(onSuccess: (predmeti: List<Predmet>, imenaPredmeta: List<String>, grupe: List<Grupa>, imenaGrupa: List<String?>) -> Unit,
                                onError: (poruka: String) -> Unit, godina: Int) {
        scope.launch {
            val predmetiZaGodinu = PredmetIGrupaRepository.getPredmeti().filter { predmet -> predmet.godina.equals(godina) }.toCollection(arrayListOf())
            if(predmetiZaGodinu is ArrayList<Predmet>) {

                val upisaneGrupe = PredmetIGrupaRepository.getUpisaneGrupeDB()
                val neupisaniPredmeti = ArrayList<Predmet>()

                if(upisaneGrupe.isEmpty())
                    neupisaniPredmeti.addAll(predmetiZaGodinu)

                for(predmet in predmetiZaGodinu) {
                    for(grupa in upisaneGrupe) {
                        if(predmet.id?.equals(grupa.predmetId)!!) {
                            continue
                        }
                        neupisaniPredmeti.add(predmet)
                    }
                }


                if(!neupisaniPredmeti.isEmpty()) {
                    val grupeZaPredmet = PredmetIGrupaRepository.getGrupeZaPredmet(neupisaniPredmeti[0].id!!)
                    if(predmetiZaGodinu is ArrayList<Predmet>) {
                        val imenaPredmeta = neupisaniPredmeti.map { predmet -> predmet.naziv }.toCollection(arrayListOf())
                        imenaPredmeta.add(0, "-")
                        val imenaGrupa = grupeZaPredmet.map { grupa -> grupa.naziv }.toCollection(arrayListOf())
                        imenaGrupa.add(0, "-")
                        onSuccess.invoke(predmetiZaGodinu, imenaPredmeta, grupeZaPredmet, imenaGrupa)
                    }
                    else
                        onError.invoke("Ne radi getGrupe u getInitalInfo")

                }
            }
            else
                onError.invoke("Ne radi getPredmeti u getInitalInfo")
        }
    }

    fun getDataZaOdabranPredmet(onSuccess: (grupe: List<Grupa>, imenaGrupa: List<String?>) -> Unit, onError: (poruka: String) -> Unit, predmet: Predmet) {
        scope.launch {
            val grupeZaPredmet = PredmetIGrupaRepository.getGrupeZaPredmet(predmet.id!!)
            if(grupeZaPredmet is List<Grupa>) {
                val imenaGrupa = grupeZaPredmet.map { grupa -> grupa.naziv }.toCollection(arrayListOf())
                imenaGrupa.add(0, "-")
                onSuccess.invoke(grupeZaPredmet, imenaGrupa)
            }
            else
                onError.invoke("Ne radi getGrupe u getInitalInfo")

        }
    }
}





