package s1615548.coinz

import com.mapbox.mapboxsdk.geometry.LatLng
import org.junit.Test

import org.junit.Assert.*
import s1615548.coinz.Model.Coin
import s1615548.coinz.Model.Coins

class CoinsUnitTest {

    @Test
    fun ConvertToGoldTest(){
        Coins.coin_InWallet.clear()
        Coins.coin_OnMap.clear()
        Coins.coin_FromMail.clear()
        Coins.coin_InBank.clear()

        val peny5 = Coin("001",5.00,"PENY")
        val quid9 = Coin("002",9.00,"QUID")
        val unknow = Coin("003",999.00,"???")
        Coins.coin_InBank.add(peny5)
        Coins.coin_InBank.add(quid9)
        Coins.coin_InBank.add(unknow)
        Coins.rate_PENY = 3.0
        Coins.rate_QUID = 7.0

        assertEquals(Coins.converToGold(0).toInt(),15)
        assertEquals(Coins.converToGold(1).toInt(),63)
        assertEquals(Coins.converToGold(2).toInt(),0)
    }

    @Test
    fun collectInTest(){
        val peny5 = Coin("001",5.00,"PENY",coordinate = LatLng(49.0,-3.0))
        Coins.coin_OnMap.add(peny5)

        assertEquals(Coins.collectIn(LatLng(49.0,-3.00005)),true)
        assertEquals(Coins.collectIn(LatLng(45.0,-3.00005)),false)
    }

    @Test
    fun collectText(){
        Coins.coin_InWallet.clear()
        Coins.coin_OnMap.clear()
        Coins.coin_FromMail.clear()
        Coins.coin_InBank.clear()

        val peny5 = Coin("001",5.00,"PENY")
        val quid9 = Coin("002",9.00,"QUID")
        val unknow = Coin("???",999.00,"???")
        Coins.coin_OnMap.add(peny5)
        Coins.coin_OnMap.add(quid9)
        Coins.coin_OnMap.add(unknow)

        Coins.collect(0)

        assertEquals(Coins.coin_OnMap.size,2)
        assertEquals(Coins.coin_InWallet.size,1)
        assertEquals(Coins.coin_FromMail.size,0)
        assertEquals(Coins.coin_FromMail.size,0)
        assertEquals(Coins.coin_InWallet[0].id,"001")
        assertEquals(Coins.coin_OnMap[0].id,"002")
        assertEquals(Coins.coin_OnMap[1].id,"???")
    }

    @Test
    fun moveToBankTest(){
        Coins.coin_InWallet.clear()
        Coins.coin_OnMap.clear()
        Coins.coin_FromMail.clear()
        Coins.coin_InBank.clear()

        val peny5 = Coin("001",5.00,"PENY")
        val quid9 = Coin("002",9.00,"QUID")
        val unknow = Coin("???",999.00,"???")

        Coins.coin_InWallet.add(peny5)
        Coins.coin_InWallet.add(quid9)
        Coins.coin_InWallet.add(unknow)

        Coins.moveToBank(1)

        assertEquals(Coins.coin_InWallet.size,2)
        assertEquals(Coins.coin_InBank.size,1)
        assertEquals(Coins.coin_OnMap.size,0)
        assertEquals(Coins.coin_FromMail.size,0)
        assertEquals(Coins.coin_InBank[0].id,"002")
        assertEquals(Coins.coin_InWallet[0].id,"001")
        assertEquals(Coins.coin_InWallet[1].id,"???")
    }

    @Test
    fun moveToBankFTest(){
        Coins.coin_InWallet.clear()
        Coins.coin_OnMap.clear()
        Coins.coin_FromMail.clear()
        Coins.coin_InBank.clear()

        val peny5 = Coin("001",5.00,"PENY")
        val quid9 = Coin("002",9.00,"QUID")
        val shil12 = Coin("003",12.00,"SHIL")
        val dolr3 = Coin("004",12.00,"DOLR")
        val unknow = Coin("???",999.00,"???")

        Coins.coin_FromMail.add(peny5)
        Coins.coin_FromMail.add(quid9)
        Coins.coin_FromMail.add(unknow)
        Coins.coin_InWallet.add(dolr3)
        Coins.coin_OnMap.add(shil12)

        Coins.moveToBankF(2)

        assertEquals(Coins.coin_InWallet.size,1)
        assertEquals(Coins.coin_InBank.size,1)
        assertEquals(Coins.coin_OnMap.size,1)
        assertEquals(Coins.coin_FromMail.size,2)
        assertEquals(Coins.coin_InBank[0].id,"???")
        assertEquals(Coins.coin_FromMail[0].id,"001")
        assertEquals(Coins.coin_FromMail[1].id,"002")
    }

    @Test
    fun sendTest(){
        Coins.coin_InWallet.clear()
        Coins.coin_OnMap.clear()
        Coins.coin_FromMail.clear()
        Coins.coin_InBank.clear()

        val peny5 = Coin("001",5.00,"PENY")
        val quid9 = Coin("002",9.00,"QUID")
        val shil12 = Coin("003",12.00,"SHIL")
        val dolr3 = Coin("004",12.00,"DOLR")
        val unknow = Coin("???",999.00,"???")

        Coins.coin_InWallet.add(peny5)
        Coins.coin_InWallet.add(quid9)
        Coins.coin_InWallet.add(shil12)
        Coins.coin_InWallet.add(dolr3)
        Coins.coin_InWallet.add(unknow)

        Coins.send(0)
        Coins.send(0)
        Coins.send(1)

        assertEquals(Coins.coin_InWallet.size,2)
        assertEquals(Coins.coin_InWallet[0].id,"003")
        assertEquals(Coins.coin_InWallet[1].id,"???")
    }

    @Test
    fun curToIntTest(){
        Coins.coin_InWallet.clear()
        Coins.coin_OnMap.clear()
        Coins.coin_FromMail.clear()
        Coins.coin_InBank.clear()

        assertEquals(curToInt("PENY"),4)
        assertEquals(curToInt("DOLR"),3)
        assertEquals(curToInt("SHIL"),2)
        assertEquals(curToInt("QUID"),1)
        assertEquals(curToInt("???"),0)
    }

    @Test
    fun sortTest(){
        Coins.coin_InWallet.clear()
        Coins.coin_OnMap.clear()
        Coins.coin_FromMail.clear()
        Coins.coin_InBank.clear()

        val peny5 = Coin("001",5.00,"PENY",type = 4)
        val quid9 = Coin("002",9.00,"QUID",type = 1)
        val shil12 = Coin("003",12.00,"SHIL", type = 2)
        val dolr3 = Coin("004",12.00,"DOLR", type = 3)
        val unknow = Coin("???",999.00,"???", type = 0)

        Coins.coin_InWallet.add(peny5)
        Coins.coin_InWallet.add(quid9)
        Coins.coin_InWallet.add(shil12)
        Coins.coin_InWallet.add(dolr3)
        Coins.coin_InWallet.add(unknow)

        Coins.sort_wallet()

        assertEquals(Coins.coin_InWallet.size,5)
        assertEquals(Coins.coin_InWallet[0].id,"001")
        assertEquals(Coins.coin_InWallet[1].id,"004")
        assertEquals(Coins.coin_InWallet[2].id,"003")
        assertEquals(Coins.coin_InWallet[3].id,"002")
        assertEquals(Coins.coin_InWallet[4].id,"???")

    }


}