package com.craftsoft.test.cdr.core.repository;

import com.craftsoft.test.cdr.core.entity.CallDetailRecord;
import com.craftsoft.test.cdr.core.entity.CallDetailRecordView;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

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

}