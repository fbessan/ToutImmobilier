package besco.corporation.toutimmobilier.Models;

import com.orm.SugarRecord;

public class Annonces extends SugarRecord {

    private int annonces_id;
    private String description;
    private String address;
    private String amount;
    private String nbrPieces;
    private String nbrChambres;
    private String superficie;
    private String reference;
    private String typeBien;
    private String typeOffre;
    private String commodites;
    private String pays;
    private String photoHome;


    public int getAnnonces_id() {
        return annonces_id;
    }

    public void setAnnonces_id(int annonces_id) {
        this.annonces_id = annonces_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getNbrPieces() {
        return nbrPieces;
    }

    public void setNbrPieces(String nbrPieces) {
        this.nbrPieces = nbrPieces;
    }

    public String getNbrChambres() {
        return nbrChambres;
    }

    public void setNbrChambres(String nbrChambres) {
        this.nbrChambres = nbrChambres;
    }

    public String getSuperficie() {
        return superficie;
    }

    public void setSuperficie(String superficie) {
        this.superficie = superficie;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getTypeBien() {
        return typeBien;
    }

    public void setTypeBien(String typeBien) {
        this.typeBien = typeBien;
    }

    public String getTypeOffre() {
        return typeOffre;
    }

    public void setTypeOffre(String typeOffre) {
        this.typeOffre = typeOffre;
    }

    public String getCommodites() {
        return commodites;
    }

    public void setCommodites(String commodites) {
        this.commodites = commodites;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getPhotoHome() {
        return photoHome;
    }

    public void setPhotoHome(String photoHome) {
        this.photoHome = photoHome;
    }

    @Override
    public String toString() {
        return description;
    }

}
