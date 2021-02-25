package com.bamboo.employee.entitiesDB;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Employees_favourite_month ")
public class EmployeesFavouriteMonth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    @Column(name = "favourite_month")
    private int favouriteMonth;

    public EmployeesFavouriteMonth(Employee employee, int favouriteMonth) {
        this.employee = employee;
        this.favouriteMonth = favouriteMonth;
    }
}
