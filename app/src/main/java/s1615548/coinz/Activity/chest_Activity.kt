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
    // SharedPreferences setting
    val settings = getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE)
    val editor = settings.edit()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chest)

        // set up button and text
        btnUnlock.isEnabled = Chest.attempt > 0 && Chest.chest_State < 2
        btnBreak.isEnabled = Chest.chest_State <2
        textResult.text = Chest.result


        // button
        btnUnlock.setOnClickListener {
            val input = textCode.text.toString()

            if(input.length == 4){

                // save change in attempt
                Chest.attempt--
                editor.putInt("attempt",Chest.attempt)

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

                // correct answer
                if(a == 4){
                    Chest.chest_State = 2
                    Golds.value += (Chest.attempt+2)*500
                    showToast("Chest unlock! you have received ${(Chest.attempt+2)*500} golds")
                    Chest.attempt = 0
                    btnUnlock.isEnabled = false
                    btnBreak.isEnabled = false
                    Chest.result = "Chest has been unlocked"

                    // save data
                    editor.putString("gold", Golds.value.toString())
                    editor.putInt("attempt",Chest.attempt)
                    editor.putString("result",Chest.result)
                    editor.putInt("cheststate",Chest.chest_State)
                    editor.apply()

                // wrong answer
                }else{
                    showToast("$attempt attempt remained")
                    Chest.result += "${8-Chest.attempt}:      ${input[0]}      ${a}A${b}B\n"
                    if(Chest.attempt < 1){
                        btnUnlock.isEnabled = false
                    }
                }
                textResult.text = Chest.result

                // save data
                editor.putInt("attempt",Chest.attempt)
                editor.putString("result",Chest.result)
                editor.apply()

            }else{
                showToast("please enter 4 digit")
            }
        }

        btnBreak.setOnClickListener{
            Chest.attempt = 0
            Golds.value += 1000
            btnUnlock.isEnabled = false
            btnBreak.isEnabled = false
            Chest.chest_State = 2
            showToast("you receive 1000 golds by crashing the chest")
            Chest.result = "Chest has been broken"
            textResult.text = Chest.result

            editor.putString("gold", Golds.value.toString())
            editor.putString("result",Chest.result)
            editor.putInt("cheststate",Chest.chest_State)
            editor.apply()

        }

        btnBackChest.setOnClickListener {
            finish()
        }
    }


}
