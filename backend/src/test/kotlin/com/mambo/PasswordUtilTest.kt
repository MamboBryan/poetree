package com.mambo

import com.mambo.application.utils.isValidPassword
import org.junit.Test
import kotlin.test.assertEquals

class PasswordUtilTest {

    @Test
    fun `validates correct password properly`(){
        val isValid = "#Password$123".isValidPassword()
        assertEquals(true, isValid, "password is valid")
    }

    @Test
    fun `validates incorrect password correctly`(){
        val isValid = "Password123".isValidPassword()
        assertEquals(false, isValid, "password is invalid")
    }

    @Test
    fun `validates password length correctly`(){
        val isValid = "$123".isValidPassword()
        assertEquals(false, isValid, "password is invalid")
    }

}