package Screens.Professionals

import Network.Orders.presantation.OrdersViewModel
import Network.Professionals.presantation.ProfessionalsViewModel
import Screens.Orders.LoadingScreen
import Screens.Orders.OrdersSingleRow
import Screens.Orders.SingleComponentForHeader
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
                        enabled = false,
                        modifier = Modifier.padding(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Blue,
                            contentColor = Color.White
                        )
                    ) {

                        Text("Verified")

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

                Row(Modifier.fillMaxWidth().background(color = Color.White).padding(20.dp),) {

                    Column (modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(20.dp)){

                        Row (modifier = Modifier, ){

                            Box(modifier = Modifier.weight(1f)){

                                Column (verticalArrangement = Arrangement.spacedBy(6.dp)){
                                    Text(text = "Name", fontWeight = FontWeight.SemiBold)
                                    Text(text = professionalData.value?.professionalName.toString())
                                }

                            }
                            Box(modifier = Modifier.weight(1f)){
                                Column (verticalArrangement = Arrangement.spacedBy(6.dp)){
                                    Text(text = "Professional Id", fontWeight = FontWeight.SemiBold)
                                    Text(text = professionalData.value?.professionalId.toString())
                                }

                            }
                            Box(modifier = Modifier.weight(1f)){

                                Column (verticalArrangement = Arrangement.spacedBy(6.dp)){
                                    Text(text = "Email", fontWeight = FontWeight.SemiBold)
                                    Text(text = professionalData.value?.email.toString())
                                }

                            }


                        }

                        Row (modifier = Modifier.background(color = Color.White, shape = RoundedCornerShape(12.dp))){

                            Box(modifier = Modifier.weight(1f)){

                                Column (verticalArrangement = Arrangement.spacedBy(6.dp)){
                                    Text(text = "Mobile", fontWeight = FontWeight.SemiBold)
                                    Text(text = professionalData.value?.mobile.toString())
                                }

                            }
                            Box(modifier = Modifier.weight(1f)){

                                Column (verticalArrangement = Arrangement.spacedBy(6.dp)){
                                    Text(text = "Adhar", fontWeight = FontWeight.SemiBold)
                                    Text(text = professionalData.value?.adharNumber.toString())
                                }

                            }
                            Box(modifier = Modifier.weight(1f)){

                                Column (verticalArrangement = Arrangement.spacedBy(6.dp)){
                                    Text(text = "Address", fontWeight = FontWeight.SemiBold)
                                    Text(text = professionalData.value?.address.toString())
                                }

                            }


                        }

                        Row (modifier = Modifier.background(color = Color.White, shape = RoundedCornerShape(12.dp))){

                            Box(modifier = Modifier.weight(1f)){

                                Column (verticalArrangement = Arrangement.spacedBy(6.dp)){
                                    Text(text = "Status", fontWeight = FontWeight.SemiBold)
                                    Text(text = "Active")
                                }

                            }



                        }

                    }

                    AsyncImage(
                        modifier = Modifier.clip(RoundedCornerShape(12.dp)).height(100.dp).width(80.dp),
                        model = professionalData.value?.photoUrl,
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds
                    )

                }
            }

            item {

                Spacer(modifier = Modifier.width(12.dp))

                Column (modifier = Modifier.background(color = Color.White)){

                    Box (contentAlignment = Alignment.Center, modifier = Modifier.padding(20.dp).height(20.dp)){
                        Text(text = "Orders", fontWeight = FontWeight.Bold)
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

                            Row (modifier = Modifier.fillMaxWidth().height(72.dp).background(Color.White), verticalAlignment = Alignment.CenterVertically){

                                SingleComponentForHeader(text = "Service Product", modifier = Modifier.weight(1f))
                                //Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
                                SingleComponentForHeader(text = "Order Id", modifier = Modifier.weight(1f))
                                //Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
                                SingleComponentForHeader(text = "Paid(₹)", modifier = Modifier.weight(1f))
                                // Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
                                SingleComponentForHeader(text = "Date", modifier = Modifier.weight(1f))
                                // Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
                                SingleComponentForHeader(text = "Status", modifier = Modifier.weight(1f))


                            }

                            for (i in orderResultState.value.orderList) {

                                OrdersSingleRow(
                                    data = i,
                                    onItemClick = {

                                    }
                                )

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