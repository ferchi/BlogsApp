package com.jfsb.blogsapp.features.dashboard.presentation.view

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jfsb.blogsapp.features.dashboard.data.datasource.remote.model.EntryModel
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun EntryCardView(
    modifier: Modifier = Modifier,
    entry: EntryModel,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(120.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { },
                    onTap = { onClick() }
                )
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.SpaceEvenly) {
            Text(
                text = entry.title!!,
                style = MaterialTheme.typography.titleMedium.copy(
                    textAlign = TextAlign.Center
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = entry.content!!.take(70).let {
                    if (entry.content!!.length > 70) "$it..." else it
                },
                style = MaterialTheme.typography.bodySmall,
                overflow = TextOverflow.Ellipsis,

                maxLines = 2,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Autor: ${entry.author!!}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Fecha: ${entry.date!!.formatDate()}",
                    style = MaterialTheme.typography.bodySmall
                )

            }
        }
    }
}

fun Date.formatDate(): String {
    val customFormat = SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
    return customFormat.format(this)
}

@Preview(name = "TicketCardView")
@Composable
private fun PreviewTicketCardView() {
    val entry = EntryModel(
        id = "BER-1",
        title = "Title",
        author = "Juan Fernando Salinas",
        content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam eget felis euismod, pretium diam vel, aliquam sapien.",
        date = Date(),
    )
    EntryCardView(
        entry = entry
    )
}