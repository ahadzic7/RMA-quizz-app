package ba.etf.rma21.projekat.data.api


import ba.etf.rma21.projekat.data.models.*
import okhttp3.RequestBody
import retrofit2.http.*

interface Api {

    @GET("/student/{id}")
    suspend fun getAccount(@Path("id") id:String): Account

    @GET("/account/{id}/lastUpdate?=date")
    suspend fun isChanged(@Path("id") id:String, @Query("date") date:String): Message

    //predmeti

    @GET("/predmet")
    suspend fun getAllPredmeti() : List<Predmet>

    @GET("/kviz/{id}/grupa")
    suspend fun getGrupeZaKviz(@Path("id") id: Int): List<Grupa>

    @GET("/predmet/{id}")
    suspend fun getPredmetId(@Path("id") id: Int): Predmet

    //grupe
    @GET("/grupa")
    suspend fun getAllGrupe(): List<Grupa>

    @GET("/student/{id}/grupa")
    suspend fun getUpisaneGrupe(@Path("id") id: String): List<Grupa>

    @POST("/grupa/{gid}/student/{id}")
    suspend fun upisiNaGrupu(@Path("gid") gid: Int, @Path("id") id: String): Message

    @GET("/predmet/{id}/grupa")
    suspend fun getGrupeZaPredmet(@Path("id") id: Int): List<Grupa>

    @GET("/grupa/{id}")
    suspend fun getGrupaById(@Path("id") id: Int): Grupa


    //kvizovi

    @GET("/kviz") suspend fun getAllKvizes() : List<Kviz>

    @GET("/kviz/{id}") suspend fun getKvizZaId(@Path("id") groupId: Int) : Kviz



    @GET("/grupa/{id}/kvizovi")
    suspend fun getKvizoveZaGrupu(@Path("id") id: Int): List<Kviz>

    @GET("student/{id}/kviztaken")
    suspend fun getPokusaji(@Path("id") id: String): List<KvizTaken>

    @POST("/student/{id}/kviz/{kid}")
    suspend fun pokusaj(@Path("id") id: String, @Path("kid") kid: Int?): KvizTaken?

    @GET("/student/{id}/kviztaken/{ktid}/odgovori")
    suspend fun getOdgovoriZaPokusaj(@Path("id") id: String?, @Path("ktid") ktid: Int?): List<Odgovor>

    @POST("/student/{id}/kviztaken/{ktid}/odgovor")
    suspend fun postaviOdgovor(@Path("id") id: String?, @Path("ktid") ktid: Int?, @Body zahtjev: RequestBody): Message

    @GET("/kviz/{id}/pitanja")
    suspend fun getPitanja(@Path("id") id: Int?): List<Pitanje>
}