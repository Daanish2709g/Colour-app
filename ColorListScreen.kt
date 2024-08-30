package com.example.colorapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.colorapp.data.ColorEntity

@Composable
fun ColorListScreen(
    viewModel: ColorViewModel = viewModel()
) {
    val colorList by viewModel.allColors.observeAsState(emptyList())
    val pendingSyncCount by viewModel.pendingSyncCount.observeAsState(0)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ColorApp") },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                val rows = colorList.chunked(2) // Split the list into rows of 2 items each

                rows.forEach { rowColors ->
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        items(rowColors) { color ->
                            ColorBox(color)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp)) // Space between rows
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = { viewModel.addColor() }) {
                        Text(text = "Add Color")
                    }
                    Button(onClick = { viewModel.syncColors() }) {
                        Text(text = "Sync ($pendingSyncCount)")
                    }
                }
            }
        }
    )
}

@Composable
fun ColorBox(color: ColorEntity) {
    Box(
        modifier = Modifier
            .size(width = 18.dp, height = 24.dp)
            .background(Color(android.graphics.Color.parseColor(color.color)))
            .padding(top = (-355).dp, start = 115.dp)
    ) {
        Text(
            text = color.color,
            fontFamily = FontFamily.SansSerif,
            fontSize = 20.sp,
            fontWeight = FontWeight.W400,
            lineHeight = 23.98.sp,
            color = Color.White,
            modifier = Modifier
                .padding(4.dp)
                .align(alignment = Alignment.CenterStart)
        )
    }
}
