package util;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class MakeConfig{
    static int numtypes(File[] files, String type){
        int num = 0;
        for(File f : files){
            if(f.getName().endsWith(type)){
                num++;
            }
        }
        return num;
    }
    static String convarray(File[] a){
        String retval="";
        for(int x=0;x<a.length;x++){
            retval+=a[x].getAbsolutePath();
            if(x!=a.length-1){
                retval+=",";
            }
        }
        return retval;
    }
    static File[] gettypes(String location, String type){
     int numfiles=numtypes(new File(location).listFiles(), type);
        File[] files = new File[numfiles];
        int i = 0;
        for(File f : new File(location).listFiles()){
            if(f.getName().endsWith(type)){
                files[i] = f;
                i++;
            }
        }
        return files;
    }
    public static void compiler() throws IOException{
        Scanner input=new Scanner(System.in);
        System.out.println("Welcome to MakeConfig! You can make .crs files here just by answering a few questions!");
        System.out.print("Name of the file:");
        String filename=input.nextLine();
        System.out.print("Main directory:");
        String maindir=input.nextLine();
        File[] mdir=new File(maindir).listFiles();
        System.out.print("Target directory:");
        String targetdir=input.nextLine();
        System.out.print("All files or selected files?(A/S)");
        String choice=input.nextLine();
        if(choice.equals("S")){
        System.out.print("Selected type or selected files?(T/F)");
        String choice2=input.nextLine();
        if(choice2.equals("T")){
            System.out.print("Type:");
            String type=input.nextLine();
            mdir=gettypes(maindir, type);
            File mfile=new File(filename);
            if(mfile.createNewFile()){
                System.out.println("Writing into file...");
                FileWriter fw = new FileWriter(mfile);
                    fw.write(new File(maindir).getAbsolutePath() + " " + new File(targetdir).getAbsolutePath() + "\n" + "S" +" " + "T" + "\n" + convarray(mdir));
                    fw.close();
                    System.out.println("Written to file! Path: " + mfile.getAbsolutePath());
            }
            else{
                System.out.println("File already exists! Continue (Y/N)");
                String schoice=input.nextLine();
                if(schoice.equals("N")){
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                else if(schoice.equals("Y")){
                    System.out.println("Overwriting...");
                    FileWriter fw = new FileWriter(mfile);
                    fw.write(new File(maindir).getAbsolutePath() + " " + new File(targetdir).getAbsolutePath() + "\n" + convarray(mdir));
                    fw.close();
                    System.out.println("Written to file! Path: " + mfile.getAbsolutePath());
                }
                else{
                    System.out.println("Invalid input! Exiting...");
                    System.exit(0);
                }
            }
        }
        else if(choice2.equals("F")){
            System.out.print("Files(seperate with ','):");
            String files=input.nextLine();
            File mfile=new File(filename);
            if(mfile.createNewFile()){
                System.out.println("Writing into file...");
                FileWriter fw = new FileWriter(mfile);
                    fw.write(new File(maindir).getAbsolutePath() + " " + new File(targetdir).getAbsolutePath() + "\n" + files);
                    fw.close();
                    System.out.println("Written to file! Path: " + mfile.getAbsolutePath());
            }
            else{
                System.out.println("File already exists! Continue (Y/N)");
                String schoice=input.nextLine();
                if(schoice.equals("N")){
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                else if(schoice.equals("Y")){
                    System.out.println("Overwriting...");
                    FileWriter fw = new FileWriter(mfile);
                    fw.write(new File(maindir).getAbsolutePath() + " " + new File(targetdir).getAbsolutePath() + "\n" + "S" +" " + "F" + "\n" + files);
                    fw.close();
                    System.out.println("Written to file! Path: " + mfile.getAbsolutePath());
                }
                else{
                    System.out.println("Invalid input! Exiting...");
                    System.exit(0);
                }
            }
        }
        else{
            System.out.println("Invalid input! Exiting...");
            System.exit(0);
        }
        }
    else if(choice.equals("A")){
        File mfile=new File(filename);
        if(mfile.createNewFile()){
            System.out.println("Writing into file...");
            FileWriter fw = new FileWriter(mfile);
            fw.write(new File(maindir).getAbsolutePath() + " " + new File(targetdir).getAbsolutePath() + "\n" + convarray(mdir));
            fw.close();
            System.out.println("Written to file! Path: " + mfile.getAbsolutePath());
        }
        else{
            System.out.println("File already exists! Continue (Y/N)");
            String schoice=input.nextLine();
            if(schoice.equals("N")){
                System.out.println("Exiting...");
                System.exit(0);
            }
            else if(schoice.equals("Y")){
                System.out.println("Overwriting...");
                FileWriter fw = new FileWriter(mfile);
                fw.write(new File(maindir).getAbsolutePath() + " " + new File(targetdir).getAbsolutePath() + "\n" + convarray(mdir));
                fw.close();
                System.out.println("Written to file! Path: " + mfile.getAbsolutePath());
            }
            else{
                System.out.println("Invalid input! Exiting...");
                System.exit(0);
            }
        }
    }
}
 public static void main(String[] args)throws IOException{
        compiler();
    }
 }