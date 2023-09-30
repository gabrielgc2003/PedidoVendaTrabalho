package com.example.trabalhobimestral.model;

import com.example.trabalhobimestral.enums.CondicaoPagamentoEnum;

import java.util.ArrayList;

public class Pedido {
    private int codigo;
    private Cliente cliente;
    private ArrayList<ProdutoPedido> produtoPedidos;
    private int cdCondicaoPagamento;
    private CondicaoPagamentoEnum condicaoPagamento;
    private int parcelas;
    private double vlParcelas;
    private double vlTotalPedido;

    public Pedido() {
    }

    public Pedido(int codigo, Cliente cliente, ArrayList<ProdutoPedido> produtoPedidos, int cdCondicaoPagamento, CondicaoPagamentoEnum condicaoPagamento, int parcelas, double vlParcelas, double vlTotalPedido) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.produtoPedidos = produtoPedidos;
        this.cdCondicaoPagamento = cdCondicaoPagamento;
        this.condicaoPagamento = condicaoPagamento;
        this.parcelas = parcelas;
        this.vlParcelas = vlParcelas;
        this.vlTotalPedido = vlTotalPedido;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ArrayList<ProdutoPedido> getProdutoPedidos() {
        return produtoPedidos;
    }

    public void setProdutoPedidos(ArrayList<ProdutoPedido> produtoPedidos) {
        this.produtoPedidos = produtoPedidos;
    }

    public int getCdCondicaoPagamento() {
        return cdCondicaoPagamento;
    }

    public void setCdCondicaoPagamento(int cdCondicaoPagamento) {
        this.cdCondicaoPagamento = cdCondicaoPagamento;
    }

    public CondicaoPagamentoEnum getCondicaoPagamento() {
        return condicaoPagamento;
    }

    public void setCondicaoPagamento(CondicaoPagamentoEnum condicaoPagamento) {
        this.condicaoPagamento = condicaoPagamento;
    }

    public int getParcelas() {
        return parcelas;
    }

    public void setParcelas(int parcelas) {
        this.parcelas = parcelas;
    }

    public double getVlParcelas() {
        return vlParcelas;
    }

    public void setVlParcelas(double vlParcelas) {
        this.vlParcelas = vlParcelas;
    }

    public double getVlTotalPedido() {
        return vlTotalPedido;
    }

    public void setVlTotalPedido(double vlTotalPedido) {
        this.vlTotalPedido = vlTotalPedido;
    }
}
