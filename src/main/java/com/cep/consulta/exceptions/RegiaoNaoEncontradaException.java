package com.cep.consulta.exceptions;

public class RegiaoNaoEncontradaException extends Exception {
    public RegiaoNaoEncontradaException() {
        super("Região não encontrada");
    }
}
