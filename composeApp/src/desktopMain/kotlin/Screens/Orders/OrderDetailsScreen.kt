package Screens.Orders

import Network.Orders.data.OrdersDataModel
import Network.Orders.data.PriceDetails
import Network.Professionals.presantation.ProfessionalsViewModel
import SharedViewModel.SharedOrderViewModel
import UI.Blue
import UI.Yellow
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.Model.ProfessionalById
import domain.usecase.UiState
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.launch
import me.sample.library.resources.Res
import me.sample.library.resources.shield_trust
import me.sample.library.resources.undraw_secure_login_pdn4
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@Composable
fun OrderDetailsScreen(navHostController: NavHostController) {

    val orderViewModel = koinInject<SharedOrderViewModel>()
    val professionalsViewModel = koinInject<ProfessionalsViewModel>()

    val orderData = orderViewModel.ordersData.collectAsState()
    val professionalData = professionalsViewModel.professionalsByIdResultState.collectAsState()

    orderData.value?.professionalID?.let {
        professionalsViewModel.getProfessionalsById(it)
    }

    val snackbarHostState = SnackbarHostState()

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Order Id: ${orderData.value?.orderId}") },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                    }
                },
                actions = {


                },
                backgroundColor = Color.White,
                elevation = 1.dp
            )
        },
        snackbarHost = {
            SnackbarHost(
                snackbar = {
                    Snackbar(snackbarData = it)
                },
                hostState = snackbarHostState
            )
        }
    ) { padding ->


        LazyColumn(
            contentPadding = PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {


            item {

                Row (modifier = Modifier.fillMaxWidth()){

                   Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {

                       Text(orderData.value?.productInfo?.productTitle ?: "null")

                       Text(
                           text = "₹ "+orderData.value?.priceTag?.price.toString(),
                           fontWeight = FontWeight.Bold
                       )

                   }

                    orderData.value?.status?.let {
                        Column (verticalArrangement = Arrangement.spacedBy(24.dp), horizontalAlignment = Alignment.End){
                            SingleComponentWithStatus(text = it)
                            if (it == "Canceled"){
                                Button(onClick = {}, colors = ButtonDefaults.buttonColors(backgroundColor = Blue, contentColor = Color.White)){
                                    Text("Create Refund")
                                }
                            }
                        }

                    }



                }




            }

            item {
                Divider(modifier = Modifier.fillMaxWidth())
            }

            item {

                Column {
                    Text("Transaction", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))

                    Row (modifier = Modifier.fillMaxWidth()){

                        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {

                            Text(("OrderId - " + orderData.value?.orderId) ?: "null")

                        }

                        Row (
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ){
                            Image(
                                painter = painterResource(Res.drawable.shield_trust),
                                contentDescription = "",
                                modifier = Modifier.size(24.dp),
                                colorFilter = ColorFilter.tint(Blue)
                            )
                            Text("Payment verified")
                        }
                    }
                }

            }

            item {
                Divider(modifier = Modifier.fillMaxWidth())
            }

            item {

                Column {
                    Text("Address", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(orderData.value?.address?.name ?: "null")
                    Text(orderData.value?.address?.mobile ?: "null")
                    Text(
                        orderData.value?.address?.building
                            ?: ("null" + orderData.value?.address?.area)
                            ?: ("null" + orderData.value?.address?.pinCode) ?: "null"
                    )

                    Text(
                        orderData.value?.address?.city ?: ("null" + orderData.value?.address?.state) ?: "null"
                    )

                    Text(orderData.value?.address?.type ?: "null", fontWeight = FontWeight.SemiBold)

                }

            }

            item {
                Divider(modifier = Modifier.fillMaxWidth())
            }

            item {

                Row (verticalAlignment = Alignment.CenterVertically){

                    Column(Modifier.weight(1f)) {
                        Text("Arrival Time", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(("Date- " + orderData.value?.dateTime?.date) ?: "null")
                        Text(("Time- " + orderData.value?.dateTime?.time) ?: "null")

                    }

                    professionalData.value.data?.let {
                        ProfessionalComponent(professionalData = it)
                    }

                }

            }


            item {
                Divider(modifier = Modifier.fillMaxWidth())
            }

            item {
                PriceTagOrderDetails(priceDetails = orderData.value?.priceTag)
            }





        }


    }

}
@Composable
fun PriceTagOrderDetails( priceDetails: PriceDetails?) {

    Row(
        Modifier
            .fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {

//        Icon(
//            painter = painterResource(id = icon),
//            contentDescription = "",
//            modifier = Modifier.size(24.dp),
//            tint = MaterialTheme.colorScheme.primary
//        )
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                text = "Price Details",
                fontWeight = FontWeight.Bold,

            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "Price",
                    style = MaterialTheme.typography.body1,

                )
                Text(
                    text = "₹${priceDetails?.price}",
                    style = MaterialTheme.typography.body1,
                )
            }

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "Tax & Fee",
                    style = MaterialTheme.typography.body1,

                )
                Text(
                    text = "₹${priceDetails?.tax}",
                    style = MaterialTheme.typography.body1,

                )
            }

            if (priceDetails?.coupon?.discount != 0){
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = "Promo-${priceDetails?.coupon?.couponCode}",
                        style = MaterialTheme.typography.body1,
                        color = Yellow
                    )
                    Text(
                        text = "-₹${priceDetails?.coupon?.discount}",
                        style = MaterialTheme.typography.body1,
                        color = Yellow
                    )
                }
            }


            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "Total ${priceDetails?.quantity}",
                    fontWeight = FontWeight.SemiBold,

                )
                Text(
                    text = "₹${priceDetails?.total}",
                    fontWeight = FontWeight.SemiBold,

                )
            }
        }

    }
}

@Composable
fun ProfessionalComponent(professionalData:ProfessionalById) {

    Row (verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(12.dp)){
        AsyncImage(
            modifier = Modifier.size(60.dp).clip(CircleShape),
            model = professionalData.photoUrl,
            contentDescription = "User",
            contentScale = ContentScale.Crop
        )
        Column (verticalArrangement = Arrangement.spacedBy(4.dp)){
            Text(professionalData.professionalName, fontWeight = FontWeight.Bold)
            Text("ID: "+professionalData.professionalId)
        }
    }
   
}