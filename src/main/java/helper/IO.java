package helper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class IO {
    public static Object getEnumByInput(String inputMessage, Object[] values) {
        System.out.println(inputMessage);
        while (true) {
            for (Object type : values)
                System.out.println("[" + type + "]");
            try {
                String inputType = new BufferedReader(new InputStreamReader(System.in)).readLine();
                for (Object type : values)
                    if (type.toString().equals(inputType))
                        return type;
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Invalid");
        }
    }

    public static String readInputFor(String inputMessage) {
        try {
            System.out.println(inputMessage);
            return new BufferedReader(new InputStreamReader(System.in)).readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String readFromInputStream(InputStream inputStream) {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null)
                resultStringBuilder
                        .append(line)
                        .append("\n");
        } catch (IOException e) {
            throw new RuntimeException();
        }

        return resultStringBuilder.toString();
    }


    public static void persistString(String output, String fileName, String foldername) {
        if (!pathExists(foldername))
            createFolder(foldername);
        try (java.io.FileWriter file = new java.io.FileWriter(foldername + "/" + fileName)) {
            file.write(output);
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean pathExists(String path) {
        return Files.exists(Paths.get(path));
    }

    public static void createFolder(String foldername) {
        String path = "";
        String[] folders = foldername.split("/");
        for (String folderpath : folders) {
            path += folderpath + "/";
            new File(path).mkdir();
        }
    }

    public static String getPersistedString(String filename, String folder) {
        String folderpath = (folder + "/").replace("//", "");
        try {
            return IO.readFromInputStream(new FileInputStream(new File(folderpath + filename)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        }
    }
}
