import algorithms.Autocompletion;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Autocompletion autocompletion = new Autocompletion();
        autocompletion.insertWord("Test");
        autocompletion.insertWord("Terror");
        autocompletion.insertWord("Apple");
        autocompletion.insertWord("Appendix");
        autocompletion.insertWord("Auto");
        autocompletion.insertWord("Baum");
        final List<String> words = autocompletion.predictWords("app");
        System.out.println(words);
    }

}
