package VeryHard;

import java.util.*;

/**
 * O( (V+E) + V*(V+E) +  VlogV ) time
 */
public class AirportConnections_03 {

    static HashMap<String, Integer> airportMap;
    static int adjMatrix[][];
    static final int white = 0;
    static final int gray = 1;
    static final int black = 2;
    static int distance[];
    static int color[];

    static void init(List<String> airports, List<List<String>> routes){
        airportMap = new HashMap<>();
        int i=0;
        for(String air : airports){
            airportMap.put(air, i);
            i++;
        }
        int s = airports.size();
        adjMatrix = new int[s][s];
        for(i=0;i<s;i++)
            for(int j=0 ; j<s ; j++)
                adjMatrix[i][j] = 0;
        for(List<String> route : routes){
            adjMatrix[ lookup(route.get(0)) ][ lookup(route.get(1)) ] = 1;
        }
        distance = new int[s];
        for(i=0 ; i<s ; i++){
            distance[i] = Integer.MAX_VALUE;
        }
        initColor(airports);
    }

    static void initColor(List<String> airports){
        int s = airports.size();
        color = new int[s];
        for(int i=0 ; i<s ; i++){
            color[i] = white;
        }
    }

    static int lookup(String airport){
        return airportMap.get(airport);
    }

    static void dfs_visit(int s){
        distance[s] = 0;
        color[s] = gray;
        int size = distance.length;
        for(int i=0 ; i<size ; i++){
            if( adjMatrix[s][i] == 1 && color[i] == white ){
                dfs_visit(i);
            }
        }
        color[s] = black;
    }

    static void dfsToCountRecheableAirports(int s, Set<Integer> nonVisitedSet){
        color[s] = gray;
        int size = distance.length;
        for(int i=0 ; i<size ; i++){
            if( adjMatrix[s][i] == 1 && color[i] == white && nonVisitedSet.contains(i) ){
                dfsToCountRecheableAirports(i, nonVisitedSet);
            }
        }
        color[s] = black;
    }


    private static class Grp implements Comparator<Grp>{
        int airport;
        int count;
        List<Integer> reacheableAirports;
        public Grp(){}
        public Grp(int a, int b, List<Integer> reacheableAirports){
            airport = a; count = b;
            this.reacheableAirports = reacheableAirports;
        }
        @Override
        public int compare(Grp o1, Grp o2) {
            return o2.count - o1.count;
        }
        @Override
        public String toString() {
            return airport + "\t" + count;
        }
        public static void print(List<Grp> grps){
            System.out.println("\nSorted : ");
            for(Grp g : grps){
                System.out.println("\t" + g.airport + "\t" + g.count + "\t" + g.reacheableAirports.size());
            }
        }
    }

    public static int airportConnections(
            List<String> airports, List<List<String>> routes, String startingAirport) {
        init(airports, routes);

        dfs_visit(lookup(startingAirport));

        Set<Integer> nonVisitedSet = new HashSet<>();
        for(int i = 0; i< distance.length ; i++){
            if( distance[i] == Integer.MAX_VALUE )
                nonVisitedSet.add(i);
        }
        int result = 0;
        ArrayList<Grp> nonVisitedAirportList = new ArrayList<>();
        do{
            nonVisitedAirportList.clear();
            for(int i = 0; i< distance.length ; i++){
                if( nonVisitedSet.contains(i) ){
                    initColor(airports);
                    dfsToCountRecheableAirports(i, nonVisitedSet);
                    // count black colors
                    List<Integer> reachables = new ArrayList<>();
                    for(int j = 0; j< color.length ; j++){
                        if( color[j] == black ){
                            reachables.add(j);
                        }
                    }
                    nonVisitedAirportList.add( new Grp(i, reachables.size(), reachables) );
                }
            }
            Collections.sort(nonVisitedAirportList, new Grp());
            if(nonVisitedAirportList.isEmpty())
                break;
            Grp gp = nonVisitedAirportList.remove(0);
            // add route
            result++;
            for(int r : gp.reacheableAirports){
                nonVisitedSet.remove(r);
            }
        }while( !nonVisitedAirportList.isEmpty() );
        return result;
    }

    public static void main(String[] args) {
        System.out.println("*** started ***");
        List<String> AIRPORTS =
                new ArrayList<String>(
                        Arrays.asList(
                                "BGI", "CDG", "DEL", "DOH",
                                "DSM", "EWR", "EYW", "HND",
                                "ICN", "JFK", "LGA", "LHR",
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

//        List<String> AIRPORTS =
//                new ArrayList<String>(
//                        Arrays.asList(
//                                "A",
//                                "B", "C", "D", "E"));
//
//        String STARTING_AIRPORT = "C";
//
//        List<List<String>> routes = new ArrayList<List<String>>();
//        routes.add(new ArrayList<String>(Arrays.asList("B", "C")));
//        //routes.add(new ArrayList<String>(Arrays.asList("C", "D")));
//        routes.add(new ArrayList<String>(Arrays.asList("D", "E")));
//        routes.add(new ArrayList<String>(Arrays.asList("E", "D")));
//        //routes.add(new ArrayList<String>(Arrays.asList("A", "B")));


        int res = airportConnections(AIRPORTS, routes, STARTING_AIRPORT);
        System.out.println("RESULT : " + res);
        System.out.println("*** ended ***");
    }


}
