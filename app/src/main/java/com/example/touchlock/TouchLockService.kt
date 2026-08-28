package com.example.touchlock

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent

class TouchLockService : AccessibilityService() {

    private var overlayView: View? = null
    private var touchLocked = false

    override fun onServiceConnected() {
        super.onServiceConnected()
        // Обязательно: без этого флага сервис не получит onKeyEvent
        serviceInfo = serviceInfo.apply {
            flags = flags or AccessibilityServiceInfo.FLAG_REQUEST_FILTER_KEY_EVENTS
        }
    }

    override fun onKeyEvent(event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN) {
            when (event.keyCode) {
                KeyEvent.KEYCODE_STAR -> {
                    lockTouch(true)
                    return true
                }
                KeyEvent.KEYCODE_POUND -> {
                    lockTouch(false)
                    return true
                }
            }
        }
        return super.onKeyEvent(event)
    }

    private fun lockTouch(lock: Boolean) {
        if (lock == touchLocked) return
        touchLocked = lock
        val wm = getSystemService(WINDOW_SERVICE) as WindowManager

        if (lock) {
            val view = View(this)
            val params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            )
            params.gravity = Gravity.TOP or Gravity.START
            wm.addView(view, params)
            overlayView = view
        } else {
            overlayView?.let { wm.removeView(it) }
            overlayView = null
        }
    }

    override fun onInterrupt() {}
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {}
}
