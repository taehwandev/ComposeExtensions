## Summary

Modifier for handling keyboard state.

You can know the open/closed state of the keyboard.
If you register the modifier, the keyboard closes when you touch the background, and you can know the status.

- compose 1.5.0-alpha02
- compose compiler 1.4.5(require java 17.)
- kotlin 1.8.20

## Download - compose 1.5.0-alpha02

Use gradle - compose 1.5.0-alpha02, compose compiler 1.4.5, kotlin 1.8.20

```groovy
implementation "tech.thdev:extensions-compose-keyboard-state:1.5.0-alpha02"
```

Use gradle.kts

```kotlin
implementation("tech.thdev:extensions-compose-keyboard-state:1.5.0-alpha02")
```

## Download - compose 1.4.1

Use gradle - compose 1.4.1, compose compiler 1.4.5, kotlin 1.8.20

```groovy
implementation "tech.thdev:extensions-compose-keyboard-state:1.4.1"
```

Use gradle.kts

```kotlin
implementation("tech.thdev:extensions-compose-keyboard-state:1.4.1")
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

![image](images/sample.png)
