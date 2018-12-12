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
class DailyCapacityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Rule
    @JvmField
    var mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION")

    @Test
    fun dailyCapacityTest() {
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

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(2500)

        val appCompatButton2 = onView(
                allOf(withId(R.id.btnActivate), withText("activate"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                8),
                        isDisplayed()))
        appCompatButton2.perform(click())

        Thread.sleep(700)

        val appCompatButton3 = onView(
                allOf(withId(R.id.btnAddWallet), withText("add coins to wallet"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()))
        appCompatButton3.perform(click())

        Thread.sleep(700)

        val appCompatButton4 = onView(
                allOf(withId(R.id.btnAddWallet), withText("add coins to wallet"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()))
        appCompatButton4.perform(click())

        Thread.sleep(300)

        val appCompatButton5 = onView(
                allOf(withId(R.id.btnAddWallet), withText("add coins to wallet"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()))
        appCompatButton5.perform(click())

        Thread.sleep(300)

        val appCompatButton6 = onView(
                allOf(withId(R.id.btnAddWallet), withText("add coins to wallet"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()))
        appCompatButton6.perform(click())

        Thread.sleep(300)

        val appCompatButton7 = onView(
                allOf(withId(R.id.btnAddWallet), withText("add coins to wallet"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()))
        appCompatButton7.perform(click())

        Thread.sleep(300)

        val appCompatButton8 = onView(
                allOf(withId(R.id.btnBackTest), withText("back"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()))
        appCompatButton8.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(2500)

        val appCompatButton9 = onView(
                allOf(withId(R.id.btnWallet), withText("Menu"),
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

        val appCompatButton10 = onView(
                allOf(withId(R.id.btnToWallet), withText("wallet"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()))
        appCompatButton10.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(2500)

        val linearLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_wallet),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(0)
        linearLayout.perform(click())

        val linearLayout2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_wallet),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(5)
        linearLayout2.perform(click())

        val linearLayout3 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_wallet),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(8)
        linearLayout3.perform(click())

        val linearLayout4 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_wallet),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(12)
        linearLayout4.perform(click())

        val linearLayout5 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_wallet),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(7)
        linearLayout5.perform(click())

        val linearLayout6 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_wallet),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(3)
        linearLayout6.perform(click())

        val linearLayout7 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_wallet),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(4)
        linearLayout7.perform(click())

        val linearLayout8 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_wallet),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(17)
        linearLayout8.perform(click())

        val linearLayout9 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_wallet),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(17)
        linearLayout9.perform(click())

        val linearLayout10 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_wallet),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(18)
        linearLayout10.perform(click())

        val linearLayout11 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_wallet),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(13)
        linearLayout11.perform(click())

        val linearLayout12 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_wallet),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(14)
        linearLayout12.perform(click())

        val linearLayout13 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_wallet),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(9)
        linearLayout13.perform(click())

        val linearLayout14 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_wallet),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(2)
        linearLayout14.perform(click())

        val linearLayout15 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_wallet),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(28)
        linearLayout15.perform(click())

        val linearLayout16 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_wallet),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(29)
        linearLayout16.perform(click())

        val linearLayout17 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_wallet),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(22)
        linearLayout17.perform(click())

        val linearLayout18 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_wallet),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(21)
        linearLayout18.perform(click())

        val linearLayout19 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_wallet),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(15)
        linearLayout19.perform(click())

        val linearLayout20 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_wallet),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(16)
        linearLayout20.perform(click())

        Thread.sleep(300)

        val textView = onView(
                allOf(withId(R.id.instruction2_wallet), withText("daily capacity: 0/25"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()))
        textView.check(matches(withText("daily capacity: 0/25")))

        val appCompatButton11 = onView(
                allOf(withId(R.id.btnSaveToBank), withText("save to bank"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()))
        appCompatButton11.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(2500)

        val appCompatButton12 = onView(
                allOf(withId(R.id.btnToWallet), withText("wallet"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()))
        appCompatButton12.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(2500)

        val textView2 = onView(
                allOf(withId(R.id.instruction2_wallet), withText("daily capacity: 18/25"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()))
        textView2.check(matches(withText("daily capacity: 18/25")))

        val textView3 = onView(
                allOf(withId(R.id.txt), withText("4.4423"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.GV_wallet),
                                        11),
                                1),
                        isDisplayed()))
        textView3.check(matches(withText("4.4423")))

        val linearLayout21 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_wallet),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(4)
        linearLayout21.perform(click())

        val linearLayout22 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_wallet),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(9)
        linearLayout22.perform(click())

        val linearLayout23 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_wallet),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(3)
        linearLayout23.perform(click())

        val linearLayout24 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_wallet),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(11)
        linearLayout24.perform(click())

        val linearLayout25 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_wallet),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(7)
        linearLayout25.perform(click())

        val linearLayout26 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_wallet),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(10)
        linearLayout26.perform(click())

        val linearLayout27 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_wallet),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(1)
        linearLayout27.perform(click())

        Thread.sleep(700)

        val appCompatButton13 = onView(
                allOf(withId(R.id.btnSaveToBank), withText("save to bank"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()))
        appCompatButton13.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(2500)

        val appCompatButton14 = onView(
                allOf(withId(R.id.btnToWallet), withText("wallet"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()))
        appCompatButton14.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(2500)

        val linearLayout28 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_wallet),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(2)
        linearLayout28.perform(click())

        Thread.sleep(300)

        val appCompatButton15 = onView(
                allOf(withId(R.id.btnSaveToBank), withText("save to bank"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()))
        appCompatButton15.perform(click())

        Thread.sleep(300)

        val textView4 = onView(
                allOf(withId(R.id.instruction2_wallet), withText("daily capacity: 25/25"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()))
        textView4.check(matches(withText("daily capacity: 25/25")))
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
