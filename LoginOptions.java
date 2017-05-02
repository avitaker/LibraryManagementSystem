import javax.swing.JOptionPane;

public class LoginOptions{
    public static void main(String[] args){
        
    }
    
    static void showDialog(){
        int chosen = JOptionPane.showOptionDialog(null, null, "Library Management System", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[]{"Student","Staff"}, null);
        
        if (chosen == JOptionPane.YES_OPTION){
            BorrowBook.showDialog();
        }
        else if (chosen == JOptionPane.NO_OPTION) {
            TeacherLogin.showDialog();
        }
    }
}