package s1615548.coinz

import android.content.Context
import android.content.Intent
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
import com.mapbox.mapboxsdk.annotations.IconFactory
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.CameraMode
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.RenderMode
import kotlinx.android.synthetic.main.activity_main.*

import com.mapbox.mapboxsdk.annotations.MarkerOptions
import s1615548.coinz.Activity.Building.appletonTower_Activity
import s1615548.coinz.Activity.Building.georgeSquare_Activity
import s1615548.coinz.Activity.Building.library_Activity
import s1615548.coinz.Activity.manageCoin_Activity
import s1615548.coinz.Model.*

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), OnMapReadyCallback, LocationEngineListener, PermissionsListener {

    // delay
    val handler = Handler()

    // SQLite
    var db = DBHandler(this, name = "data.db", version = 1, factory = null)

    // SharedPreferences
    private val preferencesFile = "MyPrefsFile" // for storing preferences

    // map
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

            // if the player is the first time play this game, download directly, else, load data first
            if(Coins.downloadDate == ""){
                showCoins()
            }else{

                // load data from SQLite
                db.loadMap()
                db.loadBank()
                db.loadWallet()
                db.loadFWallet()

                // try to load map every 3 second until success
                loopLoadingMap()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // load date
        Buildings.load()
        val settings = getSharedPreferences(preferencesFile, Context.MODE_PRIVATE)
        Golds.value = settings.getString("gold", "0.0").toDouble()
        Coins.downloadDate = settings.getString("lastDownloadDate", "")
        Coins.transfer_made = settings.getInt("transfermade",0)
        Coins.rate_PENY = settings.getString("penyrate", "0.0").toDouble()
        Coins.rate_QUID = settings.getString("quidrate", "0.0").toDouble()
        Coins.rate_SHIL = settings.getString("shilrate", "0.0").toDouble()
        Coins.rate_DOLR = settings.getString("dolrrate", "0.0").toDouble()
        Chest.chest_Location = LatLng(settings.getString("chestLat", "0.0").toDouble(),settings.getString("chestLng", "0.0").toDouble())
        Chest.shovel = settings.getInt("shovel",0)
        Chest.chest_State = settings.getInt("cheststate",0)
        Chest.solution[0] = settings.getInt("solution1",10)
        Chest.solution[1] = settings.getInt("solution2",10)
        Chest.solution[2] = settings.getInt("solution3",10)
        Chest.solution[3] = settings.getInt("solution4",10)
        Chest.attempt = settings.getInt("attempt",8)
        Chest.result = settings.getString("result","")

        // set value to originposition to avoid bug
        originLocation = Location("")
        originLocation.latitude = 55.944
        originLocation.longitude = -3.188

        // initialize map
        setContentView(R.layout.activity_main)

        Mapbox.getInstance(this, "pk.eyJ1IjoiczE2MjI1MjAiLCJhIjoiY2pub2dkc2dzMjlkbDNxbzNrdjd1NWFtMiJ9.QkDeSFdqeV9Gwquv_NfewQ")

        mapView = findViewById(R.id.mapboxMapView)

        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync (this)

        // initialize mAuth
        mAuth = FirebaseAuth.getInstance()

        //download map
        mapURL = getURL()

        // Button 1: collect reachable coins
        btnCollect.setOnClickListener{

            // funcion collectIn return true when there is coin collected
            if(Coins.collectIn(LatLng(originLocation.latitude,originLocation.longitude))){

                // refresh marker of map
                map!!.clear()
                val iconFactory = IconFactory.getInstance(this)

                // add marker: coins on map
                for(c in Coins.coin_OnMap){
                    map!!.addMarker(MarkerOptions().position(c.coordinate)
                            .title(c.currency)
                            .icon(
                                    when(c.currency){
                                        "QUID" -> iconFactory.fromResource(R.mipmap.markerq)
                                        "PENY" -> iconFactory.fromResource(R.mipmap.markerp)
                                        "DOLR" -> iconFactory.fromResource(R.mipmap.markerd)
                                        "SHIL" -> iconFactory.fromResource(R.mipmap.markers)
                                        else -> iconFactory.fromResource(R.drawable.mapbox_marker_icon_default)
                                    }
                            )
                            .snippet(
                                    if(c.value.toString().length > 5){
                                        c.value.toString().substring(0..5)
                                    }else{
                                        c.value.toString()
                                    }
                            )
                    )
                }

                // add:marker building
                for(b in Buildings.list){
                    map!!.addMarker(MarkerOptions().position(b.coordinate)
                            .title(b.name)
                            .icon(iconFactory.fromResource(R.mipmap.buildingicon))
                            .snippet(b.description)
                    )
                }

            }
        }

        // Button 2: sign out
        btnSignout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            showToast("sign out")
        }

        // Button 3: open coins management menu
        btnWallet.setOnClickListener{
            intent = Intent(this, manageCoin_Activity::class.java)
            startActivity(intent)
        }

        // Button 4: try to dig chest by consuming a shovel
        btndig.setOnClickListener{
            if(Chest.dig(LatLng(originLocation.latitude,originLocation.longitude))){
                showToast("you find the chest!")
                btndig.isEnabled = false
            }else{
                showToast("there is nothing here, ${Chest.shovel} shovel left")
                if(Chest.shovel <= 0){
                    btndig.isEnabled = false
                }
            }
        }

        // Button 5: enter nearby building
        btnBuilding.setOnClickListener {
            // flag
            var enter = false

            // loop list of building and check distance
            for(b in Buildings.list){
                if(b.coordinate.distanceTo(LatLng(originLocation.latitude,originLocation.longitude)) <= Buildings.access_range){

                    enter = true
                    when(b.name){
                        "Library" -> startActivity(Intent(this, library_Activity::class.java))
                        "Appleton Tower" -> startActivity(Intent(this, appletonTower_Activity::class.java))
                        "George square" -> startActivity(Intent(this, georgeSquare_Activity::class.java))
                    }
                    break

                }
            }

            // if can't enter any building, show instruction
            if(!enter){
                showToast("you are not close enough to any building")
            }

        }


        // test botton

        btnTest.setOnClickListener {
            startActivity(Intent(this, library_Activity::class.java))
        }

        btnTest2.setOnClickListener {
            startActivity(Intent(this, georgeSquare_Activity::class.java))
        }

        btnDelete.setOnClickListener{

            db.deleteAll()

            Golds.value = 0.0

            Coins.coin_InWallet.clear()
            Coins.coin_OnMap.clear()
            Coins.downloadDate = ""

            Chest.chest_State = 0
            Chest.shovel = 5
            Chest.attempt = 8
            Chest.result = ""
            Chest.solution[0] = 10
            Chest.solution[1] = 10
            Chest.solution[2] = 10
            Chest.solution[3] = 10
        }

        btnIncreaseR.setOnClickListener{
            Coins.collect_range += 50
            Chest.collect_range += 50
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
        if(switcher == 1){
            Chest.setLocation(Coins.coin_OnMap[2],Coins.coin_OnMap[5])
            Chest.setSolution()
            Chest.shovel = 5
        }
        if(switcher >= 1){

            for(c in Coins.coin_OnMap){
                val iconFactory = IconFactory.getInstance(this)

                map!!.addMarker(MarkerOptions().position(c.coordinate)
                        .title(c.currency)
                        .icon(
                                when(c.currency){
                                    "QUID" -> iconFactory.fromResource(R.mipmap.markerq)
                                    "PENY" -> iconFactory.fromResource(R.mipmap.markerp)
                                    "DOLR" -> iconFactory.fromResource(R.mipmap.markerd)
                                    "SHIL" -> iconFactory.fromResource(R.mipmap.markers)
                                    else -> iconFactory.fromResource(R.drawable.mapbox_marker_icon_default)
                                }
                        )
                        .snippet(
                                if(c.value.toString().length > 5){
                                    c.value.toString().substring(0..5)
                                }else{
                                    c.value.toString()
                                }
                        )
                )
                for(b in Buildings.list){
                    map!!.addMarker(MarkerOptions().position(b.coordinate)
                            .title(b.name)
                            .icon(iconFactory.fromResource(R.mipmap.buildingicon))
                            .snippet(b.description)
                    )
                }

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
                loopLoadingMap()
            }
        },3000)
    }

    public override fun onStart() {
        super.onStart()
        mapView?.onStart()

        btndig.isEnabled = Chest.shovel > 0 && Chest.chest_State == 0
    }

    override fun onStop() {
        super.onStop()

        // Save data: SharedPreferences
        val settings = getSharedPreferences(preferencesFile, Context.MODE_PRIVATE)
        val editor = settings.edit()
        editor.putString("lastDownloadDate", Coins.downloadDate)
        editor.putString("gold", Golds.value.toString())
        editor.putString("penyrate",Coins.rate_PENY.toString())
        editor.putString("quidrate",Coins.rate_QUID.toString())
        editor.putString("shilrate",Coins.rate_SHIL.toString())
        editor.putString("dolrrate",Coins.rate_DOLR.toString())
        editor.putString("chestLat",Chest.chest_Location.latitude.toString())
        editor.putString("chestLng",Chest.chest_Location.longitude.toString())
        editor.putInt("shovel",Chest.shovel)
        editor.putInt("solution1",Chest.solution[0])
        editor.putInt("solution2",Chest.solution[1])
        editor.putInt("solution3",Chest.solution[2])
        editor.putInt("solution4",Chest.solution[3])
        editor.putInt("cheststate",Chest.chest_State)
        editor.putInt("attempt",Chest.attempt)
        editor.putString("result",Chest.result)
        editor.apply()

        // Save data: SQLite
        db.saveWallet()
        db.saveBank()
        db.saveFWallet()
        db.saveMap()

    }

}




