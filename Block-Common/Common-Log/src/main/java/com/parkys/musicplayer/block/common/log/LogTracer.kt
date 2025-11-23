package com.parkys.musicplayer.block.common.log

object LogTracer {

    private val logger = Logger()

    fun i(tag: String? = null, trace: () -> String) = logger.info(tag, trace)
    fun d(tag: String? = null, trace: () -> String) = logger.debug(tag, trace)
    fun v(tag: String? = null, trace: () -> String) = logger.verbose(tag, trace)
    fun w(tag: String? = null, trace: () -> String) = logger.warn(tag, trace)
    fun e(tag: String? = null, trace: () -> String) = logger.error(tag, trace)
}