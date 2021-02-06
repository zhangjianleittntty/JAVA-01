package geektime.coding.zjl.camps.week4;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * 多线程异步方式测试
 * 同步:synchronization
 * 异步:asynchronous
 * CompletableFuture的对象创建
 * //使用默认线程池
 * static CompletableFuture<Void> runAsync(Runnable runnable)
 * static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier)
 * //可以指定线程池
 * static CompletableFuture<Void> runAsync(Runnable runnable,Executor executor)
 * static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier,Executor executor)
 * 串行关系:洗水壶->烧开水   ==>   必须有先后顺序
 * 并行关系:洗水壶->烧开水
 *         洗茶壶->洗茶杯   ==>   同一时间可以进行多个流程
 * 汇聚关系:烧开水->
 *         拿茶叶->泡茶     ==>   只有这两项都搞定后在可以进行后续流程
 */
public class CompletableTest {

    public static void main(String[] args) {
        System.out.println("HelloWorld!");
        long beginTime = System.currentTimeMillis();
        CompletableTest completableTest = new CompletableTest();
        //异步处理泡茶流程
        System.out.println(completableTest.mulitThreedF3().join());
        //同步处理泡茶流程
//        completableTest.syncFn();
        //OR聚合关系
        //completableTest.parallelF5();
        //异常处理
//        completableTest.tryF6();
//        System.out.println("消耗时间:" + (System.currentTimeMillis() - beginTime));
    }

    public void syncFn() {
        try {
            System.out.println("T1：洗水壶...");
            Thread.sleep(1);
            System.out.println("T1:烧开水...");
            Thread.sleep(15);

            System.out.println("T2：洗茶壶...");
            Thread.sleep(1);
            System.out.println("T2: 洗茶杯...");
            Thread.sleep(2);
            System.out.println("T2: 拿茶叶...");
            Thread.sleep(1);

            System.out.println("T1:拿到茶叶:龙井");
            System.out.println("T1:泡茶...");
            System.out.println("上茶：龙井");
        } catch (InterruptedException e) {}
    }

    /**
     * 分工(1)
     * 洗水壶、烧开水
     */
    public CompletableFuture<Void> mulitThreedF1() {
        CompletableFuture<Void> f1 = CompletableFuture.runAsync(
                ()->{
                    System.out.println("T1：洗水壶...");
                    sellp(1,TimeUnit.MICROSECONDS);

                    System.out.println("T1:烧开水...");
                    sellp(15,TimeUnit.MICROSECONDS);
                });
        return f1;
    }

    /**
     * 分工(2)
     * 洗茶壶、洗茶杯、拿茶叶
     */
    public CompletableFuture<String> mulitThreedF2() {
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(
                ()->{
                    System.out.println("T2：洗茶壶...");
                    sellp(1,TimeUnit.MICROSECONDS);

                    System.out.println("T2: 洗茶杯...");
                    sellp(2,TimeUnit.MICROSECONDS);

                    System.out.println("T2: 拿茶叶...");
                    sellp(1,TimeUnit.MICROSECONDS);

                    return "龙井";

                });
        return f2;
    }

    /**
     * 分工(3)
     * 任务1和任务2完成后执行泡茶
     */
    public CompletableFuture<String>  mulitThreedF3() {
        //AND的汇聚关系,其中tf是mulitThreed返回的结果
        CompletableFuture<String> f3 = mulitThreedF1().thenCombine(mulitThreedF2(),(__,tf) -> {
            System.out.println("T1:拿到茶叶:" + tf);
            System.out.println("T1:泡茶...");
            return "上茶:" + tf;
        });
        return f3;
    }

    /**
     * 任务串行
     * 串行方法:thenApply,thenAccept,thenRun,thenCompose
     * @return
     */
    public String serialsThreedF4() {
        CompletableFuture<String> f4 = CompletableFuture.supplyAsync(
                ()->"Hello World"
        ).thenApply(s -> s + " QQ")
         .thenApply(String::toUpperCase);
        return f4.join();
    }

    /**
     * AND汇聚关系:parallelAND
     * thenCombine,thenAcceptBoth,runAfterBoth
     * @param t
     * @param u
     */

    /**
     * OR汇聚关系:parallelOr
     * applyToEither,acceptEither,runAfterEither
     */
    public void parallelF5() {
        CompletableFuture<String> f51 = CompletableFuture.supplyAsync(
                ()->{
                    System.out.println("任务f51开始!");
                    int t = getRandomInt(1000,5000);
                    sellp(t,TimeUnit.MICROSECONDS);
//                    try {
//                        Thread.sleep(200);
//                    } catch (InterruptedException e) {}
                    return "任务f51:" + String.valueOf(t);
                }
        );

        CompletableFuture<String> f52 = CompletableFuture.supplyAsync(
                ()->{
                    System.out.println("任务f52开始!");
                    int t = getRandomInt(1,10);
                    sellp(t,TimeUnit.MICROSECONDS);
//                    try {
//                        Thread.sleep(20);
//                    } catch (InterruptedException e) {}
                    return "任务f52:" + String.valueOf(t);
                }
        );

        CompletableFuture<String> f53 = f51.applyToEither(f52,s->s);
        System.out.println(f53.join());
    }

    /**
     * 异步的异常处理
     *
     */
    public void tryF6() {
        //无异常的使用
//        CompletableFuture<Integer> f0 = CompletableFuture.supplyAsync(()->(7/0))
//                .thenApply(r->r*10);
        //异常处理
        CompletableFuture<Integer> f0 = CompletableFuture.supplyAsync(()->(7/0))
                .thenApply(r->r*10).exceptionally(e->0);
        System.out.println(f0.join());
    }

    private int getRandomInt(int min,int max) {
        return (int) (Math.random()*(max-min)+min);
    }

    private void sellp(int t, TimeUnit u) {
        try {
            u.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        new Thread(()->{
//            try {
//                u.sleep(t);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();
    }

}
