import java.io.IOException;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Stack;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;



public class DevneilPlayer implements PokerSquaresPlayer
{

	Card[][] gameBoard;
	
	boolean[][] isAvail;
	
	Hand[] hands;
	
	Hand hand0;
	Hand hand1;
	Hand hand2;
	Hand hand3;
	Hand hand4;
	Hand hand5;
	Hand hand6;
	Hand hand7;
	Hand hand8;
	Hand hand9;
	
	Random random;
	int randomCount = 5;		//Number of random first plays
	
	Logger logger;
	FileHandler fh; 
	
	@Override
	public void setPointSystem(PokerSquaresPointSystem system, long millis) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		System.out.println("INITIALIZE DEVNEIL");
		random = new Random();
		
		hand0 = new Hand(0);
		hand1 = new Hand(1);
		hand2 = new Hand(2);
		hand3 = new Hand(3);
		hand4 = new Hand(4);
		hand5 = new Hand(5);
		hand6 = new Hand(6);
		hand7 = new Hand(7);
		hand8 = new Hand(8);
		hand9 = new Hand(9);
		
		gameBoard = new Card[5][5];
		isAvail = new boolean[5][5];
		hands = new Hand[10];
		
		for(int i = 0; i < 5; i++)
		{
			for(int j = 0; j < 5; j++)
			{
				gameBoard[i][j] = null;
				isAvail[i][j] = true;
			}
		}
		
		hands[0] = hand0;
		hands[1] = hand1;
		hands[2] = hand2;
		hands[3] = hand3;
		hands[4] = hand4;
		hands[5] = hand5;
		hands[6] = hand6;
		hands[7] = hand7;
		hands[8] = hand8;
		hands[9] = hand9;
		
		logger = Logger.getLogger("Log");  
	    

	    try {  

	        // This block configure the logger with handler and formatter  
	        fh = new FileHandler("D:/Users/Adam/Documents/Eclipse/PokerSquares/src/Log.log");  
	        logger.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);  

	        logger.setUseParentHandlers(false);
	        
	        // the following statement is used to log any messages  
	        logger.info("DEVNEIL INITIALIZED");  

	    } catch (SecurityException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
	    
	    

		
		
	}

	@Override
	public int[] getPlay(Card card, long millisRemaining) {
		int randomEntry;
		
		PotentialAnalyzer PA;

		
		
		if(randomCount != 0)
		{
			randomCount--;
			randomEntry = random.nextInt(25);
			while(!isAvail[randomEntry / 5][randomEntry % 5])
			{
				randomEntry = random.nextInt(25);
			}
			
			int[] playPos = {randomEntry / 5, randomEntry % 5}; // decode it into row and column
			gameBoard[randomEntry / 5][randomEntry % 5] = card;
			isAvail[randomEntry / 5][randomEntry % 5] = false;
			updateHands();
			return playPos;
			
			
		}
		else
		{
			
			
			PriorityQueue<Move> PQ = new PriorityQueue<Move>();
			PA = new PotentialAnalyzer();
			
			int totalPotential;
			Move M;
			
			int bestX = 0, bestY = 0, horizHandID = 0, vertHandID = 5;
			for(int x = 0; x < 4; x++)
			{
				
				for(int y = 0; y < 4; y++)
				{
					if(isAvail[x][y])
					{
						
						totalPotential = 0;
						
						if(y == 0) horizHandID = 0;
						if(y == 1) horizHandID = 1;
						if(y == 2) horizHandID = 2;
						if(y == 3) horizHandID = 3;
						if(y == 4) horizHandID = 4;
						
						if(x == 0) vertHandID = 5;
						if(x == 1) vertHandID = 6;
						if(x == 2) vertHandID = 7;
						if(x == 3) vertHandID = 8;
						if(x == 4) vertHandID = 9;
						
						int[] loc = {x, y};
						for(Hand H: hands)
						{
							if(H.id == horizHandID || H.id == vertHandID)
							{
								totalPotential += PA.getPotential(H, card);
							}
							
							M = new Move(loc, totalPotential);
							PQ.add(M);
						}

					}
				}
			}

			
			/*
			PA = new PotentialAnalyzer(hands);
			Hand toPlay = PA.getHighestPotential(card);
			if(toPlay.id <= 4)		//If it is a horizontal hand
			{
				//Find the last available spot
				for(int i = 0; i < 5; i++)
				{
					if(isAvail[i][toPlay.id])
						xPos = i;
				}
			}
			else					//If it is a vertical hand
			{
				for(int i = 0; i < 5; i++)
				{
					if(isAvail[toPlay.id - 5][i])
						yPos = i;
				}
			}
			*/
			int[] playPos = PQ.poll().location;
			gameBoard[playPos[0]][playPos[1]] = card;
			isAvail[playPos[0]][playPos[1]] = false;
			updateHands();
			return playPos;
			
		}
	}

	public String getName() {
		return "DevneilPlayer";
	}
	
	public void updateHands()
	{
		for(int i = 0; i < 5; i++)
		{
			hand0.cards[i] = gameBoard[0][i];
			hand1.cards[i] = gameBoard[1][i];
			hand2.cards[i] = gameBoard[2][i];
			hand3.cards[i] = gameBoard[3][i];
			hand4.cards[i] = gameBoard[4][i];
			hand5.cards[i] = gameBoard[i][0];
			hand6.cards[i] = gameBoard[i][1];
			hand7.cards[i] = gameBoard[i][2];
			hand8.cards[i] = gameBoard[i][3];
			hand9.cards[i] = gameBoard[i][4];
			
		}
	}

	public static void main(String[] args) {
		PokerSquaresPointSystem system = PokerSquaresPointSystem.getAmeritishPointSystem();
		System.out.println(system);
		new PokerSquares(new DevneilPlayer(), system).play(); // play a single game
	}
}
