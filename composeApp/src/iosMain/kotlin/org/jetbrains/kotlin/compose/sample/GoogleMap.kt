package org.jetbrains.kotlin.compose.sample

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import org.jetbrains.kotlin.google.maps.GoogleMapViewController

@Composable
fun GoogleMapComposable(modifier: Modifier = Modifier) {
    UIKitView(
        modifier = modifier,
        factory = {
            GoogleMapViewController().view
        }
    )
}
