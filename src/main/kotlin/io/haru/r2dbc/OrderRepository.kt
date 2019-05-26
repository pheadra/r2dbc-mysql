package io.haru.r2dbc

import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface OrderRepository : ReactiveCrudRepository<Order, Long>
