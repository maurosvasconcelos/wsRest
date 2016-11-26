package com.projeto.Modal;

/**
 * Created by mauro on 01/09/2016.
 */
public class Pessoa {

    private Long codigo;
    private String nome;
    private String cpf;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }


    public String validarPessoa(Pessoa pessoa){
        String msg ="";
        if(pessoa.getCodigo()== null || pessoa.getCodigo() == 0){
            msg+="Informe o CODIGO" +"\n";
        }
        if(pessoa.getNome()== null || pessoa.getNome().isEmpty()){
            msg+="Informe o NOME" +"\n";
        }
        if(pessoa.getCpf() == null || pessoa.getCpf().isEmpty()){
            msg+="Informe o CPF";
        }
        return  msg;
    }

}
