package VeryHard;

import java.util.*;

/**
 * O( 2^(V) * (V+E) ) time - worst case
 * This solution is better than AirportConnections_01 solution
 */
public class AirportConnections_02 {

    static int lookup(String airport, HashMap<String, Integer> airportSet){
        return airportSet.get(airport);
    }

    enum color{
        white, gray, black
    }

    static boolean checkIfAllAirportsAreRecheable_BFS(boolean adjMat[][],
                                                      int airportsSize,
                                                      String startingAirport,
                                                      HashMap<String, Integer> airportSet){
        color colors[] = new color[airportsSize];
        int distance[] = new int[airportsSize];
        for(int i=0 ; i<airportsSize ; i++){
            colors[i] = color.white;
            distance[i] = Integer.MAX_VALUE;
        }

        int s = lookup(startingAirport, airportSet);
        colors[s] = color.gray;
        distance[s] = 0;
        Queue<Integer> que = new ArrayDeque<>();
        que.add(s);
        while( !que.isEmpty() ){
            int u = que.poll();
            for(int j=0 ; j<adjMat[u].length; j++){
                if( adjMat[u][j] && colors[j] == color.white ){
                    que.add(j);
                    distance[j] = distance[u] + 1;
                    colors[j] = color.gray;
                }
            }
            colors[u] = color.black;
        }
        for(int d : distance){
            if(  d == Integer.MAX_VALUE )
                return false;
        }
        return true;
    }

    static int result = Integer.MAX_VALUE;

    static void check(boolean adjMat[][], int airportSize, int j,
                      String startingAirport,
                      HashMap<String, Integer> airportSet,
                      int currentChanges){
        if( j >= airportSize ){
            boolean res = checkIfAllAirportsAreRecheable_BFS(adjMat, airportSize, startingAirport, airportSet);
            if( res && currentChanges < result ){
                result = currentChanges;
            }
            return;
        }
        int i = lookup(startingAirport, airportSet);
        check(adjMat, airportSize, j+1, startingAirport, airportSet, currentChanges );
        if( adjMat[i][j] )
            return;
        adjMat[i][j] = true;
        check(adjMat, airportSize, j+1, startingAirport, airportSet, currentChanges + 1);
        adjMat[i][j] = false;
    }

    public static int airportConnections(
            List<String> airports, List<List<String>> routes, String startingAirport) {
        // init
        boolean adjMat[][] = new boolean[airports.size()][airports.size()];
        for(int i=0 ; i<airports.size() ; i++){
            for(int j=0 ; j<airports.size() ; j++){
                adjMat[i][j] = false;
            }
        }
        HashMap<String, Integer> airportSet = new HashMap<>();
        int i=0;
        for(String airport : airports){
            airportSet.put(airport, i);
            i++;
        }
        for(List<String> l : routes){
            adjMat[ lookup(l.get(0), airportSet ) ][ lookup(l.get(1), airportSet) ] = true;
        }

        // run BFS

        check(adjMat, airports.size(),0, startingAirport, airportSet, 0 );
        return result;
    }

    public static void main(String[] args) {
        System.out.println("*** started ***");
//        List<String> AIRPORTS =
//                new ArrayList<String>(
//                        Arrays.asList(
//                                "BGI", "CDG", "DEL", "DOH", "DSM", "EWR", "EYW", "HND", "ICN", "JFK", "LGA", "LHR",
//                                "ORD", "SAN", "SFO", "SIN", "TLV", "BUD"));
//
//        String STARTING_AIRPORT = "LGA";
//
//        List<List<String>> routes = new ArrayList<List<String>>();
//        routes.add(new ArrayList<String>(Arrays.asList("DSM", "ORD")));
//        routes.add(new ArrayList<String>(Arrays.asList("ORD", "BGI")));
//        routes.add(new ArrayList<String>(Arrays.asList("BGI", "LGA")));
//        routes.add(new ArrayList<String>(Arrays.asList("SIN", "CDG")));
//        routes.add(new ArrayList<String>(Arrays.asList("CDG", "SIN")));
//        routes.add(new ArrayList<String>(Arrays.asList("CDG", "BUD")));
//        routes.add(new ArrayList<String>(Arrays.asList("DEL", "DOH")));
//        routes.add(new ArrayList<String>(Arrays.asList("DEL", "CDG")));
//        routes.add(new ArrayList<String>(Arrays.asList("TLV", "DEL")));
//        routes.add(new ArrayList<String>(Arrays.asList("EWR", "HND")));
//        routes.add(new ArrayList<String>(Arrays.asList("HND", "ICN")));
//        routes.add(new ArrayList<String>(Arrays.asList("HND", "JFK")));
//        routes.add(new ArrayList<String>(Arrays.asList("ICN", "JFK")));
//        routes.add(new ArrayList<String>(Arrays.asList("JFK", "LGA")));
//        routes.add(new ArrayList<String>(Arrays.asList("EYW", "LHR")));
//        routes.add(new ArrayList<String>(Arrays.asList("LHR", "SFO")));
//        routes.add(new ArrayList<String>(Arrays.asList("SFO", "SAN")));
//        routes.add(new ArrayList<String>(Arrays.asList("SFO", "DSM")));
//        routes.add(new ArrayList<String>(Arrays.asList("SAN", "EYW")));

        List<String> AIRPORTS =
                new ArrayList<String>(
                        Arrays.asList(
                                "A",
                                "B", "C", "D", "E"));

        String STARTING_AIRPORT = "C";

        List<List<String>> routes = new ArrayList<List<String>>();
        routes.add(new ArrayList<String>(Arrays.asList("B", "C")));
        routes.add(new ArrayList<String>(Arrays.asList("C", "D")));
        routes.add(new ArrayList<String>(Arrays.asList("D", "E")));
        routes.add(new ArrayList<String>(Arrays.asList("E", "D")));
        routes.add(new ArrayList<String>(Arrays.asList("A", "B")));



        int res = airportConnections(AIRPORTS, routes, STARTING_AIRPORT);
        System.out.println("RESULT : " + res);
        System.out.println("*** ended ***");
    }

}
