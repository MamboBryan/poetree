package com.mambo.poetree.android.comments

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mambo.poetree.android.ui.composables.HorizontalStat
import com.mambo.poetree.android.ui.composables.UserImage
import com.mambo.poetree.android.ui.navigation.navigateToUser
import com.mambo.poetree.data.remote.dto.CommentCompleteDto

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Tue 14 Mar 2023
 */

@Composable
fun Comment(
    modifier: Modifier = Modifier,
    navController: NavController,
    comment: CommentCompleteDto,
    onLikeClicked: () -> Unit
) {
    var liked by remember { mutableStateOf(comment.liked) }
    var count by remember { mutableStateOf(comment.likes) }

    val user = comment.user.toUser()
    val (icon, color) = when (liked) {
        true -> Pair(Icons.Rounded.Favorite, MaterialTheme.colors.primary)
        false -> Pair(Icons.Rounded.Favorite, Color.Gray)
    }

    fun navigateToUser() {
        navController.navigateToUser(userId = user.id ?: "")
    }

    Row(modifier = modifier.padding(vertical = 8.dp)) {
        UserImage(
            modifier = Modifier,
            onClick = ::navigateToUser
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        ) {
            Text(
                modifier = Modifier.clickable { navigateToUser() },
                text = user.name ?: "Username",
                fontWeight = FontWeight.Bold
            )
            Text(modifier = Modifier.padding(vertical = 8.dp), text = comment.content)
            Text(modifier = Modifier.alpha(0.8f), text = comment.createdAt)
        }
        HorizontalStat(
            icon = icon,
            count = count,
            color = color,
            modifier = Modifier.clickable {
                liked = !liked
                count = if (liked) ++count else --count
                onLikeClicked.invoke()
            },
        )
    }
}