import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;

public class App {
    public static void createFile(Scanner sc, boolean crypt, String cryptType , String data) {
        try {
            System.out.println("\nGive a name to the file:");
            System.out.print("> ");
            String nameFile = sc.nextLine();
            
            if(crypt) {
                File file = new File("./txts/" + nameFile.toLowerCase() + ".txt");

                File folder = new File("./txts/");
                if(!folder.exists())
                    folder.mkdir();

                if(!file.createNewFile()) {
                    System.out.println("An file with that name already exists...");
                    System.exit(0);
                } else {
                    System.out.println("File Created: " + file.getName());   
                }

                System.out.println("\nWhat do you want to write in this file?");
                System.out.print("> ");
                String fileTxts = sc.nextLine();

                char[] fileTxtsArr = fileTxts.toCharArray();
                for (int i = 0; i < fileTxtsArr.length; i++) {
                    switch(fileTxtsArr[i]) {
                        case 160: case 8230: case 198: case 402:
                            fileTxtsArr[i] = 'a';
                            break;
                        case 8218: case 710:
                            fileTxtsArr[i] = 'e';
                            break;
                        case 161:
                            fileTxtsArr[i] = 'i';
                            break;
                        case 162: case 228:
                            fileTxtsArr[i] = 'o';
                            break;
                        case 163:
                            fileTxtsArr[i] = 'u';
                            break;
                        case 8225:
                            fileTxtsArr[i] = 'c';
                            break;
                        default:
                            break;
                    }
                }

                FileWriter fileToWrite = new FileWriter("./txts/" + nameFile.toLowerCase() + ".txt");
                fileToWrite.write(String.valueOf(fileTxtsArr));

                fileToWrite.close();
            } else {
                File file = new File("./txts/" + cryptType + "/" + nameFile.toLowerCase() + ".txt");

                File folder = new File("./txts/" + cryptType + "/");
                if(!folder.exists())
                    folder.mkdir();

                if(!file.createNewFile()) {
                    System.out.println("An file with that name already exists...");
                    System.exit(0);
                } else {
                    System.out.println("File Created: " + file.getName());   
                }

                FileWriter fileToWrite = new FileWriter("./txts/" + cryptType + "/" + nameFile.toLowerCase() + ".txt");
                fileToWrite.write(data);

                fileToWrite.close();
            }

            
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public static void returnsReader(Scanner sc, File fileRead) {
        try {
            if(!fileRead.exists()) {
                System.out.println("\nSometing goes Wrong... :(\nFile doesn't exists. Please, create the file first!");
            } else {
                Scanner fileToRead = new Scanner(fileRead);

                while(fileToRead.hasNextLine()) {
                    String data = fileToRead.nextLine();
                    System.out.println("\nTexto do Ficheiro: " + data);
                }
                
                fileToRead.close();
                
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public static void readFile(Scanner sc) {
        try {
            System.out.println("\nWhat's the name of the file you wanna read?");
            System.out.print("> ");
            String fileReadName = sc.nextLine();

            String[] drawFileTypeMenu = {"\n┌──────────────────────────────┐", "│          Type Of File        │", "│                              │", "│  1 - Read Regular File       │", "│  2 - Read Encrypted File     │", "│  3 - Read Decrypted File     │", "│                              │", "└──────────────────────────────┘"};
            for(String i : drawFileTypeMenu) {
                System.out.println(i);
            }

            System.out.println("\nWhat's the type of the file you wanna read?");
            System.out.print("> ");
            int fileType = sc.nextInt();

            if (fileType < 1 || fileType > 3) {
                System.out.println("There is no option less than 0 or greather than 4!\n");
                
            }

            switch (fileType) {
                case 1:
                    File fileRead = new File("./txts/" + fileReadName.toLowerCase() + ".txt");
                    returnsReader(sc, fileRead);
                    break;
                case 2:
                    File fileReadEncrypt = new File("./txts/encrypt/" + fileReadName.toLowerCase() + ".txt");
                    returnsReader(sc, fileReadEncrypt);
                    break;
                case 3:
                    File fileReadDecrypt = new File("./txts/decrypt/" + fileReadName.toLowerCase() + ".txt");
                    returnsReader(sc, fileReadDecrypt);
                    break;    
            }
        } catch(Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public static void encryptOrDecryptCode(Scanner sc, File fileToCrypt, String questionToCrypt) {
        try {
            if(!fileToCrypt.exists()) {
                System.out.println("\nSometing goes Wrong... :(\nFile doesn't exists. Please, create the file first!");
            } else {
                Scanner fileCryptRead = new Scanner(fileToCrypt);

                while(fileCryptRead.hasNextLine()) {
                    String data = fileCryptRead.nextLine();
                    while (true) {
                        System.out.println("\nWhat's the value of the " + questionToCrypt + " key?");
                        System.out.print("> ");
                        
                        if (sc.hasNextInt()) {
                            int cryptKey = sc.nextInt();
                            sc.nextLine();

                            if(questionToCrypt == "encrypt") {
                                createFile(sc, false, questionToCrypt, encrypt(data, cryptKey));
                            } else {
                                createFile(sc, false, questionToCrypt, decrypt(data, cryptKey));
                            }
                            
                            break;
                        } else {
                            System.out.println("ERROR: The value most be a number! >:(");
                            sc.nextLine();
                        }
                    }
                }
    
                fileCryptRead.close();
                
            }
        } catch (Exception e) {
            System.out.println("Error:" + e);
        }
    }

    public static void fileEncryptOrDecrypt(Scanner sc, boolean choice) {
        try {
            String questionToCrypt = (choice) ? "encrypt" : "decrypt";
            System.out.println("\nWhat's the name of the file to " + questionToCrypt);
            System.out.print("> ");
            String fileNameToCrypt = sc.nextLine();

            if(questionToCrypt == "encrypt") {
                File fileToCrypt = new File("./txts/" + fileNameToCrypt.toLowerCase() + ".txt");
                encryptOrDecryptCode(sc, fileToCrypt, questionToCrypt);
            } else {
                File fileToCrypt = new File("./txts/encrypt/" + fileNameToCrypt.toLowerCase() + ".txt");
                encryptOrDecryptCode(sc, fileToCrypt, questionToCrypt);
            } 
        } catch (Exception e) {
            System.out.println("Error:" + e);
        }
    }

    public static String encrypt(String msg, int searchValue) {
        char[] msgSliced = msg.toLowerCase().toCharArray();
        for (int i = 0; i < msgSliced.length; i++) {
            if (msgSliced[i] >= 'a' && msgSliced[i] <= 'z') {
                int newAbcPosition = ((msgSliced[i] - 'a') + searchValue) % 26;
                searchValue = msgSliced[i] - 'a';
        
                msgSliced[i] = (char) ('a' + newAbcPosition);
            }
        }

        return String.valueOf(msgSliced).substring(0, 1).toUpperCase() + String.valueOf(msgSliced).substring(1);
    }

    public static String decrypt(String msg, int searchValue) {
        char[] msgSliced = msg.toLowerCase().toCharArray();
        for (int i = 0; i < msgSliced.length; i++) {
            if (msgSliced[i] >= 'a' && msgSliced[i] <= 'z') {
                int originalAbcPosition = ((msgSliced[i] - 'a') - searchValue + 26) % 26;
                searchValue = originalAbcPosition;

                msgSliced[i] = (char) ('a' + originalAbcPosition);
            }
        }

        return String.valueOf(msgSliced).substring(0, 1).toUpperCase() + String.valueOf(msgSliced).substring(1);
    }

    public static void menu(Scanner sc) {
        while (true) {
            String[] drawMenu = {"┌────────────────────────┐", "│          Menu          │", "│                        │", "│  1 - Create File       │", "│  2 - Read File         │", "│  3 - Encrypt File      │", "│  4 - Decrypt File      │", "│  0 - Exit              │", "│                        │", "└────────────────────────┘"};
            for (String i : drawMenu) {
                System.out.println(i);
            }
    
            System.out.print("> ");
            int choiceMenu = sc.nextInt();
    
            if (choiceMenu < 0 || choiceMenu > 4) {
                System.out.println("There is no option less than 0 or greater than 4!\n");
                continue;
            }
    
            switch (choiceMenu) {
                case 0:
                    System.out.println("Exiting the program...");
                    return;
                case 1:
                    sc.nextLine();
                    createFile(sc, true, "", "");
                    break;
                case 2:
                    sc.nextLine();
                    readFile(sc);
                    break;
                case 3:
                    sc.nextLine();
                    fileEncryptOrDecrypt(sc, true);
                    break;
                case 4:
                    sc.nextLine();
                    fileEncryptOrDecrypt(sc, false);
                    break;
            }
        }
    }    

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        menu(sc);
        sc.close();
    }
}