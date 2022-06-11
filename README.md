3d Button
=======
3d Button is a custom Button of Android to create 3d buttons and circle 3d buttons. This library is very small and highly customizable.


Screenshot
----------
![](https://raw.githubusercontent.com/ahmedteleb96/3d_buttons/master/screenshot/screenshot.png)

Including in your project
-------------------------
###Using library
 you just need to add the following dependency to your `build.gradle`.

    dependencies {
	        implementation 'com.github.AhmedTeleb96:3d_buttons:1.0.0'
    }
    


Customizable attributes
-----------------------

|  Attribute    |   default value   | xml           |                 java                |
|---------------|-------------------|---------------|-------------------------------------|
| button color  |      #3eadeb      | buttonColor   | setButtonColor(int color)           |
| enable shadow |        true       | shadowEnabled | setShadowEnabled(boolean isEnabled) |
| shadow color  |  Automatically generated <br> from button color   | shadowColor   | setShadowColor(int color)           |
| shadow height |        4dp        | shadowHeight  | setShadowHeight(int height)         |
| corner radius |        8dp        | cornerRadius  | setCornerRadius(int radius)         |

Usage
-----
If the default values of custom attribues did not meet your requirement, you can easily re-config that attributes. This is sample code that you can refer. you can also browse demo app for more details.

### via xml (sample)
-  Define `xmlns:button3d="http://schemas.android.com/apk/res-auto"` on root of your xml file

```xml
<com.ahmedteleb.buttons3d.Button3d
    ...
    button3d:buttonColor="@color/color_concrete"
    button3d:shadowColor="@color/color_asbestos"
    button3d:shadowEnabled="true"
    button3d:shadowHeight="5dp"
    button3d:cornerRadius="5dp"
    ...
     />
# for circle 3d button
<com.ahmedteleb.buttons3d.Button3dCircle
    ...
    button3d:buttonColor="@color/color_concrete"
    button3d:shadowColor="@color/color_asbestos"
    button3d:shadowEnabled="true"
    button3d:shadowHeight="5dp"
    button3d:cornerRadius="5dp"
    ...
     />
```

### via code (sample)
```kotlin
        val disabledBtn = findViewById<Button3d>(com.ahmedteleb.buttons3d.R.id.disabled_button)
        disabledBtn.setButtonColor(getResources().getColor(R.color.purple_500));
        disabledBtn.setShadowColor(getResources().getColor(R.color.purple_200));
        disabledBtn.setShadowEnabled(true)
        disabledBtn.setShadowHeight(5)
        disabledBtn.setCornerRadius(5)  
```

Color Swatches
--------------
For your convenience Swatches Preset by [designmono](http://designmodo.github.io/Flat-UI/) are also defined in this library
```xml
 //Color Swatches provided by http://designmodo.github.io/Flat-UI/
<color name="button3d_color_turquoise">#1abc9c</color>
<color name="button3d_color_green_sea">#16a085</color>
<color name="button3d_color_emerald">#2ecc71</color>
<color name="button3d_color_nephritis">#27ae60</color>
<color name="button3d_color_peter_river">#3498db</color>
<color name="button3d_color_belize_hole">#2980b9</color>
<color name="button3d_color_amethyst">#9b59b6</color>
<color name="button3d_color_wisteria">#8e44ad</color>
<color name="button3d_color_wet_asphalt">#34495e</color>
<color name="button3d_color_midnight_blue">#2c3e50</color>
<color name="button3d_color_sun_flower">#f1c40f</color>
<color name="button3d_color_orange">#f39c12</color>
<color name="button3d_color_carrot">#e67e22</color>
<color name="button3d_color_pumpkin">#d35400</color>
<color name="button3d_color_alizarin">#e74c3c</color>
<color name="button3d_color_pomegranate">#c0392b</color>
<color name="button3d_color_clouds">#ecf0f1</color>
<color name="button3d_color_silver">#bdc3c7</color>
<color name="button3d_color_concrete">#95a5a6</color>
<color name="button3d_color_asbestos">#7f8c8d</color>
```

Developed By
-------
Ahmed Teleb
