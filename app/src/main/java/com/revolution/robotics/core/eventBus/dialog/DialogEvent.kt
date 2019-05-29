package com.revolution.robotics.core.eventBus.dialog

import android.os.Bundle

enum class DialogEvent {
    PERMISSION_GRANTED,
    BRAIN_TURNED_ON,
    BRAIN_NOT_TURNED_ON,
    ROBOT_CONNECTED,
    ROBOT_CONNECTION_FAILED,
    ROBOT_RECONNECT,
    CHAPTER_FINISHED,
    SKIP_TESTING,
    TEST_WORKS,
    DELETE_ROBOT,
    SAVE_ROBOT,
    ADD_PROGRAM,
    REMOVE_PROGRAM,
    SAVE_CONTROLLER,
    DELETE_CONTROLLER,
    LETS_DRIVE,
    SHOW_PROGRAM_INFO,
    LOAD_PROGRAM,
    DELETE_PROGRAM,
    SAVE_PROGRAM;

    val extras = Bundle()
}
