package fr.isen.guillot.androidrestaurant
import android.content.Intent
import fr.isen.guillot.androidrestaurant.ui.theme.AndroidERestaurantTheme

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Composable

class HomeActivity : ComponentActivity() {
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
                                fontFamily = FontFamily.SansSerif
                            )
                        )

                        Column(modifier = Modifier.fillMaxSize()) {

                            Menucategory(name = "Entrées")
                            Menucategory(name = "Plats")
                            Menucategory(name = "Desserts")
                            Image(
                                painter = painterResource(id = R.drawable.image),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Menucategory(name : String ) {
    val context = LocalContext.current
    val textStyle = TextStyle(
        fontSize = 32.sp,
        fontFamily = FontFamily.SansSerif
    )
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = name,
            style = textStyle,
            modifier = Modifier.weight(1f)
        )
        ElevatedButton(
            onClick = {
                Toast.makeText(context, "C'est parti !", Toast.LENGTH_SHORT).show()
                val intent =
                    Intent(context, CategoryActivity::class.java).apply {
                        putExtra("categoryName", name)
                    }
                context.startActivity(intent)
            }
        ) {
            Text("En savoir plus")
        }
    }
}
