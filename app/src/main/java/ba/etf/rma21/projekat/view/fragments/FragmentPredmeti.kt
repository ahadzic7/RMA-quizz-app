package ba.etf.rma21.projekat.view.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.MainActivity
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.viewmodel.GrupaListViewModel
import ba.etf.rma21.projekat.viewmodel.PredmetListViewModel
import ba.etf.rma21.projekat.viewmodel.SharedViewModel
import kotlinx.coroutines.*




class FragmentPredmeti : Fragment() {
    private lateinit var spisakPredmeta: Spinner
    private lateinit var spisakGrupa: Spinner
    private lateinit var spisakGodina: Spinner
    private lateinit var dodajDugme: Button


    private lateinit var godine : List<String>
    private lateinit var prikazaniPredmeti: List<Predmet>
    private lateinit var prikazaneGrupe: List<Grupa>


    private lateinit var sharedViewModel: SharedViewModel

    private var predmetListViewModel = PredmetListViewModel.newInstance()
    private var grupaListViewModel = GrupaListViewModel.newInstance()

    private var odabranaGodina: Int = 1
    private var odabraniPredmet: String = ""
    private var odabranaGrupa: String = ""

    private var grupaZaUpisati: Grupa? = null


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_predmeti, container, false)

        godine = resources.getStringArray(R.array.listaGodina).toList()


        sharedViewModel = (activity as MainActivity?)?.sharedViewModel!!

        if (sharedViewModel.cashGodina != null)
            odabranaGodina = sharedViewModel.cashGodina!!

        spisakGodina = view.findViewById(R.id.odabirGodina)
        spisakPredmeta = view.findViewById(R.id.odabirPredmet)
        spisakGrupa = view.findViewById(R.id.odabirGrupa)

        dodajDugme = view.findViewById(R.id.dodajPredmetDugme)
        dodajDugme.setOnClickListener { zatvoriFragmentPredmeti() }
        dodajDugme.isEnabled = false

        spisakGodina.adapter = ArrayAdapter.createFromResource(dodajDugme.context, R.array.listaGodina, R.layout.spinner_layout)
        spisakGodina.setSelection(odabranaGodina - 1)

        predmetListViewModel.getDataZaOdabranuGodinu(::onSuccessDataZaOdabranuGodinu, ::onError, odabranaGodina)

        spisakGodina.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                odabranaGodina(position)
            }
        }
        spisakPredmeta.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (sharedViewModel.predmetPozicija == null)
                    odabranPredmet(position)
                else if (position == 0)
                    odabranPredmet(sharedViewModel.predmetPozicija!!)
                else
                    odabranPredmet(position)
            }
        }
        spisakGrupa.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (sharedViewModel.grupaPozicija == null)
                    odabranaGrupa(position)
                else if (position == 0)
                    odabranaGrupa(sharedViewModel.grupaPozicija!!)
                else
                    odabranaGrupa(position)
            }
        }


        return view
    }


    fun onSuccessDataZaOdabranuGodinu(neupisaniPredmeti: List<Predmet>, imenaPredmeta: List<String>, grupe: List<Grupa>, imenaGrupa: List<String?>) {
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                spisakPredmeta.adapter = ArrayAdapter(dodajDugme.context, R.layout.spinner_layout, imenaPredmeta)
                spisakGrupa.adapter = ArrayAdapter(dodajDugme.context, R.layout.spinner_layout, imenaGrupa)

                prikazaniPredmeti = neupisaniPredmeti
                prikazaneGrupe = grupe

                odabraniPredmet = imenaPredmeta[0]

            }
        }
    }

    fun onSuccessDataZaOdabraniPredmet(grupe: List<Grupa>, imenaGrupa: List<String?>) {
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                spisakGrupa.adapter = ArrayAdapter(dodajDugme.context, R.layout.spinner_layout, imenaGrupa)
                prikazaneGrupe = grupe
            }
        }
    }

    fun onSuccessUpisiGrupu() {
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                val poruka = "Uspješno ste upisani u grupu ${odabranaGrupa} predmeta ${odabraniPredmet}!"
                val porukaFragment = FragmentPoruka.newInstance(poruka)
                fragmentManager?.beginTransaction()?.replace(R.id.container, porukaFragment)?.commit()

            }
        }
    }

    @SuppressLint("ShowToast")
    fun onError(poruka: String) { Toast.makeText(context, poruka, Toast.LENGTH_SHORT) }


    companion object { fun newInstance(): FragmentPredmeti = FragmentPredmeti() }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun odabranaGodina(position: Int) {
        odabranaGodina = godine[position].toInt()

        predmetListViewModel.getDataZaOdabranuGodinu(::onSuccessDataZaOdabranuGodinu, ::onError, odabranaGodina)

        sharedViewModel.cashirajGodinu(odabranaGodina)
    }
    private fun odabranPredmet(position: Int) {

        if(position > 0) {
            val predmet: Predmet = prikazaniPredmeti[position - 1]
            predmetListViewModel.getDataZaOdabranPredmet(::onSuccessDataZaOdabraniPredmet, ::onError, predmet)
            odabraniPredmet = predmet.naziv
        }
        else
            odabraniPredmet = "-"

        spisakPredmeta.setSelection(position)

        stanjeDugmeta()

        sharedViewModel.cashirajPredmet(odabraniPredmet, position)
    }

    private fun odabranaGrupa(position: Int) {
        if(position == 0)
            odabranaGrupa = "-"
        else if(prikazaneGrupe.size + 1 > position) {
            grupaZaUpisati = prikazaneGrupe[position - 1]
            odabranaGrupa = grupaZaUpisati!!.naziv!!
        }
        if(position < spisakGrupa.adapter.count)
            spisakGrupa.setSelection(position)
        stanjeDugmeta()
        sharedViewModel.cashirajGrupu(odabranaGrupa, position)
    }

    private fun zatvoriFragmentPredmeti() {
        if(grupaZaUpisati == null) return

        sharedViewModel.cashirajPredmet(null, null)
        sharedViewModel.cashirajGrupu(null, null)

        grupaListViewModel.upisiGrupu(::onSuccessUpisiGrupu, ::onError, grupaZaUpisati!!)

    }



    private fun stanjeDugmeta() {
        if(odabraniPredmet.equals("-")) {
            dodajDugme.isEnabled = false
            return
        }
        if(odabranaGrupa.equals("-")) {
            dodajDugme.isEnabled = false
            return
        }
        dodajDugme.isEnabled = true
    }


}