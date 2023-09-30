package com.example.trabalhobimestral;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.trabalhobimestral.model.Pedido;

import java.util.ArrayList;

public class PagamentoActivity extends AppCompatActivity {
    private int indicePedido;
    private Spinner spQtdParcela;
    private RadioButton rbVista;
    private RadioButton rbPrazo;
    private RadioGroup rgSistema;
    private String[] listaQtdParcelas;
    private String[] listaParcelas;
    private double valorTotalCalculado;
    private Spinner spParcelas;
    private int posicaoQtdParcela;
    private TextView vlTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);
        setTitle("Forma de Pagamento");
        carregaPedido();
        spQtdParcela = findViewById(R.id.spQtdParcela);
        rbVista = findViewById(R.id.rbVista);
        rbPrazo = findViewById(R.id.rbPrazo);
        rgSistema = findViewById(R.id.rgSistema);
        spParcelas = findViewById(R.id.spParcelas);
        vlTotal = findViewById(R.id.vlTotal);
        rbVista.setChecked(true);
        spQtdParcela.setEnabled(false);
        spParcelas.setEnabled(false);
        posicaoQtdParcela = 0;
        carregaQtdParcelas();
        carregaListaParcelas();

        rgSistema.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                carregaQtdParcelas();
                carregaListaParcelas();
            }
        });

        spQtdParcela.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                posicaoQtdParcela = i;
                carregaQtdParcelas();
                carregaListaParcelas();
                preencheValorTotal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void carregaPedido() {
        indicePedido = Controller.getInstance().buscaUltimoPedido();
    }
    private void carregaQtdParcelas(){
        if (rbVista.isChecked()){
            listaQtdParcelas = new String[1];
            listaQtdParcelas[0] = "1";
            spQtdParcela.setEnabled(false);
            spParcelas.setEnabled(false);
        }else {
            listaQtdParcelas = new String[12];
            spQtdParcela.setEnabled(true);
            for (int i = 0; i < 12; i++) {
                listaQtdParcelas[i] = String.valueOf(i+1);
            }
        }
        ArrayAdapter adapter= new ArrayAdapter<>(
                PagamentoActivity.this,
                android.R.layout.simple_dropdown_item_1line,
                listaQtdParcelas);
        spQtdParcela.setAdapter(adapter);
    }

    private void carregaListaParcelas(){
        valorTotalCalculado = 0;
        int qtdParcelas = posicaoQtdParcela + 1;
        Pedido pedido = Controller.getInstance().retornarPedidos().get(indicePedido);
        double valorTotal = pedido.getVlTotalPedido();

        if (rbVista.isChecked()) {
            listaParcelas = new String[1];
            valorTotalCalculado = valorTotal - (valorTotal * 0.05);
            listaParcelas[0] = "1ª - R$" + String.valueOf(valorTotalCalculado).replace(".", ",");
        } else {
            listaParcelas = new String[qtdParcelas];
            valorTotalCalculado = valorTotal + (valorTotal * 0.05);
            double vlParcela = valorTotalCalculado / qtdParcelas;
            for (int i = 0; i < qtdParcelas; i++) {
                listaParcelas[i] = (i + 1) + "ª - R$" + String.valueOf(vlParcela).replace(".", ",");
            }
            spParcelas.setEnabled(true);
        }
        ArrayAdapter adapter = new ArrayAdapter(PagamentoActivity.this, android.R.layout.simple_dropdown_item_1line, listaParcelas);
        spParcelas.setAdapter(adapter);
    };
    private void preencheValorTotal(){
        vlTotal.setText(String.valueOf(valorTotalCalculado));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Controller.getInstance().deletaPedidoTemporario(indicePedido);
        finish();
    }

}