package com.example.my_api_server.lock;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Getter
@Slf4j
public class SyncCounter {

    private int count = 0; //해당 공유영역 값(Heap)

    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>();
        int threadCount = 10;
        SyncCounter counter = new SyncCounter();

        //스레드 생성
        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(counter::increaseCount); //thread 연산
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

    private void increaseCount() {
        //스레드 1번이 들어오면서 락을 획득함, 2번이 들. 3....20000
        Thread.State state = Thread.currentThread().getState();
        log.info("state1 = {}", state.toString());

        synchronized (this) {//락으로 순서 제어
            log.info("state2 = {}", state.toString());
            count++;
        }

        //스레드 1번이 락을 반환, 2번이, 3,,,,20000
        log.info("state3 = {}", state.toString());
    }
}
