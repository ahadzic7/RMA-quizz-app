package ba.etf.rma21.projekat.viewmodel.old

import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.repositories.KvizRepository

class KvizListViewModel {

    companion object { fun newInstance(): KvizListViewModel = KvizListViewModel() }


    suspend fun getKvizove():List<Kviz>{
        return KvizRepository.getAll()
    }

//     fun getDone():List<Kviz>{
//        return KvizRepository.getDone()
//    }
//     fun getFuture():List<Kviz>{
//        return KvizRepository.getFuture()
//    }
//     fun getNotTaken():List<Kviz>{
//        return KvizRepository.getNotTaken()
//    }
//    fun getMyKvizes():List<Kviz>{
//        return KvizRepository.getMyKvizes()
//    }
//     fun getPastNotTaken(): List<Kviz> {
//        return KvizRepository.getPastNotTaken()
//    }
//
//    fun getMyDoneKvizes(): List<Kviz> = KvizRepository.getMyDoneKvizes()
//
//    fun getMyFutureKvizes(): List<Kviz> = KvizRepository.getMyFutureKvizes()
//
//    fun getMyNotTakenKvizes(): List<Kviz> = KvizRepository.getMyNotTakenKvizes()
//
//    fun getMyPastNotTakenKvizes(): List<Kviz> = KvizRepository.getMyPastNotTakenKvizes()
//    suspend fun getAll() = KvizRepository.getAll()
}