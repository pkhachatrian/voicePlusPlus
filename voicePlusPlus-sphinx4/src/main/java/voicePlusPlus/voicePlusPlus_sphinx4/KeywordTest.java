/**
 * 
 */
package voicePlusPlus.voicePlusPlus_sphinx4;

import junit.framework.TestCase;
import java.util.Hashtable;

public class KeywordTest  {

	protected void setUp() {
		
	}

	protected void tearDown() {
		
	}

	/**
	 * Test method for {@link voicePlusPlus.voicePlusPlus_sphinx4.Keyword#removeNonKeywords(java.lang.String)}.
	 */
	public void testRemoveNonKeywords() {
		String nonKeywords;
		String keywordsOnly;
		
		nonKeywords= "Hello what's up dog invocabot schedule a meeting tomorrow at nine p m";
		keywordsOnly = "schedule meeting tomorrow nine pm";
		
		System.out.println(keywordsOnly);
		
		assertEquals(Keyword.removeNonKeywords(nonKeywords), keywordsOnly);
	}

}
