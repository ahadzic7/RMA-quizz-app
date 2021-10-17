package ba.etf.rma21.projekat.viewmodel

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import ba.etf.rma21.projekat.data.repositories.AccountRepository
import ba.etf.rma21.projekat.data.repositories.DBRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DBViewModel {
    companion object {
        val scope = CoroutineScope(Job() + Dispatchers.Main)

        @RequiresApi(Build.VERSION_CODES.O)
        fun updateNow(onSuccess: (response: Boolean?) -> Unit) {
            scope.launch {
                val response = DBRepository.updateNow()
                onSuccess.invoke(response)
            }
        }
    }
}