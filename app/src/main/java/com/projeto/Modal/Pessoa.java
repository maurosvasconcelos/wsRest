package com.projeto.Modal;

import java.util.Date;

/**
 * Created by mauro on 01/09/2016.
 */
public class Pessoa {

    private Long codigo;
    private String nome;
    private String cpf;
    private String horaCadastro;
    private Date dataCadastro;

    private String dataNascimento;
    private String email;
    private String celular;
    private String fixo;
    private String sexo;

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

    public String getHoraCadastro() {
        return horaCadastro;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public void setHoraCadastro(String horaCadastro) {
        this.horaCadastro = horaCadastro;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getFixo() {
        return fixo;
    }

    public void setFixo(String fixo) {
        this.fixo = fixo;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String validarPessoa(Pessoa pessoa) {
        String msg = "";

        if (pessoa.getNome() == null || pessoa.getNome().isEmpty()) {
            msg += "Informe o Nome" + "\n";
        }
        if (pessoa.getCpf() == null || pessoa.getCpf().isEmpty()) {
            msg += "Informe o Cpf" + "\n";;
        }
        if (pessoa.getDataNascimento() == null || pessoa.getDataNascimento().isEmpty()) {
            msg += "Informe a Data Nascimento" + "\n";;
        }
        if (pessoa.getSexo() == null || pessoa.getSexo().isEmpty()) {
            msg += "Informe o Sexo" + "\n";;
        }
        if (pessoa.getCelular() == null || pessoa.getCelular().isEmpty()) {
            msg += "Informe o Celular";
        }
        return msg;
    }


}
