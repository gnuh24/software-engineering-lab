package com.thug.lab.nplusone.repository;

import com.thug.lab.nplusone.dto.UserSummaryDTO;
import com.thug.lab.nplusone.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
            SELECT DISTINCT u
            FROM User u
            JOIN FETCH u.orders
            """)
    List<User> findAllWithOrders();

    @EntityGraph(attributePaths = "orders")
    @Query("SELECT u FROM User u")
    List<User> findAllUsingEntityGraph();

    @Query("""
        SELECT new com.thug.lab.nplusone.dto.UserSummaryDTO(
            u.id,
            u.name,
            count(o.id)
        )
        FROM User u
        LEFT JOIN u.orders o
        GROUP BY u.id, u.name
        """)
    List<UserSummaryDTO> findAllSummary();
}
