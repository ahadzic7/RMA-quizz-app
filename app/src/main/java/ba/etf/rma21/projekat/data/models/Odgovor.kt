package ba.etf.rma21.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity(tableName = "Odgovor")
class Odgovor(
    @PrimaryKey @SerializedName("id") val id: Int,
    @ColumnInfo(name = "odgovoreno") @SerializedName("odgovoreno") val odgovoreno: Int?,
    @ColumnInfo(name = "PitanjeId") @SerializedName("PitanjeId") val pitanjeId: Int?,
    @ColumnInfo(name = "KvizTakenId") @SerializedName("KvizTakenId") val kvizTakenId: Int?
)
