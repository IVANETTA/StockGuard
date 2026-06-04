package vista;

import javax.swing.*;
import java.awt.*;

public class PantallaNuevaVenta extends JFrame {
    

    private JTextField txtIdArticulo;
    private JTextArea txtDescripcion;
    private JComboBox<String> comboPago;
    private JTextField txtEntrega;
    private JButton btnBuscar;
    private JButton btnConfirmar;

    public PantallaNuevaVenta() {

        this.setTitle("StockGuard - Registrar Nueva Venta");
        this.setSize(500, 400);
        this.setLocationRelativeTo(null);
   
        this.setLayout(new GridLayout(6, 2, 10, 10)); 


        this.add(new JLabel(" ID del Artículo a vender:"));
        txtIdArticulo = new JTextField();
        this.add(txtIdArticulo);

        
        this.add(new JLabel("")); 
        btnBuscar = new JButton("Buscar Artículo");
        btnBuscar.setBackground(new Color(70, 130, 180));
        btnBuscar.setForeground(Color.WHITE);
        this.add(btnBuscar);

     
        this.add(new JLabel(" Descripción y Precio:"));
        txtDescripcion = new JTextArea("Esperando artículo...");
        txtDescripcion.setEditable(false); 
        this.add(new JScrollPane(txtDescripcion));

     
        this.add(new JLabel(" Forma de Pago:"));
        String[] metodos = {"CONTADO", "TRANSFERENCIA", "CREDITO PERSONAL", "TARJETA DE CREDITO", "TARJETA DE DEBITO"};
        comboPago = new JComboBox<>(metodos);
        this.add(comboPago);


        this.add(new JLabel(" Monto de Entrega ($):"));
        txtEntrega = new JTextField("0.0");
        this.add(txtEntrega);


        this.add(new JLabel("")); 
        btnConfirmar = new JButton("CONFIRMAR VENTA");
        btnConfirmar.setBackground(new Color(50, 205, 50));
        btnConfirmar.setForeground(Color.WHITE);
        this.add(btnConfirmar);
        

        this.btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                String idIngresado = txtIdArticulo.getText().trim();

                if (idIngresado.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un ID para buscar.", "Error de búsqueda", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String resultadoBusqueda = modelo.GestorArchivos.buscarArticulo(idIngresado);

                if (resultadoBusqueda != null) {
                    txtDescripcion.setText(resultadoBusqueda);

                    String metodoActual = (String) comboPago.getSelectedItem();
                    if (!metodoActual.equals("CREDITO PERSONAL")) {
                        double precio = modelo.GestorArchivos.obtenerPrecioArticulo(idIngresado);
                        txtEntrega.setText(String.valueOf(precio));
                    } else {
                        txtEntrega.setText("0.0"); 
                    }
                
                } else {
                    txtDescripcion.setText("❌ Artículo no encontrado en el inventario.");
                    txtEntrega.setText("0.0");
                }
            }
        });

        
        this.btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                //vemos que el art exista
                if (txtDescripcion.getText().contains("Esperando") || txtDescripcion.getText().contains("❌")) {
                    JOptionPane.showMessageDialog(null, "Debe buscar y cargar un artículo válido antes de confirmar la venta.", "Caja - Error", JOptionPane.WARNING_MESSAGE);
                    return; 
                }

   
                double montoEntrega = 0.0; 
                String metodoPago = (String) comboPago.getSelectedItem();
     
                try {
                 
                    String textoDinero = txtEntrega.getText().replace(",", ".");
                    montoEntrega = Double.parseDouble(textoDinero);
                } catch (NumberFormatException excepcion) {
                    JOptionPane.showMessageDialog(null, "El monto de entrega debe ser un número válido.", "Error de tipeo", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String idVendido = txtIdArticulo.getText();
                boolean exito = modelo.GestorArchivos.registrarVenta(idVendido, metodoPago, montoEntrega);

                if (exito) {

                    modelo.GestorArchivos.actualizarStockArticulo(idVendido, 1);
                    //mostramos confirmacion de venta
                    String mensajeCaja = "✅ ¡Venta registrada exitosamente en el sistema!\n\n"
                                       + "Método de Pago: " + metodoPago + "\n";
                    
                    if (metodoPago.equals("CREDITO PERSONAL")) {
                        mensajeCaja += "Se registró una entrega de: $ " + montoEntrega + "\n"
                                     + "El resto se sumará a la deuda del cliente en el Fichero.";
                    }
    
                    JOptionPane.showMessageDialog(null, mensajeCaja, "StockGuard - Ticket", JOptionPane.INFORMATION_MESSAGE);

                    txtIdArticulo.setText("");
                    txtDescripcion.setText("Esperando artículo...");
                    comboPago.setSelectedIndex(0); 
                    txtEntrega.setText("0.0");
                } else {
                    JOptionPane.showMessageDialog(null, "Error grave: No se pudo guardar la venta en el archivo.", "Fallo de Sistema", JOptionPane.ERROR_MESSAGE);
                }

             
                txtIdArticulo.setText("");
                txtDescripcion.setText("Esperando artículo...");
                comboPago.setSelectedIndex(0);
                txtEntrega.setText("0.0");
            }
        });
        

        comboPago.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                String metodo = (String) comboPago.getSelectedItem();
                String idIngresado = txtIdArticulo.getText().trim();
              
                //cuando eligen a credito credito el monto se pone en 0 para poner entrega
                if (metodo.equals("CREDITO PERSONAL")) {
                    txtEntrega.setText("0.0");
                } 
                else if (!idIngresado.isEmpty() && !txtDescripcion.getText().contains("❌")) {
                    double precio = modelo.GestorArchivos.obtenerPrecioArticulo(idIngresado);
                    if (precio > 0) {
                        txtEntrega.setText(String.valueOf(precio));
                    }
                }
            }
        });
        this.getRootPane().setDefaultButton(btnConfirmar);
    }
    
    
}