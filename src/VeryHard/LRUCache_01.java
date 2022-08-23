package VeryHard;

import java.util.HashMap;

/**
 * O( 1 ) time
 */
public class LRUCache_01 {

    static class DLLNode{
        String key;
        Integer val;
        DLLNode next;
        DLLNode prev;
        DLLNode(String k, Integer v){
            this.key = k;
            this.val = v;
            next = prev = null;
        }
        String print(){
            return "(" + key + "," + val + ")";
        }
        void resetPointers(){
            next = prev = null;
        }
    }

    static class DLL{
        DLLNode head, tail;
        DLL(){
            head = tail = null;
        }
        DLLNode peekAtHead(){
            return head;
        }
        void insertAtHead(DLLNode node){
            if( head == null && tail == null ){
                head = tail = node;
            }
            else {
                node.next = head;
                head.prev = node;
                head = node;
            }
        }
        void remove(DLLNode node){
            if( head == node && tail == node ){
                // single node present
                head = tail = null;
            }
            else if( head == node ){
                head.next.prev = null;
                head = head.next;
            }
            else if( tail == node ){
                tail = tail.prev;
                tail.next = null;
            }
            else{
                // more than 3 and delete node
                DLLNode before = node.prev;
                DLLNode after = node.next;
                before.next = after;
                after.prev = before;
                node.next = node.prev = null;
            }
            node.resetPointers();
        }

        DLLNode removeTail(){
            if( tail == null )
                return null;
            if( head == tail ){
                DLLNode node = tail;
                node.next = node.prev = null;
                head = tail = null;
                return node;
            }
            DLLNode node = tail;
            tail = tail.prev;
            tail.next = null;
            node.next = node.prev = null;
            return node;
        }

        void print(){
            System.out.println("DLL:");
            if( head == null && tail == null )
                System.out.println("\t null");
            else{
                DLLNode nd = head;
                while( nd != null ){
                    System.out.print("\t" + nd.print() );
                    nd = nd.next;
                }
                System.out.println();
                nd = tail;
                while( nd != null ){
                    System.out.print("\t" + nd.print() );
                    nd = nd.prev;
                }
                System.out.println();
            }
        }
    }

    static class LRUCache {
        int maxSize;
        HashMap<String, DLLNode> map = new HashMap<>();
        DLL list = new DLL();

        public LRUCache(int maxSize) {
            this.maxSize = maxSize > 1 ? maxSize : 1;
        }

        public void insertKeyValuePair(String key, int value) {
            list.print();
            DLLNode node;
            if( map.containsKey(key) ){
                node = map.get(key);
                // update value
                node.val = value;
                // remove node from DLL
                list.remove( node );
            }
            else{
                if( map.size() >=  maxSize){
                    // remove LRU node
                    //list.print();
                    DLLNode tail = list.removeTail();
                    // list.print();
                    map.remove(tail.key);
                }
                node = new DLLNode(key, value);
            }
            // insert node at head of DLL
            list.insertAtHead(node);
            // insert in map
            map.put(key, node);

            list.print();
        }

        public LRUResult getValueFromKey(String key) {
            if( !map.containsKey(key) ){
                return null;
            }
            DLLNode node = map.get(key);
            // remove from DLL and insert at front
            System.out.println(node.print());
            list.print();
            list.remove(node);
            list.print();
            list.insertAtHead(node);
            list.print();
            System.out.println("------------------------");
            return new LRUResult(true, node.val);
        }

        public String getMostRecentKey() {
            // Write your code here.
            return list.peekAtHead().key;
        }
    }

    static class LRUResult {
        boolean found;
        int value;

        public LRUResult(boolean found, int value) {
            this.found = found;
            this.value = value;
        }
    }

    public static void main(String[] args) {

    }
}
