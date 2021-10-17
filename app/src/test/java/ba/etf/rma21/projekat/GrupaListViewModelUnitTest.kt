package ba.etf.rma21.projekat

import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.repositories.old.GrupaRepository
import ba.etf.rma21.projekat.viewmodel.GrupaListViewModel
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Test

class GrupaListViewModelUnitTest {
    @Test
    fun grupaRepoTest1() {
        val grupelvm = GrupaListViewModel()
        val grupe  = grupelvm.getAll()
        Assert.assertEquals(15, grupe.size)
    }

    @Test
    fun grupaRepoTest2(){
        val grupelvm = GrupaListViewModel()
        val predmet  = grupelvm.getAll()[1]
        val grupe = GrupaRepository.getUpisaneGrupe(predmet.naziv)
        Assert.assertEquals(0, grupe.size)
    }

    @Test
    fun grupaRepoTest3(){
        val grupelvm = GrupaListViewModel()

//        grupelvm.upisiGrupu(Grupa("RMA1", "Razvoj mobilnih aplikacija"))
//        grupelvm.upisiGrupu(Grupa("RMA2", "Razvoj mobilnih aplikacija"))
//        grupelvm.upisiGrupu(Grupa("RMA3", "Razvoj mobilnih aplikacija"))

        val grupe = grupelvm.getUpisaneGrupe("Razvoj mobilnih aplikacija")
        Assert.assertEquals(3, grupe.size)
    }

    @Test
    fun grupaRepoTest4(){
        val grupelvm = GrupaListViewModel()

//        grupelvm.upisiGrupu(Grupa("RMA1", "Razvoj mobilnih aplikacija"))
//        grupelvm.upisiGrupu(Grupa("RMA2", "Razvoj mobilnih aplikacija"))
//        grupelvm.upisiGrupu(Grupa("RMA3", "Razvoj mobilnih aplikacija"))

        val grupe = grupelvm.getUpisaneGrupe("Razvoj mobilnih aplikacija")
        MatcherAssert.assertThat(grupe, CoreMatchers.hasItem<Grupa>(Matchers.hasProperty("naziv", CoreMatchers.`is`("RMA1"))))
        MatcherAssert.assertThat(grupe, CoreMatchers.hasItem<Grupa>(Matchers.hasProperty("nazivPredmeta", CoreMatchers.`is`("Razvoj mobilnih aplikacija"))))

    }
}