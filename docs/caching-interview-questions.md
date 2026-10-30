# Caching Interview Questions and Answers

> Sprint: Sprint 4 — Distributed Systems
> Initiative: S4-I1 — Redis & Caching
> Module: S4-I1.1 — Caching Fundamentals
> Status: In Progress

---

## Section 1 — Basic Questions

### Q1. What is caching?

**Answer:**

Caching is a technique used to temporarily store frequently
accessed data in a faster storage layer.

It reduces repeated access to slower data sources such
as relational databases.

Example:

Instead of querying PostgreSQL for every doctor profile
request, Spring Boot can retrieve previously cached data.

### Q2. Why do we need caching?

**Answer:**

Caching helps:

- Reduce database queries.
- Reduce response latency for cache hits.
- Improve throughput for suitable workloads.
- Reduce repeated computation.
- Reduce pressure on primary data stores.

### Q3. What is a cache hit?

**Answer:**

A cache hit occurs when requested data is available
in the cache.

The application can return the cached value without
querying the primary database.

### Q4. What is a cache miss?

**Answer:**

A cache miss occurs when requested data is not available
in the cache.

The application typically retrieves the data from
the database and may store it in the cache.

### Q5. What is TTL?

**Answer:**

TTL stands for Time to Live.

It defines how long a cache entry remains valid
before it expires.

### Q6. What is cache invalidation?

**Answer:**

Cache invalidation is the process of removing or updating
cached data when the original data changes.

It helps prevent stale data from being returned.

---

## Section 2 — Intermediate Questions

### Q7. What is the cache-aside pattern?

**Answer:**

Cache-aside is a strategy in which the application
checks the cache before accessing the database.

On a cache hit, it returns cached data.

On a cache miss, it queries the database, stores the result
in the cache, and returns the result.

It is also known as lazy loading.

### Q8. What is the difference between cache-aside
and write-through caching?

**Answer:**

Cache-aside:

- Application checks the cache on reads.
- Application loads missing data from the database.
- Application manages cache invalidation on writes.

Write-through:

- Application writes through a caching layer.
- The cache and primary data store are updated
  as part of the write strategy.

The consistency guarantees depend on the implementation.

### Q9. What is the difference between expiration
and eviction?

**Answer:**

Expiration makes an entry invalid after its configured
lifetime.

Eviction removes an entry from the cache because of
expiration, memory limits, an eviction policy, or an
explicit removal operation.

### Q10. What is stale data?

**Answer:**

Stale data is cached information that no longer matches
the latest value in the original data source.

Example:

PostgreSQL contains a consultation fee of ₹700,
but Redis still contains ₹500.

### Q11. What is the difference between local caching
and distributed caching?

**Answer:**

Local caching stores data inside an application's memory.

Distributed caching stores data in an external cache
service accessible by multiple application instances.

Local caches are usually faster to access, while
distributed caches provide shared cache state.

### Q12. What is LRU?

**Answer:**

LRU stands for Least Recently Used.

It is an eviction policy that removes entries that
have not been accessed recently.

### Q13. What is LFU?

**Answer:**

LFU stands for Least Frequently Used.

It removes entries based on how frequently they
have been accessed.

### Q14. What is cache hit ratio?

**Answer:**

Cache hit ratio measures the proportion of cache lookups
that successfully find the requested data.

Formula:

Cache Hit Ratio =
(Cache Hits / Total Cache Lookups) × 100

Example:

800 hits / 1000 lookups × 100 = 80%

---

## Section 3 — Advanced Questions

### Q15. What is a cache stampede?

**Answer:**

A cache stampede occurs when many concurrent requests
try to load the same missing or expired cache entry.

This can cause a sudden increase in database queries.

Possible solutions include:

- Request coalescing.
- Single-flight loading.
- Distributed locks where appropriate.
- TTL jitter.
- Early cache refresh.

### Q16. What is cache penetration?

**Answer:**

Cache penetration occurs when requests repeatedly query
data that does not exist.

Because the requested data is absent from the cache,
the application repeatedly reaches the database.

Possible solutions include:

- Negative caching.
- Input validation.
- Bloom filters for appropriate use cases.

### Q17. What is cache avalanche?

**Answer:**

Cache avalanche occurs when many cache entries expire
or the cache becomes unavailable around the same time.

The resulting requests may overwhelm the database.

Possible solutions include:

- Randomized TTL values.
- Early refresh.
- Fallback strategies.
- Monitoring and capacity planning.

### Q18. Does caching always improve performance?

**Answer:**

No.

Caching adds lookup operations, memory consumption,
network communication, invalidation logic, and
operational complexity.

It is beneficial when saved database or computation
costs outweigh those additional costs.

### Q19. How do you choose a suitable TTL?

**Answer:**

TTL depends on:

- How frequently the data changes.
- How frequently it is requested.
- Business freshness requirements.
- Cache storage capacity.
- Invalidation strategy.

Frequently changing data may require a short TTL
or explicit invalidation.

### Q20. How do you prevent stale data?

**Answer:**

Possible approaches include:

- Explicit cache invalidation after successful updates.
- Appropriate TTL.
- Write-through strategies.
- Versioned cache keys.
- Reliable event-driven invalidation.

The chosen approach depends on the consistency
requirements of the application.

---

## Section 4 — Spring Boot Scenario Questions

### Q21. How would you cache a doctor profile in Spring Boot?

**Answer:**

I would:

1. Identify the doctor profile read endpoint.
2. Use a stable cache key based on doctor ID.
3. Check the cache before querying the repository.
4. Retrieve from PostgreSQL on a cache miss.
5. Cache the result with an appropriate TTL.
6. Invalidate the key after a successful update.

Spring Cache abstraction can simplify the implementation,
while Redis can provide a distributed cache.

### Q22. A doctor updates their profile, but users still
see the old profile. How would you debug it?

**Answer:**

I would inspect:

1. Whether the database update succeeded.
2. Whether the transaction committed.
3. Whether the correct cache key was invalidated.
4. Whether collection caches also need invalidation.
5. Whether multiple application instances share
   the same cache.
6. Whether invalidation failed.
7. Whether the next read actually loads fresh data.

### Q23. Redis becomes unavailable. What should happen?

**Answer:**

The application should have an explicit cache failure
strategy.

Depending on the business requirements, it may:

- Fall back to PostgreSQL for non-critical cached reads.
- Apply connection timeouts.
- Limit repeated failed cache operations.
- Monitor cache failures.
- Use circuit breakers where appropriate.

The application must not silently return incorrect
or stale data just because the cache is unavailable.

### Q24. Would you cache appointment availability?

**Answer:**

I would evaluate it carefully.

Appointment availability can change frequently because
of bookings, cancellations, and scheduling updates.

If caching is introduced, it should use an appropriate
freshness and invalidation strategy.

The database should remain the authoritative source
for booking decisions.

### Q25. Would you cache payment status?

**Answer:**

I would avoid ordinary long-lived caching for payment
status because correctness and freshness are critical.

The application should use the authoritative payment
state for decisions that affect transactions.

---

## Section 5 — Scenario-Based Questions

### Q26. A product catalog endpoint receives 10,000 requests
per minute. Most requests access the same products.
Would you introduce caching?

**Answer:**

Caching may be beneficial because the workload is
read-heavy and accesses repeated data.

I would first measure:

- Database query latency.
- Request volume.
- Cache hit ratio.
- Response latency.
- Data update frequency.

Then I would introduce caching with a suitable
TTL and invalidation strategy.

### Q27. A cache entry expires, and 500 requests arrive
simultaneously. What could happen?

**Answer:**

A cache stampede could occur.

All requests might query the database simultaneously.

I would consider request coalescing, early refresh,
TTL jitter, or a carefully designed locking mechanism.

### Q28. Two Spring Boot instances maintain different
local cached values. Why?

**Answer:**

Each application instance maintains its own in-memory cache.

An update made through one instance may not invalidate
the other instance's local cache.

A distributed cache or a reliable invalidation mechanism
can help synchronize cache state.

### Q29. The cache hit ratio is 95%, but response latency
has not improved. Why?

**Answer:**

Possible reasons include:

- Cache lookup latency is high.
- The original database query was already inexpensive.
- Application processing dominates response time.
- Network communication adds overhead.
- Serialization and deserialization are expensive.
- The measured workload is not representative.

I would compare end-to-end latency and individual
operation timings rather than relying only on hit ratio.

### Q30. How would you measure the benefit of caching?

**Answer:**

I would compare the same workload before and after
caching and measure:

- Average response latency.
- p95 and p99 latency.
- Database query count.
- Database CPU and connection usage.
- Cache hit and miss ratio.
- Cache lookup latency.
- Throughput.

I would only claim an improvement supported
by reproducible measurements.

---

## Section 6 — Coding and Practical Questions

### Q31. Implement a basic cache-aside flow.

**Expected approach:**

```java
public Doctor getDoctor(Long id) {

    Doctor cachedDoctor = cache.get(id);

    if (cachedDoctor != null) {
        return cachedDoctor;
    }

    Doctor doctor = doctorRepository.findById(id)
            .orElseThrow();

    cache.put(id, doctor);

    return doctor;
}