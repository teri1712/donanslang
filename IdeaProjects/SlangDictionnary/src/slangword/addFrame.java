package slangword;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.TreeSet;

public class addFrame extends JFrame implements ActionListener {

    JTextArea tf;
    JButton ok;
    dictionaryManager dmg;
    JFrame pa;
    String sl;


    addFrame(String s, dictionaryManager d, JFrame par) {
        sl = s;
        dmg = d;
        pa = par;
        par.setEnabled(false);
        this.setTitle("add slang service");
        this.setSize(340, 250);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                JFrame frame = (JFrame) e.getSource();
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                pa.setEnabled(true);
            }
        });
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        tf = new JTextArea("please put your defnitions here\n each one a line'");
        tf.setBounds(10, 10, 200, 150);

        JScrollPane scrl = new JScrollPane(tf);
        scrl.setBounds(0, 0, 200, 150);

        ok = new JButton("ok");
        ok.setBounds(220, 70, 80, 30);
        ok.addActionListener(this);

        this.add(ok);
        this.add(scrl);
        this.setVisible(true);
    }


    public void actionPerformed(ActionEvent e) {
        String[] g = tf.getText().split("\\r?\\n");
        TreeSet<String> def = new TreeSet<String>();
        for (String d : g) {
            def.add(d);
            TreeSet<String> st = dmg.getSl(d);
            if (st == null) {
                st = new TreeSet<String>();
                dmg.addSl(d, st);
            }
            st.add(sl);
        }
        dmg.addDef(sl, def);
        JOptionPane.showMessageDialog(
                this,
                "success",
                "add service",
                JOptionPane.INFORMATION_MESSAGE);
        pa.setEnabled(true);
        this.dispose();
    }
}
