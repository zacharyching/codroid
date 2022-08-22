/*
 *      Copyright (c) 2022 Zachary. All rights reserved.
 *
 *      This file is part of Codroid.
 *
 *      Codroid is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *
 *      Codroid is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with Codroid.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package org.codroid.editor.decoration

import android.graphics.*
import android.util.Log
import org.codroid.editor.LineAnchor
import org.codroid.editor.graphics.TextPaint
import org.codroid.textmate.theme.FontStyle
import kotlin.experimental.and

/**
 * It only changes the style of the paint.
 */
class CharacterSpan : RepaintSpan, BackgroundSpan {

    private var mTypeface: Typeface? = null
    private var mTextColor = 0
    private var mFontStyle: FontStyle = -1
    private var mBackground = 0

    private val mPaint = TextPaint()

    override fun onRepaint(origin: TextPaint): TextPaint {
        if (isBold(mFontStyle) && isItalic(mFontStyle)) {
            mPaint.typeface = Typeface.create(origin.typeface, Typeface.BOLD_ITALIC)
        } else if (isItalic(mFontStyle)) {
            mPaint.typeface = Typeface.create(origin.typeface, Typeface.ITALIC)
        } else if (isBold(mFontStyle)) {
            mPaint.typeface = Typeface.create(origin.typeface, Typeface.BOLD)
        } else {
            mPaint.typeface = origin.typeface
        }
        if (mTextColor != 0) {
            mPaint.color = mTextColor
        }
        return mPaint
    }

    override fun onDraw(canvas: Canvas, rect: SpanRect) {
        if (mBackground != 0) {
            canvas.drawRect(
                rect.left,
                rect.top,
                rect.right,
                rect.bottom,
                mPaint.withColor(mBackground)
            )
        }
        if (isUnderline(mFontStyle)) {
            mPaint.strokeWidth = 3F
            canvas.drawLine(
                rect.left,
                rect.bottom,
                rect.right,
                rect.bottom,
                mPaint.withBlackColor()
            )
        }

        if (isStrikethrough(mFontStyle)) {
            mPaint.strokeWidth = 3F
            canvas.drawLine(
                rect.left,
                rect.centerY(),
                rect.right,
                rect.centerY(),
                mPaint.withBlackColor()
            )
        }

    }

    fun setTypeface(typeface: Typeface): CharacterSpan {
        mTypeface = typeface
        return this
    }

    fun setTextColor(color: Int): CharacterSpan {
        mTextColor = color
        return this
    }

    fun setFontStyle(style: FontStyle): CharacterSpan {
        mFontStyle = style
        return this
    }

    fun setBackground(color: Int): CharacterSpan {
        mBackground = color
        return this
    }

    private fun isItalic(style: FontStyle): Boolean =
        0b00000001.toByte() and style != 0.toByte()

    private fun isBold(style: FontStyle) =
        0b00000010.toByte() and style != 0.toByte()

    private fun isUnderline(style: FontStyle) =
        0b00000100.toByte() and style != 0.toByte()

    private fun isStrikethrough(style: FontStyle) =
        0b00001000.toByte() and style != 0.toByte()

}