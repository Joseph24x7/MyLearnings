package com.mylearnings.java.java_code.custom_collections;

public class CustomList<T> {

    int size = 0;
    private static final int DEFAULT_CAPACITY = 10;

    private Object[] elements;

    public CustomList() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    public boolean add(T element) {

        if (size == elements.length) {
            resize();
        }
        elements[size++] = element;
        return true;
    }

    public boolean remove(T element) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(element)) {
                for (int j = i; j < size - 1; j++) {
                    elements[j] = elements[j + 1];
                }
                elements[size - 1] = null;
                size--;
                return true;
            }
        }
        return false;
    }

    public boolean remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        elements[size - 1] = null;
        size--;
        return true;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (T) elements[index];
    }

    public boolean contains(T element) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(element)) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return size;
    }

    private void resize() {
        int newSize = elements.length * 2;
        Object[] newElements = new Object[newSize];
        System.arraycopy(elements, 0, newElements, 0, elements.length);
        elements = newElements;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append(elements[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

}

class ListMainClass {
    void main() {
        CustomList<String> customList = new CustomList<>();
        customList.add("element1");
        customList.add("elrement2");
        customList.add("elem4ent2");
        customList.add("element2");
        customList.add("element2");
        customList.add("elemfent2");
        customList.add("element2");
        customList.add("ele5ment2");
        customList.add("elem4ent2");
        customList.add("eleme4nt2");
        customList.add("ele3ment2");
        customList.add("ele3ment2");
        System.out.println(customList);
        System.out.println(customList.size);
        System.out.println(customList.get(3));
        System.out.println(customList.contains("element2111"));
        System.out.println(customList.remove("element211111"));
        System.out.println(customList.remove(5));
        System.out.println(customList.remove(1));
        System.out.println(customList.remove(2));
        System.out.println(customList);
    }
}