-- Create DATABASE
CREATE DATABASE mediscreen_prod CHARACTER SET 'utf8';
-- Create user
CREATE USER 'mediscreen'@'localhost' IDENTIFIED BY 'ocr-p9$+';
--Privilege
GRANT ALL PRIVILEGES ON mediscreen_prod.* TO 'mediscreen'@'localhost';