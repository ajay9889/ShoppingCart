package com.aasystechs.shoppingcart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType
//typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) ->T
//abstract class BaseActivity<binding: ViewBinding> (private val inflater: Inflate<binding>) : AppCompatActivity() {
//    _binding = inflate.invoke(inflater, container, false)
//
//}


abstract class BaseActivity<VM: ViewModel , binding: ViewBinding> (val bindingFactory : (LayoutInflater) ->binding) : AppCompatActivity() {

    private lateinit var _vm: VM
    private lateinit var _binding: binding

    protected val binding get()= _binding
    protected val viewModel get()= _vm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingFactory(layoutInflater)
        _vm = ViewModelProvider(this).get(getViewModelClass())
        setContentView(_binding.root)
    }

    private fun getViewModelClass(): Class<VM> {
        val type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        return type as Class<VM>
    }
}
