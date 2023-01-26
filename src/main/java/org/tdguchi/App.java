package org.tdguchi;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.jtattoo.plaf.hifi.HiFiLookAndFeel;

import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Hello world!
 *
 */
public class App {

    private static List<client> clients = new ArrayList<client>();
    public static void main(String[] args) throws IOException, InterruptedException {
        try {
            UIManager.setLookAndFeel(new HiFiLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        client pablo = new client();

        client marta = new client();
        client papa = new client();

        clients.add(pablo);

        clients.add(marta);
        clients.add(papa);

        pablo.login();
        marta.login();
        papa.login();

        Timer timer = new Timer(300000, e -> {
            try {
                pablo.ping();
                marta.ping();
                papa.ping();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        timer.start();

        File file = new File("config.txt");
        int comentario = 0;
        if (file.createNewFile()) {
            try {
                FileWriter fw = new FileWriter("config.txt");
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(Integer.toString(comentario));
                bw.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                FileReader fr = new FileReader("config.txt");
                BufferedReader br = new BufferedReader(fr);
                String line;
                while ((line = br.readLine()) != null) {
                    comentario = Integer.parseInt(line);
                }
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        new interfaz(clients);

    }

}
