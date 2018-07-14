package com.unknown.numee.business.beans

enum class Diagnose {
    AutismSpectrumDisorders,
    ADHD,
    AspergerSyndrome,
    Other
}

data class Child(
        val name: String,
        val age: Int,
        val diagnose: Diagnose
) {
}