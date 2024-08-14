package Screens.Professionals

import Network.Orders.presantation.OrdersViewModel
import Network.Professionals.presantation.ProfessionalsViewModel
import Screens.Orders.LoadingScreen
import SharedViewModel.SharedProfessionalViewModel
import UI.Blue
import UI.secondary_color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import domain.usecase.UiState
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun ProfessionalDetail(navHostController: NavHostController) {

    val snackbarHostState = SnackbarHostState()
    val sharedProfessionalViewModel = koinInject<SharedProfessionalViewModel>()
    val orderViewModel = koinInject<OrdersViewModel>()

    val professionalData = sharedProfessionalViewModel.professionalData.collectAsState()
    val orderResultState = orderViewModel.orderByProfessional.collectAsState()

    LaunchedEffect(Unit){
        professionalData.value?.let { orderViewModel.getOrdersByProfessional(it.professionalId) }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Professional Detail") },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                    }
                },
                actions = {

                    Button(
                        onClick = {

                        },
                        modifier = Modifier.padding(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Blue,
                            contentColor = Color.White
                        )
                    ) {

                        Text("Verify")

                    }
                },
                elevation = 1.dp,
                backgroundColor = Color.White
            )
        },
        snackbarHost = {
            SnackbarHost(
                snackbar = {
                    Snackbar(snackbarData = it)
                },
                hostState = snackbarHostState
            )
        },
        backgroundColor = secondary_color
    ) { padding ->


        LazyColumn (modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp)){

            item {

                Row(Modifier.fillMaxWidth().padding(top = 12.dp)) {

                    Row (modifier = Modifier.background(color = Color.White, shape = RoundedCornerShape(12.dp))){

                        Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {

                            Column (verticalArrangement = Arrangement.spacedBy(6.dp)){
                                Text(text = "Name", fontWeight = FontWeight.SemiBold)
                                Text(text = professionalData.value?.professionalName.toString())
                            }
                            Column (verticalArrangement = Arrangement.spacedBy(6.dp)){
                                Text(text = "Professional Id", fontWeight = FontWeight.SemiBold)
                                Text(text = professionalData.value?.professionalId.toString())
                            }
                            Column (verticalArrangement = Arrangement.spacedBy(6.dp)){
                                Text(text = "Email", fontWeight = FontWeight.SemiBold)
                                Text(text = professionalData.value?.email.toString())
                            }
                            Column (verticalArrangement = Arrangement.spacedBy(6.dp)){
                                Text(text = "Mobile", fontWeight = FontWeight.SemiBold)
                                Text(text = professionalData.value?.mobile.toString())
                            }
                            Column (verticalArrangement = Arrangement.spacedBy(6.dp)){
                                Text(text = "Adhar", fontWeight = FontWeight.SemiBold)
                                Text(text = professionalData.value?.adharNumber.toString())
                            }
                            Column (verticalArrangement = Arrangement.spacedBy(6.dp)){
                                Text(text = "Address", fontWeight = FontWeight.SemiBold)
                                Text(text = professionalData.value?.address.toString())
                            }

                        }

                        AsyncImage(
                            modifier = Modifier.padding(top = 24.dp, end = 24.dp).clip(RoundedCornerShape(12.dp)).height(100.dp).width(80.dp),
                            model = professionalData.value?.photoUrl,
                            contentDescription = "",
                            contentScale = ContentScale.FillBounds
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column (modifier = Modifier.weight(1f).background(color = Color.White, shape = RoundedCornerShape(12.dp))){

                        Box (contentAlignment = Alignment.Center, modifier = Modifier.padding(20.dp).height(20.dp)){
                            Text(text = "Orders", fontWeight = FontWeight.Bold)
                        }

                        Divider()
                        Row(modifier = Modifier.height(42.dp), verticalAlignment = Alignment.CenterVertically) {

                            Box (modifier = Modifier.weight(1f), contentAlignment = Alignment.Center){
                                Text(text = "Product")
                            }
                            Box (modifier = Modifier.weight(1f), contentAlignment = Alignment.Center){
                                Text(text = "Category")
                            }
                            Box (modifier = Modifier.weight(1f), contentAlignment = Alignment.Center){
                                Text(text = "Product Id")
                            }
                            Box (modifier = Modifier.weight(1f), contentAlignment = Alignment.Center){
                                Text(text = "Price")
                            }
                            Box (modifier = Modifier.weight(1f), contentAlignment = Alignment.Center){
                                Text(text = "Booked")
                            }

                        }
                        Divider()

                        when {
                            orderResultState.value.isLoading -> {

                                LoadingScreen()
                            }
                            orderResultState.value.error.isNotEmpty() -> {
                               Text(orderResultState.value.error)
                            }
                            orderResultState.value.orderList.isNotEmpty() -> {
                               LazyColumn {
                                   items(orderResultState.value.orderList){
                                       Text(it.professionalID)
                                   }
                               }
                            }
                            else -> {
                                // Handle any other cases, if needed
                            }
                        }


                    }






                }
            }



        }



    }


}