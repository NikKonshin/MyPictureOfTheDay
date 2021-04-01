package com.example.mypictureoftheday.model.entity

import java.util.*

private const val TODAY = "today"
private const val YESTERDAY = "YESTERDAY"
private const val MY_DAY_BEFORE_YESTERDAY = "my day before yesterday"

sealed class MyDate {

    class MyToday {
        companion object {
            private val calendar = Calendar.getInstance().apply {
                this.time = Date()
            }

            val id = TODAY
            val year = init(Calendar.YEAR)

            val month = init(Calendar.MONTH)

            val day = init(Calendar.DAY_OF_MONTH)

            val dateString = "$year-${month}-${day}"

            private fun init(value: Int, day: Int = 0): String {
                return if (calendar.get(value) - day < 10) {
                    "0${calendar.get(value)}"
                } else {
                    (calendar.get(value) - day).toString()
                }
            }
        }
    }

    class MyYesterday {
        companion object {
            private val calendar = Calendar.getInstance().apply {
                this.time = Date()
            }

            private var dayTest = init(Calendar.DAY_OF_MONTH, 1)
            private var monthTest = init(Calendar.MONTH)

            val id = YESTERDAY

            val year = init(Calendar.YEAR)

            val month = if (dayTest.toInt() <= 0) {
                init(monthTest.toInt() - 1)
            } else {
                monthTest
            }

            val day = if (dayTest.toInt() <= 0) {
                "01"
            } else {
                dayTest
            }

            val dateString = "${year}-${month}-${day}"

            private fun init(value: Int, day: Int = 0): String {
                return if (calendar.get(value) - day < 10) {
                    "0${calendar.get(value)}"
                } else {
                    (calendar.get(value) - day).toString()
                }
            }
        }
    }

    class MyDayBeforeYesterday {
        companion object {
            private val calendar = Calendar.getInstance().apply {
                this.time = Date()
            }
            private var dayTest = init(Calendar.DAY_OF_MONTH, 2)
            private var monthTest = init(Calendar.MONTH)

            val id = MY_DAY_BEFORE_YESTERDAY


            val year = init(Calendar.YEAR)

            val month = if (dayTest.toInt() <= 0) {
                init(monthTest.toInt() - 1)
            } else {
                monthTest
            }

            val day = if (dayTest.toInt() <= 0) {
                "01"
            } else {
                dayTest
            }

            val dateString = "${year}-${month}-${day}"

            private fun init(value: Int, day: Int = 0): String {
                return if (calendar.get(value) - day < 10) {
                    "0${calendar.get(value)}"
                } else {
                    (calendar.get(value) - day).toString()
                }
            }
        }

    }
}
