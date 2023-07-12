# Principios SOLID en Spring Boot
Los principios SOLID son 5 y son los siguientes:

* S: Single responsiblity principle
* O: Open/closed principle
* L: Liskov substitution principle
* I: Interface segregation principle
* D: Dependency inversion principle


## Single responsibility principle
Indica que una clase debería presentar una sola responsabilidad, es decir, no debería contener múltiples responsabilidades, puesto que el principio indica que el modificar una de ellas conllevará a la eventual modificación del resto de responsabilidades.

Un ejemplo de esto se encuentra en el archivo: [coa-api/src/main/java/edu/uday/coa/entity/Materia.java]
En este siguiente fragmento de código se puede ver la clase "Materia.java", la cual sólo contiene los métodos para acceder a los atributos de una entidad llamada Materia (incluidas como parte de las etiquetas pertenecientes a una dependencia).

	@Entity
	@Table(name = "materias")
	@Data
	@NoArgsConstructor
	public class Materia {
	    @Id
	    @Column(name ="id")
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "materia_seq")
	    @SequenceGenerator(name = "materia_seq", sequenceName = "materia_seq", initialValue = 1, allocationSize = 1)
	    private Long id;
	    @Column(name = "clave_materia")
	    private String claveMateria;
	    @Column(name ="nombre")
	    private String nombreMateria;
	    @JsonIgnore
	    @JsonProperty(value = "OtroNombreDelPlanDeEstudios")
	    @OneToMany(fetch = FetchType.LAZY, mappedBy = "materia")
	    @ToString.Exclude
	    private List<PlanEstudio> planEstudios;
	}

## Open/closed principle
Indica que las clases, módulos, funciones, deberían de estar "abiertos para su extensión, pero cerrados para su modificación". Esto se refiere principalmente a que la forma en la que se codifica la lógica interna de los métodos y de las clases debería de estar diseñado para no necesitar ser modificado cada vez que se necesiten agregar funciones o características alrededor de la clase. 

Un ejemplo de este principio se encuentra en la ruta: [coa-api/src/main/java/edu/uday/coa/service/MateriaService.java]
La clase "MateriaService.java" únicamente utiliza los métodos implementados de la interfaz "materiaRepository" para crear las operaciones básicas CRUD de la entidad "Materia". Los métodos están simplificados de tal manera que, si se deseara incluir una extensión de dichos métodos, no sería necesario modificar los ya existentes para realizarlo.

	@Service
	@Log4j2
	public class MateriaService {
	    @Autowired
	    private MateriaRepository materiaRepository;
	
	    public Materia createMateria(Materia materia){
	        log.info("crea Materia: "+materia.toString());
	        return materiaRepository.save(materia);
	    }
	
	    public Materia updateMateria(Materia Materia){
	        log.info("actualiza Materia: "+Materia.toString());
	        return materiaRepository.save(Materia);
	    }
	
	    public List<Materia> getAllMaterias() throws Exception{
	        List<Materia> materiaes = materiaRepository.findAll();
	        if(materiaes.isEmpty()){
	            throw new COAException("no se encontraron datos");
	        }
	        return  materiaes;
	    }
	
	    public void deleteMateria(Long id){
	        materiaRepository.deleteById(id);
	    }
	}

## Liskov sunstitution principle
Declara que "...una subclase debe ser sustituible por su superclase, y si al hacer esto, el programa falla, estaremos violando este principio".

Cumpliendo con este principio se confirmará que nuestro programa tiene una jerarquía de clases fácil de entender y un código reutilizable.

A continuación, se muestra un fragmento de código que viola este principio:

	public static void imprimirNumAsientos(Coche[] arrayCoches){
	    for (Coche coche : arrayCoches) {
	        if(coche instanceof Renault)
	            System.out.println(numAsientosRenault(coche));
	        if(coche instanceof Audi)
	            System.out.println(numAsientosAudi(coche));
	    }
	}
	imprimirNumAsientos(arrayCoches);  

En este ejemplo no se cumple el principio, puesto que el programa deberá de conocer cada tipo de coche para llamar a su método correspondiente. En dado caso de que se desee agregar otro tipo de coche, el método se deberá modificar para aceptarlo.

Una solución podría ser agregar un método abstracto a la superclase "Coche", la cual cada uno de los tipos de coche pueda sobreescribir dicho método con las características propias de la subclase.

## Interface segregation principle
Indica de que "las clases no deberían verse forzados a depender de interfaces que no utilizan". Esto se refiere principalmente a que, cuando tenemos varias clases que dependen de la implementación de una interfaz, y algunas de ellas no implementan la totalidad de los métodos de dicha interfaz, no deberían ser forzadas a ello. En lugar de tener que implementar dichos métodos, lo ideal sería "segregar" la interfaz en dos o más interfaces, para que las clases resagadas sólo implementen los métodos que utilizan partiendo de una nueva interfaz.

## Dependency inversion principle
Indica que las dependencias deben estar en las abstracciones, no en las concreciones, es decir:

* Los módulos de alto nivel no deberían depender de módulos de bajo nivel. Ambos deberían depender de abstracciones.
* Las abstracciones no deberían depender de detalles. Los detalles deberían depender de abstracciones.
