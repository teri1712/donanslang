package slangword;

import java.io.*;
import java.util.*;
import java.time.*;

public class dictionaryManager {
    final private String filename = "slang.txt";
    private TreeMap<String, TreeSet<String>> slangTree;
    private TreeMap<String, TreeSet<String>> defTree;
    private ArrayList<String> histry;

    // for randomized technique
    private TreeMap<Integer, String> id_sl;
    private TreeMap<String, Integer> sl_id;
    private int numSl;

    void importFile() {
        try {
            BufferedReader rd = new BufferedReader(new FileReader(filename));
            rd.readLine();
            String getl;
            while ((getl = rd.readLine()) != null) {
                String slang = "";
                TreeSet<String> getDef = new TreeSet<String>();
                int i = 0;
                while (i < getl.length() && getl.charAt(i) != '`') {
                    slang += getl.charAt(i);
                    i++;
                }
                if (i == getl.length()) // wrong format
                    continue;
                i++;
                while (i < getl.length()) {
                    String def = "";
                    while (i < getl.length() && getl.charAt(i) != '|') {
                        def += getl.charAt(i);
                        i++;
                    }
                    i += 2;
                    if (def.equals(""))
                        continue;
                    getDef.add(def);
                    TreeSet<String> t = defTree.get(def);
                    if (t == null) {
                        t = new TreeSet<String>();
                        defTree.put(def, t);
                    }
                    t.add(slang);
                }
                if (!getDef.isEmpty()) {
                    slangTree.put(slang, getDef);
                    updateAddSl(slang);
                }
            }
            rd.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public TreeSet<String> getDef(String slang) {
        return slangTree.get(slang);
    }

    public TreeSet<String> getSl(String def) {
        return defTree.get(def);
    }

    void printSet(TreeSet<String> s) {
        Iterator<String> itr = s.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
    }

    void resetDic() {
        slangTree.clear();
        defTree.clear();
        sl_id.clear();
        id_sl.clear();
        numSl = 0;
        histry.clear();
        importFile();
    }

    void updateHis(String s) {
        histry.add(s);
    }

    void printList(ArrayList<String> l) {
        if (l.isEmpty()) System.out.println("empty");
        for (String i : l) {
            System.out.println(i);
        }
    }


    //for randomized technique
    void updateAddSl(String sl) {
        sl_id.put(sl, numSl);
        id_sl.put(numSl, sl);
        numSl++;
    }

    void updateDelSl(String sl) {
        int getId = sl_id.get(sl);
        sl_id.remove(sl);
        id_sl.remove(getId);
        if (getId == numSl - 1) {
            numSl--;
            return;
        }
        String lastSl = id_sl.get(numSl - 1);
        sl_id.remove(lastSl);
        sl_id.put(lastSl, getId);
        id_sl.remove(numSl - 1);
        id_sl.put(getId, lastSl);
        numSl--;
    }

    String rndDateBasedSlang() {
        LocalDate d = LocalDate.now();
        int id = (d.hashCode() % numSl + numSl) % numSl;
        return id_sl.get(id);
    }

    String ramdomSl() {
        Random rnd = new Random();
        long id = rnd.nextLong();
        id = (id % numSl + numSl) % numSl;
        return id_sl.get((int) id);
    }

    dictionaryManager() {
        slangTree = new TreeMap<String, TreeSet<String>>();
        defTree = new TreeMap<String, TreeSet<String>>();
        histry = new ArrayList<String>();
        id_sl = new TreeMap<Integer, String>();
        sl_id = new TreeMap<String, Integer>();
        numSl = 0;
        importFile();
    }

    int adu() {
        return numSl;
    }

    public static void main(String args[]) {
        dictionaryManager dmg = new dictionaryManager();
        System.out.println("Menu:  ");
        System.out.println("1: search for slang.");
        System.out.println("2: search for definition.");
        System.out.println("3: slang searching history.");
        System.out.println("4: add new slang.");
        System.out.println("1: search slang.");
        System.out.println("6: delete a slang.");
        System.out.println("7: Reset dictionary.");
        System.out.println("8: On this day slang.");
        System.out.println("9: slang-definition quiz.");
        System.out.println("10: definition-slang quiz.");
        System.out.println("0: quit the proggram.");
        int cmd = -1;
        Scanner scr = new Scanner(System.in);
        while (true) {
            System.out.println("please input the command: ");
            cmd = scr.nextInt();
            scr.nextLine(); // skip endline character
            if (cmd == 1) {
                System.out.println("please input the slang: ");
                String sl = scr.nextLine();
                dmg.updateHis(sl);
                TreeSet<String> ans = dmg.getDef(sl);
                System.out.println("Result: ");
                if (ans == null) {
                    System.out.println("slang does not exist.");
                    continue;
                }
                dmg.printSet(ans);
            } else if (cmd == 2) {
                System.out.println("please input the definition: ");
                String def = scr.nextLine();
                TreeSet<String> ans = dmg.getSl(def);
                System.out.println("Result: ");
                if (ans == null) {
                    System.out.println("definition does not exist.");
                    continue;
                }
                dmg.printSet(ans);
            } else if (cmd == 3) {
                System.out.println("Result: ");
                dmg.printList(dmg.histry);
            } else if (cmd == 4) {
                System.out.println("please input the new slang : ");
                String sl = scr.nextLine();
                boolean exist = (dmg.slangTree.get(sl) != null);
                if (exist) {
                    System.out.println("the slang existed.");
                    continue;
                }
                System.out.println("please input the number of definition : ");
                int num;
                while ((num = scr.nextInt()) <= 0) {
                    System.out.println("invalid number. please input again.");
                }
                scr.nextLine();
                TreeSet<String> slSet = new TreeSet<String>();
                System.out.println("please input the definitions, each in a line.");
                for (int i = 0; i < num; i++) {
                    String def = scr.nextLine();
                    slSet.add(def);
                    TreeSet<String> defSet = dmg.defTree.get(def);
                    if (defSet == null) {
                        defSet = new TreeSet<String>();
                        dmg.defTree.put(def, defSet);
                    }
                    defSet.add(sl);
                }
                dmg.slangTree.put(sl, slSet);
                dmg.updateAddSl(sl);
                System.out.println("success");

            } else if (cmd == 5) {
                System.out.println("please input the slang");
                System.out.println("Options: ");
                System.out.println("1: change an existed slang to a new slang ");
                System.out.println("2: add a new definition to a slang.");
                System.out.println("input command : ");
                int getOp = scr.nextInt();
                while (getOp != 1 && getOp != 2) {
                    System.out.println("wrong command, please input again.");
                    System.out.println("input command: ");
                }
                scr.nextLine();
                if (getOp == 1) {
                    System.out.println("please input the slang : ");
                    String sl = scr.nextLine();
                    TreeSet<String> getDef = dmg.getDef(sl);
                    if (getDef == null) {
                        System.out.println("the slang does not exist.");
                        continue;
                    }
                    System.out.println("please input the new slang's name : ");
                    String newSl = scr.nextLine();
                }

            } else if (cmd == 6) {
                System.out.println("please input the slang you want to delete : ");
                String sl = scr.nextLine();
                TreeSet<String> slSet = dmg.slangTree.get(sl);
                if (slSet == null) {
                    System.out.println("slang does not exist");
                    continue;
                }
                for (String def : slSet) {
                    TreeSet<String> defSet = dmg.defTree.get(def);
                    defSet.remove(sl);
                    if (defSet.isEmpty()) dmg.defTree.remove(def);
                }
                dmg.slangTree.remove(sl);
                dmg.updateDelSl(sl);
                System.out.println("success");
            } else if (cmd == 7) {
                dmg.resetDic();
                System.out.println("success");
            } else if (cmd == 8) {
                String rndSl = dmg.rndDateBasedSlang();
                System.out.println("on this date slang : " + rndSl);
                System.out.println("defitions: ");
                dmg.printSet(dmg.getDef(rndSl));
            } else if (cmd == 9) {
                System.out.println("Quiz: ");
                String rndSl = dmg.ramdomSl();
                TreeSet<String> def = dmg.getDef(rndSl);
                Random rnd = new Random();
                int rndPos = (rnd.nextInt() % 4 + 4) % 4;
                String[] ans = new String[4];
                ans[rndPos] = dmg.getDef(rndSl).first();
                for (int i = 0; i < 4; i++) {
                    if (i != rndPos)
                        while (true) {
                            String rndDef = dmg.getDef(dmg.ramdomSl()).first();
                            if (!def.contains(rndDef)) {
                                ans[i] = rndDef;
                                break;
                            }
                        }
                }
                System.out.println("What is " + rndSl + "'s definition ( only 1 answer ): ");
                for (int i = 0; i < 4; i++) {
                    System.out.println((char) (i + 65) + ". " + ans[i]);
                }
                System.out.println("input your answer: ");
                char getinput = scr.nextLine().charAt(0);
                if ((int) getinput - 65 == rndPos) {
                    System.out.println("Correct!!");
                } else {
                    System.out.println("False answer, the answer is " + (char) (rndPos + 65));
                }
            } else if (cmd == 10) {
                System.out.println("Quiz: ");
                String Sl = dmg.ramdomSl();
                String def = dmg.getDef(Sl).first();
                Random rnd = new Random();
                int rndPos = (rnd.nextInt() % 4 + 4) % 4;
                String[] ans = new String[4];
                ans[rndPos] = Sl;
                for (int i = 0; i < 4; i++) {
                    if (i != rndPos)
                        while (true) {
                            String rndSl = dmg.ramdomSl();
                            if (!dmg.getSl(def).contains(rndSl)) {
                                ans[i] = rndSl;
                                break;
                            }
                        }
                }
                System.out.println("Which slang is " + def + " ( only 1 answer ): ");
                for (int i = 0; i < 4; i++) {
                    System.out.println((char) (i + 65) + ". " + ans[i]);
                }
                System.out.println("input your answer: ");
                char getinput = scr.nextLine().charAt(0);
                if ((int) getinput - 65 == rndPos) {
                    System.out.println("Correct!!");
                } else {
                    System.out.println("False answer, the answer is " + (char) (rndPos + 65));
                }
            } else if (cmd == 0) {
                break;
            } else {
                System.out.println("invalid command.");
            }
        }
    }
}
