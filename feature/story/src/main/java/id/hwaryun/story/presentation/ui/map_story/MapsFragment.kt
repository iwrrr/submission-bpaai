package id.hwaryun.story.presentation.ui.map_story

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import id.hwaryun.core.base.BaseFragment
import id.hwaryun.shared.utils.subscribe
import id.hwaryun.story.R
import id.hwaryun.story.data.model.viewparam.StoryViewParam
import id.hwaryun.story.databinding.FragmentMapsBinding
import id.hwaryun.story.presentation.ui.StoryViewModel

@AndroidEntryPoint
class MapsFragment :
    BaseFragment<FragmentMapsBinding, StoryViewModel>(FragmentMapsBinding::inflate) {

    override val viewModel: StoryViewModel by viewModels()

    private lateinit var googleMap: GoogleMap

    private val fusedLocationProviderClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(
            requireContext()
        )
    }

    private val mapCallback = OnMapReadyCallback { gMap ->
        googleMap = gMap
        googleMap.uiSettings.apply {
            isZoomControlsEnabled = true
            isIndoorLevelPickerEnabled = true
            isCompassEnabled = true
            isMapToolbarEnabled = true
        }
    }

    private val addLocationCallback = OnMapReadyCallback { gMap ->
        googleMap = gMap
        gMap.uiSettings.isCompassEnabled = false

        getMyLocation(gMap)

        var currentPosition = gMap.cameraPosition.target

        gMap.setOnCameraMoveListener {
            binding.ivMapMarker.animate().translationY(-40f).start()
        }

        gMap.setOnCameraIdleListener {
            binding.ivMapMarker.animate().translationY(0f).start()
            currentPosition = gMap.cameraPosition.target
        }

        binding.btnSave.setOnClickListener {
            setFragmentResult(
                KEY_RESULT,
                Bundle().apply { putParcelable(KEY_LAT_LONG, currentPosition) }
            )
            parentFragmentManager.popBackStack()
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getMyLocation(googleMap)
            }
        }

    override fun initView() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?

        val args = arguments?.getInt("action")

        if (args == ACTION_PICK_LOCATION) {
            binding.ivMapMarker.isVisible = true
            binding.btnSave.isVisible = true
            mapFragment?.getMapAsync(addLocationCallback)
            return
        }

        viewModel.getStoriesWithLocation()
        mapFragment?.getMapAsync(mapCallback)

        binding.btnBack.setOnClickListener { parentFragmentManager.popBackStack() }
    }

    override fun observeData() {
        viewModel.storyLocationResult.observe(this) {
            it.subscribe(
                doOnSuccess = { result ->
                    addManyMarker(result.payload)
                },
                doOnEmpty = {
                    getMyLocation(googleMap)
                }
            )
        }
    }

    private fun getMyLocation(gMap: GoogleMap) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            gMap.isMyLocationEnabled = true

            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val latLng = LatLng(location.latitude, location.longitude)
                    googleMap.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            latLng,
                            10f
                        )
                    )
                }
            }
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun addManyMarker(stories: List<StoryViewParam>?) {
        val boundsBuilder = LatLngBounds.Builder()
        stories?.let {
            stories.forEach { story ->
                val latLng = LatLng(story.lat, story.long)
                googleMap.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(story.name)
                        .snippet("${story.lat} ${story.long}")
                )
                boundsBuilder.include(latLng)
            }

            val bounds: LatLngBounds = boundsBuilder.build()
            googleMap.animateCamera(
                CameraUpdateFactory.newLatLngBounds(
                    bounds,
                    resources.displayMetrics.widthPixels,
                    resources.displayMetrics.heightPixels,
                    300
                )
            )
//            val latestStory = stories[0]
//            val latLng = LatLng(latestStory.lat, latestStory.long)
//            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
        }
    }

    companion object {
        const val ACTION_STORIES = 0
        const val ACTION_PICK_LOCATION = 1
        const val KEY_RESULT = "key_result"
        const val KEY_LAT_LONG = "key_lat_long"
    }
}