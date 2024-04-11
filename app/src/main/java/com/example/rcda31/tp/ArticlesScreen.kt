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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.rcda31.R
import com.example.rcda31.tp.ui.ArticleDetailView
import com.example.rcda31.tp.ui.ArticleListView
import com.example.rcda31.tp.ui.FormView

enum class ArticleScreen(val title: String, var id: String?) {
    START(title = "Vos articles", id = null),
    ADD_ARTICLE(title = "Ajouter votre article", id = null),
    DETAIL(title = "Votre article", id = null);

    fun setId(id: String): ArticleScreen {
        this.id = id
        return this
    }

    fun getRouteWithId(): String {
        if(this.id != null) (
            return "${this.name}/${this.id}"
        ) else {
            return this.name
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EniShopAppBar(
    currentScreen: ArticleScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(currentScreen.title) },
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

    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()

    val id = navController.currentBackStackEntry?.arguments?.getString("idArticle")

    // Get the name of the current screen
    val currentScreen = if (id != null) {
        ArticleScreen.DETAIL.setId(id)
    } else {
        ArticleScreen.valueOf(
            backStackEntry?.destination?.route ?: ArticleScreen.START.name
        )
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
            startDestination = ArticleScreen.START.getRouteWithId(),
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            composable(route = ArticleScreen.START.name) {
                ArticleListView(
                    onAddArticle = {
                        navController.navigate(ArticleScreen.ADD_ARTICLE.getRouteWithId())
                    },
                    onGoToDetailArticle = {
                        navController.navigate(ArticleScreen.DETAIL.setId(it.toString()).getRouteWithId())
                    }
                )
            }
            composable(route = ArticleScreen.ADD_ARTICLE.name) {
                FormView()
            }
            composable(route = "${ArticleScreen.DETAIL.name}/{idArticle}") { navBackStackEntry ->
                val idArticle = navBackStackEntry.arguments?.getString("idArticle")
                idArticle?.let { id ->
                    val articleId = id.toLong()
                    ArticleDetailView(idArticle = articleId)
                }
            }
        }
    }
}