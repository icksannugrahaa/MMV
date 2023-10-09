package com.test.dikshatek.mmv.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.test.dikshatek.mmv.utils.GlobalUtils.showToastLong
import com.test.dikshatek.mmv.utils.GlobalUtils.showToastShort

abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {
    protected lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getActivityBinding()
        setContentView(binding.root)
        setUp(savedInstanceState)
    }

    fun showToast(message: String, isShort: Boolean = true) {
        if (isShort)
            showToastShort(this, message)
        else
            showToastLong(this, message)
    }

    abstract fun getActivityBinding(): B
    abstract fun setUp(savedInstanceState: Bundle?)

}