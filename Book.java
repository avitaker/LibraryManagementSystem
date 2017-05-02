import java.lang.String;
import java.io.*;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Boolean;
import java.lang.StringBuilder;

public class Book{
    public static void main(String[] args){
        getBookList(true);
        for (int i=0; i<sBooks.size(); i++){
            System.out.println(sBooks.get(i).toString());
        }
    }
    
    private static ArrayList<Book> sBooks;
    private static boolean sChanged = false;
    private static final String sFilename = "records.txt";
    
    
    String bookName;
    String author;
    String date;
    String ISBN;
    boolean isCheckedOut;
    
    public Book(String bookName, String author, String date, String ISBN){
        this.bookName = bookName;
        this.author = author;
        this.date = date;
        this.ISBN = ISBN;
        this.isCheckedOut = false;
    }
    
    public Book(String bookName, String author, String date, String ISBN, boolean checkedOut){
        this.bookName = bookName;
        this.author = author;
        this.date = date;
        this.ISBN = ISBN;
        this.isCheckedOut = checkedOut;
    }
    
    public String getTitle(){
        return bookName;
    }
    
    public String getAuthor(){
        return author;
    }
    
    public String getPublicationDate(){
        return date;
    }
    
    public String getISBN(){
        return ISBN;
    }
    
    public boolean getCheckOutStatus(){
        return isCheckedOut;
    }
    
    public void setCheckOutStatus(boolean check){
        this.isCheckedOut = check;
    }
    
    public String getPrintableCheckout(){
        return isCheckedOut? "Yes":"No";
    }
    
    private static boolean getCheckoutBool(String checkoutString){
        return checkoutString.equals("Yes");
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Title: " + bookName + ", ");
        sb.append("Author: " + author + ", ");
        sb.append("Publication Date: " + date + ", ");
        sb.append("ISBN: " + ISBN + ", ");
        sb.append("Checked out: " + getPrintableCheckout());
        return sb.toString();
    }
    
    public static ArrayList<Book> getBookList(boolean fileChanged){
        if (sBooks==null || fileChanged){
            sBooks = new ArrayList<>();
            
            File file = new File(sFilename);
            Scanner fScanner = null;
            
            try {
                fScanner = new Scanner(file);
                fScanner.nextLine();
                int lineNumber = 0;
                String title = null;
                String author= null;
                String publicationDate= null;
                String isbn10= null;
                boolean checkedOut= false;
                Book book;
                
                while (fScanner.hasNextLine()){
                    String thisLine = fScanner.nextLine();
                    if (lineNumber<5){
                        switch(lineNumber){
                            case 0:
                                title = thisLine;
                                break;
                            case 1:
                                author = thisLine;
                                break;
                            case 2:
                                publicationDate = thisLine;
                                break;
                            case 3:
                                isbn10 = thisLine;
                                break;
                            case 4:
                                checkedOut = getCheckoutBool(thisLine);
                                break;
                            default:
                                break;
                        }
                        lineNumber++;
                    } else {
                        book = new Book(title, author, publicationDate, isbn10, checkedOut);
                        sBooks.add(book);
                        lineNumber=0;
                    }
                }
                
                book = new Book(title, author, publicationDate, isbn10, checkedOut);
                sBooks.add(book);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            } finally {
                if (fScanner!=null){
                    fScanner.close();
                }
            }
        }
        return sBooks;
    }
    
    public static String[] getDisplayStrings(Book book){
        String[] sArray = new String[5];
        sArray[0] = book.getTitle();
        sArray[1] = book.getAuthor();
        sArray[2] = book.getPublicationDate();
        sArray[3] = book.getISBN();
        sArray[4] = book.getPrintableCheckout();
        return sArray;
    }
    
    public static String[][] getAllDisplayStrings(){
        getBookList(true);
        String[][] listOfBooks = new String[sBooks.size()][5];
        for (int i=0; i<sBooks.size(); i++){
            String[] thisArray = getDisplayStrings(sBooks.get(i));
            listOfBooks[i] = thisArray;
        }
        return listOfBooks;
    }
    
    static boolean addBook(Book book, boolean adding, boolean first){
        if (sBooks==null){
            getBookList(true);
        }
        boolean added = false;
        
        StringBuilder sb = new StringBuilder();
        String newLine = "\n";
        if (adding){
            sb.append(newLine + newLine);
        } else {
            if (first){
                sb.append(Integer.toString(sBooks.size())+newLine);
            } else {
                sb.append(newLine + newLine);
            }
        }
        sb.append(book.getTitle() + newLine);
        sb.append(book.getAuthor() + newLine);
        sb.append(book.getPublicationDate() + newLine);
        sb.append(book.getISBN() + newLine);
        sb.append(book.getPrintableCheckout());
        String toAdd = sb.toString();
        FileWriter fw = null;
        try {
            if (!adding){
                if (first){
                   fw = new FileWriter(sFilename); 
                } else {
                    fw = new FileWriter(sFilename, true);
                }
            } else {
                fw = new FileWriter(sFilename, true);
            }
            fw.write(toAdd);
            fw.close();
            if (adding){
                sBooks.add(book);
            }
            added = true;
            return added;
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean writeBookListToFile(){
        getBookList(false);
        if (sBooks == null){
            return false;
        }
        
        boolean done = false;
        Book thisBook = sBooks.get(0);
        done = addBook(thisBook, false, true);
        for (int i=1; i<sBooks.size(); i++){
            thisBook = sBooks.get(i);
            done = addBook(thisBook, false, false);
        }
        return done;
    }
    
    public static boolean modifyBook(String bookTitle){
        if (sBooks==null){
            getBookList(true);
        }
        boolean modified = false;
        boolean fileWritten = false;
        for (int i=0; i<sBooks.size(); i++){
            Book thisBook = sBooks.get(i);
            if (thisBook.getTitle().toLowerCase().equals(bookTitle.toLowerCase())){
                if (thisBook.isCheckedOut){
                    thisBook.setCheckOutStatus(false);
                } else {
                    thisBook.setCheckOutStatus(true);
                }
                sBooks.set(i, thisBook);
                modified =true;
            }
        }
        if (modified){
            fileWritten = writeBookListToFile();
        }
        return modified && fileWritten;
    }
    
    static boolean modifyBook(int index){
        if (sBooks==null){
            getBookList(true);
        }
        
        if (index<0 || index>=sBooks.size()){
            return false;
        }
        
        boolean modified = false;
        boolean fileWritten = false;
        
        Book thisBook = sBooks.get(index);
        
        if (thisBook.isCheckedOut){
            thisBook.setCheckOutStatus(false);
        } else {
            thisBook.setCheckOutStatus(true);
        }
        sBooks.set(index, thisBook);
        modified =true;
        if (modified){
            fileWritten = writeBookListToFile();
        }
        return modified && fileWritten;
    }
    
    static boolean checkIfBorrowed(int index){
        if (sBooks == null){
            getBookList(true);
        }
        
        if (index<0 || index>=sBooks.size()){
            return false;
        }
        
        return sBooks.get(index).getCheckOutStatus();
    }
}