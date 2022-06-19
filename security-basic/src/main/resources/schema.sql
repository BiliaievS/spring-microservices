CREATE TABLE account
(
    account_name varchar(100),
    password     varchar(100)
);

create table client_details
(
    client_id     varchar(100),
    client_secret varchar(100),
    resource_ids  varchar(50),
    scopes        varchar(100),
    grant_types   varchar(100)
)