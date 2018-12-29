# ChangeEnvironment
一键快速多渠道打包常用配置

## 关于打包

##### 在开发客户端项目的时候经常会遇到开发，测试，正式等环境的来回切换，从而需要切换接口的地址以及不同环境的配置！比如我们需要判断当前的环境是正式还是开发环境以此来加载不同的推送ID！当然我们可以写一个配置文件，每次打包时注释掉不需要的代码。 但这样做常常会有以下几个问题
* 不安全，因为是手动注释，不可避免的发生人为的失误
* 重复的工作，每次都要打开配置文件浪费时间
* 增加代码量，而且不优雅


##### 配置环境几乎是新建项目必做的工作，主要分为以下几个步骤
* 配置manifest中的占位符
* 配置gradle的各种环境信息。例如是否需要混淆，读取不同的签名等
* 配置多渠道打包
* 配置并读取签名文件信息
* 通过代码读取环境信息并进行判断
* 输出当前渠道环境下的apk文件

### 配置步骤
#### 第一步：配置manifest中的占位符
```
 <meta-data
            android:name="server_mode"
            android:value="${SERVER_MODE}"/>
```
#### 第二步：配置manifest中的环境信息

```
 buildTypes {
        release {
            debuggable false//是否调试模式
            signingConfig signingConfigs.release//签名配置
            zipAlignEnabled false//是否压缩
            minifyEnabled false//是否混淆
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
            manifestPlaceholders = [SERVER_MODE: "RELEASE"]//mainfest的占位符
            buildapkName()
        }

        _test {
            debuggable true//是否调试模式
            signingConfig signingConfigs.release//签名配置
            zipAlignEnabled false//是否压缩
            minifyEnabled false//是否混淆
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
            manifestPlaceholders = [SERVER_MODE: "TEST"]//mainfest的占位符
            buildapkName()
        }
    }
```
#### 第三步：配置多渠道打包（如果不需要多渠道打包，此步骤可省略）
```
 //多渠道配置
    productFlavors {
        _360shouzhu {
            manifestPlaceholders = [UMENG_CHANNEL_VALUE: "360"]
        }
        ali {
            manifestPlaceholders = [UMENG_CHANNEL_VALUE: "ali"]
        }
        oppo {
            manifestPlaceholders = [UMENG_CHANNEL_VALUE: "oppo"]
        }
        xiaomi {
            manifestPlaceholders = [UMENG_CHANNEL_VALUE: "xiaomi"]
        }

    }
  //  productFlavors.all { flavor -> flavor.manifestPlaceholders = [UMENG_CHANNEL_VALUE: name] }
```

#### 第四步：配置并读取签名文件信息
```
signingConfigs {
        release {
            storeFile
            storePassword
            keyAlias
            keyPassword
        }

    }
      
 //读取签名配置文件,为保证安全配置文件可放在本地磁盘中
def getSigningProperties() {

    def propFile = file('signing.properties')
    if (propFile.canRead()) {
        def Properties props = new Properties()
        props.load(new FileInputStream(propFile))
        if (props != null && props.containsKey('STORE_FILE') && props.containsKey('STORE_PASSWORD') &&
                props.containsKey('KEY_ALIAS') && props.containsKey('KEY_PASSWORD')) {
            android.signingConfigs.release.storeFile = file(props['STORE_FILE'])
            android.signingConfigs.release.storePassword = props['STORE_PASSWORD']
            android.signingConfigs.release.keyAlias = props['KEY_ALIAS']
            android.signingConfigs.release.keyPassword = props['KEY_PASSWORD']

        } else {
            println 'signing.properties found but some entries are missing'
            android.buildTypes.release.signingConfig = null
        }
    } else {
        println 'signing.properties not found'
        android.buildTypes.release.signingConfig = null
    }
} 
```

#### 第五步：读取环境信息，并进行判断
```
  /**
     * 获取metaData
     * @param context
     * @param metaKey
     * @return
     */
    public static String getMetaValue(Context context, String metaKey) {
        Bundle metaData = null;
        String apiKey = null;
        if (context == null || metaKey == null) {
            return null;
        }
         ''''''省略
        return apiKey;
    }

   /**
     * 获取当前的环境
     * @param context
     */
    public static void init( Context context) {
        USE_SERVER_MODE = Enum.valueOf(Mode.class, DeviceUtil.getMetaValue(context, "server_mode"));
    }
```
#### 第六步： 输出apk文件
```
//生成文件的名称
def  buildapkName(){
  android.applicationVariants.all { variant ->
       variant.outputs.all { output ->
           if (outputFile != null && outputFile.name.endsWith('.apk')) {
               def fileName = "evn_${variant.productFlavors[0].name}_${variant.buildType.name}.apk"
               outputFileName = new File(fileName)
           }
       }
     }
  }
```
##### 全文纯手工编写，如果您感觉对您有帮助请动动小手点个star哦！如果有任何疑问或者意见可在Issues中留言<br>
https://github.com/sky8650/ChangeEnvironment









