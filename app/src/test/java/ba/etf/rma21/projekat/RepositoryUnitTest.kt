package ba.etf.rma21.projekat


import ba.etf.rma21.projekat.data.api.ApiConfig
import ba.etf.rma21.projekat.data.models.*
import ba.etf.rma21.projekat.data.repositories.*

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.MatcherAssert.assertThat
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import java.net.URL


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class RepositoryUnitTest {
    suspend fun obrisi(){
        val client: OkHttpClient = OkHttpClient()
        val builder: Request.Builder = Request.Builder()
            .url(URL(ApiConfig.baseURL + "/student/" + AccountRepository.acHash + "/upisugrupeipokusaji"))
            .delete()
        val request: Request = builder.build()
        withContext(Dispatchers.IO) {
            val response: Response = client.newCall(request).execute()
            var odgovor: String = response.body().toString()
        }
    }
    @Test
    fun a0_pripremiPocetak() = runBlocking {
        obrisi()
    }

    @Test
    fun a1_getPredmete() = runBlocking {
        val predmeti = PredmetIGrupaRepository.getPredmeti()
        assertThat(predmeti,CoreMatchers.notNullValue())
        assertThat(predmeti?.size,CoreMatchers.equalTo(2))

    }

    @Test
    fun a2_getGrupe() = runBlocking {
        val grupe = PredmetIGrupaRepository.getGrupe()
        assertThat(grupe,CoreMatchers.notNullValue())
        assertThat(grupe?.size,CoreMatchers.equalTo(4))
    }
    @Test
    fun a3_getUpisaneGrupe() = runBlocking {
        val upisane = PredmetIGrupaRepository.getUpisaneGrupe()
        assertThat(upisane?.size,CoreMatchers.equalTo(0))
    }

    @Test
    fun a4_upisiIProvjeri() = runBlocking {
        val grupe = PredmetIGrupaRepository.getGrupe()
        PredmetIGrupaRepository.upisiUGrupu(grupe!![0]?.id)
        val upisane = PredmetIGrupaRepository.getUpisaneGrupe()
        assertThat(upisane?.size,CoreMatchers.equalTo(1))
        assertThat(upisane?.intersect(grupe)?.size,CoreMatchers.equalTo(1))
    }

    @Test
    fun a5_zapocniUpisaniKviz() = runBlocking {
        val upisaniKvizovi = KvizRepository.getUpisaniKvizovi()
        val prije = TakeKvizRepository.getPocetiKvizovi()
        TakeKvizRepository.zapocniKviz(upisaniKvizovi!![0]?.id)
        val poslije = TakeKvizRepository.getPocetiKvizovi()
        assertThat(prije,CoreMatchers.`is`(CoreMatchers.nullValue()))
        assertThat(poslije!!.size,CoreMatchers.equalTo(1))
    }

    @Test
    fun a6_zapocniNemoguciKviz() = runBlocking {
        TakeKvizRepository.zapocniKviz(999)
        assertThat(TakeKvizRepository.getPocetiKvizovi()!!.size,CoreMatchers.equalTo(1))
    }

    @Test
    fun a7_provjeriBezOdgovora() = runBlocking {
        val poceti = TakeKvizRepository.getPocetiKvizovi()
        assertThat(OdgovorRepository.getOdgovoriKviz(poceti!![poceti.size-1]?.KvizId)!!.size,CoreMatchers.equalTo(0))
    }
    @Test
    fun a8_provjeriOdgovor() = runBlocking {
        val poceti = TakeKvizRepository.getPocetiKvizovi()
        val pitanja = PitanjeKvizRepository.getPitanja(poceti!![poceti.size-1]?.KvizId)
        val result = OdgovorRepository.postaviOdgovorKviz(poceti!![poceti.size-1]?.id,pitanja!![0]?.id,pitanja!![0]?.tacan)
        assertThat(result,CoreMatchers.notNullValue())
        assertThat(result,CoreMatchers.equalTo(50))
        assertThat(OdgovorRepository.getOdgovoriKviz(poceti!![poceti.size-1]?.KvizId)!!.size,CoreMatchers.equalTo(1))
    }
    @Test
    fun a9_provjeriKvizove() = runBlocking {
        assertThat(KvizRepository.getAll()!!.size,CoreMatchers.equalTo(3))
    }

    @Test
    fun a9a_provjeriPitanja() = runBlocking {
        val kvizovi = KvizRepository.getAll()
        assertThat(kvizovi,CoreMatchers.notNullValue())
        val pitanja = PitanjeKvizRepository.getPitanja(kvizovi!![0]?.id)
        assertThat(pitanja,CoreMatchers.notNullValue())
        assertThat(pitanja!!.size,CoreMatchers.equalTo(2))
    }

    fun checkProperties(propA:Collection<String>,propB:Collection<String>){
        for(trazeniProperty in propA){
            assertThat(propB,hasItem(trazeniProperty))
        }
    }
    @Test
    fun sveKlaseIspravne() {
        val pitanjeProperties = Pitanje::class.java.kotlin.members.map { it.name }
        val pitanjeTProperties = listOf("id","naziv","tekstPitanja","opcije","tacan")
        checkProperties(pitanjeTProperties,pitanjeProperties)

        val kvizProperties = Kviz::class.java.kotlin.members.map {it.name}
        val kvizTProperties = listOf("id","naziv","datumPocetka","datumKraj","trajanje")
        checkProperties(kvizTProperties,kvizProperties)

        val kvizTakenProperties = KvizTaken::class.java.kotlin.members.map { it.name }
        val kvizTakenTProperties = listOf("id","student","datumRada","osvojeniBodovi")
        checkProperties(kvizTakenTProperties,kvizTakenProperties)

        val grupaProperties = Grupa::class.java.kotlin.members.map { it.name }
        val grupaTProperties = listOf("id","naziv")
        checkProperties(grupaTProperties,grupaProperties)

        val predmetProperties = Predmet::class.java.kotlin.members.map { it.name }
        val predmetTProperties = listOf("id","naziv","godina")
        checkProperties(predmetTProperties,predmetProperties)

        val odgovorProperties = Odgovor::class.java.kotlin.members.map { it.name }
        val odgovorTProperties = listOf("id","odgovoreno")
        checkProperties(odgovorTProperties,odgovorProperties)
    }
}
