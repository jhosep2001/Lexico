import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        boolean identificador = true;
        boolean entero = true;
        boolean real = true;
        boolean reservada = true;
        boolean point = false;
        String RESERVED_WORDS = "while,for,until,if,then,else,loop,append";
        String[] RESERVED_LIST = RESERVED_WORDS.split(",");
        Map<String, char[]> WORDS =  new HashMap<>();
        for (String word: RESERVED_LIST) {
            WORDS.put(word, word.toCharArray());
        }


        System.out.println("Hola, ingresa los caracteres para analiar");
        String secuencia = scan.nextLine();

        if (secuencia.contains(" ")){
            System.out.println("Error, hay espacios o varias palabras en la secuencia separadas por espacio");
            return;
        }


        char[] caracteres = secuencia.toCharArray();

        //verificar si es un numero en el primer caracter
        if(isInteger(String.valueOf(caracteres[0]))) {
            for (int i=1; i<caracteres.length; i++) {
               if(!isInteger(String.valueOf(caracteres[i]))) {
                   if(caracteres[i] == '.' && point == false){
                       point = true;
                       entero = false;
                   }else {
                       //tiene un caracter junto a los numeros, no es numero o varios puntos
                       entero = false;
                       real = false;
                   }
               }
            }

            if (entero) {
                System.out.println("la secuencia es un entero");
                return;
            } else if (real) {
                System.out.println("la secuencia es un real");
                return;
            } else {
                System.out.println("La secuencia de caracteres es no clasificada");
                return;
            }
        //Si se llega a esta parte no es ni entero ni real. porque no inicia con numero
        }else {
            String reserved = "";
            List<String> notReserved = new ArrayList<String>();
            // se recorre cada caracter de la secuencia
            for (int i=0; i<caracteres.length; i++) {
                if(Character.isLetter(caracteres[i]) && reservada) {
                    //se pregunta dentro de las palabras reservadas si es posible que sea
                    for (Map.Entry<String, char[]> composicion : WORDS.entrySet()) {
                        //si la palabra ingresada ya va en el un caracter i mayor a los que tiene la palabra se descarta
                        if (i+1 <= composicion.getValue().length) {
                            // se evalua que el caracter leido sea igual al de la palabra reservada guardada
                            if(caracteres[i] != composicion.getValue()[i]) {
                                notReserved.add(composicion.getKey());
                                //si no es igual se elimina de la lista reservadas;
                            }
                        }
                    }
                } else if (isInteger(String.valueOf(caracteres[i]))) {
                    reservada = false;
                } else if (caracteres[i] == '_') {
                    reservada = false;
                } else {
                    identificador = false;
                }

            }

            for (String key: notReserved) {
                WORDS.remove(key);
            }

            if (WORDS.size() > 0 ) {
                for (Map.Entry<String, char[]> composicion : WORDS.entrySet()) {
                    System.out.println("Se encontro la palabra reservada: "+composicion.getKey());
                }
            }else if (identificador) {
                System.out.println("Se encontro que la palabra es un identificador: "+secuencia);
            }else {
                System.out.println("Error, La secuencia de caracteres es no clasificada");
            }
        }

    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
}
