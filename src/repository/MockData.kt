package com.vladtruta.repository

import com.vladtruta.model.local.Application
import com.vladtruta.model.local.Tutorial

val tutorials = mutableListOf(
    Tutorial(
        "com.google.android.apps.messaging",
        "How do I read a message?",
        "https://media.vlipsy.com/vlips/tVmiYVBz/480p.mp4",
        0.7
    ),
    Tutorial(
        "com.google.android.apps.messaging",
        "How do I write a message?",
        "https://media.vlipsy.com/vlips/tVmiYVBz/480p.mp4",
        1.0
    )
)

val applications = mutableListOf(
    Application("com.android.contacts", "Contacts"),
    Application("com.android.camera2", "Camera"),
    Application("com.google.android.apps.messaging", "Messages")
)