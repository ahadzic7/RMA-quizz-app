package ba.etf.rma21.projekat

import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.data.repositories.old.PredmetRepository
import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.MatcherAssert
import org.junit.Assert
import org.junit.Test
import org.hamcrest.CoreMatchers.`is` as Is
import org.hamcrest.Matchers.*

class PredmetRepositoryTest {
    @Test
    fun PredmetRepoTest1() {
        val predmeti  = PredmetRepository.getAll()
        Assert.assertEquals(10, predmeti.size)
    }

    @Test
    fun PredmetRepoTest2() {
        val predmeti  = PredmetRepository.getZaGodinu(2)
        Assert.assertEquals(3, predmeti.size)
    }

    @Test
    fun PredmetRepoTest3() {
        val predmet = PredmetRepository.getAll()[0]
        PredmetRepository.upisiPredmet(predmet)
        val predmeti  = PredmetRepository.getUpisani()
        Assert.assertEquals(1, predmeti.size)
        Assert.assertEquals(predmet, predmeti[0])
    }

    @Test
    fun predmetRepoTest4() {
        val predmeti = PredmetRepository.getAll()

        MatcherAssert.assertThat(predmeti, hasItem<Predmet>(hasProperty("naziv", Is("Tehnike programiranja"))))
        MatcherAssert.assertThat(predmeti, hasItem<Predmet>(hasProperty("naziv", Is("Numericki algoritmi"))))
        MatcherAssert.assertThat(predmeti, hasItem<Predmet>(hasProperty("godina", Is(2))))

    }
}