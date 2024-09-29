package model;

import java.sql.Date;

public class Product {

    // Fields
    private int id;
    private String nimi;
    private Date julkaisuPVM;
    private String tekija;
    private String julkaisija;
    private int ikaraja;
    private String tyyppi;
    private String kuvaus;
    private String genre;
    private int saldo;
    private int lainaaika;
    private String koodi;

    // No-argument constructor
    public Product() {
    }

    // Constructor with parameters
    public Product(String nimi, String julkaisuPVM, String tekija, String julkaisija, int ikaraja, String tyyppi, String kuvaus, String genre, int saldo, String koodi) {
        this.nimi = nimi;
        this.julkaisuPVM = Date.valueOf(julkaisuPVM); // Assuming julkaisuPVM is in the format "yyyy-MM-dd"
        this.tekija = tekija;
        this.julkaisija = julkaisija;
        this.ikaraja = ikaraja;
        this.tyyppi = tyyppi;
        this.kuvaus = kuvaus;
        this.genre = genre;
        this.saldo = saldo;
        this.koodi = koodi;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public Date getJulkaisuPVM() {
        return julkaisuPVM;
    }

    public void setJulkaisuPVM(Date julkaisuPVM) {
        this.julkaisuPVM = julkaisuPVM;
    }

    public String getTekija() {
        return tekija;
    }

    public void setTekija(String tekija) {
        this.tekija = tekija;
    }

    public String getJulkaisija() {
        return julkaisija;
    }

    public void setJulkaisija(String julkaisija) {
        this.julkaisija = julkaisija;
    }

    public int getIkaraja() {
        return ikaraja;
    }

    public void setIkaraja(int ikaraja) {
        this.ikaraja = ikaraja;
    }

    public String getTyyppi() {
        return tyyppi;
    }

    public void setTyyppi(String tyyppi) {
        this.tyyppi = tyyppi;
    }

    public String getKuvaus() {
        return kuvaus;
    }

    public void setKuvaus(String kuvaus) {
        this.kuvaus = kuvaus;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public int getLainaaika() {
        return lainaaika;
    }

    public void setLainaaika(int lainaaika) {
        this.lainaaika = lainaaika;
    }

    public String getKoodi() {
        return koodi;
    }

    public void setKoodi(String koodi) {
        this.koodi = koodi;
    }
}