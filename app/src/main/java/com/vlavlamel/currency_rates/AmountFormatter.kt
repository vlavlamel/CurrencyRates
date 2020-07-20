package com.vlavlamel.currency_rates

import android.text.Editable
import android.text.Selection
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*
import java.util.Currency

class AmountFormatter {
    val ROUBLE_SYMBOL = "\u20BD"

    private var mNumberFormat: NumberFormat? = null

    private var mSymbols: DecimalFormatSymbols? = null

    private var mPattern: String? = null

    private var mCurrency: Currency? = null

    private var mIsCurrencyBeforeSum: Boolean? = null

    private var mDigitsOnly: Boolean? = false

    private var mCalledBeforeTextChanged = false

    private var mCalledOnTextChanged = false

    private var mNeedDecimalPointCorrection = false

    private var mDecimalDelimeterIndex = -1

    private var mColor = 0

    private var mDelimiterColor = 0

    private var mJustRussia = true

    constructor(color: Int) : this(color, true)

    constructor(color: Int, justRussia: Boolean) {
        mJustRussia = justRussia
        mNumberFormat = if (mJustRussia) {
            NumberFormat.getCurrencyInstance(Locale("ru"))
        } else {
            NumberFormat.getCurrencyInstance()
        }
        mPattern = (mNumberFormat as DecimalFormat?)!!.toLocalizedPattern()
        mColor = color
        mDelimiterColor = color
    }

    fun getSymbols(): DecimalFormatSymbols? {
        if (mSymbols == null) {
            mSymbols = (mNumberFormat as DecimalFormat?)!!.decimalFormatSymbols.apply {
                setDecimalSeparator(',')
                setGroupingSeparator(' ')
            }
        }
        return mSymbols
    }

    private fun getDivisionCharacter(): Char? {
        return getSymbols()!!.patternSeparator
    }

    private fun getDecimalDelimeter(): Char {
        return getSymbols()!!.decimalSeparator
    }

    private fun getGroupingDelimeter(): Char {
        return getSymbols()!!.groupingSeparator
    }

    private fun isCurrencyBefore(): Boolean {
        if (mIsCurrencyBeforeSum == null) {
            val pattern = mPattern!!.substring(
                0,
                if (mPattern!!.indexOf(getDivisionCharacter()!!) == -1) mPattern!!.length else mPattern!!.indexOf(
                    getDivisionCharacter()!!
                )
            )
            mIsCurrencyBeforeSum = pattern.indexOf('\u00A4') < pattern.length / 2
        }
        return mIsCurrencyBeforeSum!!
    }

    private fun getCurrencyDelimeter(): String {
        var offset = -1
        if (isCurrencyBefore()) {
            offset = 1
        }
        val position = mPattern!!.indexOf('\u00A4')
        val currencyDelimeter =
            if (position != -1 && position + offset >= 0 && position + offset < mPattern!!
                    .length
            ) mPattern!![position + offset] else '#'
        return if (mCurrency == null || currencyDelimeter == '#' || currencyDelimeter == '0') "" else " "
    }

    private fun getCurrencySymbol(): String {
        if (mJustRussia) {
            return ROUBLE_SYMBOL
        }
        return if (mCurrency == null) "" else mCurrency!!.symbol
    }

    /*fun getAmount(s: String): Money? {
        val amount = getNumber(s)
        return if (amount == null) {
            null
        } else {
            Money(
                mNumberFormat!!.currency,
                BigDecimal(
                    amount.replace(
                        getSymbols()!!.monetaryDecimalSeparator,
                        '.'
                    )
                )
            )
        }
    }*/

    fun getNumber(s: String): String? {
        val result = StringBuilder()
        var firstNumber = -1
        var lastNumber = -1
        for (i in 0 until s.length) {
            if (Character.isDigit(s[i])) {
                if (firstNumber == -1) {
                    firstNumber = i
                }
                lastNumber = i + 1
            }
        }
        var i = firstNumber
        while (i < lastNumber && firstNumber != -1 && lastNumber != -1) {
            if (Character.isDigit(s[i]) || s[i] == getSymbols()!!.monetaryDecimalSeparator) {
                result.append(s[i])
            }
            i++
        }
        if (result.length != 0) {
            val index = result.indexOf(getSymbols()!!.monetaryDecimalSeparator.toString())
            if (index != -1) {
                result.replace(index, index + 1, ".")
            }
        }
        return if (result.length == 0) {
            null
        } else {
            result.toString()
        }
    }

    private fun getZeroAmount(): String? {
        return "0" + if (mDigitsOnly == null) "" else getDecimalDelimeter().toString() + "00"
    }

    fun beforeTextChanged(
        s: CharSequence,
        start: Int,
        count: Int,
        after: Int
    ) {
        if (!mCalledBeforeTextChanged) {
            mCalledBeforeTextChanged = true
            if (!mDigitsOnly!!) {
                mDecimalDelimeterIndex = s.toString()
                    .lastIndexOf(getDecimalDelimeter())
            }
        }
    }

    fun onTextChanged(
        s: CharSequence,
        start: Int,
        before: Int,
        count: Int
    ) {
        if (!mCalledOnTextChanged) {
            mCalledOnTextChanged = true
            mNeedDecimalPointCorrection = false
            if (!mDigitsOnly!!) {
                if (mCalledBeforeTextChanged) {
                    if (mDecimalDelimeterIndex < 0 || mDecimalDelimeterIndex >= 0 && mDecimalDelimeterIndex >= start && mDecimalDelimeterIndex < start + count //did not have decimal delimeter OR decimal delimeter was deleted
                    ) {
                        //check newly arrived symbols for decimal delimeter symbols.
                        val commaIndex = s.subSequence(start, start + count)
                            .toString()
                            .lastIndexOf(",")
                        val periodIndex = s.subSequence(start, start + count)
                            .toString()
                            .lastIndexOf(".")
                        mDecimalDelimeterIndex = Math.max(commaIndex, periodIndex) + start
                        mNeedDecimalPointCorrection = mDecimalDelimeterIndex >= start
                    }
                }
            }
        }
    }

    fun setJustRussiaFormat(justRussia: Boolean) {
        mJustRussia = justRussia
    }

    fun setDelimiterColor(color: Int) {
        mDelimiterColor = color
    }

    fun format(s: Editable) {
        val isCurrencyBefore = isCurrencyBefore()
        val currencySymbol = getCurrencySymbol()
        val currencyDelimeter = getCurrencyDelimeter()
        val groupDelimeter = getGroupingDelimeter()
        val currencyDelimeterLength = currencyDelimeter.length
        val currencySymbolLength = currencySymbol.length
        var decimalDelimeterPosition = -1
        if (mCalledBeforeTextChanged && mCalledOnTextChanged && mNeedDecimalPointCorrection
            && mDecimalDelimeterIndex > 0
        ) {
            s.replace(
                mDecimalDelimeterIndex, mDecimalDelimeterIndex + 1,
                getDecimalDelimeter().toString()
            )
            mNeedDecimalPointCorrection = false
        }
        if (isCurrencyBefore && s.toString()
                .indexOf(currencySymbol + currencyDelimeter) == 0
        ) {
            s.delete(0, currencyDelimeterLength + currencySymbolLength)
        } else if (!isCurrencyBefore && s.toString()
                .indexOf(currencySymbol + currencyDelimeter) > 0 && s.toString()
                .indexOf(currencySymbol + currencyDelimeter) == s.length - (currencyDelimeterLength + currencySymbolLength)
        ) {
            s.delete(s.length - (currencyDelimeterLength + currencySymbolLength), s.length)
        }
        var firstNumber = -1
        var lastNumber = -1
        for (i in 0 until s.length) {
            if ('0' <= s[i] && s[i] <= '9') {
                if (firstNumber == -1) {
                    firstNumber = i
                }
                lastNumber = i
            }
        }
        if (firstNumber == -1 && lastNumber == -1) {
            // Allowing empty input
            s.clear()
            mCalledBeforeTextChanged = false
            mCalledOnTextChanged = false
            return
        } else {

            // Removing unwanted symbols in the number
            run {
                var i = firstNumber
                while (i <= lastNumber) {
                    if (!('0' <= s[i] && s[i] <= '9') && getDecimalDelimeter() != s[i]
                    ) {
                        s.delete(i, i + 1)
                        i--
                        lastNumber--
                    }
                    i++
                }
            }

            // looking up for the decimal delimeter
            decimalDelimeterPosition = s.toString()
                .indexOf(getDecimalDelimeter())
            if (decimalDelimeterPosition != -1) {

                // case if delimeter is before first number
                if (decimalDelimeterPosition < firstNumber) {
                    s.insert(decimalDelimeterPosition, "0")
                    firstNumber = decimalDelimeterPosition
                    decimalDelimeterPosition++
                    lastNumber++
                }

                // case if delimeter is after last number
                if (decimalDelimeterPosition > lastNumber) {
                    s.delete(lastNumber + 1, decimalDelimeterPosition)
                    lastNumber++
                    decimalDelimeterPosition = lastNumber
                }

                // removing unneded zeros
                while (lastNumber - decimalDelimeterPosition > 2) {
                    s.delete(lastNumber, lastNumber + 1)
                    lastNumber--
                }
                if (mDigitsOnly!!) {
                    while (lastNumber - decimalDelimeterPosition > 0) {
                        s.delete(lastNumber, lastNumber + 1)
                        lastNumber--
                    }
                }

                // increasing number of zeros
                // while (lastNumber - decimalDelimeterPosition < 2) {
                // s.insert(lastNumber + 1, "0");
                // lastNumber++;
                // }

                // removing other delimeters
                var secondDelimeterPosition = s.toString()
                    .indexOf(getDecimalDelimeter(), decimalDelimeterPosition + 1)
                while (secondDelimeterPosition != -1) {
                    s.delete(secondDelimeterPosition, secondDelimeterPosition + 1)
                    if (lastNumber > secondDelimeterPosition) {
                        lastNumber--
                    }
                    secondDelimeterPosition = s.toString()
                        .indexOf(getDecimalDelimeter(), decimalDelimeterPosition + 1)
                }
                if (mDigitsOnly!!) {
                    s.delete(decimalDelimeterPosition, decimalDelimeterPosition + 1)
                    if (lastNumber >= decimalDelimeterPosition) {
                        lastNumber--
                        decimalDelimeterPosition = -1
                    }
                }
            }
            // removing leading zeros
            var limit =
                if (decimalDelimeterPosition == -1) lastNumber else decimalDelimeterPosition
            while (s[firstNumber] == '0' && firstNumber < limit && s[firstNumber + 1] != getDecimalDelimeter()
            ) {
                s.delete(firstNumber, firstNumber + 1)
                if (decimalDelimeterPosition != -1) {
                    decimalDelimeterPosition--
                }
                lastNumber--
                limit = if (decimalDelimeterPosition == -1) lastNumber else decimalDelimeterPosition
            }

            // inserting group delimeters
            limit = if (decimalDelimeterPosition == -1) lastNumber + 1 else decimalDelimeterPosition
            var i = limit - 1
            while (i >= firstNumber) {
                val position = limit - i
                val c = s[i]
                val delimeterIsNow = position % 4 == 0 && position != 0
                if (delimeterIsNow && c != getGroupingDelimeter()) {
                    s.insert(i + 1, getGroupingDelimeter().toString())
                    if (decimalDelimeterPosition != -1) {
                        decimalDelimeterPosition++
                    }
                    lastNumber++
                } else if (!delimeterIsNow && !('0' <= s[i] && s[i] <= '9')) {
                    s.delete(i, i + 1)
                    i++
                    if (decimalDelimeterPosition != -1) {
                        decimalDelimeterPosition--
                    }
                    lastNumber--
                }
                limit =
                    if (decimalDelimeterPosition == -1) lastNumber + 1 else decimalDelimeterPosition
                i--
            }
            if (decimalDelimeterPosition != -1) {
                s.setSpan(
                    ForegroundColorSpan(mDelimiterColor), decimalDelimeterPosition,
                    decimalDelimeterPosition + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
        if ((mCurrency == null || isCurrencyBefore()) && lastNumber + 1 < s.length) {
            s.delete(lastNumber + 1, s.length)
        }
        if (!mJustRussia && mCurrency != null && isCurrencyBefore()) {
            s.delete(0, firstNumber)
            if (decimalDelimeterPosition != -1) {
                decimalDelimeterPosition -= firstNumber
            }
            lastNumber -= firstNumber
            firstNumber = 0
            s.insert(0, mCurrency!!.symbol)
            s.insert(
                mCurrency!!.symbol
                    .length, getCurrencyDelimeter()
            )
            firstNumber += mCurrency!!.symbol
                .length + getCurrencyDelimeter().length
            if (decimalDelimeterPosition != -1) {
                decimalDelimeterPosition += mCurrency!!.symbol
                    .length + getCurrencyDelimeter()
                    .length
            }
            lastNumber += mCurrency!!.symbol
                .length + getCurrencyDelimeter().length
            s.setSpan(
                ForegroundColorSpan(mColor), 0, firstNumber,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        if (!mJustRussia && mCurrency == null || !isCurrencyBefore()) {
            s.delete(0, firstNumber)
            if (decimalDelimeterPosition != -1) {
                decimalDelimeterPosition -= firstNumber
            }
            lastNumber -= firstNumber
            firstNumber = 0
        }
        if (mJustRussia || mCurrency != null && !isCurrencyBefore()) {
            s.delete(lastNumber + 1, s.length)
            s.insert(s.length, getCurrencyDelimeter())
            s.insert(s.length, getCurrencySymbol())
            s.setSpan(
                ForegroundColorSpan(mColor), lastNumber + 1, s.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        var selectionStart = Selection.getSelectionStart(s)
        var selectionEnd = Selection.getSelectionEnd(s)
        val selectionRightLimit =
            Math.max(lastNumber + 1, decimalDelimeterPosition + 1)
        if (selectionStart < firstNumber) {
            selectionStart = firstNumber
        }
        if (selectionStart > selectionEnd) {
            selectionEnd = selectionStart
        }
        if (selectionEnd > lastNumber + 1) {
            selectionEnd = selectionRightLimit
        }
        if (selectionStart > selectionEnd) {
            selectionStart = selectionEnd
        }
        if (selectionStart != firstNumber && s[selectionStart - 1] == getGroupingDelimeter() && selectionStart == selectionEnd
        ) {
            selectionStart--
            selectionEnd--
        }
        Selection.setSelection(s, selectionStart, selectionEnd)
        mCalledBeforeTextChanged = false
        mCalledOnTextChanged = false
    }

    fun getCurrency(): Currency? {
        return mCurrency
    }

    fun setCurrency(currency: Currency?) {
        mNumberFormat!!.currency = currency
        mCurrency = currency
        mSymbols = null
        mIsCurrencyBeforeSum = null
    }
}