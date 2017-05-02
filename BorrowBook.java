import javax.swing.*;
import javax.swing.table.*;
import java.lang.String;
import java.lang.Boolean;
import java.awt.*;
import java.awt.GridLayout;
import java.awt.event.*;

public class BorrowBook{
    static final String[] columnNames = {"Book Title",
                        "Author",
                        "Pub/Rel Date",
                        "ISBN-10",
                        "Checked out?"};
    static Object[][] data;
    

    
    static void showDialog(){
        data = Book.getAllDisplayStrings();
        final DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {

            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        final JTable table = new JTable();
        table.setModel(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(table){
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(800, 400);
            }
        };
        table.setFillsViewportHeight(true);
        JButton borrow = new JButton("Borrow");
        JButton checkin = new JButton("Return");
        
        final JPanel myPanel = new JPanel();
        myPanel.add(scrollPane);
        JPanel buttonPanel = new JPanel(new GridLayout(2,1));
        buttonPanel.add(borrow);
        buttonPanel.add(checkin);
        myPanel.add(buttonPanel);
        borrow.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                int selected = table.getSelectionModel().getLeadSelectionIndex();
                boolean checkedOut = Book.checkIfBorrowed(selected);
                if (selected<0){
                    checkedOut = true;
                }
                if (!checkedOut){
                    boolean checkOut = Book.modifyBook(selected);
                    if (checkOut){
                        data = Book.getAllDisplayStrings();
                        tableModel.setValueAt("Yes",selected,4);
                        table.invalidate();
                        
                        JOptionPane.showMessageDialog(myPanel, "The book was successfully checked out", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    String errorString = null;
                    if (selected<0){
                        errorString = "You must select a book first";
                    } else {
                        errorString = "This book has already been checked out";
                    }
                    JOptionPane.showMessageDialog(myPanel, errorString, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        checkin.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                int selected = table.getSelectionModel().getLeadSelectionIndex();
                boolean checkedOut = Book.checkIfBorrowed(selected);
                if (checkedOut){
                    boolean checkOut = Book.modifyBook(selected);
                    if (checkOut){
                        data = Book.getAllDisplayStrings();
                        tableModel.setValueAt("No",selected,4);
                        table.invalidate();
                        
                        JOptionPane.showMessageDialog(myPanel, "The book was successfully checked in", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    String errorString = null;
                    if (selected<0){
                        errorString = "You must select a book first";
                    } else {
                        errorString = "This book is already checked in";
                    }
                    JOptionPane.showMessageDialog(myPanel, errorString, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        int logoff = JOptionPane.showOptionDialog(null, myPanel, "Library Management System", JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[]{"Log off"}, null);
        
        if (logoff == JOptionPane.YES_OPTION){
            LoginOptions.showDialog();
        }
    }
    
    public static void main(String[] args){
//        showDialog();
    }
}