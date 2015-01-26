import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;
import java.io.FilenameFilter;
import java.util.Scanner;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class Migrate{
    static File fout = new File("Report.txt");
    
    /**
     * This method compare the two folder in order to collect the .java files.
     * It writes in the file Report.txt the files that has been removed/added in the new release.
     * If the file Report.txt is already existing it just update it.
     */
    public static void checkFiles(File[] fileArr1, File[] fileArr2){
        try{
            List<String> fileList1=new LinkedList<String>();
            List<String> fileList2=new LinkedList<String>();
            
            for(int i=0; i<fileArr1.length; i++)
                fileList1.add(fileArr1[i].getName().substring(fileArr1[i].getName().lastIndexOf("/")+1));
            
            for(int i=0; i<fileArr2.length; i++)
                fileList2.add(fileArr2[i].getName().substring(fileArr2[i].getName().lastIndexOf("/")+1));
            
            if(fout.exists()&&!fout.isDirectory())
                return;
            
            FileOutputStream fos = new FileOutputStream(fout);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            
            for(int i=0; i<fileList1.size();i++){
                if(!fileList2.contains(fileList1.get(i))){
                    bw.write("File " + fileList1.get(i) + " not existing in the new release");
                    bw.newLine();
                }
            }
            
            for(int i=0; i<fileList2.size();i++){
                if(!fileList1.contains(fileList2.get(i))){
                    bw.write("File " + fileList2.get(i) + " not existing in the old release");
                    bw.newLine();
                }
            }
            
            bw.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        //args[0] should point to the old release, args[1] should point to the new release.
        File dir1 = new File(args[0]);
        File dir2 = new File(args[1]);
        
        File[] fileArr1 =dir1.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".java");
            }
        });
        
        File[] fileArr2 =dir2.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".java");
            }
        });
        
        checkFiles(fileArr1, fileArr2);
        
        Scanner keyboard = new Scanner(System.in);
        boolean inside1=false;
        boolean inside2=false;
        boolean match=false;
        
        try{
            FileOutputStream fos = new FileOutputStream(fout, true);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            
            for(int i=0; i<fileArr1.length;i++){
                try {
                    bw.write(fileArr1[i].getName());
                    bw.newLine();
                    List<String> lines1 = Files.readAllLines(Paths.get(args[0]+File.separator+fileArr1[i].getName()), Charset.defaultCharset());
                    List<String> lines2 = Files.readAllLines(Paths.get(args[1]+File.separator+fileArr1[i].getName().substring(fileArr1[i].getName().lastIndexOf("/")+1)), Charset.defaultCharset());
                    
                    File fout1 = new File("out");
                    fout1.mkdirs();
                    fout1=new File("out"+File.separator+fileArr1[i].getName());
                    fout1.createNewFile();
                    FileOutputStream fos1 = new FileOutputStream(fout1, true);
                    BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(fos1));
                    
                    int index1=0;
                    int index2=0;
                    int indexnew=0;
                    
                    System.out.println("Starting file "+fileArr1[i].getName()+ "...");
                    
                    for ( ; index1<lines1.size() ; index1++) {
                        if(lines1.get(index1).contains("/**"))
                            break;
                    }
                    if(index1>=lines1.size()){
                        bw.write("Problem in: "+fileArr1[i].getName());
                        bw.newLine();
                    }
                    
                    for( ; index2 < lines2.size() ; index2++){
                        if(lines2.get(index2).contains("/**"))
                            break;
                        indexnew++;
                        bw1.write(lines2.get(index2));
                        bw1.newLine();
                    }
                    if(index2>=lines2.size()){
                        bw.write("Problem in: "+fileArr2[i].getName());
                        bw.newLine();
                    }
                    match=true;
                    while(index2<lines2.size()&&index1<lines1.size()){
                        
                        if(lines1.get(index1).contains("/**"))
                            inside1=true;
                        if(lines2.get(index2).contains("/**"))
                            inside2=true;
                        if(lines1.get(index1).contains("*/"))
                            inside1=false;
                        if(lines2.get(index2).contains("*/"))
                            inside2=false;
                        
                        
                        //Match between the two versions
                        if(lines2.get(index2).equals(lines1.get(index1))||
                           (lines2.get(index2).replaceAll("\\s+","").toLowerCase()).equals((lines1.get(index1).replaceAll("\\s+","").toLowerCase()))){
                            bw1.write(lines2.get(index2));
                            bw1.newLine();
                            indexnew++;
                            index1++;
                            index2++;
                            match=true;
                        }else{
                            //Mismatch
                            //A collect.stats is required in the new file
                            if(lines2.get(index2).contains("/**") && match){
                                if(lines1.get(index1).contains("/** {@collect.stats}")){
                                    if(lines2.get(index2).contains("*/")){//Everything on one line case
                                        String tmp=lines2.get(index2).substring((lines2.get(index2).indexOf("*")+1),(lines2.get(index2).lastIndexOf("/")-1));
                                        if(tmp.replaceAll("\\s+","").toLowerCase().equals(lines1.get(index1+2).replaceAll("\\s+","").toLowerCase())){
                                            String tmp2=lines2.get(index2).substring(0,lines2.get(index2).indexOf("*")+2)+ " {@collect.stats}";
                                            bw1.write(tmp2);
                                            bw1.newLine();
                                            indexnew++;
                                            index1++;
                                            for(int j=0;j<2;j++){
                                                tmp2=lines2.get(index2).substring(0,lines2.get(index2).indexOf("/"))+"* "+(lines1.get(index1).substring(lines1.get(index1).lastIndexOf("*")+1));
                                                bw1.write(tmp2);
                                                bw1.newLine();
                                                indexnew++;
                                                index1++;
                                            }
                                            tmp2=lines2.get(index2).substring(0,lines2.get(index2).indexOf("/"))+"*/";
                                            lines2.set(index2, tmp2);
                                        }else{
                                            //Could be a new method. We propose a solution
                                            StringBuilder tmp2=new StringBuilder();
                                            tmp2.append(lines2.get(index2).substring(0,lines2.get(index2).indexOf("*")+2));
                                            tmp2.append(" {@collect.stats}\n");
                                            tmp2.append(lines2.get(index2).substring(0,lines2.get(index2).indexOf("*")+1));
                                            tmp2.append(" {@description.open}\n");
                                            tmp2.append(lines2.get(index2).substring(0,lines2.get(index2).indexOf("*")+1));
                                            tmp2.append(tmp);
                                            tmp2.append("\n");
                                            tmp2.append(lines2.get(index2).substring(0,lines2.get(index2).indexOf("*")+1));
                                            tmp2.append(" {@description.close}\n");
                                            tmp2.append(lines2.get(index2).substring(0,lines2.get(index2).indexOf("*")+1));
                                            tmp2.append("/");
                                            
                                            System.out.println("New Method/Variable");
                                            System.out.println("Code new release\n" + lines2.get(index2)+"\n");
                                            System.out.println("Suggested code \n"+ tmp2);
                                            System.out.println("Type 0 to reject the change and any other number to accept");
                                            
                                            int keyb=keyboard.nextInt();
                                            if(keyb!=0){
                                                bw1.write(tmp2.toString());
                                                bw1.newLine();
                                                indexnew+=5;
                                                bw.write("-> The code at line " +  lines2.get(index2) + " needs a check (new Method/Variable)");
                                                bw.newLine();
                                                bw.flush();
                                                index2++;
                                            }
                                        }
                                    }else{//It is on multiple lines
                                        if(lines2.get(index2+1).replaceAll("\\s+","").toLowerCase().equals(lines1.get(index1+2).replaceAll("\\s+","").toLowerCase())){
                                            String tmp=lines2.get(index2).substring(0,lines2.get(index2).indexOf("*")+2)+ " {@collect.stats}";
                                            bw1.write(tmp);
                                            bw1.newLine();
                                            index2++;
                                            index1+=2;
                                            tmp=lines2.get(index2).substring(0,lines2.get(index2).indexOf("*")+1)+" {@description.open}";
                                            bw1.write(tmp);
                                            bw1.newLine();
                                            indexnew+=2;
                                        }else if(lines2.get(index2+1).replaceAll("\\s+","").toLowerCase().startsWith(lines1.get(index1+2).replaceAll("\\s+","").toLowerCase())){
                                            match=false;
                                        }else{
                                            //Could be a new method. We propose a solution
                                            StringBuilder tmp2=new StringBuilder();
                                            StringBuilder tmp3=new StringBuilder();
                                            int tmpindex=index2;
                                            int count=0;
                                            
                                            tmp2.append(lines2.get(index2).substring(0,lines2.get(index2).indexOf("*")+2));
                                            tmp2.append(" {@collect.stats}\n");
                                            tmp3.append(lines2.get(index2));
                                            tmp3.append("\n");
                                            index2++;
                                            tmp2.append(lines2.get(index2).substring(0,lines2.get(index2).indexOf("*")+1));
                                            tmp2.append(" {@description.open}\n");
                                            tmp3.append(lines2.get(index2));
                                            tmp3.append("\n");
                                            count+=2;
                                            while(!(lines2.get(index2).contains("*/")||lines2.get(index2).contains("@exception")||lines2.get(index2).contains("@param")||lines2.get(index2).contains("@author")||lines2.get(index2).contains("@see")||lines2.get(index2).contains("@return")||lines2.get(index2).contains("@since"))){
                                                tmp2.append(lines2.get(index2++));
                                                tmp2.append("\n");
                                                tmp3.append(lines2.get(index2));
                                                tmp3.append("\n");
                                                count++;
                                            }
                                            tmp2.append(lines2.get(index2).substring(0,lines2.get(index2).indexOf("*")+1));
                                            tmp2.append(" {@description.close}\n");
                                            tmp2.append(lines2.get(index2++));
                                            count+=2;
                                            
                                            System.out.println("New Method/Variable");
                                            System.out.println("Code new release\n" + tmp3+"\n");
                                            System.out.println("Suggested code \n"+ tmp2);
                                            System.out.println("Type 0 to reject the change and any other number to accept");
                                                                                        
                                            int keyb=keyboard.nextInt();
                                            if(keyb!=0){
                                                bw1.write(tmp2.toString());
                                                bw1.newLine();
                                                bw.write("-> The code at line " + indexnew + " needs a check (new Method/Variable)");
                                                bw.newLine();
                                                bw.flush();
                                                indexnew+=count;
                                            }else{
                                                index2=tmpindex;
                                            }
                                            match=false;
                                        }

                                    }
                                }else{
                                    //new method?
                                    match=false;
                                }
                            }else if((lines1.get(index1).contains("{@description.close") || lines1.get(index1).contains("{@property.close")) && match){
                                String tmp=lines2.get(index2).substring(0,lines2.get(index2).indexOf("*")+1)+(lines1.get(index1).substring(lines1.get(index1).lastIndexOf("*")+1));
                                bw1.write(tmp);
                                bw1.newLine();
                                indexnew++;
                                index1++;
                            }else if(lines1.get(index1).contains("<code>") && lines2.get(index2).contains("{@code ")){
                                bw1.write(lines2.get(index2));
                                bw1.newLine();
                                indexnew++;
                                index1++;
                                index2++;
                            }else if(!inside1 && !inside2 && index1+1<lines1.size() &&
                                     lines2.get(index2).replaceAll("\\s+","").startsWith(lines1.get(index1).replaceAll("\\s+","")) &&
                                     lines2.get(index2).replaceAll("\\s+","").endsWith(lines1.get(index1+1).replaceAll("\\s+",""))){
                                bw1.write(lines2.get(index2));
                                bw1.newLine();
                                indexnew++;
                                index1+=2;
                                index2++;
                                match=true;
                            }else if(match && inside1 && inside2 && index1+1<lines1.size() &&
                                     lines2.get(index2).replaceAll("\\s+","").replaceAll("\\*","").contains(lines1.get(index1).replaceAll("\\s+","").replaceAll("\\*",""))){
                                bw1.write(lines1.get(index1));
                                bw1.newLine();
                                lines2.set(index2, lines2.get(index2).replaceAll(Pattern.quote(lines1.get(index1).substring(lines1.get(index1).lastIndexOf("*")+1)), ""));
                                index1++;                                              
                                indexnew++;
                            }else if(match && inside1 && inside2 &&
                                     (lines1.get(index1).contains("{@property")||lines1.get(index1).contains("{@description")) &&
                                     lines2.get(index2).replaceAll("\\s+","").toLowerCase().equals(lines1.get(index1+1).replaceAll("\\s+","").toLowerCase())){
                                bw1.write(lines1.get(index1));
                                bw1.newLine();
                                index1++;
                                indexnew++;
                            /*}else if(match && inside1 && inside2){
                                int tmp=index1;
                                while(!lines1.get(index1).contains("* /")){
                                    if(lines1.get(index1).contains("{@description")||lines1.get(index1).contains("{@property")){
                                        inside1=inside2=false;
                                        index1=tmp;
                                        match=false;
                                        break;
                                    }
                                    index1++;
                                }
                                if(!inside1 || !inside2)
                                    continue;
                                match=true;
                                index1=tmp;
                                System.out.println("<<");
                                while(!lines1.get(index1).contains("* /")){
                                        System.out.println(lines1.get(index1));
                                        index1++;
                                }
                                inside1=false;
                                System.out.println(">>");
                                while(!lines2.get(index2).contains("* /")){
                                    System.out.println(lines2.get(index2));
                                    bw1.write(lines2.get(index2));
                                    bw1.newLine();
                                    indexnew++;
                                    index2++;
                                }
                                inside2=false;
                                */
                            }else{
                                System.out.println("Old: ");
                                System.out.println("1 -  >> "+lines1.get(index1));
                                for(int j=1; j<25; j++)
                                    if(index1+j<lines1.size()){
                                        System.out.format("%2d-     ",j+1);
                                        System.out.println(lines1.get(index1+j));
                                    }
                                System.out.println("\n\n");
                                
                                
                                System.out.println("New: ");
                                System.out.println("1 -  >> "+lines2.get(index2));
                                for(int j=1; j<25; j++)
                                    if(index2+j<lines2.size()){
                                        System.out.format("%2d-     ",j+1);
                                        System.out.println(lines2.get(index2+j));
                                    }
                                
                                System.out.println("\n1) The code has been re-written. Advance old and new of #n #m steps without making changes (1 #old #new)");
                                System.out.println("2) The code has been re-written and the new file need a review. Write the lines for the new and the old files in the Report and advance both files of #n #m respectively (2 #old #new comment)");
                                System.out.println("3) Insert the line from the old file and advance to the next line of the old file");
                                System.out.println("4) Discard the line from the new file and advance to the next line");
                                System.out.println("5) Validate the migration");
                                System.out.println("6) Go to the next file");
                                
                                int myint = -1;
                                while(myint<1||myint>6)
                                    myint=keyboard.nextInt();
                                
                                if(myint==1){
                                    int tmp=keyboard.nextInt();
                                    index1+=tmp;
                                    tmp=keyboard.nextInt();
                                    while(tmp>0){
                                        bw1.write(lines2.get(index2));
                                        bw1.newLine();
                                        indexnew++;
                                        index2++;
                                        tmp--;
                                    }
                                    match=false;
                                }else if(myint==2){
                                    int tmp=keyboard.nextInt();
                                    index1+=tmp;
                                    tmp=keyboard.nextInt();
                                    while(tmp>0){
                                        bw1.write(lines2.get(index2));
                                        bw1.newLine();
                                        indexnew++;
                                        index2++;
                                        tmp--;
                                    }
                                    //System.out.println("Insert a comment");
                                    bw.write("-> " + indexnew + " " +keyboard.nextLine());
                                    bw.newLine();
                                    bw.flush();
                                    match=false;
                                }else if(myint==3){
                                    bw1.write(lines1.get(index1));
                                    bw1.newLine();
                                    indexnew++;
                                    index1++;
                                    match=false;
                                }else if(myint==4){
                                    index2++;
                                    match=false;
                                }else if(myint==5){
                                    System.out.println(index1 + " " +lines1.get(index1));
                                    System.out.println(index2 + " " +lines2.get(index2));
                                    System.out.println(lines2.get(index2+1).replaceAll("\\s+","").toLowerCase().equals(lines1.get(index1+2).replaceAll("\\s+","").toLowerCase()));
                                    match=true;
                                }else{
                                    index2=lines2.size();
                                    index1=lines1.size();
                                    bw.write("FILE TO RE-DO");
                                    bw.newLine();
                                    bw.flush();
                                    match=true;
                                }
                                //match=false;
                                System.out.print("\033[2J");
                                System.out.flush();
                            }
                        }
                        bw1.flush();
                    }

                    while(index1<lines1.size()){
                        if(lines1.get(index1).contains("/**")){
                            //System.out.println(lines1.get(index1));
                            bw.write("The old file was not completed yet "+index1);
                            bw.newLine();
                            break;
                        }
                    }
                    
                    while(index2<lines2.size()){
                        //System.out.println(lines2.get(index2));
                        if(lines2.get(index2).contains("/**")){
                            bw.write("Check the end of the file from line "+indexnew + " (new Methods/Variables)");
                            bw.newLine();
                            bw.flush();
                        }
                        bw1.write(lines2.get(index2));
                        bw1.newLine();
                        index2++;
                        indexnew++;
                    }
                    
                    bw1.close();
                } catch(NoSuchFileException e){
                    ;
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
            bw.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
    
}
