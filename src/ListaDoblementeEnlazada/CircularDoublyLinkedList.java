/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ListaDoblementeEnlazada;

import Iterator.Iterator;
import List.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Clase Circular Doubly Linked List parametrizada por tipo.
 * @author Grupo 4
 */
public class CircularDoublyLinkedList<E> implements List<E> {
    
    private Node<E> first, last;
    private int effectiveSize;

    /**
     * Constructor de la clase
     */
    public CircularDoublyLinkedList() {
        first = last = null;
        effectiveSize = 0;
    }

    /**
     * Agrega elemento al inicio de la lista
     */
    @Override
    public boolean addFirst(E element) {
        Node<E> nodo = new Node<>(element);
        
        if (element == null) {
            return false;
        } else if (isEmpty()) {
            nodo.setNext(nodo);
            nodo.setPrev(nodo);
            first = nodo;
            last = first;
        } else {
            nodo.setPrev(nodo);
            last.setNext(nodo);
            first.setPrev(nodo);
            nodo.setNext(nodo);
            first = nodo;
        }
        effectiveSize++;
        return true;
    }

    /**
     * Agrega elemento al final de la lista
     */
    @Override
    public boolean addLast(E element) {
        Node<E> nodo = new Node<>(element);
        
        if (element == null) {
            return false;
        } else if (isEmpty()) {
            nodo.setNext(nodo);
            nodo.setPrev(nodo);
            first = nodo;
            last = first;
        } else {
            nodo.setPrev(last);
            last.setNext(nodo);
            first.setPrev(nodo);
            nodo.setNext(first);
            last = nodo;
        }
        effectiveSize++;
        return true;
    }

    /**
     * Obtiene el primer elemento de la lista
     */
    @Override
    public E getFirst() {
        return first.getData();
    }

    /**
     * Obtiene el ultimo elemento de la lista
     */
    @Override
    public E getLast() {
        return last.getData();
    }

    /**
     * Verifica si la lista esta vacia.
     */
    @Override
    public boolean isEmpty() {
        return (first == null && last == null);
    }

    /**
     * Devuelve el tamaño de la lista
     */
    @Override
    public int size() {
        return effectiveSize;
    }

    /**
     * Obtiene el primer nodo de la lista
     */
    public Node getFirstNode() {
        return first;
    }

    /**
     * Obtiene el ultimo nodo de la lista
     */
    public Node getLastNode() {
        return last;
    }

    /**
     * Metodo toString de la lista
     */
    @Override
    public String toString() {
        String cadena = "";
        Node<E> nodoViajero = first;
        cadena = cadena + first.getData() + ",";
        nodoViajero = nodoViajero.getNext();
        while (nodoViajero != null) {
            if (nodoViajero == last) {
                cadena = cadena + nodoViajero.getData();
            } else {
                cadena = cadena + nodoViajero.getData() + ",";
            }
            nodoViajero = nodoViajero.getNext();
        }
        return cadena;
    }

    /**
     * Metodo abstracto iterator
     */
    @Override
    public Iterator<E> iterator() {
        return new MyIterator<E>();
    }
    
    @Override
    public boolean insert(int index, E element) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public E remove(int index) {
        Node<E> actual = first;
        Node<E> anterior = last;
        boolean encontrado = false;
        
        do {            
            anterior = actual;
            actual = actual.getNext();
        } while (actual != first);
        
        return null;
        
    }
    
    public class MyIterator<E> implements Iterator<E> {
        
        Node<E> nodoViajero = getFirstNode();
        
        @Override
        public boolean hasNext() {
            return nodoViajero != null;
        }
        
        @Override
        public E next() {
            E elemento = null;
            elemento = nodoViajero.getData();
            nodoViajero = nodoViajero.getNext();
            return elemento;
        }
        
    }
    
    public ListIterator<E> listIteratorNode(int i) {
        ListIterator<E> iter = new ListIterator() {
            Node<E> nodoViajero = getNode(i);
            int puntero = effectiveSize;
            
            @Override
            public boolean hasNext() {
                boolean verify = false;
                
                if (puntero != 0 && nodoViajero != null) {
                    verify = true;
                }
                
                return verify;
            }
            
            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                } else {
                    E elemento = nodoViajero.getData();
                    nodoViajero = nodoViajero.getNext();
                    return elemento;
                }
            }
            
            @Override
            public boolean hasPrevious() {
                boolean verify = false;
                
                if (puntero != 0 && nodoViajero != null) {
                    verify = true;
                }
                
                return verify;
            }
            
            @Override
            public E previous() {
                
                E elemento = nodoViajero.getData();
                nodoViajero = nodoViajero.getPrev();
                return elemento;
            }
            
            @Override
            public int nextIndex() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
            @Override
            public int previousIndex() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

            }
            
            @Override
            public void set(Object e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
            @Override
            public void add(Object e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        return iter;
    }
    
    /**
     * Metodo listIterator de la clase ListIterator
     */
    public ListIterator<E> listIterator() {
        ListIterator<E> iter = new ListIterator() {
            Node<E> nodoViajero = first;
            int puntero = effectiveSize;
            
            @Override
            public boolean hasNext() {
                boolean verify = false;
                
                if (puntero != 0 && nodoViajero != null) {
                    verify = true;
                } else {
                    puntero = effectiveSize;
                }
                
                return verify;
            }
            
            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                } else {
                    E elemento = nodoViajero.getData();
                    nodoViajero = nodoViajero.getNext();
                    puntero--;
                    return elemento;
                }
            }
            
            @Override
            public boolean hasPrevious() {
                boolean verify = false;
                
                if (puntero != 0 && nodoViajero != null) {
                    verify = true;
                } else {
                    puntero = effectiveSize;
                }
                
                return verify;
            }
            
            @Override
            public E previous() {
                nodoViajero = nodoViajero.getPrev();
                E elemento = nodoViajero.getData();
                puntero--;
                return elemento;
            }
            
            @Override
            public int nextIndex() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
            @Override
            public int previousIndex() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
            @Override
            public void set(Object e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
            @Override
            public void add(Object e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        return iter;
    }
    
    @Override
    public boolean removeFirst() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean removeLast() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean contains(E element) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public E get(int index) {
        E nodoEncontrado = null;
        if (!isEmpty()) {
            if (index == 0) {
                nodoEncontrado = first.getData();
            } else {
                if (index > 0 && index < effectiveSize) {
                    Node<E> aux = first;
                    for (int i = 0; i < effectiveSize - 1; i++) {
                        if (i == index - 1) {
                            Node<E> mod = aux.getNext();
                            nodoEncontrado = mod.getData();
                        } else {
                            aux = aux.getNext();
                        }
                    }
                }
            }
        }
        return nodoEncontrado;
        
    }
    
    public Node<E> getNode(int index) {
        Node<E> nodoEncontrado = null;
        if (!isEmpty()) {
            if (index == 0) {
                nodoEncontrado = first;
            } else {
                if (index > 0 && index < effectiveSize) {
                    Node<E> aux = first;
                    for (int i = 0; i < effectiveSize - 1; i++) {
                        if (i == index - 1) {
                            Node<E> mod = aux.getNext();
                            nodoEncontrado = mod;
                        } else {
                            aux = aux.getNext();
                        }
                    }
                }
            }
        }
        return nodoEncontrado;
        
    }
    
    @Override
    public int indexOf(E element) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean remove(E element) {
        Node<E> actual = first;
        Node<E> anterior = last;
        boolean eliminado = false;
        do {            
            if (actual.getData() == element) {
                if (actual == first) {
                    first = first.getNext();
                    last.setNext(first);
                    first.setPrev(last);
                }else if (actual == last) {
                    last = anterior;
                    first.setPrev(last);
                    last.setNext(first);
                }else{
                    anterior.setNext(actual.getNext());
                    actual.getNext().setPrev(anterior);
                }
                eliminado = true;
            }
            anterior = actual;
            actual = actual.getNext();
        } while (actual != first && eliminado == false);
        return eliminado;
    }
    
    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
