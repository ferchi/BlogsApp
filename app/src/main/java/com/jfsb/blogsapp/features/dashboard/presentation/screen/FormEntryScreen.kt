package com.jfsb.blogsapp.features.dashboard.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.jfsb.blogsapp.features.dashboard.data.datasource.remote.model.EntryModel
import com.jfsb.blogsapp.features.dashboard.presentation.viewmodel.EntryViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormEntryScreen(
    entryViewModel: EntryViewModel,
    navController: NavHostController,
) {
    val title: String by entryViewModel.title.observeAsState("")
    val author: String by entryViewModel.author.observeAsState("")
    val content: String by entryViewModel.content.observeAsState("")

    val context = LocalContext.current

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(
                    text = "Nueva entrada",
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
                ) },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Icono regresar",
                            tint = Color.Black
                        )
                    }
                }
            )
        }
    ){
        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { entryViewModel.setTitle(it) },
                label = { Text("Título*") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = author,
                onValueChange = { entryViewModel.setAuthor(it) },
                label = { Text("Autor*") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = content,
                onValueChange = { entryViewModel.setContent(it) },
                label = { Text("Contenido*") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 10,
                singleLine = false,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (entryViewModel.isValidateForm()) {
                        entryViewModel.createEntry(
                            EntryModel(
                                author = author,
                                date = Date(),
                                content = content,
                                title = title
                            )
                        )

                    } else {
                        Toast.makeText(
                            context,
                            "Faltan campos por rellenar",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text(text = "Crear entrada", color = Color.White)
            }
        }
    }
}
