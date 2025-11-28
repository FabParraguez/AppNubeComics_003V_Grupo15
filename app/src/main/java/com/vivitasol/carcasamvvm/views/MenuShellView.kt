package com.vivitasol.carcasamvvm.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import kotlinx.coroutines.launch
import com.vivitasol.carcasamvvm.navigation.Route
import com.vivitasol.carcasamvvm.screens.MangaScreen
import com.vivitasol.carcasamvvm.screens.PostScreen
import com.vivitasol.carcasamvvm.ui.theme.NubeGradient1
import com.vivitasol.carcasamvvm.ui.theme.NubeGradient2
import com.vivitasol.carcasamvvm.ui.theme.NubeBlue40
import com.vivitasol.carcasamvvm.ui.theme.NubeSecondary40
import com.vivitasol.carcasamvvm.ui.theme.NubeTertiary40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuShellView() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val innerNavController = rememberNavController()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.background(
                    Brush.verticalGradient(
                        colors = listOf(
                            NubeSecondary40,
                            NubeTertiary40
                        )
                    )
                ),
                drawerShape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
            ) {
                Text(
                    text = "Nube Comics Moneda",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    ),
                    modifier = Modifier.padding(16.dp)
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                NavigationDrawerItem(
                    label = { 
                        Text(
                            "Inicio",
                            color = Color.DarkGray,
                            fontWeight = FontWeight.Medium
                        ) 
                    },
                    selected = currentInnerRoute(innerNavController) == Route.Option1.route,
                    onClick = {
                        innerNavController.navigate(Route.Option1.route) {
                            popUpTo(Route.Option1.route) { inclusive = false }
                            launchSingleTop = true
                        }
                        scope.launch { drawerState.close() }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = Color.White.copy(alpha = 0.2f),
                        unselectedTextColor = Color.White,
                        selectedTextColor = Color.White
                    )
                )
                NavigationDrawerItem(
                    label = { 
                        Text(
                            "Inventario",
                            color = Color.DarkGray,
                            fontWeight = FontWeight.Medium
                        ) 
                    },
                    selected = currentInnerRoute(innerNavController) == Route.Option2.route,
                    onClick = {
                        innerNavController.navigate(Route.Option2.route) {
                            popUpTo(Route.Option1.route) { inclusive = false }
                            launchSingleTop = true
                        }
                        scope.launch { drawerState.close() }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = Color.White.copy(alpha = 0.2f),
                        unselectedTextColor = Color.White,
                        selectedTextColor = Color.White
                    )
                )
                NavigationDrawerItem(
                    label = { 
                        Text(
                            "Publicar Productos Nuevos",
                            color = Color.DarkGray,
                            fontWeight = FontWeight.Medium
                        ) 
                    },
                    selected = currentInnerRoute(innerNavController) == Route.Option3.route,
                    onClick = {
                        innerNavController.navigate(Route.Option3.route) {
                            popUpTo(Route.Option1.route) { inclusive = false }
                            launchSingleTop = true
                        }
                        scope.launch { drawerState.close() }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = Color.White.copy(alpha = 0.2f),
                        unselectedTextColor = Color.White,
                        selectedTextColor = Color.White
                    )
                )

                NavigationDrawerItem(
                    label = { 
                        Text(
                            "Verificador de precio",
                            color = Color.DarkGray,
                            fontWeight = FontWeight.Medium
                        ) 
                    },
                    selected = currentInnerRoute(innerNavController) == Route.Option5.route,
                    onClick = {
                        innerNavController.navigate(Route.Option5.route) {
                            popUpTo(Route.Option1.route) { inclusive = false }
                            launchSingleTop = true
                        }
                        scope.launch { drawerState.close() }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = Color.White.copy(alpha = 0.2f),
                        unselectedTextColor = Color.White,
                        selectedTextColor = Color.White
                    )
                )
                
                NavigationDrawerItem(
                    label = { 
                        Text(
                            "Posts API Externa",
                            color = Color.DarkGray,
                            fontWeight = FontWeight.Medium
                        ) 
                    },
                    selected = currentInnerRoute(innerNavController) == Route.PostsAPIExterna.route,
                    onClick = {
                        innerNavController.navigate(Route.PostsAPIExterna.route) {
                            popUpTo(Route.Option1.route) { inclusive = false }
                            launchSingleTop = true
                        }
                        scope.launch { drawerState.close() }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = Color.White.copy(alpha = 0.2f),
                        unselectedTextColor = Color.White,
                        selectedTextColor = Color.White
                    )
                )
                
                NavigationDrawerItem(
                    label = { 
                        Text(
                            "Gestión de Mangas (CRUD)",
                            color = Color.DarkGray,
                            fontWeight = FontWeight.Medium
                        ) 
                    },
                    selected = currentInnerRoute(innerNavController) == Route.MangaManagement.route,
                    onClick = {
                        innerNavController.navigate(Route.MangaManagement.route) {
                            popUpTo(Route.Option1.route) { inclusive = false }
                            launchSingleTop = true
                        }
                        scope.launch { drawerState.close() }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = Color.White.copy(alpha = 0.2f),
                        unselectedTextColor = Color.White,
                        selectedTextColor = Color.White
                    )
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { 
                        Text(
                            "Nube Comics",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        ) 
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) drawerState.open() else drawerState.close()
                            }
                        }) {
                            Icon(
                                Icons.Default.Menu, 
                                contentDescription = "Menú",
                                tint = Color.DarkGray
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = NubeBlue40 // Azul principal de NubeComics
                    )
                )
            }
        ) { innerPadding ->
            // NavHost interno para las opciones del menú
            NavHost(
                navController = innerNavController,
                startDestination = Route.Option1.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Route.Option1.route) { Option1View() }
                composable(Route.Option2.route) { Option2View(navController = innerNavController) } // <--recibe nav
                composable(Route.Option3.route) { Option3View() }
                //pantalla de detalle para la clase 2(con nav)
                composable(Route.Option2Detail.route) { backStack ->
                    val id = backStack.arguments?.getString("id") ?: "sin-id"
                    Option2DetailView(
                        id = id,
                        onBack = { innerNavController.navigateUp() }
                    )
                }
                composable(Route.Option5.route) { Option5CameraView() }
                composable(Route.PostsAPIExterna.route) { PostScreen() }
                composable(Route.MangaManagement.route) { MangaScreen() }
            }
        }
    }
}

@Composable
private fun currentInnerRoute(navController: NavHostController): String? {
    val entry by navController.currentBackStackEntryAsState()
    return entry?.destination?.route
}
