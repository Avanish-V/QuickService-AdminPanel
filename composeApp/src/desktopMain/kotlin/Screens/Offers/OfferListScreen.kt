package Screens.Offers

import Navigation.MainNavRoutes
import Network.offers.data.OfferDataModel
import Network.offers.presantation.OfferViewModel
import Network.offers.presantation.ResultState
import Screens.Orders.SingleComponent
import Screens.Orders.SingleComponentForHeader
import Screens.Orders.SingleComponentWithStatus
import Screens.Products.DeleteAlertDialog
import UI.Blue
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import domain.usecase.UiState
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import timestampToDate

@Composable
fun OfferListScreen(navHostController: NavHostController) {

    val offerViewModel = koinInject<OfferViewModel>()

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        offerViewModel.getOfferList()
    }

    val offerList = offerViewModel.state.collectAsState()

    val snackbarHostState = SnackbarHostState()

    var dialog by remember { mutableStateOf(false) }
    var id by remember { mutableStateOf("") }



    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Offer") },
                actions = {

                    Button(
                        onClick = {
                            navHostController.navigate(MainNavRoutes.Offer.createOfferScreen.routes)
                                .apply {

                                }
                        },
                        modifier = Modifier
                            .padding(10.dp)
                            .height(38.dp)
                            .shadow(
                                elevation = 15.dp,
                                spotColor = Blue,
                                ambientColor = Blue
                            ),
                        contentPadding = PaddingValues(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Blue,
                            contentColor = Color.White
                        )
                    ) {
                        Image(
                            imageVector = Icons.Default.Add,
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                        Text("Create Offer")
                    }
                },
                backgroundColor = Color.White,
                elevation = 1.dp,
                modifier = Modifier.height(80.dp)
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


        Column {

            Row(Modifier.height(48.dp), verticalAlignment = Alignment.CenterVertically) {
                SingleComponentForHeader("Image", modifier = Modifier.weight(1f))
                SingleComponentForHeader("Applies to", modifier = Modifier.weight(1f))
                SingleComponentForHeader("Discount", modifier = Modifier.weight(1f))
                SingleComponentForHeader("Promo", modifier = Modifier.weight(1f))
                SingleComponentForHeader("Expiration", modifier = Modifier.weight(1f))
                SingleComponentForHeader("Status", modifier = Modifier.weight(1f))
                SingleComponentForHeader("Action", modifier = Modifier.weight(1f))
            }

            Divider(modifier = Modifier.fillMaxWidth())

            LazyColumn {
                items(offerList.value.data){data->
                    if (data != null) {
                        OfferListSingleRow(
                            offerData = data,
                            onStatusChange = {
                               scope.launch {
                                   offerViewModel.updateStatus(status = it, id = data.promoCode).collect{
                                       when(it){
                                           is UiState.LOADING->{
                                               scope.launch { snackbarHostState.showSnackbar("Loading...") }
                                           }
                                           is UiState.SUCCESS->{
                                               scope.launch { snackbarHostState.showSnackbar("Success!") }
                                           }
                                           is UiState.ERROR->{
                                               scope.launch { snackbarHostState.showSnackbar("Error:${it.throwable.message}") }
                                           }
                                       }
                                   }
                               }
                            },
                            onDeleteClick = {
                                dialog = true
                                id = data.promoCode
                            }
                        )
                    }
                }
            }
        }

        if (dialog){
            DeleteAlertDialog(
                isEnabled = {dialog  = it},
                onConfirmClick = {

                    scope.launch {
                        offerViewModel.deleteOffer(id = id).collect{
                            when(it){
                                is UiState.LOADING->{
                                    dialog = false
                                    scope.launch { snackbarHostState.showSnackbar("Loading...") }
                                }
                                is UiState.SUCCESS->{
                                    scope.launch { snackbarHostState.showSnackbar("Deleted!") }
                                }
                                is UiState.ERROR->{
                                    scope.launch { snackbarHostState.showSnackbar("Error:$it") }
                                }
                            }
                        }
                    }
                }
            )
        }




    }

}

@Composable
fun OfferListSingleRow(
    offerData:OfferDataModel,
    onStatusChange:(Boolean)->Unit,
    onDeleteClick:()->Unit
) {

    var status by remember { mutableStateOf(offerData.status) }

    Column {
        Row (modifier = Modifier.clickable {}.fillMaxWidth().height(72.dp).background(Color.White), verticalAlignment = Alignment.CenterVertically){


            Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart){

                AsyncImage(
                    modifier = Modifier.padding(start = 24.dp).size(48.dp),
                    model = offerData.imageUrl,
                    contentDescription = "",
                   // colorFilter = ColorFilter.tint(Blue)
                )

            }
            SingleComponent(
                text = (if (offerData.appliesTo == "Category") offerData.productTAG else offerData.appliesTo).toString(),
                modifier = Modifier.weight(1f)
            )
            // Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
            SingleComponent(text = (if (offerData.discountType == "Percentage") "${offerData.discount}%" else "₹${offerData.discount}"), modifier = Modifier.weight(1f))
            // Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
            SingleComponent(text = offerData.promoCode, modifier = Modifier.weight(1f))
            // Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
            if (offerData.expiration != null){
                SingleComponent(text = timestampToDate(offerData.expiration), modifier = Modifier.weight(1f))
            }else{
                SingleComponent(text = "No", modifier = Modifier.weight(1f))
            }
            // Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
            Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart){

                Switch(
                    modifier = Modifier.padding(start = 24.dp),
                    checked = status,
                    onCheckedChange = {
                        status = it
                        onStatusChange(it)
                    },
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = Blue,
                        checkedThumbColor = Blue
                    )
                )

            }

            Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart){

                IconButton(
                    modifier = Modifier.padding(start = 24.dp),
                    onClick = {onDeleteClick()}
                ){
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = Blue
                    )
                }

            }



        }
        Divider(modifier = Modifier.fillMaxWidth())
    }
}
