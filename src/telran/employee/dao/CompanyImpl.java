package telran.employee.dao;

import telran.employee.model.Employee;
import telran.employee.model.SalesManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class CompanyImpl implements Company {
    private Set<Employee> employees;
    private int capacity;

    public CompanyImpl(int capacity) {
        this.capacity = capacity;
        employees = new HashSet<>();
    }

    // O(1)
    @Override
    public boolean addEmployee(Employee employee) {
        if (employee == null || capacity == employees.size()) {
            return false;
        }
        return employees.add(employee);
    }

    // O(n)
    @Override
    public Employee removeEmployee(int id) {
        Employee victim = findEmployee(id);
        employees.remove(victim);
        return victim;
    }

    // O(n)
    @Override
    public Employee findEmployee(int id) {
        return employees.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // O(1)
    @Override
    public int quantity() {
        return employees.size();
    }

    // O(n)
    @Override
    public double totalSalary() {
        return employees.stream()
                .mapToDouble(Employee::calcSalary)
                .sum();
    }

    // O(n)
    @Override
    public double totalSales() {
        return employees.stream()
                .filter(e -> e instanceof SalesManager)
                .map(e -> (SalesManager) e)
                .mapToDouble(SalesManager::getSalesValue)
                .sum();
    }

    // O(n)
    @Override
    public void printEmployees() {
        System.out.println("=== " + COUNTRY + " ===");
        employees.forEach(System.out::println);
        System.out.println("=======================");
    }

    // O(n)
    @Override
    public Employee[] findEmployeesHoursGreaterThan(int hours) {
        return findEmployeesByPredicate(e -> e.getHours() > hours);
    }

    // O(n)
    @Override
    public Employee[] findEmployeesSalaryBetween(int minSalary, int maxSalary) {
        return findEmployeesByPredicate(e -> e.calcSalary() >= minSalary && e.calcSalary() < maxSalary);
    }

    private Employee[] findEmployeesByPredicate(Predicate<Employee> predicate) {
        return employees.stream()
                .filter(predicate)
                .toArray(Employee[]::new);
    }
}
