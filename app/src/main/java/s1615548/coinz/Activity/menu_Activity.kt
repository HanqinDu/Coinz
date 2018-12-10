package s1615548.coinz.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_menu.*
import s1615548.coinz.Model.Chest
import s1615548.coinz.Model.Coins
import s1615548.coinz.R
import s1615548.coinz.showToast

class menu_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // show exchanging rate on view
        text_rate_peny.text = "PENY: ${Coins.rate_PENY}"
        text_rate_dolr.text = "DOLR: ${Coins.rate_DOLR}"
        text_rate_shil.text = "SHIL: ${Coins.rate_SHIL}"
        text_rate_quid.text = "QUID: ${Coins.rate_QUID}"
        textShovel.text = "Number of Shovel: ${Chest.shovel}"

        btnChest.isEnabled = Chest.chest_State == 1

        // Button 1: to wallet activity
        btnToWallet.setOnClickListener{
            Coins.sort_wallet()
            intent = Intent(this, wallet_Activity::class.java)
            startActivity(intent)
        }

        // Button 2: to bank activity
        btnToBank.setOnClickListener{
            Coins.sort_Bank()
            intent = Intent(this, bank_Activity::class.java)
            startActivity(intent)
        }

        // Button 3: to friends' coins' wallet activity
        btn_CoinsFF.setOnClickListener{
            Coins.sort_Fwallet()
            intent = Intent(this, frindscoin_Activity::class.java)
            startActivity(intent)
        }

        // Button 4: to mail activity when player has already log in, else to login activity
        btnMail.setOnClickListener{
            if(FirebaseAuth.getInstance().currentUser == null){
                showToast("please sign in before using mail")
                intent = Intent(this, login_Activity::class.java)
                startActivity(intent)
            }else {
                intent = Intent(this, mail_Activity::class.java)
                startActivity(intent)
            }
        }

        // Button 5: to open-Chest activity, only available when player has already got the chest
        btnChest.setOnClickListener {
            intent = Intent(this, chest_Activity::class.java)
            startActivity(intent)
        }

        // Button 6: back
        btnManageCoinBack.setOnClickListener{
            finish()
        }


    }
}
