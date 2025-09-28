import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


// Clase PC
class PC {
   
    private String iD;
    private String iP;
    private String sistemaOperativo;
    private ArrayList<Puerto> puertos;

     public PC(String iD, String iP, String sistemaOperativo) {
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
     public String toString() {
        return "PC [iD=" + iD + ", iP=" + iP + ", sistemaOperativo=" + sistemaOperativo + ", puertos=" + puertos + "]";
    }
    
}

// Clase Puerto
class Puerto {
    
    private int numeroPuero;
    private boolean estadoPuerto;
    private ArrayList<Vulnerabilidad> vulnerabilidades;
    
    public Puerto(int numeroPuero, boolean estadoPuerto) {
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

    public void setVulnerabilidades(ArrayList<Vulnerabilidad> vulnerabilidades) {
        this.vulnerabilidades = vulnerabilidades;
    }
    @Override
    public String toString() {
        return "Puerto [numeroPuero=" + numeroPuero + ", estadoPuerto=" + estadoPuerto + ", vulnerabilidades="
                + vulnerabilidades + "]";
    }

    
}

// Clase Vulnerabilidad
class Vulnerabilidad{ 
    private int puertoV;
    private String nombreVulnerabilidad;
    private String descripcionV;
    public Vulnerabilidad(int puertoV, String nombreVulnerabilidad, String descripcionV) {
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
    public void setNombreVulnerabilidad(String nombreVulnerabilidad) {
        this.nombreVulnerabilidad = nombreVulnerabilidad;
    }
    public String getDescripcionV() {
        return descripcionV;
    }
    public void setDescripcionV(String descripcionV) {
        this.descripcionV = descripcionV;
    }
    @Override
    public String toString() {
        return "Vulnerabilidad [puertoV=" + puertoV + ", nombreVulnerabilidad=" + nombreVulnerabilidad
                + ", descripcionV=" + descripcionV + "]";
    }
}

// Clase Usuario
class Usuario{
    private String username;
    private String passwor;
    private String rolUser;
    public Usuario(String username, String passwor, String rolUser) {
        this.username = username;
        this.passwor = passwor;
        this.rolUser = rolUser;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPasswor() {
        return passwor;
    }
    public void setPasswor(String passwor) {
        this.passwor = passwor;
    }
    public String getRolUser() {
        return rolUser;
    }
    public void setRolUser(String rolUser) {
        this.rolUser = rolUser;
    }
    @Override
    public String toString() {
        return "Usuario [username=" + username + ", passwor=" + passwor + ", rolUser=" + rolUser + "]";
    }
}

// Clase Sistema
class Sistema{
    private ArrayList<PC> listaPc;
    private ArrayList<Usuario> listaUsuarios;
    private ArrayList<Vulnerabilidad> listaVulnerabilidades;

    //cargas comunes de archivos.txt
    public void cargarPCs(String archivo)throws FileNotFoundException{ 
        File arch = new File(archivo);
        Scanner lector = new Scanner(arch);

        while (lector.hasNextLine()){
            String linea = lector.nextLine().trim();
            String[] partes = linea.split("\\|");//java confunde el separador con el or 
            listaPc.add(new PC(partes[0], partes[1], partes[2]));
        }
        lector.close();
    }
    


    public void cargarPuertos(String archivo)throws FileNotFoundException{ 
        File file = new File(archivo);
        Scanner lector = new Scanner(file);
        while(lector.hasNextLine()){
            String linea = lector.nextLine().trim();
            String[] partes = linea.split("\\|");
            String iDpc = partes[0];// punto para no perder la relacion entre los pc
            int nPuerto = Integer.parseInt(partes[1]);
            boolean estadoPuerto = partes[2].equalsIgnoreCase("Abierto");// con esto java compara buscando abierto-AbIerTo-1 para true sino false

            // me muevo como un pc en la lista de pc iniciada comparo por id hasta encontrar una coincidencia, cuando la encuentro llamo a la lista puertos (sigue)
            // agrego partes 1 y 2 
            for (PC elem : listaPc) {
                if(elem.getiD().equals(iDpc)){
                    elem.getPuertos().add(new Puerto(nPuerto, estadoPuerto));
                    break;
                }
                
            }

        }
        lector.close();

    }
    public void cargarUsuarios(String archivo)throws FileNotFoundException{
        File file = new File(archivo);
        Scanner lector = new Scanner(file);

        while(lector.hasNextLine()){
            String linea = lector.nextLine().trim();
            String [] partes = linea.split(";");
            listaUsuarios.add(new Usuario(partes[0], partes[1], partes[2]));
        }

        lector.close();
    }
    public void cargarVulnerabilidades(String archivo)throws FileNotFoundException{
        File arch = new File(archivo);
        Scanner lector = new Scanner(arch);
        
        while(lector.hasNextLine()){
            String linea = lector.nextLine().trim();
            String[] partes = linea.split("\\|");
            listaVulnerabilidades.add(new Vulnerabilidad(Integer.parseInt(partes[0]), partes[1], partes[2]));// el parseint y no el valueOf, para que me lo convierta un entero primitivo
        }

        lector.close();
    }

    //opciones para usario 

    public void mostrarPcsUser(){
        //imprimir ID, IP, SO
        // sin metodo toatring porque quiero imprimir ciertos datos y no todos como en el usuario 
        if (listaPc.isEmpty()){
            System.out.println("La lista se encuentra vacia");
        }else{
            for (PC elem : listaPc) {
            System.out.println("PC ID: "+elem.getiD());
            System.out.println("PC IP: "+elem.getiP());
            System.out.println("PC SO: "+elem.getSistemaOperativo());
            System.err.println("PUERTOS:");
            for (Puerto puertos : elem.getPuertos()) {
                String estado;
                if(puertos.isEstadoPuerto()){
                    estado = "ABIERTO";
                }else{
                    estado = "Cerrado";
                }
                System.out.println("ESTADO DEL PUERTO "+puertos.getNumeroPuero()+ ": " + estado);
            }
            System.out.println();
            System.out.println("---------------------------");

         }

        }
    
    }
}

