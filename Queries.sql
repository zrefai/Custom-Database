/*
DROP TABLE Manufacture;
DROP TABLE Updates;
DROP TABLE Transactions;
DROP TABLE Cut_Job;
DROP TABLE Fit_Job;
DROP TABLE Paint_Job;
DROP TABLE Job;
DROP TABLE Assembly;
DROP TABLE Paint;
DROP TABLE Fit;
DROP TABLE Cut;
DROP TABLE Process;
DROP TABLE Department;
DROP TABLE Customer;
DROP TABLE Assembly_Account;
DROP TABLE Process_Account;
DROP TABLE Department_Account;
DROP TABLE Account;

CREATE TABLE Account (
    account_ID INT PRIMARY KEY NOT NULL,
    date_established DATE NOT NULL
);

CREATE TABLE Assembly_Account(
    account_ID INT PRIMARY KEY NOT NULL,
    details_1 INT NOT NULL,

    FOREIGN KEY(account_ID) REFERENCES Account(account_ID)
);

CREATE TABLE Department_Account(
    account_ID INT PRIMARY KEY NOT NULL,
    details_2 INT NOT NULL,
    
    FOREIGN KEY(account_ID) REFERENCES Account(account_ID)
);

CREATE TABLE Process_Account(
    account_ID INT PRIMARY KEY NOT NULL,
    details_3 INT NOT NULL,

    FOREIGN KEY(account_ID) REFERENCES Account(account_ID)
);

CREATE TABLE Customer (
    customer_name VARCHAR(64) PRIMARY KEY NOT NULL,
    customer_address VARCHAR(64) NOT NULL,
    customer_category INT NOT NULL CHECK (customer_category between 1 and 10)
);

CREATE TABLE Assembly (
    assembly_ID INT PRIMARY KEY NOT NULL,
    date_ordered DATE NOT NULL,
    assembly_details VARCHAR(64) NOT NULL,
    customer_name VARCHAR(64),
    account_ID INT,

    FOREIGN KEY(customer_name) REFERENCES Customer(customer_name),
    FOREIGN KEY(account_ID) REFERENCES Account(account_ID)
);

CREATE TABLE Department (
    dept_ID INT PRIMARY KEY NOT NULL,
    dept_data VARCHAR(64) NOT NULL,
    account_ID INT,

    FOREIGN KEY(account_ID) REFERENCES Account(account_ID)
);

CREATE TABLE Process (
    proc_ID INT PRIMARY KEY NOT NULL,
    process_data VARCHAR(64) NOT NULL,
    dept_ID INT,
    account_ID INT,

    FOREIGN KEY(dept_ID) REFERENCES Department(dept_ID),
    FOREIGN KEY(account_ID) REFERENCES Account(account_ID)
);

CREATE TABLE Paint (
    proc_ID INT PRIMARY KEY NOT NULL,
    color VARCHAR(64) NOT NULL,
    volume INT NOT NULL,
    labor_time DECIMAL(6,3) NOT NULL,
    FOREIGN KEY(proc_ID) REFERENCES Process(proc_ID)
);

CREATE TABLE Fit (
    proc_ID INT PRIMARY KEY NOT NULL,
    fit_type VARCHAR(64) NOT NULL,
    FOREIGN KEY(proc_ID) REFERENCES Process(proc_ID)
);

CREATE TABLE Cut (
    proc_ID INT PRIMARY KEY NOT NULL,
    cutting_type VARCHAR(64) NOT NULL,
    machine_type VARCHAR(64) NOT NULL,
    FOREIGN KEY(proc_ID) REFERENCES Process(proc_ID)
);

CREATE TABLE Job (
    job_ID INT PRIMARY KEY NOT NULL,
    date_commenced DATE NOT NULL,
    date_completed DATE,
    assembly_ID INT NOT NULL,
    proc_ID INT NOT NULL,

    FOREIGN KEY(assembly_ID) REFERENCES Assembly(assembly_ID),
    FOREIGN KEY(proc_ID) REFERENCES Process(proc_ID)
);

CREATE TABLE Cut_Job (
    job_ID INT PRIMARY KEY NOT NULL,
    machine_type VARCHAR(64) NOT NULL,
    machine_time DECIMAL(6,3) NOT NULL,
    material VARCHAR(64) NOT NULL,
    labor_time DECIMAL(6,3) NOT NULL,
    assembly_ID INT NOT NULL,
    proc_ID INT NOT NULL,

    FOREIGN KEY(job_ID) REFERENCES Job(job_ID),
    FOREIGN KEY(assembly_ID) REFERENCES Assembly(assembly_ID),
    FOREIGN KEY(proc_ID) REFERENCES Process(proc_ID)
);

CREATE TABLE Paint_Job (
    job_ID INT PRIMARY KEY NOT NULL,
    color VARCHAR(64) NOT NULL,
    volume DECIMAL(6,3) NOT NULL,
    labor_time DECIMAL(6,3)NOT NULL,
    assembly_ID INT NOT NULL,
    proc_ID INT NOT NULL,

    FOREIGN KEY(job_ID) REFERENCES Job(job_ID),
    FOREIGN KEY(assembly_ID) REFERENCES Assembly(assembly_ID),
    FOREIGN KEY(proc_ID) REFERENCES Process(proc_ID)
);

CREATE TABLE Fit_Job(
    job_ID INT PRIMARY KEY NOT NULL,
    labor_time DECIMAL(6,3) NOT NULL,
    assembly_ID INT NOT NULL,
    proc_ID INT NOT NULL,

    FOREIGN KEY(job_ID) REFERENCES Job(job_ID),
    FOREIGN KEY(assembly_ID) REFERENCES Assembly(assembly_ID),
    FOREIGN KEY(proc_ID) REFERENCES Process(proc_ID)
    
);

CREATE TABLE Transactions(
    trans_ID INT PRIMARY KEY NOT NULL,
    sup_cost INT NOT NULL,
    job_ID INT NOT NULL,

    FOREIGN KEY(job_ID) REFERENCES Job(job_ID)
);

CREATE TABLE Manufacture(
    proc_ID INT NOT NULL,
    assembly_ID INT NOT NULL,

    PRIMARY KEY (proc_ID, assembly_ID),
    FOREIGN KEY (proc_ID) REFERENCES Process(proc_ID),
    FOREIGN KEY (assembly_ID) REFERENCES Assembly(assembly_ID)
)

CREATE TABLE Updates(
    account_ID INT NOT NULL,
    trans_ID INT NOT NULL,

    PRIMARY KEY (account_ID, trans_ID),
    FOREIGN KEY (account_ID) REFERENCES Account(account_ID),
    FOREIGN KEY (trans_ID) REFERENCES Transactions(trans_ID)
);
*/
/*
-- PROCEDURE FOR ERROR HANDLING --
DROP PROCEDURE error_handling;
GO
CREATE PROC error_handling 
AS
    SELECT
		ERROR_NUMBER() AS ErrorNumber,
		ERROR_MESSAGE() AS ErrorMessage;

        IF (ERROR_NUMBER() = 2627)
            BEGIN
                PRINT N'ERROR MESSAGE: Cannot insert duplicate key into table'
            END

        IF (ERROR_NUMBER() = 245)
            BEGIN
                PRINT N'ERROR MESSAGE: Wrong data type for an attribute'
            END
         
        IF (ERROR_NUMBER() = 102)
            BEGIN
                PRINT N'ERROR MESSAGE: Improper character added for an attribute'
            END 

        IF (ERROR_NUMBER() = 547) 
            BEGIN 
                PRINT N'ERROR MESSAGE: Trying to insert value as FOREIGN KEY that does not exist in main entity set(s)'
            END

        IF (XACT_STATE()) = -1  
        BEGIN  
            PRINT  N'The transaction is in an uncommittable state.' +  
                    'Rolling back transaction.'  
            ROLLBACK TRANSACTION;  
        END
GO

--------------- QUERY 1 ---------------
--Enter a new customer

DELETE FROM Customer 

BEGIN TRY
-- Try to add in a new customer
    INSERT INTO Customer (customer_name, customer_address, customer_category)
    VALUES (
        'Zaki Refai', '1063 Greystone Crest', 'Premium')
END TRY
BEGIN CATCH
	EXEC error_handling;
END CATCH

SELECT *
FROM Customer

--------------- QUERY 2 ---------------
--Enter a new department

DELETE FROM Department

BEGIN TRY
--Try to add in a new department
    INSERT INTO Department (dept_ID, dept_data)
    VALUES (
        111, 'Data')
END TRY
BEGIN CATCH
	EXEC error_handling;
END CATCH

SELECT *
FROM Department

--------------- QUERY 3 ---------------
--Enter a new assembly with its customer-name, assembly-detials, assembly-id, and date ordered

--Customer data has to 

DELETE FROM Assembly
DELETE FROM Customer

INSERT INTO Customer (customer_name, customer_address, customer_category)
    VALUES (
        'Zaki Refai', '1063 Greystone Crest', 'Premium')

SELECT *
FROM Customer

BEGIN TRY
    INSERT INTO Assembly(assembly_ID, date_ordered, assembly_details, customer_name)
    VALUES (
        111, '01:01:01', 'Details', 'Zaki Refai'
    )
END TRY
BEGIN CATCH
	EXEC error_handling;
END CATCH

SELECT *
FROM Assembly


--------------- QUERY 4 ---------------
--Enter a new process-id and its department together with its type and information relevant to the type

--Need to ask what type is going to be used to generate correct questions to ask
--Department-ID has to exist before entering the data
DELETE FROM Process
DELETE FROM Department
INSERT INTO Department(dept_ID, dept_data) 
VALUES (
    100, 'Data')

SELECT *
FROM Department

BEGIN TRY
    INSERT INTO Process(proc_ID, process_data, dept_ID)
    VALUES (
        1000, 'Data', 100)
END TRY
BEGIN CATCH
	EXEC error_handling;
END CATCH

SELECT *
FROM Process

--------------- QUERY 5 ---------------
--Create a new account and associate it with the process, assembly, or department to which it is applicable

--Needs to have the associated process, assembly, or department id to associate it with an account
--Need to ask for which kind of account
DELETE FROM Department_Account
DELETE FROM Account
DELETE FROM Department

INSERT INTO Department(dept_ID, dept_data)
VALUES (
    100, 'Data'
)

BEGIN TRY
    --Entering into department account
    INSERT INTO Account(account_ID, date_established)
    VALUES (
        10102, '01:01:01')

    INSERT INTO Department_Account(account_ID, details_2)
    VALUES (
        10102, 100)

    --Department has to exist to update it with account
    UPDATE Department
    SET account_ID = 10102
    WHERE dept_ID = 100

END TRY
BEGIN CATCH
	EXEC error_handling;
END CATCH

SELECT *
FROM Department 

SELECT *
from Department_Account

Select *
from Account

--------------- QUERY 6 ---------------
--Enter a new job, given its job-no, assembly-id, process-id, and date the job commenced

--Corresponding assembly and process have to be avaliable to enter a new job
DELETE FROM Job
DELETE FROM Process
DELETE FROM Assembly

-- Make sure values exist in process and assembly first
INSERT INTO Process(proc_ID, process_data)
VALUES (
    1, 'Data'
)

INSERT INTO Assembly(assembly_ID, date_ordered, assembly_details)
VALUES (
    1, '01:01:01', 'Details'
)

BEGIN TRY
    --Entering new job with assembly_ID, and proc_ID
    INSERT INTO Job(job_ID, date_commenced, date_completed, assembly_ID, proc_ID)
    VALUES (
        1, '01:01:01', '01:01:03', 1, 1
    )

END TRY
BEGIN CATCH
	EXEC error_handling;
END CATCH

SELECT *
FROM Job

SELECT * 
FROM Process

SELECT *
FROM Assembly

--------------- QUERY 7 ---------------
--At the completion of a job, enter the date it completed and the information relevant to the type of job

--A entered job should already have proc_ID and assembly_ID
--Need to query for date completed
--Need to have the job type and query information relevant to job type

--Inserting prerequisites
DELETE FROM Job
DELETE FROM Process
DELETE FROM Assembly


INSERT INTO Process(proc_ID, process_data)
VALUES (
    1, 'Data'
)

INSERT INTO Assembly(assembly_ID, date_ordered, assembly_details)
VALUES (
    1, '01:01:01', 'Details'
)

INSERT INTO Job(job_ID, date_commenced,assembly_ID, proc_ID)
VALUES (1, '2019-06-01', 1, 1)

BEGIN TRY
    --Entering updating job with new date finished
    UPDATE Job
    SET  date_completed = '2019-06-23'
    WHERE job_ID = 1

END TRY
BEGIN CATCH
	EXEC error_handling;
END CATCH

SELECT *
FROM Job

--------------- QUERY 8 ---------------
--Enter a transaction-no and its sup-cost and update all the costs (details) of the affected 
-- accounts by adding sup-cost to their current calues of details

--Need to query for transaction no and sup-costs and job-ID
--Need to find account type based on id or query for account type

DELETE FROM Updates
DELETE FROM Transactions
DELETE FROM Job
DELETE FROM Process
DELETE FROM Assembly
DELETE FROM Assembly_Account
DELETE FROM Department_Account
DELETE FROM Process_Account
DELETE FROM Account

--Entering prerequisites
INSERT INTO Process(proc_ID, process_data)
VALUES (
    1, 'Data'
)

INSERT INTO Assembly(assembly_ID, date_ordered, assembly_details)
VALUES (
    1, '01:01:01', 'Details'
)

INSERT INTO Job (job_ID, date_commenced, assembly_ID, proc_ID)
VALUES (
    1, '2019-11-11', 1, 1
)

--Original account_ID is the same across all accounts
INSERT INTO Account(account_ID, date_established)
VALUES (1, '2019-01-01'),
(2, '2019-01-01'),
(3, '2019-01-01')

--Need to update different entities with different accounts
INSERT INTO Assembly_Account(account_ID, details_1)
VALUES (1, '100')

INSERT INTO Department_Account(account_ID, details_2)
VALUES (2, '105')

INSERT INTO Process_Account(account_ID, details_3)
VALUES (3, '110')

--Need to query for the job_ID as well, the transaction number and the sup_cost

--Need to query for account ID and account type
    --Please enter the account ID and the account type separated by a comma (ex. ID, <type>)
--For loop through all of the account IDs / account types

BEGIN TRY
    --Entering new transaction with sup_cost and existing job_ID 
    INSERT INTO Transactions (trans_ID, sup_cost, job_ID) 
    VALUES (
        1, '100', 1
    )

    --Take this transaction and add it to the details of accounts queried 
    INSERT INTO Updates (account_ID, trans_ID)
    VALUES (1, 1),
    (2, 1),
    (3, 1)

    --Now add sup-cost to account_ID
    --This case is when we update all three accounts, quering type of account will benefit
    -- since we can just sub in the type into the UPDATE clause when we need to on a for loop
    UPDATE Assembly_Account
    SET details_1 += (SELECT sup_cost
    FROM Transactions 
    WHERE trans_ID = 1)
    WHERE account_ID = 1

    UPDATE Department_Account
    SET details_2 += (SELECT sup_cost
    FROM Transactions 
    WHERE trans_ID = 1)
    WHERE account_ID = 2

    UPDATE Process_Account
    SET details_3 += (SELECT sup_cost
    FROM Transactions 
    WHERE trans_ID = 1)
    WHERE account_ID = 3

END TRY
BEGIN CATCH
	EXEC error_handling;
END CATCH

SELECT *
FROM Assembly_Account
SELECT *
FROM Department_Account
SELECT *
FROM Process_Account

--------------- QUERY 9 ---------------
-- Retrieve the cost incurred on an assembly-id

DELETE FROM Assembly 
DELETE FROM Assembly_Account
DELETE FROM Account

INSERT INTO Assembly(assembly_ID, date_ordered, assembly_details)
VALUES (
    1, '01:01:01', 'Details'
)

INSERT INTO Account(account_ID, date_established)
VALUES (1, '2019-01-01')

INSERT INTO Assembly_Account(account_ID, details_1)
VALUES (1, '100')

--Update Assembly with assembly account
UPDATE Assembly
SET account_ID = (SELECT account_ID FROM Assembly_Account WHERE account_ID = 1)

BEGIN TRY
    --Retreive cost from assembly_ID
    SELECT details_1
    FROM Assembly AS A, Assembly_Account as AA
    WHERE A.account_ID = AA.account_ID AND A.assembly_ID = 1

END TRY
BEGIN CATCH
	EXEC error_handling;
END CATCH

SELECT * 
FROM Assembly
*/

--------------- QUERY 10 ---------------
--Retrieve the total labor time within a department for jobs completed in the department
-- during a given date
DELETE FROM Cut_Job
DELETE FROM Fit_Job
DELETE FROM Paint_Job
DELETE FROM Job
DELETE FROM Manufacture
DELETE FROM Process
DELETE FROM Department
DELETE FROM Assembly
DELETE FROM Customer

-- QUERY 1 --
INSERT INTO Customer
    (customer_name, customer_address, customer_category)
VALUES
    ('Zaki Refai', '1063 Greystone Crest', '1'),
    ('Sammy Najib', '750 W Imhoff Rd', '2'),
    ('Trevor Bryant', '750 W Imhoff Rd', '1');

-- QUERY 2 --
INSERT INTO Department
    (dept_ID, dept_data)
VALUES
    (101, 'CS'),
    (102, 'MIS'),
    (103, 'ENGR')

-- QUERY 3 --
INSERT INTO Assembly
    (assembly_ID, date_ordered, assembly_details, customer_name)
VALUES
    (1001, '2019-11-01', 'Chair', 'Zaki Refai'),
    (1002, '2019-11-03', 'Desk', 'Sammy Najib'),
    (1003, '2019-11-10', 'Table', 'Trevor Bryant');

-- QUERY 4 --
INSERT INTO Process
    (proc_ID, process_data, dept_ID)
VALUES
    (1001, 'Mill', '101'),
    (1002, 'Mill', '101'),
    (1003, 'Saw', '103')

INSERT INTO Manufacture
    (proc_ID, assembly_ID)
VALUES
    (1001, 1001),
    (1002, 1002),
    (1003, 1003)

-- QUERY 6 --
INSERT INTO Job
    (job_ID, date_commenced, assembly_ID, proc_ID)
VALUES
    (10, '2019-11-01', 1001, 1001),
    (11, '2019-11-02', 1001, 1001),
    (12, '2019-11-10', 1002, 1002),
    (13, '2019-11-12', 1003, 1003)

-- QUERY 7 --
UPDATE Job
SET  date_completed = '2019-11-02'
WHERE job_ID = 10
INSERT INTO Cut_Job
    (job_ID, machine_type, machine_time, material, labor_time, assembly_ID, proc_ID)
VALUES
    (10, 'Mill', 4.5, 'Metal', 5.0, 1001, 1001)
UPDATE Job
SET  date_completed = '2019-11-02'
WHERE job_ID = 11
INSERT INTO Paint_Job
    (job_ID, color, volume, labor_time, assembly_ID, proc_ID)
VALUES
    (11, 'Purple', 300.05, 2.2, 1001, 1001)
UPDATE Job
SET  date_completed = '2019-11-02'
WHERE job_ID = 12
INSERT INTO Fit_Job
    (job_ID, labor_time, assembly_ID, proc_ID)
VALUES
    (12, 3.5, 1002, 1002)
UPDATE Job
SET  date_completed = '2019-12-01'
WHERE job_ID = 13
INSERT INTO Fit_Job
    (job_ID, labor_time, assembly_ID, proc_ID)
VALUES
    (13, 3.4, 1003, 1003)
/*
BEGIN TRY
    SELECT SUM(ISNULL(C.labor_time, 0)) + SUM(ISNULL(PJ.labor_time, 0)) + SUM(ISNULL(F.labor_time, 0)) AS total_labor_time
    FROM Job J LEFT OUTER JOIN Cut_Job C 
        ON J.job_ID = C.job_ID LEFT OUTER JOIN Paint_Job PJ 
        ON J.job_ID = PJ.job_ID LEFT OUTER JOIN Fit_Job F 
        ON J.job_ID = F.job_ID JOIN Process P 
        ON J.proc_ID = P.proc_ID JOIN Department D 
        ON P.dept_ID = D.dept_ID
    WHERE J.date_completed = '2019-11-02' AND D.dept_ID = 101
END TRY
BEGIN CATCH
	EXEC error_handling;
END CATCH

--------------- QUERY 11 ---------------
--Retrieve the processes through which a given assembly-id has passed so far (in date-commenced order)
-- and the department responsible for each process

BEGIN TRY
    SELECT J.proc_ID, J.date_commenced, D.dept_ID
    FROM Job J JOIN Process P ON J.proc_id = P.proc_ID JOIN Department D ON P.dept_ID = D.dept_ID
    WHERE J.assembly_ID = 1001
    ORDER BY J.date_commenced
END TRY
BEGIN CATCH
	EXEC error_handling;
END CATCH

--------------- QUERY 12 ---------------
--Retrieve the jobs (together with their type information and assembly-id) completed 
-- during a given date in a given department
BEGIN TRY

SELECT machine_type, machine_time, material, labor_time, CJ.assembly_ID
FROM Cut_Job AS CJ JOIN Job AS J ON CJ.job_ID = J.job_ID 
    JOIN Process P ON J.proc_id = P.proc_ID 
    JOIN Department D ON P.dept_ID = D.dept_ID 
WHERE D.dept_ID = 101 AND J.date_completed = '2019-11-02'
        
SELECT color, volume, labor_time, PJ.assembly_ID
FROM Paint_Job AS PJ JOIN Job AS J ON PJ.job_ID = J.job_ID 
    JOIN Process P ON J.proc_id = P.proc_ID 
    JOIN Department D ON P.dept_ID = D.dept_ID
WHERE D.dept_ID = 101 AND J.date_completed = '2019-11-02'

SELECT labor_time, FJ.assembly_ID
FROM Fit_Job AS FJ JOIN Job AS J ON FJ.job_ID = J.job_ID 
    JOIN Process P ON J.proc_id = P.proc_ID 
    JOIN Department D ON P.dept_ID = D.dept_ID
WHERE D.dept_ID = 101 AND J.date_completed = '2019-11-02'

END TRY
BEGIN CATCH
	EXEC error_handling;
END CATCH

--------------- QUERY 13 ---------------
--Retrieve the customers (in name order) whose category is in a given range
BEGIN TRY

SELECT customer_name
FROM Customer
WHERE customer_category BETWEEN 1 AND 4
ORDER BY (customer_name)

END TRY
BEGIN CATCH
    EXEC error_handling;
END CATCH 

--------------- QUERY 14 ---------------
--Delete all Cut_Jobs whose job_no is in a given range

BEGIN TRY

DELETE FROM Cut_Job
WHERE Cut_Job.job_ID BETWEEN 10 AND 11

END TRY
BEGIN CATCH
    EXEC error_handling;
END CATCH 

SELECT *
FROM Cut_Job

INSERT INTO Cut_Job(job_ID, machine_type, machine_time, material, labor_time, assembly_ID, proc_ID)
VALUES (10, 'Mill', 4.5, 'Metal', 5.0, 1001, 1001)

SELECT * 
FROM Cut_Job

--------------- QUERY 15 ---------------
--Update the color of a given paint job

BEGIN TRY

UPDATE Paint_Job
SET color = 'Red'
WHERE Paint_Job.job_ID = 11

END TRY
BEGIN CATCH
    EXEC error_handling
END CATCH

SELECT *
FROM Paint_Job
*/