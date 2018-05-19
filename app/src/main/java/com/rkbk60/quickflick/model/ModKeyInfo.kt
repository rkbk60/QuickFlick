package com.rkbk60.quickflick.model

import android.view.KeyEvent

/**
 * Modifier key information.
 */
sealed class ModKeyInfo: KeyInfo() {
    /**
     * Int code equaled to KeyEvent.mCode.
     * Use left modifier code. It equals to KeyEvent.KEYCODE_XXX_LEFT.
     */
    abstract val code: Int

    /**
     * Int code equaled to KeyEvent.mMeta.
     * Use left modifier code. It equals to (KeyEvent.META_XXX_ON or KeyEvent.META_XXX_LEFT_ON)
     */
    abstract val meta: Int

    /**
     * Flag of whether is Lock mode.
     * If it would be true, the key can toggle LOCK mode.
     */
    open val lockable = false

    object SHIFT : ModKeyInfo() {
        override val code = KeyEvent.KEYCODE_SHIFT_LEFT
        override val meta = KeyEvent.META_SHIFT_ON or KeyEvent.META_SHIFT_LEFT_ON
    }

    object SHIFT_LOCK : ModKeyInfo() {
        override val code = SHIFT.code
        override val meta = SHIFT.meta
        override val lockable = true
    }

    object CTRL : ModKeyInfo() {
        override val code = KeyEvent.KEYCODE_CTRL_LEFT
        override val meta = KeyEvent.META_CTRL_ON or KeyEvent.META_CTRL_LEFT_ON
    }

    object CTRL_LOCK : ModKeyInfo() {
        override val code = CTRL.code
        override val meta = CTRL.meta
        override val lockable = true
    }

    object ALT : ModKeyInfo() {
        override val code = KeyEvent.KEYCODE_ALT_LEFT
        override val meta = KeyEvent.META_ALT_ON or KeyEvent.META_ALT_LEFT_ON
    }

    object ALT_LOCK : ModKeyInfo() {
        override val code = ALT.code
        override val meta = ALT.meta
        override val lockable = true
    }

    object META : ModKeyInfo() {
        override val code = KeyEvent.KEYCODE_META_LEFT
        override val meta = KeyEvent.META_META_ON or KeyEvent.META_META_LEFT_ON
    }

    object META_LOCK : ModKeyInfo() {
        override val code = META.code
        override val meta = META.meta
        override val lockable = true
    }

}
