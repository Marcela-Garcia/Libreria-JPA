/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import entidad.Editorial;

import java.util.List;

public class EditorialDAO extends DAO<Editorial> {

    @Override
    public void guardar(Editorial entidad) {
        super.guardar(entidad);
    }

    @Override
    public void editar(Editorial entidad) {
        super.editar(entidad);
    }

    @Override
    public void eliminar(Editorial entidad) {
        super.eliminar(entidad);
    }

    public void listarEditoriales() {
        conectar();
        List<Editorial> editoriales = (List<Editorial>) em.createQuery("SELECT e FROM Editorial e").getResultList();
        if (editoriales != null) {
            for (Editorial editorial : editoriales) {
                System.out.println(editorial);
            }
        } else {
            System.out.println("No se encontraron resultados");
        }
        desconectar();
    }

    public Editorial buscarPorId(int id) {
        conectar();
        Editorial editorial;
        try {
            editorial = em.find(Editorial.class, id);
            if (editorial != null) {
                System.out.println(editorial);
            } else {
                System.out.println("No se encontro la editorial");
            }
        } catch (Exception e) {
            System.out.println("No se encontro la editorial");
            return null;
        }
        desconectar();
        return editorial;
    }

    public boolean existeEditorial(Editorial editorial) {
        conectar();
        Editorial editorialExiste;
        try {
            editorialExiste = (Editorial) em.createQuery(
                            "SELECT e FROM Editorial e WHERE e.nombre LIKE :nombre")
                    .setParameter("nombre", editorial.getNombre())
                    .getSingleResult();
        } catch (Exception e) {
            return false;
        }
        desconectar();
        return editorialExiste != null;
    }

    public void buscarPorNombre(String nombre) {
        conectar();
        List<Editorial> editoriales;
        try {
            editoriales = em.createQuery("SELECT e FROM Editorial e WHERE e.nombre LIKE :nombre")
                    .setParameter("nombre", nombre)
                    .getResultList();
            if (editoriales.size() > 0) {
                for (Editorial e : editoriales) {
                    System.out.println(e);
                }
            } else {
                System.out.println("No se encontraron resultados");
            }
        } catch (Exception e) {
            System.out.println("Error en la busqueda: " + e.getMessage());
            return;
        }
        desconectar();
    }

    public void altaBajaEditorial(Integer id) {
        conectar();
        Editorial editorial = em.find(Editorial.class, id);
        if (editorial != null) {
            if (editorial.getAlta()) {
                editorial.setAlta(false);
                System.out.println("Se dio de baja la Editorial");
            } else {
                editorial.setAlta(true);
                System.out.println("Se dio de alta la Editorial");
            }
            em.getTransaction().begin();
            em.merge(editorial);
            em.getTransaction().commit();
        } else {
            System.out.println("No se encontro la editorial");
        }
        desconectar();
    }
}