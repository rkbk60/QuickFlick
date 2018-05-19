package com.rkbk60.quickflick.model

/**
 * KeyInfo to control application with keyboard action.
 */
sealed class TriggerKeyInfo: KeyInfo() {
    final override val mods = NULL.mods

    object ARROWKEY_MODE : TriggerKeyInfo()

    object KEYBOARD_LAYOUT : TriggerKeyInfo()
}
