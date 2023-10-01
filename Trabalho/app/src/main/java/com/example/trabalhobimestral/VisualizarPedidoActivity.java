package com.example.trabalhobimestral;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.trabalhobimestral.model.Pedido;
import com.example.trabalhobimestral.model.ProdutoPedido;

public class VisualizarPedidoActivity extends AppCompatActivity {

    private TextView tvListaPedidos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Visualizar Pedido");
        setContentView(R.layout.activity_visualizar_pedido);
        tvListaPedidos = findViewById(R.id.tvListaPedidos);
        carregaListaPedidos();
    }

    private void carregaListaPedidos() {
        String retorno = "Lista de Pedidos:\n" +
                         "------------------------------------------\n";
        String concatena = "";
        String concatenaItens = "";
        for (int i = 0; i < Controller.getInstance().retornarPedidos().size(); i++) {
            concatena += "Pedido N°:" + Controller.getInstance().retornarPedidos().get(i).getCodigo() + "\n" +
                        "Cliente: " + Controller.getInstance().retornarPedidos().get(i).getCliente().getNome() + "\n";
            Pedido pedido = Controller.getInstance().retornarPedidos().get(i);
            concatenaItens = "Lista de Produtos\n";
            for (int j = 0; j < pedido.getProdutoPedidos().size(); j++) {
                ProdutoPedido produtoPedido = pedido.getProdutoPedidos().get(j);
                concatenaItens += "--- " + (j+1) + "->" + " Codigo: " + produtoPedido.getProduto().getCodigo() + "\n" +
                                 "---->" + " Descrição: " + produtoPedido.getProduto().getDescricao() + "\n" +
                                 "---->" + " Quantidade: " + produtoPedido.getQtdProduto() + "\n" +
                                 "---->" + " Valor Total Produto(R$): " + produtoPedido.getVlTotalProduto() + "\n" +
                                 "\n\n";
            }
            concatena += concatenaItens;
            concatena += "Forma de Pagamento: " + pedido.getCondicaoPagamento().getDescricao() + "\n" +
                         "Valor Total do Pedido(R$): " + pedido.getVlTotalPedido() + "\n" +
                         "------------------------------------------\n\n\n";
        }
        retorno += concatena;
        tvListaPedidos.setText(retorno);
    }
}