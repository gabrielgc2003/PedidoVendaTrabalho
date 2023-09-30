package com.example.trabalhobimestral;

import com.example.trabalhobimestral.model.Cliente;
import com.example.trabalhobimestral.model.Pedido;
import com.example.trabalhobimestral.model.Produto;
import com.example.trabalhobimestral.model.ProdutoPedido;

import java.util.ArrayList;
import java.util.Objects;

public class Controller {
    private static Controller instancia;
    private static ArrayList<Cliente> listaClientes;
    private static ArrayList<Produto>listaProdutos;
    private static ArrayList<Pedido> listaPedidos;
    private static ArrayList<ProdutoPedido> listaProdutosPedido;

    public static Controller getInstance(){
        if (instancia == null)
            return instancia = new Controller();
        else
            return instancia;
    }

    private Controller(){
        listaClientes = new ArrayList<>();
        listaClientes.add(new Cliente("Gabriel", "1234567891234"));
        listaClientes.add(new Cliente("Joaquim", "1234567891425"));
        listaClientes.add(new Cliente("Joao", "11111111111111"));
        listaProdutos = new ArrayList<>();
        listaProdutos.add(new Produto(1, "caneta", 10));
        listaProdutos.add(new Produto(2, "goiaba", 10));
        listaProdutos.add(new Produto(3, "isopor", 10));
        listaPedidos = new ArrayList<>();
        listaProdutosPedido = new ArrayList<>();
        instancia = this;
    }
    public void salvarCliente(Cliente cliente){
        listaClientes.add(cliente);
    }
    public void salvarProduto(Produto produto){
        listaProdutos.add(produto);
    }
    public void salvarPedido(Pedido pedido){
        listaPedidos.add(pedido);
    }
    public void salvarProdutosPedido(ProdutoPedido produtoPedido){
        listaProdutosPedido.add(produtoPedido);
    }
    public void deletarProdutosPedido(){
        listaProdutosPedido.clear();
    }
    public void deletaPedidoTemporario(int i){
        listaPedidos.remove(i);
    }
    public ArrayList<Cliente> retornarClientes() {
        return listaClientes;
    }
    public ArrayList<Produto> retornarProdutos() {
        return listaProdutos;
    }
    public ArrayList<Pedido> retornarPedidos() {
        return listaPedidos;
    }
    public ArrayList<ProdutoPedido> retornarProdutosPedido() {
        return listaProdutosPedido;
    }
    public int retornaCodigo(ArrayList<Pedido> lista){
        int codigo = 0;
        for (int i = 0; i < lista.size() ; i++) {
            if (lista.get(i).getCodigo() > codigo) {
                codigo = listaPedidos.get(i).getCodigo();
            }
        }
        return codigo + 1;
    }
    public int buscaUltimoPedido(){
        int maior = 0;
        int indice = 0;
        for (int i = 0; i < listaPedidos.size() ; i++) {
            if (listaPedidos.get(i).getCodigo() > maior) {
                maior = listaPedidos.get(i).getCodigo();
                indice = i;
            }
        }
        return indice;
    }

    public boolean verificaExisteProduto(int codigo) {
        for (int i = 0; i < listaProdutosPedido.size() ; i++) {
            if (listaProdutosPedido.get(i).getProduto().getCodigo() == codigo){
                return true;
            }
        }
        return false;
    }
}
