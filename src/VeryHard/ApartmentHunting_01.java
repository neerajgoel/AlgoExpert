package VeryHard;

import java.util.List;
import java.util.Map;

/**
 * O( nm ) time - worst case
 * n = number of blocks
 * m = number of requirements
 */
public class ApartmentHunting_01 {
    private static int[][] getFwrdResult(List<Map<String, Boolean>> blocks, String[] reqs){
        int res[][] = new int[ reqs.length ][ blocks.size() + 1 ];
        for(int i=0 ; i<reqs.length ; i++)
            res[i][0] = -1;
        Map<String, Boolean> block;
        for(int i=0 ; i<reqs.length ; i++){
            String req = reqs[i];
            for(int j=1 ; j<blocks.size()+1 ; j++){
                block = blocks.get(j-1);
                if( block.get( req ) )
                    res[i][j] = 0;
                else if( res[i][j-1] > -1 )
                    res[i][j] = res[i][j-1] + 1;
                else
                    res[i][j] = -1;
            }
        }
        return res;
    }

    private static int[][] getRevResult(List<Map<String, Boolean>> blocks, String[] reqs){
        int res[][] = new int[ reqs.length ][ blocks.size() + 1 ];
        for(int i=0 ; i<reqs.length ; i++)
            res[i][blocks.size()] = -1;
        Map<String, Boolean> block;
        for(int i=0 ; i<reqs.length ; i++){
            String req = reqs[i];
            for(int j=blocks.size()-1 ; j>=0 ; j--){
                block = blocks.get(j);
                if( block.get( req ) )
                    res[i][j] = 0;
                else if( res[i][j+1] > -1 )
                    res[i][j] = res[i][j+1] + 1;
                else
                    res[i][j] = -1;
            }
        }
        return res;
    }

    private static int calc(int x, int y){
        if( x == -1 )
            return y;
        else if( y == -1 )
            return x;
        return Math.min(x, y);
    }

    private static int getResult(int frwdResult[][], int revResult[][] ){
        int min = Integer.MAX_VALUE;
        int res = -1;
        int temp;
        for(int j=1 ; j<frwdResult[0].length ; j++){
            int max = Integer.MIN_VALUE;
            for(int i=0 ; i<frwdResult.length ; i++){
                temp = calc( frwdResult[i][j], revResult[i][j-1] );
                if( temp > max )
                    max = temp;
            }
            if( min > max ){
                min = max;
                res = j-1;
            }
        }
        return res;
    }

    private static void getResult(int res[][]){
        System.out.println();
        for(int i=0 ; i<res.length ; i++){
            for(int j=0 ; j<res[0].length ; j++){
                System.out.print( res[i][j] + "\t" );
            }
            System.out.println();
        }
    }

    public static int apartmentHunting(List<Map<String, Boolean>> blocks, String[] reqs) {
        int frwdResult[][] = getFwrdResult( blocks, reqs);
        int revResult[][] = getRevResult( blocks, reqs);
        //getResult(revResult);
        int res = getResult( frwdResult, revResult);
        System.out.println("Result : " + res);
        return res;
    }

    public static void main(String[] args) {

    }
}
