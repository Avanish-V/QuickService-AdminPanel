package Screens.Login

import Navigation.NavRoutes
import UI.secondary_color
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import me.sample.library.resources.Res
import me.sample.library.resources.compose_multiplatform
import me.sample.library.resources.undraw_secure_login_pdn4
import org.jetbrains.compose.resources.painterResource

@Composable
fun LoginScreen(navHostController: NavHostController) {

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    Row (modifier = Modifier.fillMaxSize()){

        Box (modifier =Modifier.fillMaxHeight().weight(1f).background(secondary_color), contentAlignment = Alignment.Center){


            Image(
                painter = painterResource(Res.drawable.undraw_secure_login_pdn4),
                contentDescription = ""
            )


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
                        navHostController.navigate(NavRoutes.MainScreen.MainScreen.routes)
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