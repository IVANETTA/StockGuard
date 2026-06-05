package vista;

import javax.swing.*;
import java.awt.*;

public class PantallaHorarios extends JFrame {
    
    private JTextArea txtRegistro;
    private JButton btnActualizar;

    public PantallaHorarios() {
        // Configuración de la ventana
        this.setTitle("StockGuard - Auditoría de Horarios");
        java.awt.Image icono = java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/icono.png"));
        this.setIconImage(icono);
        this.setSize(600, 450); 
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(10, 10));

        //panel sup
        JPanel panelSuperior = new JPanel(new BorderLayout());
        
        JLabel lblTitulo = new JLabel("Control de Asistencia y Turnos", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        panelSuperior.add(lblTitulo, BorderLayout.NORTH);
        
        JLabel lblSubtitulo = new JLabel("Registro histórico de conexiones de empleados", SwingConstants.CENTER);
        lblSubtitulo.setForeground(Color.GRAY);
        panelSuperior.add(lblSubtitulo, BorderLayout.SOUTH);
        
        this.add(panelSuperior, BorderLayout.NORTH);
        

        //panel central
        txtRegistro = new JTextArea("Presione 'Actualizar Registro' para ver las conexiones...");
        txtRegistro.setEditable(false);
        txtRegistro.setFont(new Font("Monospaced", Font.PLAIN, 14));
        this.add(new JScrollPane(txtRegistro), BorderLayout.CENTER);


        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        //panel inferior
        
        btnActualizar = new JButton("Actualizar Registro");
        btnActualizar.setBackground(new Color(70, 130, 180));
        btnActualizar.setForeground(Color.WHITE);
        panelInferior.add(btnActualizar);
        
        this.add(panelInferior, BorderLayout.SOUTH);
        
        
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                // Llamamos al Gestor para que lea el archivo asistencia.txt
                String historial = modelo.GestorArchivos.obtenerRegistroAsistencia();
                txtRegistro.setText(historial);
            }
        });
      
    }
}