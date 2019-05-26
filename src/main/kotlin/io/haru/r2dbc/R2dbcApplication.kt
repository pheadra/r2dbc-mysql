package io.haru.r2dbc

import io.r2dbc.spi.ConnectionFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import com.github.jasync.sql.db.mysql.util.URLParser
import com.github.jasync.sql.db.mysql.pool.MySQLConnectionFactory
import com.github.jasync.r2dbc.mysql.JasyncConnectionFactory
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.nio.charset.StandardCharsets
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Bean
import org.springframework.context.event.EventListener
import org.springframework.data.r2dbc.connectionfactory.R2dbcTransactionManager
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.transaction.ReactiveTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono


@SpringBootApplication
class R2dbcApplication

fun main(args: Array<String>) {
	runApplication<R2dbcApplication>(*args)
}

@Service
class OrderService(
		val orderRepository: OrderRepository
) {
	@Transactional
	fun save(order: Order): Mono<Order> {
		return orderRepository.save(order)
	}
}

interface OrderRepository : ReactiveCrudRepository<Order, Long>

@Table("orders")
data class Order (

	@Id
	val id: Long? = null,

	val fn: String
)

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
@Configuration
@EnableTransactionManagement
@EnableR2dbcRepositories
class MysqlConfiguration : AbstractR2dbcConfiguration() {
	override fun connectionFactory(): ConnectionFactory {
		val url = "mysql://orders:orders@127.0.0.1:3306/orders"
		return JasyncConnectionFactory(MySQLConnectionFactory(
				URLParser.parseOrDie(url, StandardCharsets.UTF_8)))
	}

	@Bean
	fun transactionManager(): ReactiveTransactionManager {
		return R2dbcTransactionManager(connectionFactory())
	}

}