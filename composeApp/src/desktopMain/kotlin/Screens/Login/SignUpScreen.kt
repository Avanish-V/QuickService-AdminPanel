package Screens.Login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun SignupScreen(modifier: Modifier = Modifier) {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Text("Signup")
    }
}