--Create all tables.
CREATE TABLE Users(
    UName VARCHAR2(20) PRIMARY KEY,
    Password VARCHAR2(20) NOT NULL,
    StreetName VARCHAR2(20) NOT NULL,
    HouseNumber INT DEFAULT 0,
    City VARCHAR2(20) NOT NULL,
    PostalCode VARCHAR2(20) NOT NULL);

CREATE TABLE Recipe(
    RName VARCHAR2(20) PRIMARY KEY,
    Kind VARCHAR2(20) NOT NULL,
    Pearl INT DEFAULT 0,
    Jelly INT DEFAULT 0,
    Lemon INT DEFAULT 0,
    Orange INT DEFAULT 0);

CREATE TABLE Address(
    PostalCode VARCHAR2(20),
    StreetName VARCHAR2(20),
    House# INT,
    City VARCHAR2(20),
    PRIMARY KEY(PostalCode, StreetName, House#));

CREATE TABLE Grocery(
    GName VARCHAR2(20),
    Amount INT DEFAULT 0,
    BuyingDate DATE,
    Duration INT,
    ExpiryDate DATE,
    PRIMARY KEY(GName, BuyingDate));

CREATE TABLE GroceryDate(
        BuyingDate DATE,
        Duration INT,
        ExpiryDate DATE,
        PRIMARY KEY(BuyingDate, Duration));

CREATE TABLE Buys(
    UName VARCHAR2(20),
    GName VARCHAR2(20),
    BuyingDate DATE,
    PRIMARY KEY(UName, GName, BuyingDate),
    FOREIGN KEY(UName) REFERENCES Users,
    FOREIGN KEY(GName, BuyingDate) REFERENCES Grocery(GName, BuyingDate));

CREATE TABLE MakeRecipe(
    UName VARCHAR2(20),
    RName VARCHAR2(20),
    PRIMARY KEY(UName,RName),
    FOREIGN KEY(UName) REFERENCES Users,
    FOREIGN KEY(RName) REFERENCES Recipe);

CREATE TABLE Usage(
    UseID INT PRIMARY KEY,
     VARCHAR2(20),
     UsingDate DATE NOT NULL,
     Pearl INT DEFAULT 0,
     Jelly INT DEFAULT 0,
     Lemon INT DEFAULT 0,
     Orange INT DEFAULT 0);

CREATE TABLE Generates(
    RName VARCHAR2(20),
    UseID INT,
    PRIMARY KEY(RName, UseID),
    FOREIGN KEY(RName) REFERENCES Recipe ON DELETE CASCADE,
    FOREIGN KEY(UseId) REFERENCES Usage(UseID) ON DELETE CASCADE);

CREATE TABLE DailyReport(
    ReportDay DATE PRIMARY KEY,
    Pearl INT DEFAULT 0,
    Jelly INT DEFAULT 0,
    Lemon INT DEFAULT 0,
    Orange INT DEFAULT 0);

CREATE TABLE Supplier(
    SupplierId INT PRIMARY KEY,
    CompanyName VARCHAR2(20) NOT NULL);

CREATE TABLE Supplies(
    SupplierId INT PRIMARY KEY ,
    GName VARCHAR2(20),
    BuyingDate DATE);

CREATE TABLE ShoppingList(
    GName VARCHAR2(20),
    Amount INT NOT NULL,
    ListDate DATE,
    PRIMARY KEY (GName,ListDate));

CREATE TABLE Lists(
    GName VARCHAR2(20),
    ListDate DATE,
    UseID INT,
    PRIMARY KEY (UseID, GName, ListDate));

--Insert all Users.

INSERT INTO Users VALUES ('Sam', '123', '23rd W Ave', 2341, 'Vancouver', 'V6S1H6');
INSERT INTO Users VALUES ('Lily', '234', '23rd W Ave', 2341, 'Richmond', 'V6S1H6');
INSERT INTO Users VALUES ('Amy', '456', '78th S Ave', 4562, 'Richmond', 'V6S1H6');
INSERT INTO Users VALUES ('Frank', '567', 'King St', 6753, 'Burnaby', 'V6S1H6');
INSERT INTO Users VALUES ('Jasmine', '678', 'Queen St', 2413, 'Vancouver', 'H3M2N9');

--Insert all Recipes

INSERT INTO Recipe VALUES ('Pearl Milk Tea', 'Black Tea', 20, 0, 0, 0);
INSERT INTO Recipe VALUES ('Jelly Milk Tea', 'Black Tea', 0, 20, 0, 0);
INSERT INTO Recipe VALUES ('Pearl Jelly Milk Tea', 'Green Tea', 10, 10, 0, 0);
INSERT INTO Recipe VALUES ('Lemon Tea', 'Green Tea', 0, 0, 2, 0);
INSERT INTO Recipe VALUES ('Orange Tea', 'Green Tea', 0, 0, 0, 2);

--Insert all Address
INSERT INTO Address VALUES ('V6S1H6', '23rd W Ave', 2341, 'Vancouver');
INSERT INTO Address VALUES ('V3T1K4', 'Binary Ave', 482, 'Richmond');
INSERT INTO Address VALUES ('V4Z2T3', '78th S Ave',	4562, 'Richmond');
INSERT INTO Address VALUES ('H3K2B8', 'King St', 6753, 'Burnaby');
INSERT INTO Address VALUES ('H3M2N9', 'Queen St', 2413,	'Vancouver');

--Insert all Grocery
INSERT INTO Grocery VALUES ('Pearl', 300, '2020-02-14', 10, '2020-02-24');
INSERT INTO Grocery VALUES ('Jelly', 200, '2021-02-16', 10, '2021-02-24');
INSERT INTO Grocery VALUES ('Orange', 10, '2021-02-05', 20, '2021-02-25');
INSERT INTO Grocery VALUES ('Lemon', 50, '2021-02-01', 23, '2021-02-24');

--Insert all Grocery Date
INSERT INTO GroceryDate VALUES ('2020-02-14', 10, '2020-02-24');
INSERT INTO GroceryDate VALUES ('2021-02-16', 10, '2021-02-24');
INSERT INTO GroceryDate VALUES ('2021-02-05', 20, '2021-02-25');
INSERT INTO GroceryDate VALUES ('2021-02-01', 23, '2021-02-24');

--Insert all Buys
INSERT INTO Buys VALUES ('Sam', 'Pearl', '2021-02-14');
INSERT INTO Buys VALUES ('Sam', 'Jelly', '2021-02-16');
INSERT INTO Buys VALUES ('Amy', 'Orange', '2021-02-05');
INSERT INTO Buys VALUES ('Frank', 'Lemon', '2021-02-01');

--Insert all MakeRecipe
INSERT INTO MakeRecipe VALUES ('Sam', 'Pearl Milk Tea');
INSERT INTO MakeRecipe VALUES ('Lily', 'Pearl Milk Tea');
INSERT INTO MakeRecipe VALUES ('Amy', 'Lemon Tea');
INSERT INTO MakeRecipe VALUES ('Frank', 'Orange Tea');
INSERT INTO MakeRecipe VALUES ('Jasmine', 'Pearl Jelly Milk Tea');

--Insert all Usage
INSERT INTO Usage VALUES (1, 'Pearl Milk Tea', '2021-01-01', 20, 0, 0, 0);
INSERT INTO Usage VALUES (2, 'Pearl Milk Tea', '2021-01-01', 20, 0, 0, 0);
INSERT INTO Usage VALUES (3, 'Jelly Milk Tea', '2021-01-03', 0, 20, 0, 0);
INSERT INTO Usage VALUES (4, 'Pearl Jelly Milk Tea', '2021-01-04', 10, 10, 0, 0);
INSERT INTO Usage VALUES (5, 'Orange Tea', '2021-01-05', 0, 0, 2, 0);

--Insert All Generates
INSERT INTO Generates VALUES ('Pearl Milk Tea', 1);
INSERT INTO Generates VALUES ('Pearl Milk, Tea', 2);
INSERT INTO Generates VALUES ('Jelly Milk Tea', 3);
INSERT INTO Generates VALUES ('Pearl Jelly Milk Tea', 4);
INSERT INTO Generates VALUES ('Orange Tea', 5);

--Insert All DailyReport
INSERT INTO DailyReport VALUES ('2021-01-01', 20, 0, 0, 0);
INSERT INTO DailyReport VALUES ('2021-01-02', 0, 0, 0, 0);
INSERT INTO DailyReport VALUES ('2021-01-03', 40, 20, 0, 0);
INSERT INTO DailyReport VALUES ('2021-01-04', 40, 40, 20, 20);
INSERT INTO DailyReport VALUES ('2021-01-05', 0, 0, 0, 0);

--Insert All Supplier
INSERT INTO Supplier VALUES (1, 'Costco');
INSERT INTO Supplier VALUES (2, 'T&T Supermarket');
INSERT INTO Supplier VALUES (3, 'No Frills');
INSERT INTO Supplier VALUES (4, 'Safeway');
INSERT INTO Supplier VALUES (5, 'SaveOnFoods');

--Insert All Supplies
INSERT INTO Supplies VALUES (1, 'Pearl', '2021-01-01');
INSERT INTO Supplies VALUES (2, 'Jelly', '2021-01-02');
INSERT INTO Supplies VALUES (3, 'Orange', '2021-01-03');
INSERT INTO Supplies VALUES (4, 'Lemon', '2021-01-04');
INSERT INTO Supplies VALUES (5, 'Pearl', '2021-01-03');

--Insert All ShoppingLists
INSERT INTO ShoppingList VALUES ('Pearl', 300, '2021-01-01');
INSERT INTO ShoppingList VALUES ('Jelly', 200, '2021-01-01');
INSERT INTO ShoppingList VALUES ('Orange', 300, '2021-01-03');
INSERT INTO ShoppingList VALUES ('Lemon', 10, '2021-01-04');
INSERT INTO ShoppingList VALUES ('Pearl', 300, '2021-01-04');

--Insert All Lists
INSERT INTO Lists VALUES ('Pearl', '2021-01-01', 1);
INSERT INTO Lists VALUES ('Jelly', '2021-01-03', 3);
INSERT INTO Lists VALUES ('Orange', '2021-01-05', 5);
INSERT INTO Lists VALUES ('Pearl', '2021-01-01', 2);
INSERT INTO Lists VALUES ('Pearl', '2021-01-04', 4); 