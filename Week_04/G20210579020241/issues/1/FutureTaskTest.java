package geektime.coding.zjl.camps.week4;

import com.alibaba.fastjson.JSON;
import geektime.coding.zjl.mulitthreed.geek.javabf.ch22.FutureTask;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

@Slf4j
public class FutureTaskTest {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2;
        ExecutorService udpThreadPool = new ThreadPoolExecutor(THREAD_POOL_SIZE, THREAD_POOL_SIZE,0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

        Map<Long,String> mapResult = new HashMap<>();
        for (int i = 0;i<10;i++) {
            Future<String> result = udpThreadPool.submit(new FutureTask((i + 1000L)));
            mapResult.put((i + 1000L),result.get());
        }
        System.out.println(mapResult.toString());
        log.info("FutureTaskTest Json:{}", JSON.toJSON(mapResult));
        udpThreadPool.shutdown();
    }



}
