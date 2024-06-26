import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import me.sample.library.resources.Res
import me.sample.library.resources.box_open
import me.sample.library.resources.dashboard
import me.sample.library.resources.order_history
import me.sample.library.resources.undraw_secure_login_pdn4
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

data class DrawerContent(
    val title: String,
    val icon: DrawableResource
)

val drawerList = mutableListOf<DrawerContent>(
    DrawerContent("Dashboard",Res.drawable.dashboard),
    DrawerContent("Orders",Res.drawable.order_history),
    DrawerContent("Products",Res.drawable.box_open),

)

@Composable
fun DrawerItem(onClick: (String) -> Unit, title: String,clickedItem:String,icon:DrawableResource) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(color = if (title == clickedItem) Color.Blue else Color.White)
            .height(48.dp)
            .clickable { onClick(title)},
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(36.dp).padding(start = 16.dp),
            painter = painterResource(icon),
            contentDescription = "",
            colorFilter = ColorFilter.tint(if (title == clickedItem) Color.White else Color.Black)
        )

        Text(text = "${title}", color = if (title == clickedItem) Color.White else Color.Black)
    }
}