
package org.example.menu;
import org.example.model.AdoptionStatus;
import org.example.model.Animal;
import org.example.model.AnimalId;
import org.example.model.Dog;
import org.example.model.Cat;
import org.example.model.Bird;
import org.example.shelter.Shelter;

import java.util.Scanner;

public class ConsoleMenu {

    private final Shelter<Animal> shelter;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleMenu(Shelter<Animal> shelter) {
        this.shelter = shelter;
    }

    public void start() {
        boolean running = true;

        while (running) {
            printMenu();

            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {

                case "1" -> addAnimal();

                case "2" -> {
                    for (Animal animal : shelter.getAllAnimals()) {
                        System.out.println(animal);
                    }
                }
                case "3" -> {
                    System.out.print("Enter species (Dog/Cat/Bird): ");
                    String species = scanner.nextLine();

                    for (Animal animal : shelter.findBySpecies(species)) {
                        System.out.println(animal);
                    }
                }

                case "4" -> {
                    for (Animal animal : shelter.findAvailableAnimals()) {
                        System.out.println(animal);
                    }
                }

                case "5" -> {
                    System.out.print("Enter animal ID: ");
                    String id = scanner.nextLine();

                    boolean found = false;

                    for (Animal animal : shelter.getAllAnimals()) {
                        if (animal.getId().toString().equals(id)) {
                            found = true;

                            if (animal.getAdoptionStatus() == AdoptionStatus.ADOPTED) {
                                System.out.println("Animal already adopted.");
                            } else {
                                shelter.markAsAdopted(id);
                                System.out.println("Animal adopted successfully!");
                            }

                            break;
                        }
                    }

                    if (!found) {
                        System.out.println("Animal not found.");
                    }
                }

                case "6" -> {
                    for (Animal animal : shelter.sortByAge()) {
                        System.out.println(animal);
                    }
                }

                case "0" -> {
                    System.out.println("Goodbye!");
                    running = false;
                }

                default -> System.out.println("Invalid option.");
            }
        }
    }


    private void addAnimal() {
        System.out.print("Enter species (Dog/Cat/Bird): ");
        String species = scanner.nextLine().trim();

        if (!species.equalsIgnoreCase("Dog") &&
                !species.equalsIgnoreCase("Cat") &&
                !species.equalsIgnoreCase("Bird")) {
            System.out.println("Invalid species!");
            return;
        }

        System.out.print("Enter name: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Name cannot be empty!");
            return;
        }

        System.out.print("Enter age: ");
        int age;

        try {
            age = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Age must be a number!");
            return;
        }

        if (age < 0) {
            System.out.println("Age cannot be negative!");
            return;
        }

        Animal animal;

        if (species.equalsIgnoreCase("Dog")) {
            animal = new Dog(new AnimalId(), name, age);
        } else if (species.equalsIgnoreCase("Cat")) {
            animal = new Cat(new AnimalId(), name, age);
        } else {
            animal = new Bird(new AnimalId(), name, age);
        }

        shelter.addAnimal(animal);
        System.out.println("Animal added successfully!");
    }


    private void printMenu() {
        System.out.println("""
                1. Add animal
                2. List all animals
                3. Find animals by species
                4. List available animals
                5. Mark animal as adopted
                6. Sort animals by age
                0. Exit
                """);
    }
}
