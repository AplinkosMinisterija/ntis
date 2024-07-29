package eu.itreegroup.spark.commons.grammar;

import java.util.HashMap;
import java.util.Map;

public class LtCaseGrammar {

    private static Map<Integer, Map<Integer, String>> endings = new HashMap<>();

    static {
        fillEndings();
    }

    private static void fillEndings() {
        endings.put(1, new HashMap<>());
        endings.get(1).put(1, "as");
        endings.get(1).put(2, "o");
        endings.get(1).put(3, "ui");
        endings.get(1).put(4, "ą");
        endings.get(1).put(5, "u");
        endings.get(1).put(6, "yje");
        endings.get(1).put(7, "ai");
        endings.get(1).put(11, "ai");
        endings.get(1).put(12, "ų");
        endings.get(1).put(13, "ams");
        endings.get(1).put(14, "us");
        endings.get(1).put(15, "ais");
        endings.get(1).put(16, "uose");
        endings.get(1).put(17, "ai");

        endings.put(2, new HashMap<>());
        endings.get(2).put(1, "ias");
        endings.get(2).put(2, "io");
        endings.get(2).put(3, "iui");
        endings.get(2).put(4, "ią");
        endings.get(2).put(5, "iu");
        endings.get(2).put(6, "yje");
        endings.get(2).put(7, "y");

        endings.put(4, new HashMap<>());
        endings.get(4).put(1, "us");
        endings.get(4).put(2, "aus");
        endings.get(4).put(3, "ui");
        endings.get(4).put(4, "ų");
        endings.get(4).put(5, "umi");
        endings.get(4).put(6, "iuje");
        endings.get(4).put(7, "au");

        endings.put(5, new HashMap<>());
        endings.get(5).put(1, "ius");
        endings.get(5).put(2, "iaus");
        endings.get(5).put(3, "iui");
        endings.get(5).put(4, "ių");
        endings.get(5).put(5, "iumi");
        endings.get(5).put(6, "oje");
        endings.get(5).put(7, "iau");

        endings.put(6, new HashMap<>());
        endings.get(6).put(1, "a");
        endings.get(6).put(2, "os");
        endings.get(6).put(3, "ai");
        endings.get(6).put(4, "ą");
        endings.get(6).put(5, "ia");
        endings.get(6).put(6, "oje");
        endings.get(6).put(7, "a");

        endings.put(8, new HashMap<>());
        endings.get(8).put(1, "ė");
        endings.get(8).put(2, "ės");
        endings.get(8).put(3, "ei");
        endings.get(8).put(4, "ę");
        endings.get(8).put(5, "e");
        endings.get(8).put(6, "ėje");
        endings.get(8).put(7, "e");

        endings.put(9, new HashMap<>());
        endings.get(9).put(1, "is");
        endings.get(9).put(2, "ies");
        endings.get(9).put(3, "iai");
        endings.get(9).put(4, "į");
        endings.get(9).put(5, "imi");
        endings.get(9).put(6, "yje");
        endings.get(9).put(7, "ie");

        endings.put(10, new HashMap<>());
        endings.get(10).put(1, "is");
        endings.get(10).put(2, "ies");
        endings.get(10).put(3, "iui");
        endings.get(10).put(4, "į");
        endings.get(10).put(5, "imi");
        endings.get(10).put(6, "yje");
        endings.get(10).put(7, "ie");

        endings.put(11, new HashMap<>());
        endings.get(11).put(1, "uo");
        endings.get(11).put(2, "ens");
        endings.get(11).put(3, "iui");
        endings.get(11).put(4, "enį");
        endings.get(11).put(5, "eniu");
        endings.get(11).put(6, "yje");
        endings.get(11).put(7, "ie");

        endings.put(12, new HashMap<>());
        endings.get(12).put(1, "uo");
        endings.get(12).put(2, "s");
        endings.get(12).put(3, "iai");
        endings.get(12).put(4, "į");
        endings.get(12).put(5, "imi");
        endings.get(12).put(6, "yje");
        endings.get(12).put(7, "ie");

        endings.put(31, new HashMap<>());
        endings.get(31).put(1, "is");
        endings.get(31).put(2, "io");
        endings.get(31).put(3, "iui");
        endings.get(31).put(4, "į");
        endings.get(31).put(5, "iu");
        endings.get(31).put(6, "yje");
        endings.get(31).put(7, "i");
        endings.get(31).put(11, "iai");
        endings.get(31).put(12, "ių");
        endings.get(31).put(13, "iams");
        endings.get(31).put(14, "ius");
        endings.get(31).put(15, "iais");
        endings.get(31).put(16, "iuose");
        endings.get(31).put(17, "iai");

        endings.put(32, new HashMap<>());
        endings.get(32).put(1, "ys");
        endings.get(32).put(2, "io");
        endings.get(32).put(3, "iui");
        endings.get(32).put(4, "į");
        endings.get(32).put(5, "iu");
        endings.get(32).put(6, "uje");
        endings.get(32).put(7, "y");

        endings.put(33, new HashMap<>());
        endings.get(33).put(1, "tis");
        endings.get(33).put(2, "čio");
        endings.get(33).put(3, "čiui");
        endings.get(33).put(4, "tį");
        endings.get(33).put(5, "čiu");
        endings.get(33).put(6, "tyje");
        endings.get(33).put(7, "ti");

        endings.put(71, new HashMap<>());
        endings.get(71).put(1, "a");
        endings.get(71).put(2, "os");
        endings.get(71).put(3, "ai");
        endings.get(71).put(4, "ą");
        endings.get(71).put(5, "a");
        endings.get(71).put(6, "oje");
        endings.get(71).put(7, "a");

        endings.put(72, new HashMap<>());
        endings.get(72).put(1, "ia");
        endings.get(72).put(2, "ios");
        endings.get(72).put(3, "iai");
        endings.get(72).put(4, "ią");
        endings.get(72).put(5, "ia");
        endings.get(72).put(6, "ioje");
        endings.get(72).put(7, "ia");
    }

    public static String convertCase(String wordToConvert, int caseToConvert) {
        int prdg;
        int oldEnd = 0;
        String result = "";

        if (wordToConvert.endsWith("ias")) {
            prdg = 2;
            oldEnd = 3;
        } else if (wordToConvert.endsWith("ius")) {
            prdg = 5;
            oldEnd = 3;
        } else if (wordToConvert.endsWith("tis")) {
            prdg = 33;
            oldEnd = 3;
        } else if (wordToConvert.endsWith("as")) {
            prdg = 1;
            oldEnd = 2;
        } else if (wordToConvert.endsWith("is")) {
            prdg = 31;
            oldEnd = 2;
        } else if (wordToConvert.endsWith("ys")) {
            prdg = 32;
            oldEnd = 2;
        } else if (wordToConvert.endsWith("us")) {
            prdg = 4;
            oldEnd = 2;
        } else if (wordToConvert.endsWith("ia")) {
            prdg = 72;
            oldEnd = 2;
        } else if (wordToConvert.endsWith("a")) {
            prdg = 6;
            oldEnd = 1;
        } else if (wordToConvert.endsWith("i")) {
            prdg = 71;
            oldEnd = 1;
        } else if (wordToConvert.endsWith("k") || wordToConvert.endsWith("ė")) {
            prdg = 8;
            oldEnd = 1;
        } else {
            prdg = 0;
        }

        if (prdg == 0) {
            return result + " " + wordToConvert.substring(result.length() == 0 ? 0 : result.length() + 1, wordToConvert.length());
        } else {
            String ending = endings.get(prdg).get(caseToConvert);
            return wordToConvert.substring(0, wordToConvert.length() - oldEnd) + ending;
        }
    }

}
