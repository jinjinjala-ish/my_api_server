package com.example.my_api_server.lock;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Getter
@Slf4j
public class ReentrantCounter {

    private final ReentrantLock lock = new ReentrantLock();

    private int count = 0; //해당 공유영역 값(Heap)

    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>();
        int threadCount = 10;
        ReentrantCounter counter = new ReentrantCounter();

        //스레드 생성
        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(counter::increasCount); //thread 연산
            thread.start();
            threads.add(thread);
        }
        threads.forEach(thread ->
        {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        log.info("기대값: {}", threadCount);
        log.info("실제값: {}", counter.getCount());

    }

    private void increasCount() {
        this.lock.lock();

        //3초 정도 락을 획득하는데 시도
        try {
            if (this.lock.tryLock(3, TimeUnit.SECONDS)) { //3초 정도 기다리겠다
                try {
                    log.info("락 획득 후 연산 작업 시작!");
                    this.count++;
                    Thread.sleep(4000);
                } finally {
                    this.lock.unlock(); //락 반환(개발자가 원하는 시점에 락을 획득, 반납 가능)
                }
            } else {
                //3초안에 락 획득 못함
                log.info("3초 안에 락 획득 못 함");
            }
        } catch (InterruptedException e) {
            log.info("작업 중단");
            throw new RuntimeException(e);
        }

    }
}
