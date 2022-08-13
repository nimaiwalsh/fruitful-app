package com.nims.fruitful.ui.screens.editidea

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.nims.fruitful.model.Idea
import com.nims.fruitful.ui.common.composable.ActionToolbar
import com.nims.fruitful.ui.common.composable.BasicField
import com.nims.fruitful.ui.common.ext.fieldModifier
import com.nims.fruitful.ui.common.ext.spacer
import com.nims.fruitful.ui.common.ext.toolbarActions
import com.nims.fruitful.R.drawable as AppIcon
import com.nims.fruitful.R.string as AppText

@Composable
fun EditIdeaScreen(popUpScreen: () -> Unit, ideaId: String, viewModel: EditIdeaViewModel = hiltViewModel()) {
    val idea by viewModel.idea

    Log.d("Navigated to EditIdeaScreen with argument", ideaId)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ActionToolbar(
            title = AppText.edit_idea,
            modifier = Modifier.toolbarActions(),
            endActionIcon = AppIcon.ic_check,
            endAction = { viewModel.onDoneClick(popUpScreen) }
        )

        Spacer(modifier = Modifier.spacer())
        BasicFields(idea, viewModel)
        Spacer(modifier = Modifier.spacer())
    }

}

@Composable
private fun BasicFields(idea: Idea, viewModel: EditIdeaViewModel) {
    val fieldModifier = Modifier.fieldModifier()
    BasicField(AppText.title, idea.title, viewModel::onTitleChange, fieldModifier)
    BasicField(AppText.description, idea.description, viewModel::onDescriptionChange, fieldModifier)
}
