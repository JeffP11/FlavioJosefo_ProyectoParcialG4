/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Iterable;

import Iterator.Iterator;

/**
 * Interface Iterable parametrizada por tipo para implementar iterator().
 * @author Grupo 4
 */
public interface Iterable<E> {
    Iterator<E> iterator();
}
