package cz.chm4.chmtestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

import cz.chm4.chmtestapp.theme.CHMTestAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CHMTestApp()
        }
    }
}

@Composable
fun CHMTestApp() {
    CHMTestAppTheme {
       Text(" I am a text")
    }
}