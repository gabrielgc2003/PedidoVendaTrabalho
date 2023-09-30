package com.example.trabalhobimestral.model;

public class ProdutoPedido {
    private Produto produto;
    private int qtdProduto;
    private double vlTotalProduto;

    public ProdutoPedido() {
    }

    public ProdutoPedido(Produto produto, int qtdProduto, double vlTotalProduto) {
        this.produto = produto;
        this.qtdProduto = qtdProduto;
        this.vlTotalProduto = vlTotalProduto;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQtdProduto() {
        return qtdProduto;
    }

    public void setQtdProduto(int qtdProduto) {
        this.qtdProduto = qtdProduto;
    }

    public double getVlTotalProduto() {
        return vlTotalProduto;
    }

    public void setVlTotalProduto(double vlTotalProduto) {
        this.vlTotalProduto = vlTotalProduto;
    }
}
