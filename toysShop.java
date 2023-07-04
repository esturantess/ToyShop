import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class toysShop {
    public static void main(String[] args) {
        Toys toyOne = new Toys("Hamster", 2);
        Toys toyTwo = new Toys("Lion", 3);
        Toys toyThree = new Toys("Hare", 2);
        Toys toyFour = new Toys("Doll", 3);

        List<Toys> toys = new ArrayList<>();
        toys.add(toyOne);
        toys.add(toyTwo);
        toys.add(toyThree);
        toys.add(toyFour);

        System.out.println("�������, ��������� ��� ���������: ");
        showToys(toys);
        menuToy(toys);
    }

    static void showToys(List<Toys> toys) {
        for (Toys thing : toys) {
            System.out.println(thing);
        }
    }

    static void menuToy(List<Toys> toys) {
        Scanner inputToy = new Scanner(System.in);
        Boolean menu = true;
        String choice;

        String name = "";
        int weight = 0;

        while (menu) {

            System.out.println("\n�������� ��������:");
            System.out.println("1. �������� ����� ������� ��� ���������");
            System.out.println("2. ������� ������ ���������� �������");
            System.out.println("3. ��������� �������");
            System.out.println("��� ������ - ����� ������ ������");

            choice = inputToy.nextLine();

            switch (choice) {

                case "1":
                    System.out.print("������� ������������ ����� �������: ");
                    name = inputToy.nextLine();
                    System.out.println("������� '���' (����������� ���������) �������: ");
                    weight = inputToy.nextInt();
                    inputToy.nextLine();
                    Toys newToy = new Toys(name, weight);
                    toys.add(newToy);

                    System.out.println("\n������� ������ �������: ");
                    showToys(toys);
                    break;

                case "2":
                    if (toys.size() == 0) System.out.println("\n������� ��� :(\n");
                    else {
                        System.out.println("\n������ �������: ");
                        showToys(toys);
                    }
                    break;

                case "3":
                    Random random = new Random();

                    List<Toys> copyToys = new ArrayList<>(toys);
                    Toys temp;
                    Toys tempNext;
                    for (int i = 0; i < copyToys.size(); i++) {
                        for (int j = 1; j < copyToys.size(); j++) {
                            if (copyToys.get(j).getWeights(copyToys) > copyToys.get(j - 1).getWeights(copyToys)) {
                                temp = copyToys.get(j);
                                tempNext = copyToys.get(j - 1);
                                copyToys.set(j, tempNext);
                                copyToys.set(j - 1, temp);
                            }
                        }
                    }

                    List<Toys> toysCopy = new ArrayList<>();
                    for (int i = 0; i < copyToys.size() / 3 * 2; i++) {
                        toysCopy.add(copyToys.get(i));
                    }

                    Toys reward;
                    if (toysCopy.size() == 1) reward = toysCopy.get(0);
                    if (toysCopy.size() == 0) {
                        System.out.println("\n������� ����������� :(\n");
                        toys.clear();
                    } else {
                        reward = toysCopy.get(random.nextInt(toysCopy.size()));
                        System.out.println("\n�������� �������:");
                        System.out.println(reward);

                        try (FileWriter fw = new FileWriter("rewards.txt", true)) {
                            fw.write(reward.toString());
                            fw.write("\n");
                            fw.close();
                        } catch (Exception ex) {
                            System.out.println("������, ���������� �����");
                        }

                        for (Toys thing : toys) {
                            if (thing.containsID(reward.getID())) {
                                int w = thing.getWeightToy();
                                w--;
                                thing.setWeight(w);
                                if (thing.getWeightToy() == 0) toys.remove(thing);
                                break;
                            }
                        }

                        System.out.println("\n�������� �������: ");
                        showToys(toys);
                    }
                    break;
                default:
                    menu = false;
                    inputToy.close();
                    break;
            }
        }
    }
}