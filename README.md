## Summary

Modifier for handling keyboard state.

You can know the open/closed state of the keyboard.
If you register the modifier, the keyboard closes when you touch the background, and you can know the status.

- compose 1.4.0-alpha03
- compose compiler 1.4.0-alpha02
- kotlin 1.7.21

## Download

Use gradle - compose 1.3.3, foundation 1.3.1, compose compiler 1.3.2, kotlin 1.7.20

```groovy
implementation "tech.thdev:extensions-compose-keyboard-state:1.3.3"
```

Use gradle.kts

```kotlin
implementation("tech.thdev:extensions-compose-keyboard-state:1.3.3")
```

Use gradle - compose 1.4.0-alpha04, compose compiler 1.4.0-alpha02, kotlin 1.7.21

```groovy
implementation "tech.thdev:extensions-compose-keyboard-state:1.4.0-alpha04"
```

Use gradle.kts

```kotlin
implementation("tech.thdev:extensions-compose-keyboard-state:1.4.0-alpha04")
```

Release version are available in [Sonatyp's repository.](https://search.maven.org/search?q=tech.thdev)

## Use Code

### Use scaffold modifier

```kotlin
CompositionLocalProvider(
     LocalMutableExKeyboardStateSourceOwner provides MutableExKeyboardStateSource()
) {
     Scaffold(
         modifier = Modifier
             .removeFocusWhenKeyboardIsHidden()
     ) {
     }
}
```
 
### when checking status

```kotlin
val keyboardState by mutableKeyboardStateSource.keyboardState()

DisposableEffect(showKeyboard) {
    // Use keyboard state
    onDispose {
    }
}
```

## Sample

![image](images/sample.gif)
