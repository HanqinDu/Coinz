package s1615548.coinz.Model

import com.mapbox.mapboxsdk.geometry.LatLng

class Coin(val id:String, var value:Double, var currency: String, val marker_symbol:String, val marker_color:String, val coordinate: LatLng,var type:Int, var convert:Int = 0){

    fun equal(input: Coin):Boolean{
        return this.id == input.id
    }

}


