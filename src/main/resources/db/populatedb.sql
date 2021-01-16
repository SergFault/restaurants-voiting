DELETE
FROM user_roles;
DELETE
FROM dish;
DELETE
FROM restaurant;
DELETE
FROM users;
DELETE
FROM vote;
ALTER SEQUENCE global_seq RESTART WITH 10036;

INSERT INTO restaurant (id ,name)
values (10020,'Гавана'),
       (10021,'Триполи'),
       (10022,'Гараж'),
       (10023,'Пляж');

INSERT INTO dish (id, rest_id, price, name, date_of)
VALUES (10000,10020, '10000', 'Беляш', '2019-11-12'),
       (10001,10020, '12000', 'Бигос' ,'2019-11-12'),
       (10002,10020, '5000', 'Бисквит', '2019-11-12'),
       (10003,10020, '7000', 'Винегрет', '2019-11-12'),
       (10004,10021, '15000', 'Банановый торт', '2019-11-12'),
       (10005,10021, '20000', 'Брезаола', '2019-11-12'),
       (10006,10021, '20000', 'Лагман', '2019-11-12'),
       (10007,10021, '31000', 'Сочник', '2019-11-12'),
       (10008,10022, '6000', 'Токана', '2019-11-12'),
       (10009,10022, '11500', 'Слатур', '2019-11-12'),
       (10010,10022, '12500', 'Стью', '2019-11-12'),
       (10011,10022, '35000', 'Тарэ', '2019-11-12'),
       (10012,10023, '45000', 'Стейк', '2019-11-12'),
       (10013,10023, '33000', 'Шашлык', '2019-11-12'),
       (10014,10023, '30500', 'Шницель', '2019-11-12'),
       (10015,10023, '13500', 'Антрекот', '2019-11-12');

INSERT INTO users (id, name, email, enabled, password, registered)
VALUES (10016, 'frank' ,'frank@mail.com', true, '{noop}password', '2020-11-11 10:00:00'),
       (10017, 'eugene','eugene@mail.com', true, '{noop}password', '2020-11-05 20:00:00'),
       (10018,'dmitry','dmitry@gmail.com', true, '{noop}password', '2020-11-04 09:30:00'),
       (10019,'hill','hill@gmail.com', true, '{noop}password', '2020-10-28 15:22:00');

INSERT INTO user_roles (user_id, role)
VALUES (10016, 'USER'),
       (10017, 'USER'),
       (10017, 'ADMIN'),
       (10018, 'USER');

INSERT INTO vote (id, date_of, rating, rest_id, user_id)
values (10024,'2020-11-12', 3, '10020', '10016'),
       (10025,'2020-11-13', 6, '10021', '10016'),
       (10026,'2020-11-14', 4, '10022', '10016'),
       (10027,'2020-11-15', 10, '10023', '10016'),

       (10028,'2020-11-12', 8, '10020', '10017'),
       (10029,'2020-11-13', 9, '10021', '10017'),
       (10030,'2020-11-14', 9, '10022', '10017'),
       (10031,'2020-11-15', 10, '10023', '10017'),

       (10032,'2020-11-12', 8, '10020', '10018'),
       (10033,'2020-11-13', 9, '10021', '10018'),
       (10034,'2020-11-14', 9, '10022', '10018'),
       (10035,'2020-11-15', 10, '10023', '10018');






