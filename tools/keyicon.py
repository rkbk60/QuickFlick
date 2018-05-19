
# Note: For run this script, you have to install Roboto in system.
import cairosvg
import io
import json
import os
import sys


DEBUG_MODE = json.loads(sys.argv[1].lower())


def log(s: str) -> None:
    print("  >> " + s)


class KeyIcon:
    def __init__(self, filename: str, text_center: str = "", text_bottom: str = "") -> None:
        self.name = filename
        self.text1 = text_center
        self.text2 = text_bottom
        self.template = "keyicon.svg"


class ModKeyIcon(KeyIcon):
    def __init__(self, filename: str, text_left: str,
                 text_right, type_left: int, type_right: int) -> None:
        text_left = text_left.lower() \
            if (type_left == 0) else text_left.upper()
        text_right = text_right.lower() \
            if (type_right == 0) else text_right.upper()
        super().__init__(filename, "%s%s" % (text_left, text_right))
        self.flag_left = type_left > 1
        self.flag_right = type_right > 1
        self.template = "keyicon_mod.svg"


class FnKeyIcon(KeyIcon):
    def __init__(self, filename: str, text: str) -> None:
        super().__init__(filename, "F", text)
        self.template = "keyicon_fn.svg"


key_icon_set = [
    KeyIcon("0", "!@#$", "0"),
    KeyIcon("1", ". , ?/", "1"),
    KeyIcon("2", "ABC -_", "2"),
    KeyIcon("3", "DEF=+", "3"),
    KeyIcon("4", "GHI ; :", "4"),
    KeyIcon("5", "JKL &quot; &apos;", "5"),
    KeyIcon("6", "MNO\|", "6"),
    KeyIcon("7", "PQRS", "7"),
    KeyIcon("8", "TUV `~", "8"),
    KeyIcon("9", "WXYZ", "9"),
    KeyIcon("escape", "Esc"),
    KeyIcon("space", "%^&amp;*", "Space"),
    KeyIcon("tabenter", "Enter", "Tab"),
    KeyIcon("delete", "Dlt"),
    KeyIcon("backspace", "BS"),
    KeyIcon("brackets_left", "{ [ &lt;", "("),
    KeyIcon("brackets_right", "&gt; ] }", ")"),
    KeyIcon("arrow_mode1", "Arw"),
    KeyIcon("arrow_mode2", "Pmv"),
    ModKeyIcon("meta_alt_off_off",    "m", "a", 0, 0),
    ModKeyIcon("meta_alt_off_on",     "m", "a", 0, 1),
    ModKeyIcon("meta_alt_off_lock",   "m", "a", 0, 2),
    ModKeyIcon("meta_alt_on_off",     "m", "a", 1, 0),
    ModKeyIcon("meta_alt_on_on",      "m", "a", 1, 1),
    ModKeyIcon("meta_alt_on_lock",    "m", "a", 1, 2),
    ModKeyIcon("meta_alt_lock_off",   "m", "a", 2, 0),
    ModKeyIcon("meta_alt_lock_on",    "m", "a", 2, 1),
    ModKeyIcon("meta_alt_lock_lock",  "m", "a", 2, 2),
    ModKeyIcon("ctrl_alt_off_off",    "c", "a", 0, 0),
    ModKeyIcon("ctrl_alt_off_on",     "c", "a", 0, 1),
    ModKeyIcon("ctrl_alt_off_lock",   "c", "a", 0, 2),
    ModKeyIcon("ctrl_alt_on_off",     "c", "a", 1, 0),
    ModKeyIcon("ctrl_alt_on_on",      "c", "a", 1, 1),
    ModKeyIcon("ctrl_alt_on_lock",    "c", "a", 1, 2),
    ModKeyIcon("ctrl_alt_lock_off",   "c", "a", 2, 0),
    ModKeyIcon("ctrl_alt_lock_on",    "c", "a", 2, 1),
    ModKeyIcon("ctrl_alt_lock_lock",  "c", "a", 2, 2),
    FnKeyIcon("f1_3", "1-3"),
    FnKeyIcon("f4_6", "4-6"),
    FnKeyIcon("f7_9", "7-9"),
    FnKeyIcon("fa_c", "A-C")
]

if DEBUG_MODE:
    key_icon_set = [
        KeyIcon("test", "main", "sub"),
        ModKeyIcon("mod", "m", "d", 1, 2),
        FnKeyIcon("fn1", "0-6"),
        FnKeyIcon("fn2", "12-12")
    ]

current_dir = os.path.dirname(os.path.abspath(__file__))

tmp_dir = "%s/tmp/icons" % current_dir
if not os.path.exists(tmp_dir):
    os.mkdir(tmp_dir)

output_dirs = ['drawable-mdpi', 'drawable-hdpi', 'drawable-xhdpi',
               'drawable-xxhdpi', 'drawable-xxxhdpi']
for output_dir in output_dirs:
    output_dir = "%s/output/icons/%s/" % (current_dir, output_dir)
    if not os.path.exists(output_dir):
        os.makedirs(output_dir)

os.chdir(current_dir)

for icon in key_icon_set:
    # make template svg icon.
    template = io.open("%s/template/%s" % (current_dir, icon.template), "r")
    newtext = ""
    flag_modkey = False
    fix_x = "20px"
    for newline in template:
        newline = newline.replace("s1", icon.text1)
        newline = newline.replace("s2", icon.text2)
        if isinstance(icon, ModKeyIcon):
            if "line_left" in newline:
                flag_modkey = icon.flag_left
            elif "line_right" in newline:
                flag_modkey = icon.flag_right
            newline = newline.replace("0.00", "1.00") \
                if flag_modkey else newline
        newtext = "%s%s" % (newtext, newline)
    template.close()
    name = "%s/keyicon_%s" % (tmp_dir, icon.name)
    newsvg = io.open("%s.svg" % name, "w+")
    newsvg.write(newtext)
    newsvg.close()

    # resize template and convert to png.
    size = 0
    for output in output_dirs:
        size += 1
        svgpath = "/%s.svg" % name
        pngpath = "output/icons/%s/keyicon_%s.png" % (output, icon.name)
        cairosvg.svg2png(url=svgpath, write_to=pngpath, scale=size)
        log("generate: ./" + pngpath)
    newsvg.close()
print()
