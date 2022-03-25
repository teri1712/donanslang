package slangword;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TreeSet;

public class mainFrame extends JFrame implements ActionListener {

    JButton search, add, delete, edit, quizdef, quizsl, submit, reset, history, tdsl;
    TextField tf1, tf2, tf3, tf4;
    JPanel query, editsl, todayslang, quiz;
    JRadioButton A, B, C, D;
    dictionaryManager dmg;

    mainFrame() {

        Border border = BorderFactory.createLineBorder(Color.gray, 2);
        this.setTitle("slang word dictionary");
        this.setSize(300, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        dmg = new dictionaryManager();

        JLabel tsLb = new JLabel("today's slang : ");
        tsLb.setBounds(10, 10, 85, 30);


        search = new JButton();
        search.setBounds(6, 60, 74, 25);
        search.setText("search");
        search.setFocusable(false);
        search.addActionListener(this);

        add = new JButton();
        add.setText("add");
        add.setBounds(90, 60, 80, 25);
        add.setFocusable(false);

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


        tdsl = new JButton();
        tdsl.setBounds(200 - 3, 10, 55, 30);
        tdsl.setText("get");
        tdsl.addActionListener(this);


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

        tf1 = new TextField();
        tf2 = new TextField();
        tf3 = new TextField();
        tf4 = new TextField();
        tf1.setBounds(10, 10, 260 - 20, 30);
        tf2.setBounds(50, 10, 100, 30);
        tf3.setBounds(50, 50, 100, 30);
        tf4.setBounds(20 + 85, 10, 85, 30);
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


        todayslang = new JPanel();
        todayslang.setBorder(border);
        todayslang.setBounds(10, 230, 300 - 40, 50);
        todayslang.setLayout(null);
        todayslang.add(tf4);
        todayslang.add(tdsl);
        todayslang.add(tsLb);


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
        JLabel question = new JLabel("<html>" + "What is aaaaaaa's definition" + "</html>", SwingConstants.LEADING);

        A.setBounds(5, 60, 118, 20);
        B.setBounds(5, 80, 118, 20);
        C.setBounds(5, 100, 118, 20);
        D.setBounds(5, 120, 118, 20);
        question.setBounds(5, 5, 118, 50);
        question.setBorder(border);
        questionBox.add(question);
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
        this.add(todayslang);
        this.add(quiz);

        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == search) {
            searchFrame adu = new searchFrame(tf1.getText(), this, dmg);
            this.setEnabled(false);
        } else if (e.getSource() == delete) {
            int result = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure to delete this slang?",
                    "Option",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.NO_OPTION) {
                return;
            }
        } else if (e.getSource() == add) {
            String sl = tf1.getText();
            boolean exist = (dmg.getDef(sl) != null);
            if (exist) {
                JOptionPane.showMessageDialog(this, "slang does exist", "error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            TreeSet<String> slSet = new TreeSet<String>();
            System.out.println("please input the definitions, each in a line.");
            for (int i = 0; i < num; i++) {
                slSet.add(def);
                TreeSet<String> defSet = dmg.defTree.get(def);
                if (defSet == null) {
                    defSet = new TreeSet<String>();
                    dmg.defTree.put(def, defSet);
                }
                defSet.add(sl);
            } else if (e.getSource() == submit) {
                boolean correct;
                if (A.isSelected()) {
                } else if (B.isSelected()) {
                } else if (C.isSelected()) {
                } else if (D.isSelected()) {
                }
                JOptionPane.showMessageDialog(this, "response", "response", JOptionPane.INFORMATION_MESSAGE);

                quizdef.setEnabled(true);
                quizsl.setEnabled(true);
                submit.setEnabled(false);
            } else if (e.getSource() == quizdef) {
                quizdef.setEnabled(false);
                quizsl.setEnabled(false);
                submit.setEnabled(true);
            } else if (e.getSource() == quizsl) {
                quizdef.setEnabled(false);
                quizsl.setEnabled(false);
                submit.setEnabled(true);
            } else if (e.getSource() == edit) {

            } else if (e.getSource() == tdsl) {

            }
        }

        public static void main (String args[]){
            mainFrame mFrame = new mainFrame();
        }
    }
