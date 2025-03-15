package com.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import org.json.*;

public class App {

// 🔹 1. Leer el archivo JSON desde un .txt
public static String leerArchivo(String rutaArchivo) {
    //leer archivo json desde un txt
    String contenido = "";
    try {
    contenido = new String(Files.readAllBytes(Paths.get(rutaArchivo)));
    } catch (IOException e) {
    e.printStackTrace();
    }
    return contenido;
}


// 🔹 2. Obtener transacciones de un usuario específico
public static List<JSONObject> obtenerTransacciones(String rutaArchivo, String usuario) {
    List transaccionesUsuario = new ArrayList<>();
    try {
        // Leer el contenido del archivo usando leerArchivo
        String jsonData = leerArchivo(rutaArchivo);

        // Convertir el contenido en un JSONArray
        JSONArray transacciones = new JSONArray(jsonData);

        // Filtrar las transacciones del usuario específico
        for (int i = 0; i < transacciones.length(); i++) {
            JSONObject transaccion = transacciones.getJSONObject(i);
            if (transaccion.getString("usuario").equals(usuario)) {
                transaccionesUsuario.add(transaccion);
            }
        }
        } catch (JSONException e) {
        e.printStackTrace();
        }
    return transaccionesUsuario;
}

// 🔹 3. Generar extracto bancario en un archivo .txt
public static void generarExtracto(String usuario, List<JSONObject> transacciones) {
    String nombreArchivo = usuario + "_extracto.txt";
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
    writer.write("Extracto Bancario - " + usuario + "\n");
    writer.write("====================================\n");
    for (JSONObject transaccion : transacciones) {
    // Extraer los datos de la transacción
    String fecha = transaccion.optString("fecha", "Fecha no disponible");
    String tipo = transaccion.optString("tipo", "Tipo no disponible");
    double monto = transaccion.optDouble("monto", 0.0);

    // Escribir los datos en el archivo con el formato solicitado
    writer.write("Fecha: " + fecha + "\n");
    writer.write("Tipo: " + tipo + "\n");
    writer.write("Monto: " + monto + "\n");
    writer.write("------------------------------------\n");
    }
    writer.write("====================================\n");
    System.out.println("Extracto generado exitosamente: " + nombreArchivo);
    } catch (IOException e) {
    e.printStackTrace();
    }
}

public static void main(String[] args) {
Scanner scanner = new Scanner(System.in);

// Solicitar el nombre del usuario
System.out.print("Ingrese el nombre del usuario: ");
String usuario = scanner.nextLine();

// Solicitar la ruta del archivo JSON
System.out.print("Ingrese la ruta del archivo JSON con las transacciones: ");
String rutaArchivo = scanner.nextLine();

// Leer las transacciones del usuario
List transacciones = obtenerTransacciones(rutaArchivo, usuario);

// Verificar si hay transacciones para el usuario
if (transacciones.isEmpty()) {
System.out.println("No se encontraron transacciones para el usuario: " + usuario);
} else {
// Generar el extracto bancario
generarExtracto(usuario, transacciones);
}

scanner.close();
}
}