package com.rkbk60.quickflick.model

object KeyIndex {
    /* KeyIndex Table: (defined name by Spreadsheet rule)

       -------------INDICATOR--------------
         A1 | B1 | C1 | D1 | E1 | F1 | G1
       -----|----|----|----|----|----|-----
         A2 | B2 | C2 | D2 | E2 | F2 | G2
       -----|----|----|----|----|----|-----
         A3 | B3 | C3 | D3 | E3 | F3 | G3
       -----|----|----|----|----|----|-----
         A4 | B4 | C4 | D4 | E4 | F4 | G4
       ------------------------------------

     */
    const val NOTHING   = -1
    const val INDICATOR = 0

    const val A1 = 1
    const val B1 = 2
    const val C1 = 3
    const val D1 = 4
    const val E1 = 5
    const val F1 = 6
    const val G1 = 7

    const val A2 = 8
    const val B2 = 9
    const val C2 = 10
    const val D2 = 11
    const val E2 = 12
    const val F2 = 13
    const val G2 = 14

    const val A3 = 15
    const val B3 = 16
    const val C3 = 17
    const val D3 = 18
    const val E3 = 19
    const val F3 = 20
    const val G3 = 21

    const val A4 = 22
    const val B4 = 23
    const val C4 = 24
    const val D4 = 25
    const val E4 = 26
    const val F4 = 27
    const val G4 = 28

    fun isLeftFunctions(index: Int): Boolean = index in listOf(A1, A2, A3, A4)

    fun isRightFunctions(index: Int): Boolean = index in listOf(G1, G2, G3, G4)

    fun isFunction(index: Int): Boolean = isLeftFunctions(index) || isRightFunctions(index)

    fun isNextToLeftFunction(index: Int): Boolean = index in listOf(B1, B2, B3, B4)

    fun isNextToRightFunction(index: Int): Boolean = index in listOf(F1, F2, F3, F4)

    fun isNextToFunction(index: Int): Boolean = isNextToLeftFunction(index) || isNextToRightFunction(index)

    fun isLastOfLow(index: Int): Boolean = isRightFunctions(index) || index == INDICATOR

    fun isValid(index: Int): Boolean = index in (A1..G4)

}
