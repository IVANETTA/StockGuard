package modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GestorArchivos {

    // metodo para buscarart por su id
    public static String buscarArticulo(String idBuscado) {
        String linea;
        String resultado = null;
        try (BufferedReader lector = new BufferedReader(new FileReader("articulos.txt"))) {
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos[0].equals(idBuscado)) {
                    resultado = "✅ Artículo Encontrado:\n"
                              + "ID: " + datos[0] + "\n"
                              + "Nombre: " + datos[1] + "\n"
                              + "Categoría: " + datos[2] + "\n"
                              + "Precio de Venta: $ " + datos[3] + "\n"
                              + "Stock Disponible: " + datos[4] + " unidades";
                    break;
                }
            }
        } catch (IOException e) {
            resultado = "Error fatal: No se pudo leer el archivo articulos.txt";
        }
        return resultado;
    }
 //metodo para guardar una nueva venta en el disco duro
    public static boolean registrarVenta(String idArticulo, String metodoPago, double entrega) {
        try (FileWriter fw = new FileWriter("ventas.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter salida = new PrintWriter(bw)) {
            LocalDateTime ahora = LocalDateTime.now();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            String fechaHora = ahora.format(formato);
            salida.println(fechaHora + ", Venta del articulo: " + idArticulo + ", Forma de pago:" + metodoPago + ", con entrega de: " + entrega);
            
            return true; 
            
        } catch (IOException e) {
            return false;
        }
    }
    
    public static String obtenerListaClientes() {
        StringBuilder lista = new StringBuilder();
     
        String formatoColumnas = "%-12s %-20s %-15s %-10s\n";
        lista.append(String.format(formatoColumnas, "DNI", "Nombre", "Teléfono", "Deuda ($)"));
        lista.append("-----------------------------------------------------------------\n");

        try (BufferedReader lector = new BufferedReader(new FileReader("clientes.txt"))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",");
                lista.append(String.format(formatoColumnas, datos[0], datos[1], datos[2], datos[3]));
            }
        } catch (IOException e) {
            return "Error grave: No se pudo localizar el archivo clientes.txt";
        }

        return lista.toString(); 
    }
    
 //metodo para buscar un cliente  por dni o nombreo apellido
    public static String buscarCliente(String criterio) {
        StringBuilder lista = new StringBuilder();
        String formatoColumnas = "%-12s %-20s %-15s %-10s\n";
        lista.append(String.format(formatoColumnas, "DNI", "Nombre", "Teléfono", "Deuda ($)"));
        lista.append("-----------------------------------------------------------------\n");

        boolean encontrado = false;

        try (BufferedReader lector = new BufferedReader(new FileReader("clientes.txt"))) {
            String linea;
            
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",");
                String dniArchivo = datos[0];
                String nombreArchivo = datos[1].toLowerCase();
                String criterioMinusculas = criterio.toLowerCase();

                if (dniArchivo.equals(criterio) || nombreArchivo.contains(criterioMinusculas)) {

                    lista.append(String.format(formatoColumnas, datos[0], datos[1], datos[2], datos[3]));
                    encontrado = true;
                }
            }
        } catch (IOException e) {
            return "Error grave: No se pudo localizar el archivo clientes.txt";
        }
        if (!encontrado) {
            return "❌ No se encontró ningún cliente con ese DNI o Apellido.";
        }

        return lista.toString(); 
    }

    public static boolean actualizarDeudaCliente(String dni, double montoModificar, boolean esPago) {
        java.util.List<String> lineasArchivo = new java.util.ArrayList<>();
        boolean clienteEncontrado = false;
        try (BufferedReader lector = new BufferedReader(new FileReader("clientes.txt"))) {
            String linea;
            
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",");
                
               
                if (datos[0].equals(dni)) {
                    double deudaActual = Double.parseDouble(datos[3]);
                    
                   
                    if (esPago) {
                        deudaActual -= montoModificar;
                        if (deudaActual < 0) deudaActual = 0.0; 
                    } else {
                        deudaActual += montoModificar; 
                    }
                    
                   
                    linea = datos[0] + "," + datos[1] + "," + datos[2] + "," + deudaActual;
                    clienteEncontrado = true;
                }
                
                
                lineasArchivo.add(linea);
            }
        } catch (IOException e) {
            return false;
        }

        if (!clienteEncontrado) return false;

        try (java.io.PrintWriter escritor = new java.io.PrintWriter(new java.io.FileWriter("clientes.txt", false))) {
            for (String lineaGuardar : lineasArchivo) {
                escritor.println(lineaGuardar);
            }
            return true; // Éxito total
        } catch (IOException e) {
            return false;
        }
    }
    

    public static boolean actualizarStockArticulo(String idArticulo, int cantidadVendida) {
        java.util.List<String> lineasArchivo = new java.util.ArrayList<>();
        boolean articuloEncontrado = false;

        try (BufferedReader lector = new BufferedReader(new FileReader("articulos.txt"))) {
            String linea;
            
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",");

                if (datos[0].equals(idArticulo)) {
                    int stockActual = Integer.parseInt(datos[4]);
                    int nuevoStock = stockActual - cantidadVendida;
  
                    if (nuevoStock < 0) nuevoStock = 0; 
       
                    linea = datos[0] + "," + datos[1] + "," + datos[2] + "," + datos[3] + "," + nuevoStock;
                    articuloEncontrado = true;
                }
                
                lineasArchivo.add(linea);
            }
        } catch (IOException e) {
            return false;
        }

        if (!articuloEncontrado) return false;


        try (java.io.PrintWriter escritor = new java.io.PrintWriter(new java.io.FileWriter("articulos.txt", false))) {
            for (String lineaGuardar : lineasArchivo) {
                escritor.println(lineaGuardar);
            }
            return true;
        } catch (IOException e) {
            return false;
        }
        
    }
 // pasamos de string a double
    public static double obtenerPrecioArticulo(String idBuscado) {
        try (BufferedReader lector = new BufferedReader(new FileReader("articulos.txt"))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos[0].equals(idBuscado)) {
                    return Double.parseDouble(datos[3]);
                }
            }
        } catch (IOException e) {

        }
        return 0.0;
    }

    public static String obtenerListaArticulos() {
        StringBuilder lista = new StringBuilder();

        String formatoColumnas = "%-6s %-30s %-15s %-12s %-10s\n";
        
        lista.append(String.format(formatoColumnas, "ID", "Nombre", "Categoría", "Precio ($)", "Stock"));
        lista.append("-------------------------------------------------------------------------------\n");

        //leemos el archivo articulos.txt
        try (BufferedReader lector = new BufferedReader(new FileReader("articulos.txt"))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",");
                // datos[0]=ID, datos[1]=Nombre, datos[2]=Categoría, datos[3]=Precio, datos[4]=Stock
                lista.append(String.format(formatoColumnas, datos[0], datos[1], datos[2], datos[3], datos[4]));
            }
        } catch (IOException e) {
            return "❌ Error: No se pudo localizar el archivo articulos.txt";
        }

        return lista.toString(); 
    }

    public static boolean guardarOActualizarArticulo(String id, String nombre, String categoria, double precio, int stock) {
        java.util.List<String> lineasArchivo = new java.util.ArrayList<>();
        boolean articuloExiste = false;

    
        try (BufferedReader lector = new BufferedReader(new FileReader("articulos.txt"))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",");
                
 
                if (datos[0].equals(id)) {
                    linea = id + "," + nombre + "," + categoria + "," + precio + "," + stock;
                    articuloExiste = true;
                }
                
                lineasArchivo.add(linea);
            }
        } catch (IOException e) {
            return false;
        }
        //si el art no existe lo agregamos
     if (!articuloExiste) {
            lineasArchivo.add(id + "," + nombre + "," + categoria + "," + precio + "," + stock);
        }

        try (java.io.PrintWriter escritor = new java.io.PrintWriter(new java.io.FileWriter("articulos.txt", false))) {
            for (String lineaGuardar : lineasArchivo) {
                escritor.println(lineaGuardar);
            }
            return true;
        } catch (IOException e) {
            return false;
        }
        
    }
 //metodo para registrar a qué hora ingresa un empleado
    public static void registrarAsistencia(String usuario) {
        //capturamos horario
        java.time.LocalDateTime ahora = java.time.LocalDateTime.now();
        java.time.format.DateTimeFormatter formato = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String fechaHora = ahora.format(formato);

        //el 'true' en FileWriter es para que se agregue la línea al final sin borrar lo anterior
        try (java.io.PrintWriter escritor = new java.io.PrintWriter(new java.io.FileWriter("asistencia.txt", true))) {
            escritor.println(fechaHora + "," + usuario + ",APERTURA DE CAJA");
        } catch (java.io.IOException e) {
            System.out.println("Error interno al registrar la asistencia.");
        }
    }

    // metodo para leer el archivo de horarios y armar la tabla
    public static String obtenerRegistroAsistencia() {
        StringBuilder lista = new StringBuilder();
        String formatoColumnas = "%-25s %-15s %-20s\n";
        
        lista.append(String.format(formatoColumnas, "Fecha y Hora", "Usuario", "Evento"));
        lista.append("-----------------------------------------------------------------\n");

        try (java.io.BufferedReader lector = new java.io.BufferedReader(new java.io.FileReader("asistencia.txt"))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",");
                if(datos.length >= 3) {
                    lista.append(String.format(formatoColumnas, datos[0], datos[1], datos[2]));
                }
            }
        } catch (java.io.IOException e) {
            return "No hay registros de asistencia en el sistema todavía.";
        }

        return lista.toString();
    }
    
 // metodo para leer el archivo de ventas y armar el reporte
    public static String obtenerDetalleVentas() {
        StringBuilder lista = new StringBuilder();
      
        String formatoColumnas = "%-20s %-25s %-15s\n";
        
        lista.append(String.format(formatoColumnas, "Fecha y Hora", "Método de Pago", "Total ($)"));
        lista.append("------------------------------------------------------------------\n");

        try (java.io.BufferedReader lector = new java.io.BufferedReader(new java.io.FileReader("ventas.txt"))) {
            String linea;
            boolean hayVentas = false;
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",");
                
    
                if(datos.length >= 4) {
                    String fecha = datos[0].trim();
                    String metodo = datos[2].substring(datos[2].indexOf(":") + 1).trim();
                    String monto = datos[3].substring(datos[3].indexOf(":") + 1).trim();
                    
                    lista.append(String.format(formatoColumnas, fecha, metodo, "$" + monto));
                    hayVentas = true;
                }
            }
            if (!hayVentas) {
                return "Aún no se han registrado ventas en el sistema.";
            }
        } catch (java.io.IOException e) {
            return "El archivo de ventas está vacío o aún no ha sido creado.";
        }

        return lista.toString();
    }

    //metodopara realizar el calculo
    public static double calcularTotalRecaudado() {
        double total = 0.0;
        try (java.io.BufferedReader lector = new java.io.BufferedReader(new java.io.FileReader("ventas.txt"))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",");
                if(datos.length >= 4) {
                    try {
                        String montoStr = datos[3].substring(datos[3].indexOf(":") + 1).trim();
                        total += Double.parseDouble(montoStr); 
                    } catch (NumberFormatException ex) {
                    
                    }
                }
            }
        } catch (java.io.IOException e) {
            // Si el archivo no existe da 0.0
        }
        return total;
    }
 // metodo para buscar al cliente y acualizar la deuda
    public static boolean actualizarDeudaCliente(String dniBuscado, double montoDeudaASumar) {
        java.io.File archivo = new java.io.File("clientes.txt");
        java.io.File temporal = new java.io.File("clientes_temp.txt");
        boolean encontrado = false;

        try {
            //si el archivo no existe creamos uno nuevo
            if (!archivo.exists()) {
                archivo.createNewFile();
            }

            java.io.BufferedReader lector = new java.io.BufferedReader(new java.io.FileReader(archivo));
            java.io.BufferedWriter escritor = new java.io.BufferedWriter(new java.io.FileWriter(temporal));

            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 4 && datos[0].trim().equals(dniBuscado.trim())) {
                    //si encontramos el dni le sumamos la deuda
                    double deudaActual = Double.parseDouble(datos[3].trim());
                    double nuevaDeuda = deudaActual + montoDeudaASumar;
                    escritor.write(datos[0] + "," + datos[1] + "," + datos[2] + "," + nuevaDeuda + "\n");
                    encontrado = true;
                } else {
                    escritor.write(linea + "\n");
                }
            }
            lector.close();
            escritor.close();

            //reemplazamos el archivo viejo por el actualizado
            archivo.delete();
            temporal.renameTo(archivo);

        } catch (Exception e) {
            return false; 
        }
        return encontrado;
    }
    }
    
    
