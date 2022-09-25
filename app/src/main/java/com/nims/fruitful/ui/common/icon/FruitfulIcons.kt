package com.nims.fruitful.ui.common.icon

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material.icons.rounded.Lightbulb
import androidx.compose.material.icons.rounded.Star
import androidx.compose.ui.graphics.vector.ImageVector

/** Material icons are [ImageVector]s, custom icons are drawable resource IDs. */
object FruitfulIcons {
    val Star = Icons.Rounded.Star
    val StarBorder = Icons.Outlined.StarBorder
    val Light = Icons.Rounded.Lightbulb
    val LightBorder = Icons.Outlined.Lightbulb
}

/** A sealed class to make dealing with [ImageVector] and [DrawableRes] icons easier. */
sealed class Icon {
    data class ImageVectorIcon(val imageVector: ImageVector) : Icon()
    data class DrawableResourceIcon(@DrawableRes val id: Int) : Icon()
}