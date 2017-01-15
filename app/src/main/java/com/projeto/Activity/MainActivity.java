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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mauro.wsrest.R;
import com.projeto.Dao.PessoaDao;
import com.projeto.Util.Mask;

public class MainActivity extends AppCompatActivity implements GridView.OnClickListener, Runnable {


    private Button btnListar;
    private TextView listaPessoas;
    private ProgressDialog proDialog;
    private Handler handler = new Handler();
    private EditText editTextCpf;
    private AlertDialog alertDialog;
    private AlertDialog.Builder alerta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextCpf = (EditText) findViewById(R.id.editTextCpf);
        editTextCpf.addTextChangedListener(Mask.insert("###.###.###-##", editTextCpf));

        btnListar = (Button) findViewById(R.id.btnListar);
        btnListar.setOnClickListener(this);

        listaPessoas = (TextView) findViewById(R.id.textViewLista);
        listaPessoas.setText("");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }


    @Override
    public void onClick(View view) {
        proDialog = new ProgressDialog(this);
        proDialog.setTitle("Informação");
        proDialog.setMessage("Processando ...");
        proDialog.show();

        editTextCpf = (EditText) findViewById(R.id.editTextCpf);
        String cpf = Mask.unmask(editTextCpf.getText().toString());
        if (cpf == null || cpf == "") {
            validarCpfVazio();
        } else {
            Thread tread = new Thread(this);
            tread.start();
        }

    }

    @Override
    public void run() {
        try {

            editTextCpf = (EditText) findViewById(R.id.editTextCpf);
            String cpf = Mask.unmask(editTextCpf.getText().toString());

            PessoaDao pessoaDao = new PessoaDao();
            //String url = "http://192.168.1.5:8080/RestFulWS/pessoa/listarTodos";
            //String url = "http://hcfsolutions.com.br:8080/RestFulWS/pessoa/consultarCpf" + cpf;
            //  String url = "http://192.168.82.126:8080/RestFulWS/pessoa/consultarCpf" + cpf;
            //web
            //String url = "http://189.112.226.70:85/RestFulWS/pessoa/consultarCpf" + cpf;
             //RES
            String url = "http://192.168.1.5:8084/RestFulWS/pessoa/consultarCpf" + cpf;


            final String resultado = pessoaDao.ConectarWS(url);
            if (resultado.equals("1")) {
                Intent intent = new Intent(getApplicationContext(), CadastrarPessoa.class);
                startActivity(intent);
            }

            handler.post(new Runnable() {
                @Override
                public void run() {
                    listaPessoas.setText(resultado);
                }
            });

        } catch (Exception erro) {
            Log.e("ERRO", erro.getMessage());
        } finally {
            proDialog.dismiss();
        }
    }


    private void validarCpfVazio() {
        alerta = new AlertDialog.Builder(MainActivity.this);
        alerta.setTitle("Aviso");
        alerta.setIcon(R.mipmap.ic_aviso).setMessage("Informe o CPF!")
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}