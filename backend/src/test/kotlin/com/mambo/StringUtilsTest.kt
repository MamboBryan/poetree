package com.mambo

import com.mambo.application.utils.isValidUrl
import org.junit.Test
import kotlin.test.assertEquals

class StringUtilsTest {

    @Test
    fun `given valid url returns true`(){
        val url = "http://poetree.art"
        assertEquals(true, url.isValidUrl())
    }

    @Test
    fun `given invalid url with space returns false`(){
        val url = "http:// poetree.art"
        assertEquals(false, url.isValidUrl())
    }

}
