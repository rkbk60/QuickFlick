package com.rkbk60.quickflick.domain

import android.view.KeyEvent
import com.rkbk60.quickflick.model.AsciiKeyInfo
import com.rkbk60.quickflick.model.KeyInfo
import com.rkbk60.quickflick.model.ModKeyInfo

class KeyEventOrder(private var key: KeyInfo = KeyInfo.NULL, mods: Set<ModKeyInfo> = setOf()) {

    data class Unit(private val isDown: Boolean, val code: Int, val meta: Int) {
        fun toKeyEvent(time: Long): KeyEvent {
            val action = if (isDown) KeyEvent.ACTION_DOWN else KeyEvent.ACTION_UP
            return KeyEvent(time, time, action, code, 0, meta)
        }
    }

    private lateinit var preInputOrders: List<Unit>
    private lateinit var postInputOrders: List<Unit>
    private lateinit var mainInputOrders: List<Unit>

    private var lastMeta = mods.fold(0, { acc, mod -> acc or mod.meta })

    val mainKey: KeyInfo
        get() = key

    init {
        setModsInputOrders(mods)
        setMainInputOrders(key)
    }

    fun changeMainKey(key: KeyInfo) {
        this.key = if (key is AsciiKeyInfo.Modifiable) key else KeyInfo.NULL
        setMainInputOrders(key)
    }

    fun changeModKeys(mods: Set<ModKeyInfo>) {
        setModsInputOrders(mods)
        setMainInputOrders(key)
    }

    fun changeKeys(key: KeyInfo, mods: Set<ModKeyInfo>) {
        setModsInputOrders(mods)
        changeMainKey(key)
    }

    fun toList(): List<Unit> {
        return listOf(preInputOrders, mainInputOrders, postInputOrders).flatten()
    }

    fun toKeyEventList(time: Long): List<KeyEvent> {
        return toList().map { it.toKeyEvent(time) }
    }

    private fun setMainInputOrders(key: KeyInfo) {
        mainInputOrders = if (key is AsciiKeyInfo.Modifiable) {
            listOf(
                    Unit(true,  key.code, lastMeta),
                    Unit(false, key.code, lastMeta)
            )
        } else {
            listOf()
        }
    }

    private fun setModsInputOrders(mods: Set<ModKeyInfo>) {
        val modsList: List<Pair<ModKeyInfo, Int>> = mods.fold(listOf(), { list, mod ->
            if (list.isEmpty()) {
                listOf(Pair(mod, 0))
            } else {
                list.toMutableList().plus(
                        Pair(mod, list.last().first.meta or list.last().second)
                ).toList()
            }
        })
        preInputOrders = modsList.map {
            Unit(true, it.first.code, it.first.meta or it.second)
        }
        postInputOrders = modsList.reversed().map {
            Unit(false, it.first.code, it.second)
        }
        lastMeta = modsList.lastOrNull()?.let { it.first.meta or it.second } ?: 0
    }
}