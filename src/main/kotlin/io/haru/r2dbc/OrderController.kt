package io.haru.r2dbc

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class OrderController(
        val orderService: OrderService
) {
    @GetMapping("/order")
    fun saveOrder(): Mono<Order> {
        val order = Order(fn = "abc")
        return orderService.save(order)
    }
}