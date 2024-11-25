package com.example.bottomnavigationviewapp.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Build
import android.util.AttributeSet
import androidx.annotation.RequiresApi
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.math.sqrt

/**
 * 文件名: BottomNavView
 * 作者: zxl
 * 创建日期：2024/11/25  9:59
 * 描述: TODO
 */
class BottomNavView: BottomNavigationView {

    constructor(context: Context): super(context)

    constructor(context: Context,attrs: AttributeSet): super(context,attrs)

    private val paint by lazy {
        Paint().apply {
            isAntiAlias = true
            color = Color.parseColor("#ffcecece")
            strokeWidth = 5F
            style = Paint.Style.STROKE
        }
    }

    private var distance = 50F
    private val radiusCorner = 30F
    private val radiusCentral: Float
        get() = radiusCorner + 2 * distance

    /**
     * 这里的width代表的是外部设置的，比如layout_width
     */
    private val circleCenter: Pair<Float,Float>
        get() = (width.toFloat() / 2)to -distance

    /**
     * 更新距离
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun updateDIstance(d: Float, canvas: Canvas){
        distance = 50F - d
        this.draw(canvas)
        this.invalidate()
    }

    private fun drawBackground(canvas: Canvas){
        val leftCenter = (circleCenter.first - sqrt(3f) * (radiusCorner + distance)) to radiusCorner
        val rightCenter = (circleCenter.first + sqrt(3f) * (radiusCorner + distance)) to radiusCorner
        val bgPath = Path().apply {
            moveTo(0f,0f)
            if (distance >= -10f){
                lineTo(leftCenter.first,0f)
                arcTo(
                    leftCenter.first - radiusCorner,
                    0f,
                    leftCenter.first + radiusCorner,
                    2 * radiusCorner,
                    -90f,60f,true
                )
                arcTo(
                    circleCenter.first - radiusCentral,
                    circleCenter.second - radiusCentral,
                    circleCenter.first + radiusCentral,
                    circleCenter.second + radiusCentral,
                    150f, -120f,true
                )
                arcTo(
                    rightCenter.first - radiusCorner,
                    0f,
                    rightCenter.first + radiusCorner,
                    2 * radiusCorner,
                    -150f, 60f,true
                )
                lineTo(width.toFloat(),0f)
            }else{
                lineTo(width.toFloat(),0f)
            }
        }
        canvas.apply {
            save()
            drawPath(bgPath,paint)
            restore()
        }
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        canvas.let {
            drawBackground(canvas)
        }
    }


}