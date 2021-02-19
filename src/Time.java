//  Time object class for initializing and managing Time objects

package Assignment_4_CD;

public class Time {
    
    private int minutes = 0;
    private int seconds = 0;
    
    //  Constructor for Time given in the form MM:SS
    public Time(String fullTime) {
        int colon = fullTime.indexOf(':');
        this.minutes = Integer.parseInt(fullTime.substring(0, colon));
        this.seconds = Integer.parseInt(fullTime.substring(colon + 1));
    }

    //  Constructor for Time given in seconds
    public Time(int seconds) {
        this.minutes = seconds / 60;
        this.seconds = seconds % 60;
    }

    //  Overrides toString to convert Time objects into strings in the form MM:SS
    //  Return type: Returns formatted string (String)
    //  Parameters: No parameters
    public String toString() {
        return minutes + ":" + String.format("%02d", seconds);
    }

    //  Converts Time objects into strings displaying hours, minutes, and seconds
    //  Return type: Returns formatted string (String)
    //  Parameters: No parameters
    public String toStringHours() {
        if (minutes >= 60)
            return String.format("%d hr %d min %d sec", minutes / 60, minutes % 60, seconds);
        else
            return String.format("%d min %d sec", minutes, seconds);
    }

    //  Converts Time in the form MM:SS to seconds
    //  Return type: Returns seconds (int)
    //  Parameters: No parameters
    public int toSeconds() {
        String time = this.toString();
        int colon = time.indexOf(':');
        return 60 * Integer.parseInt(time.substring(0, colon)) + Integer.parseInt(time.substring(colon + 1));
    }

    //  Adds time
    //  Return type: Changes the instance variables minutes and seconds (void)
    //  Parameters: Time to add (Time)
    public void addTime(Time time) {
        seconds += time.seconds;
        minutes += time.minutes;

        minutes += seconds / 60;
        seconds = seconds % 60;
    }
}
