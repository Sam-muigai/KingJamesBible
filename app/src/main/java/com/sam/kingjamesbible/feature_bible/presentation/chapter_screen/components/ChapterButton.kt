package com.sam.kingjamesbible.feature_bible.presentation.chapter_screen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import com.sam.kingjamesbible.ui.theme.KingJamesBibleTheme

@Composable
fun ChapterButton(
    modifier: Modifier = Modifier,
    text: String,
    onButtonClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .padding(2.dp)
            .clickable {
                onButtonClick()
            }
            .size(65.dp),
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colors.surface,
        border = BorderStroke(0.4.dp, Color.LightGray)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier,
                text = text,
                style = MaterialTheme.typography.body1.copy(
                    fontSize = 30.sp
                )
            )
        }
    }
}

@Composable
fun Flow(modifier: Modifier = Modifier) {

}


@Preview(showBackground = true)
@Composable
fun ChapterButtonPreview() {
    KingJamesBibleTheme {

        Flow()
    }
}