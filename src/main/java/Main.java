import Environment.Environment;
import ViewController.ViewController;

/**
 *
 * @author shams
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
		int size=20;
		Environment env = new Environment(size);

		ViewController viewController = new ViewController(env, size);

		env.getModel().addObserver(viewController);

		viewController.setVisible(true);//On la rend visible
		env.start(50);
    }

}
