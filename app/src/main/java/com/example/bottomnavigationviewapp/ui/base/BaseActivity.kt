package com.example.bottomnavigationviewapp.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
/**
 * 文件名: BaseActivity
 * 作者: zxl
 * 创建日期：2024/11/25  9:24
 * 描述: TODO
 */
abstract class BaseActivity<VB: ViewBinding>: AppCompatActivity() {

    lateinit var viewBinding: VB
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = getViewBinding(layoutInflater)
        setContentView(viewBinding.root)
        initNavigation()
        initView()
    }

    abstract fun getViewBinding(layoutInflater: LayoutInflater): VB

    abstract fun initNavigation()

    abstract fun initView()
}