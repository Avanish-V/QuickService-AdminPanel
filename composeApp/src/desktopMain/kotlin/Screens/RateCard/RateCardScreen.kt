package Screens.RateCard

import Navigation.MainNavRoutes
import Network.Products.data.ProductDataModel
import Network.Rate.RateCardDataModel
import Network.Rate.RateCardViewModel
import Screens.Offers.CategorySelection
import Screens.Orders.LoadingScreen
import Screens.Orders.SingleComponentForHeader
import Screens.Products.CommonTextInput
import Screens.Promotion.CategorySelector
import UI.Blue
import UI.DarkSky
import UI.Red
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldDefaults
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import domain.usecase.UiState
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun RateCardScreen(modifier: Modifier = Modifier) {

    val rateCardViewModel = koinInject<RateCardViewModel>()

    val bottomSheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(BottomSheetValue.Collapsed),
        snackbarHostState = remember { SnackbarHostState() }
    )
    val scope = rememberCoroutineScope()

    var title by remember { mutableStateOf("") }
    var rate by remember { mutableStateOf("") }
    var applianceCategory by remember { mutableStateOf("") }

    val rateCardResult = rateCardViewModel.rateCardState.collectAsState()

    BottomSheetScaffold(
        scaffoldState = bottomSheetState,
        sheetPeekHeight = 0.dp,
        sheetShape = RoundedCornerShape(topStart = 24.dp,topEnd = 24.dp),
        backgroundColor = Color.LightGray,
        sheetContent = {

            Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(24.dp)) {

                Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically){
                    IconButton(onClick = {
                        scope.launch { bottomSheetState.bottomSheetState.collapse() }
                    }){
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                    Text("Add Rate")
                }

                Text("Product Title", fontWeight = FontWeight.SemiBold)
                CommonTextInput(
                    modifier = Modifier.fillMaxWidth(),
                    text = title,
                    onValueChange = {title = it},
                    label = "Title"
                )

                Text("Rate (₹)", fontWeight = FontWeight.SemiBold)
                CommonTextInput(
                    modifier = Modifier.fillMaxWidth(),
                    text = rate,
                    onValueChange = {rate = it},
                    label = "Rate"
                )

                Button(
                    onClick = {
                        scope.launch {
                            rateCardViewModel.createRateCard(
                                RateCardDataModel(
                                    title = title,
                                    rateId = "784545",
                                    rate = rate.toInt(),
                                    applianceCategory = applianceCategory
                                )
                            ).collect{
                                when(it){
                                    is UiState.LOADING->{
                                        scope.launch { bottomSheetState.snackbarHostState.showSnackbar("Creating....") }
                                    }
                                    is UiState.SUCCESS->{
                                        if (it.response){
                                            scope.launch { bottomSheetState.snackbarHostState.showSnackbar("Done!") }
                                        }
                                    }
                                    is UiState.ERROR->{
                                        scope.launch { bottomSheetState.snackbarHostState.showSnackbar(it.throwable.message.toString()) }
                                    }
                                }
                            }
                        }

                    },
                    modifier = Modifier.height(52.dp).fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Blue,
                        contentColor = Color.White
                    )
                ) {

                    Text("Save")
                }


            }

        },
        snackbarHost = {
            SnackbarHost(
                hostState = bottomSheetState.snackbarHostState
            ){
                Snackbar(snackbarData = it)
            }
        }
    ){

        Column(modifier = Modifier.fillMaxSize().background(Color.White)) {

            Row(
                modifier = Modifier.fillMaxWidth().padding(24.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {

                CategorySelector(
                    modifier = Modifier.width(300.dp).padding(horizontal = 24.dp),
                    isLoading = {},
                    onSelected = {
                        applianceCategory = it
                        rateCardViewModel.getRateCard(it)
                    },
                )

                Button(
                    onClick = {
                        scope.launch {
                            bottomSheetState.bottomSheetState.expand()
                        }
                    },
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
                    Text("Add Rate")
                }

            }

            Divider(modifier = Modifier.fillMaxWidth())

            Row(modifier = Modifier.fillMaxWidth().height(60.dp), verticalAlignment = Alignment.CenterVertically) {

                SingleComponentForHeader(text = "Title", modifier = Modifier.weight(1f))
                Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
                SingleComponentForHeader(text = "Rate", modifier = Modifier.weight(1f))
                Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
                SingleComponentForHeader(text = "Action", modifier = Modifier.weight(1f))
            }

            Divider(modifier = Modifier.fillMaxWidth())

            LazyColumn {

                items(rateCardResult.value.rateCardList){

                    RateCardSingleItem(
                        data = RateCardDataModel(
                            title = it.title,
                            rateId = it.rateId,
                            rate = it.rate,
                            applianceCategory = it.applianceCategory
                        ),
                        onDeleteClick = {},
                        onEditClick = {}
                    )
                }

            }
        }

        if (rateCardResult.value.isLoading) LoadingScreen()



    }




}

@Composable
fun RateCardSingleItem(data: RateCardDataModel, onDeleteClick:()->Unit, onEditClick:()->Unit) {

    Column {
        Row (modifier = Modifier.fillMaxWidth().height(48.dp), verticalAlignment = Alignment.CenterVertically){

            Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart){

                Text(modifier = Modifier.padding(start = 24.dp), text = data.title, maxLines = 2, overflow = TextOverflow.Clip)

            }
            Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
            Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart){

                Text(modifier = Modifier.padding(start = 24.dp), text ="₹${data.rate}", maxLines = 1, overflow = TextOverflow.Clip)

            }
            Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
            Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart){

                Row(modifier = Modifier.padding(start = 24.dp), horizontalArrangement = Arrangement.spacedBy(24.dp)) {

                    IconButton(
                        onClick = {onEditClick()}){
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "", tint = DarkSky)
                    }

                    IconButton(onClick = {onDeleteClick()}){
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "", tint = Red)
                    }

                }

            }

        }
        Divider(modifier = Modifier.fillMaxWidth())
    }


}