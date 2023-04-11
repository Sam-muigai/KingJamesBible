package com.sam.kingjamesbible.feature_bible.core.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import com.sam.kingjamesbible.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.sam.kingjamesbible.ui.theme.KingJamesBibleTheme

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int? = R.drawable.arrow,
    title: String,
    onIconClick: () -> Unit = {}
) {
    val rowPadding = if (icon == null) 16.dp else 8.dp
    Surface(
        modifier = modifier,
        color = MaterialTheme.colors.primary
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(rowPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(
                    modifier = Modifier
                        .size(35.dp)
                        .clickable {
                            onIconClick()
                        },
                    painter = painterResource(id = icon),
                    contentDescription = null
                )
            }
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h6
                )
            }
        }
    }
}


@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.bible))
        LottieAnimation(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            iterations = LottieConstants.IterateForever,
            composition = composition,
        )
    }
}


@Preview
@Composable
fun TopBarPreview() {
    KingJamesBibleTheme {
        TopBar(
            title = "Select A Book",
            onIconClick = {}
        )
    }
}