package com.evgeny_minaenkov.timemanager.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

data class ActionButtonModel(
    val actionId: String,
    val title: String,
    val selectedTitle: String,
    val icon: String? = null,
    val selectedIcon: String? = null,
    val isSelected: Boolean = false
)

@Composable
fun ActionButton(
    model: ActionButtonModel,
    onClick: (ActionButtonModel, Boolean) -> Unit
) {
    var isSelected by remember { mutableStateOf(model.isSelected) }
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(80.dp)
        .background(color = Color.White)
        .padding(16.dp)
        .clickable {
            isSelected = !isSelected
            onClick.invoke(model, isSelected)
        }) {
        Card(
            modifier = Modifier.fillMaxSize(),
            backgroundColor = if (isSelected) Color.White else Color.LightGray,
            shape = RoundedCornerShape(4.dp),
            border = if (isSelected) BorderStroke(1.dp, color = Color.Black) else null,
            elevation = 0.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, end = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SubtitleMedium4(
                    modifier = Modifier.weight(1f),
                    text = if (isSelected) model.selectedTitle else model.title
                )
                if (isSelected) {
                    IconArrow()
                } else {
                    IconArrow()
                }
            }
        }
    }
}