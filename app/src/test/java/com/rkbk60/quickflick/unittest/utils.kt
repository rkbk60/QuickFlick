package com.rkbk60.quickflick.unittest

import com.rkbk60.quickflick.model.Flick

fun onTap(): Flick = Flick(Flick.Direction.NONE, 0)

fun onLeft(x: Int): Flick = Flick(Flick.Direction.LEFT, x)

fun onRight(x: Int): Flick = Flick(Flick.Direction.RIGHT, x)

fun onUp(x: Int): Flick = Flick(Flick.Direction.UP, x)

fun onDown(x: Int): Flick = Flick(Flick.Direction.DOWN, x)
