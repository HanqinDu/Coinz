package s1615548.coinz.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_frindscoin.*
import kotlinx.android.synthetic.main.layout_wallet.view.*
import s1615548.coinz.Adapter.coins_gridview_adaptor
import s1615548.coinz.Model.Coins
import s1615548.coinz.Model.DBHandler
import s1615548.coinz.Model.wallet_Layout
import s1615548.coinz.R
import s1615548.coinz.changeLight
import s1615548.coinz.showToast

class frindscoin_Activity : AppCompatActivity() {

    // SQLite
    var db = DBHandler(this, name = "data.db", version = 1, factory = null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frindscoin)

        // GV adaptor
        val adapter = coins_gridview_adaptor(this, R.layout.layout_wallet, data)
        GV_wallet_mail.adapter = adapter

        // text
        instruction_mail_coin.text = "bank capacity: ${Coins.coin_InBank.size}/${Coins.bank_capacity}"

        GV_wallet_mail.setOnItemClickListener { parent, view, position, id ->
            if(adapter.selectedPositions[position]){
                adapter.selectedPositions[position] = false
                changeLight(view.img,0)
            }else{
                adapter.selectedPositions[position] = true
                changeLight(view.img,-80)
            }
        }

        // button

        btnSaveFCoin.setOnClickListener{
            val number_of_selected_coin:Int = adapter.numSelect()

            when {
                number_of_selected_coin == 0 -> showToast("please select at least one coin")
                number_of_selected_coin + Coins.coin_InBank.size > Coins.bank_capacity -> showToast("the number of coins exceed the bank capacity by " + (number_of_selected_coin + Coins.coin_InBank.size - Coins.bank_capacity))
                else -> {
                    var i = 0
                    var difference = 0
                    while(i<adapter.selectedPositions.size){
                        if(adapter.selectedPositions[i]){
                            Coins.moveToBankF(i-difference)
                            difference++
                        }
                        i++
                    }
                    showToast("$difference coins saved")

                    db.saveFWallet()
                    db.saveBank()

                    finish()
                }
            }
        }

        btnBackFCoin.setOnClickListener{
            finish()
        }
    }

    val data: ArrayList<wallet_Layout>
        get()
        {
            val item_list = ArrayList<wallet_Layout>()

            var i = 0
            while(i< Coins.coin_FromMail.size){
                when(Coins.coin_FromMail[i].currency){
                    "QUID" -> item_list.add(wallet_Layout(R.mipmap.quid, Coins.coin_FromMail[i].value.toString().substring(0..5)))
                    "PENY" -> item_list.add(wallet_Layout(R.mipmap.peny, Coins.coin_FromMail[i].value.toString().substring(0..5)))
                    "DOLR" -> item_list.add(wallet_Layout(R.mipmap.dolr, Coins.coin_FromMail[i].value.toString().substring(0..5)))
                    "SHIL" -> item_list.add(wallet_Layout(R.mipmap.shil, Coins.coin_FromMail[i].value.toString().substring(0..5)))
                }
                i++
            }

            return item_list

        }
}
