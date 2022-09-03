package VeryHard;

import java.util.*;

// O(n^2) - runtime complexity
// O(n) - space time complexity
class LineThroughPoints_01 {

  private void print(Map<Integer, Map<Integer, Integer>> map){
    for(int dx : map.keySet()){
      System.out.println(dx);
      Map<Integer, Integer> m = map.get(dx);
      for(int dy : m.keySet()){
        int pt = m.get(dy);
        System.out.print("\t" + dy + ",\t" + pt);
      }
      System.out.println();
    }
    System.out.println();
    System.out.println();
  }

  private int gcd(int a, int b){
    if( b == 0 )
      return a;
    return gcd(b, a%b);
  }

  public int lineThroughPoints(int[][] points) {
    if( points.length == 1 )
      return 1;
    Map<Integer, Map<Integer, Integer>> pntsMap = new HashMap<>();
    int maxPoints = Integer.MIN_VALUE;
    for(int i=0 ; i <points.length ; i++){
      pntsMap.clear();
      for(int j=0 ; j<points.length ; j++){
        if( i == j )
          continue;
        int dx = points[j][0] - points[i][0];
        int dy = points[j][1] - points[i][1];
        int gcd = gcd(dx, dy);
        dx = dx/gcd;
        dy = dy/gcd;
        
        int currPt;
        if( pntsMap.containsKey(dx) && pntsMap.get(dx).containsKey(dy) ){
          currPt = pntsMap.get(dx).get(dy) + 1;
          pntsMap.get(dx).put(dy, currPt);
        }
        else if( !pntsMap.containsKey(dx) ){
          // dx is not there
          // then dy is also not there
          Map<Integer, Integer> map = new HashMap<>();
          map.put(dy, 2);
          pntsMap.put(dx, map);
          currPt = 2;
        }
        else{
          // dx is there but not dy
          Map<Integer, Integer> map = pntsMap.get(dx);
          map.put(dy, 2);
          currPt = 2;
        }
        // check with max Points
        if( maxPoints < currPt )
          maxPoints = currPt;
        //print(pntsMap);
      }
    }
    System.out.println("\n\nResult : " + maxPoints);
    return maxPoints;
  }
}
