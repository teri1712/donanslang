package slangword;


import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.TreeSet;

public class searchFrame extends JFrame implements ActionListener {

    JButton def, sl;
    String query;
    dictionaryManager dmg;
    mainFrame par;

    searchFrame(String qr, mainFrame pa, dictionaryManager dmgr) {
        par = pa;
        dmg = dmgr;
        query = qr;
        this.setTitle("search service");
        this.setSize(340, 150);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                JFrame frame = (JFrame) e.getSource();
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                pa.setEnabled(true);
            }
        });
        this.setLayout(null);
        this.setLocationRelativeTo(null);

        def = new JButton();
        sl = new JButton();

        def.setText("definition");
        sl.setText("slang");
        def.setFocusable(false);
        sl.setFocusable(false);

        def.setBounds(40, 60, 110, 30);
        sl.setBounds(170, 60, 110, 30);

        JLabel jlb = new JLabel();

        jlb.setText("please pick your search option");
        jlb.setBounds(80, 20, 180, 30);

        this.add(def);
        this.add(sl);
        this.add(jlb);

        def.addActionListener(this);
        sl.addActionListener(this);
        System.out.println(qr);
        this.setVisible(true);

    }

    public void actionPerformed(ActionEvent e) {
        this.dispose();
        par.setEnabled(true);
        if (e.getSource() == def) {
            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(this, "please input something", "error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            TreeSet<String> ans = dmg.getSl(query);
            if (ans == null) {
                JOptionPane.showMessageDialog(this, "definition does not exist", "result", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            String msg = "";
            Iterator<String> itr = ans.iterator();
            while (itr.hasNext()) {
                msg += (itr.next() + (itr.hasNext() ? "," : ""));
            }
            JOptionPane.showMessageDialog(this, msg, "result", JOptionPane.INFORMATION_MESSAGE);

        } else if (e.getSource() == sl) {
            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(this, "please input something", "error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            dmg.updateHis(query);
            TreeSet<String> ans = dmg.getDef(query);
            if (ans == null) {
                JOptionPane.showMessageDialog(this, "slang does not exist", "result", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            String msg = "";
            Iterator<String> itr = ans.iterator();
            while (itr.hasNext()) {
                msg += (itr.next() + (itr.hasNext() ? "," : ""));
            }
            JOptionPane.showMessageDialog(this, msg, "result", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
