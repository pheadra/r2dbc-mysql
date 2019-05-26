package io.haru.r2dbc.config

import com.github.jasync.r2dbc.mysql.JasyncConnectionFactory
import com.github.jasync.sql.db.mysql.pool.MySQLConnectionFactory
import com.github.jasync.sql.db.mysql.util.URLParser
import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.connectionfactory.R2dbcTransactionManager
import org.springframework.transaction.ReactiveTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.nio.charset.StandardCharsets


@Configuration
@EnableTransactionManagement
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