package com.suslovalex.newsservice.viewmodel

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import com.suslovalex.newsservice.model.News
import com.suslovalex.newsservice.repository.NewsRepository
import junit.framework.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class NewsViewModelTest {

    @Mock
    lateinit var mockRepository: NewsRepository
    var newsLiveData: MutableLiveData<News> = MutableLiveData()

    @Mock
    lateinit var mockNews: News

    lateinit var newsViewModel: NewsViewModel

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        val applicationMock = ApplicationProvider.getApplicationContext<Context>()
        newsViewModel = NewsViewModel(applicationMock as Application)
    }

    @Test
    fun `fetch news`() {
        newsLiveData.value = mockNews
        `when`(mockRepository.getAllNews()).thenReturn(newsLiveData)
        assertEquals(mockNews, mockRepository.getAllNews().value)
    }

    @Test
    fun `fetch null`() {
        newsLiveData.value = null
        `when`(mockRepository.getAllNews()).thenReturn(newsLiveData)
        assertEquals(null, mockRepository.getAllNews().value)
    }
}