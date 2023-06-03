package com.dika.wismata.ui.component

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.dika.wismata.R

class CustomButton : AppCompatButton {
    private lateinit var enableBackground: Drawable
    private lateinit var disableBackground: Drawable
    private var txtColor: Int = 0

    constructor(Context: Context) : super(Context) {
        init()
    }

    constructor(Context: Context, attributeSet: AttributeSet) : super(Context, attributeSet) {
        init()
    }

    constructor(Context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        Context,
        attributeSet,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        txtColor = ContextCompat.getColor(context, android.R.color.background_light)
        enableBackground =
            ContextCompat.getDrawable(context, R.drawable.bg_button_enable) as Drawable
        disableBackground = ContextCompat.getDrawable(context, R.drawable.bg_button_disable) as Drawable
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        background = if (isEnabled) enableBackground else disableBackground

        setTextColor(txtColor)
        gravity = Gravity.CENTER
    }
}