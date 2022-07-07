CREATE DATABASE FindYourSherutLeumiDB;

DROP DATABASE FindYourSherutLeumiDB;

USE FindYourSherutLeumiDB;

CREATE TABLE LevelUser(
	CoordinatorLevelID INT UNIQUE,
    ApplicantLevelID INT UNIQUE,
    FOREIGN KEY (CoordinatorLevelID) REFERENCES Coordinator(CoordinatorID),
    FOREIGN KEY (ApplicantLevelID) REFERENCES Applicant(ApplicantID)
);
DROP TABLE LevelUser;

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
DROP TABLE Applicant;

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
DROP TABLE Coordinator;

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
DROP TABLE Service;
