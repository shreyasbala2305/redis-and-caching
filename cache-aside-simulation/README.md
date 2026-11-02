# Cache Aside Simulation — Java

A hands-on Java project demonstrating caching fundamentals, Cache-Aside architecture, cache performance optimization, concurrency, stampede prevention, and eviction policies.

This project is part of my **Production-Ready Java Backend Engineer Roadmap — Sprint 4: Redis & Caching**.

---

## Project Objectives

- Understand caching fundamentals and cache-aside architecture.
- Implement an in-memory cache using Java.
- Understand cache hits, misses, TTL, and invalidation.
- Measure cache performance against direct database access.
- Calculate cache hit and miss ratios.
- Simulate cache stampedes using concurrent requests.
- Prevent cache stampedes using the single-flight pattern.
- Implement and compare cache eviction policies.

---

## Tech Stack

- Java
- Java Collections Framework
- ConcurrentHashMap
- LinkedHashMap
- CompletableFuture
- ExecutorService
- CountDownLatch
- AtomicInteger
- Git & GitHub

---

## Project Structure

```text
cache-aside-simulation/
│
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── caching_fundamentals/
│                   └── cache_aside_simulation/
│                       ├── Doctor.java
│                       ├── DoctorRepository.java
│                       ├── DoctorCacheService.java
│                       ├── CacheAsideDemo.java
│                       ├── CacheAsideSimulationApplication.java
│                       ├── CacheEntry.java
│                       ├── CacheStampedeDemo.java
│                       ├── CacheStampedePreventionDemo.java
│                       ├── CacheEvictionDemo.java
│                       └── CacheEvictionComparisonDemo.java
│
├── learning/
│   └── 01-caching-fundamentals.md
│
├── interview/
│   └── caching-interview-questions.md
│
├── exercises/
│   └── caching-exercises.md
│
├── .gitignore
└── README.md
```

---

## Core Concepts

### 1. Cache-Aside Pattern

The application checks the cache before accessing the database.

```text
Application
    |
    v
Check Cache
    |
    +---- HIT ----> Return Cached Data
    |
    +---- MISS ---> Query Database
                       |
                       v
                  Store in Cache
                       |
                       v
                  Return Data
```

### 2. TTL — Time To Live

TTL defines how long cached data remains valid.

- Cached entries expire after a configured duration.
- Expired entries are treated as cache misses.
- TTL helps reduce stale data.

### 3. Cache Invalidation

Cache invalidation removes or updates cached entries after underlying data changes.

Without invalidation, cached data may become stale.

### 4. Cache Hit Ratio

```text
Hit Ratio = Cache Hits / Total Requests × 100
```

### 5. Cache Miss Ratio

```text
Miss Ratio = Cache Misses / Total Requests × 100
```

### 6. Cache Stampede

A cache stampede occurs when multiple concurrent requests encounter the same cache miss and independently load identical data from the database.

### 7. Single-Flight Pattern

Single-flight coordinates concurrent cache loading so multiple requests for the same key share one database-loading operation.

### 8. Cache Eviction

Cache eviction removes entries when cache capacity is exceeded.

Implemented policies:

- FIFO — First In, First Out
- LRU — Least Recently Used

---

## Exercises Completed

### Exercise 1 — Cache-Aside Simulation

Implemented a basic cache-aside flow.

- Cache lookup
- Cache hit
- Cache miss
- Database fallback
- Cache population

### Exercise 2 — Cache Hits and Misses

Demonstrated cache hits and misses through repeated doctor lookups.

### Exercise 3 — TTL Implementation

Implemented expiration using a configured TTL.

- Valid cache entry
- Expired cache entry
- Reload after expiration

### Exercise 4 — Cache Invalidation

Demonstrated stale cache data and cache invalidation.

- Updated database data
- Stale cached data
- Cache eviction
- Fresh database reload

### Exercise 5 — Hit Ratio and Miss Ratio

Measured cache effectiveness using repeated requests.

- Cache hits
- Cache misses
- Database calls
- Database calls saved
- Hit ratio
- Miss ratio

### Exercise 6 — Database vs Cache Performance Benchmark

Compared direct database access against cache-aside access.

| Metric | Result |
|---|---:|
| Direct DB requests | 100 |
| Direct DB average latency | 50.70 ms |
| Cache requests | 100 |
| Cache average latency | 1.92 ms |
| Cache hits | 99 |
| Cache misses | 1 |
| Hit ratio | 99% |
| Database calls | 1 |
| Database calls saved | 99 |
| Performance improvement | 96.21% |

> Benchmark timings depend on system performance and execution conditions.

### Exercise 7 — Cache Stampede Simulation

Simulated 50 concurrent requests against an initially empty cache.

| Metric | Result |
|---|---:|
| Concurrent requests | 50 |
| Database calls | 50 |
| Duplicate database loads | 49 |

Demonstrated how concurrent cache misses can cause duplicate database requests.

### Exercise 8 — Cache Stampede Prevention

Implemented single-flight loading using:

- ConcurrentHashMap
- CompletableFuture
- ExecutorService
- CountDownLatch

| Metric | Before | After |
|---|---:|---:|
| Concurrent requests | 50 | 50 |
| Database calls | 50 | 1 |
| Duplicate loads | 49 | 0 |

Successfully reduced database calls from 50 to 1.

### Exercise 9 — LRU Cache Eviction

Implemented an LRU cache using LinkedHashMap.

- Cache capacity: 3 entries
- Access-order tracking
- Automatic eviction

Verified that the least recently accessed entry is evicted.

### Exercise 10 — FIFO vs LRU Cache Eviction

Compared FIFO and LRU eviction policies.

| Policy | Evicted | Final Cache |
|---|---|---|
| FIFO | Doctor 1 | [2, 3, 4] |
| LRU | Doctor 2 | [3, 1, 4] |

Demonstrated how access order changes eviction behavior under LRU.

---

## Running the Simulations

### Compile

```powershell
javac -d out src/main/java/com/caching_fundamentals/cache_aside_simulation/*.java
```

### Run Cache Aside Demo

```powershell
java -cp out com.caching_fundamentals.cache_aside_simulation.CacheAsideDemo
```

### Run Cache Stampede Demo

```powershell
java -cp out com.caching_fundamentals.cache_aside_simulation.CacheStampedeDemo
```

### Run Cache Stampede Prevention Demo

```powershell
java -cp out com.caching_fundamentals.cache_aside_simulation.CacheStampedePreventionDemo
```

### Run LRU Eviction Demo

```powershell
java -cp out com.caching_fundamentals.cache_aside_simulation.CacheEvictionDemo
```

### Run FIFO vs LRU Comparison

```powershell
java -cp out com.caching_fundamentals.cache_aside_simulation.CacheEvictionComparisonDemo
```

---

## Learning Resources in This Repository

| Resource | Description |
|---|---|
| `learning/01-caching-fundamentals.md` | Caching concepts and fundamentals |
| `interview/caching-interview-questions.md` | Caching interview questions |
| `exercises/caching-exercises.md` | Hands-on exercises and results |

---

## Sprint 4 — Redis & Caching

This project belongs to:

**S4-I1 — Redis & Caching**

### Progress

- [x] Caching fundamentals
- [x] Cache-Aside simulation
- [x] TTL
- [x] Cache invalidation
- [x] Hit/miss ratio
- [x] Performance benchmarking
- [x] Cache stampede simulation
- [x] Stampede prevention
- [x] LRU eviction
- [x] FIFO vs LRU comparison
- [ ] Redis fundamentals
- [ ] Redis CLI practice
- [ ] Spring Boot Redis integration
- [ ] Redis mini-project
- [ ] Telemedicine HMS Redis integration
- [ ] Testing and documentation

---

## Key Takeaways

- Caching reduces repeated database access.
- Cache-Aside is a common application caching pattern.
- TTL helps control cached data freshness.
- Cache invalidation is essential for data consistency.
- Hit ratio measures cache effectiveness.
- Cache stampedes can cause unnecessary database load.
- Single-flight prevents duplicate concurrent loads.
- Eviction policies manage limited cache capacity.
- LRU considers access recency, while FIFO considers insertion order.

---

## Author

Shreyas Balapure

GitHub: https://github.com/shreyasbala2305