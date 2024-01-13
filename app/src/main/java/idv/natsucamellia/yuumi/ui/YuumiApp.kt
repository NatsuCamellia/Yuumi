package idv.natsucamellia.yuumi.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import idv.natsucamellia.yuumi.ui.screens.HomeScreen
import idv.natsucamellia.yuumi.ui.screens.ProfileScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YuumiApp(
    modifier: Modifier = Modifier,
    viewModel: SummonerViewModel = viewModel(factory = SummonerViewModel.Factory),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = YuumiScreen.valueOf(
        backStackEntry?.destination?.route ?: YuumiScreen.Home.name
    )
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = {
            YuumiTopAppBar(
                scrollBehavior = scrollBehavior,
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = YuumiScreen.Home.name,
            modifier = modifier
                .padding(it)
                .fillMaxSize()
        ) {
            composable(route = YuumiScreen.Home.name) {
                HomeScreen(
                    onSearch = { summonerName ->
                        navController.navigate(YuumiScreen.Profile.name)
                        viewModel.searchBySummonerName(summonerName)
                    }
                )
            }
            composable(route = YuumiScreen.Profile.name) {
                ProfileScreen(
                    summonerUiState = viewModel.summonerUiState
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YuumiTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    currentScreen: YuumiScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = currentScreen.title,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        modifier = modifier
    )
}

enum class YuumiScreen(val title: String) {
    Home(title = "Yuumi"),
    Profile(title = "Profile")
}