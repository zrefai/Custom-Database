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
    paint_type VARCHAR(64) NOT NULL,
    paint_method VARCHAR(64) NOT NULL
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
