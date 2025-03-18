package com.upayments.starpayapp.state.enums

enum class Genders(val rawValue: String) {
    MALE("3"),
    FEMALE("4"),
    OTHER("6");

    val id: String
        get() = rawValue

    val label: String
        get() = when (this) {
            MALE -> "Masculino"
            FEMALE -> "Femenino"
            OTHER -> "Otro"
        }

    companion object {
        fun fromRawValue(value: String): Genders {
            return entries.find { it.rawValue == value } ?: OTHER
        }
    }
}

enum class MaritalStatuses(val rawValue: String) {
    SINGLE("7"),
    MARRIED("8"),
    DIVORCED("9"),
    WIDOW("10");

    val id: String
        get() = rawValue

    val label: String
        get() = when (this) {
            SINGLE -> "Soltero(a)"
            MARRIED -> "Casado(a)"
            DIVORCED -> "Divorciado(a)"
            WIDOW -> "Viudo(a)"
        }

    companion object {
        fun fromRawValue(value: String): MaritalStatuses {
            return entries.find { it.rawValue == value } ?: SINGLE
        }
    }
}