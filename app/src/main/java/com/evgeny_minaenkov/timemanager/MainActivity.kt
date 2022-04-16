package com.evgeny_minaenkov.timemanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.evgeny_minaenkov.timemanager.ui.theme.TimeManagerTheme

val brownGreyColor = Color(0xFF959595)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimeManagerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ProductScreen()
                }
            }
        }
    }
}

@Composable
fun ProductScreen(productViewModel: ProductViewModel = viewModel()) {
    val sku by productViewModel.sku.observeAsState()
    val title by productViewModel.title.observeAsState()
    LazyColumn(content = {
        item { Toolbar() }
        item { ImageHeader() }
        item {
            Text(
                text = sku.orEmpty(),
                modifier = Modifier.padding(top = 24.dp, start = 16.dp),
                style = TextStyle(
                    color = brownGreyColor,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp
                )
            )
        }
        item {
            Text(
                text = title.orEmpty(),
                modifier = Modifier.padding(top = 4.dp, start = 16.dp, end = 24.dp),
                style = TextStyle(
                    color = Black,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp
                )
            )
        }
        item { RatingRowView() }
        item { PriceView(productViewModel = productViewModel) }
    }, modifier = Modifier.fillMaxSize())
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TimeManagerTheme {
        ProductScreen()
    }
}

@Composable
fun PriceView(productViewModel: ProductViewModel) {
    Row(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "13 686,00",
            modifier = Modifier.padding(start = 16.dp),
            style = TextStyle(color = Black, fontSize = 20.sp, fontWeight = FontWeight.Medium)
        )
        Text(
            text = "₽/шт.",
            modifier = Modifier
                .weight(1f)
                .padding(4.dp),
            style = TextStyle(color = brownGreyColor, fontSize = 12.sp)
        )
        CardButton(productViewModel)
    }
}

@Composable
fun CardButton(productViewModel: ProductViewModel) {
    val itemsInCart by productViewModel.itemsInCart.observeAsState()
    Button(
        onClick = {
            productViewModel.addToCart()
        },
        modifier = Modifier
            .height(48.dp)
            .width(160.dp)
            .padding(end = 24.dp)
    ) {
        if (itemsInCart == 0) {
            Text(
                text = "В корзину",
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
            )
        } else {
            Text(
                text = "$itemsInCart",
                style = TextStyle(
                    color = Color.Green,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
            )
        }
    }
}

@Composable
fun CountView(productViewModel: ProductViewModel) {
    val availableCount by productViewModel.availableCount.observeAsState()
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(78.dp)
    ) {
        Box(
            modifier = Modifier
                .background(color = brownGreyColor)
                .padding(start = 16.dp)
                .size(24.dp)
        )
        Column {
            Text(text = "В наличии")
            Text(text = "$availableCount")
        }
    }
}


@Composable
fun RatingRowView() {
    Box(
        modifier = Modifier
            .background(color = Gray)
            .height(52.dp)
            .fillMaxWidth()
    )
}

@Composable
fun ImageHeader() {
    Box(
        modifier = Modifier
            .background(color = Gray)
            .height(300.dp)
            .fillMaxWidth()
    )
}

@Composable
fun Toolbar() {
    Row(
        modifier = Modifier
            .height(44.dp)
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.background)
    ) {
        Text(text = "Back")
        Text(text = "Menu")
    }
}