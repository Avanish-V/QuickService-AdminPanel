package Screens.Products

import Navigation.MainNavRoutes
import Network.Products.data.ProductDataModel
import Network.Products.presantation.ProductViewModel
import Screens.Orders.LoadingScreen
import Screens.Orders.SingleComponentForHeader
import UI.Blue
import UI.DarkSky
import UI.LightBlue
import UI.LightRed
import UI.Red
import UI.secondary_color
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.rememberPullRefreshState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import domain.usecase.UiState
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import java.lang.Error

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductScreen(navHostController: NavHostController) {

    val result = koinInject<ProductViewModel>()

    val categoryId by remember {
        mutableStateOf(navHostController.currentBackStackEntry?.savedStateHandle?.get<String>("serviceId"))
    }

    LaunchedEffect(Unit){

            categoryId.let {
                if (it != null) {
                    result.getProductList(it)
                }
            }
    }

    var isLoading by remember { mutableStateOf(false) }

    var alertDialog by remember { mutableStateOf(false) }

    var productId by remember { mutableStateOf<ProductDataModel?>(null) }

    val productState = result.productList.collectAsState()

    val scope = rememberCoroutineScope()

    var productList by remember { mutableStateOf(emptyList<ProductDataModel>()) }

    when(productState.value){

        is UiState.LOADING->{

           isLoading = true
        }
        is UiState.SUCCESS->{
            isLoading = false
            alertDialog = false
            productList = (productState.value as UiState.SUCCESS<List<ProductDataModel>>).response
        }
        is UiState.ERROR->{
            isLoading = false
        }
    }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Products") },
                    navigationIcon = {
                        IconButton(onClick = { navHostController.popBackStack() }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                        }
                    },
                    actions = {

                        Button(
                            onClick = {
                                navHostController.navigate(MainNavRoutes.Products.AddProductScreen.routes).apply {
                                    navHostController.currentBackStackEntry?.savedStateHandle?.set("categoryId",categoryId)
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
                        ){
                            Image(
                                imageVector = Icons.Default.Add,
                                contentDescription = "",
                                colorFilter = ColorFilter.tint(Color.White)
                            )
                            Text("Product")
                        }

                    },
                    backgroundColor = Color.White,
                    elevation = 1.dp,
                    modifier = Modifier.height(80.dp)
                )
            }
        ) {


            Box(modifier = Modifier.fillMaxSize()){

                Column {

                    Row(modifier = Modifier.fillMaxWidth().height(60.dp), verticalAlignment = Alignment.CenterVertically) {

                        SingleComponentForHeader(text = "Image", modifier = Modifier.weight(1f))
                        Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
                        SingleComponentForHeader(text = "Title", modifier = Modifier.weight(1f))
                        Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
                        SingleComponentForHeader(text = "ID", modifier = Modifier.weight(1f))
                        Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
                        SingleComponentForHeader(text = "Price", modifier = Modifier.weight(1f))
                        Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
                        SingleComponentForHeader(text = "Type", modifier = Modifier.weight(1f))
                        Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
                        SingleComponentForHeader(text = "Rating", modifier = Modifier.weight(1f))
                        Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
                        SingleComponentForHeader(text = "Action", modifier = Modifier.weight(1f))
                    }

                    Divider(modifier = Modifier.fillMaxWidth())

                    if (isLoading){

                        LoadingScreen()

                    }else{

                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                        ){
                            items(productList){
                                ProductsSingleItem(
                                    data = it,
                                    onEditClick = {
                                        navHostController.navigate(MainNavRoutes.Products.AddProductScreen.routes).apply {
                                            navHostController.currentBackStackEntry?.savedStateHandle?.set("TITLE","Edit")
                                        }
                                    },
                                    onDeleteClick = {
                                        alertDialog = true
                                        productId = it
                                    }
                                )
                            }
                        }
                    }
                }

                if (alertDialog){

                    DeleteAlertDialog(
                        isEnabled = {alertDialog = false},
                        onConfirmClick = {
                            scope.launch {

                                productId?.let { it1 ->

                                    result.deleteProduct(it1.productId).collect{

                                        when(it){
                                            is UiState.LOADING->{
                                                alertDialog = false
                                                isLoading = true
                                            }

                                            is UiState.SUCCESS->{
                                                isLoading = false
                                                result.removeFromListByID(productId!!)
                                            }

                                            is UiState.ERROR->{
                                                isLoading = false
                                            }
                                        }

                                    }
                                }
                            }

                        }
                    )

                }
            }


        }

}


@Composable
fun ProductsSingleItem(data:ProductDataModel,onDeleteClick:()->Unit,onEditClick:()->Unit) {

    Column {
        Row (modifier = Modifier.fillMaxWidth().height(100.dp), verticalAlignment = Alignment.CenterVertically){

            Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart){

                AsyncImage(
                    modifier = Modifier.padding(start = 24.dp).width(140.dp).height(80.dp).clip(RoundedCornerShape(6.dp)),
                    model = data.productImage,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    clipToBounds = true
                )

            }

            Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
            Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart){

                Text(modifier = Modifier.padding(start = 24.dp), text = data.productTitle, maxLines = 1, overflow = TextOverflow.Clip)

            }
            Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
            Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart){

                Text(modifier = Modifier.padding(start = 24.dp), text =data.productId, maxLines = 1, overflow = TextOverflow.Clip)

            }
            Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
            Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart){

                Text(modifier = Modifier.padding(start = 24.dp), text ="₹${data.price}", fontWeight = FontWeight.SemiBold)

            }
            Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
            Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart){

                Text(modifier = Modifier.padding(start = 24.dp), text =data.workType, fontWeight = FontWeight.SemiBold)

            }
            Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
            Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart){

                Text(modifier = Modifier.padding(start = 24.dp), text =data.rating.rating, fontWeight = FontWeight.SemiBold)

            }
            Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
            Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart){

                Row(modifier = Modifier.padding(start = 24.dp)) {

                    IconButton(
                        onClick = {onEditClick()}){
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "", tint = DarkSky)
                    }

                    IconButton(onClick = {onDeleteClick()}){
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "", tint = Blue)
                    }

                }

            }

        }
        Divider(modifier = Modifier.fillMaxWidth())
    }


 }

@Composable
fun DeleteAlertDialog(isEnabled:(Boolean)->Unit,onConfirmClick:()->Unit) {

    AlertDialog(
        onDismissRequest = {},
        dismissButton = {
            Button(
                modifier = Modifier.padding(vertical = 24.dp, horizontal = 6.dp),
                onClick = {isEnabled(false)},
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Blue,
                    contentColor = Color.White
                )
            ){
                Text("Cancel")
            }
        },
        confirmButton = {
            TextButton(
                modifier = Modifier.padding(vertical = 24.dp, horizontal = 6.dp),
                onClick = {onConfirmClick()},
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = Color.Black
                )
            ){
                Text("Confirm")
            }
        },
        title = { Text("Delete", fontWeight = FontWeight.Bold) },
        text = { Text("Are you sure?") }
    )

}


@Composable
fun ProductList(
    categoryId: String,
    productList:(List<ProductDataModel>)->Unit,
    isLoading:(Boolean)->Unit,
    isError:(Boolean)->Unit
){

    val result = koinInject<ProductViewModel>()

    LaunchedEffect(categoryId){
        categoryId.let {
            result.getProductList(it)
        }
    }

    val productState = result.productList.collectAsState()

    when(productState.value){

        is UiState.LOADING->{

            isLoading(true)
        }
        is UiState.SUCCESS->{
           isLoading(false)
           productList((productState.value as UiState.SUCCESS<List<ProductDataModel>>).response)
        }
        is UiState.ERROR->{
           isLoading(false)
        }
    }

}