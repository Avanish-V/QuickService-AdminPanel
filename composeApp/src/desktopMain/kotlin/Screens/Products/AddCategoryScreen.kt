package Screens.Products


import Navigation.MainNavRoutes
import UI.secondary_color
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import me.sample.library.resources.Res
import me.sample.library.resources.ac
import me.sample.library.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.skia.Image
import java.io.File
import javax.swing.JFileChooser


@OptIn(ExperimentalMaterialApi::class)

@Composable
fun AddCategoryListScreen(navHostController: NavHostController) {

    var category by remember {
        mutableStateOf("")
    }
    var categoryId by remember {
        mutableStateOf("")
    }

    var tag by remember {
        mutableStateOf("")
    }

    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    Column (modifier = Modifier.fillMaxSize().background(secondary_color)){

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Add Category") },
                    navigationIcon = {
                        IconButton(onClick = {navHostController.navigate(MainNavRoutes.Products.CategoryList.routes)}){
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                        }
                    },
                    actions = {

                        Button(
                            onClick = {},
                            modifier = Modifier.padding(10.dp).height(38.dp),
                            contentPadding = PaddingValues(10.dp)
                        ){
                            Text("Submit")

                        }
                    },
                    backgroundColor = Color.White,
                    elevation = 1.dp
                )
            }
        ){

            Box(modifier = Modifier.fillMaxSize().background(secondary_color), contentAlignment = Alignment.Center){
                Column (
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier.width(400.dp).background(color = Color.White, shape = RoundedCornerShape(8.dp)).padding(vertical = 60.dp, horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){

                    Box(
                        modifier = Modifier.size(150.dp).border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ){


                        imageBitmap?.let { it1 ->
                            Image(
                                modifier = Modifier.size(120.dp),
                                bitmap = it1,
                                contentDescription = null
                            )
                        }


                        IconButton(onClick ={

                            val fileChooser = JFileChooser()


                            fileChooser.showOpenDialog(null)

                            val selectedFile: File? = fileChooser.selectedFile

                            selectedFile?.let {
                                val image = Image.makeFromEncoded(it.readBytes())
                                imageBitmap = image.toComposeImageBitmap()
                            }


                        }){
                            Icon(imageVector = Icons.Default.AddCircle, contentDescription = "")
                        }
                    }

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = category,
                        onValueChange = {category = it},
                        placeholder = { Text("Title") },
                        label = { Text("Title") }
                    )

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = categoryId,
                        onValueChange = {categoryId = it},
                        placeholder = { Text("Category Id") },
                        label = { Text("Category Id") }
                    )

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = tag,
                        onValueChange = {tag = it},
                        placeholder = { Text("TAG") },
                        label = { Text("Category TAG") }
                    )


                }
            }



        }


    }



}