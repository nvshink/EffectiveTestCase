package com.nvshink.effectivetestcase.ui.states

import com.nvshink.effectivetestcase.data.model.Course
import com.nvshink.effectivetestcase.ui.utils.SortTypes

data class CourseUIState (
    val coursesList: List<Course> = emptyList(),
    val currentCourse: Course? = null,
    val sortType: SortTypes = SortTypes.PUBLISH_DATE_ASC,
    val isLoading: Boolean = true
)