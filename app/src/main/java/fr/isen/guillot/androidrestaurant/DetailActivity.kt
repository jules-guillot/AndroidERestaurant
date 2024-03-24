package fr.isen.guillot.androidrestaurant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.google.gson.Gson
import fr.isen.guillot.androidrestaurant.model.Dish

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val menuItemJson = intent.getStringExtra("menuItem") ?: ""
            val menuItem = Gson().fromJson(menuItemJson, Dish::class.java)

            Column(modifier = Modifier.padding(16.dp)) {
                menuItem.images.firstOrNull()?.let { imageUrl ->
                    ImageFromUrls(urls = listOf(imageUrl))
                }
                Text(text = menuItem.nameFr ?: "Nom non disponible",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontFamily = FontFamily.SansSerif
                    )
                )
                // Ici, vous pouvez ajouter plus de détails sur le plat
            }
        }
    }
}