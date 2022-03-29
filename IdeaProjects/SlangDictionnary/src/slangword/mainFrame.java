package slangword;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.TreeSet;

public class mainFrame extends JFrame implements ActionListener {

    JButton search, add, delete, edit, quizdef, quizsl, submit, reset, history, tdsl, rndsl;
    TextField tf1, tf2, tf3, tf4;
    JPanel query, editsl, getSl, quiz;
    JRadioButton A, B, C, D;
    JTextArea question = new JTextArea("question");
    dictionaryManager dmg;
    int ansPos;

    mainFrame() {

        Border border = BorderFactory.createLineBorder(Color.gray, 2);
        this.setTitle("slang word dictionary");
        this.setSize(300, 550);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        dmg = new dictionaryManager();


        search = new JButton();
        search.setBounds(6, 60, 74, 25);
        search.setText("search");
        search.setFocusable(false);
        search.addActionListener(this);

        add = new JButton();
        add.setText("add");
        add.setBounds(90, 60, 80, 25);
        add.setFocusable(false);
        add.addActionListener(this);

        delete = new JButton();
        delete.setBounds(260 - 20 - 60, 60, 74, 25);
        delete.setText("delete");
        delete.setFocusable(false);
        delete.addActionListener(this);

        edit = new JButton();
        edit.setText("edit");
        edit.setBounds(260 - 70 - 20, 35, 70, 30);
        edit.setFocusable(false);
        edit.addActionListener(this);


        tdsl = new JButton("today's slang");
        tdsl.setBounds(10, 8, 115, 15);
        tdsl.addActionListener(this);

        rndsl = new JButton("random slang");
        rndsl.setBounds(10, 28, 115, 15);
        rndsl.addActionListener(this);

        quizdef = new JButton();
        quizdef.setText("definition quiz");
        quizdef.setBounds(10, 15, 110, 30);
        quizdef.addActionListener(this);


        quizsl = new JButton();
        quizsl.setText("slang quiz");
        quizsl.setBounds(10, 55, 110, 30);
        quizsl.addActionListener(this);

        submit = new JButton();
        submit.setText("submit");
        submit.setBounds(25, 100, 80, 30);
        submit.setEnabled(false);
        submit.addActionListener(this);

        reset = new JButton("reset");
        reset.setBounds(30, 455, 100, 30);
        reset.addActionListener(this);


        history = new JButton("history");
        history.setBounds(150, 455, 100, 30);
        history.addActionListener(this);

        tf1 = new TextField();
        tf2 = new TextField();
        tf3 = new TextField();
        tf4 = new TextField();
        tf1.setBounds(10, 10, 260 - 20, 30);
        tf2.setBounds(50, 10, 100, 30);
        tf3.setBounds(50, 50, 100, 30);
        tf4.setBounds(20 + 115, 10, 105, 30);
        tf4.setEditable(false);

        A = new JRadioButton();
        B = new JRadioButton();
        C = new JRadioButton();
        D = new JRadioButton();

        A.setText("A.");
        B.setText("B.");
        C.setText("C.");
        D.setText("D.");
        ButtonGroup bgr = new ButtonGroup();
        bgr.add(A);
        bgr.add(B);
        bgr.add(C);
        bgr.add(D);
        A.setSelected(true);

        query = new JPanel();
        query.setLayout(null);
        query.setBorder(border);
        query.setBounds(10, 10, 300 - 40, 100);
        query.add(search);
        query.add(add);
        query.add(delete);
        query.add(tf1);

        editsl = new JPanel();
        editsl.setBorder(border);
        editsl.setBounds(10, 120, 300 - 40, 100);
        editsl.setLayout(null);
        editsl.add(tf2);
        editsl.add(tf3);
        editsl.add(edit);


        getSl = new JPanel();
        getSl.setBorder(border);
        getSl.setBounds(10, 230, 300 - 40, 50);
        getSl.setLayout(null);
        getSl.add(tf4);
        getSl.add(tdsl);
        getSl.add(rndsl);


        quiz = new JPanel();
        quiz.setLayout(null);
        quiz.setBorder(border);
        quiz.setBounds(10, 290, 300 - 40, 153);
        quiz.add(quizdef);
        quiz.add(quizsl);
        quiz.add(submit);

        JLabel txt1 = new JLabel("old :");
        JLabel txt2 = new JLabel("new:");
        txt1.setBounds(5, 10, 30, 30);
        txt2.setBounds(5, 50, 30, 30);
        JPanel questionBox = new JPanel();
        questionBox.setBounds(125, 5, 129, 142);
        questionBox.setBorder(border);

        A.setBounds(5, 60, 118, 20);
        B.setBounds(5, 80, 118, 20);
        C.setBounds(5, 100, 118, 20);
        D.setBounds(5, 120, 118, 20);

        JScrollPane scrl = new JScrollPane(question);
        scrl.setBounds(5, 5, 118, 50);
        question.setLineWrap(true);
        question.setBorder(border);
        question.setEditable(false);

        questionBox.add(scrl);
        questionBox.add(A);
        questionBox.add(B);
        questionBox.add(C);
        questionBox.add(D);
        questionBox.setLayout(null);
        quiz.add(questionBox);
        editsl.add(txt1);
        editsl.add(txt2);
        this.add(query);
        this.add(editsl);
        this.add(getSl);
        this.add(quiz);
        this.add(reset);
        this.add(history);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == search) {
            searchFrame adu = new searchFrame(tf1.getText(), this, dmg);
            this.setEnabled(false);
        } else if (e.getSource() == delete) {

            String sl = tf1.getText();

            TreeSet<String> def = dmg.getDef(sl);
            if (def == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "slang does not exist",
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            int result = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure to delete this slang?",
                    "Option",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.NO_OPTION) {
                return;
            }
            dmg.deleteDef(sl);
            dmg.updateDelSl(sl);
            for (String s : def) {
                TreeSet<String> g = dmg.getSl(s);
                g.remove(sl);
                if (g.isEmpty()) {
                    dmg.deleteSl(s);
                }
            }
        } else if (e.getSource() == add) {
            String sl = tf1.getText();
            if (sl.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "please input some slang",
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            TreeSet<String> dt = dmg.getDef(sl);
            if (dt != null) {
                JOptionPane.showMessageDialog(
                        this,
                        "slang does exist",
                        "add service",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            addFrame af = new addFrame(sl, dmg, this);
            dmg.updateAddSl(sl);
        } else if (e.getSource() == quizdef) {
            String Sl = dmg.ramdomSl();
            String qzdef = dmg.getDef(Sl).first();
            question.setText("Which is " + qzdef + "'s slang");
            Random rnd = new Random();
            ansPos = (rnd.nextInt() % 4 + 4) % 4;
            String[] ans = new String[4];
            ans[ansPos] = Sl;
            for (int i = 0; i < 4; i++) {
                if (i != ansPos)
                    while (true) {
                        String rndSl = dmg.ramdomSl();
                        if (!dmg.getSl(qzdef).contains(rndSl)) {
                            ans[i] = rndSl;
                            break;
                        }
                    }
            }
            A.setText(ans[0]);
            B.setText(ans[1]);
            C.setText(ans[2]);
            D.setText(ans[3]);

            quizdef.setEnabled(false);
            quizsl.setEnabled(false);
            submit.setEnabled(true);
        } else if (e.getSource() == quizsl) {
            String rndSl = dmg.ramdomSl();
            question.setText("Which is " + rndSl + "'s definition");
            TreeSet<String> def = dmg.getDef(rndSl);
            Random rnd = new Random();
            ansPos = (rnd.nextInt() % 4 + 4) % 4;
            String[] ans = new String[4];
            ans[ansPos] = dmg.getDef(rndSl).first();
            for (int i = 0; i < 4; i++) {
                if (i != ansPos)
                    while (true) {
                        String rndDef = dmg.getDef(dmg.ramdomSl()).first();
                        if (!def.contains(rndDef)) {
                            ans[i] = rndDef;
                            break;
                        }
                    }
            }
            A.setText(ans[0]);
            B.setText(ans[1]);
            C.setText(ans[2]);
            D.setText(ans[3]);
            quizdef.setEnabled(false);
            quizsl.setEnabled(false);
            submit.setEnabled(true);
        } else if (e.getSource() == edit) {
            String oldSl = tf2.getText();
            TreeSet<String> dt = dmg.getDef(oldSl);
            if (dt == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "slang does not exist",
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            String newSl = tf3.getText();
            if (newSl.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "new slang can't be empty",
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            TreeSet<String> getnsl = dmg.getDef(newSl);
            if (getnsl != null) {
                JOptionPane.showMessageDialog(
                        this,
                        "slang does exist",
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            dmg.deleteDef(oldSl);
            dmg.addDef(newSl, dt);
            for (String def : dt) {
                TreeSet<String> st = dmg.getSl(def);
                st.remove(oldSl);
                st.add(newSl);
            }
            JOptionPane.showMessageDialog(
                    this,
                    "change success",
                    "edit service",
                    JOptionPane.INFORMATION_MESSAGE);
            dmg.updateDelSl(oldSl);
            dmg.updateAddSl(newSl);

        } else if (e.getSource() == tdsl) {
            tf4.setText(dmg.rndDateBasedSlang());
        } else if (e.getSource() == submit) {
            submit.setEnabled(false);
            quizsl.setEnabled(true);
            quizdef.setEnabled(true);
            int getSelectedPos;
            if (A.isSelected()) {
                getSelectedPos = 0;
            } else if (B.isSelected()) {
                getSelectedPos = 1;
            } else if (C.isSelected()) {
                getSelectedPos = 2;
            } else {
                getSelectedPos = 3;
            }
            boolean correct = getSelectedPos == ansPos;

            if (correct) {

                JOptionPane.showMessageDialog(
                        this,
                        "correct!!!",
                        "quiz service",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(
                    this,
                    "wrong answer, the correct answer is " + (char) (ansPos + 65),
                    "quiz service",
                    JOptionPane.INFORMATION_MESSAGE);
        } else if (e.getSource() == rndsl) {
            tf4.setText(dmg.ramdomSl());
        } else if (e.getSource() == reset) {
            dmg.resetDic();
            JOptionPane.showMessageDialog(
                    this,
                    "success",
                    "reset service",
                    JOptionPane.INFORMATION_MESSAGE);
        } else if (e.getSource() == history) {
            JOptionPane.showMessageDialog(
                    this, dmg.hislist()
                    ,
                    "history",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String args[]) {
        mainFrame mFrame = new mainFrame();
    }
}
