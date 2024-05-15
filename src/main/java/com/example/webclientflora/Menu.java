package com.example.webclientflora;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Scanner;

public class Menu {
    public static Scanner input = new Scanner(System.in);

    static WebClient client = WebClient.create("http://localhost:8080");
    public static void menu() {
        System.out.println(
                "0. Exit application.\n" +
                "1. Print all Flora.\n" +
                "2. Print all common names.\n" +
                "3. Search by Flora ID.\n" +
                "4. Add Flora to database.\n" +
                "5. Edit Flora in database.\n" +
                "6. Delete Flora from database.\n" +
                "\nRequest by typing the number of your choice >> ");

        String userInput = null;
        do {
            try {

                userInput = input.nextLine();
                switch (userInput) {
                    case "0" -> System.out.println("Exit application.");
                    case "1" -> printAllFlora();
                    case "2" -> printAllCommonNames();
                    case "3" -> searchById();
                    case "4" -> {
                        System.out.print("Enter new common name: ");
                        String name = input.nextLine();
                        System.out.print("Enter new scientific name: ");
                        String sciName = input.nextLine();
                        FloraService.createFlora(name, sciName);
                    }
                    case "5" -> {
                        System.out.println("Enter common name of Flora to update: ");
                        String name = input.nextLine();
                        System.out.print("Enter new common name: ");
                        String newName = input.nextLine();
                        System.out.print("Enter new scientific name: ");
                        String newSciName = input.nextLine();
                        FloraService.updateFlora(name, newName, newSciName);
                    }
                    case "6" -> {
                        System.out.println("Enter common name of flora to be deleted: ");
                        String name = input.nextLine();
                        FloraService.deleteFlora(name);
                    }
                    default -> System.out.println("Not readable, please try again.");
                }
            } catch (Exception e) {
                System.out.println("Your request was not readable, please try again.");
                input.nextLine();
            }
        } while (!userInput.equals("0"));
        System.out.println("Exits application.");

    }

    private static void printAllFlora() {
        Flux<Flora> flux1 = client
                .get()
                .uri("/rs/flora")
                .retrieve()
                .bodyToFlux(Flora.class);

        flux1.subscribe(flora ->{
            String formattedFlora = String.format("Flora ID: %d\nCommon name: %s\nScientfic name: %s\n***",
                    flora.getId(), flora.getName(), flora.getSciName());
            System.out.println(formattedFlora);
        });

    }

    private static void printAllCommonNames() {
        Flux<Flora> flux1 = client
                .get()
                .uri("/rs/flora")
                .retrieve()
                .bodyToFlux(Flora.class);


        flux1.subscribe(flora -> {
            System.out.println(flora.getName());
        });
    }

    private static void searchById() {
        System.out.println("Enter the ID you are searching for >> ");
        int idInput = input.nextInt();
        input.nextLine();
        Flux<Flora> fluxList = client
                .get()
                .uri("/rs/flora")
                .retrieve()
                .bodyToFlux(Flora.class);

        fluxList
                .filter(flora -> flora.getId() == idInput)
                .subscribe(
                        flora -> {
                            String formattedFlora = String.format("Flora ID: %d\nCommon name: %s\nScientific name: %s\n",
                                    flora.getId(), flora.getName(), flora.getSciName());
                            System.out.println(formattedFlora);
                        },
                        error -> System.out.println("No ID found. Please try again."),
                        () -> System.out.println("Search complete.")
                );

    }


}
