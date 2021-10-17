package ba.etf.rma21.projekat.data.repositories.old

import ba.etf.rma21.projekat.data.models.Predmet
//import ba.etf.rma21.projekat.data.staticData.listaDefaultUpisanihPredmeta
import ba.etf.rma21.projekat.data.staticData.listaPredmeta

class PredmetRepository {
    companion object {

        private var upisaniPredmeti:ArrayList<Predmet> = ArrayList()

        private var sviPredmeti:List<Predmet> = listaPredmeta()

        fun getUpisani(): List<Predmet> {
            return upisaniPredmeti
        }

        fun getAll(): List<Predmet> {
            return sviPredmeti
        }

        fun upisiPredmet(noviPredmet: Predmet) {
            if(!upisaniPredmeti.contains(noviPredmet))
                upisaniPredmeti.add(noviPredmet)
        }

        fun getZaGodinu(godina: Int): ArrayList<Predmet> {
            return getAll().filter { predmet -> predmet.godina.equals(godina) }.toCollection(arrayListOf())
        }


    }

}