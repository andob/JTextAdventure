## JTextAdventure

### Library to develop text adventure games on Android or CLI with Kotlin/JVM

<img src="https://raw.githubusercontent.com/andob/JTextAdventure/master/DEMO_CLI.gif"/>

### How can this be useful in a real-world app?

Consider you build an app about Ice Cream machines, with list screens, details screens, pictures, technical specs, user manuals and troubleshooting guides. You have to create a screen that guides the user through the following troubleshooting algorithm:

<img src="https://raw.githubusercontent.com/andob/JTextAdventure/master/IceCreamTroubleshootingSchema.png"/>

The result should look like this:

<img src="https://raw.githubusercontent.com/andob/JTextAdventure/master/DEMO_ANDROID.gif"/>

Implementing this UI in a classic event-based fashion can be quite tricky. A good solution would be to abstract the troubleshooting algorithm as a text adventure game. The app asks the user a question, the user responds to it, then another question is asked by the app and so on. By abstracting the algorithm as a text adventure game, we can keep a one-to-one relation between the algorithm and the code, because both the code describing the text adventure game and the troubleshooting schema are purely imperative / procedural. For more info, see the sample below. 

### Setup / sample

Import the library with:

```
repositories {
    maven { url "https://maven.andob.info/repository/open_source" }
}
```

```
dependencies {
    implementation 'ro.andob.jtextadventure:core:1.0.9'
    implementation 'ro.andob.jtextadventure:android:1.0.9'
    implementation 'ro.andob.jtextadventure:cli:1.0.9' //optional, for CLI
}
`````

Define the algorithm / text adventure game. Let's implement the ice cream machine troubleshooting guide from the above example:

```kotlin
class IceCreamTroubleshootingAlgorithm : TextAdventureGame<IConsoleEmulator>
{
    constructor(console : IConsoleEmulator) : super(console)

    fun IConsoleEmulator.promptYesNoButtons() = promptButtons("yes", "no")
    fun IConsoleEmulator.promptOkButton() = promptButtons("ok")

    fun IConsoleEmulator.readBoolean() = read()=="yes"

    override fun run()
    {
        console.write("Is ice cream too soft or thin?")
        console.promptYesNoButtons()
        if (console.readBoolean())
        {
            var theAirFlowsFreely = false
            while (!theAirFlowsFreely)
            {
                console.write("Is the machine located in an area that has enough space for the cool air to flow freely?")
                console.promptYesNoButtons()
                theAirFlowsFreely = console.readBoolean()
                if (!theAirFlowsFreely)
                {
                    console.write("Move the machine. There should be a minimum of three inches of air space on the sides of the unit.")
                    console.promptOkButton()
                    console.read()
                }
            }
            
            var theAmbiantTemperatureIsTooHigh = true
            while (theAmbiantTemperatureIsTooHigh)
            {
                console.write("Is the ambiant temperature higher than 37 degrees Celsius?")
                console.promptYesNoButtons()
                theAmbiantTemperatureIsTooHigh = console.readBoolean()
                if (theAmbiantTemperatureIsTooHigh)
                {
                    console.write("Move the machine in a cooler spot")
                    console.promptOkButton()
                    console.read()
                }
            }
        }

        console.write("DONE!")
    }
}
```

Run it with:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:orientation="vertical"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

    <ro.dobrescuandrei.jtextadventure.android.SimpleTextAdventureConsoleView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:id="@+id/consoleView"/>

</LinearLayout>
```



```kotlin
class MainActivity : AppCompatActivity()
{
    val gameRunner by lazy {
        val consoleView = findViewById<SimpleTextAdventureConsoleView>(R.id.consoleView)
        val consoleEmulator = AndroidConsoleEmulator(consoleView = consoleView)
        val game = IceCreamTroubleshootingAlgorithm(console = consoleEmulator)
        TextAdventureGameRunner(console = consoleEmulator, game = game)
    }

    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sample_activity)
        gameRunner.start()
    }

    override fun onDestroy()
    {
        gameRunner.dispose()
        super.onDestroy()
    }
}
```

**To pass arguments to ``IceCreamTroubleshootingAlgorithm``, simply use the constructor:**

```kotlin
class IceCreamMachineTroubleshootingAlgorithm
(
    console : IConsoleEmulator,
    var context : Context?,
    var someOtherArgument : Int
) : TextAdventureGame<IConsoleEmulator>(console)
{
    override fun run()
    {
        console.write(context.getString(R.string.some_message))
        //....................................................
    }

    override fun dispose()
    {
        //set context to null in order to prevent memory leaks!!!
        context = null
    }
}
```

**To customise the UI, simply extend ``TextAdventureConsoleView``:**

```kotlin
class MyCustomTextAdventureConsoleView : TextAdventureConsoleView
{
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun getLayoutId() = R.layout.my_custom_layout

    override fun removeSubviews()
    {
        //todo remove all subviews
    }

    override fun addTextView(text : String)
    {
        //todo add TextView. called on console.write(text)
    }

    override fun addButton(text : String)
    {
        //todo add Button. called on console.promptButtons()
        //todo on button click, call consoleEmulator?.onButtonClicked(text)
    }
}
```

To study a sample implementation, see [``SimpleTextAdventureConsoleView.kt``](https://github.com/andob/JTextAdventure/blob/master/jtextadventure-android/src/main/java/ro/dobrescuandrei/jtextadventure/android/SimpleTextAdventureConsoleView.kt) and [``simple_text_adventure_console_view.xml``](https://github.com/andob/JTextAdventure/blob/master/jtextadventure-android/src/main/res/layout/simple_text_adventure_console_view.xml) files.

```kotlin
class MainActivity : AppCompatActivity()
{
    val gameRunner by lazy {
        val consoleView = findViewById<MyCustomTextAdventureConsoleView>(R.id.consoleView)
        val consoleEmulator = AndroidConsoleEmulator(consoleView = consoleView, context = this, someOtherArgument = 4)
        val game = IceCreamMachineTroubleshootingAlgorithm(console = consoleEmulator)
        TextAdventureGameRunner(console = consoleEmulator, game = game)
    }

    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sample_activity)
        gameRunner.start()
    }

    override fun onDestroy()
    {
        gameRunner.dispose()
        super.onDestroy()
    }
}
```

### License

```
Copyright 2020 Andrei Dobrescu

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
