package vista;

import javax.swing.*;
import java.awt.*;

public class PantallaFichero extends JFrame {
    
    private JTextArea txtLibreta;
    private JButton btnActualizar;
    private JButton btnRegistrarPago;
    private JTextField txtBuscar;
    private JButton btnBuscar;

    public PantallaFichero() {
        // Configuración de la ventana
        this.setTitle("StockGuard - Libreta de Clientes");
        this.setSize(600, 450); 
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(10, 10));

        //panel superior
        JPanel panelSuperior = new JPanel(new BorderLayout());
        
        JLabel lblTitulo = new JLabel("Fichero de Deudores y Créditos", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        panelSuperior.add(lblTitulo, BorderLayout.NORTH);

        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBusqueda.add(new JLabel("Buscar (DNI o Apellido):"));
        
        txtBuscar = new JTextField(15);
        panelBusqueda.add(txtBuscar);
        
        btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(new Color(105, 105, 105));
        btnBuscar.setForeground(Color.WHITE);
        panelBusqueda.add(btnBuscar);

        panelSuperior.add(panelBusqueda, BorderLayout.SOUTH);
        this.add(panelSuperior, BorderLayout.NORTH);

        //damos instrucciones
        txtLibreta = new JTextArea("Presione 'Actualizar Lista' o busque un cliente...");
        txtLibreta.setEditable(false);
        txtLibreta.setFont(new Font("Monospaced", Font.PLAIN, 14)); 
        this.add(new JScrollPane(txtLibreta), BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new FlowLayout());
        
        btnActualizar = new JButton("Ver Todos (Actualizar)");
        btnActualizar.setBackground(new Color(70, 130, 180));
        btnActualizar.setForeground(Color.WHITE);
        
        btnRegistrarPago = new JButton("Registrar Pago / Deuda");
        btnRegistrarPago.setBackground(new Color(255, 140, 0)); 
        btnRegistrarPago.setForeground(Color.WHITE);

        panelInferior.add(btnActualizar);
        panelInferior.add(btnRegistrarPago);
        
        this.add(panelInferior, BorderLayout.SOUTH);

        
        //comportamiento del btn ver todos
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                String datos = modelo.GestorArchivos.obtenerListaClientes();
                txtLibreta.setText(datos);
                txtBuscar.setText(""); // Limpiamos la cajita de búsqueda
            }
        });
        
        //boton buscar
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                String criterio = txtBuscar.getText().trim();
                
                if (criterio.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, escriba un DNI o Apellido.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String resultado = modelo.GestorArchivos.buscarCliente(criterio);
                txtLibreta.setText(resultado);
            }
        });
     //comportamiento del btn registrar pago
        btnRegistrarPago.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                // 1. Pedimos el DNI mediante una ventana emergente
                String dni = JOptionPane.showInputDialog(null, "Ingrese el DNI del cliente:", "Actualizar Libreta", JOptionPane.QUESTION_MESSAGE);
                if (dni == null || dni.trim().isEmpty()) return; 
                Object[] opciones = {"Registrar Pago (Resta)", "Sumar Nueva Deuda"};
                int seleccion = JOptionPane.showOptionDialog(null, "¿Qué operación desea realizar el cliente?", "Tipo de Operación",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);
                
                if (seleccion == -1) return;
                boolean esPago = (seleccion == 0);
                
                String montoStr = JOptionPane.showInputDialog(null, "Ingrese el monto ($):", "Ingreso de Dinero", JOptionPane.QUESTION_MESSAGE);
                if (montoStr == null || montoStr.trim().isEmpty()) return;

                try {
                    double monto = Double.parseDouble(montoStr.replace(",", "."));

                    boolean exito = modelo.GestorArchivos.actualizarDeudaCliente(dni.trim(), monto, esPago);
                    
                    if (exito) {
                        JOptionPane.showMessageDialog(null, "✅ ¡La libreta se actualizó correctamente!");
                        txtLibreta.setText(modelo.GestorArchivos.obtenerListaClientes());
                    } else {
                        JOptionPane.showMessageDialog(null, "❌ Error: No se pudo actualizar. Verifique que el DNI ingresado exista.", "Operación fallida", JOptionPane.ERROR_MESSAGE);
                    }
                    
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "El monto ingresado debe ser numérico.", "Error de tipeo", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}