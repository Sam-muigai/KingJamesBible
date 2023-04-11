package com.sam.kingjamesbible.feature_bible.presentation.verse_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sam.kingjamesbible.ui.theme.KingJamesBibleTheme

@Composable
fun HeaderSection(
    modifier: Modifier = Modifier,
    title: String,
    showPreviousIcon: Boolean = true,
    showNextIcon:Boolean = true,
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit
) {
    val (color, iconModifier) = if (showPreviousIcon) {
        Pair(
            MaterialTheme.colors.onBackground,
            Modifier
                .size(35.dp)
                .clickable { onPreviousClick() })
    } else {
        Pair(
            Color.Transparent,
            Modifier
                .size(35.dp)
        )
    }
    val (nextColor, nextIconModifier) = if (showNextIcon) {
        Pair(
            MaterialTheme.colors.onBackground,
            Modifier
                .size(35.dp)
                .clickable { onNextClick() })
    } else {
        Pair(
            Color.Transparent,
            Modifier
                .size(35.dp)
        )
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            modifier = iconModifier,
            imageVector = Icons.Default.KeyboardArrowLeft,
            contentDescription = "Previous",
            tint = color
        )
        Text(
            text = title,
            style = MaterialTheme.typography.h6.copy(
                fontSize = 20.sp
            )
        )
        Icon(
            modifier = nextIconModifier,
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Next",
            tint = nextColor
        )
    }
}


@Preview
@Composable
fun HeaderSectionPreview() {
    KingJamesBibleTheme {
        HeaderSection(title = "Genesis 14", onNextClick = { }) {

        }
    }
}