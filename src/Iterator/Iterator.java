/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Iterator;

/**
 * Interface Iterator parametrizada por tipo.
 * @author Grupo 4
 */
public interface Iterator<E> {

    boolean hasNext();

    E next();
}
