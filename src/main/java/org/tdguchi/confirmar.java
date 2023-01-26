package org.tdguchi;


import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class confirmar extends javax.swing.JDialog {

    private List<client> clients;
    private int cmd;
    private final String indice = "US100";
    private final double volumen = 0.02;
    private final double sl = 30.0;
    private final double tp = 0.0;
    private int comentario = 0;    

    public confirmar(java.awt.Frame parent, boolean modal, List<client> clients, int cmd) {
        super(parent, modal);
        this.clients = clients;
        this.cmd = cmd;
        initComponents();
    }

    public void operacion(int cmd) throws NumberFormatException, IOException {
        FileReader fr = new FileReader("config.txt");
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine()) != null) {
            comentario = Integer.parseInt(line);
        }
        br.close();
        fr.close();
        
        try {
            List<Thread> hilos = new ArrayList<>();
            for (client client : clients) {
                Thread hilo = new Thread() {
                    public void run() {
                        try {
                            client.TradeTransaction(cmd, 0, indice, 0, volumen, sl, tp, comentario);
                        } catch (NumberFormatException ex) {
                            Logger.getLogger(confirmar.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(confirmar.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                };
                hilo.start();
                hilos.add(hilo);
            }
            for (Thread hilo : hilos) {
                hilo.join();
            }
            comentario = comentario + 1;
            FileOutputStream fos = new FileOutputStream("config.txt");
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            osw.write(Integer.toString(comentario));
            osw.close();
            fos.close();

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    private void initComponents() {

        si = new javax.swing.JButton();
        no = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);

        si.setText("SI");
        si.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                siActionPerformed(evt);
            }
        });

        no.setText("NO");
        no.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noActionPerformed(evt);
            }
        });

        jLabel1.setText("Vas a realizar una operación en el Nasdaq, estás de acuerdo?");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(no, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(62, 62, 62)
                        .addComponent(si, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(42, 42, 42))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(no, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(si, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void siActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_siActionPerformed
        try {
            operacion(cmd);
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
        this.dispose();
    }// GEN-LAST:event_siActionPerformed

    private void noActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_noActionPerformed
        this.dispose();

    }// GEN-LAST:event_noActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton no;
    private javax.swing.JButton si;
    // End of variables declaration//GEN-END:variables
}