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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
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
    private val _uiState = MutableStateFlow(_sortType
        .flatMapLatest { sortType ->
            flow {
                emit(CourseUIState.Loading())
                try {
                    val result: Flow<List<Course>> = when (sortType) {
                        SortTypes.PUBLISH_DATE_ASC -> {
                            courseRepository.getCoursesByPublishDateASC()
                        }

                        SortTypes.PUBLISH_DATE_DSC -> {
                            courseRepository.getCoursesByPublishDateDSC()
                        }
                    }
                    emit(
                        CourseUIState.Success(
                            result.first(),
                            currentCourse = null,
                            sortType = sortType
                        )
                    )
                } catch (e: Error) {
                    Log.e("GET_COURSES", e.message ?: "Error get courses from source")
                    emit(
                        CourseUIState.Error(
                            message = e.message.toString(),
                            currentCourse = null,
                            sortType = sortType
                        )
                    )
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            CourseUIState.Loading()
        ))

    val uiState =
        combine(
            _uiState.value, _sortType, _favoriteCourses) { uiState, sortType, favoriteCourses ->
            Log.d(
                "SUS",
                "Start combine " + uiState.coursesList.size.toString() + " state " + uiState::class.toString()
            )
            when (uiState) {
                is CourseUIState.Loading -> {
                    Log.d("SUS", "Loading " + uiState::class.toString())
                    return@combine uiState.copy(
                        coursesList = uiState.coursesList,
                        sortType = sortType,
                        currentCourse = uiState.currentCourse
                    )
                }

                is CourseUIState.Success -> {
                    Log.d("SUS", "Success " + uiState::class.toString())
                    return@combine uiState.copy(
                        coursesList = uiState.coursesList.map {
                            if (favoriteCourses.contains(
                                    FavoriteCourse(
                                        it.id
                                    )
                                )
                            ) it.copy(hasLike = true) else it
                        },
                        sortType = sortType,
                        currentCourse = uiState.currentCourse
                    )
                }

                is CourseUIState.Error -> {
                    Log.d("SUS", "Error " + uiState::class.toString())
                    return@combine uiState.copy(
                        coursesList = uiState.coursesList,
                        sortType = sortType,
                        currentCourse = uiState.currentCourse
                    )
                }
            }

        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CourseUIState.Loading())

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
//                _uiState. update {
//                    it.copy(
//                        currentCourse = event.course
//                    )
//                }
            }

            is CourseEvent.SetSortType -> {
                _sortType.value = event.sortType
            }
        }
    }

}