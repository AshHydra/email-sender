package utb.fai;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class EmailSender {
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    /*
     * Constructor opens Socket to host/port. If the Socket throws an exception
     * during opening, the exception is not handled in the constructor.
     */
    public EmailSender(String host, int port) throws UnknownHostException, IOException {
        socket = new Socket(host, port);
        writer = new PrintWriter(socket.getOutputStream(), true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Read the server's initial response (SMTP greeting)
        System.out.println(reader.readLine());
    }

    /*
     * Sends email from an email address to an email address with some subject and
     * text. If the Socket throws an exception during sending, the exception is not
     * handled by this method.
     */
    public void send(String from, String to, String subject, String text) throws IOException {
        // Send HELO command
        writer.println("HELO " + InetAddress.getLocalHost().getHostName());
        System.out.println(reader.readLine());

        // Send MAIL FROM command
        writer.println("MAIL FROM:<" + from + ">");
        System.out.println(reader.readLine());

        // Send RCPT TO command
        writer.println("RCPT TO:<" + to + ">");
        System.out.println(reader.readLine());

        // Send DATA command
        writer.println("DATA");
        System.out.println(reader.readLine());

        // Send email data (headers and body)
        writer.println("Subject: " + subject);
        writer.println("From: " + from);
        writer.println("To: " + to);
        writer.println(); // Blank line between headers and body
        writer.println(text);
        writer.println("."); // End email data with a dot on a single line
        System.out.println(reader.readLine());
    }

    /*
     * Sends QUIT and closes the socket
     */
    public void close() throws IOException {
        writer.println("QUIT");
        System.out.println(reader.readLine());

        writer.close();
        reader.close();
        socket.close();
    }
}
