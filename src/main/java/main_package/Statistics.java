package main_package;

public class Statistics {

    private int sus;
    private int infected;
    private int recovered;
    private int vaccinated;
    private int days = 0;
    private double avgInfected = 0;
    private double avgRecovered= 0;
    private double avgVaccinated = 0;

    public Statistics(){}

    public void infectOne(){
        sus -= 1;
        infected += 1;
    }

    public void recoverOne(){
        infected -= 1;
        recovered += 1;
    }

    public void nextDay(){
        days += 1;
    }

    // nwm po co to bo niby powinno być ale chyba nie potrzebujemy
//
    public void setS(int s){ sus = s; }
    public void setI(int i){ infected = i; }
    public void setR(int r){ recovered = r; }
    public void setV(int v){ vaccinated = v; }
    public void setDays(int d){ days = d; }
//
    public int getS(){ return sus; }
    public int getI(){ return infected; }
    public int getR(){ return recovered; }
    public int getV(){ return vaccinated; }

    public int getDay(){ return days; }

    public double getAvgInfected() {
        return avgInfected;
    }


}
