
package libreria;



/**
 *
 * @author Marcela
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Hola!");
        Menu.menu();
    }
    
    /* se obtiene el EntityManagerFactory usando la unidad de persistencia definida en archivo persistence.xml
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Libreria");

        // Crea un EntityManager a partir del EntityManagerFactory
        EntityManager em = emf.createEntityManager();

        try {
            
            // agregar un autor
            Autor autor1 = new Autor();
            autor1.setNombre("Cervantes Saavedra");
            autor1.setAlta(true);
      
            em.getTransaction().begin();
            em.persist(autor1);
            em.getTransaction().commit();

            // agregar una editorial
            Editorial editorial = new Editorial();
            editorial.setNombre("Editorial XYZ");
            

           /* em.getTransaction().begin();
            em.persist(editorial);
            em.getTransaction().commit();

             //agregar un libro
            Libro libro = new Libro();
            libro.setTitulo("Don Quijote de la Mancha");
          
            // ...

         

        } finally {
            // Cerrar el EntityManager y el EntityManagerFactory al finalizar
            em.close();
            emf.close();
        }
*/
    }



