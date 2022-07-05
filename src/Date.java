import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class Date{
    private int jour;
    private int mois;
    private int annee;
    private String msg="";

    static public LocalDate dateActuelle = LocalDate.now();
    

    Date(int jour, int mois, int annee){
            this.setJour(jour);
            this.setMois(mois);
            this.setAnnee(annee);
            this.msg="";
    }
    public String getMsg() {
        return this.msg;
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
            this.msg += jour + " est un jour invalide\n";
        } 
    }
    
    public void setMois(int mois) {
        if (1<= mois && mois <= 12) {
            this.mois = mois;
        }else{
            this.msg += mois + " est un mois invalide\n";
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
            this.msg +="Cette date est dépassée\n";
        }
        if(this.annee == annee ){    
            if(this.mois < mois){
                this.msg +="Cette date est dépassée\n";
            }
            if(this.mois == mois){
                if(this.jour <= jour){
                    this.msg +="Cette date est dépassée\n";
                }
            }
        }
        if((this.mois ==2 && this.jour==29) && 
        ((this.annee%4 != 0 || this.annee%100 == 0) && (this.annee % 400 != 0))){
            this.msg += "le mois de fervier a 28 jours seulement\n";
        }
        if(this.mois == 2 && this.jour >29){
            this.msg += "le mois de fervier a 28 jours seulement\n";
        }

    }

    public String toString() {
        
        if(this.msg.equals("")){
            return jour+"/"+ mois+ "/"+ annee;
        }else{
            return getMsg();

        }
    }

}