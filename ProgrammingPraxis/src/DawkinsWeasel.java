import java.util.Random;

public class DawkinsWeasel
{
	private final static int UPPER_CASE_A = 65;
	private final static int UPPER_CASE_Z = 90;
	private final static int SPACE = 32;

	private static String sQuote = "Methinks it is like a weasel";
	private static Random sRandom = new Random();
	
	public static void main(String[] args)
	{
		int highScore = 0;
		String[] attempts = new String[100];
		
		String attemptedQuote = generateRandomString(28);
		copyAttempts(attemptedQuote, attempts);

		while (highScore < 28)
		{
			mutateAttempts(attempts);
			int batchHighScoreIndex = 0;
			int batchHighScore = 0;

			for (int i = 0; i < attempts.length; i++)
			{
				String attempt = attempts[i];
				int currentScore = compareAndScore(attempt);
				if (currentScore > batchHighScore)
				{
					batchHighScoreIndex = i;
					batchHighScore = currentScore;
				}
				highScore = batchHighScore;				
			}
			System.out.println(attempts[batchHighScoreIndex]);
			copyAttempts(attempts[batchHighScoreIndex], attempts);
		}
		System.out.println("Done!");
		System.out.println(attempts[0]);
	}

	private static void copyAttempts(String attemptedQuote, String[] attempts)
	{
		for (int i = 0; i < 100; i++)
		{
			attempts[i] = attemptedQuote;
		}
	}

	private static void mutateAttempts(String[] attempts)
	{
		for (int i = 0; i < attempts.length; i++)
		{
			String attempt = attempts[i];
			char[] attemptAsChars = attempt.toCharArray();
			for (int j = 0; j < attemptAsChars.length; j++)
			{
				if (shouldMutate())
				{
					attemptAsChars[j] = generateRandomChar();
				}
			}
			final String mutatedAttempt = new String(attemptAsChars);
			attempts[i] = mutatedAttempt;
		}
	}

	private static String generateRandomString(int length)
	{
		String randomString = "";
		
		for (int i = 0; i < length; i++)
		{
			randomString += generateRandomChar().toString();
		}
		
		return randomString;
	}
	
	private static Character generateRandomChar()
	{
	    final int codeRange = UPPER_CASE_Z - UPPER_CASE_A;
	    final int nextInt = sRandom.nextInt(codeRange + 2);
	    int randomUpperCaseAscii = UPPER_CASE_A + nextInt;
	    if (randomUpperCaseAscii == UPPER_CASE_Z + 1)
	    {
	    	randomUpperCaseAscii = SPACE;
	    }

	    return new Character((char) randomUpperCaseAscii);
	}
	
	private static boolean shouldMutate()
	{
		final int targetThreshold = 5;
		int score = sRandom.nextInt(100) + 1;
		return score <= targetThreshold;
	}
	
	private static int compareAndScore(String attempt)
	{
		final String upperCaseQuote = sQuote.toUpperCase();
		char[] targetAsChars = new char[upperCaseQuote.length()];
		
		int count = 0;
		for (char c : upperCaseQuote.toCharArray())
		{
			if (c == SPACE)
			{
				targetAsChars[count] = c;
				count++;
			}
			if (c >= UPPER_CASE_A && c <= UPPER_CASE_Z)
			{
				targetAsChars[count] = c;
				count++;
			}
		}
		
		int score = 0;		
		char[] attemptAsChars = attempt.toCharArray();
		for (int i = 0; i< attemptAsChars.length; i++)
		{
			if (attemptAsChars[i] == targetAsChars[i])
			{
				score ++;
			}
		}
		return score;
	}
}
