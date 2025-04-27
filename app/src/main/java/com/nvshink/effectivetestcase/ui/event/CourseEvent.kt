package com.nvshink.effectivetestcase.ui.event

import com.nvshink.effectivetestcase.data.model.Course
import com.nvshink.effectivetestcase.ui.utils.SortTypes

sealed interface CourseEvent {
    data class SaveFavoriteCourse(val courseId: Int): CourseEvent
    data class DeleteFavoriteCourse(val courseId: Int): CourseEvent
    data class UpdateCurrentCourse(val course: Course): CourseEvent
    data class SetSortType(val sortType: SortTypes): CourseEvent
}