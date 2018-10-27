package s1615548.coinz

import android.content.Context
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

interface DownloadCompleteListener {
    fun downloadComplete(result: String)
}

object DownloadCompleteRunner : DownloadCompleteListener {
    var result : String = ""
    override fun downloadComplete(result: String) {
        this.result = result
    }
}



