package server;

import logic.Connection;
import logic.Game;
import logic.exceptions.GameOverException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            System.out.println("Ожидаем второго игрока");
            ServerSocket ss = new ServerSocket(54321);
            Socket s = ss.accept();
            Game g = new Game(new Connection(s));
            g.myMove();
            g.run();
        } catch (IOException | GameOverException e) {
            e.printStackTrace();
        }
    }
}
