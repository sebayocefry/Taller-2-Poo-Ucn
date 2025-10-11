import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.*;

// Clase PC
class PC
{
    private String iD;
    private String iP;
    private String sistemaOperativo;
    private ArrayList<Puerto> puertos;

    public PC(String iD, String iP, String sistemaOperativo)
    {
        this.iD = iD;
        this.iP = iP;
        this.sistemaOperativo = sistemaOperativo;
        this.puertos = new ArrayList<>();
    }

    public String getiD() {
        return iD;
    }
    public void setiD(String iD) {
        this.iD = iD;
    }

    public String getiP() {
        return iP;
    }
    public void setiP(String iP) {
        this.iP = iP;
    }

    public String getSistemaOperativo() {
        return sistemaOperativo;
    }
    public void setSistemaOperativo(String sistemaOperativo) {
        this.sistemaOperativo = sistemaOperativo;
    }

    public ArrayList<Puerto> getPuertos() {
        return puertos;
    }
    public void setPuertos(ArrayList<Puerto> puertos) {
        this.puertos = puertos;
    }

    @Override

    public String toString()
    {
        return "PC [iD=" + iD + ", iP=" + iP + ", sistemaOperativo=" + sistemaOperativo + ", puertos=" + puertos + "]";
    }
}

// Clase Puerto
class Puerto
{
    private int numeroPuero;
    private boolean estadoPuerto;
    private ArrayList<Vulnerabilidad> vulnerabilidades;

    public Puerto(int numeroPuero, boolean estadoPuerto)
    {
        this.numeroPuero = numeroPuero;
        this.estadoPuerto = estadoPuerto;
        this.vulnerabilidades = new ArrayList<>();
    }

    public int getNumeroPuero() {
        return numeroPuero;
    }
    public void setNumeroPuero(int numeroPuero) {
        this.numeroPuero = numeroPuero;
    }

    public boolean isEstadoPuerto() {
        return estadoPuerto;
    }
    public void setEstadoPuerto(boolean estadoPuerto) {
        this.estadoPuerto = estadoPuerto;
    }

    public ArrayList<Vulnerabilidad> getVulnerabilidades() {
        return vulnerabilidades;
    }
    public void setVulnerabilidades(ArrayList<Vulnerabilidad> vulnerabilidades) {this.vulnerabilidades = vulnerabilidades;}

    @Override

    public String toString()
    {
        return "Puerto [numeroPuero=" + numeroPuero + ", estadoPuerto=" + estadoPuerto + ", vulnerabilidades="
                + vulnerabilidades + "]";
    }
}

// Clase Vulnerabilidad
class Vulnerabilidad
{
    private int puertoV;
    private String nombreVulnerabilidad;
    private String descripcionV;

    public Vulnerabilidad(int puertoV, String nombreVulnerabilidad, String descripcionV)
    {
        this.puertoV = puertoV;
        this.nombreVulnerabilidad = nombreVulnerabilidad;
        this.descripcionV = descripcionV;
    }

    public int getPuertoV() {
        return puertoV;
    }
    public void setPuertoV(int puertoV) {
        this.puertoV = puertoV;
    }

    public String getNombreVulnerabilidad() {
        return nombreVulnerabilidad;
    }
    public void setNombreVulnerabilidad(String nombreVulnerabilidad) {this.nombreVulnerabilidad = nombreVulnerabilidad;}

    public String getDescripcionV() {
        return descripcionV;
    }
    public void setDescripcionV(String descripcionV) {
        this.descripcionV = descripcionV;
    }

    @Override

    public String toString()
    {
        return "Vulnerabilidad [puertoV=" + puertoV + ", nombreVulnerabilidad=" + nombreVulnerabilidad
                + ", descripcionV=" + descripcionV + "]";
    }
}

// Clase Usuario
class Usuario {
    private String username;
    private String passwordHash;  // Cambiado de "passwor" a "passwordHash"
    private String rolUser;

    public Usuario(String username, String passwordHash, String rolUser) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.rolUser = rolUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {  // Corregido: ahora devuelve String
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {  // Corregido: ahora recibe String
        this.passwordHash = passwordHash;
    }

    public String getRolUser() {
        return rolUser;
    }

    public void setRolUser(String rolUser) {
        this.rolUser = rolUser;
    }

    @Override
    public String toString() {
        return "Usuario [username=" + username + ", passwordHash=" + passwordHash + ", rolUser=" + rolUser + "]";
    }
}

class ManejadorUsuarios
{
    private Map<String, Usuario> usuarios;
    public ManejadorUsuarios()
    {
        this.usuarios = new HashMap<>();
    }

    //Carga los usuarios desde el archivo
    public void cargarUsuarios(String archivo)
    {
        try
        {
            List<String> lineas = Files.readAllLines(Paths.get(archivo), StandardCharsets.UTF_8);
            for (String linea : lineas)
            {
                String[] partes = linea.split(";");
                if (partes.length == 3)
                {
                    String nombre = partes[0].trim();
                    String passwordHash = partes[1].trim();
                    String rol = partes[2].trim();

                    usuarios.put(nombre, new Usuario(nombre, passwordHash, rol));
                }
            }
            System.out.println("Usuarios cargados exitosamente: " + usuarios.size());
        }
        catch (Exception e)
        {
            System.err.println("Error al cargar usuarios: " + e.getMessage());
        }
    }

    //Verifica si la contraseña ingresada coincide con el hash almacenado
    public boolean verificarContrasena(String nombreUsuario, String passwordIngresada)
    {
        Usuario usuario = usuarios.get(nombreUsuario);
        if (usuario == null)
        {
            System.out.println("Usuario no encontrado: " + nombreUsuario);
            return false;
        }

        try
        {
            // Generar hash SHA-256 de la contraseña ingresada
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(passwordIngresada.getBytes(StandardCharsets.UTF_8));

            // Convertir a Base64 para comparar
            String hashGenerado = Base64.getEncoder().encodeToString(hashBytes);

            // Comparar con el hash almacenado
            boolean coincide = usuario.getPasswordHash().equals(hashGenerado);

            if (coincide)
            {
                System.out.println("Contraseña correcta para usuario: " + nombreUsuario);
            }
            else
            {
                System.out.println("Contraseña incorrecta para usuario: " + nombreUsuario);
            }
            return coincide;
        }
        catch (Exception e)
        {
            System.err.println("Error al verificar contraseña: " + e.getMessage());
            return false;
        }
    }

    public Usuario obtenerUsuario(String nombreUsuario) {return usuarios.get(nombreUsuario);}
    public boolean existeUsuario(String nombreUsuario) {return usuarios.containsKey(nombreUsuario);}
    public String obtenerRol(String nombreUsuario)
    {
        Usuario usuario = usuarios.get(nombreUsuario);
        return usuario != null ? usuario.getRolUser() : null;
    }
}

// Clase Sistema
class Sistema
{
    private ArrayList<PC> listaPc = new ArrayList<>();

    // Devuelve pc como objeto para usarlo en operaciones
    public PC buscarPc(String p)
    {
        if (p != null)
        {
            String buscador = p.trim();
            for (PC elem : listaPc)
            {
                if (elem.getiD().equalsIgnoreCase(buscador) || elem.getiP().equalsIgnoreCase(buscador)) {
                    return elem;
                }
            }
        }
        return null;
    }

    public String calcularNivelRiesgo(PC pc)
    {
        int suma = 0;
        for (Puerto elem : pc.getPuertos())
        {
            suma += elem.getVulnerabilidades().size();
        }
        if (suma <= 1)
        {
            return "Bajo";
        }
        else if (suma <= 2)
        {
            return "Medio";
        }
        else
        {
            return "Alto";
        }
    }

    // Cargas comunes de archivos.txt
    public void cargarPCs(String archivo) throws FileNotFoundException
    {
        File arch = new File(archivo);
        Scanner lector = new Scanner(arch);

        while (lector.hasNextLine())
        {
            String linea = lector.nextLine().trim();
            String[] partes = linea.split("\\|");
            listaPc.add(new PC(partes[0], partes[1], partes[2]));
        }
        lector.close();
    }

    public void cargarPuertos(String archivo) throws FileNotFoundException
    {
        File file = new File(archivo);
        Scanner lector = new Scanner(file);

        while (lector.hasNextLine())
        {
            String linea = lector.nextLine().trim();
            String[] partes = linea.split("\\|");
            String iDpc = partes[0];
            int nPuerto = Integer.parseInt(partes[1]);
            boolean estadoPuerto = partes[2].equalsIgnoreCase("Abierto");

            for (PC elem : listaPc)
            {
                if (elem.getiD().equals(iDpc))
                {
                    elem.getPuertos().add(new Puerto(nPuerto, estadoPuerto));
                    break;
                }
            }
        }
        lector.close();
    }

    public void cargarVulnerabilidades(String archivo) throws FileNotFoundException
    {
        File arch = new File(archivo);
        Scanner lector = new Scanner(arch);

        while (lector.hasNextLine())
        {
            String linea = lector.nextLine().trim();
            String[] partes = linea.split("\\|");
            int puertoV = Integer.parseInt(partes[0]);

            // Buscar todos los puertos con este número y agregar la vulnerabilidad
            for (PC pc : listaPc)
            {
                for (Puerto puerto : pc.getPuertos())
                {
                    if (puerto.getNumeroPuero() == puertoV)
                    {
                        puerto.getVulnerabilidades().add(new Vulnerabilidad(puertoV, partes[1], partes[2]));
                    }
                }
            }
        }
        lector.close();
    }

    // Opciones ADMIN
    public void clasificarPcsVul()
    {
        for (PC pc : listaPc)
        {
            int suma = 0;
            ArrayList<String> nombreVulnerabilidades = new ArrayList<>();

            for (Puerto puertos : pc.getPuertos())
            {
                suma += puertos.getVulnerabilidades().size();
                for (Vulnerabilidad v : puertos.getVulnerabilidades())
                {
                    nombreVulnerabilidades.add(v.getNombreVulnerabilidad());
                }
            }

            String nivelVulne = calcularNivelRiesgo(pc);
            System.out.println("PC ID: " + pc.getiD());
            System.out.println("IP: " + pc.getiP());
            System.out.println("SO: " + pc.getSistemaOperativo());
            System.out.println("Nivel de riesgo: " + nivelVulne);
            System.out.println("NOMBRE DE LAS VULNERABILIDADES");

            for (String elem : nombreVulnerabilidades)
            {
                System.out.println("--: " + elem);
            }
            System.out.println("---------------------------");
        }
    }

    public void agregarPC(String id, String ip, String sistemaOperativo)
    {
        // Verificar si ya existe una PC con el mismo ID o IP
        for (PC pc : listaPc)
        {
            if (pc.getiD().equalsIgnoreCase(id) || pc.getiP().equals(ip))
            {
                System.out.println("Error: Ya existe una PC con ese ID o IP");
                return;
            }
        }

        // Crear y agregar la nueva PC
        PC nuevaPC = new PC(id, ip, sistemaOperativo);
        listaPc.add(nuevaPC);
        System.out.println("PC agregada exitosamente: " + id);
    }

    public void eliminarPC(String identificador)
    {
        if (identificador == null || identificador.trim().isEmpty())
        {
            System.out.println("Error: Identificador no válido");
            return;
        }

        String buscador = identificador.trim();
        PC pcAEliminar = null;

        // Buscar la PC por ID o IP
        for (PC pc : listaPc)
        {
            if (pc.getiD().equalsIgnoreCase(buscador) || pc.getiP().equalsIgnoreCase(buscador)) {
                pcAEliminar = pc;
                break;
            }
        }
        if (pcAEliminar != null)
        {
            listaPc.remove(pcAEliminar);
            System.out.println("PC eliminada exitosamente: " + buscador);
        }
        else
        {
            System.out.println("Error: No se encontró la PC con identificador: " + buscador);
        }
    }



    // Opciones USER
    public void mostrarPcsUser()
    {
        if (listaPc.isEmpty())
        {
            System.out.println("La lista se encuentra vacia");
        } else
        {
            for (PC elem : listaPc)
            {
                System.out.println("PC ID: " + elem.getiD());
                System.out.println("PC IP: " + elem.getiP());
                System.out.println("PC SO: " + elem.getSistemaOperativo());
                System.out.println("PUERTOS:");

                for (Puerto puertos : elem.getPuertos())
                {
                    String estado = puertos.isEstadoPuerto() ? "ABIERTO" : "Cerrado";
                    System.out.println("ESTADO DEL PUERTO " + puertos.getNumeroPuero() + ": " + estado);
                }
                System.out.println();
                System.out.println("---------------------------");
            }
        }
    }

    public void ordenarPcsPorIP() {
        if (listaPc.isEmpty()) {
            System.out.println("No hay PCs para ordenar");
            return;
        }

        // Ordenar la lista por IP usando Comparator
        listaPc.sort(new Comparator<PC>() {
            @Override
            public int compare(PC pc1, PC pc2) {
                return compararIPs(pc1.getiP(), pc2.getiP());
            }
        });

        System.out.println("PCs ordenadas por dirección IP exitosamente");
    }
    
    private int compararIPs(String ip1, String ip2) {
        String[] partes1 = ip1.split("\\.");
        String[] partes2 = ip2.split("\\.");

        for (int i = 0; i < 4; i++) {
            int octeto1 = Integer.parseInt(partes1[i]);
            int octeto2 = Integer.parseInt(partes2[i]);

            if (octeto1 != octeto2) {
                return Integer.compare(octeto1, octeto2);
            }
        }
        return 0;
    }

    public void mostrarPcsOrdenadasPorIP() {
        if (listaPc.isEmpty()) {
            System.out.println("La lista de PCs está vacía");
            return;
        }

        ordenarPcsPorIP();

        // Mostrar las PCs ordenadas
        System.out.println("\n========= PCs ORDENADAS POR IP =========");
        for (PC pc : listaPc) {
            System.out.println("IP: " + pc.getiP() + " | ID: " + pc.getiD() + " | SO: " + pc.getSistemaOperativo());
        }
    }

    
    
    
    
    public void mostrarTotalPuertosAbiertos() {
        if (listaPc.isEmpty()) {
            System.out.println("No hay PCs en la red");
            return;
        }

        int totalPuertosAbiertos = 0;
        int totalPuertosCerrados = 0;

        // Listas paralelas: una con números de puerto y otra con su frecuencia
        ArrayList<Integer> puertosRegistrados = new ArrayList<>();
        ArrayList<Integer> frecuencias = new ArrayList<>();

        System.out.println("=== ESTADÍSTICAS DE PUERTOS EN LA RED ===");

        for (PC pc : listaPc) {
            System.out.println("\nPC: " + pc.getiD() + " (" + pc.getiP() + ")");
            int puertosAbiertosPC = 0;
            int puertosCerradosPC = 0;

            for (Puerto puerto : pc.getPuertos()) {
                if (puerto.isEstadoPuerto()) {
                    puertosAbiertosPC++;
                    totalPuertosAbiertos++;

                    // Contar frecuencia de puerto abierto manualmente
                    int numeroPuerto = puerto.getNumeroPuero();
                    int indice = puertosRegistrados.indexOf(numeroPuerto);

                    if (indice == -1) {
                        puertosRegistrados.add(numeroPuerto);
                        frecuencias.add(1);
                    } else {
                        frecuencias.set(indice, frecuencias.get(indice) + 1);
                    }

                } else {
                    puertosCerradosPC++;
                    totalPuertosCerrados++;
                }
            }

            System.out.println("  Puertos abiertos: " + puertosAbiertosPC);
            System.out.println("  Puertos cerrados: " + puertosCerradosPC);

            // Mostrar vulnerabilidades si hay puertos abiertos
            if (puertosAbiertosPC > 0) {
                System.out.println("  Vulnerabilidades detectadas:");
                for (Puerto puerto : pc.getPuertos()) {
                    if (puerto.isEstadoPuerto() && !puerto.getVulnerabilidades().isEmpty()) {
                        for (Vulnerabilidad vuln : puerto.getVulnerabilidades()) {
                            System.out.println("    - Puerto " + puerto.getNumeroPuero() + ": " + vuln.getNombreVulnerabilidad());
                        }
                    }
                }
            }
        }

        System.out.println("\n=== RESUMEN GENERAL DE LA RED ===");
        System.out.println("Total de PCs en la red: " + listaPc.size());
        System.out.println("Total de puertos abiertos: " + totalPuertosAbiertos);
        System.out.println("Total de puertos cerrados: " + totalPuertosCerrados);
        System.out.println("Porcentaje de puertos abiertos: " +
                String.format("%.2f", (totalPuertosAbiertos * 100.0 / (totalPuertosAbiertos + totalPuertosCerrados))) + "%");

        // Mostrar los 5 puertos más frecuentes
        if (!puertosRegistrados.isEmpty()) {
            System.out.println("\nPuertos abiertos más frecuentes:");

            // Ordenar las listas en base a la frecuencia (algoritmo de burbuja simple)
            for (int i = 0; i < frecuencias.size() - 1; i++) {
                for (int j = i + 1; j < frecuencias.size(); j++) {
                    if (frecuencias.get(j) > frecuencias.get(i)) {
                        // Intercambiar frecuencias
                        int tempF = frecuencias.get(i);
                        frecuencias.set(i, frecuencias.get(j));
                        frecuencias.set(j, tempF);

                        // Intercambiar puertos
                        int tempP = puertosRegistrados.get(i);
                        puertosRegistrados.set(i, puertosRegistrados.get(j));
                        puertosRegistrados.set(j, tempP);
                    }
                }
            }

            int limite = Math.min(5, puertosRegistrados.size());
            for (int i = 0; i < limite; i++) {
                System.out.println("  Puerto " + puertosRegistrados.get(i) + ": " + frecuencias.get(i) + " ocurrencias");
            }
        }
    }

    public void escanearPc(String clavePc, String username, String nombreArch) throws IOException
    {
        PC pc = buscarPc(clavePc);
        if (pc == null)
        {
            System.out.println("El pc no existe");
            return;
        }

        String nivelRiesgo = calcularNivelRiesgo(pc);
        long tiempoMillis = System.currentTimeMillis();

        // Preparar texto del reporte
        StringBuilder escribirTxt = new StringBuilder();
        escribirTxt.append("----- REPORTE DE ESCANEO Secure Net -----\n");
        escribirTxt.append("Tiempo (ms): ").append(tiempoMillis).append("\n");
        escribirTxt.append("Usuario: ").append(username).append("\n");
        escribirTxt.append("PC ID: ").append(pc.getiD()).append("\n");
        escribirTxt.append("PC IP: ").append(pc.getiP()).append("\n");
        escribirTxt.append("PC SO: ").append(pc.getSistemaOperativo()).append("\n");
        escribirTxt.append("Nivel de riesgo PC: ").append(nivelRiesgo).append("\n");
        escribirTxt.append("Puertos PC:\n");

        for (Puerto p : pc.getPuertos())
        {
            String estado = p.isEstadoPuerto() ? "Abierto" : "Cerrado";
            escribirTxt.append("  - Puerto ").append(p.getNumeroPuero()).append(" : ").append(estado);

            if (!p.getVulnerabilidades().isEmpty())
            {
                escribirTxt.append("  (Vulnerabilidades: ");
                for (int i = 0; i < p.getVulnerabilidades().size(); i++)
                {
                    Vulnerabilidad v = p.getVulnerabilidades().get(i);
                    escribirTxt.append(v.getNombreVulnerabilidad());
                    if (i < p.getVulnerabilidades().size() - 1)
                    {
                        escribirTxt.append(", ");
                    }
                }
                escribirTxt.append(")");
            }
            escribirTxt.append("\n");
        }
        escribirTxt.append("------------------------------\n\n");

        // Escribir en el archivo reportes.txt
        try (FileWriter fw = new FileWriter(nombreArch, true))
        {
            fw.write(escribirTxt.toString());
        }
        System.out.println("Escaneo completado. Reporte guardado en: " + nombreArch);
    }
}