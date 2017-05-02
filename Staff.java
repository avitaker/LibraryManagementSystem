import java.lang.String;
import java.io.*;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Boolean;

public class Staff{
    
    private static ArrayList<Staff> sStaff;
    private static final String sFilename = "staffUsernamePassword.txt";
    
    String mName;
    private String mPassword;
    
    public Staff(String name, String password){
        this.mName = name;
        this.mPassword = password;
    }
    
    public String getName(){
        return mName;
    }
    
    private String getPassword(){
        return mPassword;
    }
    
    public void print(){
        System.out.println("Name: " + getName() + ", Password: " + getPassword());
    }
    
    public static boolean validate(Staff staff){
        getValidStaffs(false);
        boolean isValid = false;
        for (int i=0; i<sStaff.size(); i++){
            if (isValid == true){
                return true;
            }
            Staff thisOne = sStaff.get(i);
            if (staff.getName().equals(thisOne.getName())){
                if (thisOne.getPassword().equals(staff.getPassword())){
                    isValid = true;
                    return true;
                }
            }
            
        }
        return isValid;
    }
    
    public static ArrayList<Staff> getValidStaffs(boolean fileChanged){
        File staffFile;
        if (sStaff == null || fileChanged){
            sStaff = new ArrayList<>();
            staffFile = new File(sFilename);
            
            try{
                Scanner scanner = new Scanner(staffFile);
                
                while (scanner.hasNextLine()){
                    String staffLine = scanner.nextLine();
                    String[] parts = staffLine.split("\t");
                    
                    String name = parts[0];
                    String password = parts[1];
                    
                    Staff staff = new Staff(name, password);
                    
                    sStaff.add(staff);
                }
                
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
        return sStaff;
    }
}