package com.vladtruta.repository

import com.vladtruta.model.local.Application
import com.vladtruta.model.local.Tutorial

val tutorials = mutableListOf(
    Tutorial(
        "com.google.android.apps.messaging",
        "How do I read a message?",
        "https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4",
        0.7
    ),
    Tutorial(
        "com.google.android.apps.messaging",
        "How do I write a message?",
        "https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4",
        1.0
    )
)

val applications = mutableListOf(
    Application("com.android.contacts", "Contacts"),
    Application("com.android.camera2", "Camera"),
    Application("com.google.android.apps.messaging", "Messages")
)