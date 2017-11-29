import com.decontextualize.a2z.TextFilter;

public class MarkovFilter extends TextFilter {

  public static void main(String[] args) {
    new MarkovFilter().run();
  }

  Markov mark = new Markov(4, 100);

  public void eachLine(String line) {
    mark.feedLine(line);
  }

  public void end() {
    mark.dump();
    println(mark.generateLine());
  }

}

