package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.api.ApiAdapter
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Odgovor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.*
import kotlin.math.roundToInt

class OdgovorRepository {
    companion object {
        val hashStudenta = AccountRepository.acHash

        private lateinit var context: Context
        fun setContext(_context: Context) { context = _context }

//        suspend fun getOdgovoriKviz(idKviza: Int?): List<Odgovor> {
//            if(idKviza == null)
//                return emptyList()
//
//            val poceti = TakeKvizRepository.getPocetiKvizovi()
//
//            if(poceti == null) return emptyList()
//
//            if(poceti.isEmpty()) return emptyList()
//
//            val kt = poceti.filter { kvizTaken -> kvizTaken.KvizId.equals(idKviza) }.firstOrNull()
//            if(kt == null) return emptyList()
//
//            val id = kt.id
//            if(id == null)
//                return emptyList()
//            return withContext(Dispatchers.IO) {
//                return@withContext ApiAdapter.retrofit.getOdgovoriZaPokusaj(hashStudenta, id)
//            }
//        }

        @RequiresApi(Build.VERSION_CODES.O)
        suspend fun getOdgovoriKviz(idKviza: Int?): List<Odgovor> {
            if(idKviza == null)
                return emptyList()

            val poceti = TakeKvizRepository.getPocetiKvizovi()

            if(poceti == null) return emptyList()

            if(poceti.isEmpty()) return emptyList()

            val kt = poceti.filter { kvizTaken -> kvizTaken.KvizId.equals(idKviza) }.firstOrNull()
            if(kt == null) return emptyList()


            return withContext(Dispatchers.IO) {
                return@withContext AppDatabase.getInstance(AccountRepository.getContext()).odgovorDao().getOdgovoriByKvizTakenId(kt.id)
            }
        }


//
//        suspend fun postaviOdgovorKviz(idKvizTaken: Int?, idPitanje: Int?, odgovor: Int?):Int {
//            return withContext(Dispatchers.IO) {
//                val zapocetiKviz = TakeKvizRepository.getPocetiKvizovi()?.filter { kvizTaken -> kvizTaken.id?.equals(idKvizTaken)!! }?.firstOrNull()
//                if(zapocetiKviz == null) return@withContext -1
//
//                val pitanje = PitanjeKvizRepository.getPitanja(zapocetiKviz.KvizId).filter { pitanje -> pitanje.id?.equals(idPitanje)!! }.firstOrNull()
//                if(pitanje == null) return@withContext -1
//
//                val pitanja = PitanjeKvizRepository.getPitanja(zapocetiKviz.KvizId)
//                val odgovori = getOdgovoriKviz(zapocetiKviz.KvizId)
//
//                var tacni = 0
//                for(p in pitanja) {
//                    for (o in odgovori) {
//                        if (p.id?.equals(o.pitanjeId)!! && o.odgovoreno == p.tacan) {
//                            tacni++
//                            break
//                        }
//                    }
//                    if(p.id?.equals(idPitanje)!!) {
//                        tacni++
//                    }
//                }
//                val procenat = ((((tacni.toDouble() * 100) / pitanja.size) * 100).roundToInt()).toDouble() / 100
//
//                val json = JSONObject()
//                json.put("odgovor", odgovor)
//                json.put("pitanje", idPitanje)
//                json.put("bodovi", procenat.toInt())
//
//                val jsonString = json.toString()
//                val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString)
//
//                val response= ApiAdapter.retrofit.postaviOdgovor(AccountRepository.acHash, idKvizTaken, body)
//                if(response.message != null && response.message.equals("KvizTaken not found."))
//                    return@withContext -1
//                if(pitanje.tacan.equals(odgovor))
//                    return@withContext procenat.toInt()
//
//                return@withContext -1
//            }
//        }

//        suspend fun postaviOdgovorKviz(idKvizTaken: Int?, idPitanje: Int?, odgovor: Int?): Int {
//            return withContext(Dispatchers.IO) {
//                var odgovori = AppDatabase.getInstance(AccountRepository.getContext()).odgovorDao().getAll()
//
//                for(o in odgovori)
//                    if(o.pitanjeId?.equals(idPitanje)!!) return@withContext 50
//                val o = Odgovor(odgovori.size + 1, odgovor, idPitanje, idKvizTaken)
//                AppDatabase.getInstance(AccountRepository.getContext()).odgovorDao().insertOdgovor(o)
//                odgovori = AppDatabase.getInstance(AccountRepository.getContext()).odgovorDao().getAll()
//                val pitanja = AppDatabase.getInstance(AccountRepository.getContext()).pitanjeDao().getAll()
//                val pr = procenat(pitanja, odgovori)
//                return@withContext pr.toInt()
//            }
//        }

        @RequiresApi(Build.VERSION_CODES.O)
        suspend fun postaviOdgovorKviz(idKvizTaken: Int, idPitanje: Int, odgovor: Int): Int? {
            return withContext(Dispatchers.IO) {
                val kvizTaken = TakeKvizRepository.getPocetiKvizovi()
                var kvizId = 0
                for(kT in kvizTaken!!) {
                    if (kT.id == idKvizTaken) {
                        kvizId = kT.KvizId
                        break
                    }
                }
                val pitanja = PitanjeKvizRepository.getMojaPitanja()
                val odgovoriSvi = getOdgovoriKviz(kvizId)
                val pitanjaZaKviz = pitanja.filter { p-> p.kvizId == kvizId }
                val odgovoriZaKviz = odgovoriSvi.filter { o-> o.kvizTakenId == idKvizTaken }
                var brojTacnih = 0
                var odgovorenoVec = false
                for(pitanje in pitanjaZaKviz){
                    if(pitanje.id == idPitanje && pitanje.tacan == odgovor) {
                        brojTacnih++
                        continue
                    }
                    for(odgovorSvi in odgovoriZaKviz){
                        if(idPitanje == odgovorSvi.pitanjeId)
                            odgovorenoVec = true
                        if(pitanje.id == odgovorSvi.pitanjeId && pitanje.tacan == odgovorSvi.odgovoreno)
                            brojTacnih++
                    }
                }
                val ukupniBodovi = (brojTacnih * 100.0 / pitanjaZaKviz.size).roundToInt()
                if(odgovorenoVec) return@withContext ukupniBodovi
                val o = Odgovor(odgovoriZaKviz.size+1, odgovor, idPitanje, idKvizTaken)
                AppDatabase.getInstance(AccountRepository.getContext()).odgovorDao().insertOdgovor(o)
                return@withContext ukupniBodovi
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        suspend fun predajOdgovore(idKviz: Int) {
            return withContext(Dispatchers.IO) {
                val kvizTaken = TakeKvizRepository.getPocetiKvizovi()?.firstOrNull() { k-> k.KvizId == idKviz }
                val kviz = KvizRepository.getUpisaniKvizovi().first { k-> k.id == idKviz }
                if(kvizTaken == null) {
                    popuniOstaleOdgovore(kviz)
                    return@withContext
                }
                val odgovoriSvi = OdgovorRepository.getOdgovoriKviz(idKviz).filter { o-> o.kvizTakenId == kvizTaken.id }
                var bodovi: Int? = null
                for(odg in odgovoriSvi)
                    bodovi = postaviOdgovorPrekoApi(kvizTaken.id, odg.pitanjeId, odg.odgovoreno)

                if (bodovi == null)
                    kviz.osvojeniBodovi = 0F
                else
                    kviz.osvojeniBodovi = bodovi.toFloat()

                kviz.datumRada = Date().toString()
                kviz.predat = true
                AppDatabase.getInstance(AccountRepository.getContext()).kvizDao().deleteKviz(kviz)
                AppDatabase.getInstance(AccountRepository.getContext()).kvizDao().insertKviz(kviz)
                popuniOstaleOdgovore(kviz)
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        private suspend fun popuniOstaleOdgovore(kviz: Kviz) {
            return withContext(Dispatchers.IO) {
                val odgovori = getOdgovoriKviz(kviz.id)
                val pitanja = ApiAdapter.retrofit.getPitanja(kviz.id)
                val kvizTaken = TakeKvizRepository.zapocniKviz(kviz.id)

                for(pitanje in pitanja) {
                    var odgovorenoPitanje = false
                    for (odgovor in odgovori) {
                        if(odgovor.pitanjeId == pitanje.id)
                            odgovorenoPitanje = true
                    }
                    if(!odgovorenoPitanje)
                        postaviOdgovorPrekoApi(kvizTaken!!.id, pitanje.id!!, pitanje.opcije.size)
                }

                return@withContext
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        private suspend fun postaviOdgovorPrekoApi(idKvizTaken: Int?, idPitanje: Int?, odgovor: Int?): Int? {
            return withContext(Dispatchers.IO) {
                val hash = AccountRepository.getHash()
                val kvizoviPokrenuti = TakeKvizRepository.getPocetiKvizovi()
                var kvizId = 0
                if (kvizoviPokrenuti != null) {
                    for(kt in kvizoviPokrenuti){
                        if(kt.id == idKvizTaken) {
                            kvizId = kt.KvizId!!
                        }
                    }
                }
                var ukupniBodovi = 0
                val odgovori = ApiAdapter.retrofit.getOdgovoriZaPokusaj(hash, idKvizTaken!!)
                val pitanja = PitanjeKvizRepository.getPitanja(kvizId)
                val brojPitanja = pitanja.size
                var brojTacnih = 0
                var odgovorenoVec = false
                for(pitanje in pitanja){
                    if(pitanje.id == idPitanje && pitanje.tacan == odgovor)
                        brojTacnih++
                    for(odgovorSvi in odgovori){
                        if(idPitanje == odgovorSvi.pitanjeId)
                            odgovorenoVec = true
                        if(pitanje.id == odgovorSvi.pitanjeId && pitanje.tacan == odgovorSvi.odgovoreno)
                            brojTacnih++
                    }
                }
                ukupniBodovi = (brojTacnih*100.0/brojPitanja).roundToInt()
                if(odgovorenoVec) return@withContext ukupniBodovi
                posaljiOdgovor(idKvizTaken, idPitanje!!, odgovor!!, ukupniBodovi.toDouble())
                return@withContext ukupniBodovi
            }
        }

        private suspend fun posaljiOdgovor(idKvizTaken: Int?, idPitanje: Int?, odgovor: Int?, procenat: Double) {
            val json = JSONObject()
            json.put("odgovor", odgovor)
            json.put("pitanje", idPitanje)
            json.put("bodovi", procenat.toInt())

            val jsonString = json.toString()
            val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString)

            val response= ApiAdapter.retrofit.postaviOdgovor(AccountRepository.acHash, idKvizTaken, body)
        }







        }


}