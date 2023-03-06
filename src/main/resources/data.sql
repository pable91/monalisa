insert into USER (account_id, pw, name, email, create_date, user_role) values
    ('AccountID1','$2a$10$eIIdfGFT8qJiavcREbl','kim','kim@naver.com', NOW(), 'NORMAL'),
    ('AccountID2','$2a$10$eIIdfGFT8qJiavcREbl','lee','lee@gmail.com', NOW(), 'NORMAL'),
    ('AccountID3','$2a$10$eIIdfGFT8qJiavcREbl','park','part@gmail.com', NOW(), 'NORMAL');

insert into BOOK (name,description,cost,author,is_sold,user_id,order_id,likes) values
    ('java', 'java book desciption', 5000, 'tom', false, 2, null,0),
    ('jpa', 'jpa book desciption', 7000, 'kevin', false, 2, null,0),
    ('spring', 'spring book desciption', 15000, 'k', false, 3, null,0);