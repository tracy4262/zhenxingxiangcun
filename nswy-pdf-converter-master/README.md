### nswy-pdf-converter
![](https://img.shields.io/badge/language-java-orange.svg) ![](https://img.shields.io/badge/qq-923260818-brightgreen.svg) ![](https://img.shields.io/badge/build-%20passing-brightgreen.svg) ![](https://img.shields.io/badge/created-Mar-brightgreen.svg)

#### 概述
农事无忧图片、文档转换

#### 功能说明

1. 图片base64上传

   注：上传文件格式仅支持jpg、png

2. 上传图片转base64

   注：上传文件格式仅支持jpg、png

3. 上传文件转pdf接口

   注：jpg、png、pdf不转换文件格式直接返回存储地址，doc、docx上传转换成pdf文件格式返回地址

#### 注意事项

1. 需启动openoffice服务

   启动命令：

   ```
   nohup /opt/openoffice4/program/soffice -headless -accept="socket,host=192.168.7.42,port=8100;urp;" -nofirststartwizard &
   ```

2. 需启动fastdfs服务

   启动命令：

   ```
   /usr/bin/fdfs_trackerd /etc/fdfs/tracker.conf
   /usr/bin/fdfs_storaged /etc/fdfs/storage.conf  
   ```

3. 运行脚本见shell

#### 示例

##### 1.图片base64上传

   注：上传文件格式仅支持jpg、png

   接口地址：

​	http://xxxx:8085/imgbase64/upload

   参数示例：

```
{
 "fileName" : "test.jpg",
 "fileData" : "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDA=="
 }
```

  注：fileData为图片base64串

  返回数据示例：

1.成功

```
{

    "data": {

        "src": "http://192.168.7.61/00/71/wKgHPVqo4t2ACoWiAAIQs4bUH5Y107.jpg",

        "name": "test.jpg"

    },

    "code": "0",//0表示成功，其它表示失败

    "msg": ""

}
```

2.失败

```
{
     "code": 1 //0表示成功，其它表示失败
     ,"msg": "上传文件格式仅支持doc、docx、pdf、jpg、png！" //失败信息
 }
```



##### 2.上传图片转base64

注：上传文件格式仅支持jpg、png

接口地址：

​	http://xxxx:8085/imgbase64/converter

参数示例：

```
参数名：file
<input type="file" name="file" />
```

返回数据示例：
1.成功

```
{
     "code": 0 //0表示成功，其它表示失败
     ,"msg": "" //失败信息
     ,"data": {
     "base64": "/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDA==" //base64串
     ,"name": "test.jpg" //文件名
     }
 }
```

2.失败

```
{
     "code": 1 //0表示成功，其它表示失败
     ,"msg": "上传文件格式仅支持jpg、png！" //失败信息
 }
```



##### 3.上传文件转pdf接口

注：jpg、png、pdf不转换文件格式直接返回存储地址，doc、docx上传转换成pdf文件格式返回地址

接口地址：

​	http://xxxx:8085/pdf/upload/file

参数示例：

```
参数名：file
<input type="file" name="file" />
```

返回数据示例：
1.成功

```
{
     "code": 0 //0表示成功，其它表示失败
     ,"msg": "" //失败信息
     ,"data": {
     "src": "http://xxx/00/70/xxxx.pdf" //pdf文件url
     ,"name": "test.doc" //文件名
     }
 }
```

2.失败

```
{
     "code": 1 //0表示成功，其它表示失败
     ,"msg": "上传文件格式仅支持doc、docx、pdf、jpg、png！" //失败信息
 }
```



##### 4.根据文件资源url返回base64串接口

接口地址：

​	http://xxxx:8085/base64/converter

参数示例：

```
{
	"url" : "http://XXXX/00/72/wKgHPVqrHjeAS7QOAAL2BMGZzaI658.pdf"
}
```

返回数据示例：
1.成功

```
 {
     "data": {
     "base64": "iVBORw0AAAAASUVORK5CYII="//返回的资源文件base64串
     },
     "code": "0",//0表示成功，其它表示失败
     "msg": ""//失败信息
 }
```

2.失败

```
{
     "code": 1 //0表示成功，其它表示失败
     ,"msg": "根据资源文件url转换文件base64串失败！
				失败原因:XXX" //失败信息
 }
```



#### 备注

如果你有好的意见或建议，欢迎给我们提issue或pull request。