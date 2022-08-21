package VeryHard;

import java.util.ArrayList;
import java.util.List;

/**
 * O( c1 + c2 ) time - worst case
 * c1 = number of meetins in cal 1
 * c2 = number of meetins in cal 2
 */
public class CalenderMatching_01 {

    private static List<StringMeeting> calcTimeline(List<StringMeeting> cal1,
                                                    List<StringMeeting> cal2){
        int i,j;
        i = j = 0;
        List<StringMeeting> res = new ArrayList<>();
        StringMeeting sm = null;
        while( i < cal1.size() && j < cal2.size() ){
            TimeType tt = cal1.get(i).compare(cal2.get(j));
            System.out.println( "i:" + i + "\tj:" + j + "\ttt : " + tt );
            if( tt == TimeType.smaller ){
                // add i to timeline
                sm = cal1.get(i);
                i++;
            }
            else if( tt == TimeType.bigger ){
                // add j to timeline
                sm = cal2.get(j);
                j++;
            }
            else{
                sm = cal1.get(i).mergeOverLap(cal2.get(j));
                System.out.println( "\t sm : " + sm );
                i++;
                j++;
            }
            // check for overlap with prev result
            if( res.size() > 0 && res.get(res.size()-1).compare( sm ) == TimeType.overlap  ){
                System.out.println( "\t\t overlap : " + res.get(res.size()-1).compare( sm ));
                sm = res.get(res.size()-1).mergeOverLap( sm );
                System.out.println( "\t\t sm : " + sm );
                res.set( res.size()-1, sm );
            }
            else
                res.add( sm );
        }
        while( i <cal1.size() ){
            if( res.size() > 0 && res.get(res.size()-1).compare( cal1.get(i) ) == TimeType.overlap  ){
                sm = res.get(res.size()-1).mergeOverLap( cal1.get(i) );
                res.set( res.size()-1, sm );
            }
            else
                res.add( cal1.get(i) );
            i++;
        }
        while( j <cal2.size() ){
            if( res.size() > 0 && res.get(res.size()-1).compare( cal2.get(j) ) == TimeType.overlap  ){
                sm = res.get(res.size()-1).mergeOverLap( cal2.get(j) );
                res.set( res.size()-1, sm );
            }
            else
                res.add( cal2.get(j) );
            j++;
        }
        return res;
    }

    private static List<StringMeeting> compress(List<StringMeeting> cal){
        for(int i=1 ; i<cal.size() ; i++){
            if( cal.get(i-1).compare( cal.get(i) ) == TimeType.overlap ){
                StringMeeting sm = cal.get(i-1).mergeOverLap( cal.get(i) );
                cal.set( i-1, sm );
                cal.remove(i);
                i--;
            }
        }

        System.out.println("compress : ");
        for(StringMeeting s : cal){
            System.out.println(s);
        }
        System.out.println();

        return cal;
    }

    public static List<StringMeeting> calendarMatching(
            List<StringMeeting> calendar1,
            StringMeeting dailyBounds1,
            List<StringMeeting> calendar2,
            StringMeeting dailyBounds2,
            int meetingDuration) {
        calendar1 = compress(calendar1);
        calendar2 = compress(calendar2);

        List<StringMeeting> timeLine = calcTimeline(calendar1, calendar2);

        StringMeeting st =  StringMeeting.compareTime(
                dailyBounds1.start,
                dailyBounds2.start
        ) < 0 ? dailyBounds2 : dailyBounds1;
        timeLine.add(0, new StringMeeting(st.start, st.start));

        StringMeeting end =  StringMeeting.compareTime(
                dailyBounds1.end,
                dailyBounds2.end
        ) < 0 ? dailyBounds1 : dailyBounds2;
        timeLine.add(new StringMeeting(end.end, end.end));

        System.out.println("timeLine : ");
        for(StringMeeting s : timeLine){
            System.out.println(s.start + " : " + s.end);
        }

        ArrayList<StringMeeting> result = new ArrayList<>();
        for(int i=1 ; i<timeLine.size() ; i++){
            int diff = timeLine.get(i-1).diffInMins( timeLine.get(i) );
            System.out.println("diff : " + diff);
            if( diff >= meetingDuration ){
                StringMeeting res = new StringMeeting( timeLine.get(i-1).end,
                        timeLine.get(i).start );
                if( StringMeeting.compareTime(st.start, res.start) <= 0
                        && StringMeeting.compareTime(res.end, end.end) <= 0)
                    result.add(res);
            }
        }

        System.out.println("\nResult : ");
        for(StringMeeting s : result){
            System.out.println(s.start + " : " + s.end);
        }
        System.out.println();
        return result;
    }

    static class StringMeeting {
        public String start;
        public String end;

        public StringMeeting(String start, String end) {
            this.start = start;
            this.end = end;
        }

        private static int getHr(String s1){
            return Integer.parseInt( s1.substring(0, s1.indexOf(':')) );
        }

        private static int getMin(String s1){
            return Integer.parseInt( s1.substring(s1.indexOf(':') + 1) );
        }

        int diffInMins(StringMeeting s2){
            StringMeeting s1 = this;
            int start = getHr(s1.end) * 60 + getMin(s1.end);
            int end = getHr(s2.start) * 60 + getMin(s2.start);
            return end-start;
        }

        static int compareTime(String s1, String s2){
            //System.out.println("\t\tSM + " + s1 + " : " + s2 );
            int h1,m1,h2,m2;
            h1 = getHr(s1);
            h2 = getHr(s2);
            if( h1 < h2 )
                return -1;
            else if( h1 == h2 ){
                m1 = getMin(s1);
                m2 = getMin(s2);
                if( m1 < m2)
                    return -1;
                else if( m1 == m2 )
                    return 0;
                return 1;
            }
            return 1;
        }

        public TimeType compare(StringMeeting s2){
            StringMeeting s1 = this;
            //System.out.println("\t\tSM + " + compareTime(s1.end, s2.start) );
            if( compareTime(s1.end, s2.start) < 0 ){
                return TimeType.smaller;
            }
            else if( compareTime(s1.start, s2.end) > 0 ){
                return TimeType.bigger;
            }
            return TimeType.overlap;
        }

        public StringMeeting mergeOverLap(StringMeeting s2){
            StringMeeting s1 = this;
            if( compareTime(s1.start, s2.start) <= 0 ){
                if( compareTime(s1.end, s2.end) <= 0 )
                    return new StringMeeting(s1.start, s2.end);
                return new StringMeeting(s1.start, s1.end);
            }
            else{
                if( compareTime(s1.end, s2.end) >= 0 )
                    return new StringMeeting(s2.start, s1.end);
                return new StringMeeting(s2.start, s2.end);
            }
        }

        @Override
        public String toString(){
            return start + " : " + end;
        }
    }

    enum TimeType{
        smaller, bigger, overlap
    }

    public static void main(String[] args) {
        List<StringMeeting> calendar1 = new ArrayList<StringMeeting>();
        calendar1.add(new StringMeeting("7:00", "7:45"));
        calendar1.add(new StringMeeting("8:15", "8:30"));
        calendar1.add(new StringMeeting("9:00", "10:30"));

        calendar1.add(new StringMeeting("12:00", "15:00"));
        calendar1.add(new StringMeeting("15:15", "15:30"));
        calendar1.add(new StringMeeting("16:30", "18:30"));
        calendar1.add(new StringMeeting("20:00", "21:00"));

        StringMeeting dailyBounds1 = new StringMeeting("6:30", "22:00");

        List<StringMeeting> calendar2 = new ArrayList<StringMeeting>();
        calendar2.add(new StringMeeting("9:00", "10:00"));
        calendar2.add(new StringMeeting("11:15", "11:30"));

        calendar2.add(new StringMeeting("11:45", "17:00"));
        calendar2.add(new StringMeeting("17:30", "19:00"));
        calendar2.add(new StringMeeting("20:00", "22:15"));

        StringMeeting dailyBounds2 = new StringMeeting("8:00", "22:30");

        int meetingDuration = 60;

        List<StringMeeting> actual =
                calendarMatching(calendar1, dailyBounds1, calendar2, dailyBounds2, meetingDuration);
    }
}
