package ba.etf.rma21.projekat


import android.content.res.Resources
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import ba.etf.rma21.projekat.data.repositories.PitanjeKvizRepository
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MySpirala2AndroidTest {

    @get:Rule
    val intentsTestRule = IntentsTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun upisNaPredmet() {
        onView(withId(R.id.predmeti)).perform(click())

        onView(withId(R.id.odabirGodina)).perform(click())
        Espresso.onData(CoreMatchers.allOf(CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)), CoreMatchers.`is`("2"))).perform(click())

        onView(withId(R.id.odabirPredmet)).perform(click())
        Espresso.onData(CoreMatchers.allOf(CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)), CoreMatchers.`is`("Numericki algoritmi"))).perform(click())

        onView(withId(R.id.odabirGrupa)).perform(click())
        Espresso.onData(CoreMatchers.allOf(CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)), CoreMatchers.`is`("NA"))).perform(click())

        onView(withId(R.id.dodajPredmetDugme)).perform(click())

        onView(withId(R.id.kvizovi)).perform(click())

        onView(withId(R.id.listaKvizova)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(CoreMatchers.allOf(hasDescendant(withText("NA1")),
                hasDescendant(withText("Numericki algoritmi"))), click()))

//        val pitanja = PitanjeKvizRepository.getQuestions("NA1", "Numericki algoritmi")
//        var i = 0
//        for (pitanje in pitanja) {
//            onView(withId(R.id.navigacijaPitanja)).perform(NavigationViewActions.navigateTo(i)).perform(click())
//            onView(withId(R.id.tekstPitanja)).check(matches(withText(pitanja[i].tekstPitanja)))
//            Espresso.onData(CoreMatchers.allOf(CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)), CoreMatchers.`is`(klikni(i)))).perform(click())
//            if(i % 2 == 0) Espresso.onData(CoreMatchers.allOf(CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)), CoreMatchers.`is`(klikni(i)))).perform(click()).check(matches(matchesBackgroundColor(R.color.tacan_odgovor)))
//            else Espresso.onData(CoreMatchers.allOf(CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)), CoreMatchers.`is`(klikni(i)))).perform(click()).check(matches(matchesBackgroundColor(R.color.netacan_odgovor)))
//            i++
//        }
//
//        onView(withId(R.id.predajKviz)).perform(click())
//        onView(withId(R.id.tvPoruka)).check(matches(withText("Završili ste kviz NA1 sa tačnosti 60.0")))

    }

    @Test
    fun provjeraPamcenjaUpisa() {
        onView(withId(R.id.predmeti)).perform(click())

        onView(withId(R.id.odabirGodina)).perform(click())
        Espresso.onData(CoreMatchers.allOf(CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)), CoreMatchers.`is`("2"))).perform(click())

        onView(withId(R.id.odabirPredmet)).perform(click())
        Espresso.onData(CoreMatchers.allOf(CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)), CoreMatchers.`is`("Diskretna matematika"))).perform(click())

        onView(withId(R.id.odabirGrupa)).perform(click())
        Espresso.onData(CoreMatchers.allOf(CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)), CoreMatchers.`is`("DM1"))).perform(click())

        onView(withId(R.id.dodajPredmetDugme)).perform(click())


        onView(withId(R.id.predmeti)).perform(click())
        onView(withId(R.id.odabirGodina)).check(matches(withSpinnerText(containsString("2"))))
        onView(withId(R.id.odabirPredmet)).check(matches(withSpinnerText(containsString("-"))))
        onView(withId(R.id.odabirGrupa)).check(matches(withSpinnerText(containsString("-"))))
    }

    @Test
    fun provjeraPamcenjaUpisaOdustajanje() {
        onView(withId(R.id.predmeti)).perform(click())

        onView(withId(R.id.odabirGodina)).perform(click())
        Espresso.onData(CoreMatchers.allOf(CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)), CoreMatchers.`is`("2"))).perform(click())

        onView(withId(R.id.odabirPredmet)).perform(click())
        Espresso.onData(CoreMatchers.allOf(CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)), CoreMatchers.`is`("Numericki algoritmi"))).perform(click())

        onView(withId(R.id.odabirGrupa)).perform(click())
        Espresso.onData(CoreMatchers.allOf(CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)), CoreMatchers.`is`("NA"))).perform(click())

        onView(withId(R.id.kvizovi)).perform(click())

        onView(withId(R.id.predmeti)).perform(click())
        onView(withId(R.id.odabirGodina)).check(matches(withSpinnerText(containsString("2"))))
        onView(withId(R.id.odabirPredmet)).check(matches(withSpinnerText(containsString("Numericki algoritmi"))))
        onView(withId(R.id.odabirGrupa)).check(matches(withSpinnerText(containsString("NA"))))
    }

    @Test
    fun zaustaviKviz(){
        onView(withId(R.id.listaKvizova)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(CoreMatchers.allOf(hasDescendant(withText("TP1")),
                hasDescendant(withText("Tehnike programiranja"))), click()))

//        val pitanja = PitanjeKvizRepository.getQuestions("TP1", "Tehnike programiranja")
//        var i = 0
//        for (pitanje in pitanja) {
//            onView(withId(R.id.navigacijaPitanja)).perform(NavigationViewActions.navigateTo(i)).perform(click())
//            onView(withId(R.id.tekstPitanja)).check(matches(withText(pitanja[i].tekstPitanja)))
//            Espresso.onData(CoreMatchers.allOf(CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)), CoreMatchers.`is`(klikni(i)))).perform(click())
//            if(i % 2 == 0)
//                Espresso.onData(CoreMatchers.allOf(CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)), CoreMatchers.`is`(klikni(i)))).perform(click()).check(matches(matchesBackgroundColor(R.color.tacan_odgovor)))
//            else
//                Espresso.onData(CoreMatchers.allOf(CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)), CoreMatchers.`is`(klikni(i)))).perform(click()).check(matches(matchesBackgroundColor(R.color.netacan_odgovor)))
//
//            if(i == 2) {
//                onView(withId(R.id.zaustaviKviz)).perform(click())
//                break
//            }
//            i++
//        }
//
//        onView(withId(R.id.listaKvizova)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(CoreMatchers.allOf(hasDescendant(withText("TP1")),
//                hasDescendant(withText("Tehnike programiranja"))), click()))
//
//        for(index in 0..2) {
//            onView(withId(R.id.navigacijaPitanja)).perform(NavigationViewActions.navigateTo(index)).perform(click())
//            onView(withId(R.id.odgovoriLista)).check(matches(not(isEnabled())))
//            if(index % 2 == 0)
//                Espresso.onData(CoreMatchers.allOf(CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)), CoreMatchers.`is`(klikni(index)))).check(matches(matchesBackgroundColor(R.color.tacan_odgovor)))
//            else
//                Espresso.onData(CoreMatchers.allOf(CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)), CoreMatchers.`is`(klikni(index)))).check(matches(matchesBackgroundColor(R.color.netacan_odgovor)))
//        }
//        for(index in 3..4) {
//            onView(withId(R.id.navigacijaPitanja)).perform(NavigationViewActions.navigateTo(index)).perform(click())
//            onView(withId(R.id.odgovoriLista)).check(matches(isEnabled()))
//        }
//
//        onView(withId(R.id.predajKviz)).perform(click())
//
//        for(index in 0..4) {
//            onView(withId(R.id.navigacijaPitanja)).perform(NavigationViewActions.navigateTo(index)).perform(click())
//            onView(withId(R.id.odgovoriLista)).check(matches(not(isEnabled())))
//        }

    }

    private fun matchesBackgroundColor(expectedResourceId: Int): Matcher<View?>? {
        return object : BoundedMatcher<View?, View>(View::class.java) {
            var actualColor = 0
            var expectedColor = 0
            var message: String? = null
            override fun matchesSafely(item: View): Boolean {
                if (item.getBackground() == null) {
                    message = item.getId().toString() + " does not have a background"
                    return false
                }
                val resources: Resources = item.getContext().getResources()
                expectedColor = ResourcesCompat.getColor(resources, expectedResourceId, null)
                actualColor = try {
                    (item.getBackground() as ColorDrawable).color
                } catch (e: Exception) {
                    (item.getBackground() as GradientDrawable).color!!.defaultColor
                }
                return actualColor == expectedColor
            }

            override fun describeTo(description: Description) {
                if (actualColor != 0) {
                    message = ("Background color did not match: Expected "
                            + String.format("#%06X", 0xFFFFFF and expectedColor) + " was " + String.format("#%06X", 0xFFFFFF and actualColor))
                }
                description.appendText(message)
            }
        }
    }

    private fun klikni(i: Int): String {
        if(i % 2 != 0) return "1"
        return (i + 3).toString()
    }
}