package s1615548.coinz.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_wallet.*
import kotlinx.android.synthetic.main.layout_wallet.view.*
import s1615548.coinz.Adapter.coins_gridview_adaptor
import s1615548.coinz.Model.Coins
import s1615548.coinz.Model.wallet_Layout
import s1615548.coinz.R
import s1615548.coinz.changeLight
import s1615548.coinz.showToast

class wallet_Activity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)

       // val GV = this.findViewById(R.id.GV) as GridView

        val adapter = coins_gridview_adaptor(this, R.layout.layout_wallet, data)

        GV_wallet.adapter = adapter

        // text
        instruction.text = "bank capacity: ${Coins.coin_InBank.size}/${Coins.bank_capacity}"
        instruction2.text = "daily capacity: ${Coins.transfer_made}/${Coins.daily_capacity}"

        GV_wallet.setOnItemClickListener { parent, view, position, id ->
            if(adapter.selectedPositions[position]){
                adapter.selectedPositions[position] = false
                changeLight(view.img,0)
            }else{
                adapter.selectedPositions[position] = true
                changeLight(view.img,-80)
            }
        }

        btnSaveToBank.setOnClickListener{
            val number_of_selected_coin:Int = adapter.numSelect()

            when {
                number_of_selected_coin == 0 -> showToast("please select at least one coin")
                number_of_selected_coin + Coins.transfer_made > Coins.daily_capacity -> showToast("the number of coins exceed the daily capacity by " + (number_of_selected_coin + Coins.transfer_made - Coins.daily_capacity))
                number_of_selected_coin + Coins.coin_InBank.size > Coins.bank_capacity -> showToast("the number of coins exceed the bank capacity by " + (number_of_selected_coin + Coins.coin_InBank.size - Coins.bank_capacity))
                else -> {
                    var i = 0
                    var difference = 0
                    while(i<adapter.selectedPositions.size){
                        if(adapter.selectedPositions[i]){
                            Coins.moveToBank(i-difference)
                            difference++
                        }
                        i++
                    }
                    showToast("$difference coins saved")
                    finish()
                }
            }
        }

        btnBackWallet.setOnClickListener{
            finish()
        }

    }

    val data: ArrayList<wallet_Layout>
        get()
        {
            val item_list = ArrayList<wallet_Layout>()

            var i = 0
            while(i< Coins.coin_InWallet.size){
                when(Coins.coin_InWallet[i].currency){
                    "QUID" -> item_list.add(wallet_Layout(R.mipmap.quid, Coins.coin_InWallet[i].value.toString().substring(0..5)))
                    "PENY" -> item_list.add(wallet_Layout(R.mipmap.peny, Coins.coin_InWallet[i].value.toString().substring(0..5)))
                    "DOLR" -> item_list.add(wallet_Layout(R.mipmap.dolr, Coins.coin_InWallet[i].value.toString().substring(0..5)))
                    "SHIL" -> item_list.add(wallet_Layout(R.mipmap.shil, Coins.coin_InWallet[i].value.toString().substring(0..5)))
                }
                i++
            }

            return item_list

        }

}