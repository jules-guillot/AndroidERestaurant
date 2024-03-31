package fr.isen.guillot.androidrestaurant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.google.gson.Gson
import fr.isen.guillot.androidrestaurant.model.Dish
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import coil.compose.rememberImagePainter

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val menuItemJson = intent.getStringExtra("menuItem") ?: ""
            val menuItem = Gson().fromJson(menuItemJson, Dish::class.java)

            Column(modifier = Modifier.padding(16.dp)) {
                if (menuItem.images.isNotEmpty()) {
                    ImageCarousel(urls = menuItem.images)
                }
                Text(
                    text = menuItem.nameFr ?: "Nom non disponible",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontFamily = FontFamily.SansSerif
                    )
                )
                menuItem.ingredients.forEach { ingredient ->
                    Text(text = ingredient.nameFr ?: "Ingrédient non disponible")
                }
                menuItem.prices.firstOrNull()?.let { price ->
                    Text(text = "Prix : ${price.price}€")
                }
            }
        }
    }
}

@Composable
fun ImageCarousel(urls: List<String>) {
    LazyRow {
        items(urls) { url ->
            Image(
                painter = rememberImagePainter(
                    data = url,
                    builder = {
                        crossfade(true)
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .height(200.dp)
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        }
    }
}