package com.vivitasol.carcasamvvm.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.vivitasol.carcasamvvm.navigation.Route
import com.vivitasol.carcasamvvm.viewmodels.Option2ViewModel
import com.vivitasol.carcasamvvm.R
import kotlinx.coroutines.launch

/**
 * CLASE 2: Flujo y Navegación
 * - Lista con navegación a Detalle con argumento (id).
 * - Uso de NavController.navigate con launchSingleTop y popUpTo.
 * - Snackbar para feedback al tocar un ítem.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Option2View(
    navController: NavController,
    vm: Option2ViewModel = viewModel()
) {
    val items = vm.items.collectAsState().value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                "Mangas",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "Revisa el estado del producto y su información.",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(16.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                itemsIndexed(items) { index, item ->
                    ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Abriendo detalle de $item")
                                }
                                navController.navigate(Route.Option2Detail.build(id = (index + 1).toString())) {
                                    launchSingleTop = true
                                    // Ejemplo de control de back stack:
                                    popUpTo(Route.Option2.route) { inclusive = false }
                                }
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(item, style = MaterialTheme.typography.titleMedium)
                            Spacer(Modifier.weight(1f))
                            Text("Ver detalle", style = MaterialTheme.typography.labelLarge)
                        }
                    }
                }
            }
        }
    }
}

/**
 * Pantalla de Detalle para la navegación con argumento
 */
@Composable
fun Option2DetailView(
    id: String,
    onBack: () -> Unit
) {
    // Datos de los mangas con sus imágenes y información específica
    val mangaData = listOf(
        MangaInfo(
            "Chainsaw Man", 
            R.drawable.chainsaw_man_01, 
            "Denji es un joven cazador de demonios que se fusiona con su demonio motosierra para convertirse en Chainsaw Man. Una historia sangrienta y salvaje.", 
            "$10.990"
        ),
        MangaInfo(
            "Heaven Officials Blessing", 
            R.drawable.heaven_officials_blessing_01, 
            "Xie Lian asciende al cielo por tercera vez como dios oficial. Una épica novela de fantasía china con romance y aventura.", 
            "$25.990"
        ),
        MangaInfo(
            "JoJo's Bizarre Adventure: Phantom Blood", 
            R.drawable.jojos_bizarre_adventure_phantom_blood_esp_01, 
            "Jonathan Joestar se enfrenta a su hermano adoptivo Dio Brando en esta primera parte de la legendaria saga JoJo.", 
            "$15.990"
        ),
        MangaInfo(
            "Kaguya-sama: Love is War", 
            R.drawable.kaguya01, 
            "Dos genios del consejo estudiantil libran una guerra psicológica para hacer que el otro confiese sus sentimientos primero.", 
            "$10.990"
        ),
        MangaInfo(
            "Made in Abyss", 
            R.drawable.made_in_abyss_01, 
            "Riko desciende al misterioso Abismo para encontrar a su madre, acompañada por el robot Reg. Una aventura oscura y hermosa.", 
            "$12.990"
        ),
        MangaInfo(
            "Tokyo Revengers", 
            R.drawable.tokyo_revengers01, 
            "Takemichi viaja en el tiempo para salvar a su ex novia y cambiar el destino de la pandilla Tokyo Manji.", 
            "$10.990"
        ),
        MangaInfo(
            "Yona of the Dawn", 
            R.drawable.yona_01, 
            "La princesa Yona debe huir de su reino tras un golpe de estado y reunir a los legendarios Dragones para reclamar su trono.", 
            "$10.990"
        )
    )
    
    val mangaIndex = id.toIntOrNull()?.minus(1) ?: 0
    val manga = if (mangaIndex in mangaData.indices) mangaData[mangaIndex] else mangaData[0]
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Detalle del Producto", 
            style = MaterialTheme.typography.headlineSmall, 
            fontWeight = FontWeight.Bold,
            color = Color(0xFF42B9E4) // Color azul de NubeComics
        )
        
        // Card contenedor para la imagen y información
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Imagen específica del manga
                Image(
                    painter = painterResource(id = manga.imageRes),
                    contentDescription = "Imagen de ${manga.name}",
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                
                // Información del producto
                Text(
                    text = manga.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2186AC) // Color secundario de NubeComics
                )
                
                Text(
                    text = "ID del producto: $id",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                // Descripción específica del manga
                Text(
                    text = manga.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Text(
                    text = "Precio: ${manga.price}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF42B9E4)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Botón Volver con estilo NubeComics
        Button(
            onClick = onBack,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF42B9E4)
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) { 
            Text(
                "Volver",
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

// Clase de datos para la información del manga
data class MangaInfo(
    val name: String,
    val imageRes: Int,
    val description: String,
    val price: String
)

