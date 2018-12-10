package s1615548.coinz.Activity.Building

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_library.*
import s1615548.coinz.Model.Chest
import s1615548.coinz.Model.Golds
import s1615548.coinz.R

class library_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library)

        // golds needed to activate building's function
        val cost = 350

        // set up text and button
        var numberOfResult = 0
        var result = ""
        introLibrary.text = "You may buy information relates to the location of the chest with 350 golds"
        introLibrary2.text = "you have ${Golds.value.toInt()} golds"
        btnBuyLibrary.isEnabled = Golds.value >= cost

        // Button 1: by consuming golds, player may get tips about the code for opening the chest
        btnBuyLibrary.setOnClickListener {

            // get tips
            Golds.reduceGold(cost.toDouble())
            numberOfResult++
            result += numberOfResult.toString() + ", " + Chest.tipCode() + "\n"

            // refresh text and button
            outputLibrary.text = result
            introLibrary2.text = "golds: ${Golds.value}"
            btnBuyLibrary.isEnabled = Golds.value >= cost

            // save data
            val settings = getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE)
            val editor = settings.edit()
            editor.putString("gold", Golds.value.toString())
            editor.apply()

        }

        // Button 2: back
        btnBackLibrary.setOnClickListener {
            finish()
        }

    }
}
