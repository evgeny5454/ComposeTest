package com.evgeny_minaenkov.timemanager.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.evgeny_minaenkov.timemanager.R

@Composable
fun IconArrow() {
    Image(
        painter = painterResource(id = R.drawable.ic_arrow),
        contentDescription = "Arrow button",
        modifier = Modifier.size(24.dp)
    )
}