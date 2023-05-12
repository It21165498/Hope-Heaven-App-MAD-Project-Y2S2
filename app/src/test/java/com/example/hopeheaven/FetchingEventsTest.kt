package com.example.hopeheaven


import android.view.View
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FetchingEventsTest {

    private lateinit var fetchingEvents: FetchingEvents

    @Before
    fun setUp() {
        fetchingEvents = FetchingEvents()
    }

    @Test
    fun testGetEvents() {
        fetchingEvents.getEvents()

        assertEquals(fetchingEvents.ueRecyclerView.visibility, View.GONE)

        assertEquals(fetchingEvents.tvLoadingData.visibility, View.VISIBLE)
    }



    @Test
    fun testFilterData() {
        val eventList = arrayListOf(
            EventModel("1", "Location 1", "2023-05-07", "08:00", "Description 1", "Topic 1"),
            EventModel("2", "Location 2", "2023-05-08", "09:00", "Description 2", "Topic 2")
        )

        fetchingEvents.upEventArrayList = eventList

        fetchingEvents.filterData("2")

        assertEquals(fetchingEvents.ueRecyclerView.adapter?.itemCount, 1)

        assertEquals(fetchingEvents.upEventArrayList[0].topic, "Topic 2")
    }
}








