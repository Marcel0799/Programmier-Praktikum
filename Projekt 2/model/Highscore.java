package model;

import java.io.Serializable;
import java.util.*;

/**
 * this class should show all players who are in the highsore list and show the points/score each player has.
 *
 * @author Elidona Sh.
 * @author changed by Marcel (if theire are any Questions)
 */
public class Highscore implements Serializable {

	private static final int MAX_HIGHSCORES = 10;
	    private  ArrayList<Score> scores ;

/**
 * contructor for the highscore
 */
		public Highscore () { scores = new ArrayList<Score>();
			}
/**
 * Returns the number of elements in the array 
 * @return - scores length 
 */
	    public int size() { return scores.size();}
/**
 * add the element to the array 
 * @param score - score
 */
	    public void add(Score score) {
			if(score == null) {return;}
			scores.add(score);
			Comparator<Score> toCompareScores = Comparator.comparing(Score::getPoints, Collections.reverseOrder());
			scores.sort(toCompareScores);

			if(scores.size() > MAX_HIGHSCORES) {
				scores.remove(size()-1);
		    }
	    }
	    

/**
 *   Removes a single instance of the specified element from this Array(Score), if it is present.
 *   @param scr - a score
 */
	    public void remove (Score scr) { 
			scores.remove(scr);
	    }
/**
 * this method schould convert a array ( Score) to a ArrayList <Score> for the ListView in the HighscoreViewController 
 * @return scoresAsArrayList
 */
	    public ArrayList<Score> convertToList () {
	    	return scores;
	    }
	    
/**
 * Removes all of the elements from this collection
 */
	    public void clear() { scores.clear();}
	     
/**
 * get scores 
 * @return - scores 
 */
	   public Score[] getScores() {
	   		Score[] result = new Score[scores.size()];
	   		for(int i = 0 ; i < scores.size() ; i++) {
	   			result[i] = scores.get(i);
			}
	   		return result;
	   }

	   public ArrayList<Score> getScoresList() {
	   		return scores;
	   }

}