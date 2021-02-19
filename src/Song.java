//  Song object class for initializing and managing Song objects

package Assignment_4_CD;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Song {
    
    private String titleSong;
    private String artist;
    private String genre;
    private int rating;
    Time length;

    int listPos;
    private JButton btn;
    private JLabel lblTitle;
    private JLabel lblArtist;
    private JLabel lblRating;
    private JLabel info;

    //  Constructor
    public Song(String titleSong, String artist, String genre, int rating, Time length) {
        this.titleSong = titleSong;
        this.artist = artist;
        this.genre = genre;
        this.rating = rating;
        this.length = new Time(length.toSeconds());

        createBtnSong();
        createLabelSong();
    }

    //  Creates the JButton associated with the Song
    //  Return type: Changes global variables (void)
    //  Parameters: No parameters
    public void createBtnSong() {
        btn = new JButton();
        btn.setMaximumSize(new Dimension(500, 30));
        btn.setPreferredSize(new Dimension(500, 30));
        btn.setBackground(Color.WHITE);
        btn.setForeground(new Color(140, 140, 140));
        btn.setFont(new Font("Apple Gothic", Font.PLAIN, 14));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        //  Adds ActionListener for the Song's JButton to display Song info
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CD.selectedIndexSong = listPos;
                Driver.listCD.get(Driver.selectedIndex).changeSelectedSong();

                Driver.infoSong.removeAll();
                Driver.infoSong.add(lblTitle);
                Driver.infoSong.add(lblArtist);
                Driver.infoSong.add(lblRating);
                Driver.infoSong.add(info);
                Driver.infoSong.setVisible(true);
                Driver.infoSong.revalidate();
                Driver.infoSong.repaint();
            }
        });
    }

    //  Creates the info labels associated with the Song
    //  Return type: Changes global variables (void)
    //  Parameters: No parameters
    public void createLabelSong() {
        //  Creates all the information to be displayed when the song is clicked
        lblTitle = new JLabel("   " + titleSong);
        lblTitle.setFont(new Font("Apple Gothic", Font.BOLD, 16));

        lblArtist = new JLabel("   " + artist);
        lblArtist.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        lblArtist.setForeground(new Color(250, 35, 59));

        String stars = "☆☆☆☆☆";
        for (int i = 0; i < rating; i++)
            stars = "★" + stars.substring(0, 4);
        lblRating = new JLabel("   " + stars);
        lblRating.setFont(new Font("Dialog", Font.PLAIN, 16));
        lblRating.setForeground(new Color(140, 140, 140));

        info = new JLabel(String.format("   %s ·  %s", genre, length.toString()));
        info.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        info.setForeground(new Color(140, 140, 140));
    }

    //  Removes all elements associated with a Song
    //  Return type: Changes global variables (void)
    //  Parameters: No parameters
    public void removeSong() {
        Driver.displayPanelSong.remove(btn);
        Driver.infoSong.remove(lblTitle);
        Driver.infoSong.remove(lblArtist);
        Driver.infoSong.remove(lblRating);
        Driver.infoSong.remove(info);
        Driver.infoSong.revalidate();
        Driver.infoSong.repaint();
        Driver.displayPanelSong.revalidate();
        Driver.displayPanelSong.repaint();
    }

    //  Getters
    public String getTitle() {
        return titleSong;
    }

    public String getArtist() {
        return artist;
    }

    public String getGenre() {
        return genre;
    }

    public int getRating() {
        return rating;
    }

    public Time getTime() {
        return length;
    }

    public JButton getButton() {
        return btn;
    }
}
