# EasyPermissionManager
Android runtime permissions manager

step 1: Add it in your root build.gradle at the end of repositories:

allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}

step 2: 

dependencies {
  implementation 'com.github.DJSeokHo.EasyPermissionManager:EasyPermissionManager:1.0.7'
}
