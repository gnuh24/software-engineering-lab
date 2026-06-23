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
public class EntityGraphDemoService {

    private final UserRepository userRepository;

    public void run() {

        System.out.println("\n======================================");
        System.out.println("🚀 ENTITY GRAPH DEMO (EAGER LOAD)");
        System.out.println("======================================");

        long start = System.currentTimeMillis();

        List<User> users = userRepository.findAllUsingEntityGraph();

        System.out.println("📦 Users loaded: " + users.size());
        System.out.println("--------------------------------------");

        int i = 1;

        for (User user : users) {

            System.out.printf(
                "👤 %-2d | %-10s | orders: %d\n",
                i++,
                user.getName(),
                user.getOrders().size()
            );
        }

        long end = System.currentTimeMillis();

        System.out.println("--------------------------------------");
        System.out.println("⚡ Strategy: ENTITY GRAPH");
        System.out.println("🔥 Behavior: 1 query - eager fetch graph");
        System.out.println("⏱ Time: " + (end - start) + " ms");
        System.out.println("📊 Expected Queries: 1");
        System.out.println("======================================\n");
    }
}
