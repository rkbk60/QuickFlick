
from verbalexpressions import VerEx
import git
import glob
import io
import json
import markdown
import os
import sys


DEBUG_MODE = json.loads(sys.argv[1].lower())


def log(s: str):
    print(" >> %s" % s)


current_dir = os.path.dirname(os.path.abspath(__file__))
output_dir = "output/document_image/"
if not os.path.exists(output_dir):
    os.makedirs(output_dir)

os.chdir(current_dir)

# clone wiki
if glob.glob("./wiki/*.md"):
    git.Repo(current_dir + "/wiki").git.pull()
else:
    git.Repo.clone_from("git@github.com:rkbk60/QuickFlick.wiki.git",
                        current_dir + "/wiki")

# generate image html
template = io.open("./template/document_image.html").read()
for png in glob.glob('./wiki/*.png'):
    basename = os.path.splitext(os.path.basename(png))[0]
    html = io.open(output_dir + basename + ".html", "w+", encoding='utf-8')
    html.write(template.replace("$IMAGE", '<img src="./%s.png" />' % basename))
    html.close()
    log("generate: ./" + output_dir + basename + ".html")
print()
