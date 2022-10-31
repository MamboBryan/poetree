package com.mambo

import com.mambo.application.utils.toDate
import com.mambo.application.utils.toDateString
import org.junit.Test
import java.util.Calendar
import kotlin.test.assertEquals

class DateUtilsTest {

    @Test
    fun `given correct date, returns date`() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 8)
        calendar.set(Calendar.MONTH, 11)
        calendar.set(Calendar.YEAR, 2001)

        val date = "08-12-2001".toDate()
        assertEquals(calendar.time.toDateString(), date.toDateString())
    }

    @Test
    fun `given incorrect date, returns null`() {
        val date = "08-18-2001".toDate()
        assertEquals(null, date)
    }

}