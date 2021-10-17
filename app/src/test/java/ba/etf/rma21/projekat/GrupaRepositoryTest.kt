package ba.etf.rma21.projekat

import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.repositories.old.GrupaRepository
import ba.etf.rma21.projekat.data.repositories.old.PredmetRepository
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Test

class GrupaRepositoryTest {
    @Test
    fun grupaRepoTest1() {
        val grupe  = GrupaRepository.getAll()
        Assert.assertEquals(15, grupe.size)
    }

    @Test
    fun grupaRepoTest2(){
        val predmet = PredmetRepository.getAll()[1]
        val grupe = GrupaRepository.getUpisaneGrupe(predmet.naziv)
        Assert.assertEquals(0, grupe.size)
    }

    @Test
    fun grupaRepoTest3(){
//        GrupaRepository.upisiGrupu(Grupa("RMA1", "Razvoj mobilnih aplikacija"))
//        GrupaRepository.upisiGrupu(Grupa("RMA2", "Razvoj mobilnih aplikacija"))
//        GrupaRepository.upisiGrupu(Grupa("RMA3", "Razvoj mobilnih aplikacija"))

        val grupe = GrupaRepository.getUpisaneGrupe("Razvoj mobilnih aplikacija")
        Assert.assertEquals(3, grupe.size)
    }

    @Test
    fun grupaRepoTest4(){
//        GrupaRepository.upisiGrupu(Grupa("RMA1", "Razvoj mobilnih aplikacija"))
//        GrupaRepository.upisiGrupu(Grupa("RMA2", "Razvoj mobilnih aplikacija"))
//        GrupaRepository.upisiGrupu(Grupa("RMA3", "Razvoj mobilnih aplikacija"))

        val grupe = GrupaRepository.getUpisaneGrupe("Razvoj mobilnih aplikacija")
        MatcherAssert.assertThat(grupe, CoreMatchers.hasItem<Grupa>(Matchers.hasProperty("naziv", CoreMatchers.`is`("RMA1"))))
        MatcherAssert.assertThat(grupe, CoreMatchers.hasItem<Grupa>(Matchers.hasProperty("nazivPredmeta", CoreMatchers.`is`("Razvoj mobilnih aplikacija"))))

    }


}