package com.example.pethealthlord

import android.Manifest
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mylib.AllLocations
import com.example.mylib.AllPets
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_maps.*
import kotlinx.android.synthetic.main.fragment_welcome.*
import org.apache.commons.io.FileUtils
import com.google.android.gms.maps.model.MarkerOptions

import com.google.android.gms.maps.CameraUpdate

import com.google.android.gms.maps.model.LatLng





class MapsFragment : Fragment(), OnMapReadyCallback {
   private lateinit var locationRequest: LocationRequest
   private lateinit var locationCallback: LocationCallback


    lateinit var app: MyApplication
    val gson = Gson()
    private lateinit var mMap : GoogleMap

    private var latitude: Double= 0.toDouble()
    private var longitude: Double= 0.toDouble()



    private lateinit var mLastLocation: Location
    private var mMarker: Marker?=null

    //Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient



    companion object{
        private val MY_PERMISSION_CODE: Int = 1000;
    }

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
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
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        app = (activity?.application as MyApplication)
        val data :AllLocations = AllLocations("AllLocations")
        data.AllLocations.add(com.example.mylib.Location("Veterinarstvo Eisner", " (02) 420 23 62", "4.9 / 5", "Krajnceva ulica 3", "Open - CLose 1PM", 46.54350710858781, 15.617250487095083))
        data.AllLocations.add(com.example.mylib.Location("Veterinarstvo Peklar", " (041 537 439", "4.9 / 5", "Slovenja vas 2", "Open - CLose 3PM", 46.444032351787286, 15.803253983689821))
        data.AllLocations.add(com.example.mylib.Location("Veterinarski center Pika", " 0591 78478", "4.5 / 5", "Smetanova ulica 78", "Open - CLose 7PM", 46.561864445471386, 15.630080668160613))
        data.AllLocations.add(com.example.mylib.Location("Veterinarski center Studenci", " (02) 421 66 00", "4.9 / 5", "Kalohova ulica 22", "Open - CLose 2PM", 46.55491650926884, 15.617574504844985))

        data.AllLocations.add(com.example.mylib.Location("Klinika za male živali", " (02) 480 12 16", "4.7 / 5", "Krajnceva ulica 3", "Open - CLose 1PM", 46.55346221694055, 15.665705414696681))
        //RecylcerView


        rvLocation.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        rvLocation.adapter = LocationRVAdapter(data/*, petsList*/, object : LocationRVAdapter.MyOnClick {
                override fun onClick(p0: View?, position: Int) {


                    val callback = OnMapReadyCallback { googleMap ->
                        /**
                         * Manipulates the map once available.
                         * This callback is triggered when the map is ready to be used.
                         * This is where we can add markers or lines, add listeners or move the camera.
                         * In this case, we just add a marker near Sydney, Australia.
                         * If Google Play services is not installed on the device, the user will be prompted to
                         * install it inside the SupportMapFragment. This method will only be triggered once the
                         * user has installed Google Play services and returned to the app.
                         */
                        googleMap.clear()
                        val sydney = LatLng(data.AllLocations[position].latitude, data.AllLocations[position].longitude)
                        googleMap.addMarker(MarkerOptions().position(sydney).title(data.AllLocations[position].name))
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
                    }
                    mapFragment?.getMapAsync(callback)
                   //mMap.clear()
                    //moveMap(mapFragment,app.dataLocation.AllLocations[position].latitude.toDouble(), app.dataLocation.AllLocations[position].longitude )

                    //val action = WelcomeFragmentDirections.actionWelcomeFragmentToPetProfileFragment(position)



                }
            })


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkLocationPermission()) {
                buildLocationRequest()
                buildLocationCallback();

                fusedLocationClient =
                    LocationServices.getFusedLocationProviderClient(requireContext())
                fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.myLooper()
                )
            }

        }
        else{
            buildLocationRequest()
            buildLocationCallback()

            fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(requireContext())
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.myLooper())
        }
    }

    private fun buildLocationCallback(){
        locationCallback = object :LocationCallback(){
            override fun onLocationResult(p0: LocationResult?) {
                mLastLocation = p0!!.locations.get(p0!!.locations.size-1)

                if(mMarker != null){
                    mMarker!!.remove()
                }
                latitude = mLastLocation.latitude
                longitude = mLastLocation.longitude

                val latLng = LatLng(latitude, longitude)
                val markerOptions = MarkerOptions()
                    .position(latLng)
                    .title("Your Position")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))

                mMarker = mMap!!. addMarker(markerOptions)

                mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                mMap!!.animateCamera(CameraUpdateFactory.zoomTo(11f))
            }
        }
    }

    private fun buildLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 3000
        locationRequest.smallestDisplacement = 10f
    }




    private fun checkLocationPermission() :Boolean{
        if(ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)

        {
            if(shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION))
                requestPermissions(requireActivity(), arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ), MY_PERMISSION_CODE)


            else
               requestPermissions(requireActivity(), arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ), MY_PERMISSION_CODE)

            return false
        }
        else
            return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode)
        {
            MY_PERMISSION_CODE ->{
                if(grantResults. size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                        if (checkLocationPermission()) {
                            buildLocationRequest()
                            buildLocationCallback()

                            fusedLocationClient =
                                LocationServices.getFusedLocationProviderClient(requireContext())
                            fusedLocationClient.requestLocationUpdates(
                                locationRequest,
                                locationCallback,
                                Looper.myLooper())
                            mMap!!.isMyLocationEnabled = true
                        }
                        }
                else{
                    Toast.makeText(requireContext(), "Perrmision Denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onStop() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
        super.onStop()
    }






    override fun onMapReady(googleMap: GoogleMap, ) {
        mMap = googleMap!!
        // Add a marker in Sydney and move the camera
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            )
                mMap!!.isMyLocationEnabled = true

        } else
            mMap!!.isMyLocationEnabled = true

        mMap.uiSettings.isZoomControlsEnabled = true
    }

}