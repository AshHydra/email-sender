package utb.fai;

public class App {

    public static void main(String[] args) {
        // Kontrola, zda byly předány všechny parametry
        if (args.length < 6) {
            System.out.println("Usage: java App <smtp_server> <port> <from_email> <to_email> <subject> <message>");
            return;
        }

        // Získání parametrů ze vstupního pole args
        String smtpServer = args[0];
        int port = Integer.parseInt(args[1]);
        String from = args[2];
        String to = args[3];
        String subject = args[4];
        String message = args[5];

        // Odeslání emailu
        try {
            EmailSender sender = new EmailSender(smtpServer, port);
            sender.send(from, to, subject, message);
            sender.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}