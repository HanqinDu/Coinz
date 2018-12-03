package s1615548.coinz.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_manage_coin.*
import s1615548.coinz.Model.Coins
import s1615548.coinz.R
import s1615548.coinz.showToast

class manageCoin_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_coin)

        text_rate_peny.text = "PENY: ${Coins.rate_PENY}"
        text_rate_dolr.text = "DOLR: ${Coins.rate_DOLR}"
        text_rate_shil.text = "SHIL: ${Coins.rate_SHIL}"
        text_rate_quid.text = "QUID: ${Coins.rate_QUID}"

        btnToWallet.setOnClickListener{
            Coins.sort_wallet()
            intent = Intent(this, wallet_Activity::class.java)
            startActivity(intent)
        }

        btnToBank.setOnClickListener{
            Coins.sort_Bank()
            intent = Intent(this, bank_Activity::class.java)
            startActivity(intent)
        }

        btn_CoinsFF.setOnClickListener{
            Coins.sort_Fwallet()
            intent = Intent(this, frindscoin_Activity::class.java)
            startActivity(intent)
        }

        btnMail.setOnClickListener{
            if(FirebaseAuth.getInstance().getCurrentUser() == null){
                showToast("please sign in before using mail")
                intent = Intent(this, login_Activity::class.java)
                startActivity(intent)
            }else {
                intent = Intent(this, mail_Activity::class.java)
                startActivity(intent)
            }
        }

        btnManageCoinBack.setOnClickListener{
            finish()
        }


    }
}
