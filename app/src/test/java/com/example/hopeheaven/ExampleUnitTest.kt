package com.example.hopeheaven

import com.example.hopeheaven.databinding.ActivityMakeDonationBinding
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {


    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testEmailValidation() {
        val makeDonation = MakeDonation()

        makeDonation.email.setText("testgmail.com")
        makeDonation.saveDonationData()

        assertNotNull(makeDonation.email.error)
        assertEquals("please enter valid email", makeDonation.email.error.toString())
    }

    @Test
    fun testPhoneNumberValidation() {
        lateinit var binding: ActivityMakeDonationBinding
        val makeDonation = MakeDonation()

        binding.phone.setText("123456") // Invalid phone number
        makeDonation.saveDonationData()

        assertNotNull(binding.phone.error)
        assertEquals("please enter valid phone number", binding.phone.error.toString())
    }


}