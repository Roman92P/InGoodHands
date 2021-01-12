INSERT INTO institutions(institution_description, institution_name) VALUES ('Cel i misja: Pomoc dzieciom z ubogich rodzin.','Fundacja "Dbam o Zdrowie"');
INSERT INTO institutions(institution_description, institution_name) VALUES ('Cel i misja: Pomoc wybudzaniu dzieci ze śpiączki.','Fundacja "A kogo"');
INSERT INTO institutions(institution_description, institution_name) VALUES ('Cel i misja: Pomoc osobom znajdującym się w trudnej sytuacji życiowej.','Fundacja “Dla dzieci"');
INSERT INTO institutions(institution_description, institution_name) VALUES ('Cel i misja: Pomoc dla osób nie posiadających miejsca zamieszkania','Fundacja “Bez domu”');
INSERT INTO categories(category_name) VALUES ('Ubrania');
INSERT INTO categories(category_name) VALUES ('Zabawki');
INSERT INTO categories(category_name) VALUES ('Książki');
INSERT INTO categories(category_name) VALUES ('AGD');
INSERT INTO roles(role_name)VALUES('ROLE_ADMIN');
INSERT INTO roles( role_name)VALUES('ROLE_USER');
INSERT INTO roles(role_name)VALUES('ROLE_CHANGE_PASSWORD_PRIVILEGE');
INSERT INTO users(is_enabled, user_password, user_email, user_lastname, user_name) VALUES (true, '$2a$10$k0OSEhlDpbunl85TcSj.zOuUHuywvh/xKTJeUSih9j/slwqS7sxaa', 'forcodeemailroman@gmail.com','Admin', 'Admin');
INSERT INTO users(is_enabled, user_password, user_email, user_lastname, user_name) VALUES (true, '$2a$10$k0OSEhlDpbunl85TcSj.zOuUHuywvh/xKTJeUSih9j/slwqS7sxaa', 'forcdeemailroman@gmail.com','Admin1', 'Admin1');
INSERT INTO users(is_enabled, user_password, user_email, user_lastname, user_name) VALUES (true, '$2a$10$BUidKwRAzaELeoD.j7dPE.4rVs3aFKrBgPL2V0tEOcM22gAeKkuZa', 'admin@admin','Adminuser', 'Adminuser');
INSERT INTO user_role(user_id, role_id) VALUES (1,1);
INSERT INTO user_role(user_id, role_id) VALUES (3,1);
INSERT INTO user_role(user_id, role_id) VALUES (2,2);
INSERT INTO donations(status,pick_up_date_time,city_name, phone_number, pick_up_comment, pick_up_date, pick_up_time, bag_quantity, street_name, zip_code, institution_id, user_id,created_on) VALUES
('odebrany','2020-12-12 12:00','Wrocław', '881711914', 'test_comment1','2020-12-12','12:00', 2,'ul.Testów 28','12345',2,2,'2020-12-24');
INSERT INTO donations(status,pick_up_date_time,city_name, phone_number, pick_up_comment, pick_up_date, pick_up_time, bag_quantity, street_name, zip_code, institution_id, user_id,created_on) VALUES
('odebrany','2020-12-13 12:00','Wrocław', '881711914', 'test_comment2','2020-12-13','12:00', 2,'ul.Testów 28','12345',1,2,'2020-12-25');
INSERT INTO donations(status,pick_up_date_time,city_name, phone_number, pick_up_comment, pick_up_date, pick_up_time, bag_quantity, street_name, zip_code, institution_id, user_id,created_on) VALUES
('odebrany','2020-12-29 12:00','Wrocław', '881711914', 'test_comment3','2020-12-29','12:00', 2,'ul.Testów 28','12345',3,2,'2020-12-26');
INSERT INTO donations(status,pick_up_date_time,city_name, phone_number, pick_up_comment, pick_up_date, pick_up_time, bag_quantity, street_name, zip_code, institution_id, user_id,created_on) VALUES
('nieodebrany','2021-01-15 12:00','Wrocław', '881711914', 'test_comment4','2021-01-15','12:00', 2,'ul.Testów 28','12345',4,2,'2020-12-27');
INSERT INTO donations(status,pick_up_date_time,city_name, phone_number, pick_up_comment, pick_up_date, pick_up_time, bag_quantity, street_name, zip_code, institution_id, user_id,created_on) VALUES
('nieodebrany','2021-01-16 12:00','Wrocław', '881711914', 'test_comment5','2021-01-16','12:00', 2,'ul.Testów 28','12345',2,2,'2020-12-28');
INSERT INTO donations(status,pick_up_date_time,city_name, phone_number, pick_up_comment, pick_up_date, pick_up_time, bag_quantity, street_name, zip_code, institution_id, user_id,created_on) VALUES
('nieodebrany','2021-01-16 12:00','Wrocław', '881711914', 'test_comment5','2021-01-16','12:00', 2,'ul.Testów 28','12345',2,2,'2021-01-28');
INSERT INTO donations(status,pick_up_date_time,city_name, phone_number, pick_up_comment, pick_up_date, pick_up_time, bag_quantity, street_name, zip_code, institution_id, user_id,created_on) VALUES
('odebrany','2020-12-29 23:00','Wrocław', '881711914', 'test_comment5','2020-12-29','23:00', 2,'ul.Testów 28','12345',2,2,'2021-01-29');
INSERT INTO donations(status,pick_up_date_time,city_name, phone_number, pick_up_comment, pick_up_date, pick_up_time, bag_quantity, street_name, zip_code, institution_id, user_id,created_on) VALUES
('nieodebrany','2021-01-29 23:00','Kraków', '123456789', 'test_comment10','2021-01-29','23:00', 2,'ul.Jakaś 28','12345',1,1,'2021-01-29');
INSERT INTO donations(status,pick_up_date_time,city_name, phone_number, pick_up_comment, pick_up_date, pick_up_time, bag_quantity, street_name, zip_code, institution_id, user_id,created_on) VALUES
('odebrany','2020-12-29 23:00','Kraków', '123456789', 'test_comment12','2020-12-29','23:00', 2,'ul.Jakaś 28','12345',1,1,'2021-01-29');
INSERT INTO donations_categories(donation_id, category_id) VALUES (1,1);
INSERT INTO donations_categories(donation_id, category_id) VALUES (1,2);
INSERT INTO donations_categories(donation_id, category_id) VALUES (2,3);
INSERT INTO donations_categories(donation_id, category_id) VALUES (2,4);
INSERT INTO donations_categories(donation_id, category_id) VALUES (3,1);
INSERT INTO donations_categories(donation_id, category_id) VALUES (3,2);
INSERT INTO donations_categories(donation_id, category_id) VALUES (4,3);
INSERT INTO donations_categories(donation_id, category_id) VALUES (4,4);
INSERT INTO donations_categories(donation_id, category_id) VALUES (5,1);
INSERT INTO donations_categories(donation_id, category_id) VALUES (5,2);
INSERT INTO donations_categories(donation_id, category_id) VALUES (6,3);
INSERT INTO donations_categories(donation_id, category_id) VALUES (6,4);
INSERT INTO donations_categories(donation_id, category_id) VALUES (7,1);
INSERT INTO donations_categories(donation_id, category_id) VALUES (7,2);
INSERT INTO donations_categories(donation_id, category_id) VALUES (8,3);
INSERT INTO donations_categories(donation_id, category_id) VALUES (8,4);
INSERT INTO donations_categories(donation_id, category_id) VALUES (9,1);
INSERT INTO donations_categories(donation_id, category_id) VALUES (9,2);