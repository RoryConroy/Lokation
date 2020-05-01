package ie.wit.fragments

import android.os.Bundle
import com.google.android.gms.maps.*
import ie.wit.main.LokationApp
import ie.wit.utils.*


class MapsFragment : SupportMapFragment(), OnMapReadyCallback{

    lateinit var app: LokationApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as LokationApp
        getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        app.mMap = googleMap
        app.mMap.isMyLocationEnabled = true
        app.mMap.uiSettings.isZoomControlsEnabled = true
        app.mMap.uiSettings.setAllGesturesEnabled(true)
        app.mMap.clear()
        trackLocation(app)
        setMapMarker(app)
        getAllLokations(app)
    }
}