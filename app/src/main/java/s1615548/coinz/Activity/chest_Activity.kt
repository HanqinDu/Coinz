package s1615548.coinz.Activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_chest.*
import s1615548.coinz.Model.Chest
import s1615548.coinz.Model.Chest.attempt
import s1615548.coinz.Model.Golds
import s1615548.coinz.R
import s1615548.coinz.showToast

class chest_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chest)

        // set up button and text
        btnUnlock.isEnabled = Chest.attempt > 0 && Chest.chest_State < 2
        btnBreak.isEnabled = Chest.chest_State <2
        textResult.text = Chest.result

        // Button 1: submit code and return output on text view
        btnUnlock.setOnClickListener {
            // SharedPreferences setting
            val settings = getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE)
            val editor = settings.edit()

            // read input and check if it is 4 digits
            val input = textCode.text.toString()

            if(input.length == 4){

                // reduce available attempt times
                Chest.attempt--

                // compute output
                var a = 0
                var b = 0

                for(i in (0..3)){
                    for(j in (0..3)){
                        if((input[i].toInt() - 48) == Chest.solution[j]){
                            if(i==j){
                                a++
                            }else{
                                b++
                            }
                        }
                    }
                }

                // when answer is correct
                if(a == 4){
                    Chest.chest_State = 2
                    Golds.addGold(((Chest.attempt+3)*500).toDouble())
                    showToast("Chest unlock! you have received ${(Chest.attempt+3)*500} golds")
                    Chest.result += "Chest unlocked, code is ${Chest.solution}\n"
                    Chest.result += "you receive ${(Chest.attempt+3)*500} golds"
                    Chest.attempt = 0
                    btnUnlock.isEnabled = false
                    btnBreak.isEnabled = false

                    // save data
                    editor.putString("gold", Golds.value.toString())
                    editor.putInt("attempt",Chest.attempt)
                    editor.putString("result",Chest.result)
                    editor.putInt("cheststate",Chest.chest_State)
                    editor.apply()

                // when answer is wrong
                }else{
                    showToast("$attempt attempt remained")
                    Chest.result += "${8-Chest.attempt}:      $input    ${a}A${b}B\n"
                    if(Chest.attempt < 1){
                        btnUnlock.isEnabled = false
                    }
                }

                // update text view
                textResult.text = Chest.result

                // save data
                editor.putInt("attempt",Chest.attempt)
                editor.putString("result",Chest.result)
                editor.apply()

            }else{
                showToast("please enter 4 digit")
            }
        }

        // Button 2: break chest and get lowest amount of golds (1000)
        btnBreak.setOnClickListener{
            Chest.attempt = 0
            Golds.addGold(1000.0)
            btnUnlock.isEnabled = false
            btnBreak.isEnabled = false
            Chest.chest_State = 2
            showToast("you receive 1000 golds by crashing the chest")
            Chest.result += "Chest is broken, code is ${Chest.solution}\n"
            Chest.result += "you receive 1000 golds"
            textResult.text = Chest.result

            // save data
            val settings = getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE)
            val editor = settings.edit()
            editor.putString("gold", Golds.value.toString())
            editor.putString("result",Chest.result)
            editor.putInt("cheststate",Chest.chest_State)
            editor.apply()

        }

        // Button 3: back
        btnBackChest.setOnClickListener {
            finish()
        }
    }

}
