package com.aasystechs.shoppingcart.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.aasystechs.shoppingcart.getOrAwaitValueTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)  // is used to run kotlin code
@SmallTest   //-> Unit test
//@MediumTest  // -> LOcal db testing and no network API calls
// @LargeTest
class ShoppingDaoTest {

    // instant task executor rule
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ShoppingItemDatabase
    private lateinit var daoObj : ShoppingDao;

    @Before
    fun setUp(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingItemDatabase::class.java
        ).allowMainThreadQueries().build()

        daoObj = database.shoppingDao()
    }

    @Test
    fun insertShoppingItem() = runTest {
        val shppingItem = ShoppingItem("Ajay" , 1,1f,"url" , id =1)
        daoObj.insertShoppingItem(shppingItem)
        val allShoppingItem = daoObj.observAllShoppingItem().getOrAwaitValueTest()
        assertThat(allShoppingItem).contains(shppingItem)
    }

    @Test
    fun deleteItemShopping()= runTest {
        val shppingItem = ShoppingItem("Ajay" , 1,1f,"url" , id =1)
        daoObj.insertShoppingItem(shppingItem)
        daoObj.deleteShoppingItem(shppingItem)
        val allShoppingItem = daoObj.observAllShoppingItem().getOrAwaitValueTest()
        assertThat(allShoppingItem).doesNotContain(shppingItem)
    }

    @Test
    fun observerTotalPriceSum() =runTest {
        val shppingItem1 = ShoppingItem("Ajay", 2, 10.0f, "url", id = 1)
        val shppingItem2 = ShoppingItem("Ajay2", 4, 5.5f, "url", id = 2)
        val shppingItem3 = ShoppingItem("Ajay3", 1, 100f, "url", id = 3)
        daoObj.insertShoppingItem(shppingItem1)
        daoObj.insertShoppingItem(shppingItem2)
        daoObj.insertShoppingItem(shppingItem3)

        val totalPriceSum = daoObj.observeTotalPrice().getOrAwaitValueTest()
        assertThat(totalPriceSum).isEqualTo(20.0f+4*5.5f+100f)
    }
}