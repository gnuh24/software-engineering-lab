package com.thug.lab.nplusone;

import com.thug.lab.nplusone.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DemoRunner implements CommandLineRunner {

    private final NPlusOneDemoService nPlusOneDemoService;
    private final JoinFetchDemoService joinFetchDemoService;
    private final EntityGraphDemoService entityGraphDemoService;
    private final BatchFetchDemoService batchFetchDemoService;
    private final DtoProjectionDemoService dtoProjectionDemoService;

    @Override
    public void run(String... args) {

        nPlusOneDemoService.run();

//        joinFetchDemoService.run();

//        entityGraphDemoService.run();

//        batchFetchDemoService.run();

//          dtoProjectionDemoService.run();
    }
}
