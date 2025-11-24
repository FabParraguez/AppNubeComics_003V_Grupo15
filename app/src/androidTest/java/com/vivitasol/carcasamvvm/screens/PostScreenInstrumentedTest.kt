package com.vivitasol.carcasamvvm.screens

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertIsDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PostScreenInstrumentedTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun postList_displaysPostTitle() {
        val sample = listOf(
            com.vivitasol.carcasamvvm.model.Post(userId = 1, id = 1, title = "Hola Mundo", body = "Este es un post de ejemplo")
        )

        composeTestRule.setContent {
            PostScreenContent(posts = sample, isLoading = false, error = null, onRefresh = {})
        }

        composeTestRule.onNodeWithText("Hola Mundo").assertIsDisplayed()
    }

    @Test
    fun loading_showsLoadingText() {
        composeTestRule.setContent {
            PostScreenContent(posts = emptyList(), isLoading = true, error = null, onRefresh = {})
        }

        composeTestRule.onNodeWithText("Cargando...").assertIsDisplayed()
    }
}
