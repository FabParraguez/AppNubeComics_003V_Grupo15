package com.vivitasol.carcasamvvm.views

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vivitasol.carcasamvvm.viewmodels.Option1ViewModel
import com.vivitasol.carcasamvvm.R

/**
 * DEMO COMPONENTES BÁSICOS
 *
 * Objetivo didáctico:
 * 1) Layout vertical con Column + espaciados (Spacer) y padding (Modifier).
 * 2) Texto con jerarquía tipográfica (MaterialTheme.typography).
 * 3) Fila horizontal con Row (alineación y distribución).
 * 4) Card que contiene imagen + texto (y cómo redondear esquinas).
 * 5) Image desde drawable. Alternativa con Icon de Material3.
 * 6) Button que modifica un estado local (contador).
 *
 * Notas MVVM:
 * - Tomamos un "título" desde un ViewModel (estado de pantalla).
 * - El contador es estado de UI efímero -> lo guardamos con rememberSaveable.
 */
@Composable
fun Option1View(
    vm: Option1ViewModel = viewModel(),
    // Cambia este recurso por el que tengas en drawable (ej: R.drawable.compose_logo)
    @DrawableRes imageRes: Int? = R.drawable.nubecomics_logotipo
) {
    val titulo = vm.texto.collectAsState().value

    // Ejemplo de estado local para el contador de clicks
    var contador by rememberSaveable { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),              // margin interno de la pantalla
        verticalArrangement = Arrangement.spacedBy(16.dp), // separaciones verticales homogéneas
        horizontalAlignment = Alignment.CenterHorizontally // centrado horizontal
    ) {

        // --- TEXT (título principal) ---
        Text(
            text = titulo,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            ),
            textAlign = TextAlign.Center
        )

        // Subtítulo explicativo
        Text(
            text = "Adminsitra tu negocio, autoriza publicacion de productos, revisa stock, y otras funcionalidades",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onBackground
            ),
            textAlign = TextAlign.Center
        )

        Divider(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))

        // --- ROW (fila horizontal) ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween, // distribuye a los lados
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Actualizaciones",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.secondary
                )
            )
            // Icon como alternativa rápida a Image si no tienes drawables
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Ícono informativo",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        // --- CARD (contenedor con elevación y estilo) ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Text(
                    text = "Administrador",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                )

                // --- IMAGE ---
                // Opción 1: cargar desde drawable si imageRes != null
                // Opción 2: si no tienes recurso aún, mostramos un Icon de placeholder
                if (imageRes != null) {
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = "Logo Servel",
                        contentScale = ContentScale.Crop,       // recorta para llenar
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .clip(RoundedCornerShape(12.dp))     // bordes redondos
                    )
                } else {
                    // Placeholder con Icon
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Placeholder",
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("Agrega una imagen a /res/drawable y pásala por imageRes")
                    }
                }

                Text(
                    text = "Bienvenido a NubeComics, comencemos.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )

                // --- BUTTON (interacción y estado) ---
                Button(
                    onClick = { contador++ },
                    modifier = Modifier.align(Alignment.End), // pega el botón a la derecha
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        "Clicks: $contador",
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // Texto final con una Row simple adicional
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Administración del sistema nubecomics sucursal moneda.",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.secondary
                )
            )
        }
    }
}

/* ------------------ PREVIEW ------------------ */
/**
 * Para previsualizar sin ViewModel, usamos una función privada
 * que inyecta un título "fake" y un ícono como placeholder de imagen.
 */
@Preview(showBackground = true, widthDp = 392)
@Composable
private fun Option1ViewPreview() {
    // Simulamos el contenido de Option1 sin acceder a VM,
    // reusando la misma UI principal con parámetros "falsos".
    DemoOption1Preview()
}

@Composable
private fun DemoOption1Preview() {
    // Reutilizamos la misma Column para el preview:
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Pantalla Opción 1 (en blanco)",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Preview de componentes básicos",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Divider()
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Fila con dos extremos", style = MaterialTheme.typography.titleMedium)
            Icon(Icons.Default.Info, contentDescription = null)
        }
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                Modifier.fillMaxWidth().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Card con Imagen", style = MaterialTheme.typography.titleMedium)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Info, contentDescription = null, modifier = Modifier.size(48.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Placeholder de imagen")
                }
                var contador by rememberSaveable { mutableStateOf(0) }
                Button(onClick = { contador++ }, modifier = Modifier.align(Alignment.End)) {
                    Text("Clicks: $contador")
                }
            }
        }
        Text("Tip: usa Spacer(height/width) para separar elementos.", style = MaterialTheme.typography.bodySmall)
    }
}
