package com.nims.fruitful.ui.common.composable

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.materialIcon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nims.fruitful.R

/**
 * Now in Android navigation bar with content slot. Wraps Material 3 [NavigationBar].
 *
 * @param modifier Modifier to be applied to the navigation bar.
 * @param content Destinations inside the navigation bar. This should contain multiple
 * [NavigationBarItem]s.
 */
@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    BottomNavigation {
        BottomNavigationItem(
            selected = true,
            onClick = { },
            icon = { Icon(painter = painterResource(id = R.drawable.ic_clock), contentDescription = "") }
        )
    }

// material 3
//    NavigationBar(
//        modifier = modifier,
//        containerColor = NiaNavigationDefaults.NavigationContainerColor,
//        contentColor = NiaNavigationDefaults.navigationContentColor(),
//        tonalElevation = 0.dp,
//        content = content
//    )
}
