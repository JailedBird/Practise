# 线程相关 

## Android Handler消息机制 
熟悉Looper MessageQueue

## Java线程状态&同步&锁&内存模型

1、 线程状态流转及其相关API
2、 锁和同步 相关范式
3、 内存模型 voletile 相关 



彻底搞懂线程状态变化 

https://www.cnblogs.com/aspirant/p/8876670.html



 ![img](https://img2018.cnblogs.com/blog/137084/201908/137084-20190813080541362-1019213130.png) 

首先，当notify的唤醒之后，从Block状态进入Runnable， 获取时间片之后，进入Running状态， 如果 没满足条件，还是会进入到wait状态的；

因此，如下的代码在插入元素后（通过signal）唤醒，会获取到时间片进入RUnnable进入到Running中，然后继续await；

```
public E take() throws InterruptedException {
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (count == 0)
                notEmpty.await();
            return dequeue();
        } finally {
            lock.unlock();
        }
    }
```



## 线程池&阻塞队列

线程池类型和操作方案 

