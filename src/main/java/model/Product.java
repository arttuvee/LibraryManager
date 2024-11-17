package model;

import java.sql.Date;

public class Product {

    // Attributes
    private int id;
    private int year;
    private int ageLimit;
    private String type;
    private String genre;
    private int saldo;
    private int borrowTime;
    private String code;
    private String author;
    private String producer;

    // Localized attributes | Defaulted to empty strings
    private String name_en = "";
    private String description_en = "";
    private String name_fi = "";
    private String description_fi = "";
    private String name_ja = "";
    private String description_ja = "";
    private String name_uk = "";
    private String description_uk = "";


    // No-argument constructor
    public Product() {
    }

    // Constructor with parameters from the untranslated table
    public Product(int julkaisuvuosi, int ikaraja, int saldo, int lainaaika, String koodi, String tyyppi, String genre, String author, String producer) {
        this.id = id;
        this.year = julkaisuvuosi;
        this.ageLimit = ikaraja;
        this.saldo = saldo;
        this.borrowTime = lainaaika;
        this.code = koodi;
        this.type = tyyppi;
        this.genre = genre;
        this.author = author;
        this.producer = producer;
    }

    // Constructors with parameters from the translated table
    public void setEnglishData(String name_en, String description_en) {
        this.name_en = name_en;
        this.description_en = description_en;
    }

    public void setFinnishData(String name_fi, String description_fi) {
        this.name_fi = name_fi;
        this.description_fi = description_fi;
    }

    public void setJapaneseData(String name_ja, String description_ja) {
        this.name_ja = name_ja;
        this.description_ja = description_ja;
    }

    public void setUkrainianData(String name_uk, String description_uk) {
        this.name_uk = name_uk;
        this.description_uk = description_uk;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName(String language) {
        return switch (language) {
            case "en" -> name_en;
            case "fi" -> name_fi;
            case "ja" -> name_ja;
            case "uk" -> name_uk;
            default -> null;
        };
    }

    public void setName(String language, String nimi) {
        switch (language) {
            case "en" -> name_en = nimi;
            case "fi" -> name_fi = nimi;
            case "ja" -> name_ja = nimi;
            case "uk" -> name_uk = nimi;
        }
    }

    public String getDescription(String language) {
        return switch (language) {
            case "en" -> description_en;
            case "fi" -> description_fi;
            case "ja" -> description_ja;
            case "uk" -> description_uk;
            default -> null;
        };
    }

    public void setDescription(String language, String description) {
        switch (language) {
            case "en" -> description_en = description;
            case "fi" -> description_fi = description;
            case "ja" -> description_ja = description;
            case "uk" -> description_uk = description;
        }
    }

    public int getJulkaisuvuosi() {
        return year;
    }

    public void setJulkaisuvuosi(int year) {
        this.year = year;
    }

    public String getTekija() {
        return author;
    }

    public void setTekija(String tekija) {
        this.author = tekija;
    }

    public String getJulkaisija() {
        return producer;
    }

    public void setJulkaisija(String producer) {
        this.producer = producer;
    }

    public int getIkaraja() {
        return ageLimit;
    }

    public void setIkaraja(int ageLimit) {
        this.ageLimit = ageLimit;
    }

    public String getTyyppi() {
        return type;
    }

    public void setTyyppi(String tyyppi) {
        this.type = tyyppi;
    }

    public String getKuvaus(String language) {
        return switch (language) {
            case "en" -> description_en;
            case "fi" -> description_fi;
            case "ja" -> description_ja;
            case "uk" -> description_uk;
            default -> null;
        };
    }

    public void setKuvaus(String language, String kuvaus) {
        switch (language) {
            case "en" -> description_en = kuvaus;
            case "fi" -> description_fi = kuvaus;
            case "ja" -> description_ja = kuvaus;
            case "uk" -> description_uk = kuvaus;
        }
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
        return borrowTime;
    }

    public void setLainaaika(int lainaaika) {
        this.borrowTime = lainaaika;
    }

    public String getKoodi() {
        return code;
    }

    public void setKoodi(String koodi) {
        this.code = koodi;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }
}