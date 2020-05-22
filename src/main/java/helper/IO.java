package helper;

import java.io.BufferedReader;
import java.io.IOException;
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
}
