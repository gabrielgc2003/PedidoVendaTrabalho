package com.example.trabalhobimestral;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trabalhobimestral.model.Cliente;

public class ClienteActivity extends AppCompatActivity {

    private EditText edNome;
    private EditText edCpf;
    private Button btSalvar;
    private TextView tvListaCliente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
        setTitle("Cadastrar Cliente");
        edNome = findViewById(R.id.edNome);
        edCpf = findViewById(R.id.edCpf);
        btSalvar = findViewById(R.id.btSalvar);
        tvListaCliente = findViewById(R.id.tvListaCliente);
        atualizarListaCLientes();
        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarCliente();
            }
        });
    }

    private void atualizarListaCLientes() {
        String exibicao = "";
        for (Cliente cliente:
                Controller.getInstance().retornarClientes()){
            exibicao += "\nNome: "+cliente.getNome()+"\nCPF: "+cliente.getCpf() + "\n----------------------------------";
        }
        tvListaCliente.setText(exibicao);
    }

    private void salvarCliente() {
        if(edNome.getText().toString().isEmpty()){
            edNome.setError("Informe o nome do cliente!");
            return;
        }
        if(edCpf.getText().toString().isEmpty()){
            edCpf.setError("Informe o cpf do cliente!");
            return;
        }
        if(edCpf.getText().toString().length() != 11){
            edCpf.setError("O CPF deve conter 11 caracteres !");
            return;
        }
        Cliente cliente = new Cliente();
        cliente.setNome(edNome.getText().toString());
        cliente.setCpf(edCpf.getText().toString());
        Controller.getInstance().salvarCliente(cliente);
        Toast.makeText(ClienteActivity.this,
                "Cliente Cadastrado com Sucesso!!",
                Toast.LENGTH_LONG).show();
        this.finish();
    }
}