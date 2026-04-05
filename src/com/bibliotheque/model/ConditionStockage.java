package com.bibliotheque.model;

public class ConditionStockage {

    private int id;
    private double temperature;
    private double humidite;
    private String dateReleve;
    private boolean alerte;
    private int annexeId;

    public ConditionStockage(double temperature, double humidite,
            String dateReleve, int annexeId) {
        this.temperature = temperature;
        this.humidite = humidite;
        this.dateReleve = dateReleve;
        this.annexeId = annexeId;
        this.alerte = false;
    }

    public int getId() {
        return id;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidite() {
        return humidite;
    }

    public String getDateReleve() {
        return dateReleve;
    }

    public boolean isAlerte() {
        return alerte;
    }

    public int getAnnexeId() {
        return annexeId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTemperature(double t) {
        this.temperature = t;
    }

    public void setHumidite(double h) {
        this.humidite = h;
    }

    public void setDateReleve(String d) {
        this.dateReleve = d;
    }

    public void setAlerte(boolean a) {
        this.alerte = a;
    }

    public void setAnnexeId(int annexeId) {
        this.annexeId = annexeId;
    }

    // verifie si les conditions sont dans les normes
    // Temperature : 15 a 20 degres | Humidite : 40% a 60%
    public boolean verifierConditions() {
        return (temperature >= 15 && temperature <= 20)
                && (humidite >= 40 && humidite <= 60);
    }

    public void declencherAlerte() {
        this.alerte = true;
        System.out.println("  ⚠  ALERTE : Conditions anormales detectees !");
        System.out.println("     Temperature : " + temperature + "°C (norme: 15-20°C)");
        System.out.println("     Humidite    : " + humidite + "% (norme: 40-60%)");
    }

    public String toString() {
        return "[Condition #" + id + "] Temp: " + temperature + "°C"
                + " | Humidite: " + humidite + "%"
                + " | Date: " + dateReleve
                + " | Alerte: " + (alerte ? "OUI" : "NON");
    }
}
