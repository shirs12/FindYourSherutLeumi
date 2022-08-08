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

CALL add_new_service("והדרת פני זקן אשקלון","בת עמי [אמונה-אלומה]","קשישים","ישראל","אשקלון","הפרויקט פועל ברחבי העיר",
				0,true,false,true,false,"אם הינך בת שירות הרוצה לקחת חלק בחוויה של מייסדי המדינה עם סיפורי גבורה של ניצולי שואה - מקומך איתנו. העשייה היא להאיר את עולמם של אלו שבזכותם אנחנו כאן. אנו מחפשים בת בוגרת, אחראית, אוהבת קשישים, סבלנית ורצינית. דרושות בנות עם רצון גדול לתרום והמון בגרות נפשית , מודעות רגשית ליכולות ולעצמן , מסוגלות להכיל , אבל גם ליצור קשר אישי וחם עם הקשישים.","ביבס שמרית");

CALL update_service(13,"איגוד ערים לשמירת איכות הסביבה","בת עמי [אמונה-אלומה]","הדרכה","ישראל","חדרה","המסגר 3, אזור תעשיה דרומי",3,
				true,false,true,false,"בנות השירות משתלבות בעבודה החינוכית של היחידה הסביבתית המקומית ואחראיות על הדרכות בגני ילדים, בבתי ספר ובמתנסים. הבנות מפעילות גינות קהילתיות, מובילות מועצות נוער ירוקות עירוניות ושותפות בהכנת הכשרות והשתלמויות לצוותים חינוכיים.","שוורץ אילה");



-- ................. procedures ...................

-- SERVICE
-- procedure that calls all the services from db
DELIMITER $$
CREATE PROCEDURE `get_all_services`()
BEGIN
    SELECT service_id,service_name,organization_name,category,country,city,street_name,street_num,has_apartment,
    is_second_year_only,is_morning_service,is_evening_service,description_service,coordinator_name FROM service;
END$$
DELIMITER ;
CALL `get_all_services`(); -- test

-- procedure that calls a specific service from db
DELIMITER $$
CREATE PROCEDURE `get_service_by_id`(IN id INT)
BEGIN
	SELECT service_id,service_name,organization_name,category,country,city,street_name,
			street_num,has_apartment,is_second_year_only,is_morning_service,
			is_evening_service,description_service,coordinator_name
	FROM service WHERE service_id = id;
END$$
DELIMITER ;
CALL `get_service_by_id`(1); -- test

-- procedure that adds new service into the db
DELIMITER $$
CREATE PROCEDURE `add_new_service`(IN p_service_name NVARCHAR(40), IN p_organization_name NVARCHAR(40),
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

-- procedure that updates service in db
DELIMITER $$
CREATE PROCEDURE `update_service`(IN id INT, IN p_service_name NVARCHAR(40), IN p_organization_name NVARCHAR(40),
 IN p_category NVARCHAR(50), IN p_country NVARCHAR(30), IN p_city NVARCHAR(30), IN p_street_name NVARCHAR(30),
 IN p_street_num INT(10), IN p_has_apartment BOOLEAN, IN p_is_second_year_only BOOLEAN, IN p_is_morning_service BOOLEAN,
 IN p_is_evening_service BOOLEAN, IN p_description_service NVARCHAR(200), IN p_coordinator_name NVARCHAR(25))
BEGIN
    UPDATE service
	SET service_name = p_service_name,
    organization_name = p_organization_name,
    category = p_category,
    country = p_country,
    city = p_city,
    street_name = p_street_name,
    street_num = p_street_num,
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
    SELECT first_name,last_name,phone_number,
		city,email,applicant_password
    FROM applicant;
    END$$
DELIMITER ;
CALL `get_all_applicants`(); -- test

-- procedure that calls a specific applicant from db
DELIMITER $$
CREATE PROCEDURE `get_applicant_by_id`(IN id INT)
BEGIN
	SELECT first_name,last_name,phone_number,
		city,email,applicant_password
    FROM applicant WHERE applicant_id = id;
END$$
DELIMITER ;
CALL `get_applicant_by_id`(2); -- test

-- procedure that adds new applicant into the db
DELIMITER $$
CREATE PROCEDURE `add_new_applicant`(IN p_first_name NVARCHAR(25),
    IN p_last_name NVARCHAR(25),IN p_phone_number NVARCHAR(13),
    IN p_city NVARCHAR(25),IN p_email NVARCHAR(40),IN p_applicant_password CHAR(64))
BEGIN
    INSERT INTO applicant(first_name,last_name,phone_number,city,email,applicant_password)
    VALUES(p_first_name,p_last_name,p_phone_number,p_city,p_email,p_applicant_password);
END$$
DELIMITER ;
CALL `add_new_applicant`('אור','אליה','0503758493','ראש העין','orEli123@gmail.com','481f6cc0511143ccdd7e2d1b1b94faf0a700a8b49cd13922a70b5ae28acaa8c5'); -- test

-- procedure that updates applicant in db
DELIMITER $$
CREATE PROCEDURE `update_applicant`(IN id INT,IN p_first_name NVARCHAR(25),
    IN p_last_name NVARCHAR(25),IN p_phone_number NVARCHAR(13),
    IN p_city NVARCHAR(25),IN p_email NVARCHAR(40),IN p_applicant_password CHAR(64))
BEGIN
    UPDATE applicant
	SET first_name = p_first_name,
    last_name = p_last_name,
    phone_number = p_phone_number,
    city = p_city,
    email = p_email,
    applicant_password = p_applicant_password
	WHERE applicant_id = id;
END$$
DELIMITER ;
CALL `update_applicant`(6,'אביה','אליה','0503758483','ראש העין','orEli123@gmail.com','481f6cc0511143ccdd7e2d1b1b94faf0a700a8b49cd13922a70b5ae28acaa8c5');

-- procedure that deletes applicant by id from db
DELIMITER $$
CREATE PROCEDURE `delete_applicant`(IN id INT)
BEGIN
    DELETE FROM applicant WHERE applicant_id = id;
END$$
DELIMITER ;
CALL `delete_applicant`(6); -- test




