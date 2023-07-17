insert into users
values ('c2af3798-c102-4fc2-a10d-019541f4ecbe', '1', '1', current_timestamp, '1', 10000),
       ('231c7610-b045-41f9-8ab3-d29e469541b7', '4', '4', current_timestamp, '4', 100),
       ('4ad8127a-a5b8-480a-a9ca-583752968503', '2', '2', current_timestamp, '2', 1000),
       ('df8932d9-676c-4053-8d05-3ab505c30291', '3', '3', current_timestamp, '3', 10)
on conflict do nothing;

insert into teams
values ('aba28a0b-6b58-4aff-9972-41e62ded17b5', 'Тестовая команда Бобры', 0),
       ('7fe8b27f-654a-459d-9f8c-cf2a484f22ef', 'Тестовая команда Волки', 0)
on conflict do nothing;

insert into teams_users
values ('7fe8b27f-654a-459d-9f8c-cf2a484f22ef', 'df8932d9-676c-4053-8d05-3ab505c30291'),
       ('aba28a0b-6b58-4aff-9972-41e62ded17b5', '231c7610-b045-41f9-8ab3-d29e469541b7')
on conflict do nothing;
