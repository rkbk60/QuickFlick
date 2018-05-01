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
     * Returns current KeyInfo and list of enabled mod key.
     * After call this method, this class call reset(clearAllModsState = false) oneself.
     * @return Pair of KeyInfo and list of mod key.
     */
    fun output(): Pair<KeyInfo, List<ModKeyInfo>> {
        val result = Pair(keyInfo, mods.toList())
        reset(false)
        return result
    }

    /**
     * Resets keyInfo and each mod key states.
     * @param resetAll if true, all states will be to OFF.
     *                 If false, only keys which are ON will turn off.
     */
    fun reset(resetAll: Boolean) {
        keyInfo = KeyInfo.NULL
        if (resetAll) mods.reset() else mods.resetUnlessLock()
    }
}
