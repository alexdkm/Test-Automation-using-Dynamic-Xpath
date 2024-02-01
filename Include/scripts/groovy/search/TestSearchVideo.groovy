package search
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testcase.TestCaseFactory
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable

import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By

import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import com.kms.katalon.core.webui.driver.DriverFactory

import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty

import com.kms.katalon.core.mobile.helper.MobileElementCommonHelper
import com.kms.katalon.core.util.KeywordUtil

import com.kms.katalon.core.webui.exception.WebElementNotFoundException
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.Keys as Keys
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane

public class TestSearchVideo {

	public TestSearchVideo() {
		super();
	}

	public void testSearch(Integer row){

		String data = 'Data Files/Test Data Search/Test Data Search'
		String inputSearch = findTestData(data).getValue('Search Youtube', row)
		TestData testDataSearch = TestDataFactory.findTestData(data)
		WebDriver driver = DriverFactory.getWebDriver()

		WebUI.delay(2)

		WebUI.setText(findTestObject('Object Repository/homePage/txt_Search'), inputSearch)

		WebUI.sendKeys(findTestObject('Object Repository/homePage/txt_Search'), Keys.chord(Keys.ENTER))

		String text = null;
		Map<String, Object> attribute = new HashMap<>();
		TestObject searchVideo = null;

		try {
			text = testDataSearch.getValue('Click Video', row)
		} catch (IOException e) {
			e.printStackTrace();
		}

		attribute.put('text', text)
		searchVideo = findTestObject('Object Repository/pageSearch/object_Hasil Search',attribute);

		boolean verifyVideo = WebUI.verifyElementVisibleInViewport(searchVideo, 2, FailureHandling.OPTIONAL)

		if(!verifyVideo) {
			for (int i = 0; i < 10; i++) {
				driver.findElement(By.tagName("body")).sendKeys(Keys.PAGE_DOWN)

				boolean verifyVideo2 = verifyVideo = WebUI.verifyElementVisibleInViewport(searchVideo, 2, FailureHandling.OPTIONAL)

				if (verifyVideo2) {
					WebUI.click(searchVideo)
					WebUI.delay(5)
					break
				}
				
				if (i == 9) {
					JOptionPane.showMessageDialog(null, "Video Tidak Ditemukan")
				}
			}
		} else if (verifyVideo) {
			WebUI.click(searchVideo)
			WebUI.delay(3)
		}
	}
}