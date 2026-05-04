import controller.SimuladorController;

public class Main {

    public static void main(String[] args) {
        System.out.println("A iniciar o Simulador...");

        //O Main apenas instancia o Controller.
        //O Controller vai tratar de tudo o resto
        SimuladorController controller = new SimuladorController();

    }
}