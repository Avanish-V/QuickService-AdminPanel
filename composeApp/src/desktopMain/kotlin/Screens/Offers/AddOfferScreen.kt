package Screens.Offers

import Navigation.MainNavRoutes
import Network.Category.Presantation.CategoryViewModel
import Network.Category.data.CategoryDataModel
import Network.Products.data.ProductDataModel
import Network.offers.data.OfferDataModel
import Network.offers.presantation.OfferViewModel
import Screens.Orders.LoadingScreen
import Screens.Orders.TextInputWithTrailingIcon
import Screens.Products.CommonTextInput
import Screens.Products.ProductList
import Screens.Products.TextInputWithIcon
import UI.Blue
import UI.DarkSky
import UI.Red
import UI.WheelDatePickerBottomSheet
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
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
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import dateToTimestamp
import domain.usecase.UiState
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import java.time.LocalDate

@Composable
fun SelectedProduct(data: ProductDataModel) {
    Row(
        modifier = Modifier.fillMaxWidth().height(80.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {


        Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {

            AsyncImage(
                modifier = Modifier.padding(start = 24.dp).width(120.dp).height(60.dp)
                    .clip(RoundedCornerShape(6.dp)),
                model = data.imageUrl,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                clipToBounds = true
            )

        }

        Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
        Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {

            Text(
                modifier = Modifier.padding(start = 24.dp),
                text = data.serviceTitle,
                maxLines = 1,
                overflow = TextOverflow.Clip
            )

        }
        Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
        Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {

            Text(
                modifier = Modifier.padding(start = 24.dp),
                text = data.serviceId,
                maxLines = 1,
                overflow = TextOverflow.Clip
            )

        }
        Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
        Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {

            Text(
                modifier = Modifier.padding(start = 24.dp),
                text = "₹${data.price}",
                fontWeight = FontWeight.SemiBold
            )

        }
        Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
        Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {

            Text(
                modifier = Modifier.padding(start = 24.dp),
                text = data.workType,
                fontWeight = FontWeight.SemiBold
            )

        }
        Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
        Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {

            Text(
                modifier = Modifier.padding(start = 24.dp),
                text = data.rating.rating,
                fontWeight = FontWeight.SemiBold
            )

        }

    }
}

@Composable
fun CreateOfferScreen(navHostController: NavHostController) {

    val snackbarHostState = SnackbarHostState()
    val categoryViewModel = koinInject<CategoryViewModel>()
    val createOfferViewModel = koinInject<createOfferViewModel>()
    val offerViewModel = koinInject<OfferViewModel>()
    LaunchedEffect(Unit) { categoryViewModel.getCategory() }
    val scope = rememberCoroutineScope()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(BottomSheetValue.Collapsed)
    )
     val DISCOUNT = "https://res.cloudinary.com/dni4h8jjy/image/upload/v1721736775/OfferImages/xokhemztoz1c9tnbbghi.svg"
     val SPECIAL_OFFER = "https://res.cloudinary.com/dni4h8jjy/image/upload/v1721736776/OfferImages/stroymz8erp9dxizjg8g.png"

    val result = categoryViewModel.category.collectAsState()
    var categoryList by remember { mutableStateOf(emptyList<CategoryDataModel>()) }
    var isLoading by remember { mutableStateOf(false) }
    var datePicker by remember { mutableStateOf(false) }


    var selectedProduct by remember { mutableStateOf<ProductDataModel?>(null) }




    when (result.value) {

        is UiState.LOADING -> {
            isLoading = true

        }

        is UiState.ERROR -> {
            isLoading = false
        }

        is UiState.SUCCESS -> {
            isLoading = false
            categoryList = (result.value as UiState.SUCCESS<List<CategoryDataModel>>).response
        }

    }



    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContent = {

            var productList by remember { mutableStateOf(emptyList<ProductDataModel>()) }

            createOfferViewModel.productTAG.value
                .let { it ->
                ProductList(
                    categoryId = it,
                    productList = {
                        isLoading = false
                        productList = it
                    },
                    isLoading = {
                        isLoading = true
                    },
                    isError = {
                        isLoading = false
                    }
                )
            }

            Scaffold(

                topBar = {
                    TopAppBar(
                        title = { Text("Select a product") },
                        backgroundColor = Color.Transparent,
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch {
                                    bottomSheetScaffoldState.bottomSheetState.collapse()
                                }
                            }) {
                                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                            }
                        },
                        actions = {
                            CategorySelection(
                                modifier = Modifier.width(300.dp).padding(vertical = 5.dp),
                                categoryList = categoryList,
                                selectedCategory = {
                                    createOfferViewModel.setProductTAG(it.categoryId)

                                }
                            )

                            Button(
                                onClick = {

                                },
                                modifier = Modifier.padding(10.dp).height(38.dp),
                                contentPadding = PaddingValues(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Blue,
                                    contentColor = Color.White
                                )
                            ) {
                                Text("Done")
                            }


                        },
                        elevation = 0.dp
                    )
                }

            ) {

                LazyColumn {
                    items(productList) { it ->
                        ProductsSingleItemWithCheckBox(
                            data = it,
                            onProductSelect = {
                                createOfferViewModel.setImageUrl(it.imageUrl)
                                createOfferViewModel.setProductTAG(it.serviceTAG)
                                createOfferViewModel.setProductTitle(it.serviceTitle)
                                createOfferViewModel.setProductId(it.serviceId)
                                selectedProduct = it
                            },
                            selectedProduct = if (selectedProduct == it) selectedProduct else null,
                        )
                    }
                }

                if (isLoading) LoadingScreen()

            }

        }
    ) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Create Offer") },
                    navigationIcon = {
                        IconButton(onClick = { navHostController.popBackStack() }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                        }
                    },
                    actions = {
                        Button(
                            onClick = {

                                scope.launch {
                                    offerViewModel.createOffer(

                                        OfferDataModel(
                                            productTAG = createOfferViewModel.productTAG.value,
                                            productTitle = createOfferViewModel.productTitle.value,
                                            productId = createOfferViewModel.productId.value,
                                            imageUrl = createOfferViewModel.imageUrl.value,
                                            discount = createOfferViewModel.discount.value,
                                            discountType = createOfferViewModel.discountType.value,
                                            workType = createOfferViewModel.workType.value,
                                            userType = createOfferViewModel.userType.value,
                                            promoCode = createOfferViewModel.promoCode.value,
                                            expiration = dateToTimestamp(createOfferViewModel.expiration.value),
                                            appliesTo = createOfferViewModel.appliesTo.value,
                                            status = createOfferViewModel.status.value
                                        )
                                    ).collect {
                                        when (it) {
                                            is UiState.LOADING -> {
                                                scope.launch {
                                                    snackbarHostState.showSnackbar("Creating....")
                                                }
                                            }

                                            is UiState.SUCCESS -> {
                                                scope.launch {
                                                    snackbarHostState.showSnackbar("Success!")
                                                }
                                            }

                                            is UiState.ERROR -> {
                                                scope.launch {
                                                    snackbarHostState.showSnackbar("Error" + it.throwable)
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
        ) { padding ->


            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(24.dp),
                verticalArrangement = Arrangement.spacedBy(40.dp)
            ) {


                item {

                    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {

                        Row {
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {

                                Text(text = "Applies To", fontWeight = FontWeight.SemiBold)

                                TypeSelection(
                                    modifier = Modifier.fillMaxWidth(),
                                    selectedType = {
                                        createOfferViewModel.setAppliesTo(it)
                                        createOfferViewModel.setImageUrl(SPECIAL_OFFER)

                                    }
                                )

                            }

                            if (createOfferViewModel.appliesTo.value == "Category") {

                                Spacer(modifier = Modifier.width(24.dp))

                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {

                                    Text(text = "Category", fontWeight = FontWeight.SemiBold)
                                    CategorySelection(
                                        modifier = Modifier.fillMaxWidth(),
                                        categoryList = categoryList,
                                        selectedCategory = {
                                           createOfferViewModel.setProductTAG(it.categoryId)
                                           createOfferViewModel.setImageUrl(it.imageUrl)
                                        })

                                }


                            }


                        }

                        if (createOfferViewModel.appliesTo.value == "Particular Product") {

                            Row(verticalAlignment = Alignment.CenterVertically) {

                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    selectedProduct.let {
                                        if (it != null) {
                                            SelectedProduct(data = it)
                                        }
                                    }


                                }

                                Button(
                                    onClick = {
                                        scope.launch {
                                            bottomSheetScaffoldState.bottomSheetState.expand()
                                        }
                                    }
                                ) {
                                    Text("Select Product")
                                }


                            }

                        }


                    }


                }

                item {

                    Row {

                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {

                            Text(text = "User Type", fontWeight = FontWeight.SemiBold)
                            UserTypeSelection(
                                modifier = Modifier.fillMaxWidth(),
                                selectedUserType = {
                                    createOfferViewModel.setUserType(it)
                                }
                            )

                        }

                        Spacer(modifier = Modifier.width(24.dp))

                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {

                            Text(text = "Work Type", fontWeight = FontWeight.SemiBold)
                            WorkTypeSelection(
                                modifier = Modifier.fillMaxWidth(),
                                selectedWorkType = {
                                    createOfferViewModel.setWorkType(it)
                                }
                            )

                        }

                    }


                }

                item {
                    Row {

                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {

                            Text(text = "Discount Type", fontWeight = FontWeight.SemiBold)

                            DiscountTypeSelection(
                                modifier = Modifier.fillMaxWidth(),
                                selectedDiscountType = {
                                    createOfferViewModel.setDiscountType(it)
                                }
                            )

                        }

                        Spacer(modifier = Modifier.width(24.dp))

                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {

                            Text(text = "Discount Value", fontWeight = FontWeight.SemiBold)
                            TextInputWithIcon(
                                modifier = Modifier.fillMaxWidth(),
                                text = createOfferViewModel.discount.value.toString(),
                                onValueChange = { createOfferViewModel.setDiscount(it) },
                                label = "Discount Rupee",
                                leadingIcon = {

                                    if (createOfferViewModel.discountType.value == "Percentage") {
                                        Text("%")
                                    } else {
                                        Text("₹")
                                    }

                                }
                            )

                        }


                    }
                }

                item {

                    Row {

                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {

                            Text(text = "Discount Code", fontWeight = FontWeight.SemiBold)
                            CommonTextInput(
                                modifier = Modifier.fillMaxWidth(),
                                text = createOfferViewModel.promoCode.value,
                                onValueChange = {createOfferViewModel.setPromoCode(it) },
                                label = "Promo Code"
                            )

                        }

                        Spacer(modifier = Modifier.width(24.dp))

                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {

                            Text(text = "Expiration", fontWeight = FontWeight.SemiBold)
                            TextInputWithTrailingIcon(
                                modifier = Modifier.fillMaxWidth(),
                                text = createOfferViewModel.expiration.value,
                                onValueChange = { createOfferViewModel.setExpiration(it) },
                                palceholder = "Expiration",
                                trailingIcon = {
                                    IconButton(onClick = {

                                        datePicker = true

                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.DateRange,
                                            contentDescription = null,
                                            tint = Blue
                                        )
                                    }

                                }
                            )

                        }

                    }


                }

            }

            WheelDatePickerBottomSheet(
                showDatePicker = datePicker,
                onDoneClick = {
                    datePicker = false
                },
                onDismissClick = {
                    datePicker = false
                },
                onDateSelect = {
                   createOfferViewModel.setExpiration(it)
                }
            )

        }

    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategorySelection(
    modifier: Modifier = Modifier,
    categoryList: List<CategoryDataModel>,
    selectedCategory: (CategoryDataModel) -> Unit
) {

    var isExpanded by remember {
        mutableStateOf(false)
    }

    var selectedText by remember { mutableStateOf("") }

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
                            selectedCategory(
                                CategoryDataModel(
                                    imageUrl = label.imageUrl,
                                    categoryId = label.categoryId,
                                    categoryTitle = ""
                                )
                            )
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UserTypeSelection(modifier: Modifier = Modifier, selectedUserType: (String) -> Unit) {

    var isExpanded by remember {
        mutableStateOf(false)
    }

    var selectedText by remember { mutableStateOf("") }

    val userType = listOf("All Users", "First User", "Selected")

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
                userType.forEach { label ->
                    DropdownMenuItem(
                        onClick = {
                            selectedText = label
                            selectedUserType(label)
                            isExpanded = false

                        }
                    ) {
                        Text(label)
                    }
                }
            }


        }

    }

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DiscountTypeSelection(modifier: Modifier = Modifier, selectedDiscountType: (String) -> Unit) {

    var isExpanded by remember {
        mutableStateOf(false)
    }

    var selectedText by remember { mutableStateOf("") }

    val userType = listOf("Percentage", "Fixed Amount", "Free Inspection Charge")

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
                userType.forEach { label ->
                    DropdownMenuItem(
                        onClick = {
                            selectedText = label
                            selectedDiscountType(label)
                            isExpanded = false

                        }
                    ) {
                        Text(label)
                    }
                }
            }


        }

    }

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TypeSelection(modifier: Modifier = Modifier, selectedType: (String) -> Unit) {

    var isExpanded by remember {
        mutableStateOf(false)
    }

    var selectedText by remember { mutableStateOf("") }

    val listData = listOf("All Products", "Category", "Particular Product")

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
                listData.forEach { label ->
                    DropdownMenuItem(
                        onClick = {
                            selectedText = label
                            selectedType(label)
                            isExpanded = false

                        }
                    ) {
                        Text(label)
                    }
                }
            }


        }

    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WorkTypeSelection(modifier: Modifier = Modifier, selectedWorkType: (String) -> Unit) {

    var isExpanded by remember {
        mutableStateOf(false)
    }

    var selectedText by remember { mutableStateOf("") }

    val listData = listOf("Repair", "Service")

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
                listData.forEach { label ->
                    DropdownMenuItem(
                        onClick = {
                            selectedText = label
                            selectedWorkType(label)
                            isExpanded = false

                        }
                    ) {
                        Text(label)
                    }
                }
            }


        }

    }

}

@Composable
fun ProductsSingleItemWithCheckBox(
    data: ProductDataModel,
    onProductSelect: (ProductDataModel) -> Unit,
    selectedProduct: ProductDataModel? = null
) {

    Column {
        Row(
            modifier = Modifier.fillMaxWidth().height(100.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {

                Checkbox(
                    modifier = Modifier.padding(start = 48.dp),
                    checked = if (data == selectedProduct) true else false,
                    onCheckedChange = {


                        onProductSelect(data)
                    }
                )

            }

            Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {

                AsyncImage(
                    modifier = Modifier.padding(start = 24.dp).width(140.dp).height(80.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    model = data.imageUrl,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    clipToBounds = true
                )

            }

            Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
            Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {

                Text(
                    modifier = Modifier.padding(start = 24.dp),
                    text = data.serviceTitle,
                    maxLines = 1,
                    overflow = TextOverflow.Clip
                )

            }
            Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
            Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {

                Text(
                    modifier = Modifier.padding(start = 24.dp),
                    text = data.serviceId,
                    maxLines = 1,
                    overflow = TextOverflow.Clip
                )

            }
            Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
            Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {

                Text(
                    modifier = Modifier.padding(start = 24.dp),
                    text = "₹${data.price}",
                    fontWeight = FontWeight.SemiBold
                )

            }
            Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
            Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {

                Text(
                    modifier = Modifier.padding(start = 24.dp),
                    text = data.workType,
                    fontWeight = FontWeight.SemiBold
                )

            }
            Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
            Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {

                Text(
                    modifier = Modifier.padding(start = 24.dp),
                    text = data.rating.rating,
                    fontWeight = FontWeight.SemiBold
                )

            }

        }
        Divider(modifier = Modifier.fillMaxWidth())
    }


}