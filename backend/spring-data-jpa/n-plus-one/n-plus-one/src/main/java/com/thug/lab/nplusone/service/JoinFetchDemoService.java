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
public class JoinFetchDemoService {

    private final UserRepository userRepository;

    public void run() {

        System.out.println("\n======================================");
        System.out.println("🚀 JOIN FETCH DEMO");
        System.out.println("======================================");

        long start = System.currentTimeMillis();

        List<User> users = userRepository.findAllWithOrders();

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
        System.out.println("⚡ Strategy: JOIN FETCH");
        System.out.println("⏱ Time: " + (end - start) + " ms");
        System.out.println("📊 Expected Queries: 1");
        System.out.println("======================================\n");
    }
}
