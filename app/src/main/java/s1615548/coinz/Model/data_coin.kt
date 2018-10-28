package s1615548.coinz.Model

import com.mapbox.mapboxsdk.geometry.LatLng
import org.json.JSONObject
import s1615548.coinz.DownloadCompleteRunner
import s1615548.coinz.curToInt

object Coins{
    val bank_capacity = 100
    val daily_capacity = 25
    var collect_range:Double = 50.0
    var transfer_made = 0
    var downloadDate:String = ""
    var coin_OnMap = arrayListOf<Coin>()
    var coin_InWallet = arrayListOf<Coin>()
    var coin_InBank = arrayListOf<Coin>()
    
    fun update_map():Boolean{

        if(DownloadCompleteRunner.result == ""){
            return false
        }
        
        val jsonObject = JSONObject(DownloadCompleteRunner.result)
        val features = jsonObject.getJSONArray("features")
        var feature: JSONObject
        var properties: JSONObject
        var geometry: JSONObject
        var coor_x:Double
        var coor_y:Double
        
        if(downloadDate == jsonObject.getString("date-generated")){
            return false
        }else{
            downloadDate = jsonObject.getString("date-generated")
        }

        coin_OnMap.clear()

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
        
        return true

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

    fun sort_wallet(){
        coin_InWallet.sortWith(compareBy({-it.type},{-it.value}))
    }

}