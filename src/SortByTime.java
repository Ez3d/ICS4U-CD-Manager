//  Comparator interface class to sort songs by length, shortest to longest

package Assignment_4_CD;

import java.util.Comparator;
public class SortByTime implements Comparator<Song> {
    
    //  Compares by length when this comparator implementation is called
    //  Return type: Returns comparison output (int)
    //  Parameters: First song (Song), second song (Song)
    public int compare(Song song1, Song song2) {
        return song1.getTime().toSeconds() - song2.getTime().toSeconds();
    }
}
