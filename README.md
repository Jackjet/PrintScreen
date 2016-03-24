# PrintScreen
## 介绍
一个Socket小程序，客户端负责后台监听热键alt+S，保存剪切板中最新截图，保存到本地文件夹中，同时将
该文件发送到服务器端；服务器端负责接收文件，并将其保存到服务器文件夹中。

## 实现
服务端与客户端的通信由socket实现，文件传输网上都找得到代码。一般情况，java是无法进行后台监听键盘或者鼠标，它都需要关联
一个swt。要实现后台监听，需要java利用jna/jni调用本地dll文件来实现。这里使用了第三方jar包jintellitype实现。

注意：
+ jintellitype的配置包括：将jintellitype的jar包放入项目中；将对应的dll文件放到**包com.melloware.jintellitype**下。
+ 带jar包的项目生成jar文件时，需要在MANIFEST.MF文件中添加这些jar包所在的路径信息。

## 使用
+ client

表示客户端，运行文件夹下的client.bat即可启动客户端。
+ server

表示服务端，运行文件夹下的server.bat即可启动服务端。
+ jre7

为程序运行所需的环境，该环境为绿色简化版的64位jre1.7。
+ run.bat

点击该文件，可选择启动客户端或服务端。
