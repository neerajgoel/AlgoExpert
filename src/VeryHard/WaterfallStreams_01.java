package VeryHard;

/**
 * O(w^2 * h) Run time complexity
 * w : width of matrix
 * h : height of matrix
 */
public class WaterfallStreams_01 {
    private void print(double[][] arr){
        System.out.println("Mat : ");
        for(double[] ar : arr){
            for(double d : ar){
                System.out.print(d + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    public double[] waterfallStreams(double[][] arr, int source) {
        arr[0][source] = 100;
        if( arr.length == 2 ){
            arr[1][source] = 100;
            return arr[1];
        }
        // swap 1 with -1
        for(int i=0 ; i<arr.length ; i++){
            for(int j=0 ; j<arr[0].length ; j++){
                if( arr[i][j] == 1 )
                    arr[i][j] = -1;
            }
        }
        // handle first row
        arr[0][source] = 100;
        if( arr[1][source] == -1 ){
            // go left
            for(int k=source-1 ; k>=0 ; k--){
                arr[0][k] = arr[0][source]/2;
                if( arr[1][k] == 0 )
                    break;
            }
            // go right
            for(int k=source+1 ; k<arr[0].length ; k++){
                arr[0][k] = arr[0][source]/2;
                if( arr[1][k] == 0 )
                    break;
            }
        }

        for(int i=1 ; i<arr.length-1 ; i++){
            for(int j=0 ; j<arr[0].length ; j++){
                System.out.println("i:" + i + "\tj:" + j);
                if( arr[i-1][j] == 0 || arr[i-1][j] == -1  || arr[i][j] == -1 )
                    continue;
                print(arr);
                if( arr[i][j] == 0 )
                    arr[i][j] = arr[i-1][j];
                else
                    arr[i][j] += arr[i-1][j];

                // check for blockage down
                if( arr[i+1][j] == 0 )
                    // no blockage - so no right or left flow
                    continue;
                // check for right flow
                double flowRate = arr[i-1][j]/2;
                for(int k=j+1 ; k<arr[0].length ; k++){
                    if( arr[i][k] == -1 )
                        break;
                    arr[i][k] += flowRate;
                    if( arr[i+1][k] == 0 )
                        break;
                }
                print(arr);
                // check for left flow
                for(int k=j-1 ; k>=0 ; k--){
                    if( arr[i][k] == -1 )
                        break;
                    arr[i][k] += flowRate;
                    if( arr[i+1][k] == 0 )
                        break;
                }
                print(arr);
            }
        }

        int r = arr.length - 1;
        for(int j=0 ; j<arr[0].length ; j++){
            if( arr[r-1][j] != -1 ){
                arr[r][j] = arr[r-1][j];
            }
        }
        print(arr);
        return arr[arr.length-1];
    }

    public static void main(String[] args) {
        double[][] array = new double[][]{
                {0, 0, 0, 0, 0, 0, 0},
              {1, 0, 0, 0, 0, 0, 0},
              {0, 0, 1, 1, 1, 0, 0},
              {0, 0, 1, 0, 0, 0, 1},
              {0, 0, 1, 0, 1, 1, 0},
              {0, 1, 0, 0, 0, 0, 0},
              {0, 0, 0, 1, 0, 0, 0},
              {0, 0, 1, 0, 1, 0, 0},
              {0, 0, 0, 0, 0, 0, 0}
            };

//        double[][] array = new double[][]{
//                {0, 0, 0, 0, 0},
//                {1, 0, 1, 0, 1},
//                {1, 0, 0, 0, 1},
//                {1, 1, 0, 1, 1},
//                {0, 0, 0, 0, 0}
//        };

        System.out.println("Input array : ");
        for(double[] ar : array){
            for(double d : ar){
                System.out.print(d + "\t\t");
            }
            System.out.println();
        }
        System.out.println();

        new WaterfallStreams_01().waterfallStreams(array, 2);
    }
}
