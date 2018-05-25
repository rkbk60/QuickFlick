package com.rkbk60.quickflick.domain

import com.rkbk60.quickflick.model.KeyInfo
import com.rkbk60.quickflick.model.ModKeyInfo
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

class RepeatingInputRunner(private val action: (KeyEventOrder) -> Unit) {
    private var order = KeyEventOrder()
    private var job = Job()

    /**
     * Flags to ready to start repeating input.
     */
    var isStandby = false
        private set

    private var isPausing = false

    companion object {
        const val DELAY_TIME = 500    // unit: ms
        const val REPEATING_TIME = 50 // unit: ms
    }

    fun startInput(key: KeyInfo, mods: Set<ModKeyInfo>) {
        if (isStandby || key === KeyInfo.NULL) return
        isStandby = true
        order.changeKeys(key, mods)
        action(order)
        order.changeModKeys(mods.filter { it.lockable }.toSet())
        job = launch {
            delay(DELAY_TIME)
            while (isActive) {
                if (!isPausing) {
                    action(order)
                    delay(REPEATING_TIME)
                }
            }
        }
    }

    fun changeKeyInfo(key: KeyInfo) {
        isPausing = true
        order.changeMainKey(key)
        isPausing = false
    }

    fun stopInput() {
        if (job.isActive) {
            job.cancel()
        }
        isStandby = false
    }

    @Suppress("ProtectedInFinal", "unused")
    protected fun finalize() {
        if (job.isActive) {
            job.cancel()
        }
    }
}