# EasyPermissionManager
Android runtime permissions manager

# step 1: Add it in your root build.gradle at the end of repositories:

```
allprojects {

  repositories {
  
    ...
    maven { url 'https://jitpack.io' }
    
  }
  
}
```

# step 2: Add the dependency

```
dependencies {

  implementation 'com.github.DJSeokHo.EasyPermissionManager:EasyPermissionManager:1.0.7'
  
}
```

# How to use:
```

...
...
...

EasyPermissionManager.requestPermission(this,
    9999,
    "Permission",
    "permissions are necessary",
    "setting",
    listOf(Manifest.permission.CAMERA,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE)
    ) {

    yourMethodShouldRunAfterAllPermissionGranted()
}
        
...
...
            
// add this in your activity
override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
) {
    EasyPermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
}

// add this in your activity
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    EasyPermissionManager.onActivityResult(requestCode, resultCode)
    super.onActivityResult(requestCode, resultCode, data)
}
```
