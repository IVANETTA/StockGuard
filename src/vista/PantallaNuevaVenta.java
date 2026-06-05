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
        java.awt.Image icono = java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/icono.png"));
        this.setIconImage(icono);
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
        
        //boton buscar
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

        //boton confirmar
        this.btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                // Vemos que el art exista
                if (txtDescripcion.getText().contains("Esperando") || txtDescripcion.getText().contains("❌")) {
                    JOptionPane.showMessageDialog(null, "Debe buscar y cargar un artículo válido antes de confirmar la venta.", "Caja - Error", JOptionPane.WARNING_MESSAGE);
                    return; 
                }

                String metodoPago = (String) comboPago.getSelectedItem();
                String dniCliente = "";
                
                //al se credito personal pide dni del cliente para sumarle a la deuda
                if (metodoPago.equals("CREDITO PERSONAL")) {
                    dniCliente = JOptionPane.showInputDialog(
                        null,
                        "Ingrese el DNI del cliente para asignar la deuda:",
                        "Identificación del cliente",
                        JOptionPane.QUESTION_MESSAGE
                    );

                    // vemos que no haya cancelado o dejado en blanco
                    if (dniCliente == null || dniCliente.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, 
                            "Operación cancelada: Debe ingresar un DNI para ventas a crédito personal.", 
                            "Venta Cancelada", 
                            JOptionPane.ERROR_MESSAGE);
                        return; 
                    }
                }

                double montoEntrega = 0.0; 
     
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
                    //mostramos confirmación de venta
                    String mensajeCaja = "✅ ¡Venta registrada exitosamente en el sistema!\n\n"
                                       + "Método de Pago: " + metodoPago + "\n";
                    
                    if (metodoPago.equals("CREDITO PERSONAL")) {
                        mensajeCaja += "Se registró una entrega de: $ " + montoEntrega + "\n"
                                     + "El resto se sumará a la deuda del DNI: " + dniCliente + " en el Fichero.";
                        if (metodoPago.equals("CREDITO PERSONAL")) {
                            double precioDelArticulo = modelo.GestorArchivos.obtenerPrecioArticulo(idVendido);
                            double deudaRestante = precioDelArticulo - montoEntrega;
                            boolean clienteEncontrado = modelo.GestorArchivos.actualizarDeudaCliente(dniCliente, deudaRestante);

                            mensajeCaja += "Se registró una entrega inicial de: $ " + montoEntrega + "\n"
                                         + "Quedaría adeudando $ " + deudaRestante + "\n"
                                         + "El resto se sumó a la deuda del DNI: " + dniCliente + " en el Fichero.";
                                         
                            //en caso de no encontrar el dni
                            if (!clienteEncontrado) {
                                mensajeCaja += "\n\n⚠️ ATENCIÓN: El DNI " + dniCliente + " no existe en el Fichero.\n"
                                             + "Debe ir a 'Fichero de Clientes' y darlo de alta manualmente\n";
                            }
                        }
                    }
    
                    JOptionPane.showMessageDialog(null, mensajeCaja, "StockGuard - Ticket", JOptionPane.INFORMATION_MESSAGE);

                    txtIdArticulo.setText("");
                    txtDescripcion.setText("Esperando artículo...");
                    comboPago.setSelectedIndex(0); 
                    txtEntrega.setText("0.0");
                } else {
                    JOptionPane.showMessageDialog(null, "Error grave: No se pudo guardar la venta en el archivo.", "Fallo de Sistema", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        comboPago.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                String metodo = (String) comboPago.getSelectedItem();
                String idIngresado = txtIdArticulo.getText().trim();
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