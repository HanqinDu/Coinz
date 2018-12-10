package s1615548.coinz.Activity.Building

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_appleton_tower.*
import s1615548.coinz.Model.Coins
import s1615548.coinz.Model.DBHandler
import s1615548.coinz.Model.Golds
import s1615548.coinz.R
import s1615548.coinz.showToast
import java.util.*

class appletonTower_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appleton_tower)

        // set golds needed to activate
        val cost = 100

        // SQLite
        var db = DBHandler(this, name = "data.db", version = 1, factory = null)

        // set up text and button
        var result = ""
        introAppleton.text = "You may collect a random coin from the map with 100 golds"
        introAppleton2.text = "you have ${Golds.value.toInt()} golds"
        btnBuyAppleton.isEnabled = Golds.value >= cost && Coins.coin_OnMap.size > 0


        // Button 1: consuming golds, player are able to collect a random coin from the map
        btnBuyAppleton.setOnClickListener {

            // when there is at least one coin on map, execute random picking up
            if(Coins.coin_OnMap.size > 0){

                // collect random coin and move to wallet
                val random_index = Random().nextInt(Coins.coin_OnMap.size)
                Golds.reduceGold(cost.toDouble())
                result += "${Coins.coin_OnMap[random_index]} is collected\n"
                Coins.collect(random_index)

                // update text and button
                outputAppleton.text = result
                introAppleton2.text = "golds: ${Golds.value}"
                btnBuyAppleton.isEnabled = Golds.value >= cost && Coins.coin_OnMap.size > 0

                // save data
                val settings = getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE)
                val editor = settings.edit()
                editor.putString("gold", Golds.value.toString())
                editor.apply()
                db.saveWallet()

            }else{
                showToast("there is no coin on map")
            }
        }

        // Button 2: back
        btnBackAppleton.setOnClickListener {
            finish()
        }

    }
}
