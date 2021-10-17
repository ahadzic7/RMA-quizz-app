package ba.etf.rma21.projekat.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.KvizTaken
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import ba.etf.rma21.projekat.data.repositories.TakeKvizRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TakeKvizViewModel {
    companion object {
        val scope = CoroutineScope(Job() + Dispatchers.Main)

        @RequiresApi(Build.VERSION_CODES.O)
        fun zapocniKviz(onSuccess: (pokusaj: KvizTaken) -> Unit, onError: (poruka: String) -> Unit, kviz: Kviz) {
            scope.launch {
                val result = TakeKvizRepository.zapocniKviz(kviz.id)
                when (result) {
                    is KvizTaken -> onSuccess.invoke(result)
                    else -> onError.invoke("problem u zapocniKviz TakeKvizViewModel")
                }
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun getPocetiKvizovi(onSuccess: (pocetiKvizovi: List<KvizTaken>) -> Unit, onError: (poruka: String) -> Unit) {
            scope.launch {
                val result = TakeKvizRepository.getPocetiKvizovi()
                when (result) {
                    is List<KvizTaken> -> onSuccess.invoke(result)
                    else -> onError.invoke("problem u getPocetiKvizovi TakeKvizViewModel")
                }
            }
        }
    }
}