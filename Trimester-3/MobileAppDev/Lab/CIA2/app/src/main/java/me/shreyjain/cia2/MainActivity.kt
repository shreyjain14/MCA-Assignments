package me.shreyjain.cia2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import me.shreyjain.cia2.ui.navigation.AppNavigation
import me.shreyjain.cia2.ui.theme.CIA2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CIA2Theme {
                AppNavigation()
            }
        }
    }
}