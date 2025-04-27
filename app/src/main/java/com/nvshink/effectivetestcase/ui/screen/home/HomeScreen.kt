package com.nvshink.effectivetestcase.ui.screen.home

import android.provider.CalendarContract.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.nvshink.effectivetestcase.R
import com.nvshink.effectivetestcase.data.model.Course
import com.nvshink.effectivetestcase.ui.event.CourseEvent
import com.nvshink.effectivetestcase.ui.states.CourseUIState
import com.nvshink.effectivetestcase.ui.theme.Green
import com.nvshink.effectivetestcase.ui.utils.SortTypes
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    innerPadding: PaddingValues,
    courseUIState: CourseUIState,
    onEvent: (CourseEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .padding(horizontal = 16.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SearchBar(
                inputField = {
                    SearchBarDefaults.InputField(
                        query = "",
                        onQueryChange = { },
                        onSearch = {},
                        expanded = false,
                        onExpandedChange = { },
                        placeholder = { Text(stringResource(R.string.search_bar_placeholder)) },
                        leadingIcon = {
                            Icon(
                                painterResource(R.drawable.ic_search),
                                contentDescription = stringResource(R.string.search_icon_description),
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        },
                        enabled = false,
                    )
                },
                colors = SearchBarDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                ),
                windowInsets = WindowInsets(0.dp),
                expanded = false,
                onExpandedChange = {},
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .height(60.dp)
                    .weight(1f)
            ) { }
            IconButton(
                onClick = {},
                enabled = false,
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .size(64.dp)
                    .background(MaterialTheme.colorScheme.surfaceContainer)
            ) {
                Icon(
                    painterResource(R.drawable.ic_funnel),
                    contentDescription = stringResource(R.string.funnel_icon_description),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            TextButton(
                onClick = { onEvent(CourseEvent.SetSortType(if (courseUIState.sortType == SortTypes.PUBLISH_DATE_ASC) SortTypes.PUBLISH_DATE_DSC else SortTypes.PUBLISH_DATE_ASC)) },
                modifier = Modifier
                    .height(56.dp)
                    .padding(vertical = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.sort_type_name),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Icon(
                        painterResource(R.drawable.ic_arrow_down_up),
                        contentDescription = stringResource(R.string.arrow_up_down_icon_description)
                    )
                }
            }
        }
        if (courseUIState.isLoading) { //TODO(Fix ui state update loading val)
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                items(courseUIState.coursesList) { course ->
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
    }
}

@Composable
fun CourseCard(
    course: Course,
    onDetailsClick: () -> Unit,
    onFavoritesClick: () -> Unit
) {
    Card {
        Column {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.DarkGray)
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(8.dp)
            ) {
                IconButton(
                    onClick = onFavoritesClick,
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_bookmark),
                        contentDescription = stringResource(R.string.bookmark_icon_description),
                        tint = if (course.hasLike) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary,
                    )
                }
                Row(
                    modifier = Modifier.align(Alignment.BottomStart),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_star_fill),
                            contentDescription = stringResource(R.string.star_icon_description),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = course.rate.toString(),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    Row {
                        Text(
                            text = course.publishDate?.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
                                ?: "",
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = course.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = course.text,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = course.price + " ₽",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    TextButton(
                        onClick = onDetailsClick,
                        modifier = Modifier
                            .height(30.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.details_name),
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.size(4.dp))
                            Icon(
                                painterResource(R.drawable.ic_arrow_right_short_fill),
                                contentDescription = stringResource(R.string.arrow_right_icon_description)
                            )
                        }
                    }
                }
            }
        }
    }
}