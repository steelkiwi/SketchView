SketchView Library & Sample
===========================

SketchView library provides a custom implementation of a canvas on which user can draw via gestures.

SketchView sample is a template-like sketch editor with following basic actions:

1. Set brush color (from a range of available colors).

2. Set brush size (from a set of available sizes).

3. Set eraser size (from a set of available sizes).

4. Set image as background of the sketch.

5. Undo option (all gestures can be undone).

6. Save sketch.

7. Share sketch.

Further improvement:
* Add support for zoom in/out and translation via gestures to SketchView.

How to use
----------

Simply put SketchView into your layout:

```xml
<com.skd.sketchview.SketchView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/sketchView"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

To manipulate SketchView use its public methods:
* setBrushes()
* setSketchBackground()
* setGestureColorAndSize()
* undoLastGesture()
* getSketchBimap()

More details are in SketchView sample.

![Screenshot 1: editor](screenshots/editor.png?raw=true)

![Screenshot 2: brush color picker](screenshots/brush_color_picker.png?raw=true)

![Screenshot 3: brush size picker](screenshots/brush_size_picker.png?raw=true)

![Screenshot 4: eraser size picker](screenshots/eraser_size_picker.png?raw=true)

![Screenshot 5: sketch](screenshots/sketch.png?raw=true)
