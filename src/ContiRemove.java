import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
public class ContiRemove{
 private static String os=System.getProperty("os.name");
 static String read(File f) throws IOException{
        String text="";
        try (FileReader fileStream = new FileReader(f);
             BufferedReader bufferedReader = new BufferedReader(fileStream)) {
            String line = null;
            while ((line = bufferedReader.readLine())!= null) {
                text=line;
            }
        }
        return text;
    }
 static boolean compare(File[] a, File[] b) throws IOException{
  boolean same=true;
  if(a.length!=b.length){
   same=false;
  }
  else if(a.length==b.length){
   for(int x=0;x<a.length;x++){
    if(read(a[x]).equals(read(b[x]))==false){
      same=false;
    }
   }
  }
  return same;
 }
 public static void write(File f, String text) throws IOException{
  try (FileWriter fileStream = new FileWriter(f)) {
    fileStream.write(text);
    fileStream.close();
  }
  catch(IOException e){
   System.out.println("Can't write to " + f.getAbsolutePath());
  }
 }
 static int findfilepos(String[] a, File n){
  int pos=-1;
  for(int x=0;x<a.length;x++){
   if(a[x].equals(n.getName())){
    pos=x;
   }
  }
  return pos;
 }
 static boolean noticeChange(File a, String directory) throws IOException{
   boolean is=false;
   if(os.equals("Linux")){
   if(read(a).equals(read(new File(directory+"/"+a.getName())))==false){
    is=true;
   }
  }
   else if(os.equals("Windows")){
    if(read(a).equals(read(new File(directory+"\\"+a.getName())))==false){
      is=true;
   }
  }
   return is;
 }
 public static void move(File a, String directory) throws IOException{
  try{
   if(noticeChange(a, directory)){
    if(os.equals("Linux")){
    write(new File(directory+"/"+a.getName()), read(a));
    //a.delete(); maybe needed later
    }
    else if(os.equals("Windows")){
      write(new File(directory+"\\"+a.getName()), read(a));
      //a.delete(); maybe needed later
    }
   }
  }
  catch(IOException e){
   System.out.println("Can't write to " + directory);
  }
 }
 static int numtypes(File[] a, String extension){
  int num=0;
  for(int x=0;x<a.length;x++){
   if(a[x].getName().endsWith(extension)){
    num++;
   }
  }
  return num;
 }
 static File[] maketypearray(File[] a, String extension){
  File[] b=new File[numtypes(a, extension)];
  for(int x=0;x<a.length;x++){
   if(a[x].getName().endsWith(extension)){
    b[x]=a[x];
   }
  }
  return b;
 }
 public static void main(String[] args) throws IOException{
  Scanner input=new Scanner(System.in);
  String currentpath=new File(".").getCanonicalPath();
  System.out.println("Current directory: " + currentpath);
  System.out.print("Choose directory location:");
  String path=input.nextLine();
  System.out.println(" ");
  System.out.print("All files or selected files(A/S):");
  String choice=input.nextLine();
  if(choice.equals("A")){
  File[] marray=new File(path).listFiles();
  String[] srcontent=new String[marray.length];
  String[] targetcontent=new String[srcontent.length];
  System.out.println("Added " + marray.length + " files to array.");
  System.out.print("Target directory:");
  String target=input.nextLine();
  if(compare(marray,new File(target).listFiles())==false){
   System.out.println("The length of the target and source directory should be same.");
   System.exit(0);
  }
  else{
   //real code
    System.out.println("Reading files of " + path + " and " + target + " (" + (marray.length + new File(target).listFiles().length) + " files)...");
    for(int x=0;x<marray.length;x++){
      srcontent[x]=read(marray[x]);
      if(os.equals("Windows")){
      targetcontent[x]=read(new File(target+"\\"+marray[x].getName()));
      }
      else if(os.equals("Linux")){
      targetcontent[x]=read(new File(target+"/"+marray[x].getName()));
      }
    }
    System.out.println("Read all the files (FIRST STATE), now any changes you make in the directory " + path + " will be saved to the directory " + target + ".");
    System.out.println("Press enter to start. DO NOT STOP THIS PROGRAM.");
    input.nextLine();
    System.out.println(" ");
    while(true){
     for(int x=0;x<marray.length;x++){
      if(noticeChange(marray[x], target)){
       move(marray[x], target);
       System.out.println("Moved " + marray[x].getName() + " to " + target);
      }
     }
    }
  }
  }
  else if(choice.equals("S")){
    System.out.println(" ");
    System.out.print("Selected type or Selected files(T,F):");
    String schoice=input.nextLine();
    //subchoices
    //Selected type
    if(schoice.equals("T")){
      System.out.println(" ");
      System.out.print("Type the extension:");
      String extension=input.nextLine();
      System.out.println(" ");
      File[] marray=maketypearray(new File(path).listFiles(), extension);
      String[] srcontent=new String[marray.length];
      String[] targetcontent=new String[srcontent.length];
      System.out.println("Added " + marray.length + " files to array.");
      System.out.print("Target directory:");
      String target=input.nextLine();
      System.out.println(" ");
      if(compare(marray,new File(target).listFiles())==false){
       System.out.println("The length of the target and source directory should be same.");
       System.exit(0);
      }
      else{
       //real code
        System.out.println("Reading files of " + path + " and " + target + " (" + (marray.length + new File(target).listFiles().length) + " files)...");
        for(int x=0;x<marray.length;x++){
          srcontent[x]=read(marray[x]);
          if(os.equals("Windows")){
          targetcontent[x]=read(new File(target+"\\"+marray[x].getName()));
          }
          else if(os.equals("Linux")){
          targetcontent[x]=read(new File(target+"/"+marray[x].getName()));
          }
        }
        System.out.println("Read all the files (FIRST STATE), now any changes you make in the directory " + path + " will be saved to the directory " + target + ".");
        System.out.println("Press enter to start. DO NOT STOP THIS PROGRAM.");
        input.nextLine();
        System.out.println(" ");
        while(true){
         for(int x=0;x<marray.length;x++){
          if(noticeChange(marray[x], target)){
           move(marray[x], target);
           System.out.println("Moved " + marray[x].getName() + " to " + target);
          }
         }
        }
      }
    }
    else if(schoice.equals("F")){
      System.out.println(" ");
      System.out.println("How many files?");
      Scanner intput=new Scanner(System.in);
      int num=intput.nextInt();
      File[] marray=new File[num];
      for(int x=0;x<marray.length;x++){
       System.out.print("File " + (x+1) + ":");
       marray[x]=new File(input.nextLine());
      }
      String[] srcontent=new String[marray.length];
      String[] targetcontent=new String[srcontent.length];
      System.out.println("Added " + marray.length + " files to array.");
      System.out.print("Target directory:");
      String target=input.nextLine();
      System.out.println(" ");
      if(compare(marray,new File(target).listFiles())==false){
       System.out.println("The length of the target and source directory should be same.");
       System.exit(0);
      }
      else{
       //real code
        System.out.println("Reading files of " + path + " and " + target + " (" + (marray.length + new File(target).listFiles().length) + " files)...");
        for(int x=0;x<marray.length;x++){
          srcontent[x]=read(marray[x]);
          if(os.equals("Windows")){
          targetcontent[x]=read(new File(target+"\\"+marray[x].getName()));
          }
          else if(os.equals("Linux")){
          targetcontent[x]=read(new File(target+"/"+marray[x].getName()));
          }
        }
        System.out.println("Read all the files (FIRST STATE), now any changes you make in the directory " + path + " will be saved to the directory " + target + ".");
        System.out.println("Press enter to start. DO NOT STOP THIS PROGRAM.");
        input.nextLine();
        System.out.println(" ");
        while(true){
         for(int x=0;x<marray.length;x++){
          if(noticeChange(marray[x], target)){
           move(marray[x], target);
           System.out.println("Moved " + marray[x].getName() + " to " + target);
          }
         }
        }
      }
    }
  }
 } 
}