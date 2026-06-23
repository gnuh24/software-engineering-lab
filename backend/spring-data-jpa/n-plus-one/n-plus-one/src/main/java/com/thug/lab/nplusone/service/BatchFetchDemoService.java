package com.thug.lab.nplusone.service;

import com.thug.lab.nplusone.entity.User;
import com.thug.lab.nplusone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BatchFetchDemoService {

    private final UserRepository userRepository;

    public void run() {

        System.out.println("\n======================================");
        System.out.println("🚀 BATCH FETCH DEMO (@BatchSize = 5)");
        System.out.println("======================================");

        long start = System.currentTimeMillis();

        List<User> users = userRepository.findAll();

        System.out.println("📦 Users loaded: " + users.size());
        System.out.println("--------------------------------------");

        int i = 1;

        for (User user : users) {

            int orderSize = user.getOrders().size();

            System.out.printf(
                "👤 %-2d | %-10s | orders: %d\n",
                i++,
                user.getName(),
                orderSize
            );
        }

        long end = System.currentTimeMillis();

        System.out.println("--------------------------------------");
        System.out.println("⚡ Strategy: BATCH FETCH (@BatchSize = 5)");
        System.out.println("⏱ Time: " + (end - start) + " ms");
        System.out.println("📊 Behavior: Lazy loading grouped by batch size");
        System.out.println("📊 Batch size: 5 users per query");
        System.out.println("======================================\n");
    }
}
