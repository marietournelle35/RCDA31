package com.example.rcda31.tp

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.rcda31.R
import com.example.rcda31.tp.ui.ArticleDetailView
import com.example.rcda31.tp.ui.ArticleListView
import com.example.rcda31.tp.ui.FormView

enum class ArticleScreen(val route: String, val title: String) {
    START("start", "Vos articles"),
    ADD_ARTICLE("addArticle", "Ajouter votre article"),
    DETAIL("detail/{idArticle}", "Votre article");

    fun withArgs(vararg args: String): String {
        return this.route.replace("{idArticle}", args.first())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EniShopAppBar(
    currentScreen: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(currentScreen) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun EniShopApp(
    navController: NavHostController = rememberNavController()
) {

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val currentScreen = when {
        currentRoute?.startsWith(ArticleScreen.DETAIL.route.split("/{")[0]) == true -> {
            ArticleScreen.DETAIL.title
        }
        else -> ArticleScreen.entries.find { it.route == currentRoute }?.title ?: ArticleScreen.START.title
    }


    Scaffold(
        topBar = {
            EniShopAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ArticleScreen.START.name,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            composable(route = ArticleScreen.START.route) {
                ArticleListView(
                    onAddArticle = {
                        navController.navigate(ArticleScreen.ADD_ARTICLE.route)
                    },
                    onGoToDetailArticle = {
                        navController.navigate(ArticleScreen.DETAIL.withArgs(it.toString()))
                    }
                )
            }
            composable(route = ArticleScreen.ADD_ARTICLE.route) {
                FormView(
                    onNavigateToHome = {
                        navController.navigateUp()
                    }
                )
            }
            composable(
                route = ArticleScreen.DETAIL.route,
                arguments = listOf(navArgument("idArticle") { type = NavType.StringType })
            ) { navBackStackEntry ->
                val idArticle = navBackStackEntry.arguments?.getString("idArticle")
                idArticle?.let { id ->
                    val articleId = id.toLong()
                    ArticleDetailView(idArticle = articleId)
                }
            }
        }
    }
}