package com.thug.lab.nplusone.service;

import com.thug.lab.nplusone.QueryCounter;
import com.thug.lab.nplusone.entity.User;
import com.thug.lab.nplusone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NPlusOneDemoService {

    private final UserRepository userRepository;

    public void run() {

        QueryCounter.reset();

        System.out.println("\n======================================");
        System.out.println("🚀 N+1 DEMO");
        System.out.println("======================================");

        List<User> users = userRepository.findAll();

        QueryCounter.increment(); // query findAll

        System.out.println("📦 Users loaded: " + users.size());
        System.out.println("--------------------------------------");

        int i = 1;

        for (User user : users) {

            int orderSize = user.getOrders().size();

            // mỗi lần lazy load sẽ phát sinh query (N+1 demo)
            QueryCounter.increment();

            System.out.printf(
                "👤 %-2d | %-10s | orders: %d\n",
                i++,
                user.getName(),
                orderSize
            );
        }

        System.out.println("--------------------------------------");
        System.out.println("📊 Total Queries: " + QueryCounter.count);
        System.out.println("======================================\n");
    }
}
