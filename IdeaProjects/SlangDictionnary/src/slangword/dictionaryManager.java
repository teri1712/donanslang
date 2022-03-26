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

    void addDef(String sl, TreeSet<String> def) {
        slangTree.put(sl, def);
    }

    void addSl(String def, TreeSet<String> sl) {
        defTree.put(def, sl);
    }

    TreeSet<String> deleteDef(String sl) {
        return slangTree.remove(sl);
    }

    TreeSet<String> deleteSl(String def) {
        return defTree.remove(def);
    }


    String ramdomSl() {
        Random rnd = new Random();
        long id = rnd.nextLong();
        id = (id % numSl + numSl) % numSl;
        return id_sl.get((int) id);
    }

    String hislist() {
        String g = "";
        for (String h : histry) g += "\n" + h;
        return g;
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

}
