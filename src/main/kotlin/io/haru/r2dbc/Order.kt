package io.haru.r2dbc

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table


@Table("orders")
data class Order (

        @Id
        val id: Long? = null,

        val fn: String
)


