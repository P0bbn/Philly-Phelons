import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

/**
 * AppQuery will conduct the query and call the right classes/methods
 * 
 * @author claudia peinado romano
 *
 */

public class AppQuery {

	private static String comboBox;
	private static String radioBox;
	private static HashMap<String, Double> queryPieChartLabels = new HashMap<String, Double>();


	/**
	 * queryAnswer() will run the appropriate search
	 * 
	 * @return String of answers
	 */

	public static String queryAnswer() {

		CrimeReader reader;
		@SuppressWarnings("unused")
		String resultString = "";
		try {
			reader = new CrimeReader();
			@SuppressWarnings("static-access")
			ArrayList<Crime> crimeArray = reader.getAllCrimeArray();

			if (comboBox.equals("Most Frequent Crime")) {
				resultString = getMostFrequentCrime(crimeArray);
			} else if (comboBox.equals("Regions with Highest Crimes")) {
				resultString = getRegionWithHighestCrime(crimeArray);
			} else if (comboBox.equals("Region with Disturbance Crime")) {
				resultString = getRegionWithDisturbanceCrime(crimeArray);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return "Results for the " + comboBox + " for the " + radioBox + " : ";
	}

	/**
	 * populateChartHashDouble will fill in labels for the pie chart and read results when Hash is made up of String: Double
	 * @param answer
	 * @return
	 */
	
	private static String populateChartHashDouble(HashMap<String, Double> answer) {
		String resultString = "";
		for (Map.Entry<String, Double> keyAnswer : answer.entrySet()) {
			String key = "District " + keyAnswer.getKey();
			Double value = keyAnswer.getValue();
			resultString = resultString + " " + key + " " + value;
			queryPieChartLabels.put(key, value);
		}
		return resultString;
	}

	/**
	 * populateChartHashString will fill in labels for pie chart and read results when Hash is made up of Strings
	 * @param answer
	 * @return
	 */
	
	private static String populateChartHashString(HashMap<String, String> answer) {
		String resultString = "";
		for (Map.Entry<String, String> keyAnswer : answer.entrySet()) {
			String key = keyAnswer.getKey();
			String value = keyAnswer.getValue();
			resultString = resultString + " " + key + " " + value;
			queryPieChartLabels.put((key + " : " + value), 10.0);

		}
		return resultString;
	}

	/**
	 * getRegionWithDisturbanceCrime will search for the frequent crimes for each
	 * Region
	 * 
	 * @param crimeArray
	 * @return
	 */
	private static String getRegionWithDisturbanceCrime(ArrayList<Crime> crimeArray) {

		// Question Regions with their Disturbance Crime Rate Per League
		RegionQuestionsLeague thisRegionQuestions = new RegionQuestionsLeague(crimeArray);
		HashMap<String, Double> answer = thisRegionQuestions.findRegionWithHighestDisturbanceRatePerLeague(crimeArray,
				radioBox);

		return populateChartHashDouble(answer);
	}

	/**
	 * getRegionWithHighestCrime will search for the frequent crimes for each Region
	 * 
	 * @param crimeArray
	 * @return
	 */

	private static String getRegionWithHighestCrime(ArrayList<Crime> crimeArray) {
		RegionQuestionsLeague thisRegionQuestions = new RegionQuestionsLeague(crimeArray);
		HashMap<String, String> answer = thisRegionQuestions.getMostFrequentCrimeOfEachRegionReport(crimeArray,
				radioBox);

		return populateChartHashString(answer);
	}

	/**
	 * getMostFrequentCrime will search for the most frequent crime in the league as
	 * identified by the user
	 * 
	 * @param crimeArray
	 * @return String result
	 */

	private static String getMostFrequentCrime(ArrayList<Crime> crimeArray) {
		MostFrequentCrimeOfLeagueEachYear thisQuestion = new MostFrequentCrimeOfLeagueEachYear(crimeArray);
		HashMap<String, String> answer = thisQuestion.getMostFrequentCrimeOfLeagueEachYearOf2007To2011(crimeArray,
				radioBox);

		return populateChartHashString(answer);
	}

	/**
	 * getChart will create the panel and the chart based on what the user requests
	 * 
	 * @param pieChartTitle
	 * @return
	 */

	public static JPanel getChart(String pieChartTitle) {
		PieChart pieChartResult = new PieChart(pieChartTitle);

		pieChartResult.setPieChartLabels(queryPieChartLabels);
		JPanel chartPanel = pieChartResult.createChartPanel();
		clearPieChartLabels();
		return chartPanel;
	}

	/**
	 * Getters and Setters
	 * 
	 * @return
	 */

	public static String getComboBox() {
		return comboBox;
	}

	public static void setComboBox(String comboBoxOption) {
		comboBox = comboBoxOption;
	}

	public static String getRadioBox() {
		return radioBox;
	}

	public static void setRadioBox(String radioBoxOption) {
		radioBox = radioBoxOption;
	}

	/**
	 * will clear the queryPieChartLabels otherwise the chart will populate with previous labels.
	 */
	
	public static void clearPieChartLabels() {
		AppQuery.queryPieChartLabels = new HashMap<String, Double>();
	}

}
