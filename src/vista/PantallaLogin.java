package vista;

import javax.swing.*;
import java.awt.*;

// Heredamos de JFrame para que esta clase se convierta automáticamente en una ventana
public class PantallaLogin extends JFrame {
    
    private static final long serialVersionUID = 1L;
    
    // Declaramos los componentes de la ventana
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JComboBox<String> comboRol;
    private JButton btnIngresar;

    public PantallaLogin() {
        // Configuro visualmente la ventana
        this.setTitle("StockGuard - Inicio de Sesión");
        java.awt.Image icono = java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/icono.png"));
        this.setIconImage(icono);
        this.setSize(350, 250);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); 
        this.setLayout(new GridLayout(4, 3, 10, 10)); 

        JLabel lblUsuario = new JLabel("Usuario:");
        this.txtUsuario = new JTextField();

        JLabel lblPassword = new JLabel("Contraseña:");
        this.txtPassword = new JPasswordField();

        JLabel lblRol = new JLabel("Cargo:");
        // Creo las opciones para roles
        String[] roles = {"Administrador", "Empleado"};
        this.comboRol = new JComboBox<>(roles);

        this.btnIngresar = new JButton("Iniciar sesion");

        // Agrego los componentes a la ventana 
        this.add(lblUsuario);
        this.add(txtUsuario);
        
        this.add(lblPassword);
        this.add(txtPassword);
        
        this.add(lblRol);
        this.add(comboRol);
        
        this.add(new JLabel("")); // Agregamos un espacio invisible para empujar el botón a la derecha
        this.add(btnIngresar);
        
        this.btnIngresar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {

                String usuario = txtUsuario.getText();
                String password = new String(txtPassword.getPassword());
                String rol = (String) comboRol.getSelectedItem();
                
                if (rol.equals("Empleado")) {
                    // Guardar horario de apertura de caja automáticamente
                    modelo.GestorArchivos.registrarAsistencia(usuario);
                    
                    JOptionPane.showMessageDialog(null, "¡Bienvenido/a al sistema!\nRol: " + rol);
                    Dashboard ventanaPrincipal = new Dashboard(usuario, rol);
                    ventanaPrincipal.setVisible(true);
                    dispose();
                    
                } else if (rol.equals("Administrador")) {
                    javax.swing.JOptionPane.showMessageDialog(null, 
                        "¡Bienvenido a StockGuard!\nUsuario: " + usuario + "\nRol: " + rol);
                    Dashboard ventanaPrincipal = new Dashboard(usuario, rol);
                    ventanaPrincipal.setVisible(true);                    
                   
                    dispose();
                }
            }
        }); 
       
        // Configuramos btn ingresar para que responda al enter
        this.getRootPane().setDefaultButton(btnIngresar);
        
    }

    public static void main(String[] args) {
        PantallaLogin login = new PantallaLogin();
        login.setVisible(true); 
    }
}