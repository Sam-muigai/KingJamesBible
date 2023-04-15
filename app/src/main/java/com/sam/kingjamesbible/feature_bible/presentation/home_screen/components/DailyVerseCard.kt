package com.sam.kingjamesbible.feature_bible.presentation.home_screen.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sam.kingjamesbible.R
import com.sam.kingjamesbible.ui.theme.KingJamesBibleTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DailyVerseCard(
    modifier: Modifier = Modifier,
    verse: String,
    book:String,
    bibleVersion:String
) {
    val dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .wrapContentSize(),
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.surface,
        shape = MaterialTheme.shapes.small
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "VERSE OF THE DAY",
                style = MaterialTheme.typography.h6
            )
            Text(
                text = LocalDateTime.now().format(dateTimeFormatter),
                style = MaterialTheme.typography.h6.copy(
                    color = MaterialTheme.colors.primary
                )
            )
            Divider()
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = verse,
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "$book ",
                    style = MaterialTheme.typography.body1.copy(
                        color = MaterialTheme.colors.primary
                    )
                )
                Text(
                    text = bibleVersion,
                    style = MaterialTheme.typography.body1.copy(
                        color = MaterialTheme.colors.primary
                    )
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DailyVerseCardPreview() {
    KingJamesBibleTheme {
        DailyVerseCard(
            verse = stringResource(id = R.string.verse),
            book = "Matthew 21:4 ",
            bibleVersion = "NIV"
        )
    }
}