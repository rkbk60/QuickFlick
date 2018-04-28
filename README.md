# QuickFlick

QuickFlick is minimal size keyboard for any hackers.  
If you use terminal emulator or code editor in Android Phone, this app will be BEST choice.

## Features
* Compact Height.
* Place all of major keys which US sequence keyboard has.
* All characters and numbers can input only with one action.
* Meta/Ctrl/Alt keys have LOCK mode.

## Installation
This app is now available on Google Play.  
<a href='https://play.google.com/store/apps/details?id=com.rkbk60.quickflick&pcampaignid=MKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png' height='90px' style='max-width=100%;'/></a>

## Usage
Check [Wiki](https://github.com/rkbk60/QuickFlick/wiki/).

## License
MIT License

## TODO
### redesign app
This app is too complex to add features, to fix bug. and to test these.  
By remake model or application layor, we can be able to:
* add unit/ui tests
* maintain easily
* make release cycle more faster

### make new Keyboard/KeyboardView framework
Android Keyboard/KeyboardView have these issues:
* cannot show dynamic previews
* previews are partly racking when it is outside of KeyboardView
* hard to place keys flexibily
* hard to change keyboard height without preference activity

If we can make new framework, we will be able to
* implement preview popup
* set key/keyboard height or style easily
* fix keyboard height when device rotate to landscape to input easily

### others
**must**
* first-run-tutorial
* offline document(wiki)
* check multi-modified-key behavior

**maybe**
* add lockable Shift key
* user-defined background/indicator theme
