package com.sanzfdu.cafeteriaetsib.bl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sanzfdu.cafeteriaetsib.dl.Bocata;
import com.sanzfdu.cafeteriaetsib.dl.Ingrediente;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MirenPablo on 17/02/2016.
 */
public class ListOfThings {

    public static List<Bocata> lbocata = new ArrayList<Bocata>();
    public static List <Ingrediente> lingr = new ArrayList<Ingrediente>();

    public ListOfThings() {

    }
    public void fillLists(Context activityContext){
        lbocata = new ArrayList<Bocata>();
        Ingrediente ingred = new Ingrediente();
        /*CUIDADO NOMBRE DB!!! ESTOY VIENDO A POSTA CUAL ES EL ERROR PERO LUEGO HAY QUE CAMBIARLO PORQUE
        NO EXISTE UNA DATABASE LLAMADA bocatadqweqi.db CON UNA TABLA BOCATAS, la clase MySQL
        crea una tabla llamada asi pero en otra database*/
        /*Nota: el null es porque no tenemos un where y por tanto no tienes un array o elemento para indicar en base a que array de algo vas a buscar,
        si le pones un noseque=? luego le pones una coma y lo que quieres buscar y ya esta*/

        MySQL cn = new MySQL(activityContext, "bocatasUni.db", null, 1);

        SQLiteDatabase db = cn.getReadableDatabase();

        Cursor cBoc = db.rawQuery("SELECT * FROM Bocatas", null);

        if (cBoc.moveToFirst()) {
            lbocata = cn.extractData(cBoc, db);
            }
        Cursor cIngr = db.rawQuery("SELECT * FROM Ingredientes", null);
        if(cIngr.moveToFirst()) {
            do {
                ingred = new Ingrediente(cIngr.getString(0), null);
                lingr.add(ingred);

            } while (cIngr.moveToNext());
        }
        db.close();
        cBoc.close();
            cIngr.close();
        /*NOTA: SQLiteStatement para escribir en la base de datos, tirar el string y ya
    en cambio, SQLiteDatabase es para coger de la base de datos, un puntero para ir pillando lo que necesitemos
    de la base de datos*/
       System.out.println(lbocata.size());
    }




}


