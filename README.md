Wallpaper to Lock
=================

*Copy wallpaper from the home screen to the lock screen.*

I was able to replace my own [Lock Screen Darkifier](http://github.com/Kwpolska/lockscreendarkifier) with the *Modes and Routines* feature of my Samsung phone (there’s an option to *change lock screen wallpaper*, on schedule, and go back to the previous wallpaper after the scheduled time period ends). The problem with the system solution is that sometimes, the night wallpaper ends up being permanent (usually when editing the lock screen or rebooting the phone at night — both rare, but possible things).

So I copied parts of the old codebase into a new project that offers a single feature: copy the current home screen wallpaper to the lock screen. That’s it.

This app requires full access to your storage. This is mandated by Android, as this is the only permission level that can access the current wallpaper. (There’s a more granular option available for system apps, but I obviously can’t use that.) Rest assured the app doesn’t look at any of your other data (and it does not have any network permissions, so there is no way to exfiltrate it). You can check the code, there’s very little of it.
