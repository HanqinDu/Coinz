package s1615548.coinz.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_bank.*
import kotlinx.android.synthetic.main.layout_wallet.view.*
import s1615548.coinz.Adapter.coins_gridview_adaptor
import s1615548.coinz.Model.Coins
import s1615548.coinz.Model.Golds
import s1615548.coinz.Model.wallet_Layout
import s1615548.coinz.R
import s1615548.coinz.changeLight

class bank_Activity : AppCompatActivity() {

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

    var gold_will_gain: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank)

        val adapter = coins_gridview_adaptor(this, R.layout.layout_wallet, data)

        GV_bank.adapter = adapter

        golds_number.text = "you have ${Golds.value.toInt()} golds"
        golds_willGain.text = "you will gain 0 golds"

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

        btnConverToGold.setOnClickListener{
            var i = 0
            var difference = 0
            while(i<adapter.selectedPositions.size){
                if(adapter.selectedPositions[i]){
                    Coins.converToGold(i-difference)
                    difference++
                }
                i++
            }
            //showToast("$difference coins saved")
            finish()
        }

        btnBack.setOnClickListener{
            finish()
        }


    }
}
