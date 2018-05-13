
import itertools
import io
import json
import os
import sys

DEBUG_MODE = json.loads(sys.argv[1].lower())


def log(s: str):
    print("  >> %s" % s)


class Height:
    def __init__(self, name: str, height_level: int) -> None:
        self.name = name
        self.id = str(height_level).lower()
        self.value = "key_height_" + name
        self.info = "@dimen/%s (Lv.%d)" % (self.value, height_level)


class Orientation:
    def __init__(self, name: str, value: int) -> None:
        self.name = name
        self.id = name[0:3].lower()
        self.value = "%ddp" % value
        self.info = "for %s (footer:%s)" % (name, self.value)


class Adjustment:
    def __init__(self, is_right: bool) -> None:
        self.name = "right" if is_right else "left"
        self.id = self.name[0:1]
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
    orders = [(Height("td", 0), Orientation("to", 10), Adjustment(True))]
else:
    orders = list(itertools.product(heights, orientations, adjustments))

current_dir = os.path.dirname(os.path.abspath(__file__))
output_dir = "output/keyboard/xml/"
if not os.path.exists(output_dir):
    os.makedirs(output_dir)

os.chdir(current_dir)

for (hei, ori, adj) in orders:
    template = io.open(
        "%s/template/keyboard_%s_hand.xml" % (current_dir, adj.name))
    newtext = ""
    for newline in template:
        newline = newline.replace("$KEY_HEIGHT_INFO", hei.info)
        newline = newline.replace("$KEY_HEIGHT_VALUE", hei.value)
        newline = newline.replace("$ORIENTATION_INFO", ori.info)
        newline = newline.replace("$FOOTER_HEIGHT_DP", ori.value)
        newline = newline.replace("$ADJUSTMENT_INFO", adj.info)
        newtext += newline
    template.close()
    filename = "keyboard_%s_%s_%s.xml" % (adj.id, ori.id, hei.id)
    newxml = io.open(output_dir + filename, "w+")
    newxml.write(newtext)
    newxml.close()
    log("generate: ./" + output_dir + filename)
print()
