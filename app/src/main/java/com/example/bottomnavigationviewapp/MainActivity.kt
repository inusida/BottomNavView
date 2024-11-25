package com.example.bottomnavigationviewapp

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Canvas
import android.os.Build
import android.view.LayoutInflater
import androidx.annotation.RequiresApi
import androidx.core.animation.addListener
import androidx.core.animation.doOnEnd
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bottomnavigationviewapp.databinding.ActivityMainBinding
import com.example.bottomnavigationviewapp.ui.base.BaseActivity
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.math.abs

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun initNavigation(){
        val navView: BottomNavigationView = viewBinding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initView() {
        viewBinding.apply {
            fab.setOnClickListener {
                onFabClick()
            }
        }
    }

    lateinit var updateJob: Job

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onFabClick(){
        val objectAnimation = ObjectAnimator.ofFloat(
            viewBinding.fab,
            "translationY",
            0F,
            -viewBinding.fab.height.toFloat() + 30f,
            0F
        ).apply {
            duration = 2000
            repeatMode = ValueAnimator.REVERSE
            addUpdateListener {
                updateJob = lifecycleScope.launch {
                    viewBinding.navView.updateDIstance(abs(it.animatedValue as Float), Canvas())
                }
            }
            addListener(onEnd = {
                updateJob.cancel()
            })
        }
        objectAnimation.start()
    }
}