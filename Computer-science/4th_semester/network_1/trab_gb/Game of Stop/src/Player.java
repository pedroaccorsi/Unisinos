import java.util.HashMap;
import java.util.Map;

public class Player{

    private String name;
    private int points =0;
    private Map<String, String> answers;

    public Player(String s_answers){
        this.answers = new HashMap<String, String>();
        String[] categories = s_answers.split(";");
        for(int i=0; i<categories.length; i++) {
            String[] ans = categories[i].split(":");
            if (i == 0) {
                this.name = ans[1];
            } else {
                this.answers.put(ans[0], ans[1]);
            }
        }
    }

    public String getValueByCategory(String category){
        return "" + this.answers.get(category);
    }

    public void addPoints(Integer points){
        this.points += points;
    }

    public String get_name(){
        return this.name;
    }

    public Integer getPoints(){
        return this.points;
    }
    public String answersToString(){
        return this.answers.toString();
    }

}