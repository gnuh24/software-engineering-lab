package com.thug.lab.nplusone.service;

import com.thug.lab.nplusone.dto.UserSummaryDTO;
import com.thug.lab.nplusone.entity.User;
import com.thug.lab.nplusone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DtoProjectionDemoService {

    private final UserRepository userRepository;

    public void run() {

        System.out.println("\n======================================");
        System.out.println("🚀 DTO PROJECTION DEMO (RECORD)");
        System.out.println("======================================");

        long start = System.currentTimeMillis();

        List<UserSummaryDTO> users = userRepository.findAllSummary();

        System.out.println("📦 DTO Users loaded: " + users.size());
        System.out.println("--------------------------------------");

        int i = 1;

        for (UserSummaryDTO dto : users) {

            System.out.printf(
                "👤 %-2d | %-10s | orders: %d\n",
                i++,
                dto.name(),
                dto.orderCount()
            );
        }

        long end = System.currentTimeMillis();

        System.out.println("--------------------------------------");
        System.out.println("⚡ Strategy: DTO PROJECTION (RECORD)");
        System.out.println("🔥 Behavior: direct query → immutable DTO mapping");
        System.out.println("⏱ Time: " + (end - start) + " ms");
        System.out.println("📊 Expected Queries: 1");
        System.out.println("======================================\n");
    }
}
