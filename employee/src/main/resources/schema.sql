
CREATE TABLE IF NOT EXISTS employee (
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    name                VARCHAR(250) NOT NULL,
    surname             VARCHAR(250) NOT NULL,
    creation_timestamp  TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS vacation (
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    employee_id         INT NOT NULL,
    from_date           DATE NOT NULL,
    to_date             DATE NOT NULL,
    status              VARCHAR(50) NOT NULL,

    FOREIGN KEY (employee_id) REFERENCES employee(id)
);

CREATE TABLE IF NOT EXISTS favorite_vacation (
    employee_id         INT PRIMARY KEY,
    month_number        INT NOT NULL,
    creation_timestamp  TIMESTAMP NOT NULL,

    FOREIGN KEY (employee_id) REFERENCES employee(id) ON DELETE CASCADE,
    CONSTRAINT valid_month_number
        CHECK (month_number >= 1 AND month_number <= 12)
);

CREATE SEQUENCE VACATION_SEQUENCE START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE EMPLOYEE_SEQUENCE START WITH 1 INCREMENT BY 1;

