package s1615548.coinz.Model

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.Context
import android.content.ContentValues
import android.content.Intent
import com.mapbox.mapboxsdk.geometry.LatLng
import s1615548.coinz.Model.Coins.coin_FromMail
import s1615548.coinz.Model.Coins.coin_InBank
import s1615548.coinz.Model.Coins.coin_InWallet
import s1615548.coinz.Model.Coins.coin_OnMap
import s1615548.coinz.Model.Coins.mapdataReady
import s1615548.coinz.curToInt



class DBHandler(val context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {

    companion object {

        const val TABLE_WALLET = "coin_wallet"
        const val TABLE_MAP = "coin_map"
        const val TABLE_BANK = "coin_bank"
        const val TABLE_FWALLET = "coin_fwallet"
        const val COLUMN_CURRENCY = "currency"
        const val COLUMN_VALUE = "value"
        const val COLUMN_ID = "id"
        const val COLUMN_LAT = "lat"
        const val COLUMN_LNG = "lng"

    }

    // create table for each list of coins
    override fun onCreate(db: SQLiteDatabase?) {

        val create_table_wallet = "CREATE TABLE $TABLE_WALLET ($COLUMN_CURRENCY TEXT, $COLUMN_VALUE TEXT, $COLUMN_ID TEXT)"
        val create_table_bank =   "CREATE TABLE $TABLE_BANK ($COLUMN_CURRENCY TEXT, $COLUMN_VALUE TEXT, $COLUMN_ID TEXT)"
        val create_table_fwallet =   "CREATE TABLE $TABLE_FWALLET ($COLUMN_CURRENCY TEXT, $COLUMN_VALUE TEXT, $COLUMN_ID TEXT)"
        val create_table_map =    "CREATE TABLE $TABLE_MAP ($COLUMN_CURRENCY TEXT, $COLUMN_VALUE TEXT, $COLUMN_ID TEXT, $COLUMN_LAT TEXT, $COLUMN_LNG TEXT)"
        db?.execSQL(create_table_wallet)
        db?.execSQL(create_table_bank)
        db?.execSQL(create_table_fwallet)
        db?.execSQL(create_table_map)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP IF TABLE EXISTS $TABLE_WALLET")
        db?.execSQL("DROP IF TABLE EXISTS $TABLE_BANK")
        db?.execSQL("DROP IF TABLE EXISTS $TABLE_FWALLET")
        db?.execSQL("DROP IF TABLE EXISTS $TABLE_MAP")
        onCreate(db)
    }

    // save all the coins in wallet
    fun saveWallet(){
        // empty the table and add coins one by one
        deleteTable(TABLE_WALLET)
        for(coin in coin_InWallet){
            addCoin(TABLE_WALLET,coin)
        }
    }

    // save all the coins in bank
    fun saveBank(){
        // empty the table and add coins one by one
        deleteTable(TABLE_BANK)
        for(coin in coin_InBank){
            addCoin(TABLE_BANK,coin)
        }
    }

    // save all the coins in friends' coins' wallet
    fun saveFWallet(){
        // empty the table and add coins one by one
        deleteTable(TABLE_FWALLET)
        for(coin in coin_FromMail){
            addCoin(TABLE_FWALLET,coin)
        }
    }

    // save all the coins on map
    fun saveMap(){
        // empty the table and add coins one by one
        deleteTable(TABLE_MAP)
        for(coin in coin_OnMap){
            addCoinWithLocation(TABLE_MAP,coin)
        }
    }

    // addCoin to a table
    private fun addCoin(table:String, c: Coin){
        var values = ContentValues()
        values.put(COLUMN_CURRENCY,c.currency)
        values.put(COLUMN_ID,c.id)
        values.put(COLUMN_VALUE, c.value.toString())

        var db = writableDatabase

        db.insert(table, null, values)
    }

    // addCoin with location to a table
    private fun addCoinWithLocation(table:String, c: Coin){
        var values = ContentValues()
        values.put(COLUMN_CURRENCY,c.currency)
        values.put(COLUMN_ID,c.id)
        values.put(COLUMN_VALUE, c.value.toString())
        values.put(COLUMN_LAT, c.coordinate.latitude.toString())
        values.put(COLUMN_LNG, c.coordinate.longitude.toString())

        var db = writableDatabase

        db.insert(table, null, values)
    }

    // delete all data from a table
    private fun deleteTable(table: String){

        var db = writableDatabase
        try{
            db.execSQL("DELETE FROM $table WHERE 1")
        }catch (e:Exception){}
    }

    // delete data from all tables
    fun deleteAll(){
        var db = writableDatabase
        try{
            db.execSQL("DELETE FROM $TABLE_FWALLET WHERE 1")
            db.execSQL("DELETE FROM $TABLE_BANK WHERE 1")
            db.execSQL("DELETE FROM $TABLE_MAP WHERE 1")
            db.execSQL("DELETE FROM $TABLE_WALLET WHERE 1")
        }catch (e:Exception){}
    }

    // load data from SQLite to wallet
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

    // load data from SQLite to bank
    fun loadBank(){
        val db = writableDatabase
        val query = "SELECT * FROM $TABLE_BANK WHERE 1"

        var c = db.rawQuery(query, null)
        c.moveToFirst()

        while(!c.isAfterLast){
            if(c.getString(c.getColumnIndex(COLUMN_CURRENCY)) != null){
                coin_InBank.add(Coin(
                        currency = c.getString(c.getColumnIndex(COLUMN_CURRENCY)),
                        id = c.getString(c.getColumnIndex(COLUMN_ID)),
                        value = c.getString(c.getColumnIndex(COLUMN_VALUE)).toDouble(),
                        type = curToInt(c.getString(c.getColumnIndex(COLUMN_CURRENCY)))
                ))
            }
            c.moveToNext()
        }
    }

    // load data from SQLite to friends' coins' wallet
    fun loadFWallet(){
        val db = writableDatabase
        val query = "SELECT * FROM $TABLE_FWALLET WHERE 1"

        var c = db.rawQuery(query, null)
        c.moveToFirst()

        while(!c.isAfterLast){
            if(c.getString(c.getColumnIndex(COLUMN_CURRENCY)) != null){
                coin_FromMail.add(Coin(
                        currency = c.getString(c.getColumnIndex(COLUMN_CURRENCY)),
                        id = c.getString(c.getColumnIndex(COLUMN_ID)),
                        value = c.getString(c.getColumnIndex(COLUMN_VALUE)).toDouble(),
                        type = curToInt(c.getString(c.getColumnIndex(COLUMN_CURRENCY)))
                ))
            }
            c.moveToNext()
        }
    }

    // load data from SQLite to Map
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