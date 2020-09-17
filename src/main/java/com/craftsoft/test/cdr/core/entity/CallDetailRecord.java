package com.craftsoft.test.cdr.core.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * @author Andrew Ruban
 * @since 17.09.2020
 */

@Table(name = "call_detail_records")
@Entity(name = "call_detail_records")
@Data
@ToString
@NoArgsConstructor
public class CallDetailRecord {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;


    private String destination;

    private String account;

    private Instant startDatetime;

    private Instant endDatetime;

    private String status;

    private BigDecimal costPerminute;


}
