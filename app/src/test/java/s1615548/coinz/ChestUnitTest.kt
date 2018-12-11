package s1615548.coinz

import com.mapbox.mapboxsdk.geometry.LatLng
import org.junit.Test

import org.junit.Assert.*
import s1615548.coinz.Model.Chest
import s1615548.coinz.Model.Coin


class ChesetUnitTest {

    @Test
    fun digTest(){
        Chest.chest_Location = LatLng(49.0,-3.0)
        Chest.chest_State = 0
        Chest.shovel = 1

        assertEquals(Chest.dig(LatLng(49.0,-3.000005)),true)
        assertEquals(Chest.dig(LatLng(49.0,-4.0)),false)
        assertEquals(Chest.dig(LatLng(48.0,-3.0)),false)

        Chest.chest_State = 1

        assertEquals(Chest.dig(LatLng(49.0,-3.000005)),false)
        assertEquals(Chest.dig(LatLng(49.0,-4.0)),false)
        assertEquals(Chest.dig(LatLng(48.0,-3.0)),false)

        Chest.chest_State = 0
        Chest.shovel = 0

        assertEquals(Chest.dig(LatLng(49.0,-3.000005)),false)
        assertEquals(Chest.dig(LatLng(49.0,-4.0)),false)
        assertEquals(Chest.dig(LatLng(48.0,-3.0)),false)
    }

    @Test
    fun setLocationTest(){
        val c1 = Coin("001",5.00,"PENY",coordinate = LatLng(49.0,-3.0))
        val c2 = Coin("002",5.00,"DOLR",coordinate = LatLng(48.0,-2.0))

        Chest.setLocation(c1,c2)

        assert(Chest.chest_Location.latitude < 49)
        assert(Chest.chest_Location.latitude > 48)
        assert(Chest.chest_Location.longitude < -2)
        assert(Chest.chest_Location.longitude > -3)
    }

    @Test
    fun setSolutionTest(){
        Chest.setSolution()

        for(i in 0..3){
            for(j in 0..3){
                assert(Chest.solution[i] != Chest.solution[j] || i == j)
            }
            assert(Chest.solution[i] in 0..9)
        }
    }

    @Test
    fun tipLocationTest(){
        Chest.chest_Location = LatLng(49.0,-3.0)

        assertEquals(
                Chest.tipLocation(LatLng(47.0,0.0)),
                "the chest locate in the west-north quite far away from here"
        )
        assertEquals(
                Chest.tipLocation(LatLng(49.1,-3.1)),
                "the chest locate in the east-south quite far away from here"
        )
        assertEquals(
                Chest.tipLocation(LatLng(49.00001,-2.99999)),
                "the chest locate in the west-south quite close from here"
        )
        assertEquals(
                Chest.tipLocation(LatLng(48.997,-3.003)),
                "the chest locate in the east-north not too far away from here"
        )
    }

    

}