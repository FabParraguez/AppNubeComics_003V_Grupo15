package com.vivitasol.carcasamvvm


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vivitasol.carcasamvvm.navigation.Route
import com.vivitasol.carcasamvvm.screens.PostScreen
import com.vivitasol.carcasamvvm.views.MenuShellView
import com.vivitasol.carcasamvvm.views.WelcomeView
import com.vivitasol.carcasamvvm.ui.theme.CarcasaMVVMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarcasaMVVMTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        // Iniciamos en MenuShell que tiene el menú lateral con todas las opciones
                        startDestination = Route.MenuShell.route
                    ) {
                        composable("posts") {
                            PostScreen()
                        }
                        // Mantenemos las rutas existentes como alternativas
                        composable(Route.Welcome.route) {
                            WelcomeView(
                                onStartClick = { navController.navigate(Route.MenuShell.route) }
                            )
                        }
                        // MenuShell incluye su propio NavHost interno para Option1/2/3 y Mangas
                        composable(Route.MenuShell.route) {
                            MenuShellView()
                        }
                }
            }
        }
    }
}
}