1. Wrapper对象持有变量存储结果值，线程间传递对象引用，主线程等待子线程运行完成
2. Wrapper对象持有变量存储结果值，线程间传递对象引用，主线程调用sleep方法等待子线程运行完成
3. Wrapper对象持有变量存储结果值，线程间传递对象引用，主线程通过CountDownLatch等待子线程运行完成
4. Wrapper对象持有变量存储结果值，线程间传递对象引用，主线程通过CyclicBarrier等待子线程运行完成
5. Wrapper对象持有变量存储结果值，线程间传递对象引用，主线程通过通过同步块并wait，等待子线程运行完成时notify触发
6. Wrapper对象持有变量存储结果值，线程间传递对象引用，主线程通过Lock的await方法堵塞，等待子线程运行完成时signal触发
7. Wrapper对象持有变量存储结果值，线程间传递对象引用，主线程判断子进程活跃状态，若未完成则本身yield
8. Wrapper对象持有变量存储结果值，线程间传递对象引用，主线程循环判断本身的中断状态，子线程完成任务后对主线程发送一个中断信号
9. Wrapper对象持有变量存储结果值，线程间传递对象引用，主线程通过LockSupport堵塞，子线程任务完成后调用LockSupport.unpark()解锁主线程
10. 提交Callable，返回Future，等待获取结果
11. 提交FutureTask，等待获取结果
12. 主线程通过BlockQueue堵塞，等待子线程生产数据
13. 管道通信，主线程read时堵塞，等待子线程write
14. 主线程循环判断静态变量是否为null，子线程写入静态变量
15. Wrapper对象持有变量存储结果值，线程间传递对象引用，设置Semaphore初始为0，主线程调用acquire后会堵塞，子线程完成任务release，permits+1，主线程恢复执行
16. CompletableFuture计算后获取结果
