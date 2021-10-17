package ba.etf.rma21.projekat.viewmodel

import android.content.Context
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.repositories.AccountRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AccountViewModel {
    companion object {
        val scope = CoroutineScope(Job() + Dispatchers.Main)

        fun postaviAccount(onSuccess: () -> Unit, acHash: String, context: Context) {
            scope.launch {
                AccountRepository.setContext(context)
                AccountRepository.postaviHash(acHash)
                onSuccess.invoke()
            }
        }
    }
}