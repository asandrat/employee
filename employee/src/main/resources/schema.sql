DROP SCHEMA IF EXISTS `employees-schema`;

CREATE SCHEMA `employees-schema`;

use `employees-schema`;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS employee;

CREATE TABLE employee (
                            id int(11) NOT NULL AUTO_INCREMENT,
                            name varchar(128) DEFAULT NULL,
                            surname varchar(128) DEFAULT NULL,

                            PRIMARY KEY (id)

);


DROP TABLE IF EXISTS vacation;

CREATE TABLE vacation (
                            id int(11) NOT NULL AUTO_INCREMENT,
                            date_from date DEFAULT NULL,
                            date_to date DEFAULT NULL,
                            duration bigint DEFAULT NULL,
                            vacation_status varchar(128) DEFAULT NULL,
                            employee_id int(11) DEFAULT NULL,


                            PRIMARY KEY (id),

                            KEY FK_EMPLOYEE_ID_idx (employee_id),

                            CONSTRAINT FK_EMPLOYEE
                                FOREIGN KEY (employee_id)
                                    REFERENCES employee (id)

                                    ON DELETE NO ACTION ON UPDATE NO ACTION
);


SET FOREIGN_KEY_CHECKS = 1;
