You can input with tap or flick.  
If you want to check keymap, see [Here](keymap).

<a id="1"></a>
## Multi phase flick
On flick input, QuickFlick calculates distance level in response to distance from first tap point, and detects internal keycode to input in accordance with distance level.   
(ex. small flick --> upper char(like `A`), large flick --> lower char(like `a`))

### Flick indicator
You can check flicking state(direction, distance level) in *Flick Indicator* located at top of keyboard.  
Indicator shows direction with hue, distance level with transparent of color.  

### Change flick sensitivity
You can change flick sensitivity in [Settings > Horizontal/Vertical Flick Threshold](Settings#1).

<a id="2"></a>
## Canceling with multi tap
You can control input with multi tap.  
QuickFlick has now two functions to control input.
* **Flick reseter**: Reset flicking state with multi tap.
* **Input canceler**: Cancel input with twice tap.

These functions are able to toggle on-off in [Settings > Multi tap settings](Settings#2)
