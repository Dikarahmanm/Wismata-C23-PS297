package com.dika.wismata.ui.component

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.dika.wismata.R


class CustomEmailEdt : AppCompatEditText{
    private lateinit var emailIcon: Drawable

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")

        emailIcon = ContextCompat.getDrawable(context, R.drawable.ic_email_24) as Drawable
        compoundDrawablePadding = 16

        addTextChangedListener(onTextChanged = {
            text, _, _, _ ->
            if (!text.isNullOrEmpty()) {
                if (!text.toString().matches(emailPattern)) {
                    error = context.getString(R.string.invalid_email)
                }
            }
        })
    }

    private fun setButtonDrawables(
        startOfTheText: Drawable? = null,
        topOfTheText: Drawable? = null,
        endOfTheText: Drawable? = null,
        bottomOfTheText: Drawable? = null
    ) {
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        setButtonDrawables(startOfTheText = emailIcon)
    }
}

