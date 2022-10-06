CREATE DATABASE IF NOT EXISTS FindYourSherutLeumi;

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
    email NVARCHAR(40) NOT NULL UNIQUE,
    u_password CHAR(64) NOT NULL,
    level_user_id INT DEFAULT 1,
    FOREIGN KEY (level_user_id) REFERENCES user_level(level_id)
);
-- DROP TABLE applicant;

CREATE TABLE coordinator(
	coordinator_id INT PRIMARY KEY AUTO_INCREMENT,
	first_name NVARCHAR(25) NOT NULL,
    last_name NVARCHAR(25) NOT NULL,
    phone_number NVARCHAR(13) UNIQUE,
	email NVARCHAR(40) NOT NULL UNIQUE,
    u_password CHAR(64) NOT NULL,
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
    address NVARCHAR(30) NOT NULL,
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


-- ................. insert into ...................

-- for testing

-- user_level
INSERT INTO user_level(level_name)
VALUES('Applicant'),('Coordinator');
SELECT * FROM user_level;

-- applicant
INSERT INTO applicant(first_name,last_name,phone_number,city,email,u_password)
VALUES('דנה','לוי','0501223334','מודיעין','dana11@gmail.com','$2a$12$AoRs14RsX0vzf2mAQEo1j.Ohc2V6Vxh046/cEJFg.maKTGo06QA4m'),
	  ('שרה','אליהו','0525555566','מודיעין','sara78@gmail.com','$2a$12$W2n6wjSMt23YFgmj1Ti0/.ZCUaritxmwizEwSxiCKaW9.PpM1o7.e');

-- coordinator
INSERT INTO coordinator(first_name,last_name,phone_number,email,u_password,organization_name)
VALUES('יערה','רון כהן','050-7521671','YaelCohenAguda@gmail.com','$2a$12$APEp.5Wx96HEIBnBG5Ve4ukTy7j2AQ6LgbZlnZOOuT4wzjEpGPDte','האגודה להתנדבות'),
	  ('שרית','ויזנר','054-2610679','SaritVisnerShilo@gmail.com','$2a$12$.PNuTXPH20G6WnacEDiU5.JxeW48263TJXiBq.9010xDKLWWZ4KiW','עמותת ש"ל לשירות לאומי');

-- service
INSERT INTO service(service_name,organization_name,category,country,city,address,has_apartment,
    is_second_year_only,is_morning_service,is_evening_service,description_service,coordinator_name,coordinator_id)
    VALUES('מערך הסייבר הלאומי','האגודה להתנדבות','משרדי ממשלה','ישראל','באר שבע','באר שבע',false,false,
			true,false,'תפקיד מסווג ומעניין במחלקה המגנה על כל מערך הסייבר.הגנה מפני פריצות והתקפות רשת במשרדי הממשלה ובמדינת ישראל.','רון כהן יערה',1),
		  ('מרכז רפואי רבין-בלינסון','שילה','בריאות','ישראל','פתח תקווה','זבוטינסקי 39',true,false,
			true,false,'ישנם מספר תפקידים בבית חולים בילינסון, ביניהם: מזכירה רפואית, סיעוד (סייעת אחות), תומכת רפואה. תפקיד משמעותי דינמי ומאתגר בתחום הרפואה. *נדרש 5 יח"ל בביולוגיה, פיזיקה או כימיה.','שרית ויזנר',2);

SET SQL_SAFE_UPDATES = 0;
DELETE FROM Service WHERE service_name = 'name2';
SET SQL_SAFE_UPDATES = 1;

CALL add_new_service("והדרת פני זקן אשקלון","בת עמי [אמונה-אלומה]","קשישים","ישראל","אשקלון","הפרויקט פועל ברחבי העיר",
				true,false,true,false,"אם הינך בת שירות הרוצה לקחת חלק בחוויה של מייסדי המדינה עם סיפורי גבורה של ניצולי שואה - מקומך איתנו. העשייה היא להאיר את עולמם של אלו שבזכותם אנחנו כאן. אנו מחפשים בת בוגרת, אחראית, אוהבת קשישים, סבלנית ורצינית. דרושות בנות עם רצון גדול לתרום והמון בגרות נפשית , מודעות רגשית ליכולות ולעצמן , מסוגלות להכיל , אבל גם ליצור קשר אישי וחם עם הקשישים.","ביבס שמרית");

CALL update_service(4,"איגוד ערים לשמירת איכות הסביבה","בת עמי [אמונה-אלומה]","הדרכה","ישראל","חדרה","המסגר 3, אזור תעשיה דרומי",
				true,false,true,false,"בנות השירות משתלבות בעבודה החינוכית של היחידה הסביבתית המקומית ואחראיות על הדרכות בגני ילדים, בבתי ספר ובמתנסים. הבנות מפעילות גינות קהילתיות, מובילות מועצות נוער ירוקות עירוניות ושותפות בהכנת הכשרות והשתלמויות לצוותים חינוכיים.","שוורץ אילה");

-- ---------------- for safe deleting --------------

SET SQL_SAFE_UPDATES = 0;
DROP PROCEDURE get_all_services;
SET SQL_SAFE_UPDATES = 1;


-- ................. procedures ...................

-- SERVICE
-- procedure that calls all the services from db
DELIMITER $$
CREATE PROCEDURE `get_all_services`()
BEGIN
    SELECT * FROM service;
END$$
DELIMITER ;
CALL `get_all_services`(); -- test

-- procedure that calls all the services partialy from db
DELIMITER $$
CREATE PROCEDURE `get_all_services_partialy`()
BEGIN
    SELECT service_id,service_name,organization_name,category,country,city,description_service FROM service;
END$$
DELIMITER ;
CALL `get_all_services_partialy`(); -- test

-- procedure that calls a specific service from db
DELIMITER $$
CREATE PROCEDURE `get_service_by_id`(IN id INT)
BEGIN
	SELECT * FROM service WHERE service_id = id;
END$$
DELIMITER ;
CALL `get_service_by_id`(1); -- test

-- procedure that calls services by coordinator id from db
DELIMITER $$
CREATE PROCEDURE `get_service_by_coordinator_id`(IN id INT)
BEGIN
	SELECT * FROM service WHERE coordinator_id = id;
END$$
DELIMITER ;
CALL `get_service_by_coordinator_id`(5); -- test

-- procedure that adds new service into the db
DELIMITER $$
CREATE PROCEDURE `add_new_service`(IN p_service_name NVARCHAR(40), IN p_organization_name NVARCHAR(40),
 IN p_category NVARCHAR(50), IN p_country NVARCHAR(30), IN p_city NVARCHAR(30), IN p_address NVARCHAR(30),
 IN p_has_apartment BOOLEAN, IN p_is_second_year_only BOOLEAN, IN p_is_morning_service BOOLEAN,
 IN p_is_evening_service BOOLEAN, IN p_description_service NVARCHAR(200), IN p_coordinator_name NVARCHAR(25),IN p_coordinator_id INT)
BEGIN
    INSERT INTO service(service_name,organization_name,category,country,city,address,has_apartment,
    is_second_year_only,is_morning_service,is_evening_service,description_service,coordinator_name,coordinator_id)
    VALUES(p_service_name, p_organization_name, p_category, p_country,p_city,p_address,p_has_apartment,
    p_is_second_year_only,p_is_morning_service,p_is_evening_service,p_description_service,p_coordinator_name,p_coordinator_id);
END$$
DELIMITER ;

-- procedure that updates service in db
DELIMITER $$
CREATE PROCEDURE `update_service`(IN id INT, IN p_service_name NVARCHAR(40), IN p_organization_name NVARCHAR(40),
 IN p_category NVARCHAR(50), IN p_country NVARCHAR(30), IN p_city NVARCHAR(30), IN p_address NVARCHAR(30),
 IN p_has_apartment BOOLEAN, IN p_is_second_year_only BOOLEAN, IN p_is_morning_service BOOLEAN,
 IN p_is_evening_service BOOLEAN, IN p_description_service NVARCHAR(200), IN p_coordinator_name NVARCHAR(25))
BEGIN
    UPDATE service
	SET service_name = p_service_name,
    organization_name = p_organization_name,
    category = p_category,
    country = p_country,
    city = p_city,
    address = p_address,
    has_apartment = p_has_apartment,
    is_second_year_only = p_is_second_year_only,
    is_morning_service = p_is_morning_service,
    is_evening_service = p_is_evening_service,
    description_service = p_description_service,
    coordinator_name = p_coordinator_name
	WHERE service_id = id;
END$$
DELIMITER ;

-- procedure that deletes service by id from db
DELIMITER $$
CREATE PROCEDURE `delete_service`(IN id INT)
BEGIN
    DELETE FROM service WHERE service_id = id;
END$$
DELIMITER ;

-- procedure that deletes services by coordinator id from db
DELIMITER $$
CREATE PROCEDURE `delete_services_by_coordinator_id`(IN id INT)
BEGIN
	DELETE FROM service WHERE coordinator_id = id;
END$$
DELIMITER ;

-- USER_LEVEL
-- procedure that calls all the levels from db
DELIMITER $$
CREATE PROCEDURE `get_all_levels`()
BEGIN
    SELECT * FROM user_level;
    END$$
DELIMITER ;
CALL `get_all_levels`(); -- test

-- procedure that calls a specific level from db
DELIMITER $$
CREATE PROCEDURE `get_level_by_id`(IN id INT)
BEGIN
	SELECT * FROM user_level WHERE level_id = id;
END$$
DELIMITER ;
CALL `get_level_by_id`(1); -- test

-- procedure that adds new level into the db
DELIMITER $$
CREATE PROCEDURE `add_new_level`(IN p_name NVARCHAR(25))
BEGIN
    INSERT INTO user_level(level_name)
    VALUES(p_name);
END$$
DELIMITER ;
CALL `add_new_level`('test'); -- test

-- procedure that updates level in db
DELIMITER $$
CREATE PROCEDURE `update_level`(IN id INT, IN p_name NVARCHAR(25))
BEGIN
    UPDATE user_level
	SET level_name = p_name
	WHERE level_id = id;
END$$
DELIMITER ;
CALL `update_level`(id, 'test2'); -- test

-- procedure that deletes level by id from db
DELIMITER $$
CREATE PROCEDURE `delete_level`(IN id INT)
BEGIN
    DELETE FROM user_level WHERE level_id = id;
END$$
DELIMITER ;
CALL `delete_level`(3); -- test

-- APPLICANT
-- procedure that calls all the applicants from db
DELIMITER $$
CREATE PROCEDURE `get_all_applicants`()
BEGIN
    SELECT * FROM applicant;
    END$$
DELIMITER ;
CALL `get_all_applicants`(); -- test

-- procedure that calls a specific applicant from db
DELIMITER $$
CREATE PROCEDURE `get_applicant_by_id`(IN id INT)
BEGIN
	SELECT * FROM applicant WHERE applicant_id = id;
END$$
DELIMITER ;
CALL `get_applicant_by_id`(2); -- test

-- procedure that calls a specific applicant from db by email
DELIMITER $$
CREATE PROCEDURE `get_applicant_by_email`(IN applicant_email NVARCHAR(40))
BEGIN
	SELECT * FROM applicant WHERE email = applicant_email;
END$$
DELIMITER ;

-- procedure that adds new applicant into the db
DELIMITER $$
CREATE PROCEDURE `add_new_applicant`(IN p_first_name NVARCHAR(25),
    IN p_last_name NVARCHAR(25),IN p_phone_number NVARCHAR(13),
    IN p_city NVARCHAR(25),IN p_email NVARCHAR(40),IN p_u_password CHAR(64))
BEGIN
    INSERT INTO applicant(first_name,last_name,phone_number,city,email,u_password)
    VALUES(p_first_name,p_last_name,p_phone_number,p_city,p_email,p_u_password);
END$$
DELIMITER ;
CALL `add_new_applicant`('אור','אליה','0503758493','ראש העין','orEli123@gmail.com','$2a$12$SKpY0i8YKFvFNKv6hq1CAeKMJt504H3lDElSQPu1CxkRRobMxdxhi'); -- test

-- procedure that updates applicant in db
DELIMITER $$
CREATE PROCEDURE `update_applicant`(IN id INT,IN p_first_name NVARCHAR(25),
    IN p_last_name NVARCHAR(25),IN p_phone_number NVARCHAR(13),
    IN p_city NVARCHAR(25),IN p_email NVARCHAR(40),IN p_u_password CHAR(64))
BEGIN
    UPDATE applicant
	SET first_name = p_first_name,
    last_name = p_last_name,
    phone_number = p_phone_number,
    city = p_city,
    email = p_email,
    u_password = p_u_password
	WHERE applicant_id = id;
END$$
DELIMITER ;
CALL `update_applicant`(6,'אביה','אליה','0503758483','ראש העין','orEli123@gmail.com','$2a$12$wIeStYcwiypjfmix1m5lX.aNWzD5Mamg1gXiNHu8v4OWPLls6.DW2');

-- procedure that deletes applicant by id from db
DELIMITER $$
CREATE PROCEDURE `delete_applicant`(IN id INT)
BEGIN
    DELETE FROM applicant WHERE applicant_id = id;
END$$
DELIMITER ;
CALL `delete_applicant`(6); -- test

-- COORDINATOR
-- procedure that calls all the coordinators from db
DELIMITER $$
CREATE PROCEDURE `get_all_coordinators`()
BEGIN
    SELECT coordinator_id,first_name,last_name,phone_number,
	email,u_password,organization_name
    FROM coordinator;
    END$$
DELIMITER ;
CALL `get_all_coordinators`();

-- procedure that calls a specific coordinator from db
DELIMITER $$
CREATE PROCEDURE `get_coordinator_by_id`(IN id INT)
BEGIN
	SELECT * FROM coordinator WHERE coordinator_id = id;
END$$
DELIMITER ;
CALL `get_coordinator_by_id`(2); -- test

-- procedure that calls a specific coordinator from db by email
DELIMITER $$
CREATE PROCEDURE `get_coordinator_by_email`(IN coordinator_email NVARCHAR(40))
BEGIN
	SELECT * FROM coordinator WHERE email = coordinator_email;
END$$
DELIMITER ;

-- procedure that adds new coordinator into the db
DELIMITER $$
CREATE PROCEDURE `add_new_coordinator`(IN p_first_name NVARCHAR(25),p_last_name NVARCHAR(25),
    p_phone_number NVARCHAR(13),p_email NVARCHAR(40),p_u_password CHAR(64),
    p_organization_name NVARCHAR(40))
BEGIN
    INSERT INTO coordinator(first_name,last_name,phone_number,email,u_password,organization_name)
    VALUES(p_first_name,p_last_name,p_phone_number,p_email,p_u_password,p_organization_name);
END$$
DELIMITER ;
CALL `add_new_coordinator`('אילה','שוורץ','0528990468','ayalas@bat-ami.org.il','$2a$12$y9MshxcZguGenSOyZv0BLONHr31FqxNOJuYbnBTJsvQ8pgOIJaxFy','בת עמי [אמונה-אלומה]'); -- test

-- procedure that updates coordinator in db
DELIMITER $$
CREATE PROCEDURE `update_coordinator`(IN id INT,IN p_first_name NVARCHAR(25),p_last_name NVARCHAR(25),
    p_phone_number NVARCHAR(13),p_email NVARCHAR(40),p_u_password CHAR(64),
    p_organization_name NVARCHAR(40))
BEGIN
    UPDATE coordinator
	SET first_name = p_first_name,
    last_name = p_last_name,
    phone_number = p_phone_number,
    email = p_email,
    u_password = p_u_password,
    organization_name = p_organization_name
	WHERE coordinator_id = id;
END$$
DELIMITER ;
CALL `update_coordinator`('אילה','שוורץ','0528990468','ayalas@bat-ami.org.il','8bb0cf6eb9b17d0f7d22b456f121257dc1254e1f01665370476383ea776df414','בת עמי [אמונה-אלומה]'); -- test

-- procedure that deletes coordinator by id from db
DELIMITER $$
CREATE PROCEDURE `delete_coordinator`(IN id INT)
BEGIN
    DELETE FROM coordinator WHERE coordinator_id = id;
END$$
DELIMITER ;
CALL `delete_coordinator`(6); -- test

-------------------------------- general procedures ---------------------------------

-- this procedure finds user(applicant or coordinator) by email address
DELIMITER $$
CREATE PROCEDURE `get_user_by_email`(IN p_email NVARCHAR(40))
BEGIN
	SELECT first_name,last_name,phone_number,email,u_password,level_user_id FROM coordinator
	WHERE email = p_email
	UNION
	SELECT first_name,last_name,phone_number,email,u_password,level_user_id FROM applicant
	WHERE email = p_email;
END$$
DELIMITER ;



