Now QuickFlick has 3 modifier key(Ctrl/Alt/Meta).  
These keys can use in terminal emulator, code editor, and other apps which have any input field.  

**NOTE:**  
This app does *NOT* provide Cut/Copy/Paste/Redo/Undo functions.

## Mode
Each Modifier keys have 3 modes.

* **OFF**: Default. do nothing.
* **ON**: press modifier key once when you input char or number.  
(ex. type:`<Ctrl:ON>abc` --> `<C-a>bc`)
* **LOCK**: keep pressing modifire key.  
(ex. type:`<Ctrl:LOCK>abc` --> `<C-a><C-b><C-c>`)

## Control
See [Keymap](Keymap), and you may find that QucikFlick has two way to control modes.
* **Ctrl/Alt/Meta**: toggle ON <--\> OFF (<-- LOCK)
* **Ctrl/Alt/Meta Lock**: toggle OFF <--\> LOCK (<-- ON)

## Check mode
If these keys turn ON, key label changes lower to upper.   
(Meta: m-->M, Ctrl: c-->C, Alt: a-->A)  

If LOCK mode, add <u>underbar</u>.  
(ex. CtrlON: C, CtrlLOCK: <u>C</u>)
