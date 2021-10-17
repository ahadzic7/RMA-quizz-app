package ba.etf.rma21.projekat.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.repositories.old.GrupaRepository
import ba.etf.rma21.projekat.data.repositories.PredmetIGrupaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GrupaListViewModel {

    companion object { fun newInstance(): GrupaListViewModel = GrupaListViewModel() }
    val scope = CoroutineScope(Job() + Dispatchers.Main)


    fun upisiGrupu(grupa: Grupa) {
        GrupaRepository.upisiGrupu(grupa)
    }

    fun getUpisaneGrupe(nazivPredmeta: String): ArrayList<Grupa> {
        return GrupaRepository.getUpisaneGrupe(nazivPredmeta)
    }

    fun getAll() = GrupaRepository.getAll()



    @RequiresApi(Build.VERSION_CODES.O)
    fun upisiGrupu(onSuccess: () -> Unit, onError: (poruka: String) -> Unit, grupa: Grupa) {
        scope.launch {
            if(PredmetIGrupaRepository.upisiUGrupuDB(grupa.id)) {
                onSuccess.invoke()
            }
            else
                onError.invoke("greska u upisiGrupu, GrupaListViewModel")
        }
    }
}