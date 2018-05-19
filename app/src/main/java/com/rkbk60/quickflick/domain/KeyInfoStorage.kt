package com.rkbk60.quickflick.domain

import com.rkbk60.quickflick.model.KeyInfo
import com.rkbk60.quickflick.model.ModKeyInfo

/**
 * Model to stock and update key information.
 */
class KeyInfoStorage {
    /**
     * Stocked KeyInfo object.
     * This property will be KeyInfo.NULL, AsciiKeyInfo, or TriggerKeyInfo.
     */
    private var keyInfo: KeyInfo = KeyInfo.NULL

    /**
     * Class to update and manage mod key states.
     */
    private val mods = ModKeyStorage()

    /**
     * Stocks KeyInfo.
     * @param key any KeyInfo object
     */
    fun input(key: KeyInfo) = when (key) {
        is ModKeyInfo -> mods.update(key)
        else -> {
            this.keyInfo = key
            key.mods.forEach { this@KeyInfoStorage.mods.update(it, isSubMod = true) }
        }
    }

    /**
     * Returns current KeyInfo and set of enabled mod key.
     * After call this method, inner [keyInfo] is changed to KeyInfo.NULL
     * and modifier key states are turned off unless that LOCK.
     * @return Pair of KeyInfo and set of mod key.
     */
    fun output(): Pair<KeyInfo, Set<ModKeyInfo>> {
        val result = Pair(keyInfo, mods.toSet())
        keyInfo = KeyInfo.NULL
        mods.resetUnlessLock()
        return result
    }

    /**
     * Resets keyInfo and each modifier key states to OFF.
     */
    fun resetUnlessLock() {
        keyInfo = KeyInfo.NULL
        mods.resetUnlessLock()
    }

    /**
     * Resets keyInfo and each modifier key states to OFF.
     */
    fun reset() {
        keyInfo = KeyInfo.NULL
        mods.reset()
    }
}
