package com.sanzfdu.cafeteriaetsib.pl;

import android.os.Bundle;

import android.app.Activity;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ListView;

import com.sanzfdu.cafeteriaetsib.R;
import com.sanzfdu.cafeteriaetsib.bl.TextAdapter;
import com.sanzfdu.cafeteriaetsib.bl.DataListAdapter;

import com.sanzfdu.cafeteriaetsib.bl.MySQL;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.sanzfdu.cafeteriaetsib.dl.Bocata;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;



public class Activity_fav extends Fragment{

    private  ListView lv;
    View rootView;

    private  List<String> data=new ArrayList<String>();
    private  List<Bocata> lbocata;

    public Activity_fav(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        boolean bool = true;//Comprobara si hay algun elemento a mostrar o no

        rootView = inflater.inflate(R.layout.fragment_activity_fav, container, false);

        //Inicializamos los botones para que puedan cumplir su funcion al hacer click
        startButtonListening();

        lv = (ListView)rootView.findViewById(R.id.listado);
        //Cogemos donde ira el listado
        lbocata = new ArrayList<Bocata>();
        MySQL cn = new MySQL(getActivity().getApplicationContext(),"bocatasUni.db",null,1);

        SQLiteDatabase db = cn.getReadableDatabase();

        Cursor cBoc = db.rawQuery("SELECT * FROM Bocatas", null) ;

        if(cBoc.moveToFirst()) {
            lbocata = cn.extractData(cBoc,db);
            //Que solo se muestren los elementos que se han valorado
            for(int i = 0; i < lbocata.size();i++){
                if(lbocata.get(i).getFav() < 3){
                    lbocata.remove(i);
                    i--;
                    if (lbocata.size() == 0) {
                        data.add("Solo los bocatas con una valoracion de 3 estrellas o mas pueden aparecer aqui\n");
                        bool = false;
                        lv.setAdapter(new TextAdapter(getActivity().getApplicationContext(), data));
                    }
                }
            }
            if(bool==true) {
                //Buscar posicion en la lista para que este ordenado por fav, FALTA METODO PARA CALCULAR ESO
                Collections.sort(lbocata, Bocata.Comparators.FAV);

                lv.setAdapter(new DataListAdapter(getActivity().getApplicationContext(), lbocata));
            }
        }else{

            data.add("Base de datos vacia\n");
            lv.setAdapter(new TextAdapter(getActivity().getApplicationContext(),data));
        }
        db.close();
        cBoc.close();


        return rootView;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    public void startButtonListening() {

        Button btnRet = (Button) rootView.findViewById(R.id.buttonReturn);

        btnRet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                MenuPrincipal princMen = new MenuPrincipal();
                fragmentTransaction.replace(R.id.container_body, princMen);
                fragmentTransaction.commit();
            }
        });
    }
}
