package Screens.Login

import Navigation.NavRoutes
import Screens.Orders.TextInputWithTrailingIcon
import Screens.Products.CommonTextInput
import Screens.Products.TextInputWithIcon
import UI.Blue
import UI.CustomTextInput
import UI.PasswordTextInput
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import me.sample.library.resources.Res
import me.sample.library.resources.compose_multiplatform
import me.sample.library.resources.login
import me.sample.library.resources.savvy
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

    Row(modifier = Modifier.fillMaxSize().padding(24.dp)) {

        Column(modifier = Modifier.fillMaxHeight().weight(1f).background(Color.White)) {

//            Image(
//                modifier = Modifier.padding(40.dp).height(48.dp),
//                painter = painterResource(Res.drawable.savvy),
//                contentDescription = ""
//            )

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Image(
                    modifier = Modifier.size(600.dp).padding(start = 60.dp),
                    painter = painterResource(Res.drawable.login),
                    contentDescription = ""
                )

            }


        }

        Column(
            modifier = Modifier.fillMaxHeight().weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {


            Box(
                modifier = Modifier
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(12.dp),
                        spotColor = Blue,
                        ambientColor = Blue
                    )
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(12.dp)
                    )

            ) {

                Column(
                    modifier = Modifier.width(400.dp).padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {

                    Box(modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp), contentAlignment = Alignment.Center){
                        Image(
                            modifier = Modifier.height(48.dp),
                            painter = painterResource(Res.drawable.savvy),
                            contentDescription = ""
                        )
                    }

                    Text(
                        "Login to your account",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {

                        Text(text = "Email")

                        CustomTextInput(
                            modifier = Modifier.fillMaxWidth(),
                            text = email,
                            onValueChange = { email = it },
                            placeHolder = "example@gmail.com"
                        )
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {

                        Text(text = "Password")

                        CustomTextInput(
                            modifier = Modifier.fillMaxWidth(),
                            text = email,
                            onValueChange = { email = it },
                            placeHolder = "Password"

                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Button(
                        onClick = {
                            navHostController.navigate(NavRoutes.MainScreen.MainScreen.routes)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .shadow(
                                elevation = 15.dp,
                                spotColor = Blue,
                                ambientColor = Blue
                            ),
                        enabled = true,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Blue,
                            contentColor = Color.White
                        )
                    ) {
                        Text("SUBMIT")
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("New user?")
                        TextButton(onClick = {
                            navHostController.navigate(NavRoutes.Authentication.SignupScreen.routes)
                        }) {
                            Text("Register", fontWeight = FontWeight.SemiBold, color = Blue)
                        }
                    }
                }


            }


        }
    }

}