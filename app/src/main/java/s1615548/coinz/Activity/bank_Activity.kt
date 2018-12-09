package s1615548.coinz.Activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_bank.*
import kotlinx.android.synthetic.main.layout_wallet.view.*
import s1615548.coinz.Adapter.coins_gridview_adaptor
import s1615548.coinz.Model.Coins
import s1615548.coinz.Model.DBHandler
import s1615548.coinz.Model.Golds
import s1615548.coinz.Model.wallet_Layout
import s1615548.coinz.R
import s1615548.coinz.changeLight
import s1615548.coinz.showToast

class bank_Activity : AppCompatActivity() {

    // used for recording the total value of coin is selected in grid view
    var gold_will_gain: Double = 0.0

    // SQLite
    var db = DBHandler(this, name = "data.db", version = 1, factory = null)

    // read data from coins in Bank
    val data: ArrayList<wallet_Layout>
        get()
        {
            val item_list = ArrayList<wallet_Layout>()

            var i = 0
            while(i< Coins.coin_InBank.size){
                when(Coins.coin_InBank[i].currency){
                    "QUID" -> item_list.add(wallet_Layout(R.mipmap.quid, Coins.coin_InBank[i].value.toString().substring(0..5)))
                    "PENY" -> item_list.add(wallet_Layout(R.mipmap.peny, Coins.coin_InBank[i].value.toString().substring(0..5)))
                    "DOLR" -> item_list.add(wallet_Layout(R.mipmap.dolr, Coins.coin_InBank[i].value.toString().substring(0..5)))
                    "SHIL" -> item_list.add(wallet_Layout(R.mipmap.shil, Coins.coin_InBank[i].value.toString().substring(0..5)))
                }
                i++
            }
            return item_list
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank)

        // set up adapter for grid view with data
        val adapter = coins_gridview_adaptor(this, R.layout.layout_wallet, data)
        GV_bank.adapter = adapter

        // set up text
        golds_number.text = "you have ${Golds.value.toInt()} golds"
        golds_willGain.text = "you will gain 0 golds"

        // // Button 1: Grid view item click - turn dark when item selected
        GV_bank.setOnItemClickListener { parent, view, position, id ->
            if(adapter.selectedPositions[position]){
                adapter.selectedPositions[position] = false
                changeLight(view.img,0)

                gold_will_gain -= Coins.value_bank(position)
                golds_willGain.text = "you will gain ${gold_will_gain.toInt()} golds"

            }else{
                adapter.selectedPositions[position] = true
                changeLight(view.img,-80)

                gold_will_gain += Coins.value_bank(position)
                golds_willGain.text = "you will gain ${gold_will_gain.toInt()} golds"
            }
        }

        // Button 2: Convert selected coins to golds
        btnConverToGold.setOnClickListener{

            // convert Coin to golds with loop
            var i = 0
            var difference = 0
            while(i<adapter.selectedPositions.size){
                if(adapter.selectedPositions[i]){
                    Coins.converToGold(i-difference)
                    difference++
                }
                i++
            }
            showToast("${gold_will_gain.toInt()} received")

            // save data
            val settings = getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE)
            val editor = settings.edit()
            editor.putString("gold", Golds.value.toString())
            editor.apply()
            db.saveBank()

            finish()
        }

        // Button 3: back
        btnBackBank.setOnClickListener{
            finish()
        }


    }
}
