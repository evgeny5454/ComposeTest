package com.evgeny_minaenkov.timemanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.evgeny_minaenkov.timemanager.ui.theme.TimeManagerTheme
import com.evgeny_minaenkov.timemanager.views.*

val brownGreyColor = Color(0xFF959595)
val whiteTwo = Color(0xFFF9F9F9)
val veryLightPink = Color(0xFFEAEAEA)

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


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductScreen(productViewModel: ProductViewModel = viewModel()) {
    val sku by productViewModel.sku.observeAsState()
    val title by productViewModel.title.observeAsState()
    val isInWishList by productViewModel.isInWishList.observeAsState(false)
    val isInCompare by productViewModel.isInCompare.observeAsState(false)


    LazyColumn(content = {
        stickyHeader {
            Toolbar()
        }
        item { ImageHeader(productViewModel = productViewModel) }
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
        item { CountView(productViewModel = productViewModel) }
        item { HeaderView(height = 68.dp, title = "?????????????? ??????????????????") }
        item { DeliveryPickupView(productViewModel = productViewModel) }
        item {
            Column() {
                RouteButton(
                    model = RouteButtonModel(
                        routeId = "InStoreAvailabale",
                        title = "?????????????? ?? ??????????????????"
                    ), onClick = {
                        //Toast.makeText(, "All Chars", Toast.LENGTH_SHORT).show()
                    })
                ActionButton(model = ActionButtonModel(
                    actionId = "AddToWishList",
                    title = "???????????????? ?? ????????????",
                    selectedTitle = "?????????????????? ?? ????????????",
                    isSelected = isInWishList
                ), onClick = { _, _ ->
                    productViewModel.toggleWishList()
                })
            }
        }
        item { HeaderView(height = 68.dp, title = "????????????????????????????") }
        item { CharacteristicsView(productViewModel = productViewModel) }
        item {
            Column() {
                RouteButton(
                    model = RouteButtonModel(
                        routeId = "AllCharsScreen",
                        title = "?????? ????????????????????????????"
                    ), onClick = {
                        //Toast.makeText(, "All Chars", Toast.LENGTH_SHORT).show()
                    })
                ActionButton(model = ActionButtonModel(
                    actionId = "AddToCompare",
                    title = "???????????????? ?? ??????????????????",
                    selectedTitle = "?????????????????? ?? ????????????",
                    isSelected = isInCompare
                ), onClick = { _, _ ->
                    productViewModel.toggleCompareList()
                })
            }
        }
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
fun CharacteristicsView(productViewModel: ProductViewModel) {

    val characteristics by productViewModel.characteristics.observeAsState(emptyList())

    Column(modifier = Modifier.fillMaxWidth()) {
        characteristics.map { CharacteristicsCell(model = it) }
    }
}

@Composable
fun CharacteristicsCell(model: CharacteristicsModel) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 24.dp)
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = model.title,
                modifier = Modifier.weight(0.6f),
                style = TextStyle(color = brownGreyColor)
            )
            Text(
                text = model.value, modifier = Modifier
                    .weight(0.4f)
                    .padding(8.dp), style = TextStyle(color = Black)
            )
        }
    }
    Divider(color = veryLightPink)
}

@Composable
fun DeliveryPickupView(productViewModel: ProductViewModel) {

    val pickupStoresCount by productViewModel.pickupStoresCount.observeAsState(0)

    Row(
        modifier = Modifier.padding(
            start = 16.dp,
            end = 24.dp,
            top = 22.dp,
            bottom = 22.dp
        )
    ) {
        Box(
            modifier = Modifier
                .background(color = brownGreyColor)
                .size(24.dp)
        )
        Column(modifier = Modifier.padding(start = 24.dp)) {
            Subtitle5(text = "???????????????? ??? ????????????, 25 ????????")
            Caption(
                text = "???? ???????????? 112 ????.",
                modifier = Modifier.padding(top = 2.dp)
            )
            if (pickupStoresCount > 0) {
                Subtitle5(
                    text = "?????????????????? ??? ??????????????",
                    modifier = Modifier.padding(top = 16.dp)
                )
                Caption(
                    text = "???????????????? ?? $pickupStoresCount ??????????????????",
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }
    }
}

@Composable
fun HeaderView(height: Dp, title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = height)
            .background(color = whiteTwo)
            .padding(start = 16.dp, bottom = 16.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
        )
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
            text = "???/????.",
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
        TextButton(itemsInCart)
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
            .padding(start = 16.dp, end = 24.dp)
    ) {
        Box(
            modifier = Modifier
                .background(color = brownGreyColor)
                .size(24.dp)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 24.dp, end = 24.dp)
        ) {
            Text(text = "?????????????????? ????????????????????", style = TextStyle(color = Black))
            Text(
                text = "?? ??????????????: $availableCount",
                style = TextStyle(color = brownGreyColor, fontSize = 12.sp)
            )
        }
        Box(
            modifier = Modifier
                .background(color = brownGreyColor)
                .size(24.dp)
        )
    }
}

@Composable
fun TextButton(itemsInCart: Int?) {
    val text: String = if (itemsInCart == 0) "?? ??????????????" else itemsInCart.toString()
    Text(
        text = text,
        style = TextStyle(
            color = White,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        )
    )
}


@Composable
fun RatingRowView() {
    Box(
        modifier = Modifier
            .background(color = White)
            .height(52.dp)
            .fillMaxWidth()
    )
}

@Composable
fun ImageHeader(productViewModel: ProductViewModel) {
    val image by productViewModel.imageHeaderView.observeAsState("")
    ImageView(
        imageUri = image,
        modifier = Modifier
            .height(260.dp)
            .fillMaxWidth()
    )
}

@Composable
fun Toolbar() {
    Row(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.background),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painterResource(id = R.drawable.ic_back),
            contentDescription = "Back button",
            modifier = Modifier.clickable {

            }.size(56.dp).padding(16.dp)
        )
        Spacer(modifier = Modifier.weight(1f))

        Image(painterResource(id = R.drawable.ic_more),
            contentDescription = "Back more",
            modifier = Modifier.clickable {

            }.size(56.dp).padding(16.dp)
        )
    }
}