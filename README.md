# 高性能并发java插件--OpenBigPipe 
## OpenBigPipe介绍
OpenBigPipe是一款实现页面pagelet并发执行的java插件,主要用于java web中提高页面加载速度.支持以 BigPipe 的方式返回你的 pagelet 执行结果，和以一切可能并发的方式提高你的页面响应速度。


## 插件特点
1. 支持大部分MVC架构,如struts2,struts1,SpringMVC,servlet等.
2. 完全 POJO,是一个轻量级框架. 基于注解配置方式,避免应用程序与插件的过度依赖问题,即不用实现特定接口来完成插件的工作.
3. 高性能,OpenBigPipe提供了**BIGPIPE**(带优先级的BigPipe思想,是异步加载方式)和**CONCURRENT**(并发,是同步方式)来实现对页面的快速加载机制.首先需要将页面划分成多个可以并发执行的 pagelet,同时不同页面也可以复用这些pagelet。在BIGPIPE方式下实现的所有 pagelet 将作为页面的首次内容返回模式,只要某个pagelet渲染完成并且符合优先级条件则立刻返回给前端;CONCURRENT通过并行方式完成每个pagelet的渲染并组合成最终页面放回给前段.
4. 简化用户配置,使用注解配置以及容器自动完成某些参数的注入,使得用户可以
5. 支持缓冲和降级处理. 只要用户实现相应的接口便可支持相关工作,缓存时保存解析后的pagelet,因为有些pagelet的数据变化不大,可以缓冲提高页面的加载速度.如果在pagelet的执行方法中出现错误则采用用户配置的降级处理方式处理.
6. 可拓展性,使用AOP模式,策略模式,模板模式等提升后期开发的可拓展性.
7. 灵活性,采用在控制性中调用插件入口的方式为控制器中的业务逻辑处理提供更大的灵活性.可以根据不同的业务请求渲染和返回不同的页面.

## 插件组成
插件主要由以下部分组成:
1. BigPipe(BigPipeBean): 表示某个使用插件执行的请求.对应action的方法和该方法所在对象.
2. pagelet: 前端将页面分为多个pagelet,后端需要为每个pagelet配置对应的执行方法来完成数据的获取和页面渲染.
3. BigPipeConcroller: 控制器,主要是根据BigPipe的执行方式调用相应的Executor(框架内部的接口)来完成pagelet的数据处理和页面渲染等.
4. Executor: 执行器,包括ConcurrentPipeExecutor(执行CONCURRENT方式)和BigPipeExecutor(BIGPIPE方式);
5. PageletWorker: 负责执行pagelet对应方法和页面渲染.

## 使用
### 准备
首先需要把页面分为多个pagelet
index.ftl:

		<html>
		<head>
		    <title>struts2-bigpipe-plugin</title>
		    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		    <link rel="stylesheet" href="http://j2.s2.dpfile.com/s/c/app/main/base-old.min.83311f9422f347eb87236b9a56112b8f.css" type="text/css" />
		    <!- css配置一般放在头部,js一般不放在这边,是放在页面尾部 ->

		</head>
		<body>
		<style type="text/css">
		    tr {
			height: 250px;
		    }
		</style>
		<table width="100%">
		    <tr border="1">
			<!- 配置不同的pagelet ->
			<td width="50%" bgcolor="#f0f8ff"><div id="one">${one}</div></td>
			<td width="50%" bgcolor="#faebd7"><div id="two">${two}</div></td>
		    </tr>
		    <tr>
			<td width="50%" bgcolor="#7fffd4"><div id="three">${three}</div></td>
			<td width="50%" bgcolor="#8a2be2"><div id="four">${four}</div></td>
		    </tr>
		</table>
		<#--</body>-->
		<#--</html>-->


one.ftl

			<h2>${user.name} ${time} ms Part1</h2>


two.ftl 

			<h2>${time} ms part2</h2>


three.ftl

			<h2>${time} ms part3</h2>


four.ftl

			<h2>${time}ms part4</h2>


### 公共配置
这部分是不管使用什么框架都必须配置的.
1. BigPipeFilter过滤器,配置在web.xml最前面:

		<filter>  
	      	<filter-name>bigPipeFilter</filter-name>  
	      	<filter-class>org.opensjp.openbigpipe.core.filter.BigPipeFilter</filter-class>  
		    </filter>  
		    <filter-mapping>  
		      	<filter-name>bigPipeFilter</filter-name>  
		      	<url-pattern>/*</url-pattern>  
		    </filter-mapping>


2. 配置AOP,因为本框架是基于AOP的

		 <aop:aspectj-autoproxy proxy-target-class="true" /> 
		 <bean class="org.opensjp.openbigpipe.core.interceptor.BigPipeAspect"/> 

3. 为每个前段的pagelet配置一个执行方法

		@PageletSet
		public class PipeFour{

			    @Param
			    private String name;
			    @Param
			    private User user;
			    @Param
			    private int age;
			    @Param
			    private int time;
			    
			    @Pagelet(key="four")
			    public String execute() {
				age = 100;
				time = 5000;
				SleepUtils.sleep(time);
				return "demo/one.ftl";
			    }
			}

			@PageletSet
			public class UserPipes {
				@Param
			    private String name;
				@Param
			    private User user;
				@Param
			    private int age;
				@Param
			    private int time;
	
				@Pagelet(key="one")
				public String pipeOne(){
					time = 500;
					if (name.equals("down")) {
					    user = null;
					    user.setName("");
					}
					SleepUtils.sleep(time);		
					return "demo/one.ftl";
				}
				@Pagelet(key="two")
				public String pipeTwo(){
					time = 1000;
					SleepUtils.sleep(time);
		
					return "demo/two.ftl";
				}
	
				@Pagelet(key="three")
				public String pipeThree(){
					age = 50;
					time = 3000;
					SleepUtils.sleep(time);
		
					return "demo/three.ftl";
				}
			}

**@PageletSet**,表示pagelet集合.标注在类上,表示该类含有一个或多个Pagelet,便于插件的查找和解析.
**@Pagelet** 对应页面的一个pagelet,用于该pagelet的数据处理.即页面渲染(渲染部分用其他组件进行).pagelet中有三个属性:
	key: 表示键值,这个时必须的用于定位pagelet,且key不能重复.
	priority : 优先级,可以选择Prioriy.LOW,Priority.NORMAL,Priority.HIGH ,默认为Priority.NORMAL.
**@Param** :表示值由插件进行注入
	
### SpringMVC
控制器:
		@Controller
		public class LoginAction{
		    String name;
		    @Autowired
		    private User user;
		    @Param 
		    private BigPipeController  bigPipeController;
		    
		    @BigPipe
		    @RequestMapping("/login")
		    @ResponseBody
		    public String login(User user) throws Exception {
		    	//这边处理业务逻辑
		    	//....

		    	String[] pageletKeys = new String[]{"one","two","three","four"};
				return bigPipeController.execute("index.ftl", pageletKeys);
		    }
		    
		    @RequestMapping("/test")
		    @ResponseBody
		    public String test(User user) throws Exception {
		    	System.out.println("------------");
		    	return "index";
		    }

		}

插件注解:
**@Param** : 表示该值通过插件进行注入,BigPipeController这个是必须的,表示插件的控制器.变量名可以自定定义.由插件自动注入.
**@BigPipe** : 表示该方法是使用BigPipe插件执行的方法.可以处理一些业务逻辑.在内部调用任意位置调用bigPipeController.这样可能根据不同的请求参数返回同步的渲染页面.比较灵活.包含一个参数,ExecuteType表示执行类型,可以选择CONCURRNET和BIGPIPE.

springMVC注解:
**@ResponseBody** : 表示可以返回空白页面,主要是用于

BigPipeController的execute方法是让插件使用并发或bigPipe方式执行页面的渲染和返回.主要有两种重载方式:
			/**
			 * 该方法是整个框架的核心，负责执行请求.
			 * @param viewFrame  页面视图框架 相对路径
			 * @param pageletKeys 　pagelet的key值数据,默认的div id是key
			 * @return
			 */
			public String execute(String viewFrame,String[] pageletKeys);
			/**
			 * 
			 * @param viewFrame 视图框架
			 * @param pageletMap　各个pagelet的pagelet　key和div id的映射关系
			 * @return
			 */
			public String execute(String viewFrame,Map<String,String> pageletMap);

此时运行便可以达到并发执行的要求了.

## Struts2
其他公共配置和spring一样,这里将action中的配置.

		public class LoginAction extends ActionSupport {
		    String name;
		    @Autowired
		    private User user;
		    @Param
		    private BigPipeController  bigPipeController;
		    
		    @BigPipe
		    public String login() throws Exception {
			//可以根据需要渲染页面和错误页面,比较灵活
		    	if(user != null){
		    		if(user.getRole().equals("admin")){
		    			String[] pageletKeys = new String[]{"one","two","three","four"};
		    			return bigPipeController.execute("index.ftl", pageletKeys);
		    		}else{
		    			String[] pageletKeys = new String[]{"one","two","three","four"};
		    			return bigPipeController.execute("index.ftl", pageletKeys);
		    		}
		    	}
			return "index";
		    }
		}

这部分可以看到插件的灵活性,即可以根据不同的请求参数进行渲染不同的页面并返回.

## 设计原理
1. 当客户端发送一个请求时,首先经过BigPipeFilter过滤器,获取该请求的request和reponse并保存到BigPipeRequestContextHolder中.便于在AOP中获取.
2. 请求执行的action方法带@BigPipe注解,则会被BigPipeAspect拦截器拦截.那么会从中BigPipeRequestContextHolder获取request和reponse. 获取@BigPipe注解方法和对应类的相关信息,封装成BigPipeBean并转给BigPipeInterceptionProcess执行,默认采用SimpleBigPipeInterceptionProcess类. 
3. 在SimpleBigPipeInterceptionProcess内部首先会初始化OpenBigPipe插件,这里采用double-check方式执行,如果容器已经初始化了则放回,否则进行初始化.初始化完成相关配置,如获取,解析和注册所有的pagelet;已经完成其他配置如:线程池设置,配置用户用户拓展的缓存接口,降级处理接口等.
4. 完成初始化后,创建一个BigPipeController,将解析后的@BigPipe的注解信息设置到BigPipeController,并将BigPipeController对象注入到 @BigPipe注解action方法所在对象的对应属性中,用于调用.
5. 然后执行action方法.
6. action方法主要时完成用户的简单业务逻辑如数据的获取,为了提高响应速度,一些数据获取操作,页面渲染等业务逻辑交给了pagelet对应的方法执行.所以使用插件执行的方法应该配置多个pagelet,以及主页面模板,pagelet页面模板等. pagelet在配置的时候一般时配置key和view两个选项,分别代表键值和视图路径.前端的为每个pagelet时用div表示,并且有个对应的div id. BigPipeController提供了两个重载方法,一个是传入pagelet key数组和主页模板视图,默认情况下key值和div id值一样.另一个时传入key和div id映射的map以及主页模板视图路径. 然后action方法调用BigPipeController 的execute方法,便开始了插件的执行部分.
7. BigPipeController获取解析BigPipeBean的相关值,这些值时带有@Param注解的值.然后根据BigPipeBean 的执行方式 BIGPIPE或CONCURRENT调用不同的执行器.BIGPIPE是以BigPipe思想为基础的执行方式,并发的渲染pagelet,某个pagelet渲染完成就直接放回给前段.在这种模式下可支持为每个pagelet配置优先级,按由高到底的方式返回给前端. CONCURRENT就是在后端并发的处理数据和完成pagelet渲染,最终将渲染完成的整个页面返回给前段.
8. 以BIGPIPE执行方式为例,首先将首页框架渲染返回给前端,然后根据key获取和生成pagelet代理,并构建一个ExecutorCompletionService执行器将所有的pagelet代理封装到PageletWorker的任务中然后按照优先级用一个提交给ExecutorComplectionService执行. 并将这些执行的结果按优先级顺序放回,返回过程不是等待所有pagelet执行完成才返回的,而是只要执行完成就直接返回去.
9. PageletWorker内部,首先调用用户的缓存方法判断key对应的pagelet是否存在,如果存在则从缓存中获取并返回给前段.缓存时留给用户实现的接口,pagelet渲染页面的备份和缓冲根据用户需要进行备份,如果用户不配置该缓存的话,则表示不使用缓存.如果缓存储不存在或没有配置缓存则执行pagelet代理的execute方法,最后对所对于应的页面进行渲染.如果配置了缓存则进程保存到缓存中,否则直接返回.如果PageletWorker整个过程中发生了错误,则先看用户是否配置了降级处理,如果配置了则使用使用用户定义的方式进行处理.否则将错误打印到前端对应的pagelet中.

## 设计说明
1. 使用AOP模式
1) 使用AOP模式主要是减少用户的工作量,如request和reponse的获取
2) 可以使用注解形式配置相关信息,如插件的执行方式:BIGPIPE或CONCURRENT. 以及首页视图框架.
如果在内部直接执行BigPipeController的话,那注解上的信息无法获取到,所以使用AOP方式一方面是为了获取request和reponse减少用户的配置,同时通过该方式给用户一个同一个的调用方式,屏蔽不同MVC框架request和reponse的获取方式.同时可以@BigPipe的注解配置信息,因为该插件时完全支持注解形式,同时注解形式配置相关形式,一方面提高工作效率,另一方面可以简化代码.最后这种基于AOP方式的模式可以方便后期的拓展,如在执行前后插件可以完成一些其他功能.


2. 针对不同的框架采用了不同的request和response获取方式,为了在spring AOP中统一获取方式,加入了过滤器BigPipeFilter.因为以上采用了AOP模式来屏蔽这种request和response的获取区别,所以需要在AOP通知内部获取request和response.
struts2里关于 Spring Aop 切面方法里直接使用 com.opensymphony.xwork2.ActionContext 就可以得到了Session、Request、Response.
springMVC获取request和response的几种方式:注解,配置RequestContextListener监听器并从RequestContextHolder中获取对应的值(这种方式获取的response会抛出,不同的版本体现不同,在spring4.x 的一些版本中可以使用该特性,但3.x获取的response会报空),通过参数设置. 每种框架的获取方式不同,所以为了在AOP接口中统一获取所有的request和response,在框架中配置了BigPipeFilter,用于获取request和response并保存到ThreadLocal中.之后在AOP里可以获取这里面的值.

3. 完全POJO框架,意味者要使用注解形式来插件与应用的融合,而不是使用接口.
使用注解有以下好处:
一,可以减少应用中类的数目.很多插件使用接口方式来定义pagelet,一但页面中的pagelet数目很多的话旧造成了大量的类.
二,是方便配置,pagelet的配置信息直接在@Pagelet中配置旧可以了,不需要到spring xml文件为每个pagelet类设置相关信息.
三,方便统一管理. Pagelet可以重复使用,因为支持一个方法作为一个pagelet,所以实现可以通过注解解析出pagelet的配置,但需要的时候直接从容器中获取pagelet生成代理实例.
四,减少用户工作量,如果pagelet类中某些变量只要配置@Param,插件可以自动将从Action中获取的值注入到对应的属性中.

4. 容器
容器主要是存放解析得到的各种pagelet的定义,当需要使用某个pagelet的时候就不需要再次去整个类中去搜索.只需要通过key从容器中可以直接取出并生成代理类.和spring 容器类似.


## 设计模式

### AOP模式
使用AOP来拦截使用插件执行的请求.用于获取相关配置信息,及为后期提供拓展.

### 代理模式
为每个Pagelet提供代理去执行对应的方法,代理模式可以处理一些异常情况.如果发生异常则可以使用用户拓展的处理方式.

### 策略模式
1. 对于不同的视图采用不同的策略处理. 
2. 对于不同的执行配置采用不同执行方式

### 模板方法
在解析pagelet时使用模板方法,定义一个解析的框架,首先获取所有相关注解的class,解析这些class,注册到容器中.每一步对应一个方法,这些方法留给子类实现.

### 工厂方法
用于生成一些对象,如视图解析类对象,pagelet解析对象,pagelet注解寻找器等

### 单例模式
无状态类是安全类,所以很多无状态的处理类都采用了单例模式.如视图解析器,





