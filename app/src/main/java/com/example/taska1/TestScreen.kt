package com.example.taska1

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

data class Items(val name: String) {}

@Composable
internal fun SubItems(mutableList: MutableList<Items>, onRemove: (Items) -> Unit) {

    val lazyGridHeight = with(LocalDensity.current) {
        val itemHeight = 24.dp.toPx()
        val verticalPadding = 16.dp.toPx()
        val itemCount = mutableList.size
        val contentHeight = itemCount * itemHeight + verticalPadding * 2
        contentHeight.toDp()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .height(lazyGridHeight)
        ) {
            items(mutableList) { test ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = test.name,
                        modifier = Modifier
                            .padding(8.dp)
                    )
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = "Delete",
                        modifier = Modifier.clickable {
                            onRemove(test)
                        })
                }
            }
        }
    }
}