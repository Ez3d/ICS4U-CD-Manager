/*  INCLUDES BONUS
    Edward Zhou
    2020-11-13

    Assignment 4: CD Collection
        -   Program that stores and organizes CDs in a collection of CDs
        -   CDs have attibutes such as a name, song count, length, and a list of songs specific to the CD
            -   Songs have attributes such as a name, artist, genre, rating, and length
        -   Reads CD data from file
        -   The CD list or a specific CD can be manipulated with a number of functions:
            -   Display CD/Song list
            -   Display CD/Song info
            -   Add CD/Song
            -   Remove CD/Song
            -   Copy CD
            -   Create Sub-CD
            -   List common songs between CDs
            -   Sort songs in a CD
    
    BONUS:
        -   JFrame-based program instead of a text-based one
*/

//  Setup
package Assignment_4_CD;

import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Driver {
    static JFrame frame;
    static JPanel finalPanel, mainPanel, panelCD, infoCD, displayPanelCD, panelSong, infoSong, displayPanelSong, 
            btnPanel, menuPanel, optFullPanel, opt1Panel, opt2Panel, 
            input1Panel, input2Panel, input3Panel, input4Panel, input5Panel, removeSongPanel, sortSongsPanel, 
            divider1, divider2, divider3;
    static JScrollPane scrollCD, scrollSong;

    // Main menu buttons
    static JButton btnCDList, btnInCD, btnExit;
    // Option 1 buttons
    static JButton btnAddCD, btnRemoveCD, btnCopyCD, btnCreateSub, btnListCommon;
    // Option 2 buttons
    static JButton btnAddSong, btnRemoveSong, btnSortSongs, btnEnter;

    static JTextField input1Field, input2Field, input3Field, input4Field, input5Field;
    static JLabel input1Label, input2Label, input3Label, input4Label, input5Label, removeSongLabel, sortSongsLabel, status;
    static JTextArea logo;
    static JComboBox<String> removeSong, sortSongs;

    int option = 0;
    static int selectedIndex = -1;
    static int currentMenu = 0;

    Font font12 = new Font("Apple Gothic", Font.PLAIN, 12);

    //  CD list
    static ArrayList<CD> listCD = new ArrayList<CD>();

    //  Assigns positions to CDs the CD list
    //  Return type: Changes global variables (void)
    //  Parameters: No parameters
    public void setListPosCD() {
        //  Asigns positions
        for (int i = 0; i < listCD.size(); i++)
            listCD.get(i).listPos = i;
        //  Updates buttons to show correct position
        for (CD cd: listCD)
            cd.getButton().setText(String.format("%6d     %s", cd.listPos + 1, cd.getTitle()));
    }

    //  Changes color of the selected CD
    //  Return type: Changes global variables (void)
    //  Parameters: No parameters
    public static void changeSelectedCD() {
        Color colorDark = new Color(235, 235, 235);
        Color colorLight = new Color(245, 245, 245);
        for (int i = 0; i < listCD.size(); i++)
            if (i != selectedIndex)
                listCD.get(i).getButton().setBackground(colorLight);
            else
                listCD.get(i).getButton().setBackground(colorDark);
        displayPanelCD.revalidate();
        displayPanelCD.repaint();
    }

    //  Changes color of the selected menu option
    //  Return type: Changes global variables (void)
    //  Parameters: No parameters
    public static void changeCurrentMenu() {
        Color colorDark = new Color(215, 215, 215);
        Color colorLight = new Color(235, 235, 235);
        if (currentMenu == 1) {
            btnCDList.setBackground(colorDark);
            btnInCD.setBackground(colorLight);
        }
        else if (currentMenu == 2) {
            btnCDList.setBackground(colorLight);
            btnInCD.setBackground(colorDark);
        }
        menuPanel.revalidate();
        menuPanel.repaint();
    }

    //  Hides or resets input components
    //  Return type: Changes global variables (void)
    //  Parameters: No parameters
    public void removeInputs() {
        input1Panel.removeAll();
        input2Panel.removeAll();
        input3Panel.removeAll();
        input4Panel.removeAll();
        input5Panel.removeAll();

        input1Panel.setVisible(false);
        input2Panel.setVisible(false);
        input3Panel.setVisible(false);
        input4Panel.setVisible(false);
        input5Panel.setVisible(false);

        input1Field.setText("");
        input2Field.setText("");
        input3Field.setText("");
        input4Field.setText("");
        input5Field.setText("");

        removeSongPanel.setVisible(false);
        sortSongsPanel.setVisible(false);

        status.setVisible(false);

        btnEnter.setVisible(false);
    }

    //  Initializes all JComponents
    //  Return type: Changes global variables (void)
    //  Parameters: No parameters
    public void initJComponents() {
        Color almostWhite = new Color(245, 245, 245);

        //  Frame
        frame = new JFrame("CD Collection");
        frame.setPreferredSize(new Dimension(1350, 550));

        //  Logo
        logo = new JTextArea("  " + "Wong Music\n");
        logo.setFont(new Font("Apple Gothic", Font.BOLD, 32));

        //  CD panel
        infoCD = new JPanel();
        infoCD.setLayout(new GridLayout(0, 1));
        infoCD.setBackground(almostWhite);
        infoCD.setVisible(false);
        displayPanelCD = new JPanel();
        displayPanelCD.setLayout(new BoxLayout(displayPanelCD, BoxLayout.PAGE_AXIS));
        displayPanelCD.setBackground(almostWhite);
        scrollCD = new JScrollPane(displayPanelCD);
        scrollCD.setPreferredSize(new Dimension(400, 540));
        scrollCD.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scrollCD.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollCD.setBorder(BorderFactory.createEmptyBorder());
        scrollCD.getVerticalScrollBar().setBackground(almostWhite);
        panelCD = new JPanel();
        panelCD.setLayout(new BoxLayout(panelCD, BoxLayout.PAGE_AXIS));
        panelCD.setPreferredSize(new Dimension(400, 540));
        panelCD.add(infoCD);
        panelCD.add(scrollCD);

        //  Song panel
        infoSong = new JPanel();
        infoSong.setLayout(new GridLayout(0, 1));
        infoSong.setBackground(Color.WHITE);
        infoSong.setVisible(false);
        displayPanelSong = new JPanel();
        displayPanelSong.setLayout(new BoxLayout(displayPanelSong, BoxLayout.PAGE_AXIS));
        displayPanelSong.setBackground(Color.WHITE);
        scrollSong = new JScrollPane(displayPanelSong);
        scrollSong.setPreferredSize(new Dimension(500, 540));
        scrollSong.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scrollSong.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollSong.setBorder(BorderFactory.createEmptyBorder());
        scrollSong.getVerticalScrollBar().setBackground(Color.WHITE);
        panelSong = new JPanel();
        panelSong.setLayout(new BoxLayout(panelSong, BoxLayout.PAGE_AXIS));
        panelSong.setPreferredSize(new Dimension(500, 540));
        panelSong.add(infoSong);
        panelSong.add(scrollSong);
        panelSong.setBackground(Color.WHITE);

        //  Buttons
        initJButtons();

        //  Button Panel
        menuPanel = new JPanel();
        menuPanel.setPreferredSize(new Dimension(380, 50));
        menuPanel.setLayout(new GridLayout(1, 0, 5, 0));
        menuPanel.add(btnCDList);
        menuPanel.add(btnInCD);
        menuPanel.add(btnExit);
        menuPanel.setBackground(almostWhite);

        opt1Panel = new JPanel();
        opt1Panel.setLayout(new GridLayout(0, 1, 0, 5));
        opt1Panel.add(btnAddCD);
        opt1Panel.add(btnRemoveCD);
        opt1Panel.add(btnCopyCD);
        opt1Panel.add(btnCreateSub);
        opt1Panel.add(btnListCommon);
        opt1Panel.setBackground(almostWhite);
        opt1Panel.setVisible(false);

        opt2Panel = new JPanel();
        opt2Panel.setLayout(new GridLayout(0, 1, 0, 5));
        opt2Panel.add(btnAddSong);
        opt2Panel.add(btnRemoveSong);
        opt2Panel.add(btnSortSongs);
        opt2Panel.setBackground(almostWhite);
        opt2Panel.setVisible(false);

        optFullPanel = new JPanel();
        optFullPanel.setPreferredSize(new Dimension(380, 400));
        optFullPanel.setLayout(new BoxLayout(optFullPanel, BoxLayout.LINE_AXIS));
        optFullPanel.add(opt1Panel);
        optFullPanel.add(opt2Panel);

        input1Panel = new JPanel();
        input1Panel.setLayout(new GridLayout(1, 0));
        input1Field = new JTextField();
        input1Panel.setBackground(almostWhite);
        input1Panel.setVisible(false);

        input2Panel = new JPanel();
        input2Panel.setLayout(new GridLayout(1, 0));
        input2Field = new JTextField();
        input2Panel.setBackground(almostWhite);
        input2Panel.setVisible(false);

        input3Panel = new JPanel();
        input3Panel.setLayout(new GridLayout(1, 0));
        input3Field = new JTextField();
        input3Panel.setBackground(almostWhite);
        input3Panel.setVisible(false);

        input4Panel = new JPanel();
        input4Panel.setLayout(new GridLayout(1, 0));
        input4Field = new JTextField();
        input4Panel.setBackground(almostWhite);
        input4Panel.setVisible(false);

        input5Panel = new JPanel();
        input5Panel.setLayout(new GridLayout(1, 0));
        input5Field = new JTextField();
        input5Panel.setBackground(almostWhite);
        input5Panel.setVisible(false);

        Color text = new Color(100, 100, 100);
        removeSongPanel = new JPanel();
        removeSongPanel.setLayout(new GridLayout(1, 0));
        removeSongLabel = new JLabel("Remove: ", SwingConstants.RIGHT);
        removeSongLabel.setFont(font12);
        removeSongLabel.setForeground(text);
        String[] removeSongOptions = { "By song number", "By song title", "First song", "Last song" };
        removeSong = new JComboBox<String>(removeSongOptions);
        removeSong.setFont(font12);
        removeSong.setForeground(text);
        //  ActionListener to actively toggle text field upon selecting option
        removeSong.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeInputs();
                removeSongPanel.setVisible(true);
                btnEnter.setVisible(true);
                String selected = (String) removeSong.getSelectedItem();
                if (selected.equals("By song number")) {
                    input1Label = new JLabel("Song number: ", SwingConstants.RIGHT);
                    input1Label.setFont(font12);
                    input1Label.setForeground(text);
                    input1Panel.add(input1Label);
                    input1Panel.add(input1Field);
                    input1Panel.setVisible(true);
                }
                else if (selected.equals("By song title")) {
                    input1Label = new JLabel("Song title: ", SwingConstants.RIGHT);
                    input1Label.setFont(font12);
                    input1Label.setForeground(text);
                    input1Panel.add(input1Label);
                    input1Panel.add(input1Field);
                    input1Panel.setVisible(true);
                }
                input1Panel.revalidate();
                input1Panel.repaint();
            }
        });
        removeSongPanel.add(removeSongLabel);
        removeSongPanel.add(removeSong);
        removeSongPanel.setBackground(almostWhite);
        removeSongPanel.setVisible(false);

        sortSongsPanel = new JPanel();
        sortSongsPanel.setLayout(new GridLayout(1, 0));
        sortSongsLabel = new JLabel("Sort: ", SwingConstants.RIGHT);
        sortSongsLabel.setFont(font12);
        sortSongsLabel.setForeground(text);
        String[] sortSongsOptions = { "By title", "By artist", "By time" };
        sortSongs = new JComboBox<String>(sortSongsOptions);
        sortSongs.setFont(font12);
        sortSongs.setForeground(text);
        sortSongsPanel.add(sortSongsLabel);
        sortSongsPanel.add(sortSongs);
        sortSongsPanel.setBackground(almostWhite);
        sortSongsPanel.setVisible(false);

        status = new JLabel();
        status.setFont(font12);

        btnPanel = new JPanel();
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.PAGE_AXIS));
        btnPanel.add(menuPanel);
        divider1 = new JPanel();
        divider1.setBackground(almostWhite);
        divider2 = new JPanel();
        divider2.setBackground(almostWhite);
        divider3 = new JPanel();
        divider3.setBackground(almostWhite);
        btnPanel.add(divider1);
        btnPanel.add(optFullPanel);
        btnPanel.add(divider2);
        btnPanel.add(removeSongPanel);
        btnPanel.add(sortSongsPanel);
        btnPanel.add(input1Panel);
        btnPanel.add(input2Panel);
        btnPanel.add(input3Panel);
        btnPanel.add(input4Panel);
        btnPanel.add(input5Panel);
        btnPanel.add(btnEnter);
        btnPanel.add(status);
        btnPanel.add(divider3);
        btnPanel.setBackground(almostWhite);

        //  Main Panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));
        mainPanel.add(panelCD);
        mainPanel.add(panelSong);
        mainPanel.add(btnPanel);

        //  Final Panel
        finalPanel = new JPanel();
        finalPanel.setLayout(new BoxLayout(finalPanel, BoxLayout.PAGE_AXIS));
        finalPanel.add(logo);
        finalPanel.add(mainPanel);
        finalPanel.setBackground(Color.WHITE);

        frame.add(finalPanel);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }

    //  Handles what to do when the Enter button is pressed based on the option variable
    //  Option is a 2 digit int. First digit is the submenu number. Second digit is the function number (as displayed on assignment doc)
    //  Certain functions are omitted, as they do not use the button panel
    //  Return type: Changes global variables (void)
    //  Parameters: No parameters
    public void handleEnter() {
        //  Add CD
        if (option == 13) {
            String title;
            int qty;
            try {
                //  Reads entered file
                BufferedReader fileIn = new BufferedReader(new FileReader(input1Field.getText().trim()));
                ArrayList<Song> tempSongList = new ArrayList<Song>();
                title = fileIn.readLine();
                qty = Integer.parseInt(fileIn.readLine());
                for (int i = 0; i < qty; i++) {
                    tempSongList.add(new Song(
                        fileIn.readLine(), 
                        fileIn.readLine(),
                        fileIn.readLine(), 
                        Integer.parseInt(fileIn.readLine()), 
                        new Time(fileIn.readLine())));
                }
                //  Adds and sets up CD
                listCD.add(new CD(title, qty, tempSongList));
                setListPosCD();
                listCD.get(listCD.size() - 1).setListPosSong();

                status.setText("CD Added");
                status.setVisible(true);

                fileIn.close();
            } catch (FileNotFoundException e) { 
                status.setText("File Not Found!");
                status.setVisible(true);
                return;
            } catch (IOException e) {
                status.setText("Reading Error!");
                status.setVisible(true);
                return;
            }
        }
        //  Remove CD
        else if (option == 14) {
            try {
                //  Reads CD number to be removed, skips invalid inputs
                int index = Integer.parseInt(input1Field.getText().trim()) - 1;
                if (index < 0 || index >= listCD.size())
                    throw new NumberFormatException();
                //  Removes CD
                if (index == selectedIndex)
                    selectedIndex = -1;
                listCD.get(index).removeCD();
                listCD.remove(index);
                
                //  Shifts selectedIndex
                if (index < selectedIndex)
                    selectedIndex--;
                setListPosCD();

                status.setText("CD Removed");
                status.setVisible(true);
            } catch (NumberFormatException e) {
                status.setText("Invalid input!");
                status.setVisible(true);
                return;
            }
        }
        //  Copy CD
        else if (option == 15) {
            try {
                //  Reads CD number to be copied, skips invalid inputs
                int index = Integer.parseInt(input1Field.getText().trim()) - 1;
                if (index < 0 || index >= listCD.size())
                    throw new NumberFormatException();
                //  Copies CD
                listCD.add(listCD.get(index).copy());
                setListPosCD();

                status.setText("CD Copied");
                status.setVisible(true);
            } catch (NumberFormatException e) {
                status.setText("Invalid input!");
                status.setVisible(true);
                return;
            }
        }
        //  Create subCD
        else if (option == 16) {
            try {
                //  Reads CD number and start and end indicies, skips invalid inputs
                int index = Integer.parseInt(input1Field.getText().trim()) - 1;
                int start = Integer.parseInt(input2Field.getText().trim()) - 1;
                int end = Integer.parseInt(input3Field.getText().trim()) - 1;
                if (index < 0 || index >= listCD.size())
                    throw new NumberFormatException();
                if (end < start || end > listCD.get(index).listSong.size() - 1 || start < 0)
                    throw new NumberFormatException();
                //  Creates subCD
                listCD.add(listCD.get(index).subCopy(start, end));
                setListPosCD();

                status.setText("Sub-CD Created");
                status.setVisible(true);
            } catch (NumberFormatException e) {
                status.setText("Invalid input(s)!");
                status.setVisible(true);
                return;
            }
        }
        //  List common
        else if (option == 17) {
            try {
                //  Reads two CD numbers, skips invalid inputs
                int index1 = Integer.parseInt(input1Field.getText().trim()) - 1;
                int index2 = Integer.parseInt(input2Field.getText().trim()) - 1;
                if (index1 < 0 || index1 >= listCD.size() || index2 < 0 || index2 >= listCD.size())
                    throw new NumberFormatException();
                //  Lists common songs in the song panel
                CD.displaySongs(listCD.get(index1).common(listCD.get(index2)));

                infoCD.setVisible(false);
                infoSong.setVisible(false);
                status.setText("Displaying Common Songs");
                status.setVisible(true);
            } catch (NumberFormatException e) {
                status.setText("Invalid input(s)!");
                status.setVisible(true);
                return;
            }
        }
        //  Add song
        else if (option == 23) {
            try {
                //  Reads Song attributes, skips invalid inputs
                String title = input1Field.getText().trim();
                String artist = input2Field.getText().trim();
                String genre = input3Field.getText().trim();
                int rating = Integer.parseInt(input4Field.getText().trim());
                int timeSec = Integer.parseInt(input5Field.getText().trim());
                if (title.length() == 0 || artist.length() == 0 || genre.length() == 0)
                    throw new NumberFormatException();
                if (rating < 0 || rating > 5 || timeSec <= 0)
                    throw new NumberFormatException();

                //  Ensures that a CD is selected
                if (selectedIndex == -1) {
                    status.setText("Select a CD!");
                    status.setVisible(true);
                    return;
                }
                
                //  Adds and sets up Song
                listCD.get(selectedIndex).listSong.add(new Song(title, artist, genre, rating, new Time(timeSec)));
                listCD.get(selectedIndex).setListPosSong();
                listCD.get(selectedIndex).resetLabel();
                CD.displaySongs(listCD.get(selectedIndex).listSong);

                status.setText("Song Added");
                status.setVisible(true);
            } catch (NumberFormatException e) {
                status.setText("Invalid input(s)!");
                status.setVisible(true);
                return;
            }
        }
        //  Remove song
        else if (option == 24) {
            try {
                //  Ensures that a CD is selected
                if (selectedIndex == -1) {
                    status.setText("Select a CD!");
                    status.setVisible(true);
                    return;
                }
                if (listCD.get(selectedIndex).listSong.size() == 0) {
                    status.setText("No Songs to Remove!");
                    status.setVisible(true);
                    return;
                }
                String selection = (String) removeSong.getSelectedItem();
                //  Removes by song number
                if (selection.equals("By song number")) {
                    int index = Integer.parseInt(input1Field.getText().trim()) - 1;
                    if (index < 0 || index > listCD.get(selectedIndex).listSong.size() - 1)
                        throw new NumberFormatException();
                    listCD.get(selectedIndex).listSong.get(index).removeSong();
                    listCD.get(selectedIndex).listSong.remove(index);
                }
                //  Removes by song title
                else if (selection.equals("By song title")) {
                    if (input1Field.getText().trim().length() == 0)
                        throw new NumberFormatException();
                    int index;
                    int loopCount = 0;
                    //  Removes song and all duplicates
                    do {
                        loopCount++;
                        Collections.sort(listCD.get(selectedIndex).listSong, new SortByTitleSong());
                        index = Collections.binarySearch(listCD.get(selectedIndex).listSong, new Song(input1Field.getText().trim(), "", "", 0, new Time(0)), new SortByTitleSong());
                        if (index < 0 && loopCount == 1) {
                            status.setText("Song Not Found!");
                            status.setVisible(true);
                            return;
                        }
                        if (index < 0 && loopCount > 1)
                            break;
                        listCD.get(selectedIndex).listSong.get(index).removeSong();
                        listCD.get(selectedIndex).listSong.remove(index);
                    } while (index >= 0);
                }
                //  Removes first song
                else if (selection.equals("First song")) {
                    listCD.get(selectedIndex).listSong.get(0).removeSong();
                    listCD.get(selectedIndex).listSong.remove(0);
                }
                //  Removes last song
                else if (selection.equals("Last song")) {
                    listCD.get(selectedIndex).listSong.get(listCD.get(selectedIndex).listSong.size() - 1).removeSong();
                    listCD.get(selectedIndex).listSong.remove(listCD.get(selectedIndex).listSong.size() - 1);
                }
                //  Updates song list info
                listCD.get(selectedIndex).setListPosSong();
                listCD.get(selectedIndex).resetLabel();
                CD.displaySongs(listCD.get(selectedIndex).listSong);

                status.setText("Song Removed");
                status.setVisible(true);
            } catch (NumberFormatException e) {
                status.setText("Invalid input(s)!");
                status.setVisible(true);
                return;
            }
        }
        //  Sort songs
        else if (option == 25) {
            //  Ensures that a CD is selected
            if (selectedIndex == -1) {
                status.setText("Select a CD!");
                status.setVisible(true);
                return;
            }
            if (listCD.get(selectedIndex).listSong.size() == 0) {
                status.setText("No Songs to Sort!");
                status.setVisible(true);
                return;
            }
            String selection = (String) sortSongs.getSelectedItem();
            //  Sorts by title
            if (selection.equals("By title"))
                Collections.sort(listCD.get(selectedIndex).listSong, new SortByTitleSong());
            //  Sorts by artist name
            else if (selection.equals("By artist"))
                Collections.sort(listCD.get(selectedIndex).listSong, new SortByArtist());
            //  Sorts by time
            else if (selection.equals("By time"))
                Collections.sort(listCD.get(selectedIndex).listSong, new SortByTime());
            listCD.get(selectedIndex).setListPosSong();
            CD.displaySongs(listCD.get(selectedIndex).listSong);

            status.setText("Songs Sorted");
            status.setVisible(true);
        }
        
        //  Clears input fields
        input1Field.setText("");
        input2Field.setText("");
        input3Field.setText("");
        input4Field.setText("");
        input5Field.setText("");

        displayPanelCD.revalidate();
        displayPanelCD.repaint();
        displayPanelSong.revalidate();
        displayPanelSong.repaint();
    }

    //  Initializes all JButtons and attaches ActionListeners
    //  Sets option for handleEnter() and displays appropriate input fields
    //  Return type: Changes global variables (void)
    //  Parameters: No parameters
    public void initJButtons() {
        Color text = new Color(100, 100, 100);
        // Main menu options
        btnCDList = new JButton("CD List");
        btnCDList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentMenu = 1;
                changeCurrentMenu();

                removeInputs();
                opt1Panel.setVisible(true);
                opt2Panel.setVisible(false);
            }
        });

        btnInCD = new JButton("CD");
        btnInCD.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentMenu = 2;
                changeCurrentMenu();

                removeInputs();
                opt1Panel.setVisible(false);
                opt2Panel.setVisible(true);
            }
        });

        btnExit = new JButton("Exit");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        //  Submenu 1 Options
        btnAddCD = new JButton("Add CD");
        btnAddCD.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                option = 13;

                removeInputs();
                input1Label = new JLabel("File Name: ", SwingConstants.RIGHT);
                input1Label.setFont(font12);
                input1Label.setForeground(text);
                input1Panel.add(input1Label);
                input1Panel.add(input1Field);
                input1Panel.setVisible(true);

                btnEnter.setVisible(true);
            }
        });

        btnRemoveCD = new JButton("Remove CD");
        btnRemoveCD.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                option = 14;

                removeInputs();
                input1Label = new JLabel("Remove CD Number: ", SwingConstants.RIGHT);
                input1Label.setFont(font12);
                input1Label.setForeground(text);
                input1Panel.add(input1Label);
                input1Panel.add(input1Field);
                input1Panel.setVisible(true);
                
                btnEnter.setVisible(true);
            }
        });

        btnCopyCD = new JButton("Copy CD");
        btnCopyCD.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                option = 15;
                
                removeInputs();
                input1Label = new JLabel("Copy CD Number: ", SwingConstants.RIGHT);
                input1Label.setFont(font12);
                input1Label.setForeground(text);
                input1Panel.add(input1Label);
                input1Panel.add(input1Field);
                input1Panel.setVisible(true);
                
                btnEnter.setVisible(true);
            }
        });

        btnCreateSub = new JButton("Create Sub-CD");
        btnCreateSub.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                option = 16;

                removeInputs();
                input1Label = new JLabel("CD Number: ", SwingConstants.RIGHT);
                input1Label.setFont(font12);
                input1Label.setForeground(text);
                input1Panel.add(input1Label);
                input1Panel.add(input1Field);
                input1Panel.setVisible(true);

                input2Label = new JLabel("Start At (Inclusive): ", SwingConstants.RIGHT);
                input2Label.setFont(font12);
                input2Label.setForeground(text);
                input2Panel.add(input2Label);
                input2Panel.add(input2Field);
                input2Panel.setVisible(true);

                input3Label = new JLabel("End At (Inclusive): ", SwingConstants.RIGHT);
                input3Label.setFont(font12);
                input3Label.setForeground(text);
                input3Panel.add(input3Label);
                input3Panel.add(input3Field);
                input3Panel.setVisible(true);

                btnEnter.setVisible(true);
            }
        });

        btnListCommon = new JButton("Common Songs");
        btnListCommon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                option = 17;

                removeInputs();
                input1Label = new JLabel("CD 1 Number: ", SwingConstants.RIGHT);
                input1Label.setFont(font12);
                input1Label.setForeground(text);
                input1Panel.add(input1Label);
                input1Panel.add(input1Field);
                input1Panel.setVisible(true);

                input2Label = new JLabel("CD 2 Number: ", SwingConstants.RIGHT);
                input2Label.setFont(font12);
                input2Label.setForeground(text);
                input2Panel.add(input2Label);
                input2Panel.add(input2Field);
                input2Panel.setVisible(true);
                input2Panel.setVisible(true);

                btnEnter.setVisible(true);
            }
        });

        //  Submenu 2 options
        btnAddSong = new JButton("Add Song");
        btnAddSong.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                option = 23;

                removeInputs();
                input1Label = new JLabel("Title: ", SwingConstants.RIGHT);
                input1Label.setFont(font12);
                input1Label.setForeground(text);
                input1Panel.add(input1Label);
                input1Panel.add(input1Field);
                input1Panel.setVisible(true);

                input2Label = new JLabel("Artist: ", SwingConstants.RIGHT);
                input2Label.setFont(font12);
                input2Label.setForeground(text);
                input2Panel.add(input2Label);
                input2Panel.add(input2Field);
                input2Panel.setVisible(true);

                input3Label = new JLabel("Genre: ", SwingConstants.RIGHT);
                input3Label.setFont(font12);
                input3Label.setForeground(text);
                input3Panel.add(input3Label);
                input3Panel.add(input3Field);
                input3Panel.setVisible(true);

                input4Label = new JLabel("Rating: ", SwingConstants.RIGHT);
                input4Label.setFont(font12);
                input4Label.setForeground(text);
                input4Panel.add(input4Label);
                input4Panel.add(input4Field);
                input4Panel.setVisible(true);

                input5Label = new JLabel("Time (s): ", SwingConstants.RIGHT);
                input5Label.setFont(font12);
                input5Label.setForeground(text);
                input5Panel.add(input5Label);
                input5Panel.add(input5Field);
                input5Panel.setVisible(true);

                btnEnter.setVisible(true);
            }
        });

        btnRemoveSong = new JButton("Remove Song");
        btnRemoveSong.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                option = 24;

                removeInputs();
                removeSongPanel.setVisible(true);
                removeSong.setSelectedIndex(0);
            
                btnEnter.setVisible(true);
            }
        });

        btnSortSongs = new JButton("Sort Songs");
        btnSortSongs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                option = 25;

                removeInputs();
                sortSongsPanel.setVisible(true);
                
                btnEnter.setVisible(true);
            }
        });

        btnEnter = new JButton("Enter");
        btnEnter.setVisible(false);
        btnEnter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleEnter();
            }
        });

        //  Stylizes all buttons
        ArrayList<JButton> btns = new ArrayList<JButton>(Arrays.asList(
            btnCDList, btnInCD, btnExit, btnAddCD, btnRemoveCD, btnCopyCD, 
            btnCreateSub, btnListCommon, btnAddSong, btnRemoveSong, btnSortSongs, btnEnter));
        Font font = new Font("Apple Gothic", Font.PLAIN, 14);
        Color color = new Color(235, 235, 235);
        for (JButton btn: btns) {
            btn.setBackground(color);
            btn.setFont(font);
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
        }
    }

    //  Constructor
    public Driver() {
        initJComponents();
    }

    //  Main method
    public static void main(String[] args) throws IOException {
        new Driver();
    }
}
