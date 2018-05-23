## Java成绩爬虫（crawler1） 
### 程序功能：
* **通过登录到学校教务网查询考试成绩**


### 程序下载：[crawler1](https://github.com/Ai-yoo/Java_small-Applaction/archive/master.zip)

### 项目简要说明：
* 用户登录网站获取cookie，通过携带cookie跳转指定成绩页面，解析页面内容获取成绩

### 详细说明：
* 用户（学生）登录系统产生一个cookie，提取cookie，之后的每次登陆都需要携带coolie参数
* 跳转到指定网页，通过API提供的方法获取网页的源代码生成一个字符串
* 利用jsoup解析字符串形式的html网页源代码
* 提取出课程的名称，分数，存储到一个Set集合中

---

### 开发流程：
* 通过浏览器分析网站的请求方式为get方式，所以在网址后面添加参数用户名和密码登录系统  
<code>http://60.219.165.24/loginAction.do?zjh=用户名&mm=密码</code>，实际通过用户自己输入用户名和密码实现。   
* 创建URL对象，实际上可以理解为打开网址，获取连接  

```
    URL url = new URL(surl);   
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
```

* 通过浏览器的检查功能，在访问网址的时候，分析出需要获取cookie
* 提取cookie，携带cookie参数访问指定的显示分数的网页
* 通过输出流对象构建对象输出流对象，转换成字符流构建出一个html页面的源代码的字符串。
* 利用jsoup解析html标签，提取出需要的内容,创建Degree对象存储课程名称和分数。通过Set集合存储这些对象。
 

---

## Java电影下载连接爬虫（urlcrawler） 

### 程序功能：
* **爬取指定网站的电影下载链接**

### 程序下载：[urlcrawler](https://github.com/Ai-yoo/Java_small-Applaction/archive/master.zip)

### 项目简要说明：
* 获取网站中的每一个网页的源代码，提取电影下载链接存储，提取可以进一步代开的链接递归打开分析提取。

### 详细说明：


---

### 开发流程：
