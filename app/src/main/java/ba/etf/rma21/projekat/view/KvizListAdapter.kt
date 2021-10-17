package ba.etf.rma21.projekat.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.Converters
import ba.etf.rma21.projekat.data.models.Kviz
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

@Suppress("DEPRECATED_IDENTITY_EQUALS")
class KvizListAdapter(private var kvizovi: List<Kviz>,
                      private val onItemClicked: (kviz: Kviz) -> Unit) : RecyclerView.Adapter<KvizListAdapter.KvizViewHolder>(){

    inner class KvizViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stanje: ImageView = itemView.findViewById(R.id.status_img)
        val predmet: TextView = itemView.findViewById(R.id.predmet_tv)
        val naziv: TextView = itemView.findViewById(R.id.kviz_tv)
        val datum: TextView = itemView.findViewById(R.id.datum_tv)
        val vrijeme: TextView = itemView.findViewById(R.id.vrijeme_tv)
        val bodovi: TextView = itemView.findViewById(R.id.bodovi_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KvizViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.kviz_layout, parent, false)
        return KvizViewHolder(view)
    }

    override fun getItemCount(): Int { return kvizovi.size }

    private fun stringToDate(value: String?): Date? {
        if(value == null)
            return null
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.parse(value)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
    private fun getDisplayDate(kviz: Kviz): String {
        val currentDate = Date()

        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.uuuu")

//        if(kviz.datumPocetka == null) return currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(formatter)

        val rad = stringToDate(kviz.datumRada)
        val pocetak = stringToDate(kviz.datumPocetka)
        val kraj = stringToDate(kviz.datumKraj)

        if(rad == null) {
            //kviz nije radjen
            if(pocetak?.compareTo(currentDate)!! > 0) {
                //kviz jos nije poceo moze se raditi u buducnosti
                return pocetak.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(formatter)
            }
            else {
                //kviz nije radjen, nije bitno jel i dalje aktivan ili je prosao ALI nije odredjen datum kraja
                if(kraj == null)
                    return ""
                //kviz nije radjen, nije bitno jel i dalje aktivan ili je prosao
                return kraj.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(formatter)
            }
        }
        //kviz uradjen
        return rad.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(formatter)
    }

    private fun getImageString(kviz: Kviz):String {
        val currentDate = Calendar.getInstance().getTime()
        val rad = stringToDate(kviz.datumRada)
        val pocetak = stringToDate(kviz.datumPocetka)
        val kraj = stringToDate(kviz.datumKraj)

        if(rad == null) {
            //kviz nije radjen
            if(pocetak!!.compareTo(currentDate) > 0) {
                //kviz jos nije poceo moze se raditi u buducnosti
                return "zuta"
            }
            if(kraj == null) {
                return "zelena"
            }
            else if (kraj.compareTo(currentDate) < 0){
                //prosao rok ne moze se vise raditi
                return "crvena"
            }
            return "zelena"
        }
        //kviz uradjen
        return "plava"
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: KvizViewHolder, position: Int) {
        holder.predmet.text = kvizovi[position].naziv
        holder.naziv.text = kvizovi[position].nazivPredmeta
        holder.vrijeme.text = kvizovi[position].trajanje.toString() + " min"

        holder.itemView.setOnClickListener{ onItemClicked(kvizovi[position]) }

        holder.datum.text = getDisplayDate(kvizovi[position])
        if(kvizovi[position].osvojeniBodovi != null)
            holder.bodovi.text = kvizovi[position].osvojeniBodovi.toString()
        else
            holder.bodovi.text = ""
        val boja = getImageString(kvizovi[position])
        //Pronalazimo id drawable elementa na osnovu naziva žanra
        val context: Context = holder.stanje.getContext()
        var id: Int = context.getResources().getIdentifier(boja, "drawable", context.getPackageName())
        if (id===0) id=context.getResources().getIdentifier("ic_launcher_background.xml", "drawable", context.getPackageName())
        holder.stanje.setImageResource(id)
    }

    fun updateKvizovi(movies: List<Kviz>) {
        this.kvizovi = movies
        notifyDataSetChanged()
    }
}