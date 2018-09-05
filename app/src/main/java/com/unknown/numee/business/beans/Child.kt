package com.unknown.numee.business.beans

enum class Diagnose {
    AutismSpectrumDisorders,
    ADHD,
    AspergerSyndrome,
    Other
}

enum class CanSpeak {
    Yes,
    No,
    Somewhat,
    Other
}

enum class IQLevel {
    IQ_140,
    IQ_120_140
}

data class Child(
        var name: String = "",
        var age: Int = 10,
        var diagnose: String = "",
        var canSpeak: String = "",
        var IQLevel: String = "",
        var independenceLevel: String = "",
        var totalNumCount: Int = 0
)