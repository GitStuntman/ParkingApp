package com.example.parkingapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException
import java.util.*

class MapsFragment : Fragment() {


    lateinit var fusedLocationProviderClient :FusedLocationProviderClient
    private lateinit var currentLocation :Location
    private val permissionCode = 101

    private val callback = OnMapReadyCallback {
        val latLng = LatLng(currentLocation.latitude,currentLocation.longitude)
        val markerOptions = MarkerOptions().position(latLng).title("Current Location")
        it.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        it.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15f))
        it.addMarker(markerOptions)



    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocationUser() {
        if(ActivityCompat.checkSelfPermission(requireContext(),Manifest.permission.ACCESS_FINE_LOCATION)
            !=PackageManager.PERMISSION_GRANTED
            &&ActivityCompat.checkSelfPermission(requireContext(),Manifest.permission.ACCESS_COARSE_LOCATION)
            !=PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),permissionCode)
            return
        }

        val getLocation = fusedLocationProviderClient.lastLocation.addOnSuccessListener {location->

            if(location != null){
                currentLocation = location
                Toast.makeText(requireContext(),"${currentLocation.latitude},${currentLocation.longitude}",Toast.LENGTH_LONG).show()
                val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
                mapFragment?.getMapAsync(callback)
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            permissionCode -> if(grantResults.isNotEmpty() && grantResults[0]==
                    PackageManager.PERMISSION_GRANTED){
                getCurrentLocationUser()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
        getCurrentLocationUser()

    }
}