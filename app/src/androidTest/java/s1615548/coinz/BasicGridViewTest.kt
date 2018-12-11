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
class BasicGridViewTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Rule
    @JvmField
    var mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION")

    @Test
    fun basicGridViewTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

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
        Thread.sleep(7000)

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
                allOf(withId(R.id.btnAddBank), withText("add coins to bank"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()))
        appCompatButton4.perform(click())

        val appCompatButton5 = onView(
                allOf(withId(R.id.btnAddFWallet), withText("add coins to second wallet"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()))
        appCompatButton5.perform(click())

        val appCompatButton6 = onView(
                allOf(withId(R.id.btnBackTest), withText("back"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()))
        appCompatButton6.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val appCompatButton7 = onView(
                allOf(withId(R.id.btnWallet), withText("Menu"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()))
        appCompatButton7.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val appCompatButton8 = onView(
                allOf(withId(R.id.btnToWallet), withText("wallet"),
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
        Thread.sleep(7000)

        val imageView = onView(
                allOf(withId(R.id.img),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.GV_wallet),
                                        5),
                                0),
                        isDisplayed()))
        imageView.check(matches(isDisplayed()))

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
                allOf(withId(R.id.instruction), withText("bank capacity: 6/300"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()))
        textView2.check(matches(withText("bank capacity: 6/300")))

        val textView3 = onView(
                allOf(withId(R.id.instruction2), withText("daily capacity: 0/25"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()))
        textView3.check(matches(withText("daily capacity: 0/25")))

        val textView4 = onView(
                allOf(withId(R.id.txt), withText("2.3333"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.GV_wallet),
                                        1),
                                1),
                        isDisplayed()))
        textView4.check(matches(withText("2.3333")))

        val textView5 = onView(
                allOf(withId(R.id.txt), withText("12.345"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.GV_wallet),
                                        2),
                                1),
                        isDisplayed()))
        textView5.check(matches(withText("12.345")))

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val appCompatButton9 = onView(
                allOf(withId(R.id.btnBackWallet), withText("back"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()))
        appCompatButton9.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val appCompatButton10 = onView(
                allOf(withId(R.id.btnToBank), withText("bank"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()))
        appCompatButton10.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val textView6 = onView(
                allOf(withId(R.id.golds_number), withText("you have 0 golds"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()))
        textView6.check(matches(withText("you have 0 golds")))

        val textView7 = onView(
                allOf(withId(R.id.golds_willGain), withText("you will gain 0 golds"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()))
        textView7.check(matches(withText("you will gain 0 golds")))

        val imageView2 = onView(
                allOf(withId(R.id.img),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.GV_bank),
                                        5),
                                0),
                        isDisplayed()))
        imageView2.check(matches(isDisplayed()))

        val textView8 = onView(
                allOf(withId(R.id.txt), withText("4.4423"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.GV_bank),
                                        5),
                                1),
                        isDisplayed()))
        textView8.check(matches(withText("4.4423")))

        val textView9 = onView(
                allOf(withId(R.id.txt), withText("2.3333"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.GV_bank),
                                        1),
                                1),
                        isDisplayed()))
        textView9.check(matches(withText("2.3333")))

        val textView10 = onView(
                allOf(withId(R.id.txt), withText("2.3333"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.GV_bank),
                                        1),
                                1),
                        isDisplayed()))
        textView10.check(matches(withText("2.3333")))

        val linearLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_bank),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(0)
        linearLayout.perform(click())

        val linearLayout2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_bank),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(2)
        linearLayout2.perform(click())

        val linearLayout3 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_bank),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(3)
        linearLayout3.perform(click())

        val appCompatButton11 = onView(
                allOf(withId(R.id.btnBackBank), withText("back"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()))
        appCompatButton11.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val appCompatButton12 = onView(
                allOf(withId(R.id.btn_CoinsFF), withText("coins from friend"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                9),
                        isDisplayed()))
        appCompatButton12.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val imageView3 = onView(
                allOf(withId(R.id.img),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.GV_wallet_mail),
                                        5),
                                0),
                        isDisplayed()))
        imageView3.check(matches(isDisplayed()))

        val textView11 = onView(
                allOf(withId(R.id.txt), withText("4.4423"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.GV_wallet_mail),
                                        5),
                                1),
                        isDisplayed()))
        textView11.check(matches(withText("4.4423")))

        val textView12 = onView(
                allOf(withId(R.id.txt), withText("2.3333"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.GV_wallet_mail),
                                        1),
                                1),
                        isDisplayed()))
        textView12.check(matches(withText("2.3333")))

        val textView13 = onView(
                allOf(withId(R.id.instruction_mail_coin), withText("bank capacity: 6/300"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()))
        textView13.check(matches(withText("bank capacity: 6/300")))
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
