import javax.swing.*;
import java.lang.String;
import java.lang.Boolean;
import java.awt.*;
import java.awt.GridLayout;
import java.awt.event.*;

public class AddBook{
    static void showDialog() {
        final JTextField titleField = new JTextField(25);
        final JTextField authorField = new JTextField(25);
        final JTextField dateField = new JTextField(10);
        final JTextField ISBNField = new JTextField(10);
        JButton save = new JButton("Save");
        
        final JLabel warningLabel = new JLabel("");

        final JPanel myPanel = new JPanel(new GridLayout(5, 2));
        myPanel.add(new JLabel("Book Title:"));
        myPanel.add(titleField);
        myPanel.add(new JLabel("Author:"));
        myPanel.add(authorField);
        myPanel.add(new JLabel("Pub/Rel Date:"));
        myPanel.add(dateField);
        myPanel.add(new JLabel("ISBN-10:"));
        myPanel.add(ISBNField);
        myPanel.add(warningLabel);
        myPanel.add(save);
        save.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String author = authorField.getText();
                String date = dateField.getText();
                String isbn = ISBNField.getText();
                if (title.length() == 0){
                    warningLabel.setForeground(Color.RED);
                    warningLabel.setText("Book must have a title");
                } else if (author.length() == 0){
                    warningLabel.setForeground(Color.RED);
                    warningLabel.setText("Book must have an author");
                } else if (date.length() != 10){
                    warningLabel.setForeground(Color.RED);
                    warningLabel.setText("Publication date must follow format MM/DD/YYYY");
                } else if (isbn.length() != 10){
                    warningLabel.setForeground(Color.RED);
                    warningLabel.setText("ISBN must be 10 characters long");
                } else {
                    Book book = new Book(title,author,date,isbn);
                    boolean added = Book.addBook(book, true, false);
                    added = Book.writeBookListToFile();
                    if (added){
                        titleField.setText("");
                        authorField.setText("");
                        dateField.setText("");
                        ISBNField.setText("");
                        warningLabel.setText("");
                        JOptionPane.showMessageDialog(myPanel, "The book was added", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(myPanel, "An error occurred while adding the book", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        int logoff = JOptionPane.showOptionDialog(null, myPanel, "Library Management System", JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[]{"Log off"}, null);
        
        if (logoff == JOptionPane.YES_OPTION){
            LoginOptions.showDialog();
        }
    }
}