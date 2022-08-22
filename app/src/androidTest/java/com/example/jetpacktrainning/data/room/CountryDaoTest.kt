package com.example.jetpacktrainning.data.room

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class CountryDaoTest {
    private lateinit var dataBase: CountryDataBase
    private lateinit var dao: CountryDao

    @Before
    fun setup() {
        dataBase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CountryDataBase::class.java
        ).allowMainThreadQueries()
            .build()
        dao = dataBase.countryDao()
    }


    @Test
    fun insertCountryItem() = runTest {
        val country = CountryCacheEntity(continent = "continent 1", code = "77", id = 5, name = "Tunisia")
        dao.insert(country)

        val cacheCountry = dao.getCountryById(5)

        assertThat(cacheCountry).isEqualTo(country)
    }

    @Test
    fun getCountries() = runTest {
        val countries = listOf(
            CountryCacheEntity(continent = "continent 1", code = "77", id = 1, name = "Tunisia"),
            CountryCacheEntity(continent = "continent 3", code = "4", id = 3, name = "Hungary"),
            CountryCacheEntity(continent = "continent 8", code = "12", id = 8, name = "France"),
            CountryCacheEntity(continent = "continent 5", code = "18", id = 5, name = "USA"),
        )
        countries.forEach { dao.insert(it) }

        val cacheCountries = dao.getCountries()

        assertThat(cacheCountries).containsExactlyElementsIn(countries)
    }

    @After
    fun tearDown() {
        dataBase.close()
    }
}
