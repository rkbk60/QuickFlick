package com.rkbk60.quickflick.model

/**
 * Container of key information.
 */
abstract class KeyInfo {
    open val mods = listOf<ModKeyInfo>()

    object NULL: KeyInfo()
}
