package com.mambo.poetree.android.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Fri 10 Mar 2023
 */

@OptIn(ExperimentalMaterial3Api::class, ExperimentalUnitApi::class)
@Composable
fun UserImage(
    modifier: Modifier = Modifier.width(24.dp).height(24.dp),
    fontSize: TextUnit = TextUnit(12f, TextUnitType.Sp),
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(25),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colors.primary),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "MB",
                color = MaterialTheme.colors.onPrimary,
                fontSize = fontSize
            )
        }
    }
}

@Preview
@Composable
fun UserImagePreview() {
    UserImage(modifier = Modifier.width(150.dp).height(150.dp))
}