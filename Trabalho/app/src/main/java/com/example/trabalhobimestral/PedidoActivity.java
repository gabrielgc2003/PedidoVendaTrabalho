package com.example.trabalhobimestral;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.trabalhobimestral.model.Cliente;
import com.example.trabalhobimestral.model.Pedido;
import com.example.trabalhobimestral.model.Produto;
import com.example.trabalhobimestral.model.ProdutoPedido;

import java.util.ArrayList;

public class PedidoActivity extends AppCompatActivity {
    private AutoCompleteTextView tvAddCliente;
    private int posicaoCliente;
    private TextView tvErroCliente;
    private AutoCompleteTextView tvAddProduto;
    private TextView tvErroProduto;
    private TextView tvValorUnitario;
    private int posicaoProduto;
    private Button btPagamento;
    private ImageButton btAddProduto;
    private EditText edQtdProduto;

    private TextView tvQuantidadeProdutos;
    private TextView tvListaItens;
    private TextView tvTotalPedido;
    private double totalPedido;
    private int qtdProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);
        setTitle("Pedido");
        tvAddCliente = findViewById(R.id.tvAddCliente);
        tvErroCliente = findViewById(R.id.tvErroCliente);
        carregarClientes();
        tvAddProduto = findViewById(R.id.tvAddProduto);
        tvErroProduto = findViewById(R.id.tvErroProduto);
        carregarProdutos();
        tvValorUnitario = findViewById(R.id.tvValorUnitario);
        btPagamento = findViewById(R.id.btPagamento);
        btAddProduto = findViewById(R.id.btAddProduto);
        edQtdProduto = findViewById(R.id.edQtdProduto);
        tvQuantidadeProdutos = findViewById(R.id.tvQuantidadeProdutos);
        tvListaItens = findViewById(R.id.tvListaItens);
        tvTotalPedido = findViewById(R.id.tvTotalPedido);

        posicaoCliente = -1;
        tvAddCliente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                posicaoCliente = i;
                tvErroCliente.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        tvAddCliente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                for (int j = 0; j <  Controller.getInstance().retornarClientes().size(); j++) {
                    if (tvAddCliente.getText().toString().equals(Controller.getInstance().retornarClientes().get(j).getNome())){
                        posicaoCliente = j;
                    }
                }
                tvErroCliente.setVisibility(View.GONE);
            }
        });

        posicaoProduto = -1;
        tvAddProduto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                for (int j = 0; j <  Controller.getInstance().retornarProdutos().size(); j++) {
                    if (Integer.parseInt(tvAddProduto.getText().toString()) == Controller.getInstance().retornarProdutos().get(j).getCodigo()){
                        posicaoProduto = j;
                    }
                }
                tvErroProduto.setVisibility(View.GONE);
                carregaQtdValor(posicaoProduto);
            }
        });



        btAddProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionaProdutoLista();
                somaItens();
            }
        });

        btPagamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvaPedidoParcial();
                abrirActivity(PagamentoActivity.class);
                finish();
            }
        });

    }

    private void adicionaProdutoLista() {
        if (posicaoProduto == -1){
            tvErroProduto.setVisibility(View.VISIBLE);
            return;
        }
        if (edQtdProduto.getText().toString().isEmpty() || edQtdProduto.getText().toString() == "0"){
            edQtdProduto.setError("Selecione a quantidade do produto! Obs: Deve ser maior que 0! ");
            return;
        }
        ProdutoPedido produtoPedido = new ProdutoPedido();
        Produto produto = Controller.getInstance().retornarProdutos().get(posicaoProduto);
        if (Controller.getInstance().verificaExisteProduto(produto.getCodigo())){
            tvErroProduto.setText("Produto já adicionado no pedido!");
            tvErroProduto.setVisibility(View.VISIBLE);
            posicaoProduto = -1;
            tvAddProduto.setText(null);
            tvAddProduto.requestFocus();
            return;
        }
        int qtdProduto = Integer.parseInt(edQtdProduto.getText().toString());
        produtoPedido.setProduto(produto);
        produtoPedido.setQtdProduto(qtdProduto);
        produtoPedido.setVlTotalProduto(qtdProduto * produto.getValorUnitario());
        Controller.getInstance().salvarProdutosPedido(produtoPedido);
        posicaoProduto = -1;
        edQtdProduto.setText(null);
        tvValorUnitario.setText(null);
        tvAddProduto.setText(null);
        tvAddProduto.requestFocus();
    }

    private void salvaPedidoParcial() {
        if (posicaoCliente == -1){
            tvErroCliente.setVisibility(View.VISIBLE);
            return;
        }
        if (Controller.getInstance().retornarProdutosPedido().isEmpty()){
            tvErroProduto.setVisibility(View.VISIBLE);
            return;
        }
        Pedido pedido = new Pedido();
        ArrayList<ProdutoPedido> listaProdutoPedido = new ArrayList<>();
        for (int i = 0; i < Controller.getInstance().retornarProdutosPedido().size(); i++) {
            ProdutoPedido produtoPedido = Controller.getInstance().retornarProdutosPedido().get(i);
            listaProdutoPedido.add(produtoPedido);
        }
        pedido.setCliente(Controller.getInstance().retornarClientes().get(posicaoCliente));
        pedido.setProdutoPedidos(listaProdutoPedido);

        double valorTotalProdutos = 0;
        for (int i = 0; i <  Controller.getInstance().retornarProdutosPedido().size(); i++) {
            valorTotalProdutos +=  Controller.getInstance().retornarProdutosPedido().get(i).getVlTotalProduto();
        }
        pedido.setVlTotalPedido(valorTotalProdutos);
        pedido.setCodigo(Controller.getInstance().retornaCodigo(Controller.getInstance().retornarPedidos()));
        Controller.getInstance().salvarPedido(pedido);
        Controller.getInstance().deletarProdutosPedido();

    }

    private void carregaQtdValor(int posicaoProduto) {
        Produto produto = Controller.getInstance().retornarProdutos().get(posicaoProduto);
        String aux = String.valueOf(produto.getValorUnitario());
        tvValorUnitario.setText(aux);
    }



    private void carregarProdutos() {
        String[] vetProdutos = new String[Controller.getInstance().retornarProdutos().size()];
        for (int i = 0; i < Controller.getInstance().retornarProdutos().size(); i++){
            vetProdutos[i] = String.valueOf(Controller.getInstance().retornarProdutos().get(i).getCodigo());
        }
        ArrayAdapter<String>  adapter = new ArrayAdapter<>(PedidoActivity.this, android.R.layout.simple_dropdown_item_1line, vetProdutos);
        tvAddProduto.setAdapter(adapter);
    }

    private void carregarClientes() {
        String[] vetCliente = new String[Controller.getInstance().retornarClientes().size()];
        for (int i = 0; i < Controller.getInstance().retornarClientes().size(); i++) {
            Cliente cliente = Controller.getInstance().retornarClientes().get(i);
            vetCliente[i] = cliente.getNome();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(PedidoActivity.this, android.R.layout.simple_dropdown_item_1line, vetCliente);
        tvAddCliente.setAdapter(adapter);

    }

    private void somaItens(){
        qtdProdutos = Controller.getInstance().retornarProdutosPedido().size();
        String retorno = "Lista de Produtos do Pedido:\n" +
                         "----------------------------\n";
        String concatenado = "";
        totalPedido = 0;
        for (int i = 0; i < Controller.getInstance().retornarProdutosPedido().size(); i++) {
            Produto produto = Controller.getInstance().retornarProdutosPedido().get(i).getProduto();
            totalPedido += Controller.getInstance().retornarProdutosPedido().get(i).getVlTotalProduto();
            concatenado += "Código: " + produto.getCodigo() + "\n" +
                           "Descrição: " + produto.getDescricao() + "\n" +
                           "Valor Unitário: R$" + produto.getValorUnitario() + "\n" +
                           "Quantidade: " + Controller.getInstance().retornarProdutosPedido().get(i).getQtdProduto() + "\n" +
                           "Valor total do produto: R$" + Controller.getInstance().retornarProdutosPedido().get(i).getVlTotalProduto() + "\n" +
                           "------------------------------------------\n";
        }
        retorno += concatenado;
        tvQuantidadeProdutos.setText(String.valueOf(qtdProdutos));
        tvListaItens.setText(retorno);
        tvTotalPedido.setText(String.valueOf(totalPedido));
    }
    private void abrirActivity(Class<?> activity) {
        Intent intent = new Intent(PedidoActivity.this, activity);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}