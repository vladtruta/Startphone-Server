package com.vladtruta.repository

import com.vladtruta.model.local.Application
import com.vladtruta.model.local.Tutorial

val tutorials = mutableListOf(
    Tutorial(
        "com.google.android.apps.messaging",
        "How do I read a message?",
        "http://truta.ro/licenta/how_to_read_messages.mp4",
        0.7,
        1
    ),
    Tutorial(
        "com.google.android.apps.messaging",
        "How do I write a message?",
        "http://truta.ro/licenta/how_to_write_messages.mp4",
        1.0,
        2
    ),
    Tutorial(
        "com.android.contacts",
        "How do I add someone to my address book?",
        "http://truta.ro/licenta/how_to_add_address_book.mp4",
        1.0,
        3
    ),
    Tutorial(
        "com.android.contacts",
        "How do I call someone from my address book?",
        "http://truta.ro/licenta/how_to_call_from_address_book.mp4",
        1.0,
        4
    ),
    Tutorial(
        "com.android.dialer",
        "How do I dial a number?",
        "http://truta.ro/licenta/how_to_write_and_call_phone_nr.mp4",
        1.0,
        5
    ),
    Tutorial(
        "com.google.android.apps.maps",
        "How do I rotate the map?",
        "http://truta.ro/licenta/how_to_rotate_map.mp4",
        1.0,
        6
    ),
    Tutorial(
        "com.google.android.apps.maps",
        "How do I search for a place on the map?",
        "http://truta.ro/licenta/how_to_search_map.mp4",
        1.0,
        7
    ),
    Tutorial(
        "com.google.android.apps.maps",
        "How do I navigate the map?",
        "http://truta.ro/licenta/how_to_navigate_map.mp4",
        1.0,
        8
    ),
    Tutorial(
        "com.android.chrome",
        "How do I search on the Internet?",
        "http://truta.ro/licenta/how_to_search_internet.mp4",
        1.0,
        9
    )
)

val applications = mutableListOf(
    Application("com.android.contacts", "Contacts"),
    Application("com.android.dialer", "Phone"),
    Application("com.google.android.apps.messaging", "Messages"),
    Application("com.google.android.apps.maps", "Maps")
)