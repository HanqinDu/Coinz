package s1615548.coinz.Activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_wallet.*
import kotlinx.android.synthetic.main.layout_wallet.view.*
import s1615548.coinz.Adapter.coins_gridview_adaptor
import s1615548.coinz.Model.Coins
import s1615548.coinz.Model.DBHandler
import s1615548.coinz.Model.Golds
import s1615548.coinz.Model.wallet_Layout
import s1615548.coinz.R
import s1615548.coinz.changeLight
import s1615548.coinz.showToast

class wallet_Activity : AppCompatActivity(){

    // read data from wallet
    val data: ArrayList<wallet_Layout>
        get()
        {
            val item_list = ArrayList<wallet_Layout>()
            var value = ""

            for(c in Coins.coin_InWallet){
                value = c.value.toString()

                // avoid IndexOutOfBoundsException
                if(value.length > 5){
                    value = value.substring(0..5)
                }
                when(c.currency){
                    "QUID" -> item_list.add(wallet_Layout(R.mipmap.quid, value))
                    "PENY" -> item_list.add(wallet_Layout(R.mipmap.peny, value))
                    "DOLR" -> item_list.add(wallet_Layout(R.mipmap.dolr, value))
                    "SHIL" -> item_list.add(wallet_Layout(R.mipmap.shil, value))
                }
            }
            return item_list
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)

        // SQLite
        var db = DBHandler(this, name = "data.db", version = 1, factory = null)

        // val GV = this.findViewById(R.id.GV) as GridView

        val adapter = coins_gridview_adaptor(this, R.layout.layout_wallet, data)

        GV_wallet.adapter = adapter

        // text
        instruction.text = "bank capacity: ${Coins.coin_InBank.size}/${Coins.bank_capacity}"
        instruction2.text = "daily capacity: ${Coins.transfer_made}/${Coins.daily_capacity}"

        // Button: Grid view item click - turn dark when item selected
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

                    // Once the coin is removed from array list, the coins after that coin move forwards
                    // therefore, we should track the difference and use it to eliminate change in position
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

                    // save data

                    val settings = getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE)
                    val editor = settings.edit()
                    editor.putInt("transfermade", Coins.transfer_made)
                    editor.apply()

                    db.saveWallet()
                    db.saveBank()

                    finish()
                }
            }
        }

        btnBackWallet.setOnClickListener{
            finish()
        }
    }



}