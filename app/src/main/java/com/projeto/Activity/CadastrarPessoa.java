package com.projeto.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.mauro.wsrest.R;
import com.projeto.Dao.PessoaDao;
import com.projeto.Modal.Pessoa;
import com.projeto.Util.Mask;

public class CadastrarPessoa extends AppCompatActivity implements GridView.OnClickListener, Runnable {

    private EditText editTextId;
    private EditText editTextCpf;
    private EditText editTextNome;
    private Button btnSalvar;
    private ProgressDialog proDialog;
    private AlertDialog alertDialog;
    private AlertDialog.Builder alerta;
    private Handler handler = new Handler();
    private Pessoa pessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_pessoa);
        // eu
        editTextCpf = (EditText) findViewById(R.id.editTextCpf);
        editTextCpf.addTextChangedListener(Mask.insert("###.###.###-##", editTextCpf));

        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void voltarTelaPrincipal(View view) {
        Intent intentPrincipal = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intentPrincipal);
    }

    @Override
    public void onClick(View view) {

        proDialog = new ProgressDialog(this);
        proDialog.setTitle("Informação");
        proDialog.setMessage("Salvando ...");
        proDialog.show();

        editTextCpf = (EditText) findViewById(R.id.editTextCpf);
        String cpf = Mask.unmask(editTextCpf.getText().toString());
        editTextNome = (EditText) findViewById(R.id.editTextNome);
        editTextId = (EditText) findViewById(R.id.editTextCodigo);

        pessoa = new Pessoa();
        pessoa.setCpf(cpf);
        pessoa.setNome(editTextNome.getText().toString());
        pessoa.setCodigo(Long.parseLong(editTextId.getText().toString()));

        String retorno = pessoa.validarPessoa(pessoa);

        if (!retorno.isEmpty()) {
            exibirAlertaPessoa(retorno);
        } else {
            Thread tread = new Thread(this);
            tread.start();
        }
    }


    @Override
    public void run() {
        try {

            final PessoaDao pessoaDao = new PessoaDao();
           // final String url = "http://192.168.1.5:8080/RestFulWS/pessoa/incluir";
            final String url = "http://hcfsolutions.com.br:8080/RestFulWS/pessoa/incluir";
           // final String url =   "http://192.168.82.126:8080/RestFulWS/pessoa/incluir";
            String retorno =  pessoaDao.incluir(url ,pessoa);
            handler.post(new Runnable() {
                @Override
                public void run() {

                }
            });

            if (retorno.equals("200")) {
                Intent intentPrincipal = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentPrincipal);
            }else{
                String msg = "Dados não processados. \nFavor tente novamente!";
                exibirAlertaPessoa(msg);
            }

            Log.i("JSON", "--------------------------------------------------------------------------------");
            Log.i("JSON", "--------------------------------------------------------------------------------");
        } catch (Exception erro) {
            Log.e("ERRO", erro.getMessage());
        } finally {
            proDialog.dismiss();
        }
    }

    private void exibirAlertaPessoa(String msg) {
        alerta = new AlertDialog.Builder(CadastrarPessoa.this);
        alerta.setTitle("Aviso");

        alerta.setIcon(R.mipmap.ic_aviso).setMessage(msg)
                .setCancelable(false)
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "Cancelar escolhido", Toast.LENGTH_SHORT).show();
                        proDialog.dismiss();
                    }
                }).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "OK escolhido", Toast.LENGTH_SHORT).show();
                proDialog.dismiss();
            }
        });
        alertDialog = alerta.create();
        alertDialog.show();
    }

}
