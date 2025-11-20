package org.jetbrains.kotlin.google.maps

import swiftPMImport.org.jetbrains.kotlin.google.maps.google.maps.GMSCameraPosition
import swiftPMImport.org.jetbrains.kotlin.google.maps.google.maps.GMSMapView
import swiftPMImport.org.jetbrains.kotlin.google.maps.google.maps.GMSMarker
import kotlinx.cinterop.readValue
import platform.CoreGraphics.CGRectZero
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.UIKit.UIViewController

class GoogleMapViewController : UIViewController(nibName = null, bundle = null) {

    private lateinit var mapView: GMSMapView

    override fun viewDidLoad() {
        super.viewDidLoad()

        val camera = GMSCameraPosition.cameraWithLatitude(
            latitude = -33.86,
            longitude = 151.20,
            zoom = 6.0F
        )

        // Use zero frame; we'll size it later
        mapView = GMSMapView.mapWithFrame(CGRectZero.readValue(), camera)
        view.addSubview(mapView)

        // Add marker
        val marker = GMSMarker().apply {
            position = CLLocationCoordinate2DMake(-33.86, 151.20)
            title = "Sydney"
            snippet = "Australia"
            map = mapView
        }
    }

    override fun viewDidLayoutSubviews() {
        super.viewDidLayoutSubviews()
        // Resize mapView to match parent view
        mapView.setFrame(view.bounds)
    }
}