package VeryHard;

/**
 * O(n) Run time complexity
 * O(1) space time complexity
 */
public class RearrangeLinkedList_01 {

    public static LinkedList rearrangeLinkedList(LinkedList head, int k) {
        if( head == null || head.next == null )
            return head;
        LinkedList h1, h2, h3, t1, t2, t3;
        h1 = h2 = h3 = t1 = t2 = t3 = null;
        LinkedList node = head;
        while( node != null ){
            if( node.value == k ){
                if( h2 == null )
                    h2 = t2 = node;
                else{
                    t2.next = node;
                    t2 = t2.next;
                }
            }
            else if( node.value < k ){
                // put in first LL
                if( h1 == null )
                    h1 = t1 = node;
                else{
                    t1.next = node;
                    t1 = t1.next;
                }
            }
            else{
                // put in third LL
                if( h3 == null )
                    h3 = t3 = node;
                else{
                    t3.next = node;
                    t3 = t3.next;
                }
            }
            node = node.next;
        }
        if( t1 != null )
            t1.next = null;
        if( t2 != null )
            t2.next = null;
        if( t3 != null )
            t3.next = null;
        LinkedList.print("h1", h1);
        LinkedList.print("h2", h2);
        LinkedList.print("h3", h3);

        // merging all LL's
        if( h1 == null ){
            if( h2 == null )
                head = h3;
            else{
                head = h2;
                t2.next = h3;
                if( t3 != null )
                    t3.next = null;
            }
        }
        else if( h2 == null ){
            if( h1 == null )
                head = h3;
            else{
                head = h1;
                t1.next = h3;
                if( t3 != null )
                    t3.next = null;
            }
        }
        else if(h3 == null)  {
            if( h1 == null )
                head = h2;
            else{
                head = h1;
                t1.next = h2;
                if( t2 != null )
                    t2.next = null;
            }
        }
        else{
            // all LL's are present
            head = h1;
            t1.next = h2;
            t2.next = h3;
            t3.next = null;
        }

        LinkedList.print("head", head);
        return head;
    }

    static class LinkedList {
        public int value;
        public LinkedList next;

        public LinkedList(int value) {
            this.value = value;
            next = null;
        }

        public static void print(String str, LinkedList node){
            System.out.print( str + " : ");
            if( node == null )
                System.out.println( "0" );
            while( node != null ){
                System.out.print( node.value + "\t");
                node = node.next;
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {

    }
}
