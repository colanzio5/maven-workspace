import java.util.ArrayList;

public class MarkovGram {
  protected String gram;
  protected ArrayList<String> next;
  
  public MarkovGram(String gram_) {
    gram = gram_;
    next = new ArrayList<String>();
  }

  public void addNext(String nextItem) {
    next.add(nextItem);
  }

  public String getRandomNext() {
    int randomIndex = (int)(Math.random() * next.size());
    return next.get(randomIndex);
  }

  public void dump() {
    System.out.println("'" + gram + "'");
    System.out.print("  ");
    System.out.println(next);
  }

}
