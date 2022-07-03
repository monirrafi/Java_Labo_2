import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
public class Test {
    public static void main(String[] args) {            
   
        JTextArea headersTxt = new JTextArea(5,2);
    //    for (int i = 0 ; i < 5 ; i ++ ) {
        headersTxt.append("2222\ttest\tpp\tjj mm aaaa\t55\n") ;
        headersTxt.append("2222\ttest          \tpp\tjj mm aaaa\t55\n") ;
        headersTxt.append("2222\ttest \tpp\tjj mm aaaa\t55\n") ;
            
    //    }
        JScrollPane scroll = new JScrollPane(headersTxt); 
        scroll.setSize (300,600) ;  // this line silently ignored
        int test = JOptionPane.showConfirmDialog(null,  scroll,"test",  JOptionPane.OK_CANCEL_OPTION) ;
  }
}
    

