package com.bamboo.employee.entitiesDB;

import javax.persistence.*;

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

    public EmployeesFavouriteMonth() {
    }

    public EmployeesFavouriteMonth(Employee employee, int favouriteMonth) {
        this.employee = employee;
        this.favouriteMonth = favouriteMonth;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getFavouriteMonth() {
        return favouriteMonth;
    }

    public void setFavouriteMonth(int favouriteMonth) {
        this.favouriteMonth = favouriteMonth;
    }
}
