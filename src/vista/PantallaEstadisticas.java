package vista;

import javax.swing.*;
import java.awt.*;

public class PantallaEstadisticas extends JFrame {
    
	
    private JLabel lblTotalRecaudado;
    private JTextArea txtDetalleVentas;
    private JButton btnGenerarReporte;

    public PantallaEstadisticas() {
        // configuramos la ventana
        this.setTitle("StockGuard - Estadísticas Financieras");
        this.setSize(650, 500); 
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(15, 15));
        JLabel lblTitulo = new JLabel("Reporte General de Ventas", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        this.add(lblTitulo, BorderLayout.NORTH);

        //configuramos panel del centro
        txtDetalleVentas = new JTextArea("Presione 'Generar Reporte' para calcular la facturación...");
        txtDetalleVentas.setEditable(false);
        txtDetalleVentas.setFont(new Font("Monospaced", Font.PLAIN, 14));
        // Agregamos un borde 
        txtDetalleVentas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(new JScrollPane(txtDetalleVentas), BorderLayout.CENTER);

        //configuramos oanel inferior
        JPanel panelInferior = new JPanel(new BorderLayout(10, 10));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        
        //configuramos lbl inicial
        lblTotalRecaudado = new JLabel("Ingresos Totales: $ 0.00", SwingConstants.CENTER);
        lblTotalRecaudado.setFont(new Font("Arial", Font.BOLD, 24));
        lblTotalRecaudado.setForeground(new Color(34, 139, 34)); 
        panelInferior.add(lblTotalRecaudado, BorderLayout.NORTH);

        //configuramos los botones
        btnGenerarReporte = new JButton("Generar Reporte de Ventas");
        btnGenerarReporte.setFont(new Font("Arial", Font.BOLD, 14));
        btnGenerarReporte.setBackground(new Color(70, 130, 180));
        btnGenerarReporte.setForeground(Color.WHITE);
        panelInferior.add(btnGenerarReporte, BorderLayout.SOUTH);
        
        this.add(panelInferior, BorderLayout.SOUTH);
        
        btnGenerarReporte.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
        
                String detalle = modelo.GestorArchivos.obtenerDetalleVentas();
                txtDetalleVentas.setText(detalle);

                double total = modelo.GestorArchivos.calcularTotalRecaudado();

                lblTotalRecaudado.setText(String.format("Ingresos Totales: $ %.2f", total));
            }
        });

    }
    
}