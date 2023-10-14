package com.example.s336154_mappe2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private OppgaveDataKilde dataKilde;
    private ArrayAdapter<Task> oppgaveArrayAdapter;
    private EditText oppgaveEditText;
    private List<Task> oppgaver;


    @Override
    protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataKilde = new OppgaveDataKilde(this); dataKilde.open();
        oppgaveEditText = findViewById(R.id.editText); ListView oppgaveListView = findViewById(R.id.listView);
        oppgaver = dataKilde.finnAlleOppgaver();
        oppgaveArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, oppgaver);
        oppgaveListView.setAdapter(oppgaveArrayAdapter);

        Button leggtilButton = findViewById(R.id.leggtil);
        leggtilButton.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { String oppgaveNavn = oppgaveEditText.getText().toString(); if (!oppgaveNavn.isEmpty()) { Task oppgave = dataKilde.leggInnOppgave(oppgaveNavn); oppgaveArrayAdapter.add(oppgave); oppgaveEditText.setText(""); } }
        });

        Button slettButton = findViewById(R.id.slett); slettButton.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { long oppgaveId=0; oppgaveId = Long.parseLong(oppgaveEditText.getText().toString()); if (oppgaveId > 0) { dataKilde.slettOppgave(oppgaveId); oppgaver = dataKilde.finnAlleOppgaver(); oppgaveArrayAdapter = new ArrayAdapter<>(getBaseContext(),
                android.R.layout.simple_list_item_1, oppgaver); oppgaveListView.setAdapter(oppgaveArrayAdapter);
            oppgaveEditText.setText(""); } } });
    } //ferdig onCreate

    @Override
    protected void onResume() { dataKilde.open(); super.onResume(); }
    @Override
    protected void onPause() { dataKilde.close(); super.onPause(); }
} //ferdig MainActivity

