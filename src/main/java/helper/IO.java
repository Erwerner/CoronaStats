package helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IO {
    public static Object getEnumByInput(String inputMessage, Object[] values) {
        System.out.println(inputMessage);
        Object typeSelection = null;
        while (typeSelection == null) {
            for (Object type : values)
                System.out.println(type);
            String inputType = null;
            try {
                inputType = new BufferedReader(new InputStreamReader(System.in)).readLine();
                for (Object type : values)
                    if (type.toString().equals(inputType)) {
                        typeSelection = type;
                        continue;
                    }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Invalid");
        }

        return typeSelection;
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
