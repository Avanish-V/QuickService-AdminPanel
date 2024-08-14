package Screens.Promotion

import Navigation.MainNavRoutes
import Network.Category.Presantation.CategoryViewModel
import Network.Category.data.CategoryDataModel
import Network.Promotion.data.PromotionDataModel
import Network.Promotion.presantation.PromotionViewModel
import Network.offers.data.OfferDataModel
import Screens.Orders.LoadingScreen
import Screens.Orders.SingleComponent
import Screens.Orders.SingleComponentForHeader
import Screens.Products.ImageSection
import Screens.Products.TypeSelection
import UI.Blue
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import domain.usecase.UiState
import kotlinx.coroutines.launch
import me.sample.library.resources.Res
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PromotionScreen(navHostController: NavHostController) {

    val bottomSheetState = rememberBottomSheetScaffoldState(bottomSheetState = rememberBottomSheetState(BottomSheetValue.Collapsed))
    val scope = rememberCoroutineScope()
    val snackbarHostState = SnackbarHostState()
    val categoryViewModel = koinInject<CategoryViewModel>()
    val promotionViewModel  = koinInject<PromotionViewModel>()
    LaunchedEffect(Unit){
        promotionViewModel.getPromotion()
    }
    val promotionResult = promotionViewModel.promotionDataState.collectAsState()
    var imageBytes by remember { mutableStateOf<ByteArray?>(null) }
    var workType by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }

    BottomSheetScaffold(
        scaffoldState = bottomSheetState,
        topBar = {
            TopAppBar(
                title = { Text("Promotion") },
                actions = {

                    Button(
                        onClick = {
                            scope.launch {
                                bottomSheetState.bottomSheetState.expand()
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
                        Text("Create New")
                    }

                },
                backgroundColor = Color.White,
                elevation = 1.dp,
                modifier = Modifier.height(80.dp)
            )
        },
        sheetPeekHeight = 0.dp,
        sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        sheetGesturesEnabled = true,
        sheetContent = {

            Scaffold (
                topBar = {
                    TopAppBar(
                        title = { Text("Promotion") },
                        actions = {

                            TextButton(
                                onClick = {
                                    scope.launch {

                                        imageBytes?.let {
                                            promotionViewModel.createPromotion(
                                                imageByteArray = it,
                                                category = selectedCategory,
                                                workType = workType
                                            ).collect{
                                                when(it){
                                                    is UiState.LOADING->{
                                                        scope.launch { snackbarHostState.showSnackbar("Loading...") }
                                                    }

                                                    is UiState.SUCCESS->{
                                                        scope.launch { snackbarHostState.showSnackbar("Success", actionLabel = "Success")}
                                                    }

                                                    is UiState.ERROR->{
                                                        scope.launch { snackbarHostState.showSnackbar(it.throwable.message.toString()) }
                                                    }
                                                }
                                            }
                                        }

                                    }
                                },
                                modifier = Modifier.padding(10.dp).height(38.dp),
                                contentPadding = PaddingValues(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Blue,
                                    contentColor = Color.White
                                )
                            ) {
                                Text("Create")
                            }

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
            ){

                Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(24.dp)) {
                    ImageSection(imageByte = imageBytes, imageByteArray = { imageBytes = it })
                    TypeSelection(workType = {workType = it})
                    CategorySelector(
                        isLoading = {},
                        onSelected = {selectedCategory = it}
                    )
                }
            }

        }
    ){

        if (promotionResult.value.isLoading){
            LoadingScreen()
        }

        if (promotionResult.value.data.isNotEmpty()){
            Column {

                Row(Modifier.height(48.dp), verticalAlignment = Alignment.CenterVertically) {
                    SingleComponentForHeader("Image", modifier = Modifier.weight(1f))
                    SingleComponentForHeader("CategoryId", modifier = Modifier.weight(1f))
                    SingleComponentForHeader("WorkType", modifier = Modifier.weight(1f))
                    SingleComponentForHeader("Status", modifier = Modifier.weight(1f))
                    SingleComponentForHeader("Action", modifier = Modifier.weight(1f))
                }

                Divider(modifier = Modifier.fillMaxWidth())

                LazyColumn(){

                    items(promotionResult.value.data){
                        if (it != null) {
                            PromotionSingleItem(
                                promotionData = it,
                                onStatusChange = {},
                                onDeleteClick = {}
                            )
                        }
                    }
                }
            }
        }


    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategorySelector(modifier:Modifier = Modifier,isLoading:(Boolean)->Unit,onSelected:(String)->Unit) {

    var isExpanded by remember {
        mutableStateOf(false)
    }

    val categoryViewModel = koinInject<CategoryViewModel>()

    var selectedText by remember { mutableStateOf("Air Conditioner") }

    LaunchedEffect(Unit){
        categoryViewModel.getCategory()
    }

    val result = categoryViewModel.category.collectAsState()

    var categoryList by remember { mutableStateOf(emptyList<CategoryDataModel>()) }

    when(result.value){

        is UiState.LOADING -> {
            isLoading(true)

        }
        is UiState.ERROR -> {
           isLoading(false)
        }
        is UiState.SUCCESS -> {
            isLoading(false)
            categoryList = (result.value as UiState.SUCCESS<List<CategoryDataModel>>).response
        }

    }

    Row(modifier = modifier) {

        ExposedDropdownMenuBox(
            expanded = false,
            onExpandedChange = {
                isExpanded = !isExpanded
            }
        ) {

            TextField(

                value = selectedText,
                onValueChange = { selectedText = it },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                },
                modifier = Modifier.fillMaxWidth()
                    .border(0.5.dp, color = Color.LightGray, shape = RoundedCornerShape(8.dp)),
                readOnly = true,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }

            ) {
                categoryList.forEach { label ->
                    DropdownMenuItem(
                        onClick = {
                            selectedText = label.categoryTitle
                            onSelected(label.categoryId)
                            isExpanded = false

                        }
                    ) {
                        Text(label.categoryTitle)
                    }
                }
            }


        }

    }
}


@Composable
fun PromotionSingleItem(
    promotionData: PromotionDataModel,
    onStatusChange:(Boolean)->Unit,
    onDeleteClick:()->Unit
) {

    var status by remember { mutableStateOf(false) }

    Column {
        Row (modifier = Modifier.clickable {}.fillMaxWidth().background(Color.White), verticalAlignment = Alignment.CenterVertically){


            Box(Modifier.weight(1f).padding(vertical = 12.dp), contentAlignment = Alignment.CenterStart){

                AsyncImage(
                    modifier = Modifier.padding(start = 24.dp).width(160.dp).height(100.dp).clip(RoundedCornerShape(6.dp)),
                    model = promotionData.imageUrl,
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    clipToBounds = true
                )

            }
            SingleComponent(text = promotionData.categoryId , modifier = Modifier.weight(1f))
            // Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
            SingleComponent(text = promotionData.workType, modifier = Modifier.weight(1f))
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
                        contentDescription = null
                    )
                }

            }



        }
        Divider(modifier = Modifier.fillMaxWidth())
    }
}