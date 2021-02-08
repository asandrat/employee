
CREATE TABLE IF NOT EXISTS employee (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(250) NOT NULL,
    surname VARCHAR(250) NOT NULL
);

CREATE TABLE IF NOT EXISTS vacation (
    id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id INT NOT NULL,
    from_date DATE NOT NULL,
    to_date DATE NOT NULL,
    status VARCHAR(50) NOT NULL,
    FOREIGN KEY (employee_id) REFERENCES employee(id) ON DELETE CASCADE
);

-- pocinje sa 3 jer imam dva hardcoded entry-ja iz data.sql-a
-- todo srediti ovo
CREATE SEQUENCE HIBERNATE_SEQUENCE START WITH 3 INCREMENT BY 1;

