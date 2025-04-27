package com.nvshink.effectivetestcase.ui.screen.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nvshink.effectivetestcase.ui.event.CourseEvent
import com.nvshink.effectivetestcase.ui.screen.home.CourseCard
import com.nvshink.effectivetestcase.ui.states.CourseUIState

@Composable
fun FavoritesScreen(
    innerPadding: PaddingValues,
    courseUIState: CourseUIState,
    onEvent: (CourseEvent) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(24.dp), modifier = Modifier
            .padding(innerPadding)
            .padding(horizontal = 16.dp)
    ) {
        items(courseUIState.coursesList.filter { it.hasLike }) { course ->
            CourseCard(
                course = course,
                onFavoritesClick = {
                    if (course.hasLike) {
                        onEvent(CourseEvent.DeleteFavoriteCourse(courseId = course.id))
                    } else {
                        onEvent(CourseEvent.SaveFavoriteCourse(courseId = course.id))
                    }
                },
                onDetailsClick = {}
            )
        }
    }
}