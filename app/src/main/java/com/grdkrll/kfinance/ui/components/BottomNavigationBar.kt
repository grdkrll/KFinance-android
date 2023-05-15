package com.grdkrll.kfinance.ui.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle

@Composable
fun BottomNavigationBar(
    selectedList: List<Boolean>,
    redirectToFunctions: List<() -> Unit>,
    numberOfItems: Int = 3,
    icons: List<ImageVector> = listOf(Icons.Filled.Home, Icons.Filled.People, Icons.Filled.Person),
    iconsLabels: List<String> = listOf("Home", "Groups", "Profile")
) {
    BottomNavigation(
        contentColor = Color(197, 183, 134),
        backgroundColor = Color.White
    ) {
        repeat(numberOfItems) { i ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = icons[i],
                        iconsLabels[i],
                        tint = if (selectedList[i]) Color(217, 203, 79) else Color(197, 183, 134)
                    )
                },
                label = { Text(iconsLabels[i], style = TextStyle(color = Color(217, 203, 79))) },
                alwaysShowLabel = false,
                selected = selectedList[i],
                selectedContentColor = Color(217, 203, 79),
                unselectedContentColor = Color(197, 183, 134),
                onClick = redirectToFunctions[i]
            )
        }
    }
}
