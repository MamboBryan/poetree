package com.mambo.poetree.android.ui.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.mambo.poetree.android.ui.asColor
import com.mambo.poetree.data.domain.Poem
import com.mambo.poetree.data.domain.Topic

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Thu 09 Mar 2023
 */

@SuppressLint("ComposableNaming")
@OptIn(ExperimentalMaterialApi::class, ExperimentalUnitApi::class)
@Composable
fun Poem.getUi(block: () -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 4.dp),
        onClick = block,
        shape = RoundedCornerShape(50f),
        backgroundColor = (topic?.color ?: "#EDFFD6").asColor()
    ) {

        val icon = when (type) {
            Poem.Type.REMOTE -> Icons.Rounded.FavoriteBorder
            Poem.Type.BOOKMARK -> Icons.Rounded.BookmarkBorder
            Poem.Type.DRAFT -> Icons.Rounded.Brush
        }

        Box(modifier = Modifier) {
            Icon(
                modifier = Modifier
                    .width(150.dp)
                    .height(150.dp)
                    .align(Alignment.TopEnd)
                    .rotate(if (type == Poem.Type.DRAFT) 0f else 45f)
                    .alpha(0.2f)
                    .blur(5.dp),
                imageVector = icon,
                contentDescription = "icon"
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)
                    .align(Alignment.CenterStart),
                verticalArrangement = Arrangement.Bottom
            ) {

                Text(text = "$displayTopic  •  $displayDate")
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = title,
                    fontSize = TextUnit(24f, TextUnitType.Sp),
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                if (type == Poem.Type.REMOTE) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        HorizontalStat(
                            icon = if (bookmarked) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                            count = likes
                        )
                        HorizontalStat(
                            icon = if (commented) Icons.Rounded.ChatBubble else Icons.Rounded.ChatBubbleOutline,
                            count = comments
                        )
                        HorizontalStat(
                            icon = if (bookmarked) Icons.Rounded.Bookmark else Icons.Rounded.BookmarkBorder,
                            count = bookmarks
                        )
                        HorizontalStat(
                            icon = if (read) Icons.Rounded.CheckCircle else Icons.Rounded.CheckCircleOutline,
                            count = reads
                        )
                    }
                }

            }
        }
    }
}

@Preview
@Composable
fun GetUiPreview() {
    val poem = Poem(
        "",
        "Calculated Victory Among The Seas above and beyond the cradle",
        "kcbibovic",
        "",
        "",
        Poem.Type.DRAFT,
        "",
        Topic(1, "joy", "#F9B3D5", "", null)
    )
    poem.getUi {

    }
}