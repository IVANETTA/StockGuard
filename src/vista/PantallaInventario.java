package vista;

import javax.swing.*;
import java.awt.*;

public class PantallaInventario extends JFrame {
    
    private JTextArea txtInventario;
    private JTextField txtId, txtNombre, txtPrecio, txtStock;
    private JComboBox<String> comboCategoria; 
    private JButton btnGuardar, btnActualizarLista;

    public PantallaInventario() {
        // Configuración de la ventana
        this.setTitle("StockGuard - Gestión de Inventario");
        this.setSize(750, 600); 
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(10, 10));

        //panel superior
        JLabel lblTitulo = new JLabel("Control y Registro de Mercadería", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        this.add(lblTitulo, BorderLayout.NORTH);


        
       //panel central
        txtInventario = new JTextArea("Presione 'Ver Inventario' para cargar los artículos..."); 
        
        txtInventario.setEditable(false);
        txtInventario.setFont(new Font("Monospaced", Font.PLAIN, 14));
        this.add(new JScrollPane(txtInventario), BorderLayout.CENTER);

        //panel inferior
        JPanel panelInferior = new JPanel(new BorderLayout(10, 10));
        panelInferior.setBorder(BorderFactory.createTitledBorder("Agregar Nuevo / Actualizar Artículo"));

        //formulario de 5 filas y dos columnas
        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 10, 5));
       
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 80, 10, 80));

        panelFormulario.add(new JLabel("ID del Artículo:"));
        txtId = new JTextField();
        panelFormulario.add(txtId);

        panelFormulario.add(new JLabel("Nombre del Artículo:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Categoría:"));
       
        comboCategoria = new JComboBox<>(new String[]{"PANTALON", "SHORT", "REMERA", "ACCESORIOS", "Otro..."});
        panelFormulario.add(comboCategoria);

        panelFormulario.add(new JLabel("Precio de Venta ($):"));
        txtPrecio = new JTextField();
        panelFormulario.add(txtPrecio);

        panelFormulario.add(new JLabel("Stock Disponible:"));
        txtStock = new JTextField("1");
        panelFormulario.add(txtStock);

        panelInferior.add(panelFormulario, BorderLayout.CENTER);

        //personalizamos la vista de los botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
        btnActualizarLista = new JButton("Ver Inventario");
        btnActualizarLista.setBackground(new Color(70, 130, 180));
        btnActualizarLista.setForeground(Color.WHITE);
        panelBotones.add(btnActualizarLista);

        btnGuardar = new JButton("Guardar Artículo");
        btnGuardar.setBackground(new Color(50, 205, 50)); 
        btnGuardar.setForeground(Color.WHITE);
        panelBotones.add(btnGuardar);

        // Agregamos los botones al panel inferior
        panelInferior.add(panelBotones, BorderLayout.SOUTH);
        
        this.add(panelInferior, BorderLayout.SOUTH);

        comboCategoria.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
       
                if ("Otro...".equals(comboCategoria.getSelectedItem())) {
                    String nuevaCategoria = JOptionPane.showInputDialog(null, "Ingrese el nombre de la nueva categoría:", "Nueva Categoría", JOptionPane.QUESTION_MESSAGE);
                    
                    // excepciones
                    if (nuevaCategoria != null && !nuevaCategoria.trim().isEmpty()) {
                        String categoriaFormateada = nuevaCategoria.trim().toUpperCase();                   
                        // Reubico la nueva categoria
                        comboCategoria.insertItemAt(categoriaFormateada, comboCategoria.getItemCount() - 1);
               
                        comboCategoria.setSelectedItem(categoriaFormateada);
                    } else {
                        comboCategoria.setSelectedIndex(0);
                    }
                }
            }
            
        });
     // --- INICIO DE LA LÓGICA DEL BOTÓN VER INVENTARIO ---
        btnActualizarLista.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                // Llamamos al Gestor para traer la tabla formateada
                String datos = modelo.GestorArchivos.obtenerListaArticulos();
                // Lo mostramos en el área de texto central
                txtInventario.setText(datos);
            }
        });
        // --- FIN DE LA LÓGICA ---
        
     // --- INICIO DE LA LÓGICA DEL BOTÓN GUARDAR (ACTUALIZADA) ---
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                String id = txtId.getText().trim();
                String nombre = txtNombre.getText().trim();
                String categoria = (String) comboCategoria.getSelectedItem();
                String precioStr = txtPrecio.getText().trim().replace(",", "."); 
                String stockStr = txtStock.getText().trim();

                // 1. NUEVA VALIDACIÓN: Solo ID y Precio son obligatorios
                if (id.isEmpty() || precioStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El ID y el Precio de Venta son campos obligatorios.", "Faltan datos", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // 2. AUTOCOMPLETADO INTELIGENTE: Si dejaron campos vacíos, les ponemos valores por defecto
                if (nombre.isEmpty()) {
                    nombre = "Sin descripción";
                }
                if (stockStr.isEmpty()) {
                    stockStr = "1";
                }

                try {
                    double precio = Double.parseDouble(precioStr);
                    int stock = Integer.parseInt(stockStr);

                    // Llamamos a nuestro Gestor
                    boolean exito = modelo.GestorArchivos.guardarOActualizarArticulo(id, nombre, categoria, precio, stock);

                    if (exito) {
                        JOptionPane.showMessageDialog(null, "✅ Artículo guardado exitosamente.");
                        
                        // 3. Limpiamos el formulario, pero dejamos el stock en 1 para el próximo
                        txtId.setText("");
                        txtNombre.setText("");
                        comboCategoria.setSelectedIndex(0);
                        txtPrecio.setText("");
                        txtStock.setText("1"); // Restablecemos el 1

                        // Actualizamos la tabla automáticamente
                        btnActualizarLista.doClick(); 
                    } else {
                        JOptionPane.showMessageDialog(null, "❌ Error al intentar guardar en el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "El Precio y el Stock deben ser números.", "Error de formato", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
      
        // Al establecer el botón por defecto, al presionar Enter en cualquier campo de texto, se accionará este botón.
        this.getRootPane().setDefaultButton(btnGuardar);
        // --- FIN DE LA LÓGICA ---
        
        // Atajo de teclado: Enter para guardar
        this.getRootPane().setDefaultButton(btnGuardar);
        // --- FIN DE LA LÓGICA ---
    }
}