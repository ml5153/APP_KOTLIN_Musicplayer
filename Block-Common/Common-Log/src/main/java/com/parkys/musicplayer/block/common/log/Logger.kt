package com.parkys.musicplayer.block.common.log

import android.util.Log

internal class Logger() {

    private enum class LEVEL { VERBOSE, DEBUG, INFO, WARN, ERROR }

    fun info(tag: String? = null, trace: () -> String) = log(LEVEL.INFO, tag, trace)
    fun debug(tag: String? = null, trace: () -> String) = log(LEVEL.DEBUG, tag, trace)
    fun verbose(tag: String? = null, trace: () -> String) = log(LEVEL.VERBOSE, tag, trace)
    fun warn(tag: String? = null, trace: () -> String) = log(LEVEL.WARN, tag, trace)
    fun error(tag: String? = null, trace: () -> String) = log(LEVEL.ERROR, tag, trace)

    private fun log(level: LEVEL, tag: String?, trace: () -> String) {
        if (!BuildConfig.DEBUG) return

        val logTag = tag ?: "Logger"
        val logMsg = trace()

        when (level) {
            LEVEL.VERBOSE -> Log.v(logTag, logMsg)
            LEVEL.DEBUG -> Log.d(logTag, logMsg)
            LEVEL.INFO -> Log.i(logTag, logMsg)
            LEVEL.WARN -> Log.w(logTag, logMsg)
            LEVEL.ERROR -> Log.e(logTag, logMsg)
        }
    }
}