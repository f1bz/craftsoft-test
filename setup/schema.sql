create table call_detail_records
(
    id uuid not null,
    account varchar(20) not null,
    destination varchar(20) not null,
    start_datetime timestamp not null,
    end_datetime timestamp not null,
    status varchar(20) not null,
    cost_per_minute numeric not null,
    total_duration numeric not null,
    total_cost numeric not null
);

comment on table call_detail_records is 'This is a main table containing all info about call records ';

create index call_detail_records_account_index
    on call_detail_records (account);

create index call_detail_records_destination_index
    on call_detail_records (destination);

create unique index call_detail_records_id_uindex
    on call_detail_records (id);

alter table call_detail_records
    add constraint call_detail_records_pk
        primary key (id);

