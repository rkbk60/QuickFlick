
import itertools
import io
import os
import shutil

DEBUG_MODE = False


class Height:
    def __init__(self, name: str, height_level: int) -> None:
        self.name = name
        self.id = str(height_level)
        self.value = "key_height_" + name
        self.info = "@dimen/%s (Lv.%d)" % (self.value, height_level)


class Orientation:
    def __init__(self, name: str, value: int) -> None:
        self.name = name
        self.id = name[0:3]
        self.value = "%ddp" % value
        self.info = "for %s (footer:%s)" % (name, self.value)


class Adjustment:
    def __init__(self, is_right: bool) -> None:
        self.name = "right" if is_right else "left"
        self.id = self.name[0:1]
        if is_right:
            self.width_left = "17%p"
            self.width_right = "0dp"
        else:
            self.width_left = "0dp"
            self.width_right = "17%p"
        self.info = "for " + self.name + " hand"


heights = [
    Height("small", 1),
    Height("semi_small", 2),
    Height("medium", 3),
    Height("semi_large", 4),
    Height("large", 5)
]

orientations = [
    Orientation("portrait", 8),
    Orientation("landscape", 16)
]

adjustments = [
    Adjustment(False),
    Adjustment(True)
]


if DEBUG_MODE:
    orders = [(Height("tD", 0), Orientation("tO", 10), Adjustment(True))]
else:
    orders = list(itertools.product(heights, orientations, adjustments))

current_dir = os.path.dirname(os.path.abspath(__file__))
output_dir = current_dir + "/output/keyboard/"
if os.path.exists(output_dir):
    shutil.rmtree(output_dir)
os.makedirs(output_dir)

for (hei, ori, adj) in orders:
    template = io.open("%s/template/keyboard.xml" % current_dir)
    newtext = ""
    for newline in template:
        newline = newline.replace("$KEY_HEIGHT_INFO", hei.info)
        newline = newline.replace("$KEY_HEIGHT_VALUE", hei.value)
        newline = newline.replace("$ORIENTATION_INFO", ori.info)
        newline = newline.replace("$FOOTER_HEIGHT_DP", ori.value)
        newline = newline.replace("$ADJUSTMENT_INFO", adj.info)
        newline = newline.replace("$KEY_WIDTH_LEFT", adj.width_left)
        newline = newline.replace("$KEY_WIDTH_RIGHT", adj.width_right)
        newtext += newline
    template.close()
    filename = "keyboard_%s_%s_%s.xml" % (hei.name, ori.name, adj.name)
    filename = "keyboard_" + adj.id + ori.id + hei.id + ".xml"
    newxml = io.open(output_dir + filename, "w+")
    newxml.write(newtext)
    newxml.close()
