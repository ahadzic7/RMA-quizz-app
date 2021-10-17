package ba.etf.rma21.projekat.view.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import ba.etf.rma21.projekat.MainActivity
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.*
import ba.etf.rma21.projekat.viewmodel.OdgovorViewModel
import ba.etf.rma21.projekat.viewmodel.SharedViewModel
import ba.etf.rma21.projekat.viewmodel.TakeKvizViewModel
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.math.roundToInt


class FragmentPokusaj(var listaPitanja: List<Pitanje>) : Fragment() {
    private lateinit var navigacija: NavigationView

    private lateinit var sharedViewModel: SharedViewModel

    private var cashPitanje:Int? = 0

    private var kviz :Kviz? = null
    fun setKviz(kviz: Kviz) { this.kviz = kviz }

    private var odgovoriZaKviz: List<Odgovor>? = null
    fun setOdgovori(odgovori: List<Odgovor>?) { odgovoriZaKviz = odgovori }

    private var pokusajKviza: KvizTaken? = null



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pokusaj, container, false)

        TakeKvizViewModel.zapocniKviz(::onSuccessZapocniKviz, ::onError, kviz!!)
//        OdgovorViewModel.getOdgovoriZaKviz(::onSuccessGetOdgovoriZaKviz, ::onError, kviz!!)


        sharedViewModel = (activity as MainActivity?)?.sharedViewModel!!
        (activity as MainActivity?)!!.updateBottomNavigation(false)
        navigacija = view.findViewById(R.id.navigacijaPitanja)


        val menu = navigacija.menu
        menu.clear()
        var i = 0
        for(pitanje in listaPitanja) {
            val item = menu.add(12345, i, i, (i + 1).toString())
            item.setOnMenuItemClickListener { index -> showPitanje(index) }
            val odgovor = odgovoriZaKviz?.find { odgovor -> odgovor.pitanjeId?.equals(pitanje.id)!! }
            if(odgovor != null) {
                if(odgovor.odgovoreno?.equals(-1)!!) {
                    //kviz je predan a ovo nije odgovoreno
                    obojiBrojPitanja(i, -1)
                }
                else if (odgovor.odgovoreno.equals(pitanje.tacan)) {
                    obojiBrojPitanja(i, 1)
                }
                else {
                    obojiBrojPitanja(i, 0)
                }
            }
            i++
        }

        sharedViewModel.tacanOdgovor.observe(viewLifecycleOwner, Observer<Boolean?> { tacan ->
            var boja = "#DB4F3D"
            if (tacan!!)
                boja = "#3DDC84"
            if (cashPitanje!! < navigacija.menu.size) {
                val menuItem = navigacija.menu.getItem(cashPitanje!!)
                val span = SpannableString(menuItem.title.toString())
                span.setSpan(ForegroundColorSpan(Color.parseColor(boja)), 0, span.length, 0)
                menuItem.setTitle(span)
            }

        })

        if(kviz?.datumRada == null)
            sharedViewModel.predatKviz.observe(viewLifecycleOwner, Observer<Boolean> { predat ->
                if (predat != null && predat) {
                    showRezultat()
                    sharedViewModel.predatKviz = MutableLiveData()
                }
            })
        else { showRezultat() }


        return view
    }


    fun onSuccessZapocniKviz(pokusaj: KvizTaken) {
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                pokusajKviza = pokusaj
                MainActivity.setKvizTaken(pokusajKviza!!)
            }
        }
    }


    fun onSuccessGetOdgovoriZaKviz(odgovori: List<Odgovor>, pitanje: Pitanje?) {
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                odgovoriZaKviz = odgovori

                if(pitanje != null) {
                    val pitanjeFragment = FragmentPitanje.newInstance(pitanje)
                    pitanjeFragment.setKvizTaken(pokusajKviza)
                    val x = odgovoriZaKviz
                    val odgovoreno = odgovoriZaKviz?.find { odgovor -> odgovor.pitanjeId?.equals(pitanje.id)!! }
                    pitanjeFragment.setOdgovoreno(odgovoreno)

                    if (kviz?.datumRada != null)
                        sharedViewModel.predatKviz.value = true

                    fragmentManager?.beginTransaction()?.replace(R.id.framePitanje, pitanjeFragment)?.commit()
                }
            }
        }
    }

    fun onSuccessPredajOdgovore() {
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                val menu = navigacija.menu
                val item = menu.add(12345, menu.size(), menu.size(), "Rezultat")
                item.setOnMenuItemClickListener { showRezultat() }
                val procenat = racunajProcenat()
                val poruka = "Završili ste kviz ${kviz?.naziv} sa tačnosti ${procenat}"
                val porukaFragment = FragmentPoruka.newInstance(poruka)
                fragmentManager?.beginTransaction()?.replace(R.id.framePitanje, porukaFragment)?.commit()
                kviz?.datumRada = dateToString(Date())
                kviz?.osvojeniBodovi = procenat.toFloat()
            }
        }
    }

    @SuppressLint("ShowToast")
    fun onError(poruka: String) { Toast.makeText(context, poruka, Toast.LENGTH_SHORT) }


    private fun racunajProcenat(): Double {
        if(listaPitanja.size == 0) return 69420.0
        var tacni = 0
        if(odgovoriZaKviz != null) {
            for(p in listaPitanja) {
                for (o in odgovoriZaKviz!!) {
                    if (p.id?.equals(o.pitanjeId)!! && o.odgovoreno == p.tacan) {
                        tacni++
                        break
                    }
                }
            }
        }
        return ((((tacni.toDouble() * 100) / listaPitanja.size) * 100).roundToInt()).toDouble() / 100
    }

    fun dateToString(date: Date?): String? {
        if (date == null) return null
        return date.toString()
    }

    private fun obojiBrojPitanja(index: Int, tacanOdgovor: Int) {
        if(tacanOdgovor.equals(-1)) return
        var boja = "#DB4F3D"
        if(tacanOdgovor.equals(1))
            boja = "#3DDC84"

        val menuItem = navigacija.menu.getItem(index)
        val span = SpannableString(menuItem.title.toString())
        span.setSpan(ForegroundColorSpan(Color.parseColor(boja)), 0, span.length, 0)
        menuItem.setTitle(span)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun showRezultat(): Boolean {
        OdgovorViewModel.predajOdgovore(::onSuccessPredajOdgovore, kviz?.id!!)
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showPitanje(index: MenuItem): Boolean {
        cashPitanje = index.title.toString().toInt() - 1

        val pitanje = listaPitanja[cashPitanje!!]

        OdgovorViewModel.getOdgovoriZaKviz(::onSuccessGetOdgovoriZaKviz, ::onError, kviz!!, pitanje)


        return true
    }



}