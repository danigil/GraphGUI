import Pages.MainGraphPage;
import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import main.Graph;
import main.Algorithm;

/**
 * This class is the main.main class for Ex2 - your implementation will be tested using this class.
 */
public class Ex2 {
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraph getGrapg(String json_file) {
        DirectedWeightedGraph ans;

        Algorithm algorithm = new Algorithm();
        algorithm.load(json_file);

        ans=algorithm.getGraph();

        return ans;
    }
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        DirectedWeightedGraphAlgorithms ans = null;

        Algorithm algorithm = new Algorithm();
        algorithm.load(json_file);

        ans=algorithm;

        return ans;
    }
    /**
     * This static function will run your GUI using the json fime.
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     *
     */
    public static void runGUI(String json_file) {
        DirectedWeightedGraphAlgorithms alg = getGrapgAlgo(json_file);
        MainGraphPage mainGraphPage = new MainGraphPage();
        //mainGraphPage.createAndShowGUI();
        mainGraphPage.changeGraph((Graph) alg.getGraph());
    }
}