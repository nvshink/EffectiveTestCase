package com.nvshink.effectivetestcase.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nvshink.effectivetestcase.data.model.Course
import com.nvshink.effectivetestcase.data.model.FavoriteCourse
import com.nvshink.effectivetestcase.data.repository.CourseRepositoryImpl
import com.nvshink.effectivetestcase.data.repository.FavoriteCourseRepositoryImpl
import com.nvshink.effectivetestcase.ui.event.CourseEvent
import com.nvshink.effectivetestcase.ui.states.CourseUIState
import com.nvshink.effectivetestcase.ui.utils.SortTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class CourseViewModel @Inject constructor(
    private val courseRepository: CourseRepositoryImpl,
    private val favoriteCourseRepository: FavoriteCourseRepositoryImpl
) : ViewModel() {
    private val _sortType = MutableStateFlow(SortTypes.PUBLISH_DATE_ASC)
    private val _favoriteCourses = favoriteCourseRepository.getFavoriteCoursesById()
    private val _uiState = MutableStateFlow(CourseUIState())
    private val _courses = _sortType
        .flatMapLatest { sortType ->
            try {
                val a: Flow<List<Course>>
                when (sortType) {
                    SortTypes.PUBLISH_DATE_ASC -> {
                         courseRepository.getCoursesByPublishDateASC()
                    }
                    SortTypes.PUBLISH_DATE_DSC -> {
                        courseRepository.getCoursesByPublishDateDSC()
                    }
                }
            } catch (e: Error) {
                Log.e("GET_COURSES", e.message ?: "Error get courses from source")
                return@flatMapLatest emptyFlow()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val uiState = combine(_uiState, _sortType, _courses, _favoriteCourses) { uiState, sortType, courses, favoriteCourses ->
        uiState.copy(
            coursesList = courses.map {
                if (favoriteCourses.contains(FavoriteCourse(it.id))) it.copy(hasLike = true) else it
            },
            sortType = sortType,
            isLoading = false
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CourseUIState())

    fun onEvent(event: CourseEvent) {
        when (event) {
            is CourseEvent.SaveFavoriteCourse -> {
                val favoriteCourse = FavoriteCourse(id = event.courseId)
                viewModelScope.launch {
                    favoriteCourseRepository.upsertFavoriteCourse(favoriteCourse = favoriteCourse)
                }
            }

            is CourseEvent.DeleteFavoriteCourse -> {
                val favoriteCourse = FavoriteCourse(id = event.courseId)
                viewModelScope.launch {
                    favoriteCourseRepository.deleteFavoriteCourse(favoriteCourse = favoriteCourse)
                }
            }

            is CourseEvent.UpdateCurrentCourse -> {
                _uiState.update {
                    it.copy(
                        currentCourse = event.course
                    )
                }
            }

            is CourseEvent.SetSortType -> {
                _sortType.value = event.sortType
            }
        }
    }

}