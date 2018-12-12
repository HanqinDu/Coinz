package s1615548.coinz.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.android.synthetic.main.activity_send.*
import kotlinx.android.synthetic.main.layout_wallet.view.*
import org.json.JSONArray
import s1615548.coinz.Adapter.coins_gridview_adaptor
import s1615548.coinz.Model.Coin
import s1615548.coinz.Model.Coins
import s1615548.coinz.Model.wallet_Layout
import s1615548.coinz.R
import s1615548.coinz.changeLight
import s1615548.coinz.showToast

class send_Activity : AppCompatActivity() {

    // GV data

    val GV_data: ArrayList<wallet_Layout>
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

    // firestore setting

    private var firestore: FirebaseFirestore? = null
    private var mailRef: CollectionReference? = null

    companion object {
        private const val TAG = "send_Activity"
        private const val COLLECTION_KEY = "sendCoins"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send)

        // set adapter for grid view
        val adapter = coins_gridview_adaptor(this, R.layout.layout_wallet, GV_data)
        GV_send.adapter = adapter

        // firestore
        firestore = FirebaseFirestore.getInstance()

        // Button 1: Grid view item click - turn dark when item selected
        GV_send.setOnItemClickListener { parent, view, position, id ->
            if(adapter.selectedPositions[position]){
                adapter.selectedPositions[position] = false
                changeLight(view.img,0)
            }else{
                adapter.selectedPositions[position] = true
                changeLight(view.img,-80)
            }
        }

        // Button 2: send mail
        btnSend.setOnClickListener{
            when {
                // when the userID's length is not correct
                text_userID.text.toString().length != 28 -> showToast("please enter a valid user ID")

                // when the receiver's is the same as the players
                text_userID.text.toString() == FirebaseAuth.getInstance().currentUser!!.uid -> showToast("you can't send mail to yourself")

                // else, send a mail
                else -> {
                    // fire base reference
                    val settings = FirebaseFirestoreSettings.Builder().setTimestampsInSnapshotsEnabled(true).build()
                    firestore?.firestoreSettings = settings
                    mailRef = firestore?.collection(COLLECTION_KEY)?.document(text_userID.text.toString())?.collection("mail")

                    // convert coins to one String
                    var sendlist = ""
                    var i = 0
                    var coins_sends = 0
                    while(i < Coins.coin_InWallet.size){
                        if(adapter.selectedPositions[i]){
                            sendlist += Coins.coin_InWallet[i].toString() + "/"
                            coins_sends++
                        }
                        i++
                    }


                    val newMessage = mapOf(
                            "Message" to text_notes.text.toString(),
                            "Coins" to sendlist,
                            "Number" to coins_sends
                    )

                    mailRef?.add(newMessage)
                            ?.addOnSuccessListener {
                                showToast("$coins_sends Coins sent")

                                // remove coins which are send from wallet

                                // Once the coin is removed from array list, the coins after that coin move forwards
                                // therefore, we should track the difference and use it to eliminate change in position
                                var i = 0
                                var difference = 0
                                while(i<adapter.selectedPositions.size){
                                    if(adapter.selectedPositions[i]){
                                        Coins.send(i-difference)
                                        difference++
                                    }
                                    i++
                                }

                                finish()
                            }
                            ?.addOnFailureListener { e -> Log.e(TAG, e.message) }
                }
            }
        }

        // Button 3: back
        btnBackSend.setOnClickListener{
            finish()
        }

    }

}
