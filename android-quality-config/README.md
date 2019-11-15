Android Quality Config
======================

This package contains basic rulesets and configs for pmd, lint, spotbugs and checkstyles, which are depends on ```check``` gradle task.
This package also contains default codestyle and inspection rules for IDEA.

## Requirement
Gradle 4.9  
Android Gradle Plugin 3.3 (for Android projects)

## Setup
Define ```code_quality_rootDir``` variable at top-level build.gradle.
For example:
```
buildscript {
    ext {
        code_quality_rootDir = "${project.rootDir}/android-quality-config/"
    }
}
```

## Usage
Add this statement to module-level build.gradle
```
apply from: "${project.code_quality_rootDir}/config/quality/quality.gradle"
```

Reports are generated into build/reports directory.

Note: Mixed Kotlin / Java projects are partially supported. Java sources are checked in ```src/*/java``` and kotlin sources are checked in ```src/*/kotlin```

Basic configuration was made by Attila Polacsek

## Setting up Checkstyle plugin in IDEA

The most frequent errors are coming from Checkstyle. To speed up development, you can see these errors
directly in the editor without building the project.

1. Install the plugin: **Preferences** -> **Plugins** -> **Browse repositories**. Search for `Checkstyle-IDEA` and hit **Install**, then restart IDEA.
2. Configure the plugin: **Preferences** -> **Other settings** -> **Checkstyle**. Add a new ruleset by hitting the **+** button under the list. In the popup window,
you can add any description, and browse for the file *project.rootDir/android-quality-config/quality/checkstyle/checkstyle.xml* then hit **Next**. In the next window,
leave everything as is and hit **Next**. After the new ruleset was added, activate it by checking it,
and hit **Apply**.
3. You can now see Checkstyle errors in the editor directly.

## Setting up detekt plugin in IDEA

1. Install the plugin: **Preferences** -> **Plugins** -> **Browse repositories**. Search for `Detekt` and hit **Install**, then restart IDEA.
2. Configure the plugin: **Preferences** -> **Tools** -> **detekt**.
  * Check **Enable Detekt**
  * Check **Enable Formatting**
  * Check **Treat detekt findings as errors**
  * In the **Configuration File**, add *project.rootDir/android-quality-config/quality/detekt/detekt-config.yml* (with substituting `project.rootDir`)
3. Hit **Apply**. You can now see detekt errors in the editor directly.
