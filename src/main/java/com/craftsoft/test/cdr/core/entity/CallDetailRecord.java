package com.craftsoft.test.cdr.core.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;

/**
 * The type Call detail record.
 *
 * @author Andrew Ruban
 * @since 17.09.2020
 */
@Table(name = "call_detail_records")
@Entity(name = "CallDetailRecord")
@Data
@ToString
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
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
    private Date startDatetime;

    @NotNull
    @Column(name = "end_datetime")
    private Date endDatetime;

    @Length(max = 20)
    @NotNull
    @Column(name = "status")
    private String status;

    @NotNull
    @Column(name = "cost_per_minute")
    private BigDecimal costPerMinute;

    @Transient
    private BigDecimal totalCallCost;

    @Transient
    private BigDecimal totalCallDuration;

    @PostLoad
    private void postLoad() {
        recalculateTotalCostAndDuration();
    }

    /**
     * Recalculates total cost and duration when changing start or end datetime or cost per minute.
     */
    private void recalculateTotalCostAndDuration() {
        if (getStartDatetime() == null || getEndDatetime() == null || getCostPerMinute() == null) {
            return;
        }
        long duration = Duration.between(getStartDatetime().toInstant(), getEndDatetime().toInstant()).toMillis();
        long seconds = duration / 1000;
        BigDecimal milliseconds = BigDecimal.valueOf((duration - (seconds * 1000)) / 1000d);
        totalCallDuration = BigDecimal.valueOf(seconds).add(milliseconds);
        totalCallCost = costPerMinute.multiply(totalCallDuration);
    }

    public void setStartDatetime(Date startDatetime) {
        this.startDatetime = startDatetime;
        recalculateTotalCostAndDuration();
    }

    public void setEndDatetime(Date endDatetime) {
        this.endDatetime = endDatetime;
        recalculateTotalCostAndDuration();
    }

    public void setCostPerMinute(BigDecimal costPerMinute) {
        this.costPerMinute = costPerMinute;
        recalculateTotalCostAndDuration();
    }
}
