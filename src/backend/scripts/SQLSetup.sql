--Create all tables.
CREATE TABLE Address(
    StreetName VARCHAR2(20),
    HouseNumber INT,
    City VARCHAR2(20),
    PostalCode VARCHAR2(20),
    PRIMARY KEY(StreetName, HouseNumber, PostalCode));

CREATE TABLE Users(
    UName VARCHAR2(20) PRIMARY KEY,
    Password VARCHAR2(20) NOT NULL,
    StreetName VARCHAR2(20) NOT NULL,
    HouseNumber INT DEFAULT 0,
    PostalCode VARCHAR2(20) NOT NULL,
    FOREIGN KEY(StreetName, HouseNumber, PostalCode) REFERENCES Address);

CREATE TABLE Recipe(
    RName VARCHAR2(20) PRIMARY KEY,
    Kind VARCHAR2(20) NOT NULL,
    Pearl INT DEFAULT 0,
    Jelly INT DEFAULT 0,
    Lemon INT DEFAULT 0,
    Orange INT DEFAULT 0);

CREATE TABLE MakeRecipe(
    UName VARCHAR2(20),
    RName VARCHAR2(20),
    PRIMARY KEY(UName,RName),
    FOREIGN KEY(UName) REFERENCES Users,
    FOREIGN KEY(RName) REFERENCES Recipe);

CREATE TABLE Usage(
    UseID INT PRIMARY KEY,
    RName VARCHAR2(20),
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
    FOREIGN KEY(UseId) REFERENCES Usage ON DELETE CASCADE);

CREATE TABLE GroceryDate(
    BuyingDate DATE,
    Duration INT,
    ExpiryDate DATE,
    PRIMARY KEY(BuyingDate, Duration));

CREATE TABLE Grocery(
    GName VARCHAR2(20),
    Amount INT DEFAULT 0,
    BuyingDate DATE,
    Duration INT,
    PRIMARY KEY(GName, BuyingDate),
    FOREIGN KEY(BuyingDate, Duration) REFERENCES GroceryDate);

CREATE TABLE Buys(
    UName VARCHAR2(20),
    GName VARCHAR2(20),
    BuyingDate DATE,
    PRIMARY KEY(UName, GName, BuyingDate),
    FOREIGN KEY(UName) REFERENCES Users,
    FOREIGN KEY(GName, BuyingDate) REFERENCES Grocery);

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
    SupplierId INT,
    GName VARCHAR2(20),
    PRIMARY KEY(SupplierId, GName),
    FOREIGN KEY(SupplierId) REFERENCES Supplier);

CREATE TABLE ShoppingList(
    GName VARCHAR2(20),
    ListDate DATE,
    Amount INT NOT NULL,
    PRIMARY KEY (GName,ListDate));

CREATE TABLE Lists(
    GName VARCHAR2(20),
    ListDate DATE,
    PRIMARY KEY(GName, ListDate),
    FOREIGN KEY(GName, ListDate) REFERENCES ShoppingList);

--Insert all Address
INSERT INTO Address VALUES ('23rd W Ave', 2341, 'Vancouver', 'V6S1H6');
INSERT INTO Address VALUES ('Binary Ave', 482, 'Richmond', 'V3T1K4');
INSERT INTO Address VALUES ('78th S Ave', 4562, 'Richmond', 'V4Z2T3');
INSERT INTO Address VALUES ('King St', 6753, 'Burnaby', 'H3K2B8');
INSERT INTO Address VALUES ('Queen St', 2413,	'Vancouver', 'H3M2N9');

--Insert all Users.
INSERT INTO Users VALUES ('Sam', '123', '23rd W Ave', 2341, 'V6S1H6');
INSERT INTO Users VALUES ('Lily', '234', 'Binary Ave', 482, 'V3T1K4');
INSERT INTO Users VALUES ('Amy', '456', '78th S Ave', 4562, 'V4Z2T3');
INSERT INTO Users VALUES ('Frank', '567', 'King St', 6753, 'H3K2B8');
INSERT INTO Users VALUES ('Jasmine', '678', 'Queen St', 2413, 'H3M2N9');

--Insert all Recipes
INSERT INTO Recipe VALUES ('Pearl Milk Tea', 'Milk Tea', 20, 0, 0, 0);
INSERT INTO Recipe VALUES ('Jelly Milk Tea', 'Milk Tea', 0, 20, 0, 0);
INSERT INTO Recipe VALUES ('Milk Tea', 'Milk Tea', 10, 10, 0, 0);
INSERT INTO Recipe VALUES ('Lemon Tea', 'Fruit Tea', 0, 0, 2, 0);
INSERT INTO Recipe VALUES ('Orange Tea', 'Fruit Tea', 0, 0, 0, 2);

--Insert all MakeRecipe
INSERT INTO MakeRecipe VALUES ('Sam', 'Pearl Milk Tea');
INSERT INTO MakeRecipe VALUES ('Lily', 'Pearl Milk Tea');
INSERT INTO MakeRecipe VALUES ('Amy', 'Lemon Tea');
INSERT INTO MakeRecipe VALUES ('Frank', 'Orange Tea');
INSERT INTO MakeRecipe VALUES ('Jasmine', 'Milk Tea');

--Insert all Usage
INSERT INTO Usage VALUES (1, 'Pearl Milk Tea', DATE'2021-02-01', 20, 0, 0, 0);
INSERT INTO Usage VALUES (2, 'Pearl Milk Tea', DATE'2021-02-01', 20, 0, 0, 0);
INSERT INTO Usage VALUES (3, 'Jelly Milk Tea', DATE'2021-02-03', 0, 20, 0, 0);
INSERT INTO Usage VALUES (4, 'Milk Tea', DATE'2021-02-04',10, 10, 0, 0);
INSERT INTO Usage VALUES (5, 'Orange Tea', DATE'2021-02-05', 0, 0, 2, 0);

--Insert All Generates
INSERT INTO Generates VALUES ('Pearl Milk Tea', 1);
INSERT INTO Generates VALUES ('Pearl Milk Tea', 2);
INSERT INTO Generates VALUES ('Jelly Milk Tea', 3);
INSERT INTO Generates VALUES ('Milk Tea', 4);
INSERT INTO Generates VALUES ('Orange Tea', 5);

--Insert all Grocery Date
INSERT INTO GroceryDate VALUES (DATE'2021-02-14', 10, DATE'2020-02-24');
INSERT INTO GroceryDate VALUES (DATE'2021-02-16', 10, DATE'2021-02-24');
INSERT INTO GroceryDate VALUES (DATE'2021-02-05', 20, DATE'2021-02-25');
INSERT INTO GroceryDate VALUES (DATE'2021-02-01', 20, DATE'2021-02-21');
INSERT INTO GroceryDate VALUES (DATE'2021-02-04', 20, DATE'2021-02-24');

--Insert all Grocery
INSERT INTO Grocery VALUES ('Pearl', 300, DATE'2021-02-14', 10);
INSERT INTO Grocery VALUES ('Jelly', 200, DATE'2021-02-16', 10);
INSERT INTO Grocery VALUES ('Orange', 10, DATE'2021-02-05', 20);
INSERT INTO Grocery VALUES ('Lemon', 50, DATE'2021-02-01', 20);
INSERT INTO Grocery VALUES ('Orange', 20, DATE'2021-02-04', 20);

--Insert all Buys
INSERT INTO Buys VALUES ('Sam', 'Pearl', DATE'2021-02-14');
INSERT INTO Buys VALUES ('Sam', 'Jelly', DATE'2021-02-16');
INSERT INTO Buys VALUES ('Amy', 'Orange', DATE'2021-02-05');
INSERT INTO Buys VALUES ('Frank', 'Lemon', DATE'2021-02-01');
INSERT INTO Buys VALUES ('Jasmine', 'Orange', DATE'2021-02-04');

--Insert All DailyReport
INSERT INTO DailyReport VALUES (DATE'2021-01-01', 20, 0, 0, 0);
INSERT INTO DailyReport VALUES (DATE'2021-01-02', 0, 0, 0, 0);
INSERT INTO DailyReport VALUES (DATE'2021-01-03', 40, 20, 0, 0);
INSERT INTO DailyReport VALUES (DATE'2021-01-04', 40, 40, 20, 20);
INSERT INTO DailyReport VALUES (DATE'2021-01-05', 0, 0, 0, 0);

--Insert All Supplier
INSERT INTO Supplier VALUES (1, 'Costco');
INSERT INTO Supplier VALUES (2, 'T&T Supermarket');
INSERT INTO Supplier VALUES (3, 'No Frills');
INSERT INTO Supplier VALUES (4, 'Safeway');
INSERT INTO Supplier VALUES (5, 'SaveOnFoods');

--Insert All Supplies
INSERT INTO Supplies VALUES (1, 'Pearl');
INSERT INTO Supplies VALUES (2, 'Jelly');
INSERT INTO Supplies VALUES (3, 'Orange');
INSERT INTO Supplies VALUES (4, 'Lemon');
INSERT INTO Supplies VALUES (5, 'Pearl');

--Insert All ShoppingLists
INSERT INTO ShoppingList VALUES ('Pearl', DATE'2021-01-01', 300);
INSERT INTO ShoppingList VALUES ('Jelly', DATE'2021-01-01', 200);
INSERT INTO ShoppingList VALUES ('Orange', DATE'2021-01-03', 300);
INSERT INTO ShoppingList VALUES ('Lemon', DATE'2021-01-04', 10);
INSERT INTO ShoppingList VALUES ('Pearl', DATE'2021-01-04', 300);

--Insert All Lists
INSERT INTO Lists VALUES ('Pearl', DATE'2021-01-01');
INSERT INTO Lists VALUES ('Jelly', DATE'2021-01-01');
INSERT INTO Lists VALUES ('Orange', DATE'2021-01-03');
INSERT INTO Lists VALUES ('Lemon', DATE'2021-01-04');
INSERT INTO Lists VALUES ('Pearl', DATE'2021-01-04');