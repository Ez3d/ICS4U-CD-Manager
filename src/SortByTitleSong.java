//  Comparator interface class to sort songs by song name, alphabetically

package Assignment_4_CD;

import java.util.Comparator;
public class SortByTitleSong implements Comparator<Song> {

    //  Compares by song name lexicographically when this comparator implementation is called
    //  Return type: Returns comparison output (int)
    //  Parameters: First song (Song), second song (Song)
    public int compare(Song song1, Song song2) {
        return song1.getTitle().toLowerCase().compareTo(song2.getTitle().toLowerCase());
    }
}
