package ba.etf.rma21.projekat.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma21.projekat.MainActivity
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Odgovor
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.view.KvizListAdapter
import ba.etf.rma21.projekat.viewmodel.old.KvizListViewModel
import ba.etf.rma21.projekat.viewmodel.KvizViewModel
import ba.etf.rma21.projekat.viewmodel.PitanjeKvizViewModel
import ba.etf.rma21.projekat.viewmodel.SharedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentKvizovi : Fragment() {
    private lateinit var kvizovi: List<Kviz>

    private lateinit var filterKvizova: Spinner
    private lateinit var listaKvizova: RecyclerView
    private lateinit var kvizListAdapter: KvizListAdapter

    private lateinit var sharedViewModel: SharedViewModel
    private var kvizListViewModel = KvizListViewModel.newInstance()
    private var pitanjeKvizViewModel = PitanjeKvizViewModel()

    private val kvizViewModel = KvizViewModel()
    private val pitanjaKvizViewModel = PitanjeKvizViewModel()

    private var odabraniFilter:Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_kvizovi, container, false)
        kvizovi = emptyList()

        sharedViewModel = (activity as MainActivity?)?.sharedViewModel!!


        listaKvizova = view.findViewById(R.id.listaKvizova)
        listaKvizova.layoutManager = GridLayoutManager(activity, 2)
        kvizListAdapter = KvizListAdapter(listOf(), { kviz -> pitanjaKvizViewModel.getPitanja(::onSuccessPitanja, ::onError, kviz) })
        listaKvizova.adapter = kvizListAdapter
//        kvizListAdapter.updateKvizovi(kvizListViewModel.getKvizove().sortedBy { kviz -> kviz.datumPocetka })


        filterKvizova = view.findViewById(R.id.filterKvizova)

        filterKvizova.adapter = ArrayAdapter.createFromResource(listaKvizova.context, R.array.vrsteKvizFiltera, R.layout.spinner_layout)
        filterKvizova.setSelection(odabraniFilter)

        filterKvizova.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) { kvizListAdapter.updateKvizovi(emptyList()) }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                odabraniFilter = position
                filtrirtaj(position)
            }
        }


        kvizViewModel.getMyKvizes(::onSuccess, ::onError)

        return view
    }
    companion object { fun newInstance(): FragmentKvizovi = FragmentKvizovi() }

    private fun filtrirtaj(vrstaKviza: Int) {
        when (vrstaKviza) {
            /*"Svi moji kvizovi"*/ 0 -> kvizViewModel.getMyKvizes(::onSuccess, ::onError)
            /*"Svi kvizovi"*/ 1 -> kvizViewModel.getAll(::onSuccess, ::onError)
            /*"Urađeni kvizovi"*/ 2 -> kvizViewModel.getDone(::onSuccess, ::onError)
            /*"Budući kvizovi"*/ 3 -> kvizViewModel.getFuture(::onSuccess, ::onError)
            /*"Prošli kvizovi"*/ 4 -> kvizViewModel.getNotTaken(::onSuccess, ::onError)
        }
    }

    fun onSuccess(kvizs: List<Kviz>) {
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                kvizovi = kvizs
                kvizListAdapter.updateKvizovi(kvizovi)
            }
        }
    }

    fun onSuccessPitanja(pitanja: List<Pitanje>, kviz : Kviz, odgovori: List<Odgovor>) {
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                val pokusajFragment = FragmentPokusaj(pitanja)
                pokusajFragment.setKviz(kviz)
                if(odgovori.isEmpty()) {
                    pokusajFragment.setOdgovori(null)
                }
                else {
                    pokusajFragment.setOdgovori(odgovori)
                }
                MainActivity.setKvizPitanjaOdgovori(kviz, pitanja)
                fragmentManager?.beginTransaction()?.replace(R.id.container, pokusajFragment)?.commit()

            }
        }
    }

    @SuppressLint("ShowToast")
    fun onError(poruka: String) { Toast.makeText(context, poruka, Toast.LENGTH_SHORT) }

    private fun zatvoriFragmentKvizovi() {
        val bundle = Bundle()
        val predmetiFragment = FragmentPredmeti.newInstance()
        predmetiFragment.arguments = bundle
        fragmentManager?.beginTransaction()?.replace(R.id.container, predmetiFragment)?.commit()

    }



}