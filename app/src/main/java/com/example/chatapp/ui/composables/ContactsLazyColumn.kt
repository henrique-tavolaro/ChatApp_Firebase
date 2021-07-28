package com.example.chatapp.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.example.chatapp.domain.entity.UserModel
import com.example.chatapp.ui.fragments.home_fragment.HomeFragmentDirections
import com.example.chatapp.utils.DEFAULT_IMAGE

@ExperimentalMaterialApi
@Composable
fun ContactsLazyColumn(user: UserModel, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth(),
        elevation = 4.dp,
        onClick = {
            navController.navigate(HomeFragmentDirections.actionHomeFragmentToChatFragment(id = user.id, name = user.name, photoUrl = user.photoUrl, email = user.email))
        }

    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            user.photoUrl.let { url ->
                val image =
                    loadImageUri(
                        url = url!!.toUri(),
                        defaultImage = DEFAULT_IMAGE
                    ).value
                image?.let { img ->
                    Image(
                        bitmap = img.asImageBitmap(),
                        modifier = Modifier
                            .height(72.dp)
                            .width(72.dp)
                            .padding(8.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )
                }
            }
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = user.name,
                    fontSize = 18.sp
                )
                Text(
                    text = user.email,
                    fontSize = 14.sp
                )
            }
        }
    }

}
