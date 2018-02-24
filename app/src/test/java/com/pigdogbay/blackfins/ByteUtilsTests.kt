package com.pigdogbay.blackfins

import com.pigdogbay.blackfins.utils.convertBinaryCodedDecimal
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Test

import org.junit.Assert.*

/**
 * Created by mark on 24/02/18.
 * Tests for ByteUtils
 */
class ByteUtilsTests {

    @Test
    fun convertBinaryCodedDecimal1(){
        val actual = convertBinaryCodedDecimal(0x1234)
        assertThat(actual,equalTo(1234))
    }
}