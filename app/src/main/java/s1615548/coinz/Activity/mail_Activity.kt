package s1615548.coinz.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_mail.*
import s1615548.coinz.Adapter.mail_recyclerview_adaptor
import s1615548.coinz.Model.Coin
import s1615548.coinz.Model.Coins
import s1615548.coinz.Model.Coins.coin_FromMail
import s1615548.coinz.Model.DBHandler
import s1615548.coinz.R
import s1615548.coinz.curToInt
import s1615548.coinz.showToast

class mail_Activity : AppCompatActivity() {

    // data
    var mail:ArrayList<String> = ArrayList<String>()
    var coins:ArrayList<String> = ArrayList<String>()
    var document:ArrayList<String> = ArrayList<String>()
    var coins_number:ArrayList<Int> = ArrayList<Int>()

    // prepare variable for fire base
    private var firestore: FirebaseFirestore? = null
    private var mailRef: CollectionReference? = null

    companion object {
        private const val COLLECTION_KEY = "sendCoins"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mail)

        // SQLite
        var db = DBHandler(this, name = "data.db", version = 1, factory = null)

        // fire base
        firestore = FirebaseFirestore.getInstance()

        // create reference to the mail collection of current user
        val settings = FirebaseFirestoreSettings.Builder().setTimestampsInSnapshotsEnabled(true).build()
        firestore?.firestoreSettings = settings
        mailRef = firestore?.collection(COLLECTION_KEY)?.document(FirebaseAuth.getInstance().currentUser!!.uid)?.collection("mail")

        // recycler View adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adaptor = mail_recyclerview_adaptor(mail,coins_number)
        recyclerView.adapter = adaptor

        // download data from fire base and refresh recycler view
        mailRef?.get()?.addOnSuccessListener{
            for(i in it){
                coins.add(i.getString("Coins").toString())
                mail.add(i.getString("Message").toString())
                document.add(i.id)
                coins_number.add(i.getDouble("Number")!!.toInt())
            }
            adaptor.notifyDataSetChanged()

        }

        // set text
        textUserID.text = FirebaseAuth.getInstance().currentUser!!.uid

        // Button 1: collect coins in all the mails and delete them
        btnCollect.setOnClickListener{

            var i = 0
            var numberOfCoinCollected = 0
            while(i < document.size){

                // add coins to coin_FromMail
                if(coins[i].length!= 0){
                    val coinList = coins[i].split('/')
                    for(c in coinList){
                        if(c.isNotEmpty()){
                            coin_FromMail.add(StringToCoin(c))
                            numberOfCoinCollected++
                        }
                    }
                }

                // delete mail
                mailRef?.document(document[i])?.delete()

                i++
            }

            // show result
            showToast("$numberOfCoinCollected coins collected")

            // save data
            db.saveFWallet()

            // refresh view
            mail.clear()
            coins_number.clear()
            adaptor.notifyDataSetChanged()

        }

        // Button 2: to the activity of sending mail
        btnSendMail.setOnClickListener{
            Coins.sort_wallet()
            intent = Intent(this, send_Activity::class.java)
            startActivity(intent)
        }

        // Button 3: back
        btnBackMail.setOnClickListener{
            finish()
        }

    }

    // Function: convert String from fire base to Coin
    fun StringToCoin(input: String): Coin {
        return(Coin(id = "0",currency = input.substringBefore(":"), value = input.substringAfter(":").toDouble(),type = curToInt(input.substringBefore(":"))))
    }

}
