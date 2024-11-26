
import java.sql.DriverManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.util.*;
import java.util.Date;
public class Demo {

	public static void main(String[] args) {
		//diadikasia connection me to sql server
		
		String url= "jdbc:mysql://127.0.0.1:3306/ergasia 2";
		String username="root";
		String password="";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection connection= DriverManager.getConnection(url,username,password);
			
			Statement statement=connection.createStatement();
			
			//arxiko menu othonhs
			
			System.out.println("eisai admin h xrhsths? pata 0 gia admin kai 1 gia xrhsths");
			Scanner scanner = new Scanner(System.in);
			int systemId = scanner.nextInt();
			//elegxos gia admin h xrhsth
			while(systemId !=1 || systemId!=0)
			{
			if(systemId == 0) {
				System.out.println("eisai admin");
				break;
			}
			else if (systemId == 1) {
				System.out.println("eisai xrhsths");
				break;
			}
			
			else {
				System.out.println("ebales lathos arithmo");
				System.out.println("dwse arithmo 0 h 1");
				systemId = scanner.nextInt();	
			}
			}
			//arxikopoihsh kapoion metablhtwn giati eixame thema me thn emvelia tous
			String city = null;
			String chosen_city = null;
			String place_of_service = null;
			String place_of_return = null;
			int car_id2;
			//ayto to if einai gia ton xrhsth
			if(systemId == 1) {
				System.out.println("dialekse polh gia enoikiash ");
				try {
				String sql = "SELECT city FROM shop;";
				//kanei execute to sql query
				ResultSet shop = statement.executeQuery(sql);
				//elegxos gia na mhn printarei 2 fores thn idia polh
				List<String> printedCities = new ArrayList<>();

				while (shop.next()) {
					city = shop.getString("city");
					if (!printedCities.contains(city)) {
						printedCities.add(city);
						System.out.println("City: " + city);
					}
				}
			    
				} catch (SQLException e) {
    			    e.printStackTrace();
    			}
				
				//arxikopoihsh scanner gia na epileksei o xrhsths thn polh poy epithymei
				Scanner scan = new Scanner(System.in);
				chosen_city =scan.next();
				System.out.println("h polh poy epelekses einai " +chosen_city);
				
				String sql2 = "SELECT place_of_service FROM shop WHERE city = ?";

    			try {
    			    PreparedStatement preparedStatement = connection.prepareStatement(sql2);
    			    preparedStatement.setString(1, chosen_city);

    			    ResultSet shop = preparedStatement.executeQuery();
    			    
    			    while (shop.next()) {
    			        place_of_service = shop.getString("place_of_service");
    			        System.out.println("place of service: " + place_of_service);
    			    }

    			    //kleinei to connection
    			    shop.close();
    			    preparedStatement.close();
    			} catch (SQLException e) {
    			    e.printStackTrace();
    			}
    			//eisagwgh stoixeiwn gia to rental ta opoia ta kratame se metavlhtes kai ta bazoyme sto db otan ginei k h eisagwgh tou customer 
    			String chosen_place = scan.next();
    			System.out.println("to meros eksyphrethshs poy epelekses einai " + chosen_place);
    			System.out.println("epelekse hmerominia kai thn wra gia thn paralabh se format yyyy-MM-dd-HH");
    			String inputDate = scanner.next();
    	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH");
    	        Date date = null ;
    	        Date date2 = null;
    	        try {
    	             date = dateFormat.parse(inputDate);
    	            System.out.println("Entered Date: " + date);
    	        } catch (ParseException e) {
    	            System.out.println("Invalid date format. Please enter date in yyyy-MM-dd format.");
    	        }
    	        //epilegei ean thelei na gurisei to amaksi sto idio meros pyo to phre
    	        System.out.println("tha diaferei o topos epistrofhs apo ton topo paralabhs? eisagage true gia diaforetiko topo paralabhs");
    	        boolean space_difference = scan.nextBoolean();
    	        if(space_difference==true) {
    	        	System.out.println("eisagage topo epistrofhs");
    	            place_of_return = scan.next();
    	        	System.out.println(place_of_return);
    	        }
    	        //ean thelei na to gurisei sto idio meros tote arxikopoiyme to meros epistrofhs san to meros epiloghs
    	        if(space_difference==false) {
    	        	place_of_return = chosen_place;
    	        }
    	        //ston parakatw kwdika printarei ola ta amaksia poy antistixoun sto epilegmeno shop mesw tou foreign key shop_shop_id kai ths epilegmenhs polhs ean to rent state einai 0 dhladh den einai noikiasmeno to amaksi
    	        String sqlPlace = "SELECT shop_id ,  city FROM shop WHERE city = ? AND place_of_service = ?";
    	        try (PreparedStatement preparedStatementPlace = connection.prepareStatement(sqlPlace)) {
    	            preparedStatementPlace.setString(1, chosen_city);
    	            preparedStatementPlace.setString(2, chosen_place);
    	            try (ResultSet placeResultSet = preparedStatementPlace.executeQuery()) {
    	                System.out.println("Shops in the city " + chosen_city + " with place of service " + chosen_place + ":");
    	                while (placeResultSet.next()) {
    	                	 city = placeResultSet.getString("city");
    	                     int id = placeResultSet.getInt("shop_id");
    	                     System.out.println("City: " + city + ", Shop ID: " + id);
    	                     String sqlCars = "Select * from cars Where shop_shop_id =? AND rent_state = 0";
    	         	        try ( PreparedStatement preparedStatementCars = connection.prepareStatement(sqlCars)){
    	         	        	preparedStatementCars.setInt(1, id );
    	         	        	try(ResultSet CarsResultSet = preparedStatementCars.executeQuery()) {
    	         	        		while (CarsResultSet.next()) {
    	         					  	int car_id = CarsResultSet.getInt("car_id");
    	         		                String carType = CarsResultSet.getString("type");
    	         		                int seats = CarsResultSet.getInt("seats");
    	         		                String fuelType = CarsResultSet.getString("energy");
    	         		                double rentalPrice = CarsResultSet.getDouble("price");
    	         		                int shop1_id = CarsResultSet.getInt("shop_shop_id");
    	         		                //printarontai ta amaksia
    	         		                System.out.println("Car Type: " + carType +" Car id: "+ car_id +
    	         		                                   ", Seats: " + seats +
    	         		                                   ", Fuel Type: " + fuelType +
    	         		                                   ", Rental Price: " + rentalPrice);

    	         	        	}
    	         	        		//epilegei o xrhsths me bash to car Id
    	         	        		System.out.println("parakalw epilekste to amaksi poy thelete na noikiasete me bash to ID");
	         		                int car_chosen = scan.nextInt();
	         		                System.out.println();
	         		                String sqlCars2 = "Select * from cars Where car_id =?  ";
	       	         	        try ( PreparedStatement preparedStatementCars2 = connection.prepareStatement(sqlCars2)){
	       	         	        	preparedStatementCars2.setInt(1, car_chosen );
	       	         	        	try(ResultSet CarsResultSet2 = preparedStatementCars2.executeQuery()) {
	       	         	        		while (CarsResultSet2.next()) {
	       	         					  	car_id2 = CarsResultSet2.getInt("car_id");
	       	         		                String carType2 = CarsResultSet2.getString("type");
	       	         		                int seats2 = CarsResultSet2.getInt("seats");
	       	         		                String fuelType2 = CarsResultSet2.getString("energy");
	       	         		                double rentalPrice2 = CarsResultSet2.getDouble("price");
	       	         		                int shop1_id2 = CarsResultSet2.getInt("shop_shop_id");
	       	         		                
	       	         		                // printarete to epilegmeno amaksi
	       	         		                System.out.println("Car Type: " + carType2 +" Car id: "+ car_id2 +
	       	         		                                   ", Seats: " + seats2 +
	       	         		                                   ", Fuel Type: " + fuelType2 +
	       	         		                                   ", Rental Price: " + rentalPrice2);
	       	         		                
	       	         		                System.out.println("dwse moy hmerominia epistrofhs");
	       	         		                String inputDate2 = scanner.next();

	       	         		                SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd-HH");
	       	             	        try {
	       	             	            date2 = dateFormat2.parse(inputDate2);
	       	             	            System.out.println("Entered Date: " + date2);
	       	             	            
	       	             	            
	       	             	            
	       	             	           
	       	             	        } catch (ParseException e) {
	       	             	            System.out.println("Invalid date format. Please enter date in yyyy-MM-dd format.");
	       	             	            
	       	             	           
	       	             	        }
	       	             	    //edw ginete o ypologismos twn hmerwn poy tha einai noikiasmeno to amaksi
	       	             	    long difference_In_Time	 = date2.getTime() - date.getTime();
	       	             	    long difference_In_Days= (difference_In_Time/ (1000 * 60 * 60 * 24))% 365;
	       	             	    System.out.println(difference_In_Days);
	       	             	    double final_price = difference_In_Days*rentalPrice2;
	       	             	    System.out.println(difference_In_Days*rentalPrice2);
	       	             	    
	       	             	    System.out.println("ta stoixeia ths krathshs einai : \nCity :" +chosen_city+ "\nPlace of Service :"+chosen_place+"\nPlace of Return :" +place_of_return + "\nhmerominia paralabhs :"+date+ "\nhmerominia epistrofhs :" +date2+ "\ntelikh timh :" + final_price);
	       	             	    //ginete to teleytaio confirmation kai proxwrame sthn eisagwgh twn stoixeiwn toy customer wste na mporesw na kanw kai insert sto db tis metavlhtes poy e
	       	             	    System.out.println("eisai sigouros oti thes na sunexiseis me thn krathsh? ean nai grapse true alliws false!");
	       	             	    boolean confirmation ;
	       	             	    //arxikopoihsh metablhtwn logw embeleias
	       	             	    String name=null;
	       	             	    String surname =null;
	       	             	    String email = null;
	       	             	    int phone ;
	       	             	    int driving_license_id;
	       	             	    
	       	             	    
	       	             	    //kwdikas gia na kanei update to rent state wste na fainete oti exei noikiastei
	       	             	    Scanner scan2 = new Scanner(System.in);
	       	             	    confirmation = scan2.nextBoolean();
	       	             	    if(confirmation = true) {
	       	             	    String sqlrent = "UPDATE cars SET Rent_state = 1 WHERE car_id =?";
	       	             	    try (PreparedStatement pstmt = connection.prepareStatement(sqlrent)) { 
	       	             	    	pstmt.setInt(1, car_id2);
	       	                        int rowsUpdated = pstmt.executeUpdate();
	       	                        System.out.println(rowsUpdated + " row(s) updated successfully.");
	       	                    } catch (SQLException e) {
	       	                        System.out.println("error updating Rent_state: " + e.getMessage());
	       	                    }
	       	                
	       	             	    
	       	             	   
	       	             	    //eisagwgh stoixeiwn customer
	       	             	    System.out.println("Dwse moy ta stoixeia soy");
	       	             	    System.out.println("dwse moy to name sou");
	       	             	    name=scan.next();
	       	             	    System.out.println("dwse moy to epitheto");
	       	             	    surname = scan.next();
	       	             	    System.out.println("dwse moy to email sou");
	       	             	    email = scan.next();
	       	             	    System.out.println("dwse moyt to kinito");
	       	             	    phone = scan.nextInt();
	       	             	    System.out.println("dwse moy to id tou driving license");
	       	             	    driving_license_id = scan.nextInt();
	       	             	    
	       	             	    
	       	             	    //kalw to function NextXCustomerID wste kathe fora poy eisagete neos customer to ID tou na auksanete kata 1 apo ton prwhgoymeno
	       	             	    int NextCustomerID = getNextCustomerId();
	       	             	    //edw ginete insert sto db o customer
	       	             	    String sqlCustomer = "INSERT INTO customer (customer_id, name, surname, email,driving_license_id,phone_number,shop_shop_id) VALUES (?, ?, ?, ?, ? , ?, ?)";
	       	             	    try (PreparedStatement pstmt = connection.prepareStatement(sqlCustomer)){
	       	                        //prepei na broume pws tha ginei me ta ids
	       	             	    	
	       	             	    	//prepei na balw kai to shop id tou amaksioy poy epelekse
	       	                        pstmt.setInt(1, NextCustomerID);
	       	                        pstmt.setString(2, name);
	       	                        pstmt.setString(3, surname);
	       	                        pstmt.setString(4, email);
	       	                        pstmt.setInt(5, driving_license_id);
	       	                        pstmt.setInt(6, phone);
	       	                        pstmt.setInt(7, shop1_id2);
	       	                        pstmt.executeUpdate();
	       	                        System.out.println("epitixhs eisagwgh dedomenwn!");
	       	                    } catch (SQLException e) {
	       	                        e.printStackTrace();
	       	                    }
	       	             	
	       	             	//kanw convert ta dates se sqlDates wste na einai compatible me to db
	       	             	java.sql.Date sqlDate = new java.sql.Date(date.getTime());
	       	             	java.sql.Date sqlDate2 = new java.sql.Date(date2.getTime());	
	       	             	//bazw sto db ta stoixeia gia to rental
	       	             	int nextRentalId = getNextRentalId();
	       	             	String sqlrental = "INSERT INTO rental (rental_id, date_of_pickup, date_of_return, city, place_of_service, customer_customer_id, cars_car_id, cars_shop_shop_id, shop_shop_id, customer_shop_shop_id,place_of_return) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ?)";    
	       	             	try (PreparedStatement pstmt = connection.prepareStatement(sqlrental)) {
	       	                    pstmt.setInt(1, nextRentalId);
	       	                    pstmt.setDate(2,sqlDate);
	       	                    pstmt.setDate(3, sqlDate2);
	       	                    pstmt.setString(4, chosen_city);
	       	                    pstmt.setString(5, chosen_place);
	       	                    pstmt.setInt(6, NextCustomerID);
	       	                    pstmt.setInt(7, car_id2);
	       	                    pstmt.setInt(8, shop1_id2);
	       	                    pstmt.setInt(9, shop1_id2);
	       	                    pstmt.setInt(10, shop1_id2);
	       	                    pstmt.setString(11,place_of_return);
	       	                    pstmt.executeUpdate();
	       	                    System.out.println("Data inserted successfully.");
	       	                } catch (SQLException e) {
	       	                    System.out.println("Error inserting data: " + e.getMessage());
	       	                }
	       	             	    }      
	       	         	        	}
	         		                
    	         	        }
    	         	      
    	         			
    	     			}
    	                }
    	                
    	            }
    	                }
    	            }
    	            
    	            
    	            
    	        } catch (SQLException e) {
    	            e.printStackTrace();
    	        }
    	        
    	       
    	        
			}
			//edw einai o kwdikas gia ton admin 
			if(systemId==0) {
			//ena basiko menu
			System.out.println("ADMIN MENU");
			System.out.println("SELECT : \n 1: UPDATE \n 2: INSERT \n 3: DELETE \n");
			int a = scanner.nextInt();
			System.out.println("1 : CARS \n 2: CUSTOMERS \n 3: RENTAL \n");
			//o admin dialegei ti thelei na kanei me bash to menu kai me bash ayto ginete h antistoixh diadikasia
			int b = scanner.nextInt();
			if (a==2 && b==1) {
			String sql1 = ("INSERT INTO `ergasia 2`.`cars` (`car_id`, `type`, `seats`, `energy`, `price`, `shop_shop_id`,`Rent_state`) VALUES (?, ?, ?, ?, ?, ?,?);");
			 	if(systemId==0) {
				System.out.println("kane insert amaksi");
				int car_id1= getCarId();
				System.out.println(car_id1);
				System.out.println("dwse moy to type");
				String type = scanner.next();
				System.out.println("dwse moy twn aritho twn seats");
				int seats = scanner.nextInt();
			    System.out.println("dwse moy to energy type");
				String energy = scanner.next();
			    System.out.println("dwse moy thn timh");
				int price = scanner.nextInt();
			    System.out.println("dwse moy to shop id");
				int shop_id=scanner.nextInt();
				int Rent_state = 0;
				
				try {
    			    PreparedStatement preparedStatement = connection.prepareStatement(sql1);
    			    preparedStatement.setInt(1, car_id1);
    			    preparedStatement.setString(2, type);
    			    preparedStatement.setInt(3, seats);
    			    preparedStatement.setString(4, energy);
    			    preparedStatement.setInt(5, price);
    			    preparedStatement.setInt(6, shop_id);
    			    preparedStatement.setInt(7, Rent_state);

    			    int rowsAffected = preparedStatement.executeUpdate();
                    System.out.println("Rows affected: " + rowsAffected);
    			    preparedStatement.close();
    			} catch (SQLException e) {
    			    e.printStackTrace();
    			}
			 	}
				}
			 	if(a==1 && b==1) {
				System.out.println("update amaksi");
				try {
		            //edw ginete update to amaksi
		            System.out.println("Enter the car_id of the car to update:");
		            int car_id = scanner.nextInt();

		            //o xrhsths kaleite na dialeksei opoiodhpote attribute tou car kai na to kanei update
		            System.out.println("Enter the attribute (column) you want to update (type, seats, energy, price, shop_shop_id, Rent_state):");
		            String column = scanner.next();

		            //edw bazei to kainourgio value
		            System.out.println("Enter the new value for " + column + ":");
		            String sqlUpdate = "UPDATE `ergasia 2`.`cars` SET `" + column + "` = ? WHERE `car_id` = ?;";
		            PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

		            //mesw tou switch ginete to update
		            switch (column) {
		                case "type":
		                case "energy":
		                    String newValueStr = scanner.next();
		                    preparedStatement.setString(1, newValueStr);
		                    break;
		                case "seats":
		                case "price":
		                case "shop_shop_id":
		                case "Rent_state":
		                    int newValueInt = scanner.nextInt();
		                    preparedStatement.setInt(1, newValueInt);
		                    break;
		                default:
		                    throw new IllegalArgumentException("Invalid column name: " + column);
		            }

		            preparedStatement.setInt(2, car_id);
		            int rowsAffected = preparedStatement.executeUpdate();
		            System.out.println("Rows affected: " + rowsAffected);
		            preparedStatement.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        } catch (IllegalArgumentException e) {
		            System.out.println(e.getMessage());
		        } 
			 	}
			 	//edw einai o kwdikas gia na sbhsei ena row apo to car
			 	if(a== 3 && b ==1) {
				System.out.println("sbhse ena amaksi");
				 System.out.println("Enter the car_id of the car to delete:");
			        int car_id = scanner.nextInt();

			        String sqlDelete = "DELETE FROM `ergasia 2`.`cars` WHERE `car_id` = ?;";

			        try {
			            PreparedStatement preparedStatement = connection.prepareStatement(sqlDelete);
			            preparedStatement.setInt(1, car_id);
			            int rowsAffected = preparedStatement.executeUpdate();
			            System.out.println("Rows affected: " + rowsAffected);
			            preparedStatement.close();
			        } catch (SQLException e) {
			            e.printStackTrace();
			        } 
			 	}
			 	//kwdikas gia insert ston customer
			 	if(a==2 && b==2) {
			 		String sql1 = ("INSERT INTO `ergasia 2`.`customer` (`customer_id`, `name`, `surname`, `email`, `driving_license_id`, `phone_number`, `shop_shop_id`) VALUES (?, ?, ?, ?, ?, ?, ?);");

			 		if(systemId == 0) {
			 		    System.out.println("kane insert customer");
			 		    int customer_id1 = getNextCustomerId();
			 		    System.out.println("dwse moy to onoma");
			 		    String name = scanner.next();
			 		    System.out.println("dwse moy to epitheto");
			 		    String surname = scanner.next();
			 		    System.out.println("dwse moy to email");
			 		    String email = scanner.next();
			 		    System.out.println("dwse moy to driving license id");
			 		    String driving_license_id = scanner.next();
			 		    System.out.println("dwse moy to phone number");
			 		    String phone_number = scanner.next();
			 		    System.out.println("dwse moy to shop id");
			 		    int shop_id = scanner.nextInt();
			 		    
			 		    try {
			 		        PreparedStatement preparedStatement = connection.prepareStatement(sql1);
			 		        preparedStatement.setInt(1, customer_id1);
			 		        preparedStatement.setString(2, name);
			 		        preparedStatement.setString(3, surname);
			 		        preparedStatement.setString(4, email);
			 		        preparedStatement.setString(5, driving_license_id);
			 		        preparedStatement.setString(6, phone_number);
			 		        preparedStatement.setInt(7, shop_id);
			 		        int rowsAffected = preparedStatement.executeUpdate();
			 		        System.out.println("Rows affected: " + rowsAffected);
			 		        preparedStatement.close();
			 		    } catch (SQLException e) {
			 		        e.printStackTrace();
			 		    }
			 		}
			 	}
			 	//kwdikas gia update ston customer , leitourgei me paromoio tropo me to update sto car
			 	if(a==1 && b==2) {
			 		try {
			 		    System.out.println("Enter the customer_id of the customer to update:");
			 		    int customer_id = scanner.nextInt();
			 		    System.out.println("Enter the attribute (column) you want to update (name, surname, email, driving_license_id, phone_number, shop_shop_id):");
			 		    String column = scanner.next();
			 		    System.out.println("Enter the new value for " + column + ":");
			 		    String sqlUpdate = "UPDATE `ergasia 2`.`customer` SET `" + column + "` = ? WHERE `customer_id` = ?;";
			 		    PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);
			 		    switch (column) {
			 		        case "name":
			 		        case "surname":
			 		        case "email":
			 		        case "driving_license_id":
			 		        case "phone_number":
			 		            String newValueStr = scanner.next();
			 		            preparedStatement.setString(1, newValueStr);
			 		            break;
			 		        case "shop_shop_id":
			 		            int newValueInt = scanner.nextInt();
			 		            preparedStatement.setInt(1, newValueInt);
			 		            break;
			 		        default:
			 		            throw new IllegalArgumentException("Invalid column name: " + column);
			 		    }

			 		    preparedStatement.setInt(2, customer_id);
			 		    int rowsAffected = preparedStatement.executeUpdate();
			 		    System.out.println("Rows affected: " + rowsAffected);
			 		    preparedStatement.close();
			 		} catch (SQLException e) {
			 		    e.printStackTrace();
			 		} catch (IllegalArgumentException e) {
			 		    System.out.println(e.getMessage());
			 		}
			 	}
			 	//kwdikas gia na sbhsei ena customer idios me to car
			 	if(a==3 && b==2) {
			 		System.out.println("sbhse ena customer");
			 		System.out.println("Enter the customer_id of the customer to delete:");
			 		int customer_id = scanner.nextInt();
			 		String sqlDelete = "DELETE FROM `ergasia 2`.`customer` WHERE `customer_id` = ?;";
			 		try {
			 		    PreparedStatement preparedStatement = connection.prepareStatement(sqlDelete);
			 		    preparedStatement.setInt(1, customer_id);
			 		    int rowsAffected = preparedStatement.executeUpdate();
			 		    System.out.println("Rows affected: " + rowsAffected);
			 		    preparedStatement.close();
			 		} catch (SQLException e) {
			 		    e.printStackTrace();
			 		} 
			 	}
			    
			 	//kwdikas gia insert sto rental idios me parapanw
			 	if(a == 2 && b == 3) {
			 	    String sql1 = ("INSERT INTO `ergasia 2`.`rental` (`rental_id`, `date_of_pickup`, `date_of_return`, `city`, `place_of_service`, `customer_customer_id`, `cars_car_id`, `cars_shop_shop_id`, `shop_shop_id`, `customer_shop_shop_id , place_of_return`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?);");

			 	    if(systemId == 0) {
			 	        System.out.println("kane insert rental");
			 	        int rental_id1 = getNextRentalId();
			 	        System.out.println(rental_id1);
			 	        System.out.println("dwse moy to date of pickup (yyyy-mm-dd)");
			 	        String date_of_pickup = scanner.next();
			 	        System.out.println("dwse moy to date of return (yyyy-mm-dd)");
			 	        String date_of_return = scanner.next();
			 	        System.out.println("dwse moy thn poli");
			 	        String city2 = scanner.next();
			 	        System.out.println("dwse moy to place of service");
			 	        String place_of_service2 = scanner.next();
			 	        System.out.println("dwse moy to customer id");
			 	        int customer_customer_id = scanner.nextInt();
			 	        System.out.println("dwse moy to car id");
			 	        int cars_car_id = scanner.nextInt();
			 	        System.out.println("dwse moy to cars shop id");
			 	        int cars_shop_shop_id = scanner.nextInt();
			 	        System.out.println("dwse moy to shop id");
			 	        int shop_shop_id = scanner.nextInt();
			 	        System.out.println("dwse moy to customer shop id");
			 	        int customer_shop_shop_id = scanner.nextInt();
			 	        
			 	        try {
			 	            PreparedStatement preparedStatement = connection.prepareStatement(sql1);
			 	            preparedStatement.setInt(1, rental_id1);
			 	            preparedStatement.setString(2, date_of_pickup);
			 	            preparedStatement.setString(3, date_of_return);
			 	            preparedStatement.setString(4, city2);
			 	            preparedStatement.setString(5, place_of_service2);
			 	            preparedStatement.setInt(6, customer_customer_id);
			 	            preparedStatement.setInt(7, cars_car_id);
			 	            preparedStatement.setInt(8, cars_shop_shop_id);
			 	            preparedStatement.setInt(9, shop_shop_id);
			 	            preparedStatement.setInt(10, customer_shop_shop_id);
			 	            preparedStatement.setString(11, place_of_return);
			 	            int rowsAffected = preparedStatement.executeUpdate();
			 	            System.out.println("Rows affected: " + rowsAffected);
			 	            preparedStatement.close();
			 	        } catch (SQLException e) {
			 	            e.printStackTrace();
			 	        }
			 	    }
			 	}
			 	//kwdikas gia update sto rental idios me parapanw
			 	if(a == 1 && b == 3) {
			 	    try {
			 	        System.out.println("Enter the rental_id of the rental to update:");
			 	        int rental_id = scanner.nextInt();
			 	        System.out.println("Enter the attribute (column) you want to update (date_of_pickup, date_of_return, city, place_of_service, customer_customer_id, cars_car_id, cars_shop_shop_id, shop_shop_id, customer_shop_shop_id,place_of_return):");
			 	        String column = scanner.next();
			 	        System.out.println("Enter the new value for " + column + ":");

			 	        String sqlUpdate = "UPDATE `ergasia 2`.`rental` SET `" + column + "` = ? WHERE `rental_id` = ?;";
			 	        PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);
			 	        switch (column) {
			 	            case "date_of_pickup":
			 	            case "date_of_return":
			 	            case "city":
			 	            case "place_of_return":
			 	            case "place_of_service":
			 	                String newValueStr = scanner.next();
			 	                preparedStatement.setString(1, newValueStr);
			 	                break;
			 	            case "customer_customer_id":
			 	            case "cars_car_id":
			 	            case "cars_shop_shop_id":
			 	            case "shop_shop_id":
			 	            case "customer_shop_shop_id":
			 	                int newValueInt = scanner.nextInt();
			 	                preparedStatement.setInt(1, newValueInt);
			 	                break;
			 	            default:
			 	                throw new IllegalArgumentException("Invalid column name: " + column);
			 	        }

			 	        preparedStatement.setInt(2, rental_id);
			 	        int rowsAffected = preparedStatement.executeUpdate();
			 	        System.out.println("Rows affected: " + rowsAffected);
			 	        preparedStatement.close();
			 	    } catch (SQLException e) {
			 	        e.printStackTrace();
			 	    } catch (IllegalArgumentException e) {
			 	        System.out.println(e.getMessage());
			 	    }
			 	}
			 	//edw sbhnei ena rental idio me to prwhgoymena
			 	if(a == 3 && b == 3) {
			 	    System.out.println("sbhse ena rental");
			 	    System.out.println("Enter the rental_id of the rental to delete:");
			 	    int rental_id = scanner.nextInt();

			 	    String sqlDelete = "DELETE FROM `ergasia 2`.`rental` WHERE `rental_id` = ?;";

			 	    try {
			 	        PreparedStatement preparedStatement = connection.prepareStatement(sqlDelete);
			 	        preparedStatement.setInt(1, rental_id);
			 	        int rowsAffected = preparedStatement.executeUpdate();
			 	        System.out.println("Rows affected: " + rowsAffected);
			 	        preparedStatement.close();
			 	    } catch (SQLException e) {
			 	        e.printStackTrace();
			 	    }
			 	}
			}
			connection.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
		
		
		
	}
	//function gia na brei to epomeno akeraio sto Rental_Id etsi wste opote dhmioyrgeite ena kainourgio rental to id tou na tou dinete dunamika me bash to prwhgoymeno
	public static int getNextRentalId() {
        String sql = "SELECT MAX(rental_id) AS max_id FROM rental";
        String url= "jdbc:mysql://127.0.0.1:3306/ergasia 2";
		String username="root";
		String password="";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                int maxId = rs.getInt("max_id");
                return maxId + 1;
            } else {
                //ama de brethei kapoio id tou dinw thn timh 1 kathws tha einai to prwto
                return 1;
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving max rental_id: " + e.getMessage());
            return -1; 
        }
    }
	//leitourgei me ton idio tropo me to parapanw
	public static int getNextCustomerId() {
        String sql = "SELECT MAX(customer_id) AS max_id FROM customer";
        String url= "jdbc:mysql://127.0.0.1:3306/ergasia 2";
		String username="root";
		String password="";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                int maxId = rs.getInt("max_id");
                return maxId + 1;
            } else {
              
                return 1;
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving max rental_id: " + e.getMessage());
            return -1; 
        }
    }
	//to idio me ta alla functions
	public static int getCarId() {
        String sql = "SELECT MAX(car_id) AS max_id FROM cars";
        String url= "jdbc:mysql://127.0.0.1:3306/ergasia 2";
		String username="root";
		String password="";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                int maxId = rs.getInt("max_id");
                return maxId + 1;
            } else {
                return 1;
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving max car_id: " + e.getMessage());
            return -1;
        }
    }
}
