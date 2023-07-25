insert into USER (user_id, create_date, account_id, email, name, pw, user_role)
values (1, NOW(), 'aaa', 'aaa@naver.com', 'kim', '$2a$10$yaUq4xqHM2lNZz9nWLAsYO7prWCVm4H1pnGqiMgjrci68Dewi.NaG', 'NORMAL'),
       (2, NOW(), 'bbb', 'bbb@naver.com', 'lee', '$2a$10$yaUq4xqHM2lNZz9nWLAsYO7prWCVm4H1pnGqiMgjrci68Dewi.NaG', 'NORMAL'),
       (3, NOW(), 'ccc', 'ccc@naver.com', 'choi', '$2a$10$yaUq4xqHM2lNZz9nWLAsYO7prWCVm4H1pnGqiMgjrci68Dewi.NaG', 'NORMAL'),
       (4, NOW(), 'admin', 'dydrnjs518@gmail.com', 'ADMIN', '$2a$10$yaUq4xqHM2lNZz9nWLAsYO7prWCVm4H1pnGqiMgjrci68Dewi.NaG', 'ADMIN');

insert into BOOK (book_id, author, cost, description, is_sold, likes, name, order_id, user_id)
values (1, 'sam', 15000, 'description 1', false, 0, 'c++', null, 1),
       (2, 'king', 25000, 'description 2', false, 0, 'java', null, 1),
       (3, 'nick', 35000, 'description 3' , false, 0, 'python', null, 2),
       (4, 'bob', 42000, 'description 4', false, 0, 'GO', null, 2),
       (5, 'bob', 42000, 'description 4', false, 0, 'Ruby', null, 2),
       (6, 'bob', 42000, 'description 4', false, 0, 'JavaScript', null, 2);
