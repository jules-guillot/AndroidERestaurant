package fr.isen.guillot.androidrestaurant
import android.content.Intent
import fr.isen.guillot.androidrestaurant.ui.theme.AndroidERestaurantTheme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidERestaurantTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text(
                            text = "Bienvenue chez DroidRestaurant !",
                            modifier = Modifier.padding(bottom = 46.dp),
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                fontSize = 46.sp,
                                fontFamily = FontFamily.SansSerif // Changer la police ici
                            )
                        )

                        val textStyle = TextStyle(
                            fontSize = 32.sp,
                            fontFamily = FontFamily.SansSerif // Changer la police ici
                        )

                        Column(modifier = Modifier.fillMaxSize()) {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = "Entrées",
                                    style = textStyle,
                                    modifier = Modifier.weight(1f)
                                )
                                ElevatedButton(
                                    onClick = {
                                        val navigate = Intent(this@MainActivity, Entrees::class.java)
                                        startActivity(navigate)
                                    }
                                ) {
                                    Text("En savoir plus")
                                }
                            }
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = "Plats",
                                    style = textStyle,
                                    modifier = Modifier.weight(1f)
                                )
                                ElevatedButton(
                                    onClick = {
                                        val navigate = Intent(this@MainActivity, Plats::class.java)
                                        startActivity(navigate)
                                    }
                                ) {
                                    Text("En savoir plus")
                                }
                            }
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = "Desserts",
                                    style = textStyle,
                                    modifier = Modifier.weight(1f)
                                )
                                ElevatedButton(
                                    onClick = {
                                        val navigate = Intent(this@MainActivity, Desserts::class.java)
                                        startActivity(navigate)
                                    }
                                ) {
                                    Text("En savoir plus")
                                }
                            }
                        }
                    }
                    Image(
                        painter = painterResource(id = R.drawable.image),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(100.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}
