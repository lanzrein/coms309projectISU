package edu.iastate.cs.proj_309_vc_b_4.game.utils;

/**
 * Created by JeremyC on 11/5/2017.
 */

public class Lcontent
{
    /**
     * This Class is responsible for storing the obtained results from the Player table in mySQL
     */
        int time_taken, imageID, PlayerID;
        String name,  message;
        private double numberWins;
        private double numberLoss;

        public Lcontent(int PlayerID, String username, String message, int numberWins, int numberLoss, int imageID, int fastestTime)
        {
            this.PlayerID = PlayerID;
            this.name = username;
            this.numberWins = numberWins;
            this.numberLoss = numberLoss;
            this.time_taken = fastestTime;
            this.message = message;
            this.imageID = imageID;
        }



        /**
         * Retrieve the player's username
         * @return player's username
         */
        public String getPlayerName()
        {
            return name;
        }


        /**
         * Retrieve the time taken for a player to win a game.
         * @return time taken
         */
        public String getTimeTaken()
        {

            int minutes = time_taken/60;
            int seconds = time_taken%60;
            return Integer.toString(minutes) + " mins" + " " + Integer.toString(seconds) + " secs";
        }


        /**
         * Calculate the ratio between the number of wins and number of losses.
         * If number of wins is 0, it will produce 0. Whereas if number of loss is 0,
         * it will produce the number of wins value.
         * @return Win/Lose ratio
         */
        public String getRatio() {
            if(numberWins == 0 || numberLoss == 0)
            {
                return "N/A";
            }
            double res = numberWins/numberLoss;
            return String.format("%.2f", res);
        }


        /**
         * Calculate the total games that the player had played by adding the number of wins
         * and number of loss together.
         * @return total game played.
         */
        public String calcMostGames()
        {
            double total = numberLoss+numberWins;
            int real = (int) total;
            String res = Integer.toString(real);
            return res;
        }

    /**
     * Retrieve the player's ID
     * @return Player's ID
     */
    public String getID()
        {
            return Integer.toString(PlayerID);
        }

    }

