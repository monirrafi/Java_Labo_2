import java.awt.Font;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class GestionVols {
    // Declaration globale
    final static int MAX_PLACES=340;
    final static String CIE_AIR_RELAX  = "src/donnees/Cie_Air_Relax.txt";
    static BufferedReader fich;
    static BufferedWriter ecrire;
    static JTextArea sortie;
    static List<Vol> listVols;

    // Methode retourne index de vol dans la liste s'il existe
    // sinon retourne -1 
    public static int trouverIndex(String numeroVol) {
        int pos =-1;
        for(Vol vol:listVols){
            if(vol.getNumeroVol().equals(numeroVol)){
                pos=listVols.indexOf(vol);
            } 
        }
        return pos;
        
    }

    public static boolean rechererVol(String numeroVol){
        if(trouverIndex(numeroVol) !=-1){
            return true;
        }else{
            return false;
        }
    }
    
    // Deux methode d'affichage
    public static void afficherEntete(){
        sortie = new JTextArea();
        sortie.setColumns(70);
        sortie.setLineWrap(true);
        sortie.setWrapStyleWord(true);
    
        sortie.setFont(new Font("courier new", Font.PLAIN, 12));
        sortie.append("\t\tLa liste des "+ Vol.nombreVols+ " vols\n\n");
        sortie.append("Numéro\t" +Vol.paquetterString("destination",30) +"\tdate de départ \tRéservation\n");
    }    
    public static void afficher(){
        afficherEntete();
        for(Vol vol:listVols){
            sortie.append(vol.toString());
        }
        sortie.setSize(sortie.getPreferredSize().width, 1);

        JOptionPane.showConfirmDialog(null, sortie, "Cie Air Relax ", 
        JOptionPane.PLAIN_MESSAGE);
    }

    // Methode lire les donnees du fichier et les 
    // transmettre dans une liste
    public static void chargerVols() throws Exception{
        try{
            String ligne, elets[];
            listVols = new ArrayList<Vol>();
            fich = new BufferedReader(new FileReader(CIE_AIR_RELAX));
            ligne = fich.readLine().trim();
            while(ligne != null){
                elets = ligne.split("\\s+|/");
                Date date = new Date(Integer.parseInt(elets[2]), Integer.parseInt(elets[3]), 
                                Integer.parseInt(elets[4]));
                Vol vol = new Vol(elets[0], elets[1], date,Integer.parseInt(elets[5]) );
                listVols.add(vol);
                ligne = fich.readLine();
            }
    
		} catch (FileNotFoundException e) {
			System.out.println("Fichier introuvable. Vérifiez le chemin et le nom du fichier.");
		}
		catch (IOException e) {
			System.out.println("Un probléme est arrivé lors de la manipulation du fichier. Vérifiez vos données.");
		}catch (Exception e) { 
			System.out.println("Un probléme est arrivé lors du chargement du fichier. Contactez l'administrateur.");
		}finally {//Exécuté si erreur ou pas
        fich.close();
        }
    }

    // Methode pour retirer un vol
    public static void retirerVol(){
        char reponse='O';
        String strReponce="";
        String msg="";
        do{
            String volChercher = JOptionPane.showInputDialog(null,
             "Entrez le numéro du vol a retirer");
            int posTrouve = trouverIndex(volChercher);
            if(!rechererVol(volChercher)){
                msg= "le vol numéro "+ volChercher + " n'existe pas";
            }else{
                String confirmation = JOptionPane.showInputDialog(null,
                "le vol numéro "+ volChercher + " destination "+ listVols.get(posTrouve).getDestination()
                + "\n date de depart le "+ listVols.get(posTrouve).getDateDepart() +
                "\n avec nombre de resevation " +listVols.get(posTrouve).getNbTotalReservation()
                +"\n Désirez-vous vraiment retirer ce vol (O/N)?");
                char confirmer = confirmation.toUpperCase().charAt(0);
                if(confirmer == 'O'){
                    --Vol.nombreVols;
                    listVols.remove(posTrouve);
                    msg ="le vol numéro  \n " + volChercher +" \n  a été retiré";
                }else{
                    msg ="";
                }
            }
            if(msg != ""){
            JOptionPane.showMessageDialog(null,msg,
            "Supression du vol", JOptionPane.PLAIN_MESSAGE); 
            }
            strReponce =JOptionPane.showInputDialog(null,
            "voulez-vos entrez un autre numéro du vol  à retirer (O/N)? :");
            if(strReponce == null){
                reponse = 'N';
            }else{
            reponse = Character.toUpperCase(strReponce.charAt(0));
            }
    }while(reponse=='O');    
    }

    // Methode pour ajouter un vol
    public static void insererVol(){
        char reponse='O';
        String strReponce="";
        do{
            String msg="";
            Date date;
            int repDate =0;
            String strDate;
            String volChercher = JOptionPane.showInputDialog(null, 
            "Entrez le numéro du vol à ajouter :");
            //int posTrouve = trouverIndex(volChercher);
            if(rechererVol(volChercher)){
                msg= "le vol numéro "+ volChercher + " existe déjà";
            }else{
                // boucle si la date n'est pas correcte
                do{
                    strDate = JOptionPane.showInputDialog(null, 
                    "Entrez la date sous forme JJ/MM/AAAA Ex:29/10/2023");
                    if(strDate == null){
                        repDate=1;
                        msg="";
                    }else{
                        String strMg = verifierChaineDate(strDate);// verifier la forme de chaine de caractere de la date entree
                        if(strMg != ""){
                            msg = strMg;
                            JOptionPane.showMessageDialog(null,msg,"CORRECTION DE LA DATE", JOptionPane.PLAIN_MESSAGE); 
                        }else{
                            repDate=1;
                        }
                    }
                }while(repDate==0);
                    if(strDate != null){
                
                    String eleDate[] = strDate.split("/");
                    date = new Date(Integer.parseInt(eleDate[0]), Integer.parseInt(eleDate[1]), 
                                        Integer.parseInt(eleDate[2]));
                    String destination = JOptionPane.showInputDialog(null, 
                    "Entrez la destination du vol ");
                    Vol vol = new Vol(volChercher, destination, date,0);
                    listVols.add(vol);
                    msg ="le vol \nnuméro " + vol .getNumeroVol() +"\n destination "+ vol.getDestination() 
                    +"\n date de départ "+date+ "\n a été enregistré";
                        //repDate=1;
                        //titre="Ajout des vols";
                    }
            }
            if(msg !=""){        
            
                JOptionPane.showMessageDialog(null,msg,
                "Ajout des vols", JOptionPane.PLAIN_MESSAGE); 
            }
            strReponce =JOptionPane.showInputDialog(null,
            "voulez-vos entrez un autre numéro du vol  à ajouter (O/N)? :");
            if(strReponce == null){
                reponse = 'N';
            }else{
            reponse = Character.toUpperCase(strReponce.charAt(0));
            }
        }while(reponse=='O');    
    }

    // Methode pour mettre à jour le fichier texte
    public static void ecrireFichier()throws IOException {
        ecrire = new BufferedWriter(new FileWriter(CIE_AIR_RELAX));
    
        for(Vol vol:listVols){
            ecrire.write(vol.toString());
        }
        ecrire.close();
        
    }

    // Méthode pour modifier la date de départ du vol
    public static void modifierDate(){
        char reponse='O';
        String msg="";
        Date date;
        int repDate =0;
        String strDate;
        String strReponce="";
        do{
            String volChercher = JOptionPane.showInputDialog(null, 
            "Entrez le numéro du vol dont on veut modifier la date de depart :");
            int posTrouve = trouverIndex(volChercher);
            if(!rechererVol(volChercher)){
                msg= "le vol numéro "+ volChercher + " n'existe pas";
            }else{
                // boucle si la date n'est pas correcte
                do{
                    String strMg="";
                    strDate = JOptionPane.showInputDialog(null, 
                    "le vol numéro "+ volChercher + " destination "+ listVols.get(posTrouve).getDestination()
                    + "\n date de depart le "+ listVols.get(posTrouve).getDateDepart() 
                    + "\n Entrez la nouvelle date sous forme JJ/MM/AAAA Ex:29/10/2023");
                    if(strDate == null){
                        repDate=1;
                        msg="";
                    }else{
                        strMg = verifierChaineDate(strDate);// verifier la forme de chaine de caractere de la date entree
                        if(strMg != ""){
                            msg = strMg;
                            JOptionPane.showMessageDialog(null,msg,"CORRECTION DE LA DATE", JOptionPane.PLAIN_MESSAGE); 
                        }else{
                            repDate=1;
                        }
                    }
                }while(repDate==0);
                    if(strDate != null){
                
                    String eleDate[] = strDate.split("/");
                    date = new Date(Integer.parseInt(eleDate[0]), Integer.parseInt(eleDate[1]), 
                                        Integer.parseInt(eleDate[2]));
                listVols.get(posTrouve).setDateDepart(date);
                msg = "la novelle date de depart du vol numéro " + volChercher + " a été modifiée a "  + date;
                }
        }
        if(msg !=""){        
        
            JOptionPane.showMessageDialog(null,msg,
            "MODIFICATION DE LA DATE DE DÉPART", JOptionPane.PLAIN_MESSAGE); 
        }
        strReponce =JOptionPane.showInputDialog(null,
        "voulez-vos entrez un autre numéro du vol  à modifier la date de depart (O/N)? :");
        if(strReponce == null){
            reponse = 'N';
        }else{
        reponse = Character.toUpperCase(strReponce.charAt(0));
        }
    }while(reponse=='O');    
    }

    // Méthode pour réserver un vol
    public static void reseverVol(){
        char reponse='O';
        String msg="";
        do{
            String volChercher = JOptionPane.showInputDialog(null,
             "Entrez le numéro du vol pour ajouter des réservations ");
             int posTrouve = trouverIndex(volChercher);
             if(!rechererVol(volChercher)){
                 msg= "le vol numéro "+ volChercher + " n'existe pas";
            }else{
                int nbActuel = listVols.get(posTrouve).getNbTotalReservation();
                int diffrenceReservation = MAX_PLACES - nbActuel;
                int nbReservation = Integer.parseInt(JOptionPane.showInputDialog(null,
                "le vol numéro "+ volChercher + " destination "+ listVols.get(posTrouve).getDestination()
                + "\n date de depart le "+ listVols.get(posTrouve).getDateDepart() +
                "\n les places restantes : " + diffrenceReservation + " place(s) " +
                "\n Entrez le nombre des places dont vous désire réserver "));
                if(nbReservation > diffrenceReservation){
                   msg="Il n'y a pas assez des places disponibles!!\n Il reste que " 
                   + diffrenceReservation + " place(s) seulement "; 
                }else{
                    listVols.get(posTrouve).setNbTotalReservation(nbReservation+nbActuel);
                    msg = "la réservation du vol numéro " + volChercher + " destination "+ listVols.get(posTrouve).getDestination()
                    + "\n a été complété avec succés pour la date de départ " + listVols.get(posTrouve).getDateDepart();
                }

            }
            JOptionPane.showMessageDialog(null,msg,
            "RÉSERVATION D'UN VOL", JOptionPane.PLAIN_MESSAGE); 
            reponse=JOptionPane.showInputDialog(null,
            "voulez-vos entrez un autre numéro du vol pour ajouter des reservations O/N?").charAt(0);
            reponse = Character.toUpperCase(reponse);
        }while(reponse=='O');    
    }

    // Méthode pour vérifier la forme de la date entrée
    public static String verifierChaineDate(String strDate){
        boolean b = true;
        String msg="";
        Date date;
        String datemsg="";


        if(strDate.length()!=10 || (strDate.indexOf("/") != 2 && 
        strDate.substring(3).indexOf("/") != 2) ){
            msg += strDate +" doit etre sous forme JJ/MM/AAAA Ex:29/10/2023\n";
        }else{
            String strNombre = strDate.substring(0,2)
                               +strDate.substring(3,5)
                               +strDate.substring(6,10);
            try {
                Float f = Float.parseFloat(strNombre);
            } catch (NumberFormatException e) {
                b = false;
            }
            
            if(b == false){
                msg += strDate +" doit etre avoir que des nombres\n";
            }else{
                    //msg = "1" + strDate;

                date = new Date(Integer.parseInt(strDate.substring(0,2)), 
                Integer.parseInt(strDate.substring(3,5)), 
                Integer.parseInt(strDate.substring(6,10)));
               // datemsg=date.getMsg();
                date.compareNow();
                datemsg=date.getMsg();
                if(datemsg != ""){
                    msg += datemsg;
                }

            } 
        } 
        return msg;   

    }

    // Menu principal
    public static int menu(){
        String options="1-Lister des vols\n2-Ajouter un vol\n3-Retirer un vol\n";
		options+="4-Modifier la date de départ\n5-Réservation d'un vol\n6-Terminer\nEntrez votre choix\n";
		return Integer.parseInt(JOptionPane.showInputDialog(null,options,"MENU PRINCIPAL",JOptionPane.PLAIN_MESSAGE));
    }
    
    public static void main(String[] args) throws Exception{
        
        char sortie ='O';

        chargerVols();
        do{
            int choix = menu();
            switch(choix){
                case 1: afficher();
                break;
                case 2: insererVol();;
                break;
                case 3: retirerVol();
                break;
                case 4: modifierDate();
                break;
                case 5: reseverVol();
                break;
                case 6: ecrireFichier();
                //JOptionPane.showMessageDialog(null, "Merci d'utiliser notre systéme",
                // "Merci", JOptionPane.PLAIN_MESSAGE);
                sortie='N';
                break;
                default:JOptionPane.showMessageDialog(null, "Votre choix est invalide!!",
                 "choix invaide", JOptionPane.PLAIN_MESSAGE);
                break; 

            }
        }while(sortie=='O');
        /* 
        Date date = new Date("32","07","2020");
        System.out.println(date);
        JOptionPane.showMessageDialog(null, date);
        */
        System.exit(0);
    }
}
