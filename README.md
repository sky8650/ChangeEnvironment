# ChangeEnvironment
一键快速多渠道打包常用配置

## 关于打包

#### 在公司开发客户端项目的时候经常会遇到开发，测试，正式等环境的来回切换，从而需要切换接口的地址以及不同环境的配置！比如我们需要判断当前的环境是正式还是开发环境以此来加载不同的推送ID！当然我们可以写一个配置文件，每次打包时注释掉不需要的代码。 但这样做常常会有以下几个问题<br>
* 不安全，因为是手动注释，不可避免的发生人为的失误
* 重复的工作，每次都要打开配置文件浪费时间
* 增加代码量，而且不优雅

