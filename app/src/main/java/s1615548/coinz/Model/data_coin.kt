package s1615548.coinz.Model

import com.mapbox.mapboxsdk.geometry.LatLng
import org.json.JSONObject
import s1615548.coinz.DownloadCompleteRunner
import s1615548.coinz.DownloadFileTask

object Coins{
    var downloadDate:String = ""
    var coin_Onmap = arrayListOf<Coin>()
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
        var coor: LatLng
        
        if(downloadDate == jsonObject.getString("date-generated")){
            return false
        }else{
            downloadDate = jsonObject.getString("date-generated")
        }

        coin_Onmap.clear()

        for(i in 0 .. (features.length()-1)){
            feature = features.getJSONObject(i)
            properties = feature.getJSONObject("properties")
            geometry = feature.getJSONObject("geometry")

            coor_x = geometry.getString("coordinates").substringBefore(']').substringAfter(',').toDouble()
            coor_y = geometry.getString("coordinates").substringBefore(',').substringAfter('[').toDouble()
            

            coin_Onmap.add(Coin(
                    properties.getString("id"),
                    properties.getDouble("value"),
                    properties.getString("currency"),
                    properties.getString("marker-symbol"),
                    properties.getString("marker-color"),
                    LatLng(coor_x,coor_y)
                    ))

        }
        
        return true

    }
    
    fun collectIn(range: Double, location: LatLng): Boolean{
        
        var output = false
        
        var i = 0
        while(i < coin_Onmap.size){
            if(coin_Onmap[i].coordinate.distanceTo(location) <= range){
                coin_InWallet.add(coin_Onmap[i])
                coin_Onmap.removeAt(i)
                output = true
            }else{
                i++
            }
        }
        
        return output
    }
}