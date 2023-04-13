package com.example.taska1
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun VerticalListWithGrid() {
    val scrollState = rememberLazyListState()
    val items = remember {
        mutableStateListOf(
            ListItem("Item 1"),
        )
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(1f)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                state = scrollState
            ) {
                items(items) { item ->
                    ListItem(item, onRemove = { items.remove(item) })
                    Divider()
                }
            }
        }
        Box(modifier = Modifier.fillMaxWidth()) {
            TextButton(
                onClick = { items += ListItem("item ${items.size + 1}") },
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(text = "Add one more pack")
            }
        }
        BottomButtons()


    }

}

@Composable
fun ListItem(
    item: ListItem,
    onRemove: () -> Unit,
) {
    val items = remember {
        mutableStateListOf(
            Items("aaaa-10"),
            Items("aaa-10"),
            Items("aaaa-10"),
            Items("aaaa-10"),
        )
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Button(
            onClick = { items.add(Items("new item")) }, modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(horizontal = 10.dp)
                .clip(RoundedCornerShape(20.dp))
        ) {
            Text(text = "Add Items")
        }
        SubItems(items){
            items.remove(it)
        }
        SelectableItems()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 20.dp)
                .align(Alignment.End)
        ) {
            TextButton(
                onClick = { onRemove() },
                modifier = Modifier
                    .padding(end = 20.dp)
                    .align(Alignment.BottomEnd)
            ) {
                Text(text = "Remove", color = Color.Red)
            }
        }
    }
}

@Composable
fun SelectableItems(
) {
    val items = listOf("Small", "Medium", "Large")
    var selectedItem by remember { mutableStateOf(items.first()) }


    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Type of package", modifier = Modifier.padding(bottom = 10.dp))
                DropDownMenuWithSelected(
                    items = items,
                    selectedItem = selectedItem,
                    onItemSelected = { item ->
                        selectedItem = item
                    })
            }

            Spacer(modifier = Modifier.weight(1f))
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Weight", modifier = Modifier.padding(bottom = 10.dp))
                WeightTextField()
            }

        }

    }
}

@Composable
fun DropDownMenuWithSelected(
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = Modifier.wrapContentSize()) {
        DropdownItem(text = selectedItem, selected = expanded, onClick = { expanded = true })
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    }
                ) {
                    Text(text = item)
                }
            }
        }
    }
}

@Composable
fun DropdownItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(30.dp),
        color = Color.White,
        elevation = 4.dp,
        modifier = Modifier.width(110.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = text,
                    color = if (selected) Color.Blue else Color.Black,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(2.dp)
                        .padding(15.dp)
                )
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Drop Down")
            }
        }
    }
}

@Composable
fun WeightTextField() {
    var input by remember {
        mutableStateOf("")
    }
    Surface(
        shape = RoundedCornerShape(30.dp),
        color = Color.White,
        elevation = 4.dp,
        modifier = Modifier.width(110.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            TextField(
                value = input,
                modifier = Modifier
                    .width(100.dp)
                    .height(50.dp),
                textStyle = TextStyle(fontSize = 15.sp),
                singleLine = true,
                maxLines = 1,
                onValueChange = {
                    input = it
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    backgroundColor = Color.White
                )
            )
        }
    }
}

@Composable
fun BottomButtons(enabled: Boolean = false) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            modifier = Modifier.width(200.dp),
            onClick = { },
            shape = RoundedCornerShape(20.dp),
            enabled = enabled
        ) {
            Text(text = "Submit")
        }
        Button(
            modifier = Modifier.width(200.dp),
            onClick = { },
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(text = "Cancel")
        }
    }
}

data class ListItem(val text: String, val subItems: MutableList<SubItem> = mutableListOf())

data class SubItem(val name: String)

