import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

class Notepad extends Frame implements ActionListener {
    TextArea ta = new TextArea();
    private String file_name;
    private String path;

    /**
     * Constructor creates an instance of Notepad
     * With a default window Title set to "untitlled"
     */
    public Notepad() {
        MenuBar mb = new MenuBar();
        add(ta);
        setMenuBar(mb);
        Menu m1 = new Menu("File");
        mb.add(m1);
        MenuItem mi1[] = { new MenuItem("New"), new MenuItem("Open"), new MenuItem("Save"), new MenuItem("Save As"),
                new MenuItem("Exit") };
        for (MenuItem i : mi1) {
            m1.add(i);
            i.addActionListener(this);
        }
        
        MyWindow w = new MyWindow(this);
        addWindowListener(w);
        setSize(500, 500);
        setTitle("untitled");
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String arg = (String) ae.getActionCommand();

        // Creating a New File
        if (arg.equals("New")) {
            Notepad n1 = new Notepad();
        }

        // Opening a File
        try {
            if (arg.equals("Open")) {
                FileDialog fdw = new FileDialog(this, "Select File", FileDialog.LOAD);
                fdw.setAlwaysOnTop(true);
                fdw.setVisible(true);
                file_name = fdw.getFile();
                path = fdw.getDirectory();
                String filePath = path + file_name;
                File f = new File(filePath);
                FileInputStream fii = new FileInputStream(f);
                String data = "";
                int len_file = (int) f.length();
                for (int i = 0; i < len_file; i++)
                    data += (char) fii.read();
                fii.close();

                this.ta.setText(data);
                this.setTitle(file_name);
            }
        } catch (Exception e) {
        }

        // To save
        try {
            if ((arg.equals("Save") && this.getTitle() == "untitled") || arg.equals("Save As")) {
                FileDialog dialog1 = new FileDialog(this, arg, FileDialog.SAVE);
                dialog1.setVisible(true);
                path = dialog1.getDirectory();
                file_name = dialog1.getFile();
                String filePath = path + file_name;
                String text_data = ta.getText();
                int len2 = text_data.length();
                byte buf[] = text_data.getBytes();
                File f1 = new File(filePath + ".txt");
                FileOutputStream fobj1 = new FileOutputStream(f1);
                for (int k = 0; k < len2; k++) {
                    fobj1.write(buf[k]);
                }
                fobj1.close();
                this.setTitle(file_name);
            } else {
                new FileOutputStream(new File(path + file_name + ".txt")).close();
                String temp_data = ta.getText();
                int len_data = temp_data.length();
                byte buf[] = temp_data.getBytes();
                FileOutputStream fobj2 = new FileOutputStream(path + file_name + ".txt");
                for (int k = 0; k < len_data; k++) {
                    fobj2.write(buf[k]);
                }
                fobj2.close();
            }

        } catch (Exception e) {
        }

        // To exit
        if (arg.equals("Exit"))
            dispose();
    }

    public class MyWindow extends WindowAdapter {
        Notepad nnn;

        public MyWindow(Notepad nn) {
            nnn = nn;
        }

        public void windowClosing(WindowEvent we) {
            nnn.dispose();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Notepad nn = new Notepad();
    }
}
