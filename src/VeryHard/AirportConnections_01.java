package VeryHard;

import java.util.*;

/**
 * O( 2^(V^2) * (V+E) ) time
 * This solution takes lot of time.
 */
public class AirportConnections_01 {

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

    static void check(boolean adjMat[][], int airportSize, int i, int j,
                      String startingAirport,
                      HashMap<String, Integer> airportSet,
                      Integer result, int currentChanges){
        if( i >= airportSize || j >= airportSize ){
            boolean res = checkIfAllAirportsAreRecheable_BFS(adjMat, airportSize, startingAirport, airportSet);
            if( res && currentChanges < result ){
                result = currentChanges;
            }
            return;
        }
        int r,c;
        if( j < airportSize - 1 ){
            r = i;
            c = j + 1;
        }
        else{
            r = i + 1;
            c = 0;
        }
        boolean origState = adjMat[i][j];
        check(adjMat, airportSize, r, c, startingAirport, airportSet, result, currentChanges );
        adjMat[i][j] = !origState;
        check(adjMat, airportSize, r, c, startingAirport, airportSet, result, currentChanges );
        adjMat[i][j] = origState;
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

        Integer result = new Integer(Integer.MAX_VALUE);
        check(adjMat, airports.size(),0, 0, startingAirport, airportSet, result, 0 );
        return result;
    }

    public static void main(String[] args) {
        System.out.println("*** started ***");
        List<String> AIRPORTS =
                new ArrayList<String>(
                        Arrays.asList(
                                "BGI", "CDG", "DEL", "DOH", "DSM", "EWR", "EYW", "HND", "ICN", "JFK", "LGA", "LHR",
                                "ORD", "SAN", "SFO", "SIN", "TLV", "BUD"));

        String STARTING_AIRPORT = "LGA";

        List<List<String>> routes = new ArrayList<List<String>>();
        routes.add(new ArrayList<String>(Arrays.asList("DSM", "ORD")));
        routes.add(new ArrayList<String>(Arrays.asList("ORD", "BGI")));
        routes.add(new ArrayList<String>(Arrays.asList("BGI", "LGA")));
        routes.add(new ArrayList<String>(Arrays.asList("SIN", "CDG")));
        routes.add(new ArrayList<String>(Arrays.asList("CDG", "SIN")));
        routes.add(new ArrayList<String>(Arrays.asList("CDG", "BUD")));
        routes.add(new ArrayList<String>(Arrays.asList("DEL", "DOH")));
        routes.add(new ArrayList<String>(Arrays.asList("DEL", "CDG")));
        routes.add(new ArrayList<String>(Arrays.asList("TLV", "DEL")));
        routes.add(new ArrayList<String>(Arrays.asList("EWR", "HND")));
        routes.add(new ArrayList<String>(Arrays.asList("HND", "ICN")));
        routes.add(new ArrayList<String>(Arrays.asList("HND", "JFK")));
        routes.add(new ArrayList<String>(Arrays.asList("ICN", "JFK")));
        routes.add(new ArrayList<String>(Arrays.asList("JFK", "LGA")));
        routes.add(new ArrayList<String>(Arrays.asList("EYW", "LHR")));
        routes.add(new ArrayList<String>(Arrays.asList("LHR", "SFO")));
        routes.add(new ArrayList<String>(Arrays.asList("SFO", "SAN")));
        routes.add(new ArrayList<String>(Arrays.asList("SFO", "DSM")));
        routes.add(new ArrayList<String>(Arrays.asList("SAN", "EYW")));


        int res = airportConnections(AIRPORTS, routes, STARTING_AIRPORT);
        System.out.println("RESULT : " + res);
        System.out.println("*** ended ***");
    }

}
