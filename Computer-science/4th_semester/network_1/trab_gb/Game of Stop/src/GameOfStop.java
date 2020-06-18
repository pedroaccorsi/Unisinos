import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GameOfStop implements Serializable {
    private static final long serialVersionUID = -7524021481133441646L;
    private ArrayList<String> Categories = new ArrayList<String>();
    private String Answers  = "";
    private char letter;
    private String player;

    public GameOfStop(){
        this.letter = Character.toUpperCase( (char)(new Random().nextInt(26)+ 'a') );
        this.Categories.add("Nome");
        this.Categories.add("CEP");
        this.Categories.add("Cor");
        this.Categories.add("Comida");
        this.Categories.add("Animal");
        this.Categories.add("Marca");
    }

    public String getPlayer(){
        return this.player;
    }

    public void setName(String player){
        this.player = player;
        this.Answers += "player:" + player + ";";
    }

    public ArrayList<String> getCategories(){
        return this.Categories;
    }

    public char getLetter(){
        return this.letter;
    }

    public void AddNewAnswer(String answer){
        String[] arr = answer.trim().split(":");
        this.Answers += arr[0].trim() + ":" + arr[1].trim() + ";";
    }

    public Boolean isStop(){
        String[] arr = this.Answers.split(";");
        return arr.length == this.Categories.size() + 1;
    }

    public String getAnswers(){
        return this.Answers;
    }

    public String getRules(){
        String rt = "";
        rt += "Letter: " + letter + "\n";
        rt += "Cattegories: " + this.Categories.toString();
        return rt;
    }

    @Override
    public String toString(){
        String rt= "";
        rt += "Player: "  + this.player;
        rt += "Answers: " + this.Answers;
        return rt;
    }

}
