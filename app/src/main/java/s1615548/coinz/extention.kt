package s1615548.coinz

import android.content.Context
import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import java.util.*

// add function to Context to make Toast.makeText easier
fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this,message, duration).show()
}

// get download URL with current date
fun getURL():String{

    // read date in three string
    val time = Calendar.getInstance()
    time.timeZone = TimeZone.getTimeZone("GMT+1:00")

    var year = time.get(Calendar.YEAR).toString()
    var month = time.get(Calendar.MONTH).toString()
    var date = time.get(Calendar.DATE).toString()

    // android's month begin with 0, so add one before use
    var mon = month.toInt()
    mon++

    month = mon.toString()

    // add 0 in front of month and date if needed
    if(month.length == 1){
        month = "0$month"
    }
    if(date.length == 1){
        date = "0$date"
    }

    return "http://homepages.inf.ed.ac.uk/stg/coinz/$year/$month/$date/coinzmap.geojson"

}

// function that used to change light of icon
fun changeLight(imageview: ImageView, brightness: Int){
    val array = floatArrayOf(1f,0f,0f,0f,brightness.toFloat(), 0f,1f,0f,0f,brightness.toFloat(),0f,0f,1f,0f,brightness.toFloat(),0f,0f,0f,1f,0f)
    var matrix = ColorMatrix(array)
    imageview.colorFilter = ColorMatrixColorFilter(matrix)
}

// convert currency to int so that Coins can be sorted easier
fun curToInt(input: String): Int{
    return when(input){
        "PENY" -> 4
        "DOLR" -> 3
        "SHIL" -> 2
        "QUID" -> 1
        else -> 0
    }
}





