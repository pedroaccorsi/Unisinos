import Constants.Connection;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class WinnerCalculator {
    private ArrayList<String> answers_per_player;
    private ArrayList<String> players = new ArrayList<String>();
    private ArrayList<Integer> points = new ArrayList<Integer>();
    private Integer num_players = Connection.MAX_CLIENTS;;
    private String winner = "";


    public WinnerCalculator(ArrayList<String> answers_per_player){
        this.answers_per_player = answers_per_player;
        for( String str : this.answers_per_player ){
            String[] arr2 = str.split(":");
            this.players.add(arr2[1]);
        }
    }

    public String setWinner(){

        for(int i=0; i<Connection.MAX_CLIENTS; i++){

            int pontos = 0;

            String[] arr = this.answers_per_player.get(i).split(";");
            int num_of_answers = (arr.length-1);

            if(num_of_answers<=0)
                continue;

            for(int j=1; j<=num_of_answers; j++){

                boolean achei_igual = false;
                String curr_category = arr[j].split(":")[0];
                String curr_answer   = arr[j].split(":")[1];

                for(int k=0; k<Connection.MAX_CLIENTS && achei_igual == false; k++){
                    if(i==k)
                        continue;

                    String[] arr2 = this.answers_per_player.get(k).split(";");

                    int num_of_answers2 = (arr2.length-1);

                    if(num_of_answers2<=0)
                        continue;

                    for(int w=1; w<=num_of_answers2 && achei_igual == false; w++) {
                        String comparing_category = arr2[w].split(":")[0];
                        String comparing_answer = arr2[w].split(":")[1];

                        achei_igual = curr_category.equals(comparing_category) && curr_answer.equals(comparing_answer);
                    }

                }
                pontos += achei_igual ? 10 : 5;
            }
            points.add(pontos);
        }

        int size = points.size();
        System.out.println("");

        for(int i=0; i<size; i++){
            System.out.println("player: " + answers_per_player.get(i).split(";")[0].split(":")[1] + ", points: " + points.get(i) );
        }

        return "";
    }


}
