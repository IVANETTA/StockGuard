package vista;

import javax.swing.*;
import java.awt.*;

//creamos la clase hija Dashboard con su clase padre JFrame
public class Dashboard extends JFrame {
    
    // constructor que recibe quién inició sesión y qué cargo tiene
    public Dashboard(String nombreUsuario, String rol) {
    	
        //configuración de la ventana
        this.setTitle("StockGuard - Panel Principal | " + rol);    
        this.setSize(800, 600); 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(10, 10)); 

        //mensaje de bienvenida 
        JLabel lblBienvenida = new JLabel("¡Bienvenido/a a StockGuard, seleccione una opción " + nombreUsuario + "!", SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 24));
        this.add(lblBienvenida, BorderLayout.CENTER);

        //armamos el menú inferior dependiendo del cargo
        armarMenuPorRol(rol);
    }

    // metodo privado para diferenciar el menu segun el rol
    private void armarMenuPorRol(String rol) {
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
        
        //configuramos los botones para empleados
        if (rol.equals("Empleado")) {
        	
            //btn para nueva venta vista
            JButton btnNuevaVenta = new JButton("NUEVA VENTA");
            btnNuevaVenta.setFont(new Font("Arial", Font.BOLD, 16));
            btnNuevaVenta.setBackground(new Color(50, 205, 50));
            btnNuevaVenta.setForeground(Color.WHITE);
            //btn nueva venta comportamiento
            btnNuevaVenta.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    
                    PantallaNuevaVenta ventanaVenta = new PantallaNuevaVenta();
                    ventanaVenta.setVisible(true);
                }
            });
            
            //btn para fichero y cobros vista
            JButton btnFichero = new JButton("FICHERO Y COBROS");
            btnFichero.setFont(new Font("Arial", Font.BOLD, 16));
            btnFichero.setBackground(new Color(50, 205, 50));
            btnFichero.setForeground(Color.WHITE);
            // btn fichero comportamiento
            btnFichero.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    PantallaFichero ventanaFichero = new PantallaFichero();
                    ventanaFichero.setVisible(true);
                }
            });
            
            //agregamos los boones creados
            panelInferior.add(btnNuevaVenta);
            panelInferior.add(btnFichero);
            
            
            //configuramos los botones para dministrador
        } else if (rol.equals("Administrador")) {
        	
        	//configuramos la vista de boton inventario
            JButton btnInventario = new JButton("Gestionar Stock");
            btnInventario.setFont(new Font("Arial", Font.BOLD, 14));
            btnInventario.setBackground(new Color(47, 79, 79)); 
            btnInventario.setForeground(Color.WHITE);
            //config comportamiento de btn inventario
            btnInventario.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    PantallaInventario ventanaAdmin = new PantallaInventario();
                    ventanaAdmin.setVisible(true);
                }
            });
            
            //botono control de horarios vista
            JButton btnHorarios = new JButton("Control de Horarios");
            btnHorarios.setFont(new Font("Arial", Font.BOLD, 14));
            btnHorarios.setBackground(new Color(47, 79, 79));
            btnHorarios.setForeground(Color.WHITE);
            //comportamiento de btn control de horarios
            btnHorarios.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    PantallaHorarios ventanaHorarios = new PantallaHorarios();
                    ventanaHorarios.setVisible(true);
                }
            });
            
            //boton estadisticas vista
            JButton btnEstadisticas = new JButton("Estadísticas");
            btnEstadisticas.setFont(new Font("Arial", Font.BOLD, 14));
            btnEstadisticas.setBackground(new Color(47, 79, 79));
            btnEstadisticas.setForeground(Color.WHITE);            
            //COMPORTAMIENTO DE BTN ESTADISTICAS          
            btnEstadisticas.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    PantallaEstadisticas ventanaStats = new PantallaEstadisticas();
                    ventanaStats.setVisible(true);
                }
            });
            
            //vista de btn fihcero de clientes
            JButton btnFicheroAdmin = new JButton("Fichero de Clientes");
            btnFicheroAdmin.setFont(new Font("Arial", Font.BOLD, 14));
            btnFicheroAdmin.setBackground(new Color(47, 79, 79)); 
            btnFicheroAdmin.setForeground(Color.WHITE);
            //comportamineto btn fichero
            btnFicheroAdmin.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    PantallaFichero ventanaFichero = new PantallaFichero();
                    ventanaFichero.setVisible(true);
                }
            });
            //agrego los botones
            panelInferior.add(btnInventario);
            panelInferior.add(btnEstadisticas);
            panelInferior.add(btnFicheroAdmin);
            panelInferior.add(btnHorarios);

        }
        //agregamos el panel de botones a la parte de abajo de la ventana
        this.add(panelInferior, BorderLayout.SOUTH);
    }
}
