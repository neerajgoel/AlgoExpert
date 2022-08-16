package Hard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Average: O(n^2) time | O(n^2) space
 * Worst: O(n^3) time | O(n^2) space
 */

public class FourNumberSum {

    static class Grp{
        int first;
        int second;
        Grp(int _f, int _s){
            first = _f;
            second = _s;
        }
    }

    public static List<Integer[]> fourNumberSum(int[] arr, int ts) {
        HashMap<Integer, List<Grp>> bucks = new HashMap<>();
        List<Integer[]> res = new ArrayList<>();
        int df, sm;
        for(int i=1 ; i<arr.length ; i++){
            for(int j=i+1 ; j<arr.length ; j++){
                df = ts - arr[i] - arr[j];
                if( bucks.containsKey(df) ){
                    for(Grp p : bucks.get(df))
                        res.add( new Integer[] {arr[i], arr[j], p.first, p.second} );
                }
            }
            for(int k=0 ; k<i ; k++){
                sm = arr[k] + arr[i];
                if( !bucks.containsKey(sm) )
                    bucks.put(sm, new ArrayList<>());
                bucks.get(sm).add(new Grp(arr[k], arr[i]));
            }
        }
        return res;
    }

    public static void main(String[] args) {

    }

}
