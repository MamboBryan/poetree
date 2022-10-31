package com.mambo

import com.mambo.application.utils.isValidEmail
import com.mambo.application.utils.isValidPassword
import org.junit.Test
import kotlin.test.assertEquals

class EmailUtilTest {

    @Test
    fun `validates correct email properly`(){
        val isValid = "poetree@co.ke".isValidEmail()
        assertEquals(true, isValid)
    }

    @Test
    fun `validates incorrect email properly`(){
        val isValid = "poetree@com".isValidPassword()
        assertEquals(false, isValid)
    }

}