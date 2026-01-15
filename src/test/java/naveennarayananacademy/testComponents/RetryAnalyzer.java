package naveennarayananacademy.testComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    int count = 0;
    int MAX_RETRY = 1;
	
	@Override
	public boolean retry(ITestResult result) {
		if (count < MAX_RETRY) {
            count++;
            return true;
        }
		return false;
	}

}
