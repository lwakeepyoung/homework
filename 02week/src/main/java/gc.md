### java垃圾回收器说明

---

#### SerialGC(串行GC)
特点
+ 对年轻代采用标记-复制算法
+ 对老年代采用标记-清除-整理算法
+ gc时采用单线程，会stw
+ 适用于内存不大的客户端程序

可以使用参数 -XX：+USeParNewGC 改进版本的serialGC，配合cms使用


#### ParallelGC(并行GC)
特点
+ 对年轻代采用标记-复制算法
+ 对老年代采用标记-清除-整理算法
+ 采用多线程gc，会stw
+ 多线程垃圾回收会加快垃圾回收的速度，适用于高吞吐量的程序

-XX：ParallelGCThreads=N用来指定使用GC线程数，默认为cpu核数；


#### CMS
特点
+ 对年轻代采用标记-复制算法
+ 对老年代采用标记-清除算法
+ 整理碎片会在空闲时进行
+ 多线程gc，初始标记和最终标记时会stw

设计目的是为了老年代gc时不出现长时间卡顿，使用这两种手段达到这个目的
1. 对老年代回收时不进行整理，而是交给空闲列表（free-lists）来进行整理
2. 在标记-清除阶段的大部分工作和应用线程一起并非执行（仍和应用线程竞争cpu资源）。
   默认情况下，CMS 使用的并发线程数等于 CPU 核心数的 1/4、

cms gc 的六个阶段
    
1. ***初始标记***（标记所有根可达对象，会stw）
2. 并发标记（标记所有的存活对象）
3. 并发预清理（标记更改对象）
4. ***最终标记***（标记存活对象，会stw，通常会在年轻代为空时执行这个阶段）
5. 标记清除
6. 标记重置（以便开始下一个循环）

#### G1
特点
+ 将整个内存分为n份（默认2048），增加单次回收的速度
+ 可配置参数预期单次gc时间，jvm会优化算法尽可能达到指定gc时间
+ 可能存在退化情况
+ 可能装不下大对象

G1 （优先回收垃圾多的内存）回收过程
1. GC pause (G1 Evacuation Pause) (young) 启动时先进行young区回收
2. 并发标记（标记所有存活对象）
   1. 初始标记 （标记gc根对象能直达的对象）
   2. Root Region Scan（Root区扫描）标记所有从 "根区域" 可达的存活对象。根区域包括：非空的区域，以及在标记过程中不得不收集的区域。
   3. Concurrent Mark（并发标记）只遍历对象图，并在一个特殊的位图中标记能访问到的对象
   4. Remark（再次标记）会发生stw
   5. Cleanup（清理）
3. 转移暂停: 混合模式（Evacuation Pause (mixed)）

注意事项
1. 并发模式失败
2. 晋升失败
3. 巨型对象分配失败

#### ZGC
+ 对内存大的程序提升gc效果明显，10ms以内