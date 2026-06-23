# Spring Data JPA - N+1 Query Lab

![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5-green)
![Hibernate](https://img.shields.io/badge/Hibernate-6.x-orange)
![MySQL](https://img.shields.io/badge/MySQL-8.4-blue)
![Docker Compose](https://img.shields.io/badge/Docker_Compose-v2-2496ED?logo=docker&logoColor=white)

Một lab nhỏ nhằm mô phỏng và giải thích một trong những vấn đề hiệu năng phổ biến nhất khi làm việc với ORM:

> N+1 Query Problem

---

# Mục tiêu

Giúp người học:

* Hiểu N+1 Query là gì
* Quan sát SQL thực tế được sinh ra bởi Hibernate
* Hiểu nguyên nhân gốc rễ của N+1
* So sánh các cách giải quyết phổ biến
* Hiểu khi nào nên dùng từng giải pháp

---

# Cấu trúc dự án

```text
src/main/java/com/thug/lab/nplusone

├── dto
│   └── UserSummaryDTO.java
│
├── entity
│   ├── User.java
│   └── UserOrder.java
│
├── repository
│   ├── UserRepository.java
│   └── OrderRepository.java
│
├── service
│   ├── NPlusOneDemoService.java
│   ├── JoinFetchDemoService.java
│   ├── EntityGraphDemoService.java
│   ├── BatchFetchDemoService.java
│   └── DtoProjectionDemoService.java
│
├── DemoRunner.java
├── QueryCounter.java
└── NPlusOneApplication.java
```

---

# Database Setup

Khởi động MySQL:

```bash
docker compose up -d
```

Kiểm tra container:

```bash
docker ps
```

Thông tin database:

```text
Host: localhost
Port: 3306
Database: nplusone_lab
```

Dữ liệu mẫu:

```text
10 Users
20 Orders

Mỗi User có 2 Orders
```

---

# Chạy Project

```bash
mvn spring-boot:run
```

hoặc:

```bash
./mvnw spring-boot:run
```

---

# User Entity Relationship Configuration

Tất cả các demo trong project đều sử dụng cùng một quan hệ giữa `User` và `UserOrder`.

Mặc định, collection `orders` được cấu hình với `FetchType.LAZY`:

```java
@OneToMany(
        mappedBy = "user",
        fetch = FetchType.LAZY
)
// @BatchSize(size = 5)
private List<UserOrder> orders = new ArrayList<>();
```

Lưu ý:

* Mặc định KHÔNG bật Batch Fetching
* Chỉ bật `@BatchSize` khi chạy Batch Fetch Demo
* Tất cả các demo khác giữ nguyên cấu hình trên

---

# Demo 1 - N+1 Query Problem

## DemoRunner

```java
nPlusOneDemoService.run();
```

## Repository

```java
userRepository.findAll();
```

## Điều gì xảy ra?

1. Hibernate thực hiện một query để lấy danh sách User.
2. Khi vòng lặp truy cập:

```java
user.getOrders()
```

Lazy Loading được kích hoạt.
3. Hibernate tiếp tục phát sinh thêm nhiều query để lấy Orders tương ứng với từng User.

## Kết quả

Tổng số query tăng theo số lượng User:

```text
1 query lấy User
+
N query lấy Orders
=
N + 1 queries
```

## Cách quan sát

Quan sát SQL log trong console.

Bạn sẽ thấy:

* Một query lấy danh sách User
* Sau đó xuất hiện thêm nhiều query liên quan đến bảng Orders

---

# Demo 2 - JOIN FETCH

## DemoRunner

```java
joinFetchDemoService.run();
```

## Repository

```java
@Query("""
        SELECT DISTINCT u
        FROM User u
        JOIN FETCH u.orders
        """)
List<User> findAllWithOrders();
```

## Điều gì xảy ra?

Hibernate được yêu cầu lấy User và Orders trong cùng một lần truy vấn.

## Kết quả

```text
Single Query
```

## Cách quan sát

Quan sát SQL log.

So sánh với Demo N+1, số lượng query giảm đáng kể chỉ còn duy nhất 1 query.

---

# Demo 3 - Entity Graph

## DemoRunner

```java
entityGraphDemoService.run();
```

## Repository

```java
@EntityGraph(attributePaths = "orders")
@Query("""
        SELECT u
        FROM User u
        """)
List<User> findAllUsingEntityGraph();
```

## Điều gì xảy ra?

Entity Graph yêu cầu Hibernate load thêm relation `orders` khi lấy User.

Ưu điểm:

* Không cần viết JOIN FETCH thủ công
* Tách biệt fetch strategy khỏi business logic

## Kết quả

```text
Single Query
```

## Cách quan sát

Quan sát SQL log.

So sánh với Demo N+1 để thấy Hibernate chủ động load Orders ngay từ đầu.

---

# Demo 4 - Batch Fetching

## Bước 1

Mở file:

```text
User.java
```

Từ:

```java
@OneToMany(
        mappedBy = "user",
        fetch = FetchType.LAZY
)
// @BatchSize(size = 5)
private List<UserOrder> orders = new ArrayList<>();
```

Thành:

```java
@OneToMany(
        mappedBy = "user",
        fetch = FetchType.LAZY
)
@BatchSize(size = 5)
private List<UserOrder> orders = new ArrayList<>();
```

## Bước 2

Trong DemoRunner:

```java
batchFetchDemoService.run();
```

## Điều gì xảy ra?

Hibernate vẫn sử dụng Lazy Loading.

Tuy nhiên thay vì lấy Orders cho từng User riêng lẻ, Hibernate sẽ gom nhiều User lại thành từng batch.

## Kết quả

Số lượng query giảm đáng kể so với N+1.

## Cách quan sát

So sánh SQL log giữa:

```text
N+1 Demo
```

và

```text
Batch Fetch Demo
```

Bạn sẽ thấy số lượng query ít hơn rõ rệt. Từ n + 1 xuống còn ( n / batchSize ) + 1

---

# Demo 5 - DTO Projection

## DemoRunner

```java
dtoProjectionDemoService.run();
```

## Repository

```java
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
```

## Điều gì xảy ra?

Thay vì load Entity hoàn chỉnh, Hibernate trả về DTO chứa đúng dữ liệu cần thiết.

## Ưu điểm

* Không Lazy Loading
* Không Persistence Context overhead
* Không Dirty Checking
* Tối ưu cho API Response

## Kết quả

```text
Single Query
```

---

# So sánh các giải pháp

| Technique      | Query Count                 | Khuyến nghị        |
| -------------- | --------------------------- | ------------------ |
| Lazy + Loop    | N + 1                       | ❌ Tránh            |
| JOIN FETCH     | 1                           | ✅ Rất tốt          |
| Entity Graph   | 1                           | ✅ Rất tốt          |
| Batch Fetch    | 1 + ceil(N / Batch Size)    | ✅ Tốt              |
| DTO Projection | 1                           | ✅ Tốt nhất cho API |

---

# Góc nhìn System Design

N+1 không chỉ là vấn đề của Hibernate.

Bản chất của nó là:

> Uncontrolled Fan-out Queries

Khi hệ thống mở rộng, N+1 có thể gây ra:

* Database overload
* Connection pool exhaustion
* Latency spikes
* Cache miss explosion
* Cascading failures

Tư duy quan trọng:

| Sai                                  | Đúng                             |
| ------------------------------------ | -------------------------------- |
| Query trong loop                     | Batch query                      |
| Load toàn bộ entity                  | Chỉ fetch dữ liệu cần thiết      |
| Phụ thuộc hoàn toàn vào Lazy Loading | Chủ động thiết kế Fetch Strategy |
| Tối ưu sau khi production gặp sự cố  | Thiết kế đúng từ đầu             |

---

# Kết luận

N+1 Query là một trong những vấn đề hiệu năng phổ biến nhất khi sử dụng ORM.

Các giải pháp phổ biến:

1. JOIN FETCH
2. Entity Graph
3. Batch Fetching
4. DTO Projection

Trong các hệ thống thực tế:

* JOIN FETCH thường được dùng khi cần load relation rõ ràng.
* Entity Graph giúp quản lý fetch strategy linh hoạt hơn.
* DTO Projection thường là lựa chọn tối ưu nhất cho API đọc dữ liệu.
* Batch Fetching là giải pháp giảm thiểu N+1 khi không thể thay đổi query hiện có.

Mục tiêu của lab này không phải ghi nhớ cú pháp, mà là hiểu cách Hibernate tương tác với database và nhận diện N+1 trước khi nó trở thành vấn đề trong production.
