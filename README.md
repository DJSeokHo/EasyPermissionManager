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

  implementation 'com.github.DJSeokHo:EasyPermissionManager:1.0.9'
  
}
```

# How to use:
```
class EasyPermissionManagerDemoActivity : AppCompatActivity() {

    private val permissionManager = EasyPermissionManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_easy_permission_manager_demo)

        findViewById<Button>(R.id.button).setOnClickListener {

            permissionManager.requestPermission(
                "Permission",
                "permissions are necessary",
                "setting",
                arrayOf(Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
            ) {

                yourMethodShouldRunAfterAllPermissionGranted()
            }

        }
    }

    private fun yourMethodShouldRunAfterAllPermissionGranted() {
        Toast.makeText(this, "All permission is ok, go go go", Toast.LENGTH_SHORT).show()
    }
}
```
