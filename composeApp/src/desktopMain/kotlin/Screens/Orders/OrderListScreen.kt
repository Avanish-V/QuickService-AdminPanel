package Screens.Orders

import Navigation.MainNavRoutes
import Network.Orders.data.OrdersDataModel
import Network.Orders.data.Status
import Network.Orders.presantation.OrdersViewModel
import Screens.MainScreen
import SharedViewModel.SharedOrderViewModel
import UI.Blue
import UI.DarkSky
import UI.LightBlue
import UI.LightRed
import UI.LightSky
import UI.LightYellow
import UI.Red
import UI.Yellow
import UI.filterMenu
import UI.orderFilterList
import UI.secondary_color
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import domain.usecase.UiState
import kotlinx.serialization.json.Json
import me.sample.library.resources.Res
import me.sample.library.resources.filter_order
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OrderListScreen(navHostController: NavHostController) {

    val viewModel = koinInject<OrdersViewModel>()
    val orderViewModel = koinInject<SharedOrderViewModel>()

    LaunchedEffect(Unit){
        viewModel.getAllOrders()
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    var searchInput by remember {
        mutableStateOf("")
    }

    val orderState by viewModel.allOrders.collectAsState()

    var ordersList by remember { mutableStateOf(emptyList<OrdersDataModel>()) }

    when (orderState) {
        is UiState.LOADING -> {
            isLoading = true
        }

        is UiState.ERROR -> {
            val error = (orderState as UiState.ERROR).throwable
            Text("Error loading products: ${error.message}")
            isLoading = false
        }

        is UiState.SUCCESS -> {
            isLoading = false
            val products = (orderState as UiState.SUCCESS).response
            ordersList = products

        }
    }

    Column (modifier = Modifier.fillMaxSize().background(Color.White)){

        Column {

            Row (modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){

               Column (modifier = Modifier.weight(1f)){


                   SearchField(
                       modifier = Modifier.weight(1f),
                       text = searchInput,
                       onValueChange = {searchInput = it},
                       palceholder = "Search order...",
                       trailingIcon = {

                       },
                       onSearchClick = {}

                   )
               }
                Column(Modifier.weight(1f), horizontalAlignment = Alignment.End) {

                   filterMenu(
                       listData = orderFilterList,
                       onSelect = {

                       }
                   )

                }
              

            }

            Divider(modifier = Modifier.fillMaxWidth())

            Row (modifier = Modifier.fillMaxWidth().height(72.dp).background(Color.White), verticalAlignment = Alignment.CenterVertically){

                SingleComponentForHeader(text = "Service", modifier = Modifier.weight(1f))
                //Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
                SingleComponentForHeader(text = "Order Id", modifier = Modifier.weight(1f))
                //Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
                SingleComponentForHeader(text = "Paid", modifier = Modifier.weight(1f))
               // Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
                SingleComponentForHeader(text = "Date", modifier = Modifier.weight(1f))
               // Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
                SingleComponentForHeader(text = "Status", modifier = Modifier.weight(1f))


            }
            Divider(modifier = Modifier.fillMaxWidth())
        }

        if (isLoading){

            LoadingScreen()

        }else{

            LazyColumn (modifier = Modifier){

                items(ordersList){
                    OrdersSingleRow(
                        data = it,
                        onItemClick = {
                            navHostController.navigate(MainNavRoutes.Orders.OrderDetailScreen.routes).apply {
                                orderViewModel.passOrderData(it)
                            }

                        }
                    )
                }
            }

        }



    }


}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        CircularProgressIndicator(
            modifier = Modifier.size(40.dp).shadow(elevation = 0.dp, spotColor = Blue, ambientColor = Blue),
            color = Blue,
            strokeCap = StrokeCap.Round

        )
    }
}

@Composable
fun OrdersSingleRow(data: OrdersDataModel,onItemClick:()->Unit) {


    Column {
        Row (modifier = Modifier.clickable { onItemClick() }.fillMaxWidth().height(72.dp).background(Color.White), verticalAlignment = Alignment.CenterVertically){

            SingleComponent(text = data.serviceInfo.serviceTitle , modifier = Modifier.weight(1f))
           // Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
            SingleComponent(text = data.orderId, modifier = Modifier.weight(1f))
           // Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
            SingleComponent(text = "₹${data.priceTag.total}", modifier = Modifier.weight(1f))
           // Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
            SingleComponent(text = data.dateTime.date, modifier = Modifier.weight(1f))
           // Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
            SingleComponentWithStatus(text = data.status, modifier = Modifier.weight(1f))



        }
        Divider(modifier = Modifier.fillMaxWidth())
    }




}

@Composable
fun SingleComponent(text:String,modifier: Modifier = Modifier) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ){
        Text(text, modifier = Modifier.padding(start = 24.dp))
    }

}


@Composable
fun SingleComponentForHeader(text:String,modifier: Modifier = Modifier) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ){
        Text(text, color = Color.Gray, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(start = 24.dp))
    }

}

@Composable
fun SingleComponentWithStatus(text:String,modifier: Modifier = Modifier) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ){

        Box(modifier = Modifier
            .background(
                color = when (text) {
                    Status.Placed.name -> LightYellow
                    Status.Assigned.name -> LightSky
                    Status.Completed.name -> LightBlue
                    Status.Canceled.name -> LightRed
                    Status.Refunded.name -> LightSky
                    else -> Color.White
                },
                shape = RoundedCornerShape(5.dp)
            )
            .width(120.dp)
            .border(
                width = 2.dp,
                color = when (text) {
                    Status.Placed.name -> LightYellow
                    Status.Assigned.name -> LightSky
                    Status.Completed.name -> LightBlue
                    Status.Canceled.name -> LightRed
                    Status.Refunded.name -> LightSky
                    else -> Color.White
                },
                shape = RoundedCornerShape(5.dp)
            ),
            contentAlignment = Alignment.Center
        ){

            Text(
                text,
                color = when (text) {
                    Status.Placed.name -> Yellow
                    Status.Assigned.name -> DarkSky
                    Status.Completed.name -> Blue
                    Status.Canceled.name -> Red
                    Status.Refunded.name -> DarkSky
                    else -> Color.Black
                },

                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
            )

        }


    }

}

@Composable
fun TextInputWithTrailingIcon(
    modifier: Modifier = Modifier,
    text: String,
    onValueChange: (String) -> Unit,
    palceholder: String,
    trailingIcon: @Composable (() -> Unit)
) {


    TextField(
        modifier = modifier.border(
            0.5.dp,
            color = Color.LightGray,
            shape = RoundedCornerShape(8.dp)
        ),
        value = text,
        onValueChange = {
            onValueChange(it)
        },
        placeholder = { Text(palceholder) },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        trailingIcon = {
            trailingIcon()
        }

    )

}


@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    text: String,
    onValueChange: (String) -> Unit,
    palceholder: String,
    trailingIcon: @Composable (() -> Unit),
    onSearchClick:()->Unit
) {



    Row(modifier = Modifier.padding(24.dp).background(secondary_color), verticalAlignment = Alignment.CenterVertically) {

        TextField(
            modifier = modifier.border(
                0.5.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
            ).height(52.dp),
            value = text,
            onValueChange = {
                onValueChange(it)
            },
            placeholder = { Text(palceholder) },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            trailingIcon = {
                trailingIcon()
            }

        )

        Button(
            modifier = Modifier
                .background(color = Blue, shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp))
                .height(52.dp)
                .shadow(
                    elevation = 15.dp,
                    spotColor = Blue,
                    ambientColor = Blue
                ),
            onClick = {
                onSearchClick()
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Blue,
                contentColor = Color.White
            )
        ){
            Text("Search")
        }


    }


}