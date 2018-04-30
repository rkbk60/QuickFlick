# simple makefile to run script and move output files

PYTHON = pipenv run python

# these valuses have to lower(js style bool) because of using json.loads()
ARG_DEBUG = true
ARG_RELEASE = false

CP_TARGET = ./../app/src/main/res/

debug: clean
	@echo
	@echo --- Dubug mode ON ---
	@echo
	-$(PYTHON) indicator_morse.py $(ARG_DEBUG)
	-$(PYTHON) keyboard_layout.py $(ARG_DEBUG)
	-$(PYTHON) keyicon.py $(ARG_DEBUG)

release: clean indicator_morse.py keyboard_layout.py keyicon.py

indicator_morse.py:
	$(PYTHON) $@ $(ARG_RELEASE)
	cp -r ./output/indicator/* $(CP_TARGET)

keyboard_layout.py:
	$(PYTHON) $@ $(ARG_RELEASE)
	cp -r ./output/keyboard/* $(CP_TARGET)

keyicon.py:
	$(PYTHON) $@ $(ARG_RELEASE)
	cp -r ./output/icons/* $(CP_TARGET)

%.py:
	@echo You have to make new task if $@ exists.

clean:
	rm -rf output/*
	rm -rf tmp/*

.PHONY: debug release clean indicator_morse.py \
 keyboard_layout.py keyicon.py

# vim: set noexpandtab
