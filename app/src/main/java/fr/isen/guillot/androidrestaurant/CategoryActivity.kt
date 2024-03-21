package fr.isen.guillot.androidrestaurant

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.guillot.androidrestaurant.ui.theme.AndroidERestaurantTheme
import fr.isen.guillot.androidrestaurant.ui.theme.DetailActivity

class CategoryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val categoryName = intent.getStringExtra("categoryName") ?: "Erreur"
        setContent {
            AndroidERestaurantTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    Column(modifier = Modifier.padding(30.dp)) {
                        Text(
                            text = categoryName,
                            modifier = Modifier.padding(bottom = 46.dp),
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                fontSize = 46.sp,
                                fontFamily = FontFamily.SansSerif
                            )
                        )
                        val menuItemsForCategory = getMenuItemsForCategory(categoryName)

                        LazyColumn(
                            contentPadding = PaddingValues(
                                horizontal = 16.dp,
                                vertical = 8.dp
                            )
                        ) {
                            items(items = menuItemsForCategory, itemContent = { menuItem ->
                                val context = LocalContext.current
                                MenuItemView(menuItem) {
                                    val intent = Intent(context, DetailActivity::class.java).apply {
                                        putExtra("itemName", menuItem.title)
                                        // Vous pouvez aussi passer d'autres informations ici, comme l'ID de l'élément
                                    }
                                    context.startActivity(intent)
                                }
                            })
                        }
                    }
                }
            }
        }
    }

    private fun getMenuItemsForCategory(categoryName: String): List<MenuItem> {
        val resources = resources
        return listOf(
            MenuItem(1, resources.getString(R.string.entree1), "Entrées"),
            MenuItem(2, resources.getString(R.string.entree2), "Entrées"),
            MenuItem(3, resources.getString(R.string.plat1), "Plats"),
            MenuItem(4, resources.getString(R.string.plat2), "Plats"),
            MenuItem(5, resources.getString(R.string.dessert1), "Desserts"),
            MenuItem(6, resources.getString(R.string.dessert2), "Desserts")
        ).filter { it.category == categoryName }
    }
}

data class MenuItem(val id: Int, val title: String, val category: String)

@Composable
fun MenuItemView(menuItem: MenuItem, onClick: () -> Unit) {
    Text(text = menuItem.title,
        style = TextStyle(fontSize = 32.sp, fontFamily = FontFamily.SansSerif),
        modifier = Modifier.clickable(onClick = onClick)
    )
}

