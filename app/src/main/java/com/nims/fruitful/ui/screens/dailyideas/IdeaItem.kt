package com.nims.fruitful.ui.screens.dailyideas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nims.fruitful.model.Idea
import com.nims.fruitful.ui.common.composable.DropdownContextMenu
import com.nims.fruitful.ui.common.ext.contextMenu
import com.nims.fruitful.ui.theme.FruitfulTheme

@Composable
fun IdeaItem(
    modifier: Modifier = Modifier,
    idea: Idea,
    onActionClick: (String) -> Unit
) {
    Card(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = 10.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = idea.title, style = MaterialTheme.typography.titleMedium)
                if (idea.description.isNotBlank()) {
                    Text(text = idea.description, style = MaterialTheme.typography.bodyMedium)
                }
            }

            if (idea.favourite) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Star",
                )
            }

            DropdownContextMenu(
                IdeaActionOption.getOptions(),
                Modifier.contextMenu(),
                onActionClick
            )
        }
    }
}

@Preview
@Composable
fun IdeaItem_Preview() {
    FruitfulTheme {
        val item = Idea(
            id = "",
            title = "Great idea",
            description = "An awesome description",
            favourite = false,
            userId = ""
        )
        IdeaItem(idea = item, onActionClick = {})
    }
}