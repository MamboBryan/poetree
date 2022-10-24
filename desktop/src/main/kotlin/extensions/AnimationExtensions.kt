package com.mambo.poetree.extensions

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.ui.unit.IntOffset

/**
 * ENTER
 */
fun slideInRight(
    animation: FiniteAnimationSpec<IntOffset> = spring(
        stiffness = Spring.StiffnessMediumLow,
        visibilityThreshold = IntOffset.VisibilityThreshold
    ),
    initialOffsetX: (fullWidth: Int) -> Int = { it },
) = slideInHorizontally(
    initialOffsetX = initialOffsetX,
    animationSpec = animation
)

fun slideInLeft(
    animation: FiniteAnimationSpec<IntOffset> = spring(
        stiffness = Spring.StiffnessMediumLow,
        visibilityThreshold = IntOffset.VisibilityThreshold
    ),
    initialOffsetX: (fullWidth: Int) -> Int = { -300 },
) = slideInHorizontally(
    initialOffsetX = initialOffsetX,
    animationSpec = animation
)

/**
 * EXIT
 */
fun slideOutRight(
    animation: FiniteAnimationSpec<IntOffset> = spring(
        stiffness = Spring.StiffnessMediumLow,
        visibilityThreshold = IntOffset.VisibilityThreshold
    ),
    initialOffsetX: (fullWidth: Int) -> Int = { -it / 2 },
) = slideOutHorizontally(
    targetOffsetX = { it },
    animationSpec = animation
)

fun slideOutLeft(
    animation: FiniteAnimationSpec<IntOffset> = spring(
        stiffness = Spring.StiffnessMediumLow,
        visibilityThreshold = IntOffset.VisibilityThreshold
    ),
    initialOffsetX: (fullWidth: Int) -> Int = { -it / 2 },
) = slideOutHorizontally(
    targetOffsetX = { -300 },
    animationSpec = animation
)