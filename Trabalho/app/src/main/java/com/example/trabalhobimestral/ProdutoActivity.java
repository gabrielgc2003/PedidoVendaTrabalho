package com.example.trabalhobimestral;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trabalhobimestral.model.Produto;

public class ProdutoActivity extends AppCompatActivity {
    private EditText edCodigo;
    private EditText edDescricao;
    private EditText edValorUnitario;
    private Button btSalvar;
    private TextView tvListaProduto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);
        setTitle("Cadastrar Produto");
        edCodigo = findViewById(R.id.edCodigo);
        edDescricao = findViewById(R.id.edDescricao);
        edValorUnitario = findViewById(R.id.edValorUnitario);
        btSalvar = findViewById(R.id.btSalvar);
        tvListaProduto = findViewById(R.id.tvListaProduto);

        atualizarListaProdutos();
        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarProduto();
            }
        });
    }

    private void atualizarListaProdutos() {
        String exibicao = "";
        for (Produto produto:
                Controller.getInstance().retornarProdutos() ) {
            exibicao += "\nCódigo: "+produto.getCodigo()
                    +"\nDescrição: "+produto.getDescricao()
                    +"\nValor Unitário(R$): "+produto.getValorUnitario()
                    +"\n----------------------------------";
        }
        tvListaProduto.setText(exibicao);
    }

    private void salvarProduto() {
        for (Produto produto:
                Controller.getInstance().retornarProdutos() ) {
            int codigo = Integer.parseInt(edCodigo.getText().toString());
            if (codigo == produto.getCodigo()){
                edCodigo.setError("Já existe um produto com o código informado. Tente outro!");
                return;
            }
        }
        if (edCodigo.getText().toString().isEmpty()){
            edCodigo.setError("Informe o código do produto!");
        }

        int codigo = Integer.parseInt(edCodigo.getText().toString());
        if(codigo <= 0){
            edCodigo.setError("Informe um código maior que 0 !");
            return;
        }
        if (edDescricao.getText().toString().isEmpty()){
            edDescricao.setError("Informe a descrição do produto!");
            return;
        }

        if (edValorUnitario.getText().toString().isEmpty()){
            edValorUnitario.setError("Informe o valor unitário do produto!");
            return;
        }
        Double valorUnitario = Double.parseDouble(edValorUnitario.getText().toString());
        if(valorUnitario <= 0){
            edCodigo.setError("Informe um valor unitário maior que 0 !");
            return;
        }
        Produto produto = new Produto();
        produto.setCodigo(codigo);
        produto.setDescricao(edDescricao.getText().toString());
        produto.setValorUnitario(valorUnitario);
        Controller.getInstance().salvarProduto(produto);
        Toast.makeText(ProdutoActivity.this,
                "Produto Cadastrado com Sucesso!!",
                Toast.LENGTH_LONG).show();
        this.finish();
    }
}