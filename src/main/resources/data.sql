insert into USER (account_id, pw, name) values
    ('AccountID1','$2a$10$eIIdfGFT8qJiavcREbl','kim'),
    ('AccountID2','$2a$10$eIIdfGFT8qJiavcREbl','lee'),
    ('AccountID3','$2a$10$eIIdfGFT8qJiavcREbl','park');

insert into BOOK (name,description,cost,author,is_sold,user_id,order_id) values
    ('java', 'java book desciption', 5000, 'tom', false, 2, null),
    ('jpa', 'jpa book desciption', 7000, 'kevin', false, 2, null),
    ('spring', 'spring book desciption', 15000, 'k', false, 3, null);