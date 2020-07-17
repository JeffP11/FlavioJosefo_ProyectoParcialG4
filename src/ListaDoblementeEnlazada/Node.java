/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ListaDoblementeEnlazada;

/**
 * Clase Node  parametrizada por tipo para implementarla en la clase CircularDoublyLinkedList.
 * @author Grupo 4
 */
public class Node<E> {

    private Node<E> prev, next;
    private E data;

    public Node(E data) {
        this.data = data;
    }

    public Node<E> getPrev() {
        return prev;
    }

    public void setPrev(Node<E> prev) {
        this.prev = prev;
    }

    public Node<E> getNext() {
        return next;
    }

    public void setNext(Node<E> next) {
        this.next = next;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }
}
