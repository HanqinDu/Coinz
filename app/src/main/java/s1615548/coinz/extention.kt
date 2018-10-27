package s1615548.coinz

import android.content.Context
import android.widget.Toast

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this,message, duration).show()
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



