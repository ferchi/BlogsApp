package com.jfsb.blogsapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jfsb.blogsapp.features.dashboard.presentation.screen.DashboardScreen
import com.jfsb.blogsapp.features.dashboard.presentation.screen.EntryDetailScreen
import com.jfsb.blogsapp.features.dashboard.presentation.screen.FormEntryScreen
import com.jfsb.blogsapp.features.dashboard.presentation.viewmodel.EntryViewModel

@Composable
fun NavGraph(
    navHostController: NavHostController,
    entryViewModel: EntryViewModel,
) {
    NavHost(
        navController = navHostController,
        startDestination = Routes.Dashboard.route
    ) {
        composable(Routes.Dashboard.route) {
            DashboardScreen(
                entryViewModel = entryViewModel,
                navController = navHostController,
            )
        }
        composable(Routes.Details.route) {
            EntryDetailScreen(
                entryViewModel = entryViewModel,
                navController = navHostController,
            )
        }

        composable(Routes.Form.route) {
            FormEntryScreen(
                entryViewModel = entryViewModel,
                navController = navHostController,
            )
        }
    }
}