package logic;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection {

    private final Socket socket;
    private final BufferedReader reader;
    private final PrintWriter writer;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream());
        writer.flush();
    }

    public Deck readDeck() throws IOException {
        String str;
        do{
            str = reader.readLine();
        }while (str == null || str.isEmpty());
        return new Gson().fromJson(str, Deck.class);
    }

    public void sendDeck(Deck deck){
        writer.println(new Gson().toJson(deck));
        writer.print("\n");
        writer.flush();
    }

    public int readInt() throws IOException {
        String str;
        do{
            str = reader.readLine();
        }while (str == null || str.isEmpty());
        return new Gson().fromJson(str, Integer.class);
    }

    public void sendInt(int i){
        writer.println(new Gson().toJson(i));
        writer.print("\n");
        writer.flush();
    }
}
