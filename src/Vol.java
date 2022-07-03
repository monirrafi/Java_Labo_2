public class Vol {
    private String numeroVol;
    private String destination;
    private Date dateDepart;
    private int nbTotalReservation;
    public static int nombreVols=0;

    Vol(String numeroVol,String destination,Date dateDepart,int nbTotalReservation){
        this.setDateDepart(dateDepart);
        this.destination=destination;
        this.numeroVol=numeroVol;
        this.setNbTotalReservation(nbTotalReservation);
        ++nombreVols;

    }

    public void setDateDepart(Date dateDepart) {
        this.dateDepart = dateDepart;
    }
    public void setNbTotalReservation(int nbTotalReservation) {
            this.nbTotalReservation = nbTotalReservation;
    }
    public String getNumeroVol() {
        return numeroVol;
    }
    public Date getDateDepart() {
        return dateDepart;
    }
    public String getDestination() {
        return destination;
    }
    public int getNbTotalReservation() {
        return nbTotalReservation;
    }
    public static String paquetterString(String mot, int longueur){
        int lng = mot.length();
        for(int i=0;i<longueur-lng;i++){
                mot = mot + " ";
        }
        return mot;
    }

    public String toString() {
            return this.numeroVol + "\t" + paquetterString(this.destination,30) + "\t" + 
            this.dateDepart.getJour() + " " + 
            this.dateDepart.getMois() + " " + this.dateDepart.getAnnee() + "\t" + nbTotalReservation  + "\n" ;
    }
}
