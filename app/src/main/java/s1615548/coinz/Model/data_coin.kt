package s1615548.coinz.Model

import android.provider.Telephony
import com.mapbox.mapboxsdk.geometry.LatLng
import org.json.JSONObject
import s1615548.coinz.curToInt

object Golds{
    var value:Double = 0.0
}

object Coins{
    var rate_SHIL = 0.0
    var rate_DOLR = 0.0
    var rate_QUID = 0.0
    var rate_PENY = 0.0

    val bank_capacity = 100
    val daily_capacity = 25

    var collect_range:Double = 50.0
    var transfer_made = 0
    var downloadDate:String = ""
    var coin_OnMap = arrayListOf<Coin>()
    var coin_InWallet = arrayListOf<Coin>()
    var coin_InBank = arrayListOf<Coin>()
    var downloadResult: String = ""
    
    fun update_map():Int{

        // 0: download processing;  1: update map complete;  2: it's the same map;  -1: fail to download

        if(downloadResult == ""){
            return 0
        }

        var jsonObject: JSONObject

        try{
            jsonObject = JSONObject(downloadResult)
        }catch(RuntimeException: Exception){
            return -1
        }

        if(downloadDate == jsonObject.getString("date-generated")){
            return 2
        }else{
            downloadDate = jsonObject.getString("date-generated")
        }

        // 1: update begin

        coin_OnMap.clear()

        val features = jsonObject.getJSONArray("features")
        val rate = jsonObject.getJSONObject("rates")
        var feature: JSONObject
        var properties: JSONObject
        var geometry: JSONObject
        var coor_x:Double
        var coor_y:Double

        rate_SHIL = rate.getString("SHIL").toDouble()
        rate_DOLR = rate.getString("DOLR").toDouble()
        rate_QUID = rate.getString("QUID").toDouble()
        rate_PENY = rate.getString("PENY").toDouble()

        for(i in 0 .. (features.length()-1)){
            feature = features.getJSONObject(i)
            properties = feature.getJSONObject("properties")
            geometry = feature.getJSONObject("geometry")

            coor_x = geometry.getString("coordinates").substringBefore(']').substringAfter(',').toDouble()
            coor_y = geometry.getString("coordinates").substringBefore(',').substringAfter('[').toDouble()
            

            coin_OnMap.add(Coin(
                    properties.getString("id"),
                    properties.getDouble("value"),
                    properties.getString("currency"),
                    properties.getString("marker-symbol"),
                    properties.getString("marker-color"),
                    LatLng(coor_x,coor_y),
                    curToInt(properties.getString("currency"))
                    ))
        }
        
        return 1

    }
    
    fun collectIn(location: LatLng): Boolean{
        
        var output = false
        
        var i = 0
        while(i < coin_OnMap.size){
            if(coin_OnMap[i].coordinate.distanceTo(location) <= collect_range){
                coin_InWallet.add(coin_OnMap[i])
                coin_OnMap.removeAt(i)
                output = true
            }else{
                i++
            }
        }
        
        return output
    }

    fun moveToBank(i:Int){
        coin_InBank.add(coin_InWallet[i])
        coin_InWallet.removeAt(i)

        transfer_made++
    }

    fun converToGold(i:Int){
        when(coin_InBank[i].currency){
            "SHIL" -> Golds.value += coin_InBank[i].value * rate_SHIL
            "DOLR" -> Golds.value += coin_InBank[i].value * rate_DOLR
            "QUID" -> Golds.value += coin_InBank[i].value * rate_QUID
            "PENY" -> Golds.value += coin_InBank[i].value * rate_PENY
        }
        coin_InBank.removeAt(i)
    }

    fun sort_wallet(){
        coin_InWallet.sortWith(compareBy({-it.type},{-it.value}))
    }

    fun sort_Bank() {
        coin_InBank.sortWith(compareBy({-it.type},{-it.value}))
    }

}