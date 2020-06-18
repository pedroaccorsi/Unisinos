import java.util.ArrayList;

public class WinnerCalculator {
    private ArrayList<Player> players;
    private ArrayList<String> all_categories;
    private Character letter;
    private String winner = "";

    public WinnerCalculator(ArrayList<Player> players, char letter){
        this.all_categories = new GameOfStop().getCategories();
        this.players = players;
        this.letter = letter;
    }

    private void setWinner(){
        int num_players = this.players.size();
        int max = Integer.MIN_VALUE;

        for(Player player : this.players){
            for(String category : this.all_categories){
                boolean same_answer = false;
                String ans_player = player.getValueByCategory(category);
                if(ans_player.isBlank() || ans_player.equals("null") || ans_player == null || ans_player.startsWith(""+this.letter) == false)
                    continue;
                for(Player player1 : this.players){
                    if(player.get_name().equals(player1.get_name()) || same_answer)
                        continue;
                    String ans_player1 = player1.getValueByCategory(category);

                    same_answer = ans_player.equals(ans_player1);
                }
                player.addPoints( same_answer ? 5 : 10);
            }
        }


        for(Player player : this.players){
            //System.out.println("player: " + player.get_name() + ", points: " + player.getPoints());
            if(player.getPoints() > max){
                max = player.getPoints();
                this.winner = player.get_name();
            }
        }
    }

    public String getWinner(){
        if(this.winner.isBlank())
            setWinner();
        return this.winner;
    }


}
