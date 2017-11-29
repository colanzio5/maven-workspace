import java.util.ArrayList;
import java.util.HashSet;
import com.decontextualize.a2z.TextFilter;

public class NGram extends TextFilter {

  public static void main(String[] args) {
    new NGram().run();
  }

  protected ArrayList<String> words = new ArrayList<String>();
  protected HashSet<String> grams = new HashSet<String>();
  protected int n = 3; // order

  public void eachLine(String line) {
    String[] tokens = line.split(" ");
    for (String t: tokens) {
      t = t.toLowerCase();
      t = t.replaceAll("[.,:;?!]", "");
      words.add(t);
    }
  }

  public void end() {
    for (int i = 0; i < words.size() - n + 1; i++) {
      String key = "";
      for (int j = i; j < i + n; j++) {
        key += words.get(j);
        key += " ";
      }
      grams.add(key);
    }
    for (String ngram: grams) {
      println(ngram);
    }
  }

}
