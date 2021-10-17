package ba.etf.rma21.projekat

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.children
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.KvizTaken
import ba.etf.rma21.projekat.data.models.Odgovor
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.repositories.AccountRepository
import ba.etf.rma21.projekat.data.repositories.OdgovorRepository
import ba.etf.rma21.projekat.view.fragments.FragmentKvizovi
import ba.etf.rma21.projekat.view.fragments.FragmentPokusaj
import ba.etf.rma21.projekat.view.fragments.FragmentPredmeti
import ba.etf.rma21.projekat.viewmodel.AccountViewModel
import ba.etf.rma21.projekat.viewmodel.DBViewModel
import ba.etf.rma21.projekat.viewmodel.OdgovorViewModel
import ba.etf.rma21.projekat.viewmodel.SharedViewModel


import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    lateinit var sharedViewModel: SharedViewModel

    lateinit var bottomNavigation: BottomNavigationView

    private fun toast(poruka:String){ Toast.makeText(this, poruka, Toast.LENGTH_SHORT).show() }

    private var hashStudenta = "c7483b55-e746-464e-86e9-9b232b174c0b"


    private val OnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.kvizovi -> {
                val kvizoviFragment = FragmentKvizovi.newInstance()
                openFragmentWithoutBack(kvizoviFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.predmeti -> {
                val predmetiFragments = FragmentPredmeti.newInstance()
                openFragmentWithBack(predmetiFragments)
                return@OnNavigationItemSelectedListener true
            }
            R.id.predajKviz -> {
                obradiPredatiKviz()
                sharedViewModel.predatKviz(true)
                return@OnNavigationItemSelectedListener true
            }
            R.id.zaustaviKviz -> {
                onBackPressed()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    companion object {
        private var kviz: Kviz? = null
        private var pitanja: List<Pitanje>? = null
        fun setKvizPitanjaOdgovori(kviz: Kviz, pitanja: List<Pitanje>) {
            this.kviz = kviz
            this.pitanja = pitanja
        }

        private var kvizTaken: KvizTaken? = null
        fun setKvizTaken(kvizTaken: KvizTaken) { this.kvizTaken = kvizTaken }

        private fun obradiPredatiKviz() {
            if(kviz == null) return

            OdgovorViewModel.getOdgovoriZaKviz(::onSuccessGetOdgovor, ::onError, kviz!!, null)

        }
        fun onSuccessPostaviOdgovor(response: Int, odgovor: Odgovor) {
            GlobalScope.launch(Dispatchers.IO) {
                withContext(Dispatchers.Main) {
//                    var o = OdgovorViewModel.getOdgovoriZaKviz(::onSuccessGetOdgovor, ::onError, kviz!!)

                }
            }
        }

        fun onSuccessGetOdgovor(odgovori: List<Odgovor>, p: Pitanje?) {
            GlobalScope.launch(Dispatchers.IO) {
                withContext(Dispatchers.Main) {
                    for(pitanje in pitanja!!) {
                        var x = false

                        for(odgovor in odgovori) {
                            if (odgovor.pitanjeId?.equals(pitanje.id)!!) {
                                x = true

                            }
                        }
                        if(!x)
                            OdgovorViewModel.postaviOdgovor(::onSuccessPostaviOdgovor, ::onError, kvizTaken?.id!!, pitanje.id!!, -1)
                    }
                }
            }
        }

        @SuppressLint("ShowToast")
        fun onError(poruka: String) {  }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        sharedViewModel = ViewModelProviders.of(this)[SharedViewModel::class.java]

        bottomNavigation = findViewById(R.id.bottomNav)
        bottomNavigation.setOnNavigationItemSelectedListener(OnNavigationItemSelectedListener)
        bottomNavigation.selectedItemId = R.id.kvizovi

        var hash: String? = null
        if (intent != null) {
            hash = intent.getStringExtra("payload")

            if(hash == null) {
                val uri = intent.data
                if(uri != null) {
                    val params = uri.pathSegments
                    hash = params[params.size - 1]
                }
                else hash = hashStudenta
            }
        }
        else
            hash = hashStudenta

        AccountViewModel.postaviAccount(::onSuccessPostaviAccount, hash!!, this)
        val fragmentKvizovi = FragmentKvizovi.newInstance()
        openFragmentWithBack(fragmentKvizovi)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onSuccessPostaviAccount() {
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                toast(AccountRepository.getHash())
                DBViewModel.updateNow(::onSuccessUpdate)
            }
        }
    }

    fun onSuccessUpdate(response: Boolean?) {
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                toast(response.toString())
            }

        }

    }

    fun updateBottomNavigation(mainFragment:Boolean) {
        val menu = bottomNavigation.menu
        if(mainFragment) {
            menu.getItem(0).setVisible(true)
            menu.getItem(1).setVisible(true)
            menu.getItem(2).setVisible(false)
            menu.getItem(3).setVisible(false)
        }
        else {
            menu.getItem(0).setVisible(false)
            menu.getItem(1).setVisible(false)
            menu.getItem(2).setVisible(true)
            menu.getItem(3).setVisible(true)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        sharedViewModel.tacanOdgovor = MutableLiveData()
        if(supportFragmentManager.fragments[0] is FragmentPokusaj){
            updateBottomNavigation(true)
            bottomNavigation.selectedItemId= R.id.kvizovi
            openFragmentWithBack(FragmentKvizovi.newInstance())
        }
    }

    private fun openFragmentWithBack(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun openFragmentWithoutBack(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

    override fun onResume() { super.onResume() }

    override fun onPause() { super.onPause() }
}
