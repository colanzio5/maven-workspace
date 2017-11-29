import java.util.HashMap;
import java.util.ArrayList;

public class Markov {
  protected HashMap<String, MarkovGram> grams;
  protected ArrayList<String> lineStarts;
  protected int n;
  protected int max;

  public Markov(int n_, int max_) {
    n = n_;
    max = max_;
    grams = new HashMap<String, MarkovGram>();
    lineStarts = new ArrayList<String>();
  }

  public void feedLine(String line) {

    // keep track of the first n characters of this line, for generation
    // later
    String lineStart = line.substring(0, n);
    lineStarts.add(lineStart);

    // look at every group of n characters in the current line and record
    // what character follows that group
    for (int i = 0; i < line.length() - n; i++) {
      String gramStr = line.substring(i, i+n);
      String next = String.valueOf(line.charAt(i+n));
      // if we've already seen this gram, add a new next character
      if (grams.containsKey(gramStr)) {
        MarkovGram g = grams.get(gramStr);
        g.addNext(next);
      }
      // otherwise, create a new gram and store it
      else {
        MarkovGram g = new MarkovGram(gramStr);
        g.addNext(next);
        grams.put(gramStr, g);
      }
    }
    
  }

  public String generateLine() {

    int randomIndex = (int)(Math.random() * lineStarts.size());
    String current = lineStarts.get(randomIndex);

    String output = current;

    for (int i = 0; i < max; i++) {
      if (grams.containsKey(current)) {
        MarkovGram g = grams.get(current);
        String nextStr = g.getRandomNext();
        output += nextStr;
        // single-argument form of substring returns everything from index to
        // end of string
        current = output.substring(output.length() - n);
      }
      else {
        break;
      }
    }
    return output;

  }

  public void dump() {
    for (MarkovGram m: grams.values()) {
      m.dump();
    }
  }

}
