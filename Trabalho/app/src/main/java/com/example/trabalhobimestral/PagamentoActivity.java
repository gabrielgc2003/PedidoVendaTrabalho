package com.example.trabalhobimestral;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trabalhobimestral.enums.CondicaoPagamentoEnum;
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
    private Button btFecharPedido;
    private double vlParcela;

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
        btFecharPedido = findViewById(R.id.btFecharPedido);
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
                if (i == 0){
                    carregaListaParcelas();
                    preencheValorTotal();
                } else {
                    carregaListaParcelas();
                    preencheValorTotal();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btFecharPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fecharPedido();
            }
        });
    }

    private void fecharPedido() {
        if (rbVista.isChecked()){
            Controller.getInstance().retornarPedidos().get(indicePedido).setCondicaoPagamento(CondicaoPagamentoEnum.ZERO);
            Controller.getInstance().retornarPedidos().get(indicePedido).setVlParcelas(valorTotalCalculado);

        }else{
            Controller.getInstance().retornarPedidos().get(indicePedido).setCondicaoPagamento(CondicaoPagamentoEnum.UM);
            Controller.getInstance().retornarPedidos().get(indicePedido).setVlParcelas(vlParcela);
        }
        Controller.getInstance().retornarPedidos().get(indicePedido).setVlTotalPedido(valorTotalCalculado);
        Controller.getInstance().retornarPedidos().get(indicePedido).setParcelas(posicaoQtdParcela + 1);
        System.out.println(Controller.getInstance().retornarPedidos().get(indicePedido).getProdutoPedidos().toString());
        Toast.makeText(PagamentoActivity.this,
                "Pedido de número ("+ Controller.getInstance().retornarPedidos().get(indicePedido).getCodigo() +") foi registrado com sucesso!!",
                Toast.LENGTH_LONG).show();
        this.finish();
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
        ArrayList<Pedido>  listaPedidos = Controller.getInstance().retornarPedidos();
        Pedido pedido =listaPedidos.get(indicePedido);
        double valorTotal = pedido.getVlTotalPedido();

        if (rbVista.isChecked()) {
            listaParcelas = new String[1];
            valorTotalCalculado = valorTotal - (valorTotal * 0.05);
            listaParcelas[0] = "1ª - R$" + String.valueOf(valorTotalCalculado).replace(".", ",");
        } else {
            listaParcelas = new String[qtdParcelas];
            valorTotalCalculado = valorTotal + (valorTotal * 0.05);
            vlParcela = valorTotalCalculado / qtdParcelas;
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