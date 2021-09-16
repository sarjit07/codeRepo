import java.util.HashMap;
import java.util.Map;

public class LRU {

    private int size;
    private final int capacity;
    private Map<Integer, CacheNode> cache;
    private CacheNode head, tail;

    public LRU(final int capacity) {
        this.size = 0;
        this.capacity = capacity;
        this.cache = new HashMap<>();
        head = new CacheNode();
        tail = new CacheNode();
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        CacheNode node = this.cache.get(key);
        if (node == null) return -1;
        removeNode(node);
        addNode(node);
        return node.value;
    }

    public void put(int key, int value) {
        CacheNode node = this.cache.get(key);
        if (node == null) {
            CacheNode node1 = new CacheNode(key, value);
            cache.put(key, node1);
            addNode(node1);
            ++size;
            if (size > capacity) {
                CacheNode tail = popTail();
                cache.remove(tail.key);
                --size;
            }
        } else {
            node.value = value;
            removeNode(node);
            addNode(node);
        }
    }

    private void addNode(CacheNode node) {
        //adding in front of head always. As head is dummy.
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(CacheNode node) {
        CacheNode prev = node.prev;
        CacheNode next = node.next;
        prev.next = next;
        next.prev = prev;
    }

    private CacheNode popTail() {
        CacheNode res = tail.prev;
        removeNode(res);
        return res;
    }
}

class CacheNode {
    int key;
    int value;
    CacheNode next;
    CacheNode prev;

    CacheNode() {
    }

    CacheNode(int key, int value) {
        this.key = key;
        this.value = value;
    }
}