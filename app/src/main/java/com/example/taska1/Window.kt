package com.localazy.quicknote.windows

import android.content.Context
import android.graphics.PixelFormat
import android.graphics.Point
import android.os.Build
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.example.taska1.R
import com.example.taska1.registerDraggableTouchListener


class Window(context: Context) {

    private val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val rootView = layoutInflater.inflate(R.layout.window, null)

    private val windowParams = WindowManager.LayoutParams(
        0,
        0,
        0,
        0,
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
        PixelFormat.TRANSLUCENT
    )


    private fun getCurrentDisplayMetrics(): DisplayMetrics {
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        return dm
    }


    private fun calculateSizeAndPosition(
        params: WindowManager.LayoutParams,
        widthInDp: Int,
        heightInDp: Int
    ) {
        val dm = getCurrentDisplayMetrics()
        // We have to set gravity for which the calculated position is relative.
        params.gravity = Gravity.TOP or Gravity.LEFT
        params.width = (widthInDp * dm.density).toInt()
        params.height = (heightInDp * dm.density).toInt()
        params.x = 0
        params.y = 0
    }


    private fun initWindowParams() {
        calculateSizeAndPosition(windowParams, 300, 300)
    }


    private fun initWindow() {
        rootView.findViewById<View>(R.id.window_close).setOnClickListener { close() }
        rootView.findViewById<View>(R.id.window_header).registerDraggableTouchListener(
            initialPosition = { Point(windowParams.x, windowParams.y) },
            positionListener = { x, y -> setPosition(x, y) }
        )
    }


    init {
        initWindowParams()
        initWindow()
    }


    fun open() {
        try {
            windowManager.addView(rootView, windowParams)
        } catch (e: Exception) {

        }
    }


    fun close() {
        try {
            windowManager.removeView(rootView)
        } catch (e: Exception) {

        }
    }

    private fun setPosition(x: Int, y: Int){
        windowManager.updateViewLayout(rootView,windowParams)
        windowParams.x = x
        windowParams.y = y



    }

    private fun update(){
        try {
            windowManager.updateViewLayout(rootView,windowParams)
        }catch (_:Exception){

        }
    }

}