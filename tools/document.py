
from verbalexpressions import VerEx
import glob
import io
import json
import markdown
import os
import sys


DEBUG_MODE = json.loads(sys.argv[1].lower())


def log(s: str):
    print(" >> %s" % s)


url = 'file:///android-asset/'

# fix hyperlink
re_fixln = (VerEx().
            find("[").
            anything_but("]").
            find("](").
            anything_but("#").
            maybe("#").
            anything_but(")").
            find(")").
            regex())
to_fixln = r'[\2](%s\4.md\5\6)' % url

# img-tag to a-tag
re_img2a = (VerEx().
            find("<img").
            anything().
            find(' src="').
            anything_but('"').
            find('"').
            anything().
            find("/>").
            regex())
to_img2a = r'[[View image]](%s\4)' % url

current_dir = os.path.dirname(os.path.abspath(__file__))
output_dir = "output/document/"
if not os.path.exists(output_dir):
    os.makedirs(output_dir)

os.chdir(current_dir)

template = io.open("./template/document.html").read()

for md in glob.glob('./wiki/*.md'):
    source = io.open(md)
    newtext = ""
    for newline in source:
        newline = re_fixln.sub(to_fixln, r'%s' % newline)
        newline = re_img2a.sub(to_img2a, r'%s' % newline)
        newtext += newline
    source.close()
    basename = os.path.basename(md)
    newmd = io.open(output_dir + basename, "w+")
    newmd.write(template.replace("$MARKDOWN", markdown.markdown(newtext)))
    newmd.close()
    log("generate: ./" + output_dir + basename)
print()
