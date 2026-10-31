# Cache Aside Simulation

> Sprint 4 — Distributed Systems
>
> Initiative: S4-I1 — Redis & Caching
>
> Module: S4-I1.1 — Caching Fundamentals

## Overview

A standalone Java implementation demonstrating the
Cache-Aside caching pattern.

The project simulates doctor records stored in a database
and uses an in-memory HashMap as a cache.

It demonstrates how an application checks the cache before
accessing the primary data source.

## Learning Objectives

- Understand cache hits and cache misses.
- Implement the Cache-Aside pattern.
- Understand application-level caching.
- Track cache hit and miss statistics.
- Understand database fallback behavior.

## Technologies

- Java
- Java Collections Framework
- HashMap
- Optional
- Object-Oriented Programming

## Project Structure

```text
src/main/java/com/caching_fundamentals/cache_aside_simulation/
├── Doctor.java
├── DoctorRepository.java
├── DoctorCacheService.java
├── CacheAsideDemo.java
└── CacheAsideSimulationApplication.java