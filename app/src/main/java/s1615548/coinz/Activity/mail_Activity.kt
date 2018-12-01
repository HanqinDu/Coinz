package s1615548.coinz.Activity

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
import s1615548.coinz.R
import s1615548.coinz.showToast

class mail_Activity : AppCompatActivity() {

    // data
    var mail:ArrayList<String> = ArrayList<String>()
    var coins:ArrayList<String> = ArrayList<String>()

    // fire base
    private var firestore: FirebaseFirestore? = null
    private var mailRef: CollectionReference? = null

    companion object {
        private const val TAG = "send_Activity"
        private const val COLLECTION_KEY = "sendCoins"
        private const val DOCUMENT_KEY = "Coin"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mail)

        // fire base
        firestore = FirebaseFirestore.getInstance()

        val settings = FirebaseFirestoreSettings.Builder().setTimestampsInSnapshotsEnabled(true).build()
        firestore?.firestoreSettings = settings
        mailRef = firestore?.collection(COLLECTION_KEY)?.document("user334")?.collection("mail")

        // recycler View
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adaptor = mail_recyclerview_adaptor(mail)
        recyclerView.adapter = adaptor

        //download
        mailRef?.get()?.addOnSuccessListener{
            for(i in it){
                coins.add(i.getString("Coins").toString())
                mail.add(i.getString("Message").toString())
            }
            adaptor.notifyDataSetChanged()
        }

        // buttons
        btnBackMail.setOnClickListener{
            finish()
        }

    }

    fun loadMail(){

    }



}
