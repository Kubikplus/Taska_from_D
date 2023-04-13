package com.example.taska1
//
import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.dp


data class Item(val name: String, val amount: String)

@Composable
fun ItemsScreen() {

    val items = listOf(
        Item("aaa-10", "10"),
        Item("aaa-10", "10"),
        Item("aaa-10", "10"),
        Item("aaa-10", "10"),
        Item("aaa-10", "10"),
        Item("aaa-10", "10"),
        Item("aaa-10", "10"),
        Item("aaa-10", "10"),
        Item("aaa-10", "10"),
        Item("aaa-10", "10"),
        Item("aaa-10", "10"),


        )

    val scrollState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(modifier = Modifier.weight(1f)) {
            LazyColumn(
                state = scrollState,
                modifier = Modifier.fillMaxSize()
            ) {
                items(items) { item ->
                    SelectableItems(item)
                }
            }
        }
        ButtonsNext()
    }
}

@Composable
fun ButtonsNext() {
    Box(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            Button(onClick = { /*TODO*/ }, modifier = Modifier.width(200.dp)) {
                Text(text = "Submit")

            }
            Button(onClick = { /*TODO*/ }, modifier = Modifier.width(200.dp)) {
                Text(text = "Cancel")
            }

        }
    }
}


@Composable
fun SelectableItems(items: Item) {
    var isValid by remember {
        mutableStateOf(false)
    }
    var text by remember {
        mutableStateOf("0")
    }
    Row(
        modifier = Modifier
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Checkbox(
            checked = true,
            onCheckedChange = {},
            colors = CheckboxDefaults.colors(checkedColor = Color.Blue)
        )
        Text(text = items.name)
        Spacer(modifier = Modifier.weight(1f))
        TextField(
            value = text,
            onValueChange = {
                text = it
                if(!isValidInput(amount = items.amount, input = it )){
                    Log.d("Error","Invalid input")
                    isValid = false
                }else{
                    Log.d("Error","Valid input")
                }
            },
            modifier = Modifier
                .width(90.dp)
                .height(50.dp)
                .shake(true)
                .padding(end = 10.dp)
                .shadow(30.dp, shape = RoundedCornerShape(10.dp)),
            maxLines = 1,
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Gray,
                disabledTextColor = Color.Transparent,
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(30.dp)
        )
        Text(text = "/${items.amount}")


    }

}

fun isValidInput(amount: String, input: String): Boolean {
    if (amount.toInt() < input.toInt()) {
        return false
    }
    return true
}

fun Modifier.shake(enabled: Boolean) = composed(

    factory = {

        val scale by animateFloatAsState(
            targetValue = if (enabled) .9f else 1f,
            animationSpec = repeatable(
                iterations = 5,
                animation = tween(durationMillis = 50, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            )
        )

        Modifier.graphicsLayer {
            scaleX = if (enabled) scale else 1f
            scaleY = if (enabled) scale else 1f
        }
    },
    inspectorInfo = debugInspectorInfo {
        name = "shake"
        properties["enabled"] = enabled
    }
)