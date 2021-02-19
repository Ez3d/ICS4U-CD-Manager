//  CD object class for initializing and managing CD objects

package Assignment_4_CD;

import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class CD {
    
    private String titleCD;
    private int totalSongs;
    ArrayList<Song> listSong = new ArrayList<Song>();
    Time totalTime = new Time(0);

    int listPos;
    private JButton btn;
    private JLabel lblTitle;
    private JLabel info;

    static int selectedIndexSong = -1;

    //  Constructor
    public CD(String title, int totalSongs, ArrayList<Song> songs) {
        this.titleCD = title;
        this.totalSongs = totalSongs;

        for (Song song: songs)
            listSong.add(new Song(song.getTitle(), song.getArtist(), song.getGenre(), song.getRating(), song.getTime()));
        
        calcTime();
        createBtnCD();
        createLabelCD();
    }

    //  Calculates the CD's total length
    //  Return type: Changes global variables (void)
    //  Parameters: No parameters
    public void calcTime() {
        totalTime = new Time(0);
        for (Song song: listSong)
            totalTime.addTime(song.getTime());
    }

    //  Resets the CD's info label
    //  Return type: Changes global variables (void)
    //  Parameters: No parameters
    public void resetLabel() {
        //  Removes info, re-calculates, and re-adds
        Driver.infoCD.removeAll();
        totalSongs = listSong.size();
        calcTime();
        createLabelCD();
        Driver.infoCD.add(lblTitle);
        Driver.infoCD.add(info);
        Driver.infoCD.setVisible(true);
        Driver.infoCD.revalidate();
        Driver.infoCD.repaint();
    }

    //  Assigns positions to Songs in a CD's list
    //  Return type: Changes global variables (void)
    //  Parameters: No parameters
    public void setListPosSong() {
        //  Assigns positions
        for (int i = 0; i < listSong.size(); i++)
            listSong.get(i).listPos = i;
        //  Updates buttons to show correct position
        for (Song song: listSong)
            song.getButton().setText(String.format("%6d     %s", song.listPos + 1, song.getTitle()));
            
        selectedIndexSong = -1;
    }

    //  Adds all Songs in a list to the Song display panel
    //  Return type: Changes global variables (void)
    //  Parameters: List to add (ArrayList<Song>)
    public static void displaySongs(ArrayList<Song> list) {
        Driver.displayPanelSong.removeAll();
        for (Song song: list)
            Driver.displayPanelSong.add(song.getButton());
        Driver.displayPanelSong.revalidate();
        Driver.displayPanelSong.repaint();
    }

    //  Changes color of the selected Song
    //  Return type: Changes global variables (void)
    //  Parameters: No parameters
    public void changeSelectedSong() {
        Color colorDark = new Color(245, 245, 245);
        for (int i = 0; i < listSong.size(); i++)
            if (i != selectedIndexSong)
                listSong.get(i).getButton().setBackground(Color.WHITE);
            else
                listSong.get(i).getButton().setBackground(colorDark);
        Driver.displayPanelSong.revalidate();
        Driver.displayPanelSong.repaint();
    }
    
    //  Creates the JButton associated with the CD
    //  Return type: Changes global variables (void)
    //  Parameters: No parameters
    public void createBtnCD() {
        btn = new JButton();
        btn.setMaximumSize(new Dimension(400, 50));
        btn.setPreferredSize(new Dimension(400, 50));
        btn.setBackground(new Color(245, 245, 245));
        btn.setForeground(new Color(115, 115, 115));
        btn.setFont(new Font("Apple Gothic", Font.PLAIN, 14));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Driver.selectedIndex = listPos;
                Driver.changeSelectedCD();
                selectedIndexSong = -1;
                changeSelectedSong();

                Driver.infoSong.removeAll();
                Driver.infoCD.removeAll();
                Driver.infoCD.add(lblTitle);
                Driver.infoCD.add(info);
                Driver.infoCD.setVisible(true);
                Driver.infoCD.revalidate();
                Driver.infoCD.repaint();

                setListPosSong();
                displaySongs(listSong);
            }
        });
        Driver.displayPanelCD.add(btn);
    }

    //  Creates the info labels associated with the CD
    //  Return type: Changes global variables (void)
    //  Parameters: No parameters
    public void createLabelCD() {
        lblTitle = new JLabel("  " + titleCD);
        lblTitle.setBackground(new Color(245, 245, 245));
        lblTitle.setFont(new Font("Apple Gothic", Font.BOLD, 24));

        info = new JLabel(String.format("   %d songs ·  %s", totalSongs, totalTime.toStringHours()));
        info.setBackground(new Color(245, 245, 245));
        info.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        info.setForeground(new Color(140, 140, 140));
    }

    //  Removes a CD from the CD list
    //  Return type: Changes global variables (void)
    //  Parameters: No parameters
    public void removeCD() {
        //  Removes all songs from the CD
        for (Song song: listSong) {
            song.removeSong();
        }
        Driver.infoCD.remove(lblTitle);
        Driver.infoCD.remove(info);
        Driver.infoCD.revalidate();
        Driver.infoCD.repaint();
        Driver.displayPanelCD.remove(btn);
        Driver.displayPanelCD.revalidate();
        Driver.displayPanelCD.repaint();
    }

    //  Creates a copy of a CD
    //  Return type: Returns a copy of a CD (CD)
    //  Parameters: No parameters
    public CD copy() {
        CD copy = new CD(titleCD + " copy", totalSongs, listSong);
        setListPosSong();
        return copy;
    }

    //  Creates a sub-CD of a CD
    //  Return type: Returns a sub-CD of a CD (CD)
    //  Parameters: Sub-CD start index (int), Sub-CD end index (inclusive) (int)
    public CD subCopy(int start, int end) {
        CD copy = new CD(titleCD + " sub", end - start + 1, new ArrayList<Song>(listSong.subList(start, end + 1)));
        setListPosSong();
        return copy;
    }
    
    //  Finds common songs between two CDs
    //  Return type: Returns list of common Songs (Arraylist<Song>)
    //  Parameters: CD to compare to (CD)
    public ArrayList<Song> common(CD cd) {
        ArrayList<Song> songs = new ArrayList<Song>();
        //  Finds common songs
        for (Song song1: listSong) {
            for (Song song2: cd.listSong) {
                if (song1.getTitle().equalsIgnoreCase(song2.getTitle()))
                    songs.add(new Song(song1.getTitle(), song1.getArtist(), song1.getGenre(), song1.getRating(), song1.getTime()));
            }
        }
        //  Remove duplicates
        ArrayList<String> titles = new ArrayList<String>();
        ArrayList<Song> songsNew = new ArrayList<Song>();
        for (int i = 0; i < songs.size(); i++)
            titles.add(songs.get(i).getTitle().toLowerCase());
        for (int i = 0; i < songs.size(); i++)
            if (titles.lastIndexOf(titles.get(i)) == i)
                songsNew.add(songs.get(i));
        
        //  Assign positions and updates buttons
        for (int i = 0; i < songsNew.size(); i++)
            songsNew.get(i).listPos = i;
        for (Song song: songsNew)
            song.getButton().setText(String.format("%6d     %s", song.listPos + 1, song.getTitle()));
        return songsNew;
    }

    //  Getters
    public String getTitle() {
        return titleCD;
    }

    public int getTotalSongs() {
        return totalSongs;
    }

    public Time getTime() {
        return totalTime;
    }

    public JButton getButton() {
        return btn;
    }
}
