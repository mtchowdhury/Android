package com.example.tycohanx.emsv2.DataBase;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

public class CSVWriter {
    public static final char DEFAULT_ESCAPE_CHARACTER = '\"';
    public static final String DEFAULT_LINE_END = "\n";
    public static final char DEFAULT_QUOTE_CHARACTER = '\"';
    public static final char DEFAULT_SEPARATOR = ',';
    public static final char NO_ESCAPE_CHARACTER = '\u0000';
    public static final char NO_QUOTE_CHARACTER = '\u0000';
    private char escapechar;
    private String lineEnd;
    private PrintWriter pw;
    private char quotechar;
    private char separator;

    public CSVWriter(Writer writer) {
        this(writer, ',', '\"', '\"', DEFAULT_LINE_END);
    }

    public CSVWriter(Writer writer, char separator, char quotechar, char escapechar, String lineEnd) {
        this.pw = new PrintWriter(writer);
        this.separator = separator;
        this.quotechar = quotechar;
        this.escapechar = escapechar;
        this.lineEnd = lineEnd;
    }

    public void writeNext(String[] nextLine) {
        if (nextLine != null) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < nextLine.length; i++) {
                if (i != 0) {
                    sb.append(this.separator);
                }
                String nextElement = nextLine[i];
                if (nextElement != null) {
                    if (this.quotechar != '\u0000') {
                        sb.append(this.quotechar);
                    }
                    for (int j = 0; j < nextElement.length(); j++) {
                        char nextChar = nextElement.charAt(j);
                        if (this.escapechar != '\u0000' && nextChar == this.quotechar) {
                            sb.append(this.escapechar).append(nextChar);
                        } else if (this.escapechar == '\u0000' || nextChar != this.escapechar) {
                            sb.append(nextChar);
                        } else {
                            sb.append(this.escapechar).append(nextChar);
                        }
                    }
                    if (this.quotechar != '\u0000') {
                        sb.append(this.quotechar);
                    }
                }
            }
            sb.append(this.lineEnd);
            this.pw.write(sb.toString());
        }
    }

    public void flush() throws IOException {
        this.pw.flush();
    }

    public void close() throws IOException {
        this.pw.flush();
        this.pw.close();
    }
}
