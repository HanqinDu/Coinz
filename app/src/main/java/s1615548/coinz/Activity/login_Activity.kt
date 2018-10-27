package s1615548.coinz.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import s1615548.coinz.R
import s1615548.coinz.showToast

class login_Activity : AppCompatActivity(){

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        btnCreate.setOnClickListener{

            if(input_email.text.toString().isNotEmpty() && input_password.text.toString().isNotEmpty()){

                mAuth?.createUserWithEmailAndPassword(input_email.text.toString(), input_password.text.toString())
                        ?.addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with user info
                                showToast("create account" + input_email.text.toString())
                            } else {
                                // Sign in failed, display a message to the user
                                showToast("create account fail")
                            }
                        }

            }
        }

        btnLogin.setOnClickListener{

            if(input_email.text.toString().isNotEmpty() && input_password.text.toString().isNotEmpty()){
                mAuth?.signInWithEmailAndPassword(input_email.text.toString(), input_password.text.toString())
                        ?.addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with user info
                                showToast("sign in success")
                                finish()
                            } else {
                                // Sign in failed, display a message to the user
                                showToast("sign in fail")
                            }
                        }

            }
        }


    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non´null) and update UI
        // updateUI(mAuth?.currentUser)
    }


}