
import cairosvg
import io
import os
import shutil


DEBUG_MODE = False


class MorseSignal:
    SIZE_RATE = 6
    rect = '<rect x="%spx" y="0" width="%fpx" height="1px" fill="%s"/>\n'
    SIGNAL_0 = "_"
    SIGNAL_1 = "."
    SIGNAL_3 = "-"
    SIGNAL_EDGE = "/"

    def __init__(self, filename: str, signal: str) -> None:
        self.filename = filename
        self.signal = signal
        self.width = 0
        self.svg = ""
        for pulse in self.__getTrueSignal(signal):
            if pulse == self.SIGNAL_0:
                self_size = self.SIZE_RATE
                self.svg += self.rect % (self.width, self_size, "#222222")
                self.width += self_size
            elif pulse == self.SIGNAL_1:
                self_size = self.SIZE_RATE
                self.svg += self.rect % (self.width, self_size, "#ababab")
                self.width += self_size
            elif pulse == self.SIGNAL_3:
                self_size = self.SIZE_RATE * 3
                self.svg += self.rect % (self.width, self_size, "#ababab")
                self.width += self_size
            elif pulse == self.SIGNAL_EDGE:
                self_size = self.SIZE_RATE * 3
                self.svg += self.rect % (self.width, self_size, "#222222")
                self.width += self_size
        if DEBUG_MODE:
            print("%s:\n%s" % (self.filename, self.svg))

    def __getTrueSignal(self, signal: str) -> str:
        return self.SIGNAL_0.join(signal) + self.SIGNAL_EDGE


morse_set = [
    MorseSignal("left", "."),
    MorseSignal("right", ".."),
    MorseSignal("up", ".--."),
    MorseSignal("down", "---")
]

current_dir = os.path.dirname(os.path.abspath(__file__))

tmp_dir = "%s/tmp/" % current_dir

if os.path.exists(tmp_dir):
    shutil.rmtree(tmp_dir)
os.mkdir(tmp_dir)

output_dirs = {'drawable-mdpi', 'drawable-hdpi', 'drawable-xhdpi',
               'drawable-xxhdpi', 'drawable-xxxhdpi'}
for output_dir in output_dirs:
    output_dir = "%s/output/%s/" % (current_dir, output_dir)
    if os.path.exists(output_dir) & DEBUG_MODE:
        shutil.rmtree(output_dir)
    elif not os.path.exists(output_dir):
        os.makedirs(output_dir)

os.chdir(current_dir)

for morse in morse_set:
    # make template svg file.
    template = io.open("%s/template/morse.svg" % current_dir, "r")
    newtext = ""
    for newline in template:
        newline = newline.replace("@WIDTH", str(morse.width))
        newline = morse.svg if "svg-value" in newline else newline
        newtext += newline
    template.close()
    name = "%s/tmp/indicator_morse_%s" % (current_dir, morse.filename)
    svg = io.open("%s.svg" % name, "w+")
    svg.write(newtext)
    svg.close()

    # resize template and convert to png.
    size = 0
    for output in output_dirs:
        size += 1
        path_svg = "/%s.svg" % name
        path_png = "output/%s/indicator_morse_%s.png" % (output, morse.filename)
        cairosvg.svg2png(url=path_svg, write_to=path_png, scale=size)

if not DEBUG_MODE:
    shutil.rmtree("%s/tmp" % current_dir)
