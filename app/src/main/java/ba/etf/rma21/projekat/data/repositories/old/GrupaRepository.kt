package ba.etf.rma21.projekat.data.repositories.old

import ba.etf.rma21.projekat.data.models.Grupa
//import ba.etf.rma21.projekat.data.staticData.listDefaultUpisanihGrupa
import ba.etf.rma21.projekat.data.staticData.listaGrupa

class GrupaRepository {
    companion object {
        init {
            // TODO: Implementirati
        }

        private var sveGrupe = listaGrupa()

        private var upisaneGrupe:ArrayList<Grupa> = ArrayList() //listDefaultUpisanihGrupa() as ArrayList<Grupa>

        fun getAll() = sveGrupe


        fun getGroupsByPredmet(nazivPredmeta: String): List<Grupa> {
            return sveGrupe.filter { grupa -> grupa.nazivPredmeta.equals(nazivPredmeta) }.toCollection(arrayListOf())
        }

        fun upisiGrupu(grupa: Grupa) {
            if(!upisaneGrupe.contains(grupa))
                upisaneGrupe.add(grupa)
        }

        fun getUpisaneGrupe(nazivPredmeta: String): ArrayList<Grupa> {
            return upisaneGrupe.filter { grupa -> grupa.nazivPredmeta.equals(nazivPredmeta) }.toCollection(arrayListOf())
        }
    }
}