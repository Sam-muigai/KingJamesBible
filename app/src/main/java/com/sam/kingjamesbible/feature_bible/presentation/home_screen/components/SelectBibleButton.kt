package com.sam.kingjamesbible.feature_bible.presentation.home_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun SelectBibleButton(
    modifier: Modifier = Modifier,
    visible: Boolean,
    onTestamentClick: (String) -> Unit,
    onButtonClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onButtonClick()
            },
        shape = MaterialTheme.shapes.small
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "READ BIBLE",
                style = MaterialTheme.typography.h6
            )
            AnimatedVisibility(visible = visible) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BibleButton(
                        text = "NEW TESTAMENT",
                        onButtonClick = { onTestamentClick("NEW TESTAMENT") }
                    )
                    Divider(modifier = Modifier.padding(horizontal = 8.dp))
                    BibleButton(
                        text = "OLD TESTAMENT",
                        onButtonClick = { onTestamentClick("OLD TESTAMENT") }
                    )
                }
            }
        }
    }
}

@Composable
fun BibleButton(
    modifier: Modifier = Modifier,
    text: String,
    onButtonClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onButtonClick() }
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.h6
        )
    }
}
