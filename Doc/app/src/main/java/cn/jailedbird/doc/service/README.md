startService 启动多次 针对Intent, 一次取消全部玩完
bindService 绑定的对象是 ConnectService, 取消也是ConnectService
通过这个区分是谁启动 针对同一个ConnectService 不管调用多次bindService都是指只会启动一次 
unbindService也需要传递这个 ConnectService 


start和bind都取消 Service才会被销毁;知道了么?


如何更深层次的交互呢?  直接获取Service中的Binder对象 后者直接是在onServiceConnected 进行协程的操作; 不断轮询任务 针对同一Service返回的Binder是一样的;
```
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            (service as? TestService.ZjcBinder)?.let {
                "Main bind you".log()
                lifecycleScope.launch{
                    while (true){
                        delay(1000)
                        it.log()
                    }
                }
                it.log()
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            "Main unbind you".log()
        }

    }
```