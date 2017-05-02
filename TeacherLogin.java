import javax.swing.*;
import java.lang.String;
import java.lang.Boolean;
import java.awt.GridLayout;

public class TeacherLogin{
    
    static void showDialog(){
        JTextField usernameField = new JTextField(20);
        JTextField passwordField = new JPasswordField(20);
        
        final JPanel myPanel = new JPanel(new GridLayout(2,2));
        myPanel.add(new JLabel("Username:"));
        myPanel.add(usernameField);
        myPanel.add(new JLabel("Password:"));
        myPanel.add(passwordField);
        
        int login = JOptionPane.showOptionDialog(null, myPanel, "Log In", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[]{"Login","Cancel"}, null);
        
        if (login == JOptionPane.OK_OPTION){
            String username = usernameField.getText();
            String password = passwordField.getText();
            
            Staff staff = new Staff(username, password);
            Staff.getValidStaffs(true);
            boolean isValid = Staff.validate(staff);
            if (isValid){
                AddBook.showDialog();
            } else {
                int error = JOptionPane.showConfirmDialog(myPanel, "Login credentials were incorrect. Retry?", "Error", JOptionPane.OK_OPTION);
                if (error == JOptionPane.OK_OPTION){
                    showDialog();
                } else if (error == JOptionPane.NO_OPTION){
                    LoginOptions.showDialog();
                    return;
                }
            }
        } else if (login == JOptionPane.NO_OPTION){
            LoginOptions.showDialog();
        }
    }
}