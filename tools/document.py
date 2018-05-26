
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


url = 'file:///android_asset/'

# pre hyperlink fixer
re_disable_link = (VerEx().
                   find("[").
                   anything_but("]").
                   find("](http").
                   anything_but(")").
                   find(")").
                   regex())
to_disable_link = r'\2'

# pre hyperlink fixer
re_fix_innerlink = (VerEx().
                    find("[").
                    anything_but("]").
                    find("](#").
                    anything_but(")").
                    find(")").
                    regex())
# %s will be self markdown name
to_fix_innerlink = r'[\2](%s#\4)'

# fix hyperlink
re_fix_linkpath = (VerEx().
                   find("[").
                   anything_but("]").
                   find("](").
                   anything_but('#').
                   maybe("#").
                   anything_but(")").
                   find(")").
                   regex())
to_fix_linkpath = r'[\2](%s\4.html\5\6)' % url

# img-tag to a-tag
re_img2a = (VerEx().
            find("<img").
            anything().
            find(' src="').
            anything().
            find('.png"').
            anything().
            find("/>").
            regex())
to_img2a = r'[[View image]](%s\4.html?is_image=1)' % url

# fix minus to space in html hyperlink
re_minus2space = (VerEx().
                  find(url).
                  anything_but("#").
                  maybe("#").
                  anything_but(".").
                  find('.html').
                  regex())

# regex to find title
re_title = (VerEx().
            find("# ").
            anything().
            regex())

current_dir = os.path.dirname(os.path.abspath(__file__))
output_dir = "output/document/"
if not os.path.exists(output_dir):
    os.makedirs(output_dir)

os.chdir(current_dir)

# clone wiki
if glob.glob("./wiki/*.md"):
    git.Repo(current_dir + "/wiki").git.pull()
else:
    git.Repo.clone_from("git@github.com:rkbk60/QuickFlick.wiki.git",
                        current_dir + "/wiki")

# generate document html
template = io.open("./template/document.html").read()
for md in glob.glob('./wiki/*.md'):
    source_all = io.open(md, encoding='utf-8').read()
    source = source_all.splitlines()
    basename = os.path.splitext(os.path.basename(md))[0]
    if re_title.match(source_all) is None:
        source = ["# " + basename] + source
    newtext = ""
    for newline in source:
        newline = re_fix_innerlink.sub(to_fix_innerlink % basename, newline)
        newline = re_disable_link.sub(to_disable_link, newline)
        newline = re_fix_linkpath.sub(to_fix_linkpath, newline)
        newline = re_img2a.sub(to_img2a, newline)
        newtext += "\n" + newline
    html = io.open(output_dir + basename + ".html", "w+", encoding='utf-8')
    context = markdown.markdown(newtext)
    newcontext = u""
    for newline in context.split("<"):
        if newline:
            newline = "<" + newline
        newline = newline.replace("Wiki", "Document")
        targets = re_minus2space.findall(newline)
        if targets:
            for target in targets:
                target = target[1]
                newline = newline.replace(
                    target, target.replace("-", " "))
        newcontext += newline
    result = template.replace("$MARKDOWN", newcontext)
    html.write(result)
    html.close()
    log("generate: ./" + output_dir + basename + ".html")
print()
