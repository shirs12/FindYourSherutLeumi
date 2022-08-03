CREATE DATABASE FindYourSherutLeumi;

-- DROP DATABASE FindYourSherutLeumi;

USE FindYourSherutLeumi;

-- ................. tables ...................
CREATE TABLE user_level(
	level_id INT PRIMARY KEY AUTO_INCREMENT,
    level_name NVARCHAR(25) UNIQUE
);
-- DROP TABLE user_level;

CREATE TABLE applicant(
	applicant_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name NVARCHAR(25) NOT NULL,
    last_name NVARCHAR(25) NOT NULL,
    phone_number NVARCHAR(13) UNIQUE,
    city NVARCHAR(25),
    email NVARCHAR(40),
    applicant_password CHAR(64) NOT NULL,
    level_user_id INT DEFAULT 1,
    FOREIGN KEY (level_user_id) REFERENCES user_level(level_id)
);
-- DROP TABLE applicant;

CREATE TABLE coordinator(
	coordinator_id INT PRIMARY KEY AUTO_INCREMENT,
	first_name NVARCHAR(25) NOT NULL,
    last_name NVARCHAR(25) NOT NULL,
    phone_number NVARCHAR(13) UNIQUE,
	email NVARCHAR(40) NOT NULL,
    coordinator_password CHAR(64),
    organization_name NVARCHAR(40),
    level_user_id INT DEFAULT 2,
    FOREIGN KEY (level_user_id) REFERENCES user_level(level_id)
);
-- DROP TABLE coordinator;

CREATE TABLE service(
	service_id INT PRIMARY KEY AUTO_INCREMENT,
    service_name NVARCHAR(40) NOT NULL,
    organization_name NVARCHAR(40) NOT NULL,
    category NVARCHAR(50),
    country NVARCHAR(30) NOT NULL,
    city NVARCHAR(30) NOT NULL,
    street_name NVARCHAR(30) NOT NULL,
    street_num INT(10),
    has_apartment BOOLEAN,
    is_second_year_only BOOLEAN,
    is_morning_service BOOLEAN,
    is_evening_service BOOLEAN,
    description_service NVARCHAR(200),
    coordinator_name NVARCHAR(25) NOT NULL,
    coordinator_id INT,
	FOREIGN KEY (coordinator_id) REFERENCES coordinator(coordinator_id)
);
-- DROP TABLE service;


-- ................. procedures ...................

-- procedure that calls all the services in the db
DELIMITER $$
CREATE PROCEDURE `get_all_services`()
BEGIN
    SELECT service_name,organization_name,category,country,city,street_name,street_num,has_apartment,
    is_second_year_only,is_morning_service,is_evening_service,description_service,coordinator_name FROM service;
END$$
DELIMITER ;
CALL `get_all_services`();

-- procedure that adds service into the db
DELIMITER $$
CREATE PROCEDURE `insert_service_info`(IN p_service_name NVARCHAR(40), IN p_organization_name NVARCHAR(40),
 IN p_category NVARCHAR(50), IN p_country NVARCHAR(30), IN p_city NVARCHAR(30), IN p_street_name NVARCHAR(30),
 IN p_street_num INT(10), IN p_has_apartment BOOLEAN, IN p_is_second_year_only BOOLEAN, IN p_is_morning_service BOOLEAN,
 IN p_is_evening_service BOOLEAN, IN p_description_service NVARCHAR(200), IN p_coordinator_name NVARCHAR(25))
BEGIN
    INSERT INTO service(service_name,organization_name,category,country,city,street_name,street_num,has_apartment,
    is_second_year_only,is_morning_service,is_evening_service,description_service,coordinator_name)
    VALUES(p_service_name, p_organization_name, p_category, p_country,p_city,p_street_name,p_street_num,p_has_apartment,
    p_is_second_year_only,p_is_morning_service,p_is_evening_service,p_description_service,p_coordinator_name);
END$$
DELIMITER ;


-- ................. insert into ...................

-- for testing

-- user_level
INSERT INTO user_level(level_name)
VALUES('Applicant'),('Coordinator');
SELECT * FROM user_level;

-- applicant
INSERT INTO applicant(first_name,last_name,phone_number,city,email,applicant_password)
VALUES('דנה','לוי','0501223334','מודיעין','dana11@gmail.com','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4'),
	  ('שרה','אליהו','0525555566','מודיעין','sara78@gmail.com','0ffe1abd1a08215353c233d6e009613e95eec4253832a761af28ff37ac5a150c');

-- coordinator
INSERT INTO coordinator(first_name,last_name,phone_number,email,coordinator_password,organization_name)
VALUES('יערה','רון כהן','050-7521671','YaelCohenAguda@gmail.com','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','האגודה להתנדבות'),
	  ('שרית','ויזנר','054-2610679','SaritVisnerShilo@gmail.com','ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f','עמותת ש"ל לשירות לאומי');

-- service
INSERT INTO service(service_name,organization_name,category,country,city,street_name,street_num,has_apartment,
    is_second_year_only,is_morning_service,is_evening_service,description_service,coordinator_name)
    VALUES('מערך הסייבר הלאומי','האגודה להתנדבות','משרדי ממשלה','ישראל','באר שבע','באר שבע',0,false,false,
			true,false,'תפקיד מסווג ומעניין במחלקה המגנה על כל מערך הסייבר.הגנה מפני פריצות והתקפות רשת במשרדי הממשלה ובמדינת ישראל.','רון כהן יערה'),
		  ('מרכז רפואי רבין-בלינסון','שילה','בריאות','ישראל','פתח תקווה','פתח תקווה',39,true,false,
			true,false,'ישנם מספר תפקידים בבית חולים בילינסון, ביניהם: מזכירה רפואית, סיעוד (סייעת אחות), תומכת רפואה. תפקיד משמעותי דינמי ומאתגר בתחום הרפואה. *נדרש 5 יח"ל בביולוגיה, פיזיקה או כימיה.','שרית ויזנר');
SET SQL_SAFE_UPDATES = 0;
DELETE FROM Service WHERE service_name = 'name2';
SET SQL_SAFE_UPDATES = 1;

