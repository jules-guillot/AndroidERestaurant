package fr.isen.guillot.androidrestaurant.ui.theme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class CartActivity : ComponentActivity() {
    object CartManager {
        private val cartItems = mutableMapOf<String, Int>()

        fun addToCart(itemName: String, quantity: Int) {
            cartItems[itemName] = cartItems.getOrDefault(itemName, 0) + quantity
        }

        fun removeFromCart(itemName: String) {
            cartItems.remove(itemName)
        }

        fun getCartItems() = cartItems
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CartView()
        }
    }

    @Composable
    fun CartView() {
        val cartItems = CartManager.getCartItems()

        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Votre Panier", modifier = Modifier.padding(bottom = 16.dp))
            LazyColumn {
                items(cartItems.entries.toList()) { entry ->
                    CartItemView(entry.key, entry.value)
                }
            }
            Button(onClick = {
                // Logique pour passer à la caisse
            }) {
                Text("Passer à la caisse")
            }
        }
    }

    @Composable
    fun CartItemView(itemName: String, quantity: Int) {
        Row(modifier = Modifier.fillMaxWidth().padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("$itemName x $quantity", modifier = Modifier.weight(1f))
            Button(onClick = { CartManager.removeFromCart(itemName) }) {
                Text("Retirer")
            }
        }
    }
}