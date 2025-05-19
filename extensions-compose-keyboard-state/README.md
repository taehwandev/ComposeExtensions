## Summary
You can use Compose Modifiers to close the keyboard and collect related events.

If you want to close the keyboard and clear focus, you can use `keyboardHide()`.

To check the current visibility state of the keyboard, you can use `rememberKeyboardVisible()`.

## Use Code

### Use scaffold modifier

```kotlin
Scaffold(
    modifier = Modifier
        .imePadding()
        .keyboardHide()
) {
}
```
 
### when checking status

```kotlin
val keyboardState by rememberKeyboardVisible()

LaunchedEffect(showKeyboard) { // Or DisposableEffect(keyboardState) if you want to react when keyboardState changes
    // Use keyboard state
}
```

## Sample

![image](../images/sample.png)
