package ba.etf.rma21.projekat

import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.viewmodel.PredmetListViewModel
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Test

class PredmetListViewModelUnitTest {
    @Test
    fun predmetModelTest1() {
        val predmetlvm = PredmetListViewModel()
        val predmeti  = predmetlvm.getAll()
        Assert.assertEquals(10, predmeti.size)
    }

    @Test
    fun predmetModelTest2() {
        val predmetlvm = PredmetListViewModel()
        val predmeti  = predmetlvm.getPredmeteZaGodinu(1)
        Assert.assertEquals(3, predmeti.size)
    }
    

    @Test
    fun predmetModelTest4() {
        val predmetlvm = PredmetListViewModel()
        val predmeti = predmetlvm.getAll()

        MatcherAssert.assertThat(predmeti, CoreMatchers.hasItem<Predmet>(Matchers.hasProperty("naziv", CoreMatchers.`is`("Tehnike programiranja"))))
        MatcherAssert.assertThat(predmeti, CoreMatchers.hasItem<Predmet>(Matchers.hasProperty("naziv", CoreMatchers.`is`("Numericki algoritmi"))))
        MatcherAssert.assertThat(predmeti, CoreMatchers.hasItem<Predmet>(Matchers.hasProperty("godina", CoreMatchers.`is`(2))))

    }
}