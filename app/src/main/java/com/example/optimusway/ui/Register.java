package com.example.optimusway.ui;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.optimusway.R;

public class Register extends AppCompatActivity {

    private EditText nome;
    private EditText idade;
    private EditText empresa;
    private EditText email;
    private EditText senha;
    private UsuarioDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);
            nome = findViewById(R.id.nome);
            idade = findViewById(R.id.idade);
            empresa = findViewById(R.id.empresa);
            email = findViewById(R.id.email);
            senha = findViewById(R.id.senha);
            dao = new UsuarioDAO(this);

    }
    public void registrar (View view){

        Usuario user = new Usuario();

        user.setNome(nome.getText().toString());
        user.setIdade(idade.getText().toString());
        user.setEmpresa(empresa.getText().toString());
        user.setEmail(email.getText().toString());
        user.setSenha(senha.getText().toString());
        long id = dao.inserir(user);
        Toast.makeText(this, "Usuario cadastro com id: " + id, Toast.LENGTH_SHORT).show();
    }
}
