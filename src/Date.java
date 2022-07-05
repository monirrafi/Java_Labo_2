import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class Date{
    private int jour;
    private int mois;
    private int annee;

    static public LocalDate dateActuelle = LocalDate.now();
    private static String msg="";

    Date(int jour, int mois, int annee){
            this.setJour(jour);
            this.setMois(mois);
            this.setAnnee(annee);
    }
    public String getMsg() {
        return msg;
    }
    public int getJour() {
        return jour;
    }
    public int getAnnee() {
        return annee;
    }
    public int getMois() {
        return mois;
    }
    public void setAnnee(int annee) {
        this.annee = annee;
    }
    public void setJour(int jour) {
        if (1<= jour && jour <= 31) {
                this.jour = jour;
        }else{
            msg = jour + " est un jour invalide";
        } 
    }
    
    public void setMois(int mois) {
        if (1<= mois && mois <= 12) {
            this.mois = mois;
        }else{
            msg = mois + " est un mois invalide";
        } 
    }
    public void compareNow(){
        String now;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        now =sdf.format(cal.getTime());
        int jour = Integer.parseInt(now.substring(8));
        int mois = Integer.parseInt(now.substring(5,7));
        int annee = Integer.parseInt(now.substring(0,4));
        //String  resultat = "apres";
        if (this.annee < annee ) {
            msg="Cette date est dépassée";
        }
        if(this.annee == annee ){    
            if(this.mois < mois){
                msg="Cette date est dépassée";
            }
            if(this.mois == mois){
                if(this.jour <= jour){
                    msg="Cette date est dépassée";
                }
            }
        }
        if((this.mois ==2 && this.jour==29) && 
        ((this.annee%4 != 0 || this.annee%100 == 0) && (this.annee % 400 != 0))){
            msg = "le mois de fervier a 28 jours seulement";
        }
        if(this.mois == 2 && this.jour >29){
            msg = "le mois de fervier a 28 jours seulement";
        }

    }

    public String toString() {
        if(msg.equals("")){
            return jour+"/"+ mois+ "/"+ annee;
        }else{
            return msg;

        }
    }

}