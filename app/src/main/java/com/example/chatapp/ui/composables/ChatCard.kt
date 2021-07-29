package com.example.chatapp.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatapp.domain.entity.Message

@Composable
fun ChatCard(message: Message, color: Color, modifier: Modifier = Modifier){
    Card(
        modifier = modifier,
        backgroundColor = color,
        shape = RoundedCornerShape(10.dp),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier.padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .padding(4.dp)
                    .widthIn(0.dp, 280.dp),
                text = message.message
            )
            Text(
                modifier = Modifier.padding(4.dp),
                text = message.time,
                style = TextStyle(
                    fontSize = 12.sp,
                    color = Color.LightGray
                )
            )
        }

    }
}