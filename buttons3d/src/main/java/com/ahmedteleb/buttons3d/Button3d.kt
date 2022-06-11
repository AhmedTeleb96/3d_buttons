package com.ahmedteleb.buttons3d

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatButton

/**
 * Created by Ahmed Teleb on 11/6/2020.
 */
class Button3d : AppCompatButton, View.OnTouchListener{


    //Custom values
    private var isShadowEnabled = true
    private var mButtonColor = 0
    private var mShadowColor = 0
    private var mShadowHeight = 0
    private var mCornerRadius = 0

    //Native values
    private var mPaddingLeft = 0
    private var mPaddingRight = 0
    private var mPaddingTop = 0
    private var mPaddingBottom = 0

    //Background drawable
    private var pressedDrawable: Drawable? = null
    private var unpressedDrawable: Drawable? = null

    var isShadowColorDefined = false

    constructor(context: Context) : super(context) {
        init()
        setOnTouchListener(this)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
        parseAttrs(context, attrs)
        setOnTouchListener(this)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init()
        parseAttrs(context, attrs)
        setOnTouchListener(this)
    }


    override fun onFinishInflate() {
        super.onFinishInflate()
        //Update background color
        refresh()
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                updateBackground(pressedDrawable)
                setPadding(mPaddingLeft, mPaddingTop + mShadowHeight, mPaddingRight, mPaddingBottom)
            }
            MotionEvent.ACTION_MOVE -> {
                val r = Rect()
                view.getLocalVisibleRect(r)
                if (!r.contains(motionEvent.x.toInt(), motionEvent.y.toInt() + 3 * mShadowHeight) &&
                    !r.contains(motionEvent.x.toInt(), motionEvent.y.toInt() - 3 * mShadowHeight)
                ) {
                    updateBackground(unpressedDrawable)
                    setPadding(
                        mPaddingLeft,
                        mPaddingTop + mShadowHeight,
                        mPaddingRight,
                        mPaddingBottom + mShadowHeight
                    )
                }
            }
            MotionEvent.ACTION_OUTSIDE, MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                updateBackground(unpressedDrawable)
                setPadding(
                    mPaddingLeft,
                    mPaddingTop + mShadowHeight,
                    mPaddingRight,
                    mPaddingBottom + mShadowHeight
                )
            }
        }
        return false
    }

    private fun init() {
        //Init default values
        isShadowEnabled = true
        val resources = resources ?: return
        mButtonColor = resources.getColor(R.color.button3d_default_color)
        mShadowColor = resources.getColor(R.color.button3d_default_shadow_color)
        mShadowHeight = resources.getDimensionPixelSize(R.dimen.button3d_default_shadow_height)
        mCornerRadius = resources.getDimensionPixelSize(R.dimen.button3d_default_conner_radius)
        gravity = Gravity.CENTER
    }

    @SuppressLint("ResourceType")
    private fun parseAttrs(context: Context, attrs: AttributeSet) {
        //Load from custom attributes
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.Button3d) ?: return
        for (i in 0 until typedArray.indexCount) {
            when (val attr = typedArray.getIndex(i)) {
                R.styleable.Button3d_shadowEnabled -> {
                    isShadowEnabled = typedArray.getBoolean(attr, true) //Default is true
                }
                R.styleable.Button3d_buttonColor -> {
                    mButtonColor = typedArray.getColor(attr,resources.getColor( R.color.button3d_default_color))
                }
                R.styleable.Button3d_shadowColor -> {
                    mShadowColor = typedArray.getColor(attr, resources.getColor(R.color.button3d_default_shadow_color))
                    isShadowColorDefined = true
                }
                R.styleable.Button3d_shadowHeight -> {
                    mShadowHeight =
                        typedArray.getDimensionPixelSize(attr, R.dimen.button3d_default_shadow_height)
                }
                R.styleable.Button3d_cornerRadius -> {
                    mCornerRadius =
                        typedArray.getDimensionPixelSize(attr, R.dimen.button3d_default_conner_radius)
                }
            }
        }
        typedArray.recycle()

        //Get paddingLeft, paddingRight
        val attrsArray = intArrayOf(
            android.R.attr.paddingLeft,  // 0
            android.R.attr.paddingRight, // 1
        )
        val ta = context.obtainStyledAttributes(attrs, attrsArray) ?: return
        mPaddingLeft = ta.getDimensionPixelSize(0, 0)
        mPaddingRight = ta.getDimensionPixelSize(1, 0)
        ta.recycle()

        //Get paddingTop, paddingBottom
        val attrsArray2 = intArrayOf(
            android.R.attr.paddingTop,  // 0
            android.R.attr.paddingBottom, // 1
        )
        val ta1 = context.obtainStyledAttributes(attrs, attrsArray2) ?: return
        mPaddingTop = ta1.getDimensionPixelSize(0, 0)
        mPaddingBottom = ta1.getDimensionPixelSize(1, 0)
        ta1.recycle()
    }

    private fun refresh() {
        val alpha = Color.alpha(mButtonColor)
        val hsv = FloatArray(3)
        Color.colorToHSV(mButtonColor, hsv)
        hsv[2] *= 0.8f // value component
        //if shadow color was not defined, generate shadow color = 80% brightness
        if (!isShadowColorDefined) {
            mShadowColor = Color.HSVToColor(alpha, hsv)
        }
        //Create pressed background and unpressed background drawables
        if (this.isEnabled) {
            if (isShadowEnabled) {
                pressedDrawable = createDrawable(mCornerRadius, Color.TRANSPARENT, mButtonColor)
                unpressedDrawable = createDrawable(mCornerRadius, mButtonColor, mShadowColor)
            } else {
                mShadowHeight = 0
                pressedDrawable = createDrawable(mCornerRadius, mShadowColor, Color.TRANSPARENT)
                unpressedDrawable = createDrawable(mCornerRadius, mButtonColor, Color.TRANSPARENT)
            }
        } else {
            Color.colorToHSV(mButtonColor, hsv)
            hsv[1] *= 0.25f // saturation component
            mShadowColor = Color.HSVToColor(alpha, hsv)
            val disabledColor: Int = mShadowColor
            // Disabled button does not have shadow
            pressedDrawable = createDrawable(mCornerRadius, disabledColor, Color.TRANSPARENT)
            unpressedDrawable = createDrawable(mCornerRadius, disabledColor, Color.TRANSPARENT)
        }
        updateBackground(unpressedDrawable)
        //Set padding
        setPadding(
            mPaddingLeft,
            mPaddingTop + mShadowHeight,
            mPaddingRight,
            mPaddingBottom + mShadowHeight
        )
    }

    private fun updateBackground(background: Drawable?) {
        if (background == null) return
        //Set button background
        this.background = background
    }

    private fun createDrawable(radius: Int, topColor: Int, bottomColor: Int): LayerDrawable {
        val outerRadius = floatArrayOf(
            radius.toFloat(),
            radius.toFloat(),
            radius.toFloat(),
            radius.toFloat(),
            radius.toFloat(),
            radius.toFloat(),
            radius.toFloat(),
            radius.toFloat()
        )

        //Top
        val topRoundRect = RoundRectShape(outerRadius, null, null)
        val topShapeDrawable = ShapeDrawable(topRoundRect)
        topShapeDrawable.paint.color = topColor
        //Bottom
        val roundRectShape = RoundRectShape(outerRadius, null, null)
        val bottomShapeDrawable = ShapeDrawable(roundRectShape)
        bottomShapeDrawable.paint.color = bottomColor
        //Create array
        val drawArray = arrayOf<Drawable>(bottomShapeDrawable, topShapeDrawable)
        val layerDrawable = LayerDrawable(drawArray)

        //Set shadow height
        if (isShadowEnabled && topColor != Color.TRANSPARENT) {
            //unpressed drawable
            layerDrawable.setLayerInset(0, 0, 0, 0, 0) /*index, left, top, right, bottom*/
        } else {
            //pressed drawable
            layerDrawable.setLayerInset(
                0,
                0,
                mShadowHeight,
                0,
                0
            ) /*index, left, top, right, bottom*/
        }
        layerDrawable.setLayerInset(1, 0, 0, 0, mShadowHeight) /*index, left, top, right, bottom*/
        return layerDrawable
    }

    //Setter
    fun setShadowEnabled(isShadowEnabled: Boolean) {
        this.isShadowEnabled = isShadowEnabled
        setShadowHeight(0)
        refresh()
    }

    fun setButtonColor(buttonColor: Int) {
        this.mButtonColor = buttonColor
        refresh()
    }

    fun setShadowColor(shadowColor: Int) {
        this.mShadowColor = shadowColor
        isShadowColorDefined = true
        refresh()
    }

    fun setShadowHeight(shadowHeight: Int) {
        this.mShadowHeight = shadowHeight
        refresh()
    }

    fun setCornerRadius(cornerRadius: Int) {
        this.mCornerRadius = cornerRadius
        refresh()
    }

    fun set3dButtonPadding(left: Int, top: Int, right: Int, bottom: Int) {
        mPaddingLeft = left
        mPaddingRight = right
        mPaddingTop = top
        mPaddingBottom = bottom
        refresh()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        refresh()
    }

    //Getter
    fun isShadowEnabled(): Boolean {
        return isShadowEnabled
    }

    fun getButtonColor(): Int {
        return mButtonColor
    }

    override fun getShadowColor(): Int {
        return mShadowColor
    }

    fun getShadowHeight(): Int {
        return mShadowHeight
    }

    fun getCornerRadius(): Int {
        return mCornerRadius
    }

}