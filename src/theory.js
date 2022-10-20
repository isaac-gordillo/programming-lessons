
// JAVASCRIPT (js) VS JAVA, C++, .NET >> TYPESCRIPT (ts)
/**
 * 
 * La mayor diferencia entre js y otros lenguajes como Java o C++ es el tipado:
 * mientras que java es totalmente flexible a la hora de declarar variables
 * y reasignar los valores sin importar el tipo de dato que contenga, otros lenguajes, 
 * no permiten hacer eso ya que a la hora de crear la variable hay que especificarle
 * el tipo de dato que va a contener y eso no puede cambiar durante la ejecución.
 * 
 * La otra gran diferencia es la forma en la que se estructura el código,
 * mientras que JavaScript está orientado a funciones, Java, C++ o .NET estan orientado a objetos
 * 
 * JavaScript no te avisa de los fallos en tiempo de desarrollo el resto de lenguajes sí.
 * 
 * Estos problemas/desventajas se solventan usando TypeScript que es un Lenguaje basado en JavaScript (superset)
 *  cuyo fin es ayudar al programador a escribir un codigo más robusto y menos propenso a fallos, aportando la
 * flexibilidad de javascript y la seguridad de los otros lenguajes con el tipado además de la orientación
 * objetos. 
 * TypeScript no es ejecutado directamente si no que es traducido a JavaScript que es lo que
 * los navegadores web o intepretadores (Node.js) van a ejecutar finalmente.
 *
 */

/** NOTAS
 * cada ";" separa una sentencia de ejecución 
 */

// TIPOS DE DATOS 
    // PRIMITIVOS
    true; false; // boolean
    'texto'; // string
    123; // number
    null; // null
    undefined; // undefined
//  NaN // Not a Number 
    
    // COMPUESTOS    
    ({ 
        nombre: 'Eustaquio',
        edad: 50,               // objetos
        esEspañol: true
    })


    [
        'nombre',
        'descripcion',
        123,
        true,                   // Arrays | Arreglos | Vectores
        undefined,
        {marca: 'bmw', matricula: null}
        ['azul', 'rojo', 'amarillo'] // esto sería una Matriz o Array bidimensional
    ]


// VARIABLES
/**
 * Una variable es una posición en memoria en la cual guardamos un dato y la identificamos con
 * un nombre para poder acceder a ella cuando queramos. En la variable se almacenan un tipo de dato
 * de los mencionados arriba o una composición de ellos.
 * 
 * para declarar una variable debemos usar const o let dependiendo si queremos que esa variable se le pueda
 * reasignar un nuevo valor o no. a partir de ahí ya puedes usarla en el resto del programa.
 */


const numeroPi = 3.14159; // el numero Pi siempre será el mismo por lo que no tiene sentido cambiar el valor en un futuro;

numeroPi = 24 // esto da error

let numeroGoles = 0; // en cambio si almacenados algo que puede ir cambiando en el tiempo debemos permitir reasignar valores usando let

numeroGoles = 1;



   
