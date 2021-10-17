package ba.etf.rma21.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Account")
data class Account(
    @PrimaryKey @SerializedName("id") val id: Int,
    @ColumnInfo(name = "student") @SerializedName("student") val  student: String,
    @ColumnInfo(name = "acHash") @SerializedName("acHash") var acHash: String,
    @ColumnInfo(name = "lastUpdate") @SerializedName("updatedAt") var lastUpdate: String,
    @SerializedName("message") val message: String?
)