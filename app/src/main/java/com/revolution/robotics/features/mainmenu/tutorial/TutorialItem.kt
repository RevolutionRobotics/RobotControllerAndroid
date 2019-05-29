package com.revolution.robotics.features.mainmenu.tutorial

import androidx.annotation.StringRes
import com.revolution.robotics.R

enum class TutorialItem(@StringRes val title: Int, @StringRes val description: Int) {
    ROBOTS(R.string.tutorial_robots_title, R.string.tutorial_robots_description),
    PROGRAMS(R.string.tutorial_programs_title, R.string.tutorial_programs_description),
    CHALLENGES(R.string.tutorial_challenges_title, R.string.tutorial_challenges_description),
    COMMUNITY(R.string.tutorial_community_title, R.string.tutorial_community_description),
    SETTINGS(R.string.tutorial_settings_title, R.string.tutorial_settings_description)
}
