package s1615548.coinz.Model

import com.mapbox.mapboxsdk.geometry.LatLng

class Coin(val id:String, var value:Double, var currency: String, val marker_symbol:String = "", val marker_color:String = "", val coordinate: LatLng = LatLng(0.0,0.0),var type:Int = 0, var convert:Int = 0){

    override fun toString(): String {
        return "$currency:$value"
    }

}


