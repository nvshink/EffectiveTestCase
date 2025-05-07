package com.nvshink.effectivetestcase.ui.states

import com.nvshink.effectivetestcase.data.model.Course
import com.nvshink.effectivetestcase.ui.utils.SortTypes

sealed interface CourseUIState {
    val coursesList: List<Course>
    val currentCourse: Course?
    val sortType: SortTypes
    data class Loading(
        override val coursesList: List<Course> = emptyList(),
        override val currentCourse: Course? = null,
        override val sortType: SortTypes = SortTypes.PUBLISH_DATE_ASC
    ) : CourseUIState

    data class Success(
        override val coursesList: List<Course>,
        override val currentCourse: Course?,
        override val sortType: SortTypes
    ) : CourseUIState

    data class Error(
        val message: String,
        override val coursesList: List<Course> = emptyList(),
        override val currentCourse: Course? = null,
        override val sortType: SortTypes = SortTypes.PUBLISH_DATE_ASC
    ) : CourseUIState
}