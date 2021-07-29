package com.example.chatapp.ui.composables


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.chatapp.R

@ExperimentalMaterialApi
@Composable
fun GoogleButton(
    text: String = "Sign In with Google",
    icon: Int = R.drawable.ic_google_logo,
    borderColor: Color = Color.LightGray,
    backgroundColor: Color = MaterialTheme.colors.surface,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,
        border = BorderStroke(width = 1.dp, color = borderColor),
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(
                    start = 12.dp,
                    end = 16.dp,
                    top = 12.dp,
                    bottom = 12.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "Google Button",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text)

        }
    }
}