package s1615548.coinz.Model

import com.mapbox.mapboxsdk.geometry.LatLng
import org.json.JSONObject
import s1615548.coinz.curToInt
import java.util.*

// Data: building
object Buildings{
    var list = arrayListOf<Building>()
    var access_range = 50

    fun load(){
        list.add(Building("Library", LatLng(55.942710,-3.189074), "information about the code of chest"))
        list.add(Building("Appleton Tower", LatLng(55.944263,-3.186688), "collect random coins from map"))
        list.add(Building("George square", LatLng(55.943585,-3.188791), "information about the location of chest"))
        list.add(Building("Student Centre", LatLng(55.945924,-3.188141), "information about the next day's exchanging rate"))
    }

}

// Data: Golds
object Golds{
    var value:Double = 0.0
}

// Data: Chest
object Chest{
    var chest_Location = LatLng(0.0, 0.0)
    var chest_State = 0   // 0: underground   1:been owned by player   2:already opened
    var shovel = 0
    var solution = mutableListOf(10, 10, 10, 10)
    var collect_range = 100
    var attempt = 8
    var result = ""

    fun dig(location: LatLng): Boolean {
        if(shovel>0){
            if(chest_Location.distanceTo(location) <= collect_range && chest_State == 0){
                shovel--
                chest_State = 1
                return true
            }else{
                shovel--
                return false
            }
        }else{
            return false
        }
    }

    fun setLocation(c1: Coin, c2:Coin){
        chest_Location = LatLng((c1.coordinate.latitude + c2.coordinate.latitude)/2,(c1.coordinate.longitude + c2.coordinate.longitude)/2)
    }

    fun setSolution(){
        val random = Random()
        solution[0] = random.nextInt(10)
        while (solution[1] == 10 || solution[1] == solution[0]){
            solution[1] = random.nextInt(10)
        }
        while (solution[2] == 10 || solution[2] == solution[0] || solution[2] == solution[1]){
            solution[2] = random.nextInt(10)
        }
        while (solution[3] == 10 || solution[3] == solution[0] || solution[3] == solution[1] || solution[3] == solution[2]){
            solution[3] = random.nextInt(10)
        }
    }

    fun tipCode():String{
        val i = Random().nextInt(8)
        val j = Random().nextInt(6) + 2
        val k = Random().nextInt(4)

        return when(i){
            0 -> "the summing of the first three code is ${solution[0] + solution[1] + solution[2]}"
            1,2 -> "the digit at position ${k+1} is ${solution[k]}"
            2,3 -> "the digit at position ${k+1} is a ${if(solution[k]%2 == 0){"even number"}else{"odd number"}}"
            else -> "the digit at position ${k+1} is ${if(solution[k] > j){"larger than $j"}else{"smaller or equal than $j"}}"
        }
    }

    fun tipLocation(location: LatLng):String{
        val latDifference = chest_Location.latitude - location.latitude
        val lngDifference = chest_Location.longitude - location.longitude

        var direction:String = ""
        val distance = chest_Location.distanceTo(location)

        if(latDifference>0){
            if(lngDifference > 0){
                direction = "east-north"
            }else{
                direction = "west-north"
            }
        }else{
            if(lngDifference > 0){
                direction = "east-south"
            }else{
                direction = "west-south"
            }
        }

        return when(distance){
            in 0..300 -> "the chest locate in the $direction quite close from here"
            in 300..600 -> "the chest locate in the $direction not too far away from here"
            else -> "the chest locate in the $direction quite far away from here"
        }
    }

}


// Data: coin
object Coins{
    var rate_SHIL = 0.0
    var rate_DOLR = 0.0
    var rate_QUID = 0.0
    var rate_PENY = 0.0

    var mapdataReady = false

    val bank_capacity = 300
    val daily_capacity = 25

    var collect_range:Double = 50.0
    var transfer_made = 0
    var send_made = 0
    var downloadDate:String = ""
    var coin_OnMap = arrayListOf<Coin>()
    var coin_InWallet = arrayListOf<Coin>()
    var coin_InBank = arrayListOf<Coin>()
    var coin_FromMail = arrayListOf<Coin>()
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

            transfer_made = 0
            send_made = 0
            return 2
        }else{
            downloadDate = jsonObject.getString("date-generated")
        }

        // 1: update begin

        coin_OnMap.clear()
        coin_FromMail.clear()
        coin_InWallet.clear()

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

    fun collect(i: Int){
        coin_InWallet.add(coin_OnMap[i])
        coin_OnMap.removeAt(i)
    }

    fun moveToBank(i:Int){
        coin_InBank.add(coin_InWallet[i])
        coin_InWallet.removeAt(i)

        transfer_made++
    }

    fun moveToBankF(i:Int){
        coin_InBank.add(coin_FromMail[i])
        coin_FromMail.removeAt(i)
    }

    fun send(i:Int){
        coin_InWallet.removeAt(i)
        send_made++
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

    fun value_bank(i:Int): Double{
        when(coin_InBank[i].currency){
            "SHIL" -> return coin_InBank[i].value * rate_SHIL
            "DOLR" -> return coin_InBank[i].value * rate_DOLR
            "QUID" -> return coin_InBank[i].value * rate_QUID
            "PENY" -> return coin_InBank[i].value * rate_PENY
            else -> return 0.0
        }
    }

    fun sort_wallet(){
        coin_InWallet.sortWith(compareBy({-it.type},{-it.value}))
    }

    fun sort_Fwallet(){
        coin_FromMail.sortWith(compareBy({-it.type},{-it.value}))
    }

    fun sort_Bank() {
        coin_InBank.sortWith(compareBy({-it.type},{-it.value}))
    }

}