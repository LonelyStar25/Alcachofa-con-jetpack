package com.mycompany.practicapsp;

import java.io.*;
import java.util.*;

public class Ejer {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int menú = 1;
        boolean SOWindows = false, error;
        String ruta, nombre, comando = "";

        //detecta el SO del usuario
        if (System.getProperty("os.name").contains("Windows")) {
            SOWindows = true;
        }

        do {
            //imprime el menú
            try {
                System.out.print("1. Crear una carpeta dada la ruta y el nombre\n"
                        + "2. Crear un fichero dada la ruta y el nombre\n"
                        + "3. Listar las interfaces de red de nuestro ordenador\n"
                        + "4. Mostrar la IP del ordenador dado el nombre de la interfaz de red\n"
                        + "5. Mostrar la dirección MAC dada la interfaz de red\n"
                        + "6. Comprobar conectividad con internet\n"
                        + "7. Salir\n"
                        + "> ");
                menú = Integer.parseInt(sc.nextLine());
                error = false;
            } catch (NumberFormatException ex) {
                System.out.println("Inténtalo de nuevo");
                error = true;
            }
            if (menú < 1 || menú > 7) {
                System.out.println("Inténtalo de nuevo");
            }

            if (error == false) {
                //decide qué comando usar según la opción elegida
                switch (menú) {
                    case 1:
                        System.out.print("Introduce ruta\n"
                                + "> ");
                        ruta = sc.nextLine();
                        System.out.print("Introduce nombre de la carpeta a crear\n"
                                + "> ");
                        nombre = sc.nextLine();
                        comando = "mkdir " + ruta + "\\" + nombre;
                        break;

                    case 2:
                        System.out.print("Introduce ruta\n"
                                + "> ");
                        ruta = sc.nextLine();
                        System.out.print("Introduce nombre del archivo a crear\n"
                                + "> ");
                        nombre = sc.nextLine();
                        comando = "echo. > " + ruta + "\\" + nombre;
                        break;

                    case 3:
                        if (SOWindows) {
                            comando = "ipconfig";
                        } else {
                            comando = "ifconfig";
                        }
                        break;

                    case 4:
                        System.out.print("Introduce nombre de la interfaz de red\n"
                                + "> ");
                        nombre = sc.nextLine();
                        if (SOWindows) {
                            comando = "netsh interface ip show address \"" + nombre + "\" | findstr \"IP Address\"";
                        } else {
                            //No he encontrado cómo hacerlo en función de la interfaz de red :c
                            comando = "ifconfig -a";
                        }
                        break;

                    case 5:
                        System.out.print("Introduce nombre de la interfaz de red\n"
                                + "> ");
                        nombre = sc.nextLine();
                        if (SOWindows) {
                            //Igual que la opción 4, no encuentro cómo hacerlo
                            comando = "getmac";
                        } else {
                            comando = "cat /sys/class/net/" + nombre + "/address";
                        }
                        break;

                    case 6:
                        if (SOWindows) {
                            comando = "ping google.com";
                        } else {
                            comando = "curl -I https://linuxconfig.org";
                        }
                        break;
                }

                //ejecuta el comando y muestra su salida
                if (menú > 0 && menú < 7) {
                    try {
                        Process proceso;
                        if (SOWindows == true) {
                            proceso = Runtime.getRuntime().exec(comando);
                        } else {
                            proceso = Runtime.getRuntime().exec("/bin/bash -c " + comando);
                        }
                        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
                        String s;
                        while ((s = stdInput.readLine()) != null) {
                            System.out.println(s);
                        }
                    } catch (IOException ex) {
                        System.out.println("Has introducido algo mal o ha habido algún error, inténtalo de nuevo");
                    }
                }
            }
            System.out.println();

            //repite mientras no se seleccione la opción de salir
        } while (menú != 7);
    }
}
