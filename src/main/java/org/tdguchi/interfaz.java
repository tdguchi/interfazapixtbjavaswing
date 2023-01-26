package org.tdguchi;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import net.miginfocom.swing.MigLayout;

public class interfaz extends JFrame {

    private List<client> clients;
    private List<trade> tradesList;
    private double sl = 0.0;
    private double tp = 0.0;
    private static final String indice = "US100";
    private static final double volumen = 0.02;
    private static JTextField tfsl;
    private static JTextField tftp;
    private static Double dttp;
    private static Double dssl;
    private static JPanel panel1;
    private static JLabel label15;
    private static JLabel label16;

    public interfaz(List<client> clients) {
        this.clients = clients;
        initComponents();
        try {
            mostrarTrades();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void cambiarchecked(int comment, boolean checked) {

        for (client client : clients) {
            for (trade trade : client.getTradesList()) {
                if (trade.getComment() == comment) {
                    trade.setChecked(checked);
                }
            }
        }

    }

    public void mostrarTrades() throws JsonMappingException, JsonProcessingException {
        panel1.removeAll();
        panel1.revalidate();
        panel1.repaint();
        for (client cliente : clients) {
            cliente.getTrades();
        }
        client client = clients.get(2);
        tradesList = client.getTrades();
        // recorre la lista, para cada trade añade los datos a las celdas del panel1
        Iterator<trade> it = tradesList.iterator();
        panel1.add(label15, "cell 2 0");
        panel1.add(label16, "wrap");
        while (it.hasNext()) {
            trade trade = it.next();
            trade.setChecked(false);
            int comment = trade.getComment();
            double sl = trade.getSl();
            double tp = trade.getTp();
            JCheckBox checkbox = new JCheckBox();
            panel1.add(checkbox);
            panel1.add(new JLabel(String.valueOf(comment)));
            panel1.add(new JLabel(String.valueOf(sl)));
            panel1.add(new JLabel(String.valueOf(tp)), "wrap");
            checkbox.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (checkbox.isSelected()) {
                        cambiarchecked(trade.getComment(), true);
                    } else {
                        cambiarchecked(trade.getComment(), false);
                    }
                }
            });
        }
        panel1.revalidate();
        panel1.repaint();

        revalidate();

        repaint();

    }

    private void initComponents() {
        JFrame frame = new JFrame();
        JButton largo = new JButton();
        JButton corto = new JButton();
        panel1 = new JPanel();
        label15 = new JLabel();
        label16 = new JLabel();
        JButton cerrar = new JButton();
        JButton actualizar = new JButton();
        JPanel panel3 = new JPanel();
        JLabel label10 = new JLabel();
        tfsl = new JTextField();
        JLabel label11 = new JLabel();
        tftp = new JTextField();
        JButton modificar = new JButton();

        {
            frame.setLayout(new MigLayout(
                    "fill,hidemode 3,align trailing center",
                    // columns
                    "[200,fill]" +
                            "[30,fill]",
                    // rows
                    "[30]" +
                            "[30]" +
                            "[130]"));
            // ---- largo ----
/*             largo.setIcon(new ImageIcon("ki85a7eBT_64x64.png"));
 */            ImageIcon icon = new ImageIcon(this.getClass().getResource("/ki85a7eBT_64x64.png"));
            largo.setIcon(icon);
            largo.setPreferredSize(new Dimension(50, 50));
            frame.add(largo, "cell 0 0 2 0,growy");

            // escribe el action listener para el boton largo
            largo.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    try {
                        largoActionPerformed(evt);
                    } catch (JsonProcessingException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            });

            // ---- corto ----
            ImageIcon icon2 = new ImageIcon(this.getClass().getResource("/arrow-157087_960_720_64x64.png"));
            corto.setIcon(icon2);
            frame.add(corto, "cell 0 1 2 1,growy");

            // escribe el action listener para el boton corto

            corto.addActionListener(new ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    try {
                        cortoActionPerformed(evt);
                    } catch (JsonProcessingException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            {
                panel1.setLayout(new MigLayout(
                        "fill,hidemode 3",
                        // columns
                        "[fill]" +
                                "[fill]" +
                                "[fill]" +
                                "[fill]",
                        // rows
                        "[60]" +
                                "[60]" +
                                "[60]" +
                                "[60]"));

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setTitle("A Invertir");
                frame.setLocationRelativeTo(null);

                label15.setText("stop");
                panel1.add(label15, "cell 3 0,alignx center,growx 0");

                // ---- label16 ----
                label16.setText("profit");
                panel1.add(label16, "cell 4 0,alignx center,growx 0");
            }
            frame.add(panel1, "cell 0 2,grow");
        }

        // ---- cerrar ----
        cerrar.setText("Cerrar");
        frame.add(cerrar, "cell 1 2,growy, alignx right, growx 0");

        // escribe el action listener para el boton cerrar

        cerrar.addActionListener(new ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cerrarActionPerformed(evt);
            }
        });

        {
            panel3.setLayout(new MigLayout(
                    "hidemode 3",
                    // columns
                    "[5,fill]" +
                            "[5,fill]",
                    // rows
                    "[]"));
            // ---- label10 ----
            label10.setText("sl:");
            panel3.add(label10, "cell 0 0,alignx center");

            // ---- tfsl ----
            tfsl.setText("0.0");
            panel3.add(tfsl, "");

            // ---- label11 ----
            label11.setText("tp:");
            panel3.add(label11, "cell 0 1,alignx center");

            // ---- tftp ----
            tftp.setText("0.0");
            panel3.add(tftp, "cell 1 1");

            // ---- modificar ----
            modificar.setText("Modificar");

            // escribe el action listener para el boton modificar

            modificar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    modificarActionPerformed(evt);
                }
            });

            panel3.add(modificar, "cell 1 2");
        }

        actualizar.setText("Actualizar");

        // escribe el action listener para el boton actualizar

        actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actualizarActionPerformed(evt);
            }
        });

        frame.add(panel3, "cell 0 3");
        frame.add(actualizar , "cell 1 3,alignx right, growx 0");
        frame.pack();
        frame.setLocationRelativeTo(frame.getOwner());

        frame.setVisible(true);

    }

    protected void actualizarActionPerformed(ActionEvent evt) {
        try {
            mostrarTrades();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    protected void modificarActionPerformed(ActionEvent evt) {
        try {
            String ssl = tfsl.getText();
            String ttp = tftp.getText();
            if (ssl == "0.0" || ssl.isEmpty()) {
                dssl = 0.0;
            } else {
                dssl = Double.parseDouble(ssl);
            }
            if (ttp == "0.0" || ttp.isEmpty()) {
                dttp = 0.0;
            } else {
                dttp = Double.parseDouble(ttp);
            }
            for (client client : clients) {
                Thread hilo = new Thread() {
                    public void run() {
                        try {
                            List<trade> trades = client.getTradesList();
                            Iterator<trade> iterator = trades.iterator();
                            while (iterator.hasNext()) {
                                trade trade = iterator.next();
                                if (dssl != 0.0) {
                                    sl = dssl;
                                } else {
                                    sl = trade.getSl();
                                }
                                if (dttp != 0.0) {
                                    tp = dttp;
                                } else {
                                    tp = trade.getTp();
                                }
                                if (trade.getChecked()) {
                                    client.TradeTransaction(trade.getCmd(), trade.getPosition(), indice, 3, volumen,
                                            sl, tp, trade.getComment());
                                Thread.sleep(100);
                                }
                            }
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                hilo.start();
                hilo.join();
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            Thread.sleep(3000);
            mostrarTrades();
        } catch (JsonProcessingException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void cerrarActionPerformed(ActionEvent evt) {
        try {
            for (client client : clients) {
                Thread hilo = new Thread() {
                    public void run() {
                        try {
                            List<trade> trades = client.getTradesList();
                            Iterator<trade> iterator = trades.iterator();
                            while (iterator.hasNext()) {
                                trade trade = iterator.next();
                                    if (trade.getChecked()) {
                                    client.TradeTransaction(trade.getCmd(), trade.getPosition(), indice, 2, volumen,
                                            trade.getSl(), trade.getTp(), trade.getComment());
                                Thread.sleep(100);
                                }
                            }
                        } catch (IOException  e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                hilo.start();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            Thread.sleep(3000);
            mostrarTrades();
        } catch (JsonProcessingException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void cortoActionPerformed(java.awt.event.ActionEvent evt)
            throws JsonMappingException, JsonProcessingException, InterruptedException {// GEN-FIRST:event_cortoActionPerformed
        confirmar dialog = new confirmar(new javax.swing.JFrame(), true, clients, 1);
        dialog.setAlwaysOnTop(true);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        Thread.sleep(1000);
        mostrarTrades();
    }

    private void largoActionPerformed(java.awt.event.ActionEvent evt) // GEN-FIRST:event_largoActionPerformed
            throws JsonMappingException, JsonProcessingException, InterruptedException {// GEN-FIRST:event_cortoActionPerformed
        confirmar dialog = new confirmar(new javax.swing.JFrame(), true, clients, 0);
        dialog.setAlwaysOnTop(true);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        Thread.sleep(1000);
        mostrarTrades();
    }
}
