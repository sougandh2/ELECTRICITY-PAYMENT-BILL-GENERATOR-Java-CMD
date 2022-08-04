import java.util.*;

class Consumer {
    private String name;
    private String userName;
    private String passCode;
    private String consumerNumber;
   
    public Consumer(String name, String userName, String passCode, String consumerNumber) {
        this.name = name;
        this.userName = userName;
        this.passCode = passCode;
        this.consumerNumber = consumerNumber;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassCode() {
        return passCode;
    }
    public String getName() {
        return name;
    }

    public String getConsumerNumber() {
        return consumerNumber;
    }
}

class Consumers {
    public static ArrayList<Consumer> consumerList = new ArrayList<>();
    public static Scanner s = new Scanner(System.in);
    public static HashMap<Consumer,Double> billPayments = new HashMap<>();

    public void addConsumer() {
        System.out.println("PROVIDE NAME ");
        String name = s.next();
        System.out.println("PROVIDE CONSUMER NUMBER ");
        String consum = s.next();
        System.out.println("PROVIDE USERNAME ");
        String user = s.next();
        System.out.println("PROVIDE PASSCODE ");
        String pass = s.next();

        for(Consumer c : consumerList) {
            if(user.compareTo(c.getUserName())==0) {
                System.out.println("SORRY THIS USERNAME ALREADY EXITS!");
                return;
            }
        }

        Consumer cus = new Consumer(name, user, pass, consum);
        this.consumerList.add(cus);
    }

    public Boolean login(String username, String passcode) {
        for(Consumer c : this.consumerList) {
            if(c.getUserName().compareTo(username)==0 && c.getPassCode().compareTo(passcode)==0) {
                return true;
            }
        }
        return false;
    }

    private Double billCalculation(Double units) {
        
        Double billpay = 0d;
        if (units < 100) {
            billpay = units * 1.20;
        } else if (units < 300) {
            billpay = 100 * 1.20 + (units - 100) * 2;
        } else if (units > 300) {
            billpay = 100 * 1.20 + 200 * 2 + (units - 300) * 3;
        }
        return billpay;
    }

    public void consumerRemove() {
        int id = 1;
        System.out.println("SELECT THE ID OF CONSUMER TO REMOVE");
        for(Consumer con : this.consumerList) {
            System.out.println("CONSUMER ID : "+id++);
            System.out.println("CONSUMER NAME : "+con.getName());
            System.out.println("CONSUMER NUMBER : "+con.getConsumerNumber());
        }
        System.out.println("ENTER ID TO REMOVE (0 to Back): ");
        int r = s.nextInt();
        if(r==0) {
            return;
        }
        this.consumerList.remove(--r);
        
        System.out.println("REMOVAL SUCCESSFULL! ");

    }

    public void billProcess() {
        System.out.println("PLEASE PROVIDE AMOUNT OF POWER USED BY EACH");
        Double pwr;
        Double bill;
        for(Consumer con : this.consumerList) {
            System.out.println("CONSUMER NAME : "+con.getName());
            System.out.println("CONSUMER NUMBER : "+con.getConsumerNumber());
            System.out.println("ENTER UNITS CONSUMED : ");
            pwr = s.nextDouble();
            bill = this.billCalculation(pwr);
            billPayments.put(con,bill);
        }
    }

    public void billPayment(String username) {
        int ans;
        for(Consumer con : billPayments.keySet()) {
            if(username.compareTo(con.getUserName())==0) {
                System.out.println("YOUR BILL AMOUNT IS : "+billPayments.get(con));
            }
        }
        try {
            System.out.println("1.PAY NOW");
            System.out.println("2.BACK");
            System.out.println("CHOOSE OPTION : ");
            ans = s.nextInt();
            if(ans == 1) {
                for(Consumer con : billPayments.keySet()) {
                    if(username.compareTo(con.getUserName())==0) {
                        billPayments.put(con,0d);
                    }
                }
                System.out.println("PAYMENT COMPLETE!");
            }
        } catch(Exception e ) { System.out.println("PLEASE PROVIDE VALID INPUT NEXT TIME");}
        
    }
}

public class Vand {
    public static Scanner s = new Scanner(System.in);
    static Consumers c = new Consumers();

    public static void mainContent() {
        int opt = 0;
        while(opt!=4) {
            try {
                System.out.println("\n\n___WELCOME ELECTRICITY BILLING SYSTEM____");
                System.out.println("1.KSEB LOGIN");
                System.out.println("2.CONSUMER LOGIN");
                System.out.println("3.CONSUMER REGISTER");
                System.out.println("4.EXIT");
                System.out.print("PLEASE PROVIDE AN OPTION : ");
                opt = s.nextInt();
                
                if(opt==1) {
                    System.out.println("PROVIDE ADMIN USERCODE : ");
                    s.nextLine();
                    String userName = s.nextLine();
                    System.out.println("PROVIDE ADMIN PASSCODE : ");
                    String passCode = s.nextLine();
                    if(userName.compareTo("kseb")==0 && passCode.compareTo("kseb")==0) {
                        int spt=0;
                        while(spt!=4) {
                            System.out.println("\n__WELCOME ADMIN!__\n");
                            
                            System.out.println("\t1.ADD CONSUMER ");
                            System.out.println("\t2.REMOVE CONSUMER ");
                            System.out.println("\t3.BILL GENERATION");
                            System.out.println("\t4.EXIT: ");
                            System.out.print("\tCHOOSE ANY : ");
                            spt = s.nextInt();
                            if(spt==1) {
                                c.addConsumer();
                            }
                            if(spt==2) {
                                c.consumerRemove();
                            }
                            if(spt==3) {
                                c.billProcess();
                            }
                        }
                    } else {
                        System.out.print("\tAUTHORIZATION FAILED ");
                    }
                }
                if(opt == 2) {
                    System.out.println("PROVIDE YOUR USERID : ");
                    s.nextLine();
                    String uName = s.nextLine();
                    System.out.println("PROVIDE YOUR PASSCODE : ");
                    String passCode = s.nextLine();

                    if(c.login(uName,passCode)) {
                        int spt = 0; 
                        while(spt!=2) {
                            System.out.println("\n__LOGIN SUCCESS__\n");
                            System.out.println("\t1.PAY BILL ");
                            System.out.println("\t2.EXIT: ");
                            System.out.print("\tCHOOSE ANY : ");
                            spt = s.nextInt();
                            if(spt==1) {
                                c.billPayment(uName);
                            }
                        }
                    } else {
                        System.out.println("CREDENTIALS NOT RECOGNISED BY OUR INTERNAL SYSTEM");
                    }
                }

                if(opt == 3) {
                    c.addConsumer();
                }

            } catch (Exception e) {
                System.out.println("PLEASE PROVIDE VALID INPUT!");
                continue;
            }
        }
        System.out.print("THANK YOU FOR USING OUR SERVICE");
    }

    public static void main(String[] args) {
        mainContent();
    }
}
