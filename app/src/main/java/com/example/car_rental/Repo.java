package com.example.car_rental;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;

public class Repo {

    private static final String SAMPLE_TABLE_NAME = "vRentalInfo";
    private final Context context;
    final String DB_NAME = "carrental.db";
    final int DB_Version = 14;
    private  ConnectingDatabase connectingDatabase;
    public Repo(Context context) {
        this.context = context;
        connectingDatabase  = getDatabase();
    }

    public CustomerModel insertCustomer(String name, String phone) {

        final SQLiteDatabase db = writeDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put("Name", name);
        contentValue.put("Phone", phone);
        try {
            long id = db.insert("Customer", null, contentValue);
            return new CustomerModel(String.valueOf(id), name, phone);
        }catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }


    public ArrayList<VehicleModel> readAllVehicles() {
        final SQLiteDatabase db = readDatabase();
//        dataBaseChecker(db);
        ArrayList<VehicleModel> VehicleModel = new ArrayList();
        Cursor cursor = db.rawQuery("select * from Vehicle ", null);

        if (cursor.moveToFirst()) {

            do {
                String vid =  cursor.getString(0);
                String vdes = cursor.getString(1);
                String vtype = cursor.getString(3);
                String vcategory = cursor.getString(4);
                VehicleModel.add(new VehicleModel(vid,vdes,vtype,vcategory));

                System.out.println("Vehicle id " + cursor.getString(0));
                System.out.println("Vehicle description is " + cursor.getString(1));
                System.out.println("Vehicle type is " + cursor.getString(3));
                System.out.println("Vehicle category is " + cursor.getString(4));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return VehicleModel;
    }

    public VehicleModel insertVehicle(String vid, String vdes, String vtype, String vcategory) {
        final SQLiteDatabase db = getDatabase().getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put("VehicleID", vid);
        contentValue.put("Description", vdes);
        contentValue.put("Type", vtype);
        contentValue.put("Category", vcategory);
        try {
            long id = db.insert("Vehicle", null, contentValue);
            return new VehicleModel(String.valueOf(vid), vdes, vtype, vcategory );
        }catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    public ConnectingDatabase getDatabase() {
        return new ConnectingDatabase(context, DB_NAME);
    }

    public SQLiteDatabase writeDatabase() {
        return connectingDatabase.getWritableDatabase();
    }

    public SQLiteDatabase readDatabase() {
        return  connectingDatabase.openDataBase();
    }

//    public void dataBaseChecker(SQLiteDatabase database) {
//        Cursor x = database.rawQuery("PRAGMA user_version", new String[]{});
//        x.moveToFirst();
//        int version = x.getInt(0);
//        if (version < DB_Version) {
//            context.deleteDatabase(DB_NAME);
//        }
//        x.close();
//    }


    public ArrayList<CustomerModel> readAllCustomers() {
        final SQLiteDatabase db = readDatabase();
//        dataBaseChecker(db);
        ArrayList<CustomerModel> customerModel = new ArrayList();
        Cursor cursor = db.rawQuery("select * from Customer ", null);

        if (cursor.moveToFirst()) {

            do {
                String id =  cursor.getString(0);
                String name = cursor.getString(1);
                String phone = cursor.getString(2);
                customerModel.add(new CustomerModel(id, name, phone));

                System.out.println("Cust id " + cursor.getString(0));
                System.out.println("Cust name " + cursor.getString(1));
                System.out.println("Phone num " + cursor.getString(2));
            } while (cursor.moveToNext());
        }
//SELECT * FROM 'vRentalInfo' where CustomerID Like "%9%" or CustomerName LIKE "%D%"
        cursor.close();
        return customerModel;
    }

    public ArrayList<RentalInfoModel> searchQuery(String search) {
        final SQLiteDatabase db = readDatabase();
        ArrayList<RentalInfoModel> rentalInfoModels = new ArrayList();
        String selection = "SELECT * FROM vRentalInfo WHERE"+ " CustomerID " + " LIKE " +"'%"+ search + "%'" +  " or CustomerName " + " LIKE "+ "'%"+ search + "%'";
        String arg = "'%'"+search+"'%'";
        Cursor cursor = db.rawQuery( selection, null);
        System.out.println("Search query called "+selection);
        if (cursor.moveToFirst()) {

            do {
                String id =  cursor.getString(8);
                String name = cursor.getString(9);
                String bal = cursor.getString(11);
                if (bal.equals("0")) {
                    bal = "0.0";
                }
                rentalInfoModels.add(new RentalInfoModel("Customer Id "+id, "Customer name: "+name, "Remaining Bal $"+bal));

                System.out.println("Cust id " + cursor.getString(8));
                System.out.println("Cust name " + cursor.getString(9));
                System.out.println("Rem bal $ " + cursor.getString(11));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return rentalInfoModels;
    }


    /**
     * Select V.VIN, V.Vehicle, AVG(R.TotalAmount) as AverageDailyPrice
     * FROM vRentalInfo as V, Rental as R
     * WHERE V.VIN = R.VehicleID AND V.VIN LIKE '%A%' AND V.Vehicle LIKE '%Audi%'
     * GROUP BY v.VIN, v.Vehicle;
     * @param vehDescription
     *
     * @return
     */
    public ArrayList<RentalInfoModel> searchQuery5b(String vin, String vehDescription) {
        final SQLiteDatabase db = readDatabase();
        ArrayList<RentalInfoModel> rentalInfoModels = new ArrayList();
        String selection = "Select V.VIN, V.Vehicle, AVG(R.TotalAmount) as AverageDailyPrice FROM vRentalInfo as V, Rental" +
                " as R WHERE V.VIN = R.VehicleID AND V.VIN LIKE " + "'%" + vin  + "%'" + " AND V.Vehicle LIKE "+ "'%" + vehDescription + "%'" + " GROUP BY v.VIN, v.Vehicle";
        Cursor cursor = db.rawQuery( selection, null);
        System.out.println("Search query called "+selection);
        if (cursor.moveToFirst()) {

            do {
                String vId =  cursor.getString(0);
                String description = cursor.getString(1);
                String avgDailyPrice = cursor.getString(2);
                rentalInfoModels.add(new RentalInfoModel("Vin: "+vId, "Vehicle: "+description, "Average Daily Price $ "+avgDailyPrice));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return rentalInfoModels;
    }

    /**
     * SELECT IIF(PaymentDate IS NULL, TotalAmount, 0) as 'Remaining Balance'
     * FROM Rental
     * Where ReturnDate = '2019-11-15' AND CustID = 210 AND VehicleID = '19VDE1F3XEE414842';
     */

    public String searchQuery4(String date, String vehicleId, String custId) {
        final SQLiteDatabase db = readDatabase();
        String selection = "SELECT IIF(PaymentDate IS NULL, TotalAmount, 0) as 'Remaining Balance'\n" +
                "FROM Rental\n" +
                "Where ReturnDate = "+ "'"+date+"'"+" AND CustID ="+  "'"+ custId +"'" + " AND VehicleID = " + "'"+vehicleId+ "'";
        Cursor cursor = db.rawQuery( selection, null);
        System.out.println("Search query called "+selection);
        if (cursor.moveToFirst()) {

            do {
                String bal =  cursor.getString(0);
                System.out.println("Rem bal $ " + cursor.getString(0));
                return bal;
            } while (cursor.moveToNext());
        }
        cursor.close();
        return "0";
    }

    /**
     * UPDATE Rental
     * SET PaymentDate = ReturnDate, Returned = 1
     * WHERE CustID = 210 AND VehicleID = 'JTHFF2C26F135BX45' AND ReturnDate = '2019-11-15';

     */

    public String updateRental(String date, String vehicleId, String custId) {
        final SQLiteDatabase db = readDatabase();
        String query = "UPDATE Rental SET paymentDate = ReturnDate, Returned = 1 WHERE CustID = "+"'"+custId+"'" + "AND VehicleID = "+"'"+vehicleId+"'"+ "AND ReturnDate ="+"'"+date+"'";

        Cursor cursor = db.rawQuery( query, null);
        System.out.println("Search query called "+query);
        if (cursor.moveToFirst()) {

            do {
                String bal =  cursor.getString(0);
                System.out.println("Rem bal $ " + cursor.getString(0));
                return bal;
            } while (cursor.moveToNext());
        }
        cursor.close();
        return "0";
    }

    /**
     * SELECT Vehicle.VehicleID, Vehicle.Description
     * FROM Vehicle, Rental, Rate
     * Where Vehicle.Type = Rate.Type AND Vehicle.Category = Rate.Category AND
     * Vehicle.Type = 1 and Vehicle.Category = 1 AND
     * Vehicle.VehicleID = Rental.VehicleID AND
     * '2019-03-14' NOT BETWEEN Rental.StartDate AND Rental.ReturnDate AND
     * '2019-03-21' NOT BETWEEN Rental.StartDate AND Rental.ReturnDate;
     * @return
     */

    public ArrayList<RentalInfoModel> searchQuery3(String vType, String category, String startDate, String endDate) {
        final SQLiteDatabase db = readDatabase();
        String selection = "SELECT Vehicle.VehicleID, Vehicle.Description FROM Vehicle, Rental, Rate \n" +
                "Where Vehicle.Type = Rate.Type AND Vehicle.Category = Rate.Category AND Vehicle.Type = "+ vType +" AND Vehicle.Category = "+ category +" AND \n" +
                "Vehicle.VehicleID = Rental.VehicleID AND" + " '" + startDate + "' "+ "NOT BETWEEN Rental.StartDate AND Rental.ReturnDate AND"  + " '" + endDate + "' "+  "NOT BETWEEN Rental.StartDate AND Rental.ReturnDate";
        System.out.println("Search query called "+selection);

        Cursor cursor = db.rawQuery( selection, null);
        ArrayList<RentalInfoModel> models = new ArrayList<>();
        if (cursor.moveToFirst()) {

            do {
                String vin = cursor.getString(0);
                String desc = cursor.getString(1);
                models.add(new RentalInfoModel("VIN: "+vin, "Vehicle "+desc, ""));
                System.out.println("vin $ " + cursor.getString(0));
                System.out.println("desc $ " + cursor.getString(1));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return models;
    }

    /**
     * INSERT INTO Rental(CustID, VehicleID, StartDate, OrderDate, RentalType, Qty, TotalAmount, PaymentDate, Returned)
     * VALUES (210, '19VDE1F3XEE414842', '2019-09-14', '2019-09-14', 1, 1,(SELECT RentalQty * IIF(RentalType = 1, Daily, Weekly) From Rate, Rental Where Type = 5 and Category = 0), IIF(PAYNOW, OrderDate, NULL), IIF(PAYNOW, 1, 0));
     * @return
     */


    public ArrayList<RentalInfoModel> searchQuery3b(String custId, String vehId, String startDate, String endDate, String rentalType, String quantity, String returnDate, boolean payNow) {
        final SQLiteDatabase db = readDatabase();

        vehId = "'"+vehId+"'";
        startDate = "'" + startDate + "'";
        endDate = "'" + endDate + "'";

        /**
         * INSERT INTO Rental
         *
         */
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        String s = String.format("INSERT INTO Rental VALUES (%s, %s, %s, %s, %s, %s, %s,(SELECT Qty * IIF(RentalType = 1, Daily, Weekly) From Rate, Rental Where Type = 4 and Category = 0), IIF(true, '2020-03-14', NULL), IIF(true, 1, 0))",
                custId, vehId, startDate,dateFormat.format(date), rentalType, quantity,  returnDate, payNow);
        String selection = String.format("INSERT INTO Rental(CustID, VehicleID, StartDate, OrderDate, RentalType, Qty, TotalAmount, PaymentDate, Returned)\n" +
                "VALUES (%s, %s, %s, %s, %s, %s,(SELECT Qty * IIF(RentalType = 1, Daily, Weekly) From Rate, Rental Where Type = 5 and Category = 0), IIF(%b, OrderDate, NULL), IIF(%b, 1, 0));", custId, vehId, startDate, endDate, rentalType, quantity, payNow, payNow);

        Cursor cursor = db.rawQuery( s, null);
        System.out.println("Search query called "+s);
        ArrayList<RentalInfoModel> models = new ArrayList<>();
        if (cursor.moveToFirst()) {

            do {
                String vin = cursor.getString(0);
                String desc = cursor.getString(1);
                models.add(new RentalInfoModel("VIN: "+vin, "Vehicle "+desc, ""));
                System.out.println("vin $ " + cursor.getString(0));
                System.out.println("desc $ " + cursor.getString(1));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return models;
    }



}
