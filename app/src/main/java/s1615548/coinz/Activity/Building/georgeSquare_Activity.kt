package s1615548.coinz.Activity.Building

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mapbox.mapboxsdk.geometry.LatLng
import kotlinx.android.synthetic.main.activity_george_square.*
import s1615548.coinz.Model.Chest
import s1615548.coinz.Model.Golds
import s1615548.coinz.R

class georgeSquare_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_george_square)

        // golds needed to activate building's function
        val cost = 200

        // set up text and button
        introGeorge.text = "You may get a tip about the location of the chest with 200 golds"
        introGeorge2.text = "you have ${Golds.value.toInt()} golds"
        btnBuyGeorge.isEnabled = Golds.value >= cost

        // Button 1: consuming golds, player get information about where the chest is
        btnBuyGeorge.setOnClickListener {

            // show tips
            Golds.reduceGold(cost.toDouble())
            outputGeorge.text = Chest.tipLocation(LatLng(55.943585,-3.188791))

            // refresh text
            introGeorge2.text = "golds: ${Golds.value}"

            // save data
            val settings = getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE)
            val editor = settings.edit()
            editor.putString("gold", Golds.value.toString())
            editor.apply()

            // disable the button since it is no need to use the function twice
            btnBuyGeorge.isEnabled = false
        }

        // Button 2: back
        btnBackGeorge.setOnClickListener {
            finish()
        }
    }

}
