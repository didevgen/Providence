package rest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class ErrorMessageHelper {
    public static String getExceptionDetails(Throwable exception) {
        if (exception == null) {
            return null;
        }

        StringBuilder details = new StringBuilder();
        Writer result = new StringWriter();
        PrintWriter pw = new PrintWriter(result);
        exception.printStackTrace(pw);
        pw.close();
        details.append(result.toString());

        return details.toString();
    }
}
