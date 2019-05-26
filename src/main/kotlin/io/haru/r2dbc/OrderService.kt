package io.haru.r2dbc

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono


@Service
class OrderService(
        val orderRepository: OrderRepository
) {
    @Transactional
    fun save(order: Order): Mono<Order> {
        return orderRepository.save(order)
    }
}