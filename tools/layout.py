
import io
import os
import shutil

DEBUG_MODE = False


class Dimention:
    def __init__(self, name: str) -> None:
        self.name = name
        self.value = "key_height_" + name


class Orientation:
    def __init__(self, name: str, value: int) -> None:
        self.name = name
        self.value = "%ddp" % value


dimentions = [
    Dimention("small"),
    Dimention("semi_small"),
    Dimention("medium"),
    Dimention("semi_large"),
    Dimention("large")
]

pr = Orientation("portrait", 8)
ls = Orientation("landscape", 16)
portraits = list(map(lambda x: (x, pr), dimentions))
landscapes = list(map(lambda x: (x, ls), dimentions))


if DEBUG_MODE:
    orders = [(Dimention("tD"), Orientation("tO", 10))]
else:
    orders = portraits + landscapes

current_dir = os.path.dirname(os.path.abspath(__file__))
output_dir = current_dir + "/output/keyboard/"
if os.path.exists(output_dir):
    shutil.rmtree(output_dir)
os.makedirs(output_dir)

for (dim, ori) in orders:
    template = io.open("%s/template/keyboard.xml" % current_dir)
    newtext = ""
    for newline in template:
        newline = newline.replace("$KEY_HEIGHT_VALUE", dim.value)
        newline = newline.replace("$FOOTER_HEIGHT_DP", ori.value)
        newtext += newline
    template.close()
    filename = "keyboard_%s_%s.xml" % (dim.name, ori.name)
    newxml = io.open(output_dir + filename, "w+")
    newxml.write(newtext)
    newxml.close()
