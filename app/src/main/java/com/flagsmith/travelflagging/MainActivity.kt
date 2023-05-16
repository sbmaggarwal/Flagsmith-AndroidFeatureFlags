package com.flagsmith.travelflagging

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.flagsmith.Flagsmith
import com.flagsmith.travelflagging.ui.theme.TravelFlaggingTheme

const val APP_NAME = "Flagsmith Travel"
const val FLAGSMITH_DEVELOPMENT_KEY = "HizvZTYLTPvfWTzzURgGFN"
const val FEATURE_FLIGHT_BOOKING_KEY = "flight_booking_enabled"

lateinit var flagsmith : Flagsmith

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val context = LocalContext.current

            initFlagsmith(context);

            TravelFlaggingTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TitleWithLogo(APP_NAME, R.drawable.flagsmith)
                        TwoByTwoButtonGrid()
                    }
                }
            }
        }
    }
}

private fun initFlagsmith(context: Context) {
    flagsmith = Flagsmith(
        environmentKey = FLAGSMITH_DEVELOPMENT_KEY,
        context = context,
    )
}

@Composable
fun TitleWithLogo(title: String, logoResId: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(logoResId),
            contentDescription = "Logo",
            modifier = Modifier
                .size(100.dp)
                .padding(top = 16.dp)
        )
        TitleText(title)
    }
}

@Composable
fun TitleText(title: String) {
    Text(
        text = title,
        modifier = Modifier
            .padding(top = 32.dp)
            .fillMaxWidth(),
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Center
    )
}


@Composable
fun TwoByTwoButtonGrid() {

    val context = LocalContext.current

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val buttonModifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .height(80.dp)
                .clip(RoundedCornerShape(8.dp))

            Button(modifier = buttonModifier, onClick = {
                handleButton1Click(context)
            }) {
                ButtonContent(Icons.Default.Home, "Book Hotel")
            }

            Button(modifier = buttonModifier, onClick = {
                handleButton2Click(context)
            }) {
                ButtonContent(Icons.Default.Home, "Book Train")
            }

            Button(modifier = buttonModifier, onClick = {
                handleButton3Click(context)
                showToast(context, "Flight booking enabled: false")
            }) {
                ButtonContent(Icons.Default.Home, "Book Flights")
            }

            Button(modifier = buttonModifier, onClick = {
                handleButton4Click(context)
            }) {
                ButtonContent(Icons.Default.Home, "Book AirBnB")
            }
        }
    }
}

@Composable
fun ButtonContent(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = icon, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(fontSize = MaterialTheme.typography.labelMedium.fontSize * 2)
        )
    }
}

fun handleButton1Click(context: Context) {
    showToast(context, message = "Booking Hotel...")
}

fun handleButton2Click(context: Context) {
    showToast(context, message = "Booking Train...")
}

fun handleButton3Click(context: Context) {

    flagsmith.getValueForFeature(featureId = FEATURE_FLIGHT_BOOKING_KEY) { result ->
        val isFlightBookingEnabled = result.getOrNull()
        showToast(context, "Flight booking enabled: $isFlightBookingEnabled")
    }
}

fun handleButton4Click(context: Context) {
    showToast(context, message = "Booking AirBnB...")
}

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    TravelFlaggingTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TitleWithLogo(APP_NAME, R.drawable.flagsmith)
                TwoByTwoButtonGrid()
            }
        }
    }
}