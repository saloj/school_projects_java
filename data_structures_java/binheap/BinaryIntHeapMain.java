package binheap;

public class BinaryIntHeapMain {

    public static void main(String[] args) {
        BinaryIntHeap heap = new BinaryIntHeap();

        heap.insert(80);
        heap.insert(75);
        heap.insert(60);
        heap.insert(68);
        heap.insert(55);
        heap.insert(40);
        heap.insert(52);
        heap.insert(67);
        heap.insert(10);
        heap.insert(22);
        heap.insert(100);

        System.out.println(heap);
        System.out.println(heap.size());
        System.out.println(heap.isEmpty());
        System.out.println(heap.pullHighest());
    }
}
