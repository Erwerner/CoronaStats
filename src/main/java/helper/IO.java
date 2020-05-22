package helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
}
