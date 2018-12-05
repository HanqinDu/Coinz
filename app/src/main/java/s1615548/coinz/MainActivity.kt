package s1615548.coinz

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.mapbox.android.core.location.LocationEngine
import com.mapbox.android.core.location.LocationEngineListener
import com.mapbox.android.core.location.LocationEnginePriority
import com.mapbox.android.core.location.LocationEngineProvider
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.CameraMode
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.RenderMode
import kotlinx.android.synthetic.main.activity_main.*
import s1615548.coinz.Activity.login_Activity

import com.mapbox.mapboxsdk.annotations.MarkerOptions
import s1615548.coinz.Activity.manageCoin_Activity

import s1615548.coinz.Model.DBHandler
import s1615548.coinz.Model.Coin
import s1615548.coinz.Model.Coins
import s1615548.coinz.Model.Golds
import java.util.*


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), OnMapReadyCallback, LocationEngineListener, PermissionsListener {

    // delay
    val handler = Handler()

    // SQLite
    var db = DBHandler(this, name = "data.db", version = 1, factory = null)

    // SharedPreferences
    private val preferencesFile = "MyPrefsFile" // for storing preferences

    private val tag = "MainActivity"
    private var mapView: MapView? = null
    private var map: MapboxMap? = null

    private lateinit var originLocation : Location
    private lateinit var permissionsManager : PermissionsManager
    private lateinit var locationEngine : LocationEngine
    private lateinit var locationLayerPlugin : LocationLayerPlugin

    private lateinit var mAuth: FirebaseAuth

    private lateinit var mapURL:String

    private lateinit var year:String
    private lateinit var month:String
    private lateinit var date:String

    //implement download interface

    val DownloadCompleteRunner = object : DownloadCompleteListener {
        override fun downloadComplete(result: String) {

            Coins.downloadResult = result

            // load data
            if(Coins.downloadDate == ""){
                showCoins()
                showToast("empty downloadDate")
            }else{
                db.loadMap()
                db.loadBank()
                db.loadWallet()
                db.loadFWallet()

                loopLoadingMap()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // load last download date
        val settings = getSharedPreferences(preferencesFile, Context.MODE_PRIVATE)
        Coins.downloadDate = settings.getString("lastDownloadDate", "")
        Golds.value = settings.getString("gold", "0.0").toDouble()
        Coins.rate_PENY = settings.getString("penyrate", "0.0").toDouble()
        Coins.rate_QUID = settings.getString("quidrate", "0.0").toDouble()
        Coins.rate_SHIL = settings.getString("shilrate", "0.0").toDouble()
        Coins.rate_DOLR = settings.getString("dolrrate", "0.0").toDouble()

        // give value to originposition to avoid bug
        originLocation = Location("")
        originLocation.latitude = 55.944
        originLocation.longitude = -3.188

        // map initialize
        setContentView(R.layout.activity_main)

        Mapbox.getInstance(this, "pk.eyJ1IjoiczE2MjI1MjAiLCJhIjoiY2pub2dkc2dzMjlkbDNxbzNrdjd1NWFtMiJ9.QkDeSFdqeV9Gwquv_NfewQ")

        mapView = findViewById(R.id.mapboxMapView)

        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync (this)

        // initialize mAuth
        mAuth = FirebaseAuth.getInstance()

        // initialize URL

        mapURL = "http://homepages.inf.ed.ac.uk/stg/coinz/2018/10/05/coinzmap.geojson"

        //download map
        mapURL = getURL()

        // BUTTONS

        btnToLogin.setOnClickListener{

            if(mAuth.getCurrentUser() == null){
                intent = Intent(this, login_Activity::class.java)
                startActivity(intent)
            }else{
                showToast("already sign in with account " +  mAuth.getCurrentUser().toString())
                // to the sending_coin activity
            }
        }

        btnShowCoin.setOnClickListener{
            DownloadFileTask(DownloadCompleteRunner).execute(mapURL)
        }

        btnCollect.setOnClickListener{

            if(Coins.collectIn(LatLng(originLocation.latitude,originLocation.longitude))){
                map!!.clear()
                var i = 0
                while(i<Coins.coin_OnMap.size){
                    map!!.addMarker(MarkerOptions().position(Coins.coin_OnMap[i].coordinate)
                            .title(Coins.coin_OnMap[i].currency)
                            .snippet(Coins.coin_OnMap[i].value.toString())
                    )

                    i++
                }
            }

        }

        btnSignout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            showToast("sign out")
        }

        btnWallet.setOnClickListener{
            intent = Intent(this, manageCoin_Activity::class.java)
            startActivity(intent)
        }

        // test botton

        for_test.setOnClickListener{
            val settings = getSharedPreferences(preferencesFile, Context.MODE_PRIVATE)
            showToast(settings.getString("lastDownloadDate", ""))
        }

        btntest2.setOnClickListener{
            showToast(Coins.coin_OnMap.size.toString())
        }

        btnDelete.setOnClickListener{
            db.deleteAll()

            Coins.coin_InWallet.clear()
            Coins.coin_OnMap.clear()
            Coins.downloadDate = ""

        }

        btnIncreaseR.setOnClickListener{
            Coins.collect_range += 50
            showToast("collection range = " + Coins.collect_range)
        }

    }

    override fun onMapReady(mapboxMap: MapboxMap?) {
        if (mapboxMap == null) {
            Log.d(tag, "[onMapReady] mapboxMap is null")
        } else {
            map = mapboxMap
// Set user interface options
            map?.uiSettings?.isCompassEnabled = true
            map?.uiSettings?.isZoomControlsEnabled = true
// Make location information available
            enableLocation()
        }

        //download file after map ready
        DownloadFileTask(DownloadCompleteRunner).execute(mapURL)
    }

    private fun enableLocation() {
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            Log.d(tag, "Permissions are granted")
            initialiseLocationEngine()
            initialiseLocationLayer()
        } else {
            Log.d(tag, "Permissions are not granted")
            permissionsManager = PermissionsManager(this)
            permissionsManager.requestLocationPermissions(this)
        }
    }

    @SuppressWarnings("MissingPermission")
    private fun initialiseLocationEngine() {
        locationEngine = LocationEngineProvider(this)
                .obtainBestLocationEngineAvailable()
        locationEngine.apply {
            interval = 5000 // preferably every 5 seconds
            fastestInterval = 1000 // at most every second
            priority = LocationEnginePriority.HIGH_ACCURACY
                    activate()
        }
        val lastLocation = locationEngine.lastLocation
        if (lastLocation != null) {
            originLocation = lastLocation
            setCameraPosition(lastLocation)
        } else { locationEngine.addLocationEngineListener(this) }
    }

    @SuppressWarnings("MissingPermission")
    private fun initialiseLocationLayer() {
        if (mapView == null) { Log.d(tag, "mapView is null") }
        else {
            if (map == null) { Log.d(tag, "map is null") }
            else {
                locationLayerPlugin = LocationLayerPlugin(mapView!!,
                        map!!, locationEngine)
                locationLayerPlugin.apply {
                    setLocationLayerEnabled(true)
                    cameraMode = CameraMode.TRACKING
                    renderMode = RenderMode.NORMAL
                }
            }
        }
    }

    private fun setCameraPosition(location: Location) {
        val latlng = LatLng(location.latitude, location.longitude)
        map?.animateCamera(CameraUpdateFactory.newLatLng(latlng))
    }

    override fun onLocationChanged(location: Location?) {
        if (location == null) {
            Log.d(tag, "[onLocationChanged] location is null")
        } else {
            originLocation = location
            setCameraPosition(originLocation)
        }
    }

    @SuppressWarnings("MissingPermission")
    override fun onConnected() {
        Log.d(tag, "[onConnected] requesting location updates")
        locationEngine.requestLocationUpdates()
    }

    override fun onExplanationNeeded(permissionsToExplain:
                                     MutableList<String>?) {
        Log.d(tag, "Permissions: $permissionsToExplain")
// Present popup message or dialog
    }

    override fun onPermissionResult(granted: Boolean) {
        Log.d(tag, "[onPermissionResult] granted == $granted")
        if (granted) {
            enableLocation()
        } else {
// Open a dialogue with the user
        }
    }

    public fun showCoins(){
        val switcher = Coins.update_map()
        if(switcher >= 1){

            var i = 0
            while(i<Coins.coin_OnMap.size){
                map!!.addMarker(MarkerOptions().position(Coins.coin_OnMap[i].coordinate)
                        .title(Coins.coin_OnMap[i].currency)
                        .snippet(Coins.coin_OnMap[i].value.toString())
                )
                i++
            }
        }
        if(switcher == -1){
            showToast("please check your internet connection")
        }
        if(switcher == 0){
            showToast("map is downloading")
        }
    }

    fun loopLoadingMap(){
        handler.postDelayed({
            if(Coins.mapdataReady){
                showCoins()
            }else{
                showToast("map loading")
                loopLoadingMap()
            }
        },500)
    }

    public override fun onStart() {
        super.onStart()
        mapView?.onStart()

    }

    override fun onStop() {
        super.onStop()

        // save last download date
        val settings = getSharedPreferences(preferencesFile, Context.MODE_PRIVATE)
        val editor = settings.edit()
        editor.putString("lastDownloadDate", Coins.downloadDate)
        editor.putString("gold", Golds.value.toString())
        editor.putString("penyrate",Coins.rate_PENY.toString())
        editor.putString("quidrate",Coins.rate_QUID.toString())
        editor.putString("shilrate",Coins.rate_SHIL.toString())
        editor.putString("dolrrate",Coins.rate_DOLR.toString())
        editor.apply()

        // save data
        db.deleteAll()
        db.saveWallet()
        db.saveBank()
        db.saveFWallet()
        db.saveMap()
    }

}




