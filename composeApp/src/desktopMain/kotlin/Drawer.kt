import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

data class DrawerContent(
    val title: String,

    )

val drawerList = mutableListOf<DrawerContent>(
    DrawerContent("Dashboard"),
    DrawerContent("Orders"),
    DrawerContent("Products"),
    DrawerContent("Offers"),
    DrawerContent("Professionals"),
)

@Composable
fun DrawerItem(onClick: (String) -> Unit, title: String,clickedItem:String) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(color = if (title == clickedItem) Color.Blue else Color.White)
            .height(48.dp)
            .clickable { onClick(title)},
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "${title}", color = if (title == clickedItem) Color.White else Color.Black)
    }
}