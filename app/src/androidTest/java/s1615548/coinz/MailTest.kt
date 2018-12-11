package s1615548.coinz


import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
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
class MailTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Rule
    @JvmField
    var mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION")

    @Test
    fun mailTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val appCompatButton = onView(
                allOf(withId(R.id.btnWallet), withText("Menu"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()))
        appCompatButton.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val appCompatButton2 = onView(
                allOf(withId(R.id.btnMail), withText("mail"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                8),
                        isDisplayed()))
        appCompatButton2.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val appCompatEditText = onView(
                allOf(withId(R.id.input_email),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()))
        appCompatEditText.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val textView = onView(
                allOf(withId(R.id.textLogin), withText("Account"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()))
        textView.check(matches(withText("Account")))

        val appCompatEditText2 = onView(
                allOf(withId(R.id.input_email),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()))
        appCompatEditText2.perform(replaceText("1159698816@qq.com"), closeSoftKeyboard())

        val appCompatEditText3 = onView(
                allOf(withId(R.id.input_password),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()))
        appCompatEditText3.perform(replaceText("123456"), closeSoftKeyboard())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val appCompatButton3 = onView(
                allOf(withId(R.id.btnLogin), withText("sign in"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()))
        appCompatButton3.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val appCompatButton4 = onView(
                allOf(withId(R.id.btnMail), withText("mail"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                8),
                        isDisplayed()))
        appCompatButton4.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val appCompatButton5 = onView(
                allOf(withId(R.id.btnBackMail), withText("Back"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()))
        appCompatButton5.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val appCompatButton6 = onView(
                allOf(withId(R.id.btnManageCoinBack), withText("back"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()))
        appCompatButton6.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val appCompatButton7 = onView(
                allOf(withId(R.id.btnTest), withText("for test"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()))
        appCompatButton7.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val appCompatEditText4 = onView(
                allOf(withId(R.id.editTextCode),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()))
        appCompatEditText4.perform(click())

        val appCompatEditText5 = onView(
                allOf(withId(R.id.editTextCode),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()))
        appCompatEditText5.perform(replaceText("1219"), closeSoftKeyboard())

        val appCompatEditText6 = onView(
                allOf(withId(R.id.editTextCode), withText("1219"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()))
        appCompatEditText6.perform(pressImeActionButton())

        val appCompatButton8 = onView(
                allOf(withId(R.id.btnActivate), withText("activate"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                8),
                        isDisplayed()))
        appCompatButton8.perform(click())

        val appCompatButton9 = onView(
                allOf(withId(R.id.btnAddWallet), withText("add coins to wallet"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()))
        appCompatButton9.perform(click())

        val appCompatButton10 = onView(
                allOf(withId(R.id.btnBackTest), withText("back"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()))
        appCompatButton10.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val appCompatButton11 = onView(
                allOf(withId(R.id.btnWallet), withText("Menu"),
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
        Thread.sleep(7000)

        val appCompatButton12 = onView(
                allOf(withId(R.id.btnMail), withText("mail"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                8),
                        isDisplayed()))
        appCompatButton12.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val appCompatButton13 = onView(
                allOf(withId(R.id.btnSendMail), withText("send coins to others"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()))
        appCompatButton13.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val textInputEditText = onView(
                allOf(withId(R.id.text_userID),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.textInputLayout),
                                        0),
                                0),
                        isDisplayed()))
        textInputEditText.perform(longClick())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(50)

        val linearLayout = onView(
                allOf(withContentDescription("Paste"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(`is`("android.widget.RelativeLayout")),
                                        0),
                                0),
                        isDisplayed()))
        linearLayout.perform(click())

        val textInputEditText2 = onView(
                allOf(withId(R.id.text_userID),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.textInputLayout),
                                        0),
                                0),
                        isDisplayed()))
        textInputEditText2.perform(replaceText("wVoqt4Xko9OuBTNughb5qjx0eZJ2"), closeSoftKeyboard())

        val textInputEditText3 = onView(
                allOf(withId(R.id.text_notes),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.textInputLayout2),
                                        0),
                                0),
                        isDisplayed()))
        textInputEditText3.perform(replaceText("Hi"), closeSoftKeyboard())

        val linearLayout2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_send),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                1)))
                .atPosition(0)
        linearLayout2.perform(click())

        pressBack()

        val linearLayout3 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_send),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                1)))
                .atPosition(1)
        linearLayout3.perform(click())

        val linearLayout4 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_send),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                1)))
                .atPosition(2)
        linearLayout4.perform(click())

        val appCompatButton14 = onView(
                allOf(withId(R.id.btnSend), withText("send"),
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
        Thread.sleep(7000)

        val appCompatButton15 = onView(
                allOf(withId(R.id.btnBackMail), withText("Back"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()))
        appCompatButton15.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val appCompatButton16 = onView(
                allOf(withId(R.id.btnToWallet), withText("wallet"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()))
        appCompatButton16.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val textView2 = onView(
                allOf(withId(R.id.txt), withText("4.4423"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.GV_wallet),
                                        2),
                                1),
                        isDisplayed()))
        textView2.check(matches(withText("4.4423")))

        val textView3 = onView(
                allOf(withId(R.id.txt), withText("6.6789"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.GV_wallet),
                                        1),
                                1),
                        isDisplayed()))
        textView3.check(matches(withText("6.6789")))

        val textView4 = onView(
                allOf(withId(R.id.txt), withText("7.7777"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.GV_wallet),
                                        0),
                                1),
                        isDisplayed()))
        textView4.check(matches(withText("7.7777")))

        val appCompatButton17 = onView(
                allOf(withId(R.id.btnBackWallet), withText("back"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()))
        appCompatButton17.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val appCompatButton18 = onView(
                allOf(withId(R.id.btnManageCoinBack), withText("back"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()))
        appCompatButton18.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val appCompatButton19 = onView(
                allOf(withId(R.id.btnSignout), withText("sign out"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()))
        appCompatButton19.perform(click())

        val appCompatButton20 = onView(
                allOf(withId(R.id.btnWallet), withText("Menu"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()))
        appCompatButton20.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val appCompatButton21 = onView(
                allOf(withId(R.id.btnMail), withText("mail"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                8),
                        isDisplayed()))
        appCompatButton21.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val textView5 = onView(
                allOf(withId(R.id.textLogin), withText("Account"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()))
        textView5.check(matches(withText("Account")))

        val appCompatEditText7 = onView(
                allOf(withId(R.id.input_email),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()))
        appCompatEditText7.perform(click())

        val appCompatEditText8 = onView(
                allOf(withId(R.id.input_email),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()))
        appCompatEditText8.perform(replaceText("q1159698816@gmail.com"), closeSoftKeyboard())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val appCompatEditText9 = onView(
                allOf(withId(R.id.input_password),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()))
        appCompatEditText9.perform(longClick())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(50)

        val appCompatButton22 = onView(
                allOf(withId(R.id.btnCreate), withText("Create new account"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()))
        appCompatButton22.perform(click())

        val linearLayout5 = onView(
                allOf(withContentDescription("Paste"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(`is`("android.widget.RelativeLayout")),
                                        0),
                                0),
                        isDisplayed()))
        linearLayout5.perform(click())

        val appCompatEditText10 = onView(
                allOf(withId(R.id.input_password),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()))
        appCompatEditText10.perform(replaceText("XXX1a3j5y7zsyzm"), closeSoftKeyboard())

        val appCompatButton23 = onView(
                allOf(withId(R.id.btnLogin), withText("sign in"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()))
        appCompatButton23.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val appCompatButton24 = onView(
                allOf(withId(R.id.btnMail), withText("mail"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                8),
                        isDisplayed()))
        appCompatButton24.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val textView6 = onView(
                allOf(withId(R.id.mail_title), withText("Hi"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recyclerView),
                                        0),
                                0),
                        isDisplayed()))
        textView6.check(matches(withText("Hi")))

        val textView7 = onView(
                allOf(withId(R.id.coins_number), withText("Contains 3 coins"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recyclerView),
                                        0),
                                1),
                        isDisplayed()))
        textView7.check(matches(withText("Contains 3 coins")))

        val textView8 = onView(
                allOf(withId(R.id.textMail), withText("Mail"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()))
        textView8.check(matches(withText("Mail")))

        val appCompatButton25 = onView(
                allOf(withId(R.id.btnCollect), withText("collect all"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()))
        appCompatButton25.perform(click())

        val appCompatButton26 = onView(
                allOf(withId(R.id.btnBackMail), withText("Back"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()))
        appCompatButton26.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val appCompatButton27 = onView(
                allOf(withId(R.id.btn_CoinsFF), withText("coins from friend"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                9),
                        isDisplayed()))
        appCompatButton27.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val textView9 = onView(
                allOf(withId(R.id.txt), withText("12.345"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.GV_wallet_mail),
                                        2),
                                1),
                        isDisplayed()))
        textView9.check(matches(withText("12.345")))

        val textView10 = onView(
                allOf(withId(R.id.txt), withText("2.3333"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.GV_wallet_mail),
                                        1),
                                1),
                        isDisplayed()))
        textView10.check(matches(withText("2.3333")))

        val textView11 = onView(
                allOf(withId(R.id.txt), withText("10.987"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.GV_wallet_mail),
                                        0),
                                1),
                        isDisplayed()))
        textView11.check(matches(withText("10.987")))

        val textView12 = onView(
                allOf(withId(R.id.textViewSecondWallet), withText("Second Wallet"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()))
        textView12.check(matches(withText("Second Wallet")))

        val linearLayout6 = onData(anything())
                .inAdapterView(allOf(withId(R.id.GV_wallet_mail),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
                .atPosition(1)
        linearLayout6.perform(click())

        val appCompatButton28 = onView(
                allOf(withId(R.id.btnSaveFCoin), withText("save to bank"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()))
        appCompatButton28.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val appCompatButton29 = onView(
                allOf(withId(R.id.btnToBank), withText("bank"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()))
        appCompatButton29.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val textView13 = onView(
                allOf(withId(R.id.txt), withText("2.3333"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.GV_bank),
                                        0),
                                1),
                        isDisplayed()))
        textView13.check(matches(withText("2.3333")))

        val appCompatButton30 = onView(
                allOf(withId(R.id.btnBackBank), withText("back"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()))
        appCompatButton30.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val appCompatButton31 = onView(
                allOf(withId(R.id.btn_CoinsFF), withText("coins from friend"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                9),
                        isDisplayed()))
        appCompatButton31.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val textView14 = onView(
                allOf(withId(R.id.txt), withText("12.345"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.GV_wallet_mail),
                                        1),
                                1),
                        isDisplayed()))
        textView14.check(matches(withText("12.345")))

        val textView15 = onView(
                allOf(withId(R.id.txt), withText("10.987"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.GV_wallet_mail),
                                        0),
                                1),
                        isDisplayed()))
        textView15.check(matches(withText("10.987")))

        val textView16 = onView(
                allOf(withId(R.id.instruction_mail_coin), withText("bank capacity: 1/300"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()))
        textView16.check(matches(withText("bank capacity: 1/300")))
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
