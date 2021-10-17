package ba.etf.rma21.projekat.view.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import ba.etf.rma21.projekat.MainActivity
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.Converters
import ba.etf.rma21.projekat.data.models.KvizTaken
import ba.etf.rma21.projekat.data.models.Odgovor
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.viewmodel.OdgovorViewModel
import ba.etf.rma21.projekat.viewmodel.SharedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FragmentPitanje (var pitanje: Pitanje) :Fragment() {
    private lateinit var tekstPitanja : TextView
    private lateinit var odgovoriLista: ListView

    private lateinit var sharedViewModel: SharedViewModel

    private var odg : Odgovor? = null
    fun setOdgovoreno(o: Odgovor?) { odg = o }

    private var kvizTaken : KvizTaken? = null
    fun setKvizTaken(kt: KvizTaken?) { kvizTaken = kt }

    companion object { fun newInstance (pitanje: Pitanje) : FragmentPitanje = FragmentPitanje(pitanje) }

    private fun toast(poruka:String){ Toast.makeText(tekstPitanja.context, poruka, Toast.LENGTH_SHORT).show() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler().postDelayed({
            if (odg != null) {
                if (odg!!.odgovoreno != -1) {
                    odgovoriLista.performItemClick(odgovoriLista, odg!!.odgovoreno!!, odgovoriLista.adapter.getItemId(odg!!.odgovoreno!!))
                }
            }
        }, 1)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pitanje, container, false)

        sharedViewModel = (activity as MainActivity?)?.sharedViewModel!!

        tekstPitanja = view.findViewById(R.id.tekstPitanja)
        tekstPitanja.text = pitanje.tekstPitanja

        odgovoriLista = view.findViewById(R.id.odgovoriLista)
        odgovoriLista.adapter = ArrayAdapter<String>(tekstPitanja.context, android.R.layout.simple_list_item_1, pitanje.opcije)

        odgovoriLista.setOnItemClickListener { parent, _, position, id -> showRjesenje(position, parent) }

        if(sharedViewModel.predatKviz.value != null && sharedViewModel.predatKviz.value!!)
            odgovoriLista.isEnabled = false

        sharedViewModel.predatKviz.observe(viewLifecycleOwner, Observer<Boolean> {predat ->
            odgovoriLista.isEnabled = false
            sharedViewModel.predatKviz = MutableLiveData()
            sharedViewModel.tacanOdgovor = MutableLiveData()
        })

        return view
    }

    fun toList(string: String?): List<String>? {
        if (string == null) return null
        return string.split(',')
    }

    fun onSuccessPostaviOdgovor(response: Int, odgovor: Odgovor) {
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {

            }
        }
    }




    @SuppressLint("ShowToast")
    fun onError(poruka: String) { Toast.makeText(context, poruka, Toast.LENGTH_SHORT) }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showRjesenje(index: Int, parent: AdapterView<*>) {
        val view = parent.getChildAt(index) as TextView
        if(index == pitanje.tacan) {
//            toast("dobaaaaaaaar")
            view.setBackgroundColor(getColor(tekstPitanja.context, R.color.tacan_odgovor))
//            val odgovorTacan = parent.getChildAt(index) as TextView
//            view.setTextColor(Color.parseColor("#3DDC84"))
            sharedViewModel.setTacanOdgovor(true)
        }
        else {
//            toast("lossssss")
            view.setBackgroundColor(getColor(tekstPitanja.context, R.color.netacan_odgovor))
            parent[pitanje.tacan].setBackgroundColor(getColor(tekstPitanja.context, R.color.tacan_odgovor))
            sharedViewModel.setTacanOdgovor(false)
        }
        parent.isEnabled = false

//        sharedViewModel.addPitanjeOdabir(pitanje, index)


        OdgovorViewModel.postaviOdgovor(::onSuccessPostaviOdgovor, ::onError, kvizTaken?.id!!, pitanje.id!!, index)

    }
}