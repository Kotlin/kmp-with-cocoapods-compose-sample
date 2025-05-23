package org.jetbrains.kotlin.compose.sample

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cocoapods.GoogleMaps.GMSServices
import org.jetbrains.compose.resources.painterResource

import org.jetbrains.kotlin.compose.sample.composeapp.generated.resources.Res
import org.jetbrains.kotlin.compose.sample.composeapp.generated.resources.compose_multiplatform

@Composable
fun App() {
    // https://developers.google.com/maps/documentation/ios-sdk/config
    GMSServices.provideAPIKey("API_KEY")

    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }

            AnimatedVisibility(showContent) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val greeting = remember { Greeting().greet() }
                    val podGreeting = remember { Greeting().greetWithDependency() }

                    Image(painterResource(Res.drawable.compose_multiplatform), contentDescription = null)
                    Text(
                        "Compose: $greeting",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        "Pod: $podGreeting",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    GoogleMapComposable(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    )
                }
            }
        }
    }
}