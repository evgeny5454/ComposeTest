package com.evgeny_minaenkov.timemanager.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evgeny_minaenkov.timemanager.ui.theme.TimeManagerTheme

data class RouteButtonModel(val routeId: String, val title: String, val icon: Int? = null)

@Composable
fun RouteButton(
    model: RouteButtonModel,
    onClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .height(64.dp)
            .fillMaxWidth()
            .clickable {
                onClick.invoke(model.routeId)
            }
            .padding(start = 16.dp, end = 16.dp), verticalAlignment = Alignment.CenterVertically
    ) {

        IconArrow()

        Subtitle5(
            text = model.title,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp, end = 16.dp)
        )

        IconArrow()
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TimeManagerTheme {
        RouteButton(model = RouteButtonModel("1234", "Привет"), onClick = { })
    }
}