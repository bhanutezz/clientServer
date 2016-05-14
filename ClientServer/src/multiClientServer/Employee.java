package multiClientServer;

import java.io.Serializable;

public class Employee implements Serializable {

    private int noMonths;
    private int noDays;
    private double payRate;
    private double hours;

    Employee( int mon, int days, double pay, double hour ) 
    {
        noMonths = mon;
        noDays = days;
        payRate = pay;
        hours = hour;
    }

    public Employee() {
        // TODO Auto-generated constructor stub
    }

    public int getNoMonths() {
        return noMonths;
    }

    public void setNoMonths(int noMonths) {
        this.noMonths = noMonths;
    }

    public int getNoDays() {
        return noDays;
    }

    public void setNoDays(int noDays) {
        this.noDays = noDays;
    }

    public double getPayRate() {
        return payRate;
    }

    public void setPayRate(double payRate) {
        this.payRate = payRate;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }
}