package s1615548.coinz


import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.rule.GrantPermissionRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class GridViewSelectTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Rule
    @JvmField
    var mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION")

    @Test
    fun gridViewSelectTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(2500)

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(2500)

        val appCompatButton = onView(
                allOf(withId(R.id.btnTest), withText("for test"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()))
        appCompatButton.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(2500)

        val appCompatEditText = onView(
                allOf(withId(R.id.editTextCode),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()))
        appCompatEditText.perform(click())

        val appCompatEditText2 = onView(
                allOf(withId(R.id.editTextCode),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()))
        appCompatEditText2.perform(replaceText("1219"), closeSoftKeyboard())

        val appCompatEditText3 = onView(
                allOf(withId(R.id.editTextCode), withText("1219"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()))
        appCompatEditText3.perform(pressImeActionButton())

        val appCompatButton2 = onView(
                allOf(withId(R.id.btnActivate), withText("activate"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                8),
                        isDisplayed()))
        appCompatButton2.perform(click())

        val appCompatButton3 = onView(
                allOf(withId(R.id.btnAddWallet), withText("add coins to wallet"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()))
        appCompatButton3.perform(click())

        val appCompatButton4 = onView(
                allOf(withId(R.id.btnBackTest), withText("back"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()))
        appCompatButton4.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(2500)

        val appCompatButton5 = onView(
                allOf(withId(R.id.btnWallet), withText("Menu"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()))
        appCompatButton5.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(2500)

        val appCompatButton6 = onView(
                allOf(withId(R.id.btnToWallet), withText("wallet"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()))
        appCompatButton6.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(2500)

        val linearLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_wallet),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(4)
        linearLayout.perform(click())

        val linearLayout2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_wallet),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(1)
        linearLayout2.perform(click())

        val linearLayout3 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_wallet),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(5)
        linearLayout3.perform(click())

        val linearLayout4 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_wallet),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(4)
        linearLayout4.perform(click())

        val linearLayout5 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_wallet),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(2)
        linearLayout5.perform(click())

        val linearLayout6 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_wallet),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(2)
        linearLayout6.perform(click())

        val textView = onView(
                allOf(withId(R.id.txt), withText("4.4423"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.GV_wallet),
                                        5),
                                1),
                        isDisplayed()))
        textView.check(matches(withText("4.4423")))

        val textView2 = onView(
                allOf(withId(R.id.txt), withText("2.3333"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.GV_wallet),
                                        1),
                                1),
                        isDisplayed()))
        textView2.check(matches(withText("2.3333")))


        val linearLayout7 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_wallet),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(1)
        linearLayout7.perform(click())

        val linearLayout8 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_wallet),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(5)
        linearLayout8.perform(click())

        val appCompatButton7 = onView(
                allOf(withId(R.id.btnSaveToBank), withText("save to bank"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()))
        appCompatButton7.perform(click())


        val linearLayout9 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_wallet),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(5)
        linearLayout9.perform(click())

        val linearLayout10 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_wallet),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(2)
        linearLayout10.perform(click())

        val linearLayout11 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_wallet),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(1)
        linearLayout11.perform(click())

        val linearLayout12 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_wallet),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(2)
        linearLayout12.perform(click())

        val appCompatButton8 = onView(
                allOf(withId(R.id.btnSaveToBank), withText("save to bank"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()))
        appCompatButton8.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(2500)

        val appCompatButton9 = onView(
                allOf(withId(R.id.btnToBank), withText("bank"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()))
        appCompatButton9.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(2500)

        val textView5 = onView(
                allOf(withId(R.id.txt), withText("4.4423"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.GV_bank),
                                        1),
                                1),
                        isDisplayed()))
        textView5.check(matches(withText("4.4423")))

        val textView6 = onView(
                allOf(withId(R.id.txt), withText("2.3333"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.GV_bank),
                                        0),
                                1),
                        isDisplayed()))
        textView6.check(matches(withText("2.3333")))

        val linearLayout13 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_bank),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(1)
        linearLayout13.perform(click())

        val linearLayout14 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_bank),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(1)
        linearLayout14.perform(click())

        val linearLayout15 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_bank),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(0)
        linearLayout15.perform(click())

        val textView8 = onView(
                allOf(withId(R.id.golds_willGain), withText("you will gain 25 golds"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()))
        textView8.check(matches(withText("you will gain 25 golds")))

        val appCompatButton10 = onView(
                allOf(withId(R.id.btnConverToGold), withText("convert to Gold"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()))
        appCompatButton10.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(2500)

        val appCompatButton11 = onView(
                allOf(withId(R.id.btnToBank), withText("bank"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()))
        appCompatButton11.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(2500)

        val textView9 = onView(
                allOf(withId(R.id.txt), withText("4.4423"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.GV_bank),
                                        0),
                                1),
                        isDisplayed()))
        textView9.check(matches(withText("4.4423")))

        val textView10 = onView(
                allOf(withId(R.id.golds_number), withText("you have 25 golds"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()))
        textView10.check(matches(withText("you have 25 golds")))

        val textView11 = onView(
                allOf(withId(R.id.golds_willGain), withText("you will gain 0 golds"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()))
        textView11.check(matches(withText("you will gain 0 golds")))
    }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
