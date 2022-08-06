package com.nims.fruitful.ui.screens.daily_ideas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nims.fruitful.R
import com.nims.fruitful.model.Idea
import com.nims.fruitful.ui.common.composable.DropdownContextMenu
import com.nims.fruitful.ui.common.ext.contextMenu

@Composable
@ExperimentalMaterialApi
fun IdeaItem(
    idea: Idea,
    onActionClick: (String) -> Unit
) {
    Card(
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = idea.title, style = MaterialTheme.typography.subtitle2)
            }

            if (idea.favourite) {
                Icon(
                    painter = painterResource(R.drawable.ic_flag),
                    contentDescription = "Star"
                )
            }

            DropdownContextMenu(IdeaActionOption.getOptions(), Modifier.contextMenu(), onActionClick)
        }
    }
}