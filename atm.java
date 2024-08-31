package ATM;

import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

class BankAccount {
    private String name;
    private String pin;
    private double balance = 0;
    private String transactionHistory = "";
    private Scanner sc = new Scanner(System.in);
    private int loginAttempts = 0;

    public void register() {
        System.out.print("Enter your name: ");
        this.name = sc.nextLine();
        System.out.print("Set a 4-digit PIN: ");
        this.pin = sc.nextLine();
        while (pin.length() != 4) {
            System.err.println("PIN must be 4 digits.");
            System.out.print("Set a 4-digit PIN: ");
            this.pin = sc.nextLine();
        }
        System.out.println("Registration successful. Please login to continue.");
    }

    public boolean login() {
        while (loginAttempts < 3) {
            System.out.print("Enter your name: ");
            String entName = sc.nextLine();
            System.out.print("Enter your PIN: ");
            String entPin = sc.nextLine();

            if (entName.equals(this.name) && entPin.equals(this.pin)) {
                return true;
            } else {
                loginAttempts++;
                System.err.println("Login failed. Incorrect name or PIN.");
                if (loginAttempts >= 3) {
                    System.err.println("Too many failed attempts. Exiting.");
                    System.exit(0);
                }
            }
        }
        return false;
    }

    public void withdraw() {
        System.out.print("Enter amount to withdraw: ");
        double amount = sc.nextDouble();
        if (amount > balance) {
            System.err.println("Insufficient balance.");
        } else {
            System.out.print("Confirm withdrawal of $" + amount + " (Y/N): ");
            char confirm = sc.next().toUpperCase().charAt(0);
            if (confirm == 'Y') {
                balance -= amount;
                transactionHistory += "Withdrawn: $" + amount + " on " + getTimeStamp() + "\n";
                System.out.println("Withdrawal successful.");
            } else {
                System.out.println("Withdrawal cancelled.");
            }
        }
        showSummary();
    }

    public void deposit() {
        System.out.print("Enter amount to deposit: ");
        double amount = sc.nextDouble();
        System.out.print("Confirm deposit of $" + amount + " (Y/N): ");
        char confirm = sc.next().toUpperCase().charAt(0);
        if (confirm == 'Y') {
            balance += amount;
            transactionHistory += "Deposited: $" + amount + " on " + getTimeStamp() + "\n";
            System.out.println("Deposit successful.");
        } else {
            System.out.println("Deposit cancelled.");
        }
        showSummary();
    }

    public void transfer() {
        System.out.print("Enter recipient's name: ");
        sc.nextLine(); 
        String recipient = sc.nextLine();
        System.out.print("Enter amount to transfer: ");
        double amount = sc.nextDouble();
        if (amount > balance) {
            System.err.println("Insufficient balance.");
        } else {
            System.out.print("Confirm transfer of $" + amount + " to " + recipient + " (Y/N): ");
            char confirm = sc.next().toUpperCase().charAt(0);
            if (confirm == 'Y') {
                balance -= amount;
                transactionHistory += "Transferred: $" + amount + " to " + recipient + " on " + getTimeStamp() + "\n";
                System.out.println("Transfer successful.");
            } else {
                System.out.println("Transfer cancelled.");
            }
        }
        showSummary();
    }

    public void checkBalance() {
        System.out.println("Current balance: $" + balance);
    }

    public void transHistory() {
        System.out.println("Transaction History:\n" + transactionHistory);
    }

    public void logout() {
        System.out.println("You have successfully logged out.");
        System.exit(0);
    }

    private void showSummary() {
        System.out.println("Current balance: $" + balance);
    }

    private String getTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

	public String getName() {
		return this.name;
	}
}

public class atm {

    public static int takeIntegerInput(int limit) {
        int input = 0;
        boolean flag = false;
        Scanner sc = new Scanner(System.in);

        while (!flag) {
            try {
                input = sc.nextInt();
                if (input >= 1 && input <= limit) {
                    flag = true;
                } else {
                    System.err.println("Choose a number between 1 and " + limit);
                }
            } catch (Exception e) {
                System.err.println("Enter only an integer value.");
                sc.next();
            }
        }
        return input;
    }

    public static void main(String[] args) {
        System.out.println("\n-------- Welcome To SBI ATM System --------\n");
        System.out.println("1. Register\n2. Exit");
        System.out.print("Enter your choice: ");
        int choice = takeIntegerInput(2);

        if (choice == 1) {
            BankAccount b = new BankAccount();
            b.register();

            while (true) {
                System.out.println("\n1. Login\n2. Exit");
                System.out.print("Enter your choice: ");
                int ch = takeIntegerInput(2);

                if (ch == 1) {
                    if (b.login()) {
                        System.out.println("\n\n**Welcome Back " + b.getName() + "**\n");
                        boolean isFinished = false;

                        while (!isFinished) {
                            System.out.println("\n1. Withdraw\n2. Deposit\n3. Transfer\n4. Check Balance\n5. Transaction History\n6. Logout");
                            System.out.print("Enter your choice: ");
                            int c = takeIntegerInput(6);

                            isFinished = switch (c) {
                                case 1 -> {
                                    b.withdraw();
                                    yield false;
                                }
                                case 2 -> {
                                    b.deposit();
                                    yield false;
                                }
                                case 3 -> {
                                    b.transfer();
                                    yield false;
                                }
                                case 4 -> {
                                    b.checkBalance();
                                    yield false;
                                }
                                case 5 -> {
                                    b.transHistory();
                                    yield false;
                                }
                                case 6 -> {
                                    b.logout();
                                    yield true;
                                }
                                default -> {
                                    System.err.println("Invalid choice. Please try again.");
                                    yield false;
                                }
                            };
                        }
                    }
                } else {
                    System.exit(0);
                }
            }
        } else {
            System.exit(0);
        }
    }
}
