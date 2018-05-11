package com.rkbk60.quickflick.domain

import com.rkbk60.quickflick.model.ModKeyInfo

/**
 * Modifier keys storage model.
 * This class also manage current each modifier keys states.
 */
class ModKeyStorage {
    /**
     * Mod key mode.
     */
    private enum class State { OFF, ON, LOCK }

    /**
     * Current mod keys states.
     */
    private val states = mutableMapOf(
            ModKeyInfo.CTRL  to State.OFF,
            ModKeyInfo.ALT   to State.OFF,
            ModKeyInfo.META  to State.OFF,
            ModKeyInfo.SHIFT to State.OFF
    )

    /**
     * Update mod key mode.
     * @param mod mod key object
     * @param isSubMod when [mod] parameter is KeyInfo.mods element, this will be true.
     */
    fun update(mod: ModKeyInfo, isSubMod: Boolean = false) {
        val key = when (mod) {
            ModKeyInfo.CTRL,
            ModKeyInfo.CTRL_LOCK  -> ModKeyInfo.CTRL
            ModKeyInfo.ALT,
            ModKeyInfo.ALT_LOCK   -> ModKeyInfo.ALT
            ModKeyInfo.META,
            ModKeyInfo.META_LOCK  -> ModKeyInfo.META
            ModKeyInfo.SHIFT,
            ModKeyInfo.SHIFT_LOCK -> ModKeyInfo.SHIFT
        }
        /*
           It will be:
              now \ param  Main unLockable  Main Lockable  sub unLockable  (sub Lockable)
           --------------------------------------------------------------------------------
              off        ->      ON             LOCK             ON             LOCK
              ON         ->      off            LOCK             ON             ON
              LOCK       ->      off            off              LOCK           LOCK
         */
        states[key] = when (states[key]) {
            State.OFF  -> if (mod.lockable) State.LOCK else State.ON
            State.ON   -> if (isSubMod) State.ON else if (mod.lockable) State.LOCK else State.OFF
            State.LOCK -> if (isSubMod) State.LOCK else State.OFF
            else -> return
        }
    }

    /**
     * Get states as set of mod keys.
     * If mode is LOCK then keyInfo will be lockable object.
     * @return set of enable ModKeyInfo
     */
    fun toSet(): Set<ModKeyInfo> {
        return states.filterValues { it != State.OFF }.map {
            if (it.value == State.LOCK)
                when (it.key) {
                    ModKeyInfo.CTRL  -> ModKeyInfo.CTRL_LOCK
                    ModKeyInfo.ALT   -> ModKeyInfo.ALT_LOCK
                    ModKeyInfo.META  -> ModKeyInfo.META_LOCK
                    ModKeyInfo.SHIFT -> ModKeyInfo.SHIFT_LOCK
                    else -> it.key
                }
            else
                it.key
        }.toSet()
    }

    /**
     * Turn off each stats unless lock mode.
     */
    fun resetUnlessLock() {
        states.forEach { if (it.value == State.ON) states[it.key] = State.OFF }
    }

    /**
     * Turn off all states.
     */
    fun reset() {
        states.forEach { states[it.key] = State.OFF }
    }
}
