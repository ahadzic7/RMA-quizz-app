package ba.etf.rma21.projekat.data.models

import androidx.room.*
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

@Entity(tableName = "Kviz")
data class Kviz(@PrimaryKey @SerializedName("id")
                val id: Int?,
                @ColumnInfo(name = "naziv") @SerializedName("naziv")
                val naziv: String?,
                var nazivPredmeta: String?,
                @ColumnInfo(name = "datumPocetka") @SerializedName("datumPocetak") @TypeConverters(Converters::class)
                val datumPocetka: String?,
                @ColumnInfo(name = "datumKraj") @SerializedName("datumKraj") @TypeConverters(Converters::class)
                val datumKraj: String?,
                @ColumnInfo(name = "datumRada") @TypeConverters(Converters::class)
                var datumRada: String?,
                @ColumnInfo(name = "trajanje") @SerializedName("trajanje")
                val trajanje: Int?,
                val nazivGrupe: String?,
                var osvojeniBodovi: Float?,

                @ColumnInfo(name = "predat")
                var predat: Boolean
) : Serializable {}