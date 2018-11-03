package s1615548.coinz

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.Location
import android.net.ConnectivityManager
import android.os.Bundle
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
import s1615548.coinz.Activity.bank_Activity
import s1615548.coinz.Activity.manageCoin_Activity

import s1615548.coinz.Activity.wallet_Activity
import s1615548.coinz.Model.Coins


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), OnMapReadyCallback, LocationEngineListener, PermissionsListener {

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
            showCoins()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        // give value to originposition to avoid bug
        originLocation = Location("")
        originLocation.latitude = 55.944
        originLocation.longitude = -3.188

        // map initialize
        super.onCreate(savedInstanceState)
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
        //DownloadFileTask(DownloadCompleteRunner).execute(mapURL)

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
        if(switcher == 1){

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
            showToast("please wait for the map downloading")
        }
        if(switcher == 2){
            showToast("this is the newest map")
        }

    }

    public override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }



}
