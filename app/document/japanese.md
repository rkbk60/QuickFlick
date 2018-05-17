<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="file:///github-markdown.css" ref="stylesheet" type="text/css">
    <style>
        .markdown-body {
            box-sizing: border-box;
            margin: 0 auto;
            padding: 15px;
        }
    </style>
</head>
<body>
<p>QuickFlickの日本語版Wikiです。<br />
作者が日本人のため、こちらの情報が正確である可能性が高いです。<br />
Here is QuickFlick Japanese Wiki.<br />
I am Japanese, so this page could have accurate information.  </p>
<h2>目次</h2>
<p><a href="file:///android-asset/.md#initializing">0. 導入</a><br />
<a href="file:///android-asset/.md#how_to_input">1. 入力について</a><br />
&emsp;<a href="file:///android-asset/.md#multi_phase_flick">1.1 多段階フリック</a><br />
&emsp;<a href="file:///android-asset/.md#flick_indicator">1.2 フリックインジケーター</a><br />
&emsp;<a href="file:///android-asset/.md#canceling_with_multi_tap">1.3 マルチタップによる入力制御</a><br />
<a href="file:///android-asset/.md#keymap">2. キーマップ</a><br />
<a href="file:///android-asset/.md#arrow_key">3. 方向キー</a><br />
<a href="file:///android-asset/.md#fn_key">4. Fnキー</a><br />
<a href="file:///android-asset/.md#mod_key">5. 修飾キー</a><br />
<a href="file:///android-asset/.md#settings">6. 設定</a><br />
&emsp;<a href="file:///android-asset/.md#setting_threshold">6.1 Horizontal/Vertical Flick Threshold</a><br />
&emsp;<a href="file:///android-asset/.md#setting_mtc">6.2 Multi tap settings</a><br />
&emsp;<a href="file:///android-asset/.md#setting_appearance">6.3 Appearance</a><br />
<a href="file:///android-asset/.md#other">7. その他</a>  </p>
<p><a id="initializing"></a></p>
<h2>0. 導入</h2>
<p>Google Playストアより、当アプリ(<a href="file:///android-asset/https://play.google.com/store/apps/details?id=com.rkbk60.quickflick">QuickFlick Keyboard</a>.md)をダウンロードしてください。<br />
その後、設定 &gt; 言語と入力 &gt; 仮想キーボード &gt; キーボードを管理 からQuickFlickの有効化を行ってください。</p>
<p><a id="how_to_input"></a></p>
<h2>1. 入力について</h2>
<p>一般的な日本語入力アプリと同様、タップもしくはフリックで入力ができます。<br />
<strong>ただしキーごとに入力できる文字が大きく異なるため、かならずキーマップを確認しておいてください。</strong><br />
→ <a href="file:///android-asset/.md#keymap">キーマップ</a></p>
<p><a id="multi_phase_flick"></a></p>
<h3>1.1 多段階フリック</h3>
<p>QuickFlickではより少ないタップ数での入力を実現するため、多段階フリックを実装しています。<br />
タップした箇所からの方向および段階的な距離によって入力する文字を変えることができます。<br />
ほとんどのキーでは、タップのみで数字を、小距離でのフリックで大文字を、それ以上であれば小文字を入力できます。</p>
<p>なお、フリック感度は設定によって変えることができます。<br />
詳しくはこちらをご確認ください。<br />
→ <a href="file:///android-asset/.md#setting_threshold">設定 &gt; Horizontal/Vertical Flick Threshold</a></p>
<p><a id="flick_indicator"></a></p>
<h3>1.2 フリックインジケーター</h3>
<p>フリックの状態(方向/距離)はキーボード上部のインジケーターによって随時確認できます。<br />
インジケーターは方向を色で、距離はその濃度で表現します。</p>
<p><a id="canceling_with_multi_tap"></a></p>
<h3>1.3 マルチタップによる入力制御</h3>
<p>マルチタップにより、キー入力を制御することができます。<br />
バージョン1.5現在、以下の2機能が利用可能です。
- <strong>Flick reseter</strong>: マルチタップをする度、現在のフリック情報をリセットすることができます。
- <strong>Input canceler</strong>: 2回マルチタップを行うことで、現在実行されようとしているキー入力をキャンセルすることができます。</p>
<p>それぞれの設定は設定画面から有効化/無効化ができます。<br />
→ <a href="file:///android-asset/.md#setting_mtc">設定 &gt; Multi tap settings</a></p>
<p><a id="keymap"></a></p>
<h2>2. キーマップ</h2>
<p>全体的なキーマップは以下のとおりです。<br />
タップした場合は緑背景の内容が入力されます。</p>
<p><a href="file:///android-asset/keymap_overview.png">[View image]</a></p>
<p><a id="arrow_key"></a></p>
<h2>3. 方向キー</h2>
<p><code>arw mode</code>をフリックせずに入力すると、方向キーのモードを切り替えることができます。<br />
モードは以下の2種類があります。
- <strong>Awr</strong>: 連続入力も可能な方向キーです。この状態ではフリックをするだけで入力が可能です。
- <strong>Pmv</strong>: PageUp/Down, Home, Endのそれぞれを入力できます。</p>
<p>なお、各モードにおけるキーマップは以下の通りです。</p>
<p><a href="file:///android-asset/keymap_arrow.png">[View image]</a></p>
<p><a id="fn_key"></a></p>
<h2>4. Fnキー</h2>
<p>バージョン1.5現在、QuickFlickは左右どちらかにF1~F12キー(以降Fnキー)を配置しています。<br />
またこれらのキーで上下どちらかにフリックすることで、Fnキーの左右配置を入れ替えることができます(この切り替えは設定画面からも可能です)。<br />
以下にFnキーのキーマップを示します。</p>
<p><a href="file:///android-asset/keymap_fn.png">[View image]</a></p>
<p><a id="mod_key"></a></p>
<h2>5. 修飾キー</h2>
<p>QuickFlickはCtrl/Alt/Metaの3種類の修飾キーを実装しています。<br />
各修飾キーは3つのモードを持っています。
- <strong>OFF</strong>: キー押下がされていない状態で、小文字で表現されます。
- <strong>ON</strong>: キーが押下された状態で、大文字で表現されます。<br />
非修飾キーを入力するとOFFになります。<br />
( <code>&lt;Ctrl&gt;abc</code> → <code>&lt;C-a&gt; b c</code> )
- <strong>LOCK</strong>: キーが押下された状態で、<u>下線付きの大文字</u>で表現されます。<br />
非修飾キーを入力してもOFFになりません。<br />
( <code>&lt;Ctrl-Lock&gt;abc</code> → <code>&lt;C-a&gt; &lt;C-b&gt; &lt;C-c&gt;</code> )</p>
<p><a id="settings"></a></p>
<h2>6. 設定</h2>
<p>バージョン1.5現在、以下の設定が利用できます。</p>
<p><a id="setting_threshold"></a></p>
<h3>6.1 Horizontal/Vertical flick threshold</h3>
<p>多段階フリックのフリック感度(閾値)を、thou単位(thou = ミリインチ)で設定できます。
- <strong>1st threshold</strong>: タップ/フリックの判定に使用される値です。この距離未満のフリックはタップとみなされます。
- <strong>2nd threshold</strong>: フリックレベルの判定に使用される値です。この値を超える度にフリックレベルが加算され、入力できる文字が変化します。</p>
<p><a id="setting_mtc"></a></p>
<h3>6.2 Multi tap settings</h3>
<p>入力中にマルチタップを行った際の挙動を変更できます。
現在以下の機能を利用することができます。
- <strong>Flick reseter</strong>
- <strong>Input canceler</strong></p>
<p>各機能の説明はこちらをご確認ください。<br />
→ <a href="file:///android-asset/.md#canceling_with_multi_tap">入力について &gt; マルチタップによる入力制御</a></p>
<p><a id="setting_appearance"></a></p>
<h3>6.3 Appearance</h3>
<p>キーボードの見た目を変更できます。<br />
- <strong>Toggle layout to right</strong>: キーボードのレイアウトを変えます。
- <strong>Indicator theme</strong>: フリックインジケーターの表示パターンを変更します。</p>
<p>この他、キーの高さと背景色の設定実装を検討しています。</p>
<p><a id="other"></a></p>
<h2>7. その他</h2>
<ul>
<li>アプリの日本語化は行わない予定です。ご了承ください。</li>
</ul>
</body>
</html>
