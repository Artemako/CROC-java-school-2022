package src.ru.croc.tasks.task11;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

class ClientCommunication {
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private BufferedReader reader;
    private String address;
    private int port;
    private String nickname;
    private Date time;
    private String hhmmssTime;

    private Thread threadReadMessages;
    private Thread threadWriteMessages;

    public ClientCommunication(String address, int port) {
        this.address = address;
        this.port = port;
        try {
            this.socket = new Socket(address, port);
        } catch (IOException e) {
            System.out.println("Ошибка при подключении сокета.");
        }
        try {

            reader = new BufferedReader(new InputStreamReader(System.in));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            threadReadMessages = new Thread(new ReadMessages());
            threadWriteMessages = new Thread(new WriteMessages());

            System.out.print("Введите никнейм: ");
            try {
                nickname = reader.readLine();
                //System.out.println("Привет, " + nickname + ".\n");
                out.write("Привет, " + nickname + ".\n");
                out.flush();
            } catch (IOException ignored) {
            }


        } catch (Exception e) {
            ClientCommunication.this.downClient();
        }
    }

    public void startThreads() {
        //System.out.println("startThreads");
        threadReadMessages.start();
        threadWriteMessages.start();
    }

    private void downClient() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
            }
        } catch (IOException ignored) {}
    }

    private class ReadMessages implements Runnable {
        @Override
        public void run() {
            String message;
            try {
                //System.out.println(socket.isConnected());
                while (true) {
                    message = in.readLine();
                    //System.out.println(message);
                    if (message.equals("logout")) {
                        System.out.println("Есть logout");
                        ClientCommunication.this.downClient();
                        break;
                    }
                    System.out.println(message);
                }
            } catch (IOException e) {
                ClientCommunication.this.downClient();
            }
        }
    }

    public class WriteMessages implements Runnable {
        @Override
        public void run() {
            while (true) {
                String message;
                try {
                    time = new Date();
                    hhmmssTime = new SimpleDateFormat("HH:mm:ss").format(time);
                    message = reader.readLine();
                    if (message.equals("logout")) {
                        out.write("logout" + "\n");
                        ClientCommunication.this.downClient();
                        break;
                    } else {
                        out.write("[" + hhmmssTime + "] " + nickname + ": " + message + "\n");
                    }
                    out.flush();
                } catch (IOException e) {
                    ClientCommunication.this.downClient();
                }

            }
        }
    }


}