package Screens.Products


import Navigation.MainNavRoutes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import me.sample.library.resources.Res
import me.sample.library.resources.ac
import me.sample.library.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategoryListScreen(navHostController: NavHostController) {

    Column (modifier = Modifier.fillMaxSize().background(Color.White)){

        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End){

            Button(
                onClick = {navHostController.navigate(MainNavRoutes.Products.AddCategory.routes)},
                modifier = Modifier.padding(10.dp).height(38.dp),
                contentPadding = PaddingValues(10.dp)
            ){
                Image(
                    imageVector = Icons.Default.Add,
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(Color.White)
                )
                Text("Category")
            }

        }


        Divider()

        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            columns = GridCells.Fixed(8),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
        ){

            items(10){

                Card(modifier = Modifier.width(80.dp), onClick = {}){

                    Column (
                        modifier= Modifier.padding(vertical = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Image(
                            modifier = Modifier.size(60.dp),
                            painter = painterResource(Res.drawable.ac),
                            contentDescription =  null
                        )
                        Text("AC Service")
                    }

                }


            }
        }

    }



}