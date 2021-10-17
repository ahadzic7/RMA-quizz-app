package ba.etf.rma21.projekat.data.models

import androidx.room.TypeConverter
import java.util.*

class Converters {
        @TypeConverter
        fun stringToDate(value: String?): Date? {
            if (value == null) return null
        val d = value.split('T')[0]
        val datumi = d.split('-').map { str -> str.toInt() }
        return Date(datumi[0] - 1900, datumi[1], datumi[2])

        }

        @TypeConverter
        fun dateToString(date: Date?): String? {
            if (date == null) return null
            return date.toString()
        }

        @TypeConverter
        fun fromString(stringListString: String): List<String> {
            return stringListString.split(",").map { it }
        }

        @TypeConverter
        fun toString(stringList: List<String>): String {
            return stringList.joinToString(separator = ",")
        }


//    @TypeConverter
//    fun stringToDate(value: String?): Date? {
//        return if (value == null) null else Date(value)
//    }
//
//    @TypeConverter
//    fun dateToString(date: Date?): String? {
//        if (date == null) return null
//        return date.toString()
//    }
////
//    @TypeConverter
//    fun fromList(opcije: List<Int?>?): String? {
//        if (opcije == null) return null
//        return opcije.map { i -> i.toString() }.toString()
//    }
//
//    @TypeConverter
//    fun toList(string: String?): List<String>? {
//        if (string == null) return null
//        return string.split(',')
//    }
}