INSERT INTO institutions(institution_description, institution_name) VALUES ('Cel i misja: Pomoc dzieciom z ubogich rodzin.','Fundacja "Dbam o Zdrowie"');
INSERT INTO institutions(institution_description, institution_name) VALUES ('Cel i misja: Pomoc wybudzaniu dzieci ze śpiączki.','Fundacja "A kogo"');
INSERT INTO institutions(institution_description, institution_name) VALUES ('Cel i misja: Pomoc osobom znajdującym się w trudnej sytuacji życiowej.','Fundacja “Dla dzieci"');
INSERT INTO institutions(institution_description, institution_name) VALUES ('Cel i misja: Pomoc dla osób nie posiadających miejsca zamieszkania','Fundacja “Bez domu”');
INSERT INTO categories(category_name) VALUES ('Ubrania');
INSERT INTO categories(category_name) VALUES ('Zabawki');
INSERT INTO categories(category_name) VALUES ('Książki');
INSERT INTO categories(category_name) VALUES ('AGD');
INSERT INTO roles(role_id,role_name)VALUES(1,'ROLE_ADMIN');
INSERT INTO roles(role_id, role_name)VALUES(2,'ROLE_USER');
INSERT INTO users(user_id,is_enabled, user_password, user_email, user_lastname, user_name) VALUES (1,true, '$2a$10$k0OSEhlDpbunl85TcSj.zOuUHuywvh/xKTJeUSih9j/slwqS7sxaa', 'admin@admin','Admin', 'Admin');
INSERT INTO users(user_id,is_enabled, user_password, user_email, user_lastname, user_name) VALUES (2,true, '$2a$10$BUidKwRAzaELeoD.j7dPE.4rVs3aFKrBgPL2V0tEOcM22gAeKkuZa', 'admin@admin','Adminuser', 'Adminuser');
INSERT INTO user_role(user_id, role_id) VALUES (1,1);
INSERT INTO user_role(user_id, role_id) VALUES (2,2);
INSERT INTO donations(city_name, phone_number, pick_up_comment, pick_up_date, pick_up_time, bag_quantity, street_name, zip_code, institution_id, user_id) VALUES
('Wrocław', '881711914', 'test_comment','2021-01-12','12:00', 2,'ul.Testów 28','12345',2,2);