package fr.isen.guillot.androidrestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import fr.isen.guillot.androidrestaurant.model.MenuResponse
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONObject
import coil.compose.rememberImagePainter
import fr.isen.guillot.androidrestaurant.ui.theme.AndroidERestaurantTheme
import fr.isen.guillot.androidrestaurant.model.*

class CategoryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val categoryName = intent.getStringExtra("categoryName") ?: "Catégorie"

        setContent {
            val isLoading = remember { mutableStateOf(true) }
            val menuItems = remember { mutableStateOf<List<Dish>>(listOf()) }
            val context = LocalContext.current

            LaunchedEffect(key1 = Unit) {
                fetchMenuItems(categoryName) { dishes ->
                    menuItems.value = dishes
                    isLoading.value = false
                }
            }

            AndroidERestaurantTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    if (isLoading.value) {
                        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                    } else {
                        MenuScreen(categoryName = categoryName,
                            items = menuItems.value,
                            onClick = { menuItem ->
                                val intent = Intent(context, DetailActivity::class.java).apply {
                                    val gson = Gson()
                                    val menuItemJson = gson.toJson(menuItem)
                                    putExtra("menuItem", menuItemJson)
                                }
                                context.startActivity(intent)
                            })
                    }
                }
            }
        }
    }

    private fun fetchMenuItems(categoryName: String, onResult: (List<Dish>) -> Unit) {
        val queue = Volley.newRequestQueue(this)
        val url = "http://test.api.catering.bluecodegames.com/menu"
        val params = JSONObject().apply {
            put("id_shop", "1")
        }

        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, url, params, { response ->
            try {
                val menuResponse = Gson().fromJson(response.toString(), MenuResponse::class.java)
                val filteredItems = menuResponse.data.firstOrNull { it.nameFr == categoryName }?.items ?: arrayListOf()
                onResult(filteredItems)
            } catch (e: Exception) {
                Log.e("CategoryActivity", "Parsing error", e)
                onResult(listOf())
            }
        }, { error ->
            Log.e("CategoryActivity", "Volley error: ${error.message}")
            onResult(listOf())
        })

        queue.add(jsonObjectRequest)
    }
}

@Composable
fun MenuScreen(categoryName: String, items: List<Dish>, onClick: (Dish) -> Unit) {
    Column {
        Text(
            text = categoryName,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // Crée 2 colonnes
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            items(items.size) { index ->
                MenuItemComposable(item = items[index], onClick = onClick) // Correction ici
            }
        }

    }
}


@Composable
fun MenuItemComposable(item: Dish, onClick: (Dish) -> Unit) {
    Column(modifier = Modifier
        .clickable { onClick(item) }
        .padding(8.dp)) {
        if (item.images.isNotEmpty()) {
            ImageFromUrls(urls = item.images)
        } else {
            Image(
                painter = painterResource(id = R.drawable.image), // Remplacez `default_image` par le nom de votre image par défaut
                contentDescription = "Image par défaut",
                modifier = Modifier.aspectRatio(1f).fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        }

        Text(text = item.nameFr ?: "Nom non disponible")
        // Affichez d'autres informations de l'item ici
    }
}

@Composable
fun ImageFromUrls(urls: List<String>) {
    var currentUrlIndex by remember { mutableIntStateOf(0) }
    val painter = rememberImagePainter(
        data = urls.getOrNull(currentUrlIndex),
        builder = {
            crossfade(true)
            error(android.R.drawable.ic_menu_close_clear_cancel) // Utilisez une icône d'erreur par défaut d'Android
            listener(
                onError = { _, throwable ->
                    Log.d("ImageFromUrls", "Erreur de chargement pour l'URL : ${urls.getOrNull(currentUrlIndex)} marche pas")
                    if (currentUrlIndex < urls.size - 1) {
                        // Essayez la prochaine URL si celle actuelle échoue
                        currentUrlIndex++
                    }
                }
            )
        }
    )

    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
            .aspectRatio(1f) // Assurez-vous que l'image est carrée
            .fillMaxWidth(), // Remplissez la largeur maximale disponible
        contentScale = ContentScale.Crop // Gère comment l'image doit être redimensionnée ou déplacée pour remplir les dimensions données.
    )
}
