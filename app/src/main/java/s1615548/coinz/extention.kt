package s1615548.coinz

import android.content.Context
import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import java.util.*

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this,message, duration).show()
}

fun getURL():String{

    val time = Calendar.getInstance()
    time.setTimeZone(TimeZone.getTimeZone("GMT+1:00"))

    var year = time.get(Calendar.YEAR).toString()
    var month = time.get(Calendar.MONTH).toString()
    var date = time.get(Calendar.DATE).toString()

    var mon = month.toInt()
    mon++

    month = mon.toString()

    if(month.length == 1){
        month = "0$month"
    }
    if(date.length == 1){
        date = "0$date"
    }

    return "http://homepages.inf.ed.ac.uk/stg/coinz/$year/$month/$date/coinzmap.geojson"

}

fun changeLight(imageview: ImageView, brightness: Int){
    val array = floatArrayOf(1f,0f,0f,0f,brightness.toFloat(), 0f,1f,0f,0f,brightness.toFloat(),0f,0f,1f,0f,brightness.toFloat(),0f,0f,0f,1f,0f)
    var matrix = ColorMatrix(array)
    imageview.setColorFilter(ColorMatrixColorFilter(matrix))
}


fun curToInt(input: String): Int{
    return when(input){
        "PENY" -> 4
        "DOLR" -> 3
        "SHIL" -> 2
        "QUID" -> 1
        else -> 0
    }
}





