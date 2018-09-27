# FragmentSwitcher
一款简易的自动管理fragment回退栈的切换工具，可以快速的实现多Activity多Fragment界面搭建，一个模块一个Activity通过切换Fragment实现UI界面切换。

# 该库有一下特点：
1，支持配置默认展示的Fragment；
2，支持Fragment跨Activity跳转，弱化了Activity，避免过多的创建Activity；

# 使用

在根build.gradle中配置：    
	allprojects {  
		repositories {  
			...  
			maven { url 'https://jitpack.io' }    
		}    
	}      
在需要依赖的地方添加：    
	dependencies {  
	        implementation 'com.github.wangzhanxian:FragmentSwitcher:v1.0.0'  
	}    


通过在布局文件中配置自定义的FragmentSwitcher（继承自FrameLayout）即可，该自定义View相当于Fagment切换的容器，
你也可以在布局文件中配置默认启动的Fragment，如下：  
<com.wzx.app.fastui.FragmentSwitcher xmlns:android="http://schemas.android.com/apk/res/android"  
	android:layout_width="match_parent"  
	android:layout_height="match_parent"  
	xmlns:app="http://schemas.android.com/apk/res-auto"  
	android:id="@+id/fs_switcher"  
	app:defaultFragmentName="com.wzx.app.smartui.fragments.MainFragment" />  

也可以在代码中设置，如下：  
fs_switcher.setDefalutFragmentName(getDefaultFragmentName()).start();  

切换Fragment 如下：  
fs_switcher.switchFragment(targetFragmentClass.getName(),extras);  
只需要两个参数，要开启的Fragment类名和要传递的参数；  

如果你想从AActivity的Fragment跳转到BActivity的Fragment，你只需要将要跳转的Fragment类上添加注解@Host(所属的Activity)即可。  

并且支持goback操作，如果你想反回上一个页面，你只需要调用：  
fs_switcher.goback();  

其他操作可自行参照BasesActivity。  

如果你的容器Activity是SingleTask等启动模式时，需要在onNewIntent方法进行处理；  
