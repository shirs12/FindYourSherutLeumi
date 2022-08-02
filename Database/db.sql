CREATE DATABASE FindYourSherutLeumiDB;

-- DROP DATABASE FindYourSherutLeumiDB;

USE FindYourSherutLeumiDB;

CREATE TABLE LevelUser(
	CoordinatorLevelID INT UNIQUE,
    ApplicantLevelID INT UNIQUE,
    FOREIGN KEY (CoordinatorLevelID) REFERENCES Coordinator(CoordinatorID),
    FOREIGN KEY (ApplicantLevelID) REFERENCES Applicant(ApplicantID)
);
-- DROP TABLE LevelUser;

CREATE TABLE Applicant(
	ApplicantID INT PRIMARY KEY AUTO_INCREMENT,
    ApplicantLevelID INT,
    FirstName NVARCHAR(25) NOT NULL,
    LastName NVARCHAR(25) NOT NULL,
    PhoneNumber NVARCHAR(13) UNIQUE,
    City NVARCHAR(25),
    Email NVARCHAR(40),
    ApplicantPassword CHAR(64)
);
-- DROP TABLE Applicant;

CREATE TABLE Coordinator(
	CoordinatorID INT PRIMARY KEY AUTO_INCREMENT,
    CoordinatorLevelID INT,
	FirstName NVARCHAR(25) NOT NULL,
    LastName NVARCHAR(25) NOT NULL,
    PhoneNumber NVARCHAR(13) UNIQUE,
	Email NVARCHAR(40) NOT NULL,
    CoordinatorPassword CHAR(64),
    OrganizationName NVARCHAR(40),
    ServiceID INT
);
-- DROP TABLE Coordinator;

CREATE TABLE Service(
	ServiceID INT PRIMARY KEY AUTO_INCREMENT,
    ServiceName NVARCHAR(40) NOT NULL,
    OrganizationName NVARCHAR(40) NOT NULL,
    Category NVARCHAR(50),
    Country NVARCHAR(30) NOT NULL,
    City NVARCHAR(30) NOT NULL,
    StreetName NVARCHAR(30) NOT NULL,
    StreetNum INT(10),
    HasApartment BOOLEAN,
    IsSecondYearOnly BOOLEAN,
    IsMorningService BOOLEAN,
    IsEveningService BOOLEAN,
    DescriptionService NVARCHAR(200),
    CoordinatorName NVARCHAR(25) NOT NULL,
    CoordinatorID INT,
    FOREIGN KEY (CoordinatorID) REFERENCES Coordinator(CoordinatorID)
);
-- DROP TABLE Service;

-- for testing
INSERT INTO Service(ServiceName,OrganizationName,Category,Country,City,StreetName,StreetNum,HasApartment,
    IsSecondYearOnly,IsMorningService,IsEveningService,DescriptionService,CoordinatorName)
    VALUES('name1','organization1','cat1','country1','city1','streetname1',1,true,false,
			true,false,'description1111111111111111111111','coordinator'),
            ('name2','organization2','cat2','country2','city2','streetname2',2,false,false,
			true,false,'description222222222222','coordinator2');
SET SQL_SAFE_UPDATES = 0;
DELETE FROM Service WHERE ServiceName = 'name2';
SET SQL_SAFE_UPDATES = 1;

-- procedure that calls all the services in the db
DELIMITER $$
CREATE PROCEDURE `getAllServices`()
BEGIN
    SELECT * FROM Service;
END$$
DELIMITER ;
CALL `getAllServices`();

-- procedure that adds service into the db
DELIMITER $$
CREATE PROCEDURE `insertServiceInfo`(IN p_ServiceName NVARCHAR(40), IN p_OrganizationName NVARCHAR(40),
 IN p_Category NVARCHAR(50), IN p_Country NVARCHAR(30), IN p_City NVARCHAR(30), IN p_StreetName NVARCHAR(30),
 IN p_StreetNum INT(10), IN p_HasApartment BOOLEAN, IN p_IsSecondYearOnly BOOLEAN, IN p_IsMorningService BOOLEAN,
 IN p_IsEveningService BOOLEAN, IN p_DescriptionService NVARCHAR(200), IN p_CoordinatorName NVARCHAR(25))
BEGIN
    INSERT INTO Service(ServiceName,OrganizationName,Category,Country,City,StreetName,StreetNum,HasApartment,
    IsSecondYearOnly,IsMorningService,IsEveningService,DescriptionService,CoordinatorName)
    VALUES(p_ServiceName,p_OrganizationName,p_Category,p_Country,p_City,p_StreetName,p_StreetNum,p_HasApartment,
    p_IsSecondYearOnly,p_IsMorningService,p_IsEveningService,p_DescriptionService,p_CoordinatorName);
END$$
DELIMITER ;


