package com.sam.kingjamesbible.feature_bible.presentation.book_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sam.kingjamesbible.R
import java.util.*


@Composable
fun BookColumn(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.surface,
    bookTitle: String = "MATTHEW",
    onBookClick: () -> Unit
) {
    Surface(
        modifier = modifier.clickable {
            onBookClick()
        },
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    modifier = Modifier.size(45.dp),
                    painter = painterResource(id = R.drawable.bible),
                    contentDescription = "Bible",
                )
                Text(
                    text = bookTitle.uppercase(Locale.ENGLISH),
                    style = MaterialTheme.typography.h6.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
            }
            Icon(
                modifier = Modifier.size(35.dp),
                painter = painterResource(id = R.drawable.more_than),
                contentDescription = null
            )
        }
    }
}