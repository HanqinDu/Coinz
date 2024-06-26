package s1615548.coinz

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.activity_test.*
import s1615548.coinz.Model.Chest
import s1615548.coinz.Model.Coin
import s1615548.coinz.Model.Coins
import s1615548.coinz.Model.Golds

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        btnDeleteData.isEnabled = false
        btnAddWallet.isEnabled = false
        btnAddBank.isEnabled = false
        btnAddFWallet.isEnabled = false
        btnGetGold.isEnabled = false
        btnGetChest.isEnabled = false

        val c1 = Coin("001",10.987654,"PENY",type = curToInt("PENY"))
        val c2 = Coin("002",6.678945,"SHIL",type = curToInt("SHIL"))
        val c3 = Coin("003",12.345678,"SHIL",type = curToInt("SHIL"))
        val c4 = Coin("004",7.77777,"SHIL",type = curToInt("SHIL"))
        val c5 = Coin("005",4.44233,"QUID",type = curToInt("QUID"))
        val c6 = Coin("006",2.333333,"DOLR",type = curToInt("DOLR"))

        btnActivate.setOnClickListener {
            if(editTextCode.text.toString() == "1219"){
                btnDeleteData.isEnabled = true
                btnAddWallet.isEnabled = true
                btnAddBank.isEnabled = true
                btnAddFWallet.isEnabled = true
                btnGetGold.isEnabled = true
                btnGetChest.isEnabled = true

                Coins.rate_DOLR = 11.111
                Coins.rate_PENY = 22.222
                Coins.rate_QUID = 33.333
                Coins.rate_SHIL = 44.444

                Chest.solution[0] = 1
                Chest.solution[1] = 2
                Chest.solution[2] = 3
                Chest.solution[3] = 4

                Chest.shovel = 3
                Chest.chest_State = 0

                Chest.result = ""
                Chest.attempt = 8

                Golds.value = 0.0

                Coins.coin_InWallet.clear()
                Coins.coin_InBank.clear()
                Coins.coin_FromMail.clear()
                Coins.coin_OnMap.clear()

                Coins.downloadDate = ""
                Coins.send_made = 0
                Coins.transfer_made = 0
            }
        }

        btnGetGold.setOnClickListener {
            Golds.addGold(1000.0)
        }

        btnGetChest.setOnClickListener {
            Chest.chest_State = 1
        }

        btnAddWallet.setOnClickListener {
            Coins.coin_InWallet.add(c1)
            Coins.coin_InWallet.add(c2)
            Coins.coin_InWallet.add(c3)
            Coins.coin_InWallet.add(c4)
            Coins.coin_InWallet.add(c5)
            Coins.coin_InWallet.add(c6)
        }

        btnAddBank.setOnClickListener {
            Coins.coin_InBank.add(c1)
            Coins.coin_InBank.add(c2)
            Coins.coin_InBank.add(c3)
            Coins.coin_InBank.add(c4)
            Coins.coin_InBank.add(c5)
            Coins.coin_InBank.add(c6)
        }

        btnAddFWallet.setOnClickListener {
            Coins.coin_FromMail.add(c1)
            Coins.coin_FromMail.add(c2)
            Coins.coin_FromMail.add(c3)
            Coins.coin_FromMail.add(c4)
            Coins.coin_FromMail.add(c5)
            Coins.coin_FromMail.add(c6)
        }

        btnDeleteData.setOnClickListener {
            Golds.value = 0.0

            Coins.coin_InWallet.clear()
            Coins.coin_OnMap.clear()
            Coins.downloadDate = ""
            Coins.transfer_made = 0

            Chest.chest_State = 0
            Chest.shovel = 5
            Chest.attempt = 8
            Chest.result = ""
            Chest.solution[0] = 10
            Chest.solution[1] = 10
            Chest.solution[2] = 10
            Chest.solution[3] = 10
        }

        btnBackTest.setOnClickListener {
            finish()
        }


    }
}
