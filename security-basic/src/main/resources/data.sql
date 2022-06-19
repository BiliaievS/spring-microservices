INSERT INTO account(account_name, password)
VALUES ('sbiliaiev', 'spring');
INSERT INTO account(account_name, password)
VALUES ('admin', 'admin');

insert into client_details(client_id, client_secret, resource_ids, scopes, grant_types)
values ('acme', 'asmesecret', null, 'read', 'authorization_code, reference_code');