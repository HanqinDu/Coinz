package s1615548.coinz

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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

import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Geometry
import com.mapbox.geojson.Point
import com.google.gson.JsonObject
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import com.mapbox.mapboxsdk.annotations.Icon
import com.mapbox.mapboxsdk.annotations.IconFactory
import com.mapbox.mapboxsdk.annotations.Marker
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import s1615548.coinz.DownloadCompleteRunner.result

import com.mapbox.mapboxsdk.style.layers.CircleLayer
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.layers.SymbolLayer
import com.mapbox.mapboxsdk.style.sources.VectorSource
import kotlinx.android.synthetic.main.activity_main.view.*
import org.json.JSONObject
import s1615548.coinz.Model.Coins


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

    private var collect_range:Double = 50.0

    override fun onCreate(savedInstanceState: Bundle?) {

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

            if(Coins.update_map()){

                var i = 0
                while(i<Coins.coin_Onmap.size){
                    map!!.addMarker(MarkerOptions().position(Coins.coin_Onmap[i].coordinate)
                            .title(Coins.coin_Onmap[i].currency)
                            .snippet(Coins.coin_Onmap[i].value.toString())
                    )

                    i++
                }

            }

        }

        btnCollect.setOnClickListener{

            if(Coins.collectIn(collect_range,LatLng(originLocation.latitude,originLocation.longitude))){
                map!!.clear()
                var i = 0
                while(i<Coins.coin_Onmap.size){
                    map!!.addMarker(MarkerOptions().position(Coins.coin_Onmap[i].coordinate)
                            .title(Coins.coin_Onmap[i].currency)
                            .snippet(Coins.coin_Onmap[i].value.toString())
                    )

                    i++
                }
            }

        }

        // test botton

        for_test.setOnClickListener{

        }

        btnIncreaseR.setOnClickListener{
            collect_range += 50
            showToast("collection range = " + collect_range)
        }

        btnNumberOfCoin.setOnClickListener{
            showToast("coins on map: "+ Coins.coin_Onmap.size.toString())
            showToast("coins in wallet: "+ Coins.coin_InWallet.size.toString())
            showToast("coins in bank: "+ Coins.coin_InBank.size.toString())
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

    public override fun onStart() {
        super.onStart()
        mapView?.onStart()

        DownloadFileTask(DownloadCompleteRunner).execute(mapURL)
    }


}
