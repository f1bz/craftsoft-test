package com.craftsoft.test.cdr.core.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * @author Andrew Ruban
 * @since 17.09.2020
 */

@Table(name = "call_detail_records")
@Entity(name = "CallDetailRecord")
@Data
@ToString
@NoArgsConstructor
public class CallDetailRecord {

    @Id
    private UUID id;

    @Length(max = 20)
    @Column(name = "destination")
    private String destination;

    @Length(max = 20)
    @NotNull
    @NotBlank
    @Column(name = "account")
    private String account;

    @NotNull
    @Column(name = "start_datetime")
    private Instant startDatetime;

    @NotNull
    @Column(name = "end_datetime")
    private Instant endDatetime;

    @Length(max = 20)
    @NotNull
    @Column(name = "status")
    private String status;

    @NotNull
    @Column(name = "cost_per_minute")
    private BigDecimal costPerMinute;

}
