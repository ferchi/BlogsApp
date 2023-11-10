package com.jfsb.blogsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.jfsb.blogsapp.core.navigation.NavGraph
import com.jfsb.blogsapp.features.dashboard.presentation.viewmodel.EntryViewModel
import com.jfsb.blogsapp.ui.theme.BlogsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlogsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val entryViewModel: EntryViewModel by viewModels()

                    NavGraph(
                        navHostController = navController,
                        entryViewModel = entryViewModel
                    )
                }
            }
        }
    }
}