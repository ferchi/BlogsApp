package com.jfsb.blogsapp.features.dashboard.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.jfsb.blogsapp.core.navigation.Routes
import com.jfsb.blogsapp.core.network.models.DefaultResult
import com.jfsb.blogsapp.features.dashboard.data.datasource.remote.model.EntryModel
import com.jfsb.blogsapp.features.dashboard.presentation.view.EntryCardView
import com.jfsb.blogsapp.features.dashboard.presentation.viewmodel.EntryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    entryViewModel: EntryViewModel,
    navController: NavHostController,
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Blog App",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Color.Black,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    IconButton(onClick = {
                        entryViewModel.clearTicketData()
                        navController.navigate(Routes.Form.route)
                    }) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Icono de nuevo",
                            tint = Color.Black
                        )
                    }
                }
            )
        },
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .verticalScroll(scrollState)
        ) {
            entryViewModel.getAllEntries()
            val configuration = LocalConfiguration.current
            val screenHeight = configuration.screenHeightDp.dp

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(horizontal = 16.dp),
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                when (val state = entryViewModel.entriesListState.value) {
                    is DefaultResult.Loading -> {
                        Box(modifier = Modifier.fillMaxSize()) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }
                    is DefaultResult.Success<*> -> {
                        LazyColumn(Modifier.height(screenHeight)) {
                            items((state.data as List<*>).size) { index ->
                                EntryCardView(
                                    modifier = Modifier.padding(
                                        horizontal = 16.dp,
                                        vertical = 4.dp
                                    ),
                                    entry = (state.data as List<EntryModel>)[index],
                                    onClick = {
                                        entryViewModel.setCurrentEntry((state.data)[index])
                                        navController.navigate(Routes.Details.route)
                                    },
                                )
                            }
                        }
                    }
                    is DefaultResult.Error -> {
                        Text(text = "Error: ${state.message}")
                    }
                }
            }
        }
    }
}
