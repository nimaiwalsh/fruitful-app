package com.nims.fruitful.ui.screens.dailyideas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nims.fruitful.ui.common.composable.ActionToolbar
import com.nims.fruitful.ui.common.ext.smallSpacer
import com.nims.fruitful.ui.common.ext.toolbarActions
import com.nims.fruitful.R.drawable as AppIcon
import com.nims.fruitful.R.string as AppText

@Composable
@ExperimentalMaterialApi
fun DailyIdeasScreen(
    navigateToEditIdea: (String) -> Unit,
    viewModel: DailyIdeasViewModel = hiltViewModel()
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToEditIdea("") },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
                modifier = Modifier.padding(16.dp)
            ) { Icon(Icons.Filled.Add, "Add") }
        }
    ) { padding ->
        ScreenContent(modifier = Modifier.padding(padding), navigateToEditIdea, viewModel)
    }

    DisposableEffect(viewModel) {
        viewModel.addListener()
        onDispose { viewModel.removeListener() }
    }
}

@Composable
@ExperimentalMaterialApi
private fun ScreenContent(
    modifier: Modifier,
    navigateToEditIdea: (String) -> Unit,
    viewModel: DailyIdeasViewModel
) {

    val ideas = viewModel.ideas

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        ActionToolbar(
            title = AppText.ideas,
            endActionIcon = AppIcon.ic_settings,
            endAction = { },
            modifier = Modifier.toolbarActions(),
        )

        Spacer(modifier = Modifier.smallSpacer())

        LazyColumn {
            items(ideas, key = { it.id }) { ideaItem ->
                IdeaItem(
                    idea = ideaItem,
                    onActionClick = { action ->
                        viewModel.onIdeaActionClick(navigateToEditIdea, ideaItem, action)
                    }
                )
            }
        }
    }
}