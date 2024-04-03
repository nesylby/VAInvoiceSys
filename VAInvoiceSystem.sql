CREATE DATABASE IF NOT EXISTS VAInvoiceSystem;
USE VAInvoiceSystem;

CREATE TABLE IF NOT EXISTS clients (
  client_id INT NOT NULL AUTO_INCREMENT,
  full_name VARCHAR(255) NOT NULL,
  email_address VARCHAR(255) NOT NULL,
  phone_number VARCHAR(20),
  billing_address VARCHAR(255),
  payment_terms VARCHAR(50),
  service_type VARCHAR(50),
  service_frequency VARCHAR(50),
  PRIMARY KEY (client_id)
);

CREATE TABLE IF NOT EXISTS services (
  service_id INT NOT NULL AUTO_INCREMENT,
  service_name VARCHAR(255) NOT NULL,
  hourly_rate DECIMAL(10,2) NOT NULL,
  total_hours_billed DECIMAL(10,2) DEFAULT 0.00,
  PRIMARY KEY (service_id)
);

CREATE TABLE IF NOT EXISTS invoices (
  invoice_id INT NOT NULL AUTO_INCREMENT,
  client_name VARCHAR(255) NOT NULL,
  invoice_number INT NOT NULL,
  invoice_date DATE NOT NULL,
  due_date DATE NOT NULL,
  service_name VARCHAR(255) NOT NULL,
  number_of_hours DECIMAL(5,2) NOT NULL,
  total_amount DECIMAL(10,2) NOT NULL,
  is_paid BOOLEAN NOT NULL DEFAULT FALSE,
  PRIMARY KEY (invoice_id)
);

SHOW TABLES;

SELECT * FROM clients;

SELECT * FROM invoice_items;

SELECT * FROM invoices;

SELECT * FROM services;


