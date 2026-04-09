package com.example.my_api_server.lock;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Getter
@Slf4j
public class Counter {

    private int count = 0; //해당 공유영역 값(Heap)

    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>();
        int threadCount = 142000;
        Counter counter = new Counter();

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
        count++;
    }
}
