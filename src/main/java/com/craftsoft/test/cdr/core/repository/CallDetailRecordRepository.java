package com.craftsoft.test.cdr.core.repository;

import com.craftsoft.test.cdr.core.entity.CallDetailRecord;
import com.craftsoft.test.cdr.core.entity.CallDetailRecordView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Andrew Ruban
 * @since 17.09.2020
 */
@Repository
public interface CallDetailRecordRepository extends PagingAndSortingRepository<CallDetailRecord, UUID> {

    @Query("select cdr From CallDetailRecord cdr where cdr.id in :uuids")
    List<CallDetailRecordView> getAllThatExists(List<UUID> uuids);

    @Query("select cdr From CallDetailRecord cdr where" +
            " (coalesce(:accounts) is null or cdr.account in :accounts) " +
            " and (coalesce(:destinations) is null or cdr.destination in :destinations) " +
            " and (coalesce(:statuses) is null or cdr.status in :statuses) " +
            " and (coalesce(:startDatetime) is null or cdr.startDatetime >= :startDatetime)" +
            " and (coalesce(:endDatetime) is null or cdr.endDatetime <= :endDatetime)" +
            " and (coalesce(:duration) is null or extract(epoch from (cdr.endDatetime - cdr.startDatetime)) <= :duration)")
    List<CallDetailRecord> findAllWithParams(List<String> accounts, List<String> destinations, List<String> statuses, Date startDatetime, Date endDatetime, Integer duration, Pageable page);

    @Query("select count(cdr) From CallDetailRecord cdr where" +
            " (coalesce(:accounts) is null or cdr.account in :accounts) " +
            " and (coalesce(:destinations) is null or cdr.destination in :destinations) " +
            " and (coalesce(:statuses) is null or cdr.status in :statuses) " +
            " and (coalesce(:startDatetime) is null or cdr.startDatetime >= :startDatetime)" +
            " and (coalesce(:endDatetime) is null or cdr.endDatetime <= :endDatetime)" +
            "and (coalesce(:duration) is null or extract(epoch from (cdr.endDatetime - cdr.startDatetime)) <= :duration)")
    Long findCountAllForAverage(List<String> accounts, List<String> destinations, List<String> statuses, Date startDatetime, Date endDatetime, Integer duration);


}