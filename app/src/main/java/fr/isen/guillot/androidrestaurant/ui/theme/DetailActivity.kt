package fr.isen.guillot.androidrestaurant.ui.theme

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
class DetailActivity : ComponentActivity() {

    companion object CartManager {
        private val cartItems = mutableMapOf<String, Int>()

        fun addToCart(itemName: String, quantity: Int) {
            cartItems[itemName] = cartItems.getOrDefault(itemName, 0) + quantity
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val itemName = intent.getStringExtra("itemName") ?: "Nom inconnu"
        val itemIngredientsResName = itemName.lowercase().replace(" ", "_").replace("'", "_") + "_ingredients"
        val itemPriceResName = itemName.lowercase().replace(" ", "_").replace("'", "_") + "_prix"
        val resId = resources.getIdentifier(itemIngredientsResName, "array", packageName)
        val priceResId = resources.getIdentifier(itemPriceResName, "string", packageName)
        val itemIngredients = if (resId != 0) resources.getStringArray(resId) else arrayOf("Ingrédients inconnus")
        val itemPrice = if (priceResId != 0) getString(priceResId) else "Prix inconnu"

        setContent {
            val context = LocalContext.current
            var quantity by remember { mutableIntStateOf(1) }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = itemName, style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(bottom = 16.dp))

                PhotoCarousel(listOf("url_image1", "url_image2")) // Remplacez par les URL réelles des images

                Spacer(modifier = Modifier.height(16.dp))

                IngredientsList(itemIngredients)

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Prix : $itemPrice", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(bottom = 16.dp))

                Spacer(modifier = Modifier.height(16.dp))

                QuantitySelector(quantity = quantity, onQuantityChanged = { newQuantity ->
                    quantity = newQuantity
                })

                Spacer(modifier = Modifier.height(16.dp))

                AddToCartButton(itemName = itemName, quantity = quantity)

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { context.startActivity(Intent(context, CartActivity::class.java)) }) {
                    Text("Voir le panier")
                }
            }
        }
    }
}

@Composable
fun PhotoCarousel(photoUrls: List<String>) {
    LazyRow(modifier = Modifier.height(200.dp)) {
        items(photoUrls) { photoUrl ->
            Image(
                painter = rememberImagePainter(photoUrl),
                contentDescription = null,
                modifier = Modifier.size(200.dp).padding(8.dp)
            )
        }
    }
}

@Composable
fun QuantitySelector(quantity: Int, onQuantityChanged: (Int) -> Unit) {
    Row {
        Button(onClick = { if (quantity > 1) onQuantityChanged(quantity - 1) }) {
            Text("-")
        }
        Text(text = quantity.toString(), modifier = Modifier.padding(horizontal = 8.dp))
        Button(onClick = { onQuantityChanged(quantity + 1) }) {
            Text("+")
        }
    }
}

@Composable
fun AddToCartButton(itemName: String, quantity: Int) {
    val context = LocalContext.current
    Button(onClick = {
        DetailActivity.addToCart(itemName, quantity)
        context.startActivity(Intent(context, CartActivity::class.java))
    }) {
        Text("Ajouter au panier")
    }
}

@Composable
fun IngredientsList(ingredients: Array<String>) {
    Column {
        Text("Ingrédients :",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center)
        ingredients.forEach { ingredient ->
            Text(text = "• $ingredient", style = MaterialTheme.typography.bodyMedium)
        }
    }
}