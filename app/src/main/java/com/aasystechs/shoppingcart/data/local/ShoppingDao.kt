package com.aasystechs.shoppingcart.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    @Delete
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    @Query("SELECT * FROM shopping_item")
    fun observAllShoppingItem(): LiveData<List<ShoppingItem>>

    @Query("SELECT SUM (price* amount) from shopping_item")
    fun observeTotalPrice(): LiveData<Float>


}