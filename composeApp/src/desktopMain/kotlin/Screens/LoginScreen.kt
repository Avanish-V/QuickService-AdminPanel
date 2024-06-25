package Screens

import Navigation.NavRoutes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Colors
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.jetbrains.skia.Color

@Composable
fun LoginScreen(navHostController: NavHostController) {

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    Row (modifier = Modifier.fillMaxSize()){

        Column (modifier =Modifier.fillMaxHeight().weight(1f)){



        }
        Column (modifier =Modifier.fillMaxHeight().weight(1f), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){


            Column (modifier = Modifier.width(400.dp), verticalArrangement = Arrangement.spacedBy(24.dp), horizontalAlignment = Alignment.CenterHorizontally){

                Text("LogIn", fontWeight = FontWeight.Bold, fontSize = 32.sp)

                Spacer(modifier = Modifier.height(60.dp))

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = email,
                    onValueChange = {email = it},
                    placeholder = { Text("example@gmail.com") },
                    label = { Text("Email") }
                )

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = password,
                    onValueChange = {password = it},
                    placeholder = { Text("Password") },
                    label = { Text("Password") }
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        navHostController.navigate(NavRoutes.MainScreen.Dashboard.routes)
                    },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    enabled = true,
                    colors =ButtonDefaults.buttonColors(
                        backgroundColor = androidx.compose.ui.graphics.Color.Blue
                    )
                ){
                    Text("LogIn")
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("New user?")
                    TextButton(onClick = {
                        navHostController.navigate(NavRoutes.Authentication.SignupScreen.routes)
                    }){
                        Text("Register", fontWeight = FontWeight.SemiBold)
                    }
                }

            }



        }
    }

}