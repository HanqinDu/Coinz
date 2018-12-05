package s1615548.coinz.Model

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.Context
import android.content.ContentValues
import android.content.Intent
import com.mapbox.mapboxsdk.geometry.LatLng
import s1615548.coinz.Model.Coins.coin_InBank
import s1615548.coinz.Model.Coins.coin_InWallet
import s1615548.coinz.Model.Coins.coin_OnMap
import s1615548.coinz.Model.Coins.mapdataReady
import s1615548.coinz.curToInt


class DBHandler(val context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {

    companion object {
        private const val DATABASE_NAME = "data.db"

        const val TABLE_WALLET = "coin_wallet"
        const val TABLE_MAP = "coin_map"
        const val TABLE_BANK = "coin_bank"
        const val TABLE_FWALLET = "coin_fwallet"
        const val COLUMN_CURRENCY = "currency"
        const val COLUMN_VALUE = "value"
        const val COLUMN_ID = "id"
        const val COLUMN_LAT = "lat"
        const val COLUMN_LNG = "lng"

        const val TABLE_MAP_VERSION = "map_version"
        const val COLUMN_DATE = "date"

    }

    override fun onCreate(db: SQLiteDatabase?) {

        val create_table_wallet = "CREATE TABLE $TABLE_WALLET ($COLUMN_CURRENCY TEXT, $COLUMN_VALUE TEXT, $COLUMN_ID TEXT)"
        val create_table_map =    "CREATE TABLE $TABLE_MAP ($COLUMN_CURRENCY TEXT, $COLUMN_VALUE TEXT, $COLUMN_ID TEXT, $COLUMN_LAT TEXT, $COLUMN_LNG TEXT)"
        db?.execSQL(create_table_wallet)
        db?.execSQL(create_table_map)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP IF TABLE EXISTS $TABLE_WALLET")
        db?.execSQL("DROP IF TABLE EXISTS $TABLE_MAP")
        onCreate(db)
    }

    fun saveMap(){
        for(coin in coin_OnMap){
            addRow_loc(TABLE_MAP,coin)
        }
    }

    fun saveWallet(){
        for(coin in coin_InWallet){
            addRow(TABLE_WALLET,coin)
        }
    }

    fun addRow(table:String, c: Coin):Long{
        var values = ContentValues()
        values.put(COLUMN_CURRENCY,c.currency)
        values.put(COLUMN_ID,c.id)
        values.put(COLUMN_VALUE, c.value.toString())

        var db = writableDatabase

        return db.insert(table, null, values)
    }

    fun addRow_loc(table:String, c: Coin):Long{
        var values = ContentValues()
        values.put(COLUMN_CURRENCY,c.currency)
        values.put(COLUMN_ID,c.id)
        values.put(COLUMN_VALUE, c.value.toString())
        values.put(COLUMN_LAT, c.coordinate.latitude.toString())
        values.put(COLUMN_LNG, c.coordinate.longitude.toString())

        var db = writableDatabase

        return db.insert(table, null, values)
    }



    fun deleteAll(){
        var db = writableDatabase
        db.execSQL("DELETE FROM $TABLE_WALLET WHERE 1")
        db.execSQL("DELETE FROM $TABLE_MAP WHERE 1")
    }

    fun readRow():String{
        var output = ""
        var db = writableDatabase
        val query = "SELECT * FROM $TABLE_WALLET WHERE 1"  // *: every column  1:every row

        //Cursor point to a location in your results
        var c = db.rawQuery(query, null)
        c.moveToFirst()

        while(!c.isAfterLast){
            if(c.getString(c.getColumnIndex(COLUMN_CURRENCY)) != null){
                output += c.getString(c.getColumnIndex(COLUMN_CURRENCY)) + c.getString(c.getColumnIndex(COLUMN_VALUE))
                output += "\n"
            }
            c.moveToNext()
        }
        db.close()
        return output
    }

    fun loadWallet(){
        val db = writableDatabase
        val query = "SELECT * FROM $TABLE_WALLET WHERE 1"

        var c = db.rawQuery(query, null)
        c.moveToFirst()

        while(!c.isAfterLast){
            if(c.getString(c.getColumnIndex(COLUMN_CURRENCY)) != null){
                coin_InWallet.add(Coin(
                        currency = c.getString(c.getColumnIndex(COLUMN_CURRENCY)),
                        id = c.getString(c.getColumnIndex(COLUMN_ID)),
                        value = c.getString(c.getColumnIndex(COLUMN_VALUE)).toDouble(),
                        type = curToInt(c.getString(c.getColumnIndex(COLUMN_CURRENCY)))
                ))
            }
            c.moveToNext()
        }

        db.close()

    }

    fun loadMap(){
        val db = writableDatabase
        val query = "SELECT * FROM $TABLE_MAP WHERE 1"

        var c = db.rawQuery(query, null)
        c.moveToFirst()

        while(!c.isAfterLast){
            if(c.getString(c.getColumnIndex(COLUMN_CURRENCY)) != null){
                coin_OnMap.add(Coin(
                        currency = c.getString(c.getColumnIndex(COLUMN_CURRENCY)),
                        id = c.getString(c.getColumnIndex(COLUMN_ID)),
                        value = c.getString(c.getColumnIndex(COLUMN_VALUE)).toDouble(),
                        type = curToInt(c.getString(c.getColumnIndex(COLUMN_CURRENCY))),
                        coordinate = LatLng(c.getString(c.getColumnIndex(COLUMN_LAT)).toDouble(),
                                            c.getString(c.getColumnIndex(COLUMN_LNG)).toDouble())
                ))
            }
            c.moveToNext()
        }

        mapdataReady = true
    }


}